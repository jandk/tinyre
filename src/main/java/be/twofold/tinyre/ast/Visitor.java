package be.twofold.tinyre.ast;

public interface Visitor<R> {

    R visitAlternative(Alternative re);

    R visitAnyChar(AnyChar re);

    R visitCharClass(CharClass re);

    R visitDisjunction(Disjunction re);

    R visitLiteral(Literal re);

    R visitRepeat(Repeat re);

}
