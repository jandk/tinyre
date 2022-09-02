package be.twofold.tinyre.automaton;

public final class State {

    private final int id;

    State(int id) {
        this.id = id;
    }

    int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof State
            && id == ((State) obj).id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

}
