import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

public class Lexer {
    private int state;
    private List<Token> tokens;
    private final String code;
    private StringBuilder buffer;
    private int letter;

    static final Logger logger = LogManager.getLogger(Logger.class);

    public Lexer(final String filePath) {
        state = 0;
        tokens = new LinkedList<>();
        buffer = new StringBuilder();

        File input_file = new File(filePath);
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(input_file.toPath());
        } catch (IOException e) {
            logger.error("Cannot read file " + filePath);
            e.printStackTrace();
        }
        code = new String(bytes, StandardCharsets.UTF_8);
    }

    public void parse() {
        for(letter = 0; letter < code.length(); ++letter) {
            char c = code.charAt(letter);
            switch (state) {
                case 0: initialState(c); break;
                case 1: slash(c); break;
                case 2: identifier(c); break;
                case 4: nonzeroDigit(c); break;
                case 5: charLiteral(c); break;
                case 6: stringLiteral(c); break;
                case 7: dot(c); break;
                case 8: greater(c); break;
                case 9: less(c); break;
                case 10: ampersand(c); break;
                case 11: singleOperator(c); break;
                case 12: colon(c); break;
                case 13: plus(c); break;
                case 14: minus(c); break;
                case 15: singleLineComment(c); break;
                case 16: multiLineComment(c); break;
                case 17: starInMultiLineComment(c); break;
                case 18: divideEqual(c); break;
                case 19: maybeComment(c); break;
                case 20: pipe(c); break;
                case 21: colonOrSeparator(c); break;
                case 22: greaterGreater(c); break;
                //case 23: greaterGreaterGreater(c); break;
                case 24: lessLess(c); break;
                case 25: dotDot(c); break;
                case 26: identifier(c); break;
                case 27: ampersandOrPipe(c); break;
                case 28: dotDotDot(c); break;
                case 29: maybeCommentAfterIdentifier(c); break;
                case 30: maybeEscapeSequence(c); break;
                case 31: maybeEscapeSequenceChar(c); break;
                case 32: expectEndOfChar(c); break;
                case 33: digitInChar(c); break;
                case 34: underlineInDigit(c); break;

                default: {
                    //initialState(c);
                    logger.warn("Unknown state " + c);
                    break;
                }
            }
        }
        HtmlConverter.convert(tokens);
    }

    /**
     * state 0
     * initial state of automata
     * buffer is empty
     */
    private void initialState(char c) {
        if(c == '/') {
            addToBuffer(c, 1);
        } else if(Character.isWhitespace(c)) {
            makeToken(TokenName.WHITESPACE, c);
            state = 0;
        } else if(Character.isJavaIdentifierStart(c)) {
            addToBuffer(c, 2);
        } else if(c == '0') {
            addToBuffer(c, 3);
        } else if(Character.isDigit(c)) {
            addToBuffer(c, 4);
        } else if(c == '\'') {
            addToBuffer(c, 5);
        } else if(c == '\"') {
            addToBuffer(c, 6);
        } else if(c == '.') {
            addToBuffer(c, 7);
        } else if(Util.isSeparator(c)) {
            makeToken(TokenName.SEPARATOR, c);
            state = 0;
        } else if(c == '>') {
            addToBuffer(c, 8);
        } else if(c == '<') {
            addToBuffer(c, 9);
        } else if(c == '&') {
            addToBuffer(c, 10);
        } else if(c == '^' || c == '!' || c == '*' || c == '=' || c == '%') {
            addToBuffer(c, 11);
        } else if(c == ':') {
            addToBuffer(c, 12);
        } else if(c == '+') {
            addToBuffer(c, 13);
        } else if(c == '-') {
            addToBuffer(c, 14);
        } else if(c == '?' || c == '~') {
            makeToken(TokenName.OPERATOR, c);
            state = 0;
        } else if(c == '#') {
            makeToken(TokenName.ERROR, c);
            state = -1;
        } else if(c == '|') {
            addToBuffer(c, 20);
        } else {
            logger.warn("Unrecognized character " + c);
        }
    }

    /**
     * state 1
     * / in buffer
     * possible states: //, /*, /=...
     */
    private void slash(char c) {
        if(c == '/') {
            addToBuffer(c, 15);
        } else if(c == '*') {
            addToBuffer(c, 16);
        } else if(c == '=') {
            addToBuffer(c, 18);
        } else if(c == ':') {
            addToBuffer(c, 21);
        } else if(Util.isOperator(c)) {
            addToBuffer(c, -1);
            //makeToken(TokenName.ERROR, buffer.toString());
        } else {
            makeToken(TokenName.OPERATOR, buffer.toString());
            letter--;
            state = 0;
        }
    }

    /**
     * state 15
     * // in buffer
     * it is a single line comment
     */
    private void singleLineComment(char c) {
        if(Character.isWhitespace(c) && c != '\t' && c != ' ') {
            makeToken(TokenName.COMMENT, buffer.toString());
            makeToken(TokenName.WHITESPACE, c);
            state = 0;
        } else {
            addToBuffer(c, 15);
        }
    }

    /*
    * state 16
    * /* in buffer
    * */
    private void multiLineComment(char c) {
        if(c == '*') {
            addToBuffer(c, 17);
        } else {
            addToBuffer(c, 16);
        }
    }

    /**
     * state 17
     * /*.....* in buffer
     */
    private void starInMultiLineComment(char c) {
        if(c == '/') {
            addToBuffer(c, 0);
            makeToken(TokenName.COMMENT, buffer.toString());
        } else {
            addToBuffer(c, 16);
        }
    }

    /**
     * state 18
     * OPERATOR= in buffer
     */
    private void divideEqual(char c) {
        if(c == '/') {
            addToBuffer(c, 19);
        } else if(c == ':') {
            addToBuffer(c, 21);
        } else if(Util.isOperator(c)) {
            addToBuffer(c, -1);
        } else {
            letter--;
            makeToken(TokenName.OPERATOR, buffer.toString());
            state = 0;
        }
    }

    /*
    * state 19
    * OPERATOR/ in buffer
    * */
    private void maybeComment(char c) {
        if(c == '/' || c == '*') {
            buffer.deleteCharAt(buffer.length() - 1);
            makeToken(TokenName.OPERATOR, buffer.toString());
            buffer.append('/');
            if(c == '/') {
                addToBuffer(c, 15);
            } else {
                addToBuffer(c, 16);
            }
        } else {
            letter--;
            state = -1;
        }
    }

    /*
    * state 21
    * OPERATOR: in buffer
    * */
    private void colonOrSeparator(char c) {
        if(c == ':') {
            buffer.deleteCharAt(buffer.length() - 1);
            makeToken(TokenName.OPERATOR, buffer.toString());
            makeToken(TokenName.SEPARATOR, "::");
            state = 0;
        } else {
            letter--;
            state = -1;
        }
    }

    /*
    * state 8
    * > in buffer
    * */
    private void greater(char c) {
        if(c == '=') {
            addToBuffer(c, 18);
        } else if(c == ':') {
            addToBuffer(c, 21);
        } else if(c == '>') {
            addToBuffer(c, 22);
        } else if(c == '/') {
            addToBuffer(c, 19);
        } else if(Util.isOperator(c)) {
            addToBuffer(c, -1);
        } else {
            makeToken(TokenName.OPERATOR, buffer.toString());
            letter--;
            state = 0;
        }
    }

    /*
    * state 22
    * >> in buffer
    * */
    private void greaterGreater(char c) {
        if(c == '=') {
            addToBuffer(c, 18);
        } else if(c == ':') {
            addToBuffer(c, 21);
        } else if(c == '>') {
            addToBuffer(c, 11);
        }else if(c == '/') {
            addToBuffer(c, 19);
        } else if(Util.isOperator(c)) {
            addToBuffer(c, -1);
        } else {
            makeToken(TokenName.OPERATOR, buffer.toString());
            letter--;
            state = 0;
        }
    }

    /*
     * state 9
     * < in buffer
     * */
    private void less(char c) {
        if(c == '=') {
            addToBuffer(c, 18);
        } else if(c == ':') {
            addToBuffer(c, 21);
        } else if(c == '<') {
            addToBuffer(c, 24);
        } else if(c == '/') {
            addToBuffer(c, 19);
        } else if(Util.isOperator(c)) {
            addToBuffer(c, -1);
        } else {
            makeToken(TokenName.OPERATOR, buffer.toString());
            letter--;
            state = 0;
        }
    }

    /*
     * state 24
     * << in buffer
     * */
    private void lessLess(char c) {
        if(c == '=') {
            addToBuffer(c, 18);
        } else if(c == ':') {
            addToBuffer(c, 21);
        } else if(c == '<') {
            addToBuffer(c, 11);
        } else if(c == '/') {
            addToBuffer(c, 19);
        } else if(Util.isOperator(c)) {
            addToBuffer(c, -1);
        } else {
            makeToken(TokenName.OPERATOR, buffer.toString());
            letter--;
            state = 0;
        }
    }

    /*
    * state 2 or 26
    * letter or $ or _ or digit in buffer
    * */
    private void identifier(char c) {
        if(Character.isJavaIdentifierPart(c)) {
            addToBuffer(c, 26);
        } else if(c == '#') {
            addToBuffer(c, -1);
        } else if(c == '/') {
            addToBuffer(c, 29);
        } else {
            if(isNullLiteral(buffer.toString())) {
                makeToken(TokenName.NULL_LITERAL, buffer.toString());
            } else if(isBooleanLiteral(buffer.toString())) {
                makeToken(TokenName.BOOLEAN_LITERAL, buffer.toString());
            } else if(isKeyword(buffer.toString())) {
                makeToken(TokenName.KEYWORD, buffer.toString());
            } else {
                makeToken(TokenName.IDENTIFIER, buffer.toString());
            }
            state = 0;
            letter--;
        }
    }

    /*
    * state 12
    * : in buffer
    * */
    private void colon(char c) {
        if(c == ':') {
            makeToken(TokenName.SEPARATOR, "::");
            state = 0;
        } else if(Util.isOperator(c)) {
            addToBuffer(c, -1);
        } else {
            makeToken(TokenName.OPERATOR, buffer.toString());
            state = 0;
            letter--;
        }
    }

    /*
    * state 10
    * & in buffer
    * */
    private void ampersand(char c) {
        if(c == '&') {
            addToBuffer(c, 27);
        } else if( c == '=') {
            addToBuffer(c, 18);
        } else if(c == ':') {
            addToBuffer(c, 21);
        } else if(c == '/') {
            addToBuffer(c, 19);
        } else if(Util.isOperator(c)) {
            addToBuffer(c, -1);
        } else {
            letter--;
            makeToken(TokenName.OPERATOR, buffer.toString());
            state = 0;
        }
    }

    /*
    * state 27
    * && or || in buffer
    * */
    private void ampersandOrPipe(char c) {
        if(c == ':') {
            addToBuffer(c, 21);
        } else if(c == '/') {
            addToBuffer(c, 19);
        } else if(Util.isOperator(c)) {
            addToBuffer(c, -1);
        } else {
            letter--;
            makeToken(TokenName.OPERATOR, buffer.toString());
            state = 0;
        }
    }

    /*
     * state 20
     * | in buffer
     * */
    private void pipe(char c) {
        if(c == '|') {
            addToBuffer(c, 28);
        } else if( c == '=') {
            addToBuffer(c, 18);
        } else if(c == ':') {
            addToBuffer(c, 21);
        } else if(c == '/') {
            addToBuffer(c, 19);
        } else if(Util.isOperator(c)) {
            addToBuffer(c, -1);
        } else {
            letter--;
            makeToken(TokenName.OPERATOR, buffer.toString());
            state = 0;
        }
    }

    /*
    * state 11
    * OPERATOR in buffer
    * -- c == : -> 21
    * -- c == = -> 18
    * -- c == / -> 19
    * -- c == OPERATOR -> -1
    * -- else -> 0
    * */
    private void singleOperator(char c) {
        if(c == '=') {
            addToBuffer(c, 18);
        } else if(c == ':') {
            addToBuffer(c, 21);
        } else if(c == '/') {
            addToBuffer(c, 19);
        } else if(Util.isOperator(c)) {
            addToBuffer(c, -1);
        } else {
            letter--;
            makeToken(TokenName.OPERATOR, buffer.toString());
            state = 0;
        }
    }

    /*
    * state 7
    * . in buffer
    * */
    private void dot(char c) {
        if(Character.isDigit(c)) {
            addToBuffer(c, 23);
        } else if(c == '.') {
            addToBuffer(c, 25);
        } else {
            letter--;
            makeToken(TokenName.SEPARATOR, buffer.toString());
            state = 0;
        }
    }

    /*
    * state 25
    * .. in buffer
    * */
    private void dotDot(char c) {
        if(c == '.') {
            addToBuffer(c, 28);
        } else {
            makeToken(TokenName.SEPARATOR, buffer.charAt(0));
            makeToken(TokenName.SEPARATOR, buffer.charAt(1));
            state = 0;
        }
    }

    /*
    * state 28
    * ... in buffer
    * */
    private void dotDotDot(char c) {
        if(c == '.') {
            addToBuffer(c, -1);
        } else {
            letter--;
            makeToken(TokenName.SEPARATOR, buffer.toString());
            state = 0;
        }
    }

    /*
    * state 13
    * + in buffer
    * */
    private void plus(char c) {
        if(c == '+') {
            addToBuffer(c, 11);
        } else if(c == ':') {
            addToBuffer(c, 21);
        } else if(c == '=') {
            addToBuffer(c, 18);
        } else if(c == '/') {
            addToBuffer(c, 19);
        } else if(Util.isOperator(c)) {
            addToBuffer(c, -1);
        } else {
            letter--;
            makeToken(TokenName.OPERATOR, buffer.toString());
            state = 0;
        }
    }

    /*
    * state 14
    * - in buffer
    * */
    private void minus(char c) {
        if(c == '-') {
            addToBuffer(c, 11);
        } else if(c == ':') {
            addToBuffer(c, 21);
        } else if(c == '=') {
            addToBuffer(c, 18);
        } else if(c == '/') {
            addToBuffer(c, 19);
        } else if(Util.isOperator(c)) {
            addToBuffer(c, -1);
        } else {
            letter--;
            makeToken(TokenName.OPERATOR, buffer.toString());
            state = 0;
        }
    }

    /*
    * state 29
    * INDENTIFIER/ in buffer
    * */
    private void maybeCommentAfterIdentifier(char c) {
        if(c == '/' || c == '*') {
            buffer.deleteCharAt(buffer.length() - 1);
            if(isNullLiteral(buffer.toString())) {
                makeToken(TokenName.NULL_LITERAL, buffer.toString());
            } else if(isBooleanLiteral(buffer.toString())) {
                makeToken(TokenName.BOOLEAN_LITERAL, buffer.toString());
            } else if(isKeyword(buffer.toString())) {
                makeToken(TokenName.KEYWORD, buffer.toString());
            } else {
                makeToken(TokenName.IDENTIFIER, buffer.toString());
            }
            buffer.append('/');
            if (c == '/') {
                addToBuffer(c, 15);
            } else {
                addToBuffer(c, 16);
            }
        }
        else {
            letter -= 2;
            buffer.deleteCharAt(buffer.length()-1);
            if(isNullLiteral(buffer.toString())) {
                makeToken(TokenName.NULL_LITERAL, buffer.toString());
            } else if(isBooleanLiteral(buffer.toString())) {
                makeToken(TokenName.BOOLEAN_LITERAL, buffer.toString());
            } else if(isKeyword(buffer.toString())) {
                makeToken(TokenName.KEYWORD, buffer.toString());
            } else {
                makeToken(TokenName.IDENTIFIER, buffer.toString());
            }
            state = 0;
        }
    }

    /*
    * state 6
    * " in buffer
    * */
    private void stringLiteral(char c) {
        if(c == '\\') {
            addToBuffer(c, 30);
        } else if(c == '\"') {
            buffer.append(c);
            makeToken(TokenName.STRING_LITERAL, buffer.toString());
            state = 0;
        } else if(Character.isWhitespace(c) && c != ' ' && c != '\t') {
            makeToken(TokenName.ERROR, buffer.toString());
            makeToken(TokenName.WHITESPACE, c);
            state = 0;
        } else {
            addToBuffer(c, 6);
        }
    }

    /*
    * state 30
    * "STRING\ in buffer
    * */
    private void maybeEscapeSequence(char c) {
        if(Util.isEscapeSequence("\\"+c)) {
            addToBuffer(c, 6);
        } else {
            addToBuffer(c, -1);
        }
    }

    /*
    * state 5
    * ' in buffer
    * */
    private void charLiteral(char c) {
        if(c == '\\') {
            addToBuffer(c, 31);
        } else if(Character.isWhitespace(c) && c != ' ' && c != '\t') {
            makeToken(TokenName.ERROR, buffer.toString());
            makeToken(TokenName.WHITESPACE, c);
            state = 0;
        } else {
            addToBuffer(c, 32);
        }
    }

    /*
    * state 31
    * '\ in buffer
    * */
    private void maybeEscapeSequenceChar(char c) {
        if(Character.isDigit(c)) {
            addToBuffer(c, 33);
        } else if(Util.isEscapeSequence("\\" + c)) {
            addToBuffer(c, 32);
        } else {
            addToBuffer(c, -1);
        }
    }

    /*
    * state 32
    * 'CHAR in buffer
    * */
    private void expectEndOfChar(char c) {
        if(c == '\'') {
            buffer.append(c);
            makeToken(TokenName.CHAR_LITERAL, buffer.toString());
            state = 0;
        } else {
            addToBuffer(c, -1);
        }
    }

    /*
    * state 33
    * '\DIGITS in buffer
    * */
    private void digitInChar(char c) {
        if(Character.isDigit(c)) {
            addToBuffer(c, 33);
        } else if(c == '\'') {
            addToBuffer(c, 0);
            makeToken(TokenName.CHAR_LITERAL, buffer.toString());
        } else {
            addToBuffer(c, -1);
        }
    }

    /*
    * state 4
    * NONZERO DIGIT in buffer*/
    private void nonzeroDigit(char c) {
        if(c == '_') {
            addToBuffer(c, 34);
        } else if(Character.isDigit(c)) {
            addToBuffer(c, 4);
        } else if(Character.isJavaIdentifierPart(c)) {
            addToBuffer(c, -1);
        } else if(c == '.') {
            addToBuffer(c, 23);
        } else {
            makeToken(TokenName.INT_LITERAL, buffer.toString());
            letter--;
            state = 0;
        }
    }

    /*
    * state 34
    * DIGITS_in buffer
    * */
    private void underlineInDigit(char c) {
        if(Character.isDigit(c)) {
            addToBuffer(c, 4);
        } else {
            addToBuffer(c, -1);
        }
    }

    private void error(char c) {

    }

    private boolean isKeyword(String value) {
        switch (value.length()) {
            case 2: {
                return "do".equals(value) || "if".equals(value);
            }
            case 3: {
                return "for".equals(value) || "int".equals(value) ||
                        "new".equals(value) || "try".equals(value);
            }
            case 4: {
                return "byte".equals(value) || "case".equals(value) ||
                        "char".equals(value) || "else".equals(value) ||
                        "enum".equals(value) || "goto".equals(value) ||
                        "long".equals(value) || "this".equals(value) ||
                        "void".equals(value);
            }
            case 5: {
                return "break".equals(value) || "catch".equals(value) ||
                        "class".equals(value) || "const".equals(value) ||
                        "final".equals(value) || "float".equals(value) ||
                        "short".equals(value) || "super".equals(value) ||
                        "throw".equals(value) || "while".equals(value);
            }
            case 6: {
                return "assert".equals(value) || "double".equals(value) ||
                        "import".equals(value) || "native".equals(value) ||
                        "public".equals(value) || "return".equals(value) ||
                        "static".equals(value) || "switch".equals(value) ||
                        "throws".equals(value);
            }
            case 7: {
                return "boolean".equals(value) || "default".equals(value) ||
                        "extends".equals(value) || "finally".equals(value) ||
                        "package".equals(value) || "private".equals(value);
            }
            case 8: {
                return "abstract".equals(value) || "continue".equals(value) ||
                        "strictfp".equals(value) || "volatile".equals(value);
            }
            case 9: {
                return "interface".equals(value) || "protected".equals(value) ||
                        "transient".equals(value);
            }
            case 10: {
                return "implements".equals(value) || "instanceof".equals(value);
            }
            case 12: {
                return "synchronized".equals(value);
            }
            default: {
                return false;
            }
        }
    }

    private boolean isBooleanLiteral(String value) {
        if(value.length() < 4 || value.length() > 5) return false;
        return "true".equals(value) || "false".equals(value);
    }

    private boolean isNullLiteral(String value) {
        return value.length() == 4 && "null".equals(value);
    }

    private void makeToken(TokenName tokenName, String value) {
        logger.info(tokenName.toString() + " " + value);
        tokens.add(new Token(tokenName, value));
        buffer = new StringBuilder();
        //state = 0;
    }

    private void makeToken(TokenName tokenName, char value) {
        logger.info(tokenName.toString() + " " + value);
        tokens.add(new Token(tokenName, String.valueOf(value)));
        buffer = new StringBuilder();
    }

    private void addToBuffer(char c, int state) {
        buffer.append(c);
        this.state = state;
    }
}
