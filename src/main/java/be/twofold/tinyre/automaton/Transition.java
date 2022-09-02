package be.twofold.tinyre.automaton;

public final class Transition {

    private final char min;
    private final char max;
    private final int target;

    public Transition(char min, char max, int target) {
        if (min > max) {
            throw new IllegalArgumentException("min > max");
        }
        this.min = min;
        this.max = max;
        this.target = target;
    }

    public char getMin() {
        return min;
    }

    public char getMax() {
        return max;
    }

    public int getTarget() {
        return target;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Transition)) return false;

        Transition other = (Transition) obj;
        return min == other.min
            && max == other.max
            && target == other.target;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + Character.hashCode(min);
        result = 31 * result + Character.hashCode(max);
        result = 31 * result + Integer.hashCode(target);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        appendChar(builder, min);
        if (min != max) {
            builder.append('-');
            appendChar(builder, max);
        }
        return builder
            .append(" -> ")
            .append(target)
            .toString();
    }

    private static void appendChar(StringBuilder builder, char c) {
        if (c >= 0x21 && c <= 0x7e && c != '\\' && c != '"') {
            builder.append(c);
        } else {
            builder
                .append("\\u")
                .append(Integer.toHexString(c | 0x10000).substring(1));
        }
    }

}
