package frontend.parse;

/**
 * This exception is thrown when parse errors are encountered.
 * You can explicitly create objects of this exception type by
 * calling the method generateParseException in the generated
 * parser.
 *
 * You can modify this class to customize your error reporting
 * mechanisms so long as you retain the public fields.
 *
 * NOTE: This class is LEGACY and a product of the original JavaCC generation of this parser code.
 * As of 2017-07-26, it has been pruned of unused code and has maximal encapsulation.
 * Feel free to remove or refactor it if it no longer satisfies requirements.
 */
public class ParseException extends Exception {
    /**
     * This variable determines which constructor was used to create
     * this object and thereby affects the semantics of the
     * "getMessage" method (see below).
     */
    private final boolean specialConstructor;

    /**
     * This is the last token that has been consumed successfully.  If
     * this object has been created due to a parse error, the token
     * followng this token will (therefore) be the first error token.
     */
    private final Token currentToken;

    /**
     * Each entry in this array is an array of integers.  Each array
     * of integers represents a sequence of tokens (by their ordinal
     * values) that is expected at this point of the parse.
     */
    private final int[][] expectedTokenSequences;

    /** Literal token values. */
    private static final String[] tokenImage = {
        "<EOF>",
        "\" \"",
        "\"\\t\"",
        "\"\\n\"",
        "\"\\r\"",
        "\"\\f\"",
        "\"(\"",
        "\")\"",
        "\"[\"",
        "\"]\"",
        "\"{\"",
        "\"}\"",
        "\".\"",
        "\"-\"",
        "\"!\"",
        "\"*\"",
        "\"/\"",
        "\"+\"",
        "\"<\"",
        "\">\"",
        "\"=\"",
        "\"==\"",
        "\"!=\"",
        "\"&&\"",
        "\"||\"",
        "\",\"",
        "\";\"",
        "\"public\"",
        "\"static\"",
        "\"void\"",
        "\"main\"",
        "\"String\"",
        "\"class\"",
        "\"extends\"",
        "\"Thread\"",
        "\"synchronized\"",
        "\"return\"",
        "\"if\"",
        "\"else\"",
        "\"while\"",
        "\"Xinu\"",
        "\"true\"",
        "\"false\"",
        "\"this\"",
        "\"null\"",
        "\"new\"",
        "\"int\"",
        "\"boolean\"",
        "\"//\"",
        "<token of kind 49>",
        "\"/*\"",
        "<SINGLE_LINE_COMMENT>",
        "\"*/\"",
        "\"*/\"",
        "<token of kind 54>",
        "<INT>",
        "<OCT>",
        "<HEX>",
        "<STRING>",
        "<ID>",
        "<LETTER>",
        "<DIGIT>",
        "<token of kind 62>",
    };

    /**
     * The end of line string for this machine.
     */
    private static final String eol = System.getProperty("line.separator", "\n");

    /**
     * This constructor is used by the method "generateParseException"
     * in the generated parser.  Calling this constructor generates
     * a new object of this type with the fields "currentToken",
     * "expectedTokenSequences", and "tokenImage" set.  The boolean
     * flag "specialConstructor" is also set to true to indicate that
     * this constructor was used to create this object.
     * This constructor calls its super class with the empty string
     * to force the "toString" method of parent class "Throwable" to
     * print the error message in the form:
     *     ParseException: <result of getMessage>
     */
    public ParseException(Token currentTokenVal, int[][] expectedTokenSequencesVal) {
        super("");
        specialConstructor = true;
        currentToken = currentTokenVal;
        expectedTokenSequences = expectedTokenSequencesVal;
    }

    /**
     * The following constructors are for use by you for whatever
     * purpose you can think of.  Constructing the exception in this
     * manner makes the exception behave in the normal way - i.e., as
     * documented in the class "Throwable".  The fields "errorToken",
     * "expectedTokenSequences", and "tokenImage" do not contain
     * relevant information.  The JavaCC generated code does not use
     * these constructors.
     */
    public ParseException() {
        super();
        specialConstructor = false;
        currentToken = null;
        expectedTokenSequences = null;
    }

    /**
     * This method has the standard behavior when this object has been
     * created using the standard constructors.  Otherwise, it uses
     * "currentToken" and "expectedTokenSequences" to generate a parse
     * error message and returns it.  If this object has been created
     * due to a parse error, and you do not catch it (it gets thrown
     * from the parser), then this method is called during the printing
     * of the final stack trace, and hence the correct error message
     * gets displayed.
     */
    public String getMessage() {
        if (!specialConstructor) {
            return super.getMessage();
        }
        StringBuffer expected = new StringBuffer();
        int maxSize = 0;
        for (int i = 0; i < expectedTokenSequences.length; i++) {
            if (maxSize < expectedTokenSequences[i].length) {
                maxSize = expectedTokenSequences[i].length;
            }
            for (int j = 0; j < expectedTokenSequences[i].length; j++) {
                expected.append(tokenImage[expectedTokenSequences[i][j]]).append(' ');
            }
            if (expectedTokenSequences[i][expectedTokenSequences[i].length - 1] != 0) {
                expected.append("...");
            }
            expected.append(eol).append("    ");
        }
        String retval = "Encountered \"";
        Token tok = currentToken.next;
        for (int i = 0; i < maxSize; i++) {
            if (i != 0) retval += " ";
            if (tok.kind == 0) {
                retval += tokenImage[0];
                break;
            }
            retval += " " + tokenImage[tok.kind];
            retval += " \"";
            retval += add_escapes(tok.image);
            retval += " \"";
            tok = tok.next;
        }
        retval += "\" at line " + currentToken.next.beginLine + ", column " + currentToken.next.beginColumn;
        retval += "." + eol;
        if (expectedTokenSequences.length == 1) {
            retval += "Was expecting:" + eol + "    ";
        } else {
            retval += "Was expecting one of:" + eol + "    ";
        }
        retval += expected.toString();
        return retval;
    }

    /**
     * Used to convert raw characters to their escaped version
     * when these raw version cannot be used as part of an ASCII
     * string literal.
     */
    private String add_escapes(String str) {
        StringBuffer retval = new StringBuffer();
        char ch;
        for (int i = 0; i < str.length(); i++) {
            switch (str.charAt(i)) {
                case 0:
                    continue;
                case '\b':
                    retval.append("\\b");
                    continue;
                case '\t':
                    retval.append("\\t");
                    continue;
                case '\n':
                    retval.append("\\n");
                    continue;
                case '\f':
                    retval.append("\\f");
                    continue;
                case '\r':
                    retval.append("\\r");
                    continue;
                case '\"':
                    retval.append("\\\"");
                    continue;
                case '\'':
                    retval.append("\\\'");
                    continue;
                case '\\':
                    retval.append("\\\\");
                    continue;
                default:
                    if ((ch = str.charAt(i)) < 0x20 || ch > 0x7e) {
                         String s = "0000" + Integer.toString(ch, 16);
                         retval.append("\\u" + s.substring(s.length() - 4, s.length()));
                    } else {
                         retval.append(ch);
                    }
                    continue;
            }
        }
        return retval.toString();
    }
}
