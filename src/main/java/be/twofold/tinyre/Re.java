package be.twofold.tinyre;

import be.twofold.tinyre.ast.*;

public abstract class Re {

    public abstract <R> R accept(Visitor<R> visitor);

}
