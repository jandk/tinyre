package be.twofold.tinyre.ast;

import be.twofold.tinyre.*;

public final class Literal extends Re {
    public final String value;

    public Literal(String value) {
        this.value = value;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitLiteral(this);
    }
}
