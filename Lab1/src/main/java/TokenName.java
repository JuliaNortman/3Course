public enum TokenName {
    COMMENT(10987175), //#A7A6A7
    WHITESPACE(16777215), //FFFFFF
    IDENTIFIER(9373321), //#8F0689
    OPERATOR(0), //000000
    SEPARATOR(4306134), //( ) { } [ ] ; , . ... @ ::  ##41B4D6
    INT_LITERAL(16745216), //#FF8300
    //FLOAT_LITERAL,
    CHAR_LITERAL(16711885), //#FF00CD
    STRING_LITERAL(2729729), //#29A701
    ERROR(16711680); //#FF0000

    private final long color;

    TokenName(final long color) {
        this.color = color;
    }

    public long getColor() {
        return color;
    }
}
