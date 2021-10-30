package be.twofold.tinyre;

import java.util.*;

public abstract class Re {

    public abstract <R> R accept(Visitor<R> visitor);

    public interface Visitor<R> {

        R visitAlternative(Alternative re);

        R visitAnyChar(AnyChar re);

        R visitCharClass(CharClass re);

        R visitCharRange(CharRange re);

        R visitComplement(Complement re);

        R visitDisjunction(Disjunction re);

        R visitIntersection(Intersection re);

        R visitLiteral(Literal re);

        R visitRepeat(Repeat re);

    }

    public static class Alternative extends Re {
        public final List<Re> exprs;

        public Alternative(List<Re> exprs) {
            this.exprs = exprs;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitAlternative(this);
        }
    }

    public static class AnyChar extends Re {

        public AnyChar() {
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitAnyChar(this);
        }
    }

    public static class CharClass extends Re {
        public final char identifier;

        public CharClass(char identifier) {
            this.identifier = identifier;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitCharClass(this);
        }
    }

    public static class CharRange extends Re {
        public final char min;
        public final char max;

        public CharRange(char min, char max) {
            this.min = min;
            this.max = max;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitCharRange(this);
        }
    }

    public static class Complement extends Re {
        public final Re expr;

        public Complement(Re expr) {
            this.expr = expr;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitComplement(this);
        }
    }

    public static class Disjunction extends Re {
        public final List<Re> exprs;

        public Disjunction(List<Re> exprs) {
            this.exprs = exprs;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitDisjunction(this);
        }
    }

    public static class Intersection extends Re {
        public final Re left;
        public final Re right;

        public Intersection(Re left, Re right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitIntersection(this);
        }
    }

    public static class Literal extends Re {
        public final String s;

        public Literal(String s) {
            this.s = s;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitLiteral(this);
        }
    }

    public static class Repeat extends Re {
        public final Re expr;
        public final int min;
        public final int max;

        public Repeat(Re expr, int min, int max) {
            this.expr = expr;
            this.min = min;
            this.max = max;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitRepeat(this);
        }
    }

}
