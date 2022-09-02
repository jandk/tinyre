package be.twofold.tinyre.test;

public final class Range implements Comparable<Range> {
    private final int lower;
    private final int upper;

    private Range(int lower, int upper) {
        this.lower = lower;
        this.upper = upper;
    }

    public static Range of(int value) {
        return new Range(value, value);
    }

    public static Range of(int lower, int upper) {
        return new Range(lower, upper);
    }

    public int getLower() {
        return lower;
    }

    public int getUpper() {
        return upper;
    }

    @Override
    public int compareTo(Range o) {
        int compare = Integer.compare(lower, o.lower);
        if (compare != 0) {
            return compare;
        }
        return Integer.compare(upper, o.upper);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Range)) return false;

        Range range = (Range) other;
        return lower == range.lower
            && upper == range.upper;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + lower;
        result = 31 * result + upper;
        return result;
    }

    @Override
    public String toString() {
        return "[" + lower + (lower == upper ? "" : " -> " + upper) + "]";
    }
}
