package be.twofold.tinyre;

import java.util.*;

public final class ReParser {

    static final int Infinity = Integer.MAX_VALUE;

    private final String source;
    private int position;

    public ReParser(String source) {
        this.source = Objects.requireNonNull(source);
    }

    public Re parse() {
        Re expr = disjunction();
        if (!eof()) {
            throw error("Expected EOF");
        }
        return expr;
    }

    private Re disjunction() {
        List<Re> list = new ArrayList<>();
        do {
            list.add(alternative());
        } while (match('|'));

        return list.size() == 1 ? list.get(0) : new Re.Disjunction(list);
    }

    private Re alternative() {
        // Alternatives can be empty
        if (eof() || peek(")|")) {
            return new Re.Literal("");
        }

        List<Re> list = new ArrayList<>();
        do {
            list.add(term());
        } while (!peek(")|"));

        return list.size() == 1 ? list.get(0) : new Re.Alternative(list);
    }

    private Re term() {
        return quantifier(atom());
    }

    private Re atom() {
        if (match('.')) {
            return new Re.AnyChar();
        }

        if (match('[')) {
            return characterClass();
        }

        if (match('(')) {
            read();
            Re expr = disjunction();
            if (!match(')')) {
                throw error("expected ')'");
            }
            return expr;
        }

        if (match('\\')) {
        }

        throw new UnsupportedOperationException();
    }

    Re quantifier(Re expr) {
        if (match('?')) {
            return new Re.Repeat(expr, 0, 1);
        }
        if (match('*')) {
            return new Re.Repeat(expr, 0, Infinity);
        }
        if (match('+')) {
            return new Re.Repeat(expr, 1, Infinity);
        }
        if (match('{')) {
            int min = digits(0);
            int max = match(',') ? digits(Infinity) : min;
            if (!match('}')) {
                throw error("Expected '}'");
            }
            if (min > max) {
                throw error("Invalid quantifier range");
            }
            return new Re.Repeat(expr, min, max);
        }
        return expr;
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

    private Re characterClass() {
        boolean negate = match('^');

        Re expr = classRanges();
        if (!match(']')) {
            throw error("expected ']'");
        }
        if (negate) {
            return new Re.Intersection(new Re.AnyChar(), new Re.Complement(expr));
        }
        return expr;

    }

    private Re classRanges() {
        List<Re> list = new ArrayList<>();
        do {
            list.add(classRange());
        } while (!match(']'));

        return list.size() == 1 ? list.get(0) : new Re.Disjunction(list);
    }

    Re classRange() {
        if (match('\\')) {
            Re charClass = characterClassEscape();
            if (charClass != null) {
                return charClass;
            }
        }

        char c = characterEscape();
        if (match('-')) {
            return new Re.CharRange(c, characterEscape());
        }

        return new Re.Literal(String.valueOf(c));
    }

    private Re characterClassEscape() {
        char ch = peek();
        switch (ch) {
            case 'D':
            case 'S':
            case 'W':
            case 'd':
            case 's':
            case 'w':
                return new Re.CharClass(read());
        }
        if (!Ascii.isAlnum(ch)) {
            throw error("Invalid escape");
        }
        return null;
    }

    private char characterEscape() {
        match('\\');
        return read();
    }


    private RuntimeException error(String message) {
        return new ReException(message);
    }

    private char peek() {
        return eof() ? 0 : source.charAt(position);
    }

    private boolean peek(String chars) {
        return chars.indexOf(peek()) >= 0;
    }

    private boolean match(char ch) {
        if (peek() == ch) {
            position++;
            return true;
        }
        return false;
    }

    private char read() {
        if (eof()) {
            throw error("Unexpected EOF");
        }
        return source.charAt(position++);
    }

    private boolean eof() {
        return position >= source.length();
    }

}
