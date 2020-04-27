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
}
