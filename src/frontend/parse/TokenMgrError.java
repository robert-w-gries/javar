package frontend.parse;

/**
 * Token Manager Error.
 * NOTE: This class is LEGACY and a product of the original JavaCC generation of this parser code.
 * As of 2017-07-26, it has been pruned of unused code and has maximal encapsulation.
 * Feel free to remove or refactor it if it no longer satisfies requirements.
 */
public class TokenMgrError extends Error {
    /*
     * Ordinals for various reasons why an Error of this type can be thrown.
     */

    /**
     * Lexical error occurred.
     */
    public static final int LEXICAL_ERROR = 0;

    /**
     * An attempt was made to create a second instance of a static token manager.
     */
    public static final int STATIC_LEXER_ERROR = 1;

    /**
     * Tried to change to an invalid lexical state.
     */
    public static final int INVALID_LEXICAL_STATE = 2;

    /**
     * Indicates the reason why the exception is thrown. It will have
     * one of the above 4 values.
     */
    private final int errorCode;

    /** Constructor with message and reason. */
    public TokenMgrError(String message, int reason) {
        super(message);
        errorCode = reason;
    }

    /** Full Constructor. */
    public TokenMgrError(boolean EOFSeen, int lexState, int errorLine, int errorColumn, String errorAfter, char curChar, int reason) {
        this(LexicalError(EOFSeen, lexState, errorLine, errorColumn, errorAfter, curChar), reason);
    }

    /**
     * You can also modify the body of this method to customize your error messages.
     * For example, cases like LOOP_DETECTED and INVALID_LEXICAL_STATE are not
     * of end-users concern, so you can return something like :
     *
     *     "Internal Error : Please file a bug report .... "
     *
     * from this method for such cases in the release version of your parser.
     */
    public String getMessage() {
        return super.getMessage();
    }

    /**
     * Returns a detailed message for the Error when it is thrown by the
     * token manager to indicate a lexical error.
     * Parameters :
     *    EOFSeen     : indicates if EOF caused the lexical error
     *    curLexState : lexical state in which this error occurred
     *    errorLine   : line number when the error occurred
     *    errorColumn : column number when the error occurred
     *    errorAfter  : prefix that was seen before this error occurred
     *    curchar     : the offending character
     * Note: You can customize the lexical error message by modifying this method.
     */
    private static String LexicalError(boolean EOFSeen, int lexState, int errorLine, int errorColumn, String errorAfter, char curChar) {
        return ("Lexical error at line " +
                 errorLine + ", column " +
                 errorColumn + ".  Encountered: " +
                 (EOFSeen ? "<EOF> " : ("\"" + addEscapes(String.valueOf(curChar)) + "\"") + " (" + (int)curChar + "), ") +
                 "after : \"" + addEscapes(errorAfter) + "\"");
    }

    /**
     * Replaces unprintable characters by their escaped (or unicode escaped)
     * equivalents in the given string
     */
    private static final String addEscapes(String str) {
        final StringBuffer retval = new StringBuffer();
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
