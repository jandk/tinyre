package be.twofold.tinyre;

final class Ascii {

    private static final String Table = "" +
        "\u0100\u0100\u0100\u0100\u0100\u0100\u0100\u0100" +
        "\u0100\u0580\u0180\u0180\u0180\u0180\u0100\u0100" +
        "\u0100\u0100\u0100\u0100\u0100\u0100\u0100\u0100" +
        "\u0100\u0100\u0100\u0100\u0100\u0100\u0100\u0100" +
        "\u0480\u0200\u0200\u0200\u0200\u0200\u0200\u0200" +
        "\u0200\u0200\u0200\u0200\u0200\u0200\u0200\u0200" +
        "\u0840\u0841\u0842\u0843\u0844\u0845\u0846\u0847" +
        "\u0848\u0849\u0200\u0200\u0200\u0200\u0200\u0200" +
        "\u0200\u081a\u081b\u081c\u081d\u081e\u081f\u0010" +
        "\u0010\u0010\u0010\u0010\u0010\u0010\u0010\u0010" +
        "\u0010\u0010\u0010\u0010\u0010\u0010\u0010\u0010" +
        "\u0010\u0010\u0010\u0200\u0200\u0200\u0200\u0200" +
        "\u0200\u082a\u082b\u082c\u082d\u082e\u082f\u0020" +
        "\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020" +
        "\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020" +
        "\u0020\u0020\u0020\u0200\u0200\u0200\u0200\u0100";

    static final int Upper = 0x0010;
    static final int Lower = 0x0020;
    static final int Digit = 0x0040;
    static final int Space = 0x0080;
    static final int Cntrl = 0x0100;
    static final int Punct = 0x0200;
    static final int Blank = 0x0400;
    static final int Xdigit = 0x0800;

    static final int Alpha = Upper | Lower;
    static final int Alnum = Upper | Lower | Digit;
    static final int Graph = Upper | Lower | Digit | Punct;

    private Ascii() {
    }

    static int getType(int c) {
        return isAscii(c) ? Table.charAt(c) : 0;
    }

    static boolean isAscii(int c) {
        return (c & ~0x7f) == 0;
    }

    static boolean isType(int c, int mask) {
        return (getType(c) & mask) != 0;
    }

    static boolean isUpper(int c) {
        return isType(c, Upper);
    }

    static boolean isLower(int c) {
        return isType(c, Lower);
    }

    static boolean isDigit(int c) {
        return isType(c, Digit);
    }

    static boolean isSpace(int c) {
        return isType(c, Space);
    }

    static boolean isCntrl(int c) {
        return isType(c, Cntrl);
    }

    static boolean isPunct(int c) {
        return isType(c, Punct);
    }

    static boolean isBlank(int c) {
        return isType(c, Blank);
    }

    static boolean isXdigit(int c) {
        return isType(c, Xdigit);
    }

    static boolean isAlpha(int c) {
        return isType(c, Alpha);
    }

    static boolean isAlnum(int c) {
        return isType(c, Alnum);
    }

    static boolean isGraph(int c) {
        return isType(c, Graph);
    }

    static int toDigit(int ch) {
        return Table.charAt(ch & 0x7f) & 0x0f;
    }

    static int toLower(int ch) {
        return isUpper(ch) ? ch + 0x20 : ch;
    }

    static int toUpper(int ch) {
        return isLower(ch) ? ch - 0x20 : ch;
    }

}
