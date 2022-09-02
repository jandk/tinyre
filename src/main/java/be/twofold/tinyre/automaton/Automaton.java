package be.twofold.tinyre.automaton;

public final class Automaton {

    private final Transition[][] transitions;

    Automaton(Transition[][] transitions) {
        this.transitions = transitions;
    }

    public static AutomatonBuilder builder() {
        return new AutomatonBuilder();
    }

}
