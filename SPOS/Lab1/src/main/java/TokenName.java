public enum TokenName {
    COMMENT(10987175), //#A7A6A7
    WHITESPACE(16777215), //FFFFFF
    IDENTIFIER(9373321), //#8F0689
    OPERATOR(0), //000000
    SEPARATOR(4306134), //( ) { } [ ] ; , . ... @ ::  ##41B4D6
    INT_LITERAL(16752128), //#FF9E00
    //FLOAT_LITERAL,
    CHAR_LITERAL(1310551), //#13FF57
    STRING_LITERAL(1141804), //#116C2C
    BOOLEAN_LITERAL(14114533), //#D75EE5
    NULL_LITERAL(14318459), //#DA7B7B
    KEYWORD(2108613), //#202CC5
    ERROR(16711680); //#FF0000

    private final long color;

    TokenName(final long color) {
        this.color = color;
    }

    public long getColor() {
        return color;
    }
}
