public class Util {
    public static boolean isCharLeft(int pos, String code) {
        return pos < code.length() - 1;
    }

    public static boolean isSeparator(char c) {
        return c == '(' || c == ')' || c == '{' || c == '}' ||
                c == '[' || c == ']' || c == ';' || c == ',' ||
                c == '@';
    }

    public static boolean isOperator(char c) {
        return  c == '=' || c == '>' || c == '<' || c == '!' ||
                c == '~' || c == ':' || c == '?' || c == '&' ||
                c == '|' || c == '+' || c == '-' || c == '*' ||
                c == '/' || c == '^' || c == '%';
    }

    public static boolean isEscapeSequence(String sequence) {
        return  "\\b".equals(sequence) || "\\t".equals(sequence) ||
                "\\n".equals(sequence) || "\\".equals(sequence) ||
                "\'".equals(sequence) || "\"".equals(sequence) ||
                "\\r".equals(sequence) || "\\f".equals(sequence);
    }

    public static boolean isOctalDigit(char c) {
        return Character.isDigit(c) && c != '8' && c != '9';
    }

    public static boolean isBinaryDigit(char c) {
        return c == '0' || c == '1';
    }

    public static boolean isHexDigit(char c) {
        return Character.isDigit(c) || c == 'a' ||
                c == 'b' || c == 'c' || c == 'd' ||
                c == 'e' || c == 'f' || c == 'A' ||
                c == 'B' || c == 'C' || c == 'D' ||
                c == 'E' || c == 'F';
    }

    public static boolean isFloatSuffix(char c) {
        return c == 'f' || c == 'F' || c == 'd' || c == 'D';
    }
}
