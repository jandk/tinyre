package be.twofold.tinyre;

import org.junit.jupiter.api.*;

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
            assertThat(repeat.max).isEqualTo(Integer.MAX_VALUE);
        });
    }

    @Test
    void quantifierAcceptsPlus() {
        Re re = new ReParser("+").quantifier(all);
        assertThat(re).isInstanceOfSatisfying(Re.Repeat.class, repeat -> {
            assertThat(repeat.min).isEqualTo(1);
            assertThat(repeat.max).isEqualTo(Integer.MAX_VALUE);
        });
    }

    @Test
    void quantifierAcceptsMin() {
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
    void quantifierAcceptsMinUnbounded() {
        Re re = new ReParser("{2,}").quantifier(all);
        assertThat(re).isInstanceOfSatisfying(Re.Repeat.class, repeat -> {
            assertThat(repeat.min).isEqualTo(2);
            assertThat(repeat.max).isEqualTo(Integer.MAX_VALUE);
        });
    }

    @Test
    void classRangeAcceptsClasses() {
        Re re = new ReParser("\\d").classRange();
    }

}
