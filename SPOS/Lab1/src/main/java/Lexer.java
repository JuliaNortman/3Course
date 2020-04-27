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
        for(int i = 0; i < code.length(); ++i) {
            char c = code.charAt(i);
            switch (state) {
                case 0: initialState(c); break;
                case 1: slash(c); break;
                case 15: singleLineComment(c); break;
                case 16: multiLineComment(c); break;
                case 17: starInMultiLineComment(c); break;
                default: {
                    initialState(c);
                    break;
                }
            }
        }
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
            state = 5;
        } else if(c == '\"') {
            state = 6;
        } else if(c == '.') {
            state = 7;
        } else if(Util.isSeparator(c)) {
            makeToken(TokenName.SEPARATOR, c);
            state = 0;
        } else if(c == '>') {
            state = 8;
        } else if(c == '<') {
            state = 9;
        } else if(c == '&') {
            addToBuffer(c, 10);
            state = 10;
        } else if(c == '^' || c == '!' || c == '*' || c == '=' || c == '%') {
            addToBuffer(c, 11);
            state = 11;
        } else if(c == ':') {
            state = 12;
        } else if(c == '+') {
            state = 13;
        } else if(c == '-') {
            state = 14;
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
            makeToken(TokenName.ERROR, buffer.toString());
        } else {
            makeToken(TokenName.OPERATOR, buffer.toString());
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

    private void error(char c) {

    }

    private void makeToken(TokenName tokenName, String value) {
        tokens.add(new Token(tokenName, value));
        buffer = new StringBuilder();
    }

    private void makeToken(TokenName tokenName, char value) {
        tokens.add(new Token(tokenName, String.valueOf(value)));
    }

    private void addToBuffer(char c, int state) {
        buffer.append(c);
        this.state = state;
    }
}
