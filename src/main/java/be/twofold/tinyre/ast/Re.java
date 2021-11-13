package be.twofold.tinyre.ast;

public abstract class Re {

    abstract void toString(StringBuilder builder);

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        toString(builder);
        return builder.toString();
    }

}
