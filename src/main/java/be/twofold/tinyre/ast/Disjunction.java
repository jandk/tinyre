package be.twofold.tinyre.ast;

import be.twofold.tinyre.*;

import java.util.*;

public final class Disjunction extends Re {
    public final List<Re> exprs;

    public Disjunction(List<Re> exprs) {
        this.exprs = exprs;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitDisjunction(this);
    }
}
