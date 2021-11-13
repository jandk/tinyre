package be.twofold.tinyre;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.util.stream.*;

import static be.twofold.tinyre.ReParser.*;
import static org.assertj.core.api.Assertions.*;

class ReParserTest {

    @Test
    void parseAcceptsEmptyString() {
        assertThat(parse(""))
            .isInstanceOfSatisfying(Re.Literal.class, literal -> {
                assertThat(literal.s).isEmpty();
            });
    }


    @Test
    void parseSingleCharacter() {
        assertThat(parse("a"))
            .isInstanceOfSatisfying(Re.Literal.class, literal -> {
                assertThat(literal.s).isEqualTo("a");
            });
    }

    @Test
    void parseDisjunction() {
        assertThat(parse("a|b|c"))
            .isInstanceOfSatisfying(Re.Disjunction.class, disjunction -> {
                assertThat(disjunction.exprs).hasSize(3);
            });
    }

    @Test
    void parseAlternative() {
        assertThat(parse("abc"))
            .isInstanceOfSatisfying(Re.Alternative.class, alternative -> {
                assertThat(alternative.exprs).hasSize(3);
            });
    }

    @Test
    void parseQuantifierQuestion() {
        assertThat(parse("a?"))
            .isInstanceOfSatisfying(Re.Repeat.class, repeat -> {
                assertThat(repeat.min).isEqualTo(0);
                assertThat(repeat.max).isEqualTo(1);
            });
    }

    @Test
    void parseQuantifierStar() {
        assertThat(parse("a*"))
            .isInstanceOfSatisfying(Re.Repeat.class, repeat -> {
                assertThat(repeat.min).isEqualTo(0);
                assertThat(repeat.max).isEqualTo(ReParser.Infinity);
            });
    }

    @Test
    void parseQuantifierPlus() {
        assertThat(parse("a+"))
            .isInstanceOfSatisfying(Re.Repeat.class, repeat -> {
                assertThat(repeat.min).isEqualTo(1);
                assertThat(repeat.max).isEqualTo(ReParser.Infinity);
            });
    }

    @Test
    void parseQuantifierCount() {
        assertThat(parse("a{2}"))
            .isInstanceOfSatisfying(Re.Repeat.class, repeat -> {
                assertThat(repeat.min).isEqualTo(2);
                assertThat(repeat.max).isEqualTo(2);
            });
    }

    @Test
    void parseQuantifierMinMax() {
        assertThat(parse("a{2,3}"))
            .isInstanceOfSatisfying(Re.Repeat.class, repeat -> {
                assertThat(repeat.min).isEqualTo(2);
                assertThat(repeat.max).isEqualTo(3);
            });
    }

    @Test
    void parseQuantifierMinOnly() {
        assertThat(parse("a{2,}"))
            .isInstanceOfSatisfying(Re.Repeat.class, repeat -> {
                assertThat(repeat.min).isEqualTo(2);
                assertThat(repeat.max).isEqualTo(ReParser.Infinity);
            });
    }

    @Test
    void parseQuantifierMaxOnly() {
        assertThat(parse("a{,3}"))
            .isInstanceOfSatisfying(Re.Repeat.class, repeat -> {
                assertThat(repeat.min).isEqualTo(0);
                assertThat(repeat.max).isEqualTo(3);
            });
    }

    @Test
    void parseAtomParentheses() {
        assertThat(parse("(a)"))
            .isInstanceOfSatisfying(Re.Literal.class, literal -> {
                assertThat(literal.s).isEqualTo("a");
            });
    }

    @Test
    void parseAtomAnyChar() {
        assertThat(parse("."))
            .isInstanceOf(Re.AnyChar.class);
    }

    @ParameterizedTest
    @ValueSource(chars = {'d', 'D', 's', 'S', 'w', 'W'})
    void parseEscapeClasses(char input) {
        assertThat(parse("\\" + input))
            .isInstanceOfSatisfying(Re.CharClass.class, charClass -> {
                assertThat(charClass.identifier).isEqualTo(input);
            });
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForParseEscapeCharacters")
    void parseEscapeCharacters(String input, String expected) {
        assertThat(parse("\\" + input))
            .isInstanceOfSatisfying(Re.Literal.class, charClass -> {
                assertThat(charClass.s).isEqualTo(expected);
            });
    }

    @Test
    void parseEscapeHex() {
        assertThat(parse("\\x42"))
            .isInstanceOfSatisfying(Re.Literal.class, charClass -> {
                assertThat(charClass.s).isEqualTo("B");
            });
    }

    @Test
    void parseEscapeUnicode() {
        assertThat(parse("\\u20ac"))
            .isInstanceOfSatisfying(Re.Literal.class, charClass -> {
                assertThat(charClass.s).isEqualTo("\u20ac");
            });
    }

    @Test
    void parseEscapeBackslash() {
        assertThat(parse("\\-"))
            .isInstanceOfSatisfying(Re.Literal.class, charClass -> {
                assertThat(charClass.s).isEqualTo("-");
            });
    }

    private static Stream<Arguments> provideArgumentsForParseEscapeCharacters() {
        return Stream.of(
            Arguments.of("a", "\u0007"),
            Arguments.of("e", "\u001b"),
            Arguments.of("f", "\f"),
            Arguments.of("n", "\n"),
            Arguments.of("r", "\r"),
            Arguments.of("t", "\t")
        );
    }

    // region Failures

    @Test
    void parseThrowsOnExtraParentheses() {
        assertThatExceptionOfType(ReException.class)
            .isThrownBy(() -> parse("(a))"))
            .withMessage("Expected EOF");
    }

    @Test
    void parseThrowsOnUnclosedGroup() {
        assertThatExceptionOfType(ReException.class)
            .isThrownBy(() -> parse("(a"))
            .withMessage("Unclosed group");
    }


    @Test
    void parseThrowsOnIllegalRange() {
        assertThatExceptionOfType(ReException.class)
            .isThrownBy(() -> parse("a{3,2}"))
            .withMessage("Invalid quantifier range");
    }

    @Test
    void parseThrowsOnOutOfRange() {
        assertThatExceptionOfType(ReException.class)
            .isThrownBy(() -> parse("a{9999999999}"))
            .withMessage("Invalid quantifier range");
    }

    @Test
    void parseThrowsOnUnclosedBrace() {
        assertThatExceptionOfType(ReException.class)
            .isThrownBy(() -> parse("a{2,3"))
            .withMessage("Expected '}'");
    }

    @Test
    void parseThrowsOnIllegalEscape() {
        assertThatExceptionOfType(ReException.class)
            .isThrownBy(() -> parse("\\z"))
            .withMessage("Invalid escape sequence");
    }

    @Test
    void parseThrowsOnInvalidHex() {
        assertThatExceptionOfType(ReException.class)
            .isThrownBy(() -> parse("\\xag"))
            .withMessage("Invalid hexadecimal digit");
    }

    // endregion

}
