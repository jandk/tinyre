package be.twofold.tinyre;

final class Ascii {

    private Ascii() {
    }

    static boolean isAlnum(char c) {
        return isAlpha(c) || isDigit(c);
    }

    static boolean isAlpha(char c) {
        return isLower(c) || isUpper(c);
    }

    static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    static boolean isLower(char c) {
        return c >= 'a' && c <= 'z';
    }

    static boolean isUpper(char c) {
        return c >= 'A' && c <= 'Z';
    }

}
