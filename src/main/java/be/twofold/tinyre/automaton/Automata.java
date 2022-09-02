package be.twofold.tinyre.automaton;

public final class Automata {
    private Automata() {
    }

    public static Automaton makeCharRange(char min, char max) {
        AutomatonBuilder builder = Automaton.builder();
        State s1 = builder.createState();
        State s2 = builder.createState();
        builder.addTransition(s1, s2, min, max);
        return builder.build();
    }
}
