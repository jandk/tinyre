package be.twofold.tinyre.ast;

import be.twofold.tinyre.*;

public final class Repeat extends Re {
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
