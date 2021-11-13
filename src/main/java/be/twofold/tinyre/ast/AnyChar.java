package be.twofold.tinyre.ast;

public final class AnyChar extends Re {
    public static AnyChar Instance = new AnyChar();

    private AnyChar() {
    }

    @Override
    void toString(StringBuilder builder) {
        builder.append('.');
    }
}
