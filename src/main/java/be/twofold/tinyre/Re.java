package be.twofold.tinyre;

public abstract class Re {

    public abstract <R> R accept(Visitor<R> visitor);

    public interface Visitor<R> {

        R visitAnyChar(AnyChar re);

        R visitCharRange(CharRange re);

        R visitComplement(Complement re);

        R visitConcatenation(Concatenation re);

        R visitIntersection(Intersection re);

        R visitLiteral(Literal re);

        R visitPredefined(Predefined re);

        R visitRepeat(Repeat re);

        R visitUnion(Union re);

    }

    public static class AnyChar extends Re {

        public AnyChar() {
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitAnyChar(this);
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

    public static class Concatenation extends Re {
        public final Re left;
        public final Re right;

        public Concatenation(Re left, Re right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitConcatenation(this);
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

    public static class Predefined extends Re {
        public final char identifier;

        public Predefined(char identifier) {
            this.identifier = identifier;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitPredefined(this);
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

    public static class Union extends Re {
        public final Re left;
        public final Re right;

        public Union(Re left, Re right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitUnion(this);
        }
    }

}
