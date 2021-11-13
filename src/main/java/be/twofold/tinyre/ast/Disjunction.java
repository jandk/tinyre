package be.twofold.tinyre.ast;

import java.util.*;

public final class Disjunction extends Re {
    public final List<Re> exprs;

    public Disjunction(List<Re> exprs) {
        this.exprs = exprs;
    }

    @Override
    void toString(StringBuilder builder) {
        builder.append('(');
        Iterator<Re> iterator = exprs.iterator();
        iterator.next().toString(builder);
        while (iterator.hasNext()) {
            iterator.next().toString(builder);
            builder.append('|');
        }
        builder.append(')');
    }
}
