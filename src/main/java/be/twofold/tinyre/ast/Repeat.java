package be.twofold.tinyre.ast;

public final class Repeat extends Re {
    public static final int Infinity = Integer.MAX_VALUE;

    public final Re expr;
    public final int min;
    public final int max;

    public Repeat(Re expr, int min, int max) {
        this.expr = expr;
        this.min = min;
        this.max = max;
    }

    @Override
    void toString(StringBuilder builder) {
        expr.toString(builder);

        if (min == 0 && max == 1) {
            builder.append('?');
        } else if (min == 0 && max == Infinity) {
            builder.append('*');
        } else if (min == 1 && max == Infinity) {
            builder.append('+');
        } else if (min == max) {
            builder.append('{').append(min).append('}');
        } else {
            builder.append('{').append(min).append(',').append(max);
        }
    }
}
