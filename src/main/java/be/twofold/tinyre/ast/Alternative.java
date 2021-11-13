package be.twofold.tinyre.ast;

import java.util.*;

public final class Alternative extends Re {
    public final List<Re> exprs;

    public Alternative(List<Re> exprs) {
        this.exprs = exprs;
    }

    @Override
    void toString(StringBuilder builder) {
        for (Re expr : exprs) {
            expr.toString(builder);
        }
    }
}
