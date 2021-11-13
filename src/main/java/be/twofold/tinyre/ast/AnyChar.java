package be.twofold.tinyre.ast;

import be.twofold.tinyre.*;

public final class AnyChar extends Re {
    public static AnyChar Instance = new AnyChar();

    private AnyChar() {
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitAnyChar(this);
    }
}
