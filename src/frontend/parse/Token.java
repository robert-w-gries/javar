package frontend.parse;

/**
 * Describes the input token stream.
 * NOTE: This class is LEGACY and a product of the original JavaCC generation of this parser code.
 * As of 2017-07-26, it has been pruned of unused code and has maximal encapsulation.
 * Feel free to remove or refactor it if it no longer satisfies requirements.
 */
public class Token {
    /**
     * A reference to the next regular (non-special) token from the input
     * stream.  If this is the last token from the input stream, or if the
     * token manager has not read tokens beyond this one, this field is
     * set to null.  This is true only if this token is also a regular
     * token.  Otherwise, see below for a description of the contents of
     * this field.
     */
    public Token next;

    /**
     * An integer that describes the kind of this token.  This numbering
     * system is determined by JavaCCParser, and a table of these numbers is
     * stored in the file ...Constants.java.
     */
    public int kind;

    /**
     * The string image of the token.
     */
    public String image;

    /**
     * This field is used to access special tokens that occur prior to this
     * token, but after the immediately preceding regular (non-special) token.
     * If there are no such special tokens, this field is set to null.
     * When there are more than one such special token, this field refers
     * to the last of these special tokens, which in turn refers to the next
     * previous special token through its specialToken field, and so on
     * until the first special token (whose specialToken field is null).
     * The next fields of special tokens refer to other special tokens that
     * immediately follow it (without an intervening regular token).  If there
     * is no such token, this field is null.
     */
    public Token specialToken;

    /** The line number of the first character of this Token. */
    public int beginLine;
    /** The column number of the first character of this Token. */
    public int beginColumn;
    /** The line number of the last character of this Token. */
    public int endLine;
    /** The column number of the last character of this Token. */
    public int endColumn;

    /**
     * No-argument constructor
     */
    public Token() {}

    /**
     * Constructs a new token for the specified Image and Kind.
     */
    public Token(int kind, String image) {
         this.kind = kind;
         this.image = image;
    }

    /**
     * Returns the image.
     */
    @Override
    public String toString() {
         return image;
    }
}
