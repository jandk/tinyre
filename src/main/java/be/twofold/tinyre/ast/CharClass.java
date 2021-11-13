package be.twofold.tinyre.ast;

public final class CharClass extends Re {
    public final char identifier;

    public CharClass(char identifier) {
        this.identifier = identifier;
    }

    @Override
    void toString(StringBuilder builder) {
        builder.append('\\').append(identifier);
    }
}
