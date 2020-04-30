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
}
