package be.twofold.tinyre;

import java.util.*;

public final class ReParser {

    static final int Infinity = Integer.MAX_VALUE;

    private final String source;
    private int position;

    public ReParser(String source) {
        this.source = Objects.requireNonNull(source);
    }

    public static Re parse(String source) {
        return new ReParser(source).parse();
    }

    public Re parse() {
        Re expr = disjunction();
        if (!eof()) {
            throw error("Expected EOF");
        }
        return expr;
    }

    private Re disjunction() {
        Re expr = alternative();
        if (peek() != '|') {
            return expr;
        }

        List<Re> exprs = new ArrayList<>();
        exprs.add(expr);
        while (match('|')) {
            exprs.add(alternative());
        }
        return new Re.Disjunction(exprs);
    }

    private Re alternative() {
        List<Re> exprs = new ArrayList<>();
        while (!eof() && peek() != '|' && peek() != ')') {
            exprs.add(term());
        }

        switch (exprs.size()) {
            case 0:
                return new Re.Literal("");
            case 1:
                return exprs.get(0);
            default:
                return new Re.Alternative(exprs);
        }
    }

    private Re term() {
        return quantifier(atom());
    }

    private Re atom() {
        switch (peek()) {
            case '(':
                read();
                Re expr = disjunction();
                if (!match(')')) {
                    throw error("Unclosed group");
                }
                return expr;

            case '.':
                read();
                return new Re.AnyChar();

            case '[':
                throw new UnsupportedOperationException();

            case '\\':
                read();
                return escape();

            default:
                return literal(read());
        }
    }

    private Re quantifier(Re expr) {
        switch (peek()) {
            case '?':
                read();
                return new Re.Repeat(expr, 0, 1);

            case '*':
                read();
                return new Re.Repeat(expr, 0, Infinity);

            case '+':
                read();
                return new Re.Repeat(expr, 1, Infinity);

            case '{':
                read();
                int min = digits(0);
                int max = match(',') ? digits(Infinity) : min;
                if (!match('}')) {
                    throw error("Expected '}'");
                }
                if (min > max) {
                    throw error("Invalid quantifier range");
                }
                return new Re.Repeat(expr, min, max);

            default:
                return expr;
        }
    }

    private int digits(int defaultValue) {
        int start = position;
        while (Ascii.isDigit(peek())) {
            position++;
        }
        if (start == position) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(source.substring(start, position));
        } catch (NumberFormatException e) {
            throw error("Invalid quantifier range");
        }
    }

    private Re escape() {
        char ch = read();
        switch (ch) {
            case 'D':
                return new Re.CharClass('D');
            case 'S':
                return new Re.CharClass('S');
            case 'W':
                return new Re.CharClass('W');
            case 'a':
                return literal('\u0007');
            case 'd':
                return new Re.CharClass('d');
            case 'e':
                return literal('\u001b');
            case 'f':
                return literal('\f');
            case 'n':
                return literal('\n');
            case 'r':
                return literal('\r');
            case 's':
                return new Re.CharClass('s');
            case 't':
                return literal('\t');
            case 'u':
                return literal(readHex(4));
            case 'w':
                return new Re.CharClass('w');
            case 'x':
                return literal(readHex(2));

            default:
                if (Ascii.isAlnum(ch)) {
                    throw error("Invalid escape sequence");
                }
                return literal(ch);
        }
    }

    private char readHex(int n) {
        int result = 0;
        for (int i = 0; i < n; i++) {
            char ch = read();
            if (!Ascii.isXdigit(ch)) {
                throw error("Invalid hexadecimal digit");
            }

            result = (result << 4) | Ascii.toDigit(ch);
        }
        return (char) result;
    }

    private Re.Literal literal(char ch) {
        return new Re.Literal(String.valueOf(ch));
    }

    // region Helpers

    private RuntimeException error(String message) {
        return new ReException(message);
    }

    private char peek() {
        return eof() ? 0 : source.charAt(position);
    }

    private char read() {
        return eof() ? 0 : source.charAt(position++);
    }

    private boolean match(char ch) {
        if (peek() == ch) {
            position++;
            return true;
        }
        return false;
    }

    private boolean eof() {
        return position >= source.length();
    }

    // endregion

}
