package be.twofold.tinyre.automaton;

import java.util.*;

public final class AutomatonBuilder {
    private final List<List<Transition>> transitions = new ArrayList<>();

    private int nextId;

    AutomatonBuilder() {
    }

    public State createState() {
        return new State(nextId++);
    }

    public void addTransition(State source, State target, char match) {
        addTransition(source, target, match, match);
    }

    public void addTransition(State source, State target, char min, char max) {
        transitions
            .get(source.getId())
            .add(new Transition(min, max, target.getId()));
    }

    public Automaton build() {
        throw new UnsupportedOperationException();
    }

}
