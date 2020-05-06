import org.apache.commons.lang3.tuple.ImmutablePair;

public class Token {
    private ImmutablePair<TokenName, String> token;

    public Token(TokenName tokenName, String value) {
        token = new ImmutablePair<>(tokenName, value);
    }

    @Override
    public String toString() {
        return "<" + token.left.name() + "  |  " + token.right +
                '>';
    }

    public TokenName getTokenName() {
        return token.left;
    }

    public String getValue() {
        return token.right;
    }
}
