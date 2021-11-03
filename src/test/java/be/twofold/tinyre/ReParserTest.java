package be.twofold.tinyre;

import org.junit.jupiter.api.*;

import static be.twofold.tinyre.ReParser.*;
import static org.assertj.core.api.Assertions.*;

class ReParserTest {

    private final Re all = new Re.AnyChar();

    @Test
    void parseAcceptsEmptyString() {
        Re re = new ReParser("").parse();
        assertThat(re).isInstanceOfSatisfying(Re.Literal.class, literal -> {
            assertThat(literal.s).isEmpty();
        });
    }

    // region Quantifier

    @Test
    void quantifierAcceptsQuestion() {
        Re re = new ReParser("?").quantifier(all);
        assertThat(re).isInstanceOfSatisfying(Re.Repeat.class, repeat -> {
            assertThat(repeat.min).isEqualTo(0);
            assertThat(repeat.max).isEqualTo(1);
        });
    }

    @Test
    void quantifierAcceptsStar() {
        Re re = new ReParser("*").quantifier(all);
        assertThat(re).isInstanceOfSatisfying(Re.Repeat.class, repeat -> {
            assertThat(repeat.min).isEqualTo(0);
            assertThat(repeat.max).isEqualTo(Infinity);
        });
    }

    @Test
    void quantifierAcceptsPlus() {
        Re re = new ReParser("+").quantifier(all);
        assertThat(re).isInstanceOfSatisfying(Re.Repeat.class, repeat -> {
            assertThat(repeat.min).isEqualTo(1);
            assertThat(repeat.max).isEqualTo(Infinity);
        });
    }

    @Test
    void quantifierAcceptsCount() {
        Re re = new ReParser("{2}").quantifier(all);
        assertThat(re).isInstanceOfSatisfying(Re.Repeat.class, repeat -> {
            assertThat(repeat.min).isEqualTo(2);
            assertThat(repeat.max).isEqualTo(2);
        });
    }

    @Test
    void quantifierAcceptsMinMax() {
        Re re = new ReParser("{2,3}").quantifier(all);
        assertThat(re).isInstanceOfSatisfying(Re.Repeat.class, repeat -> {
            assertThat(repeat.min).isEqualTo(2);
            assertThat(repeat.max).isEqualTo(3);
        });
    }

    @Test
    void quantifierAcceptsMinOnly() {
        Re re = new ReParser("{2,}").quantifier(all);
        assertThat(re).isInstanceOfSatisfying(Re.Repeat.class, repeat -> {
            assertThat(repeat.min).isEqualTo(2);
            assertThat(repeat.max).isEqualTo(Infinity);
        });
    }

    @Test
    void quantifierAcceptsMaxOnly() {
        Re re = new ReParser("{,3}").quantifier(all);
        assertThat(re).isInstanceOfSatisfying(Re.Repeat.class, repeat -> {
            assertThat(repeat.min).isEqualTo(0);
            assertThat(repeat.max).isEqualTo(3);
        });
    }

    @Test
    void quantifierThrowsOnIllegalRange() {
        assertThatExceptionOfType(ReException.class)
            .isThrownBy(() -> new ReParser("{3,2}").quantifier(all))
            .withMessage("Invalid quantifier range");
    }

    @Test
    void quantifierThrowsOnOutOfRange() {
        assertThatExceptionOfType(ReException.class)
            .isThrownBy(() -> new ReParser("{9999999999}").quantifier(all))
            .withMessage("Invalid quantifier range");
    }

    @Test
    void quantifierThrowsOnUnclosedBrace() {
        assertThatExceptionOfType(ReException.class)
            .isThrownBy(() -> new ReParser("{2,3").quantifier(all))
            .withMessage("Expected '}'");
    }

    // endregion

}
