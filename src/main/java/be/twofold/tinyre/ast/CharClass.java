package be.twofold.tinyre.ast;

import be.twofold.tinyre.*;

public final class CharClass extends Re {
    public final char identifier;

    public CharClass(char identifier) {
        this.identifier = identifier;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitCharClass(this);
    }
}
