package be.twofold.tinyre.ast;

public final class Literal extends Re {
    public final String value;

    public Literal(String value) {
        this.value = value;
    }

    @Override
    void toString(StringBuilder builder) {
        builder.append(value);
    }
}
