
public class SpecialStateMachine {

    private static int currState = 0;

    private static boolean prevPrint = false;
    private static boolean newChar = false;
    private static boolean commentEnd = false;
    private static boolean multiComment = false;
    private static boolean stringLiteral = false;

    private enum Class{
        INVALID, // Invalid chars
        OTHER,   // Non special chars
        NORMAL,  // Single special chars without other char matchings
        OREQUAL, // = or ! could be != or ==
        AND,     // &
        OR,      // |
        QUOTE,   // "
        COMMENT  // /
    }

    private static final Class[] char_classes = new Class[128];

    private static final int START  = 0, DONE  = 1;


    static {
        for (int i = 0; i < 128; ++i) char_classes[i] = Class.INVALID;
        for (char c : Scanner.WHITE_SPACES) char_classes[c] = Class.OTHER;
        for (char c : Scanner.SPECIAL_CHARS) char_classes[c] = Class.NORMAL;
        for (char c : Scanner.DIGITS) char_classes[c] = Class.OTHER;
        for (char c : Scanner.LETTERS) char_classes[c] = Class.OTHER;
        char_classes['!'] = Class.OREQUAL;
        char_classes['='] = Class.OREQUAL;
        char_classes['&'] = Class.AND;
        char_classes['|'] = Class.OR;
        char_classes['"'] = Class.QUOTE;
        char_classes['/'] = Class.COMMENT;
    }

    // One char buffer to check things like = vs ==
    private static char curr, prev;

    private static String token = "";

    public static void handle(Scanner scanner) {
        token = "";
        currState = START;
        newChar = false;
        setCurr(scanner);
        newChar = true;

        while (currState != DONE) {
            switch (char_classes[curr]) {

                case INVALID: {
                    while(char_classes[curr] == Class.INVALID){
                        setCurr(scanner);
                    }
                    System.out.println("Invalid token");
                    currState = DONE;
                    break;
                }

                case OTHER: {
                    currState = DONE;
                    break;
                }

                case NORMAL: {
                    print(curr);
                    currState = DONE;
                    setCurr(scanner);
                    break;
                }

                case OREQUAL: {
                    prev = curr;
                    prevPrint = true;
                    setCurr(scanner);
                    prevPrint = false;
                    if(curr == '=') {
                        switch (prev) {
                            // !=
                            case '!': {
                                System.out.println("NOTEQUAL");
                                break;
                            }
                            // ==
                            case '=': {
                                System.out.println("EQUAL");
                                break;
                            }
                            // Other
                            default: {
                                //This should never happen
                                System.out.println("Illegal Token");
                                break;
                            }
                        }
                        setCurr(scanner);
                        currState = DONE;
                    }else{
                        print(prev);
                        newChar = false;
                        continue;
                    }
                    break;
                }


                case AND: {
                    prev = curr;
                    prevPrint = true;
                    setCurr(scanner);
                    prevPrint = false;
                    // &
                    if (curr == '&') {
                        System.out.println("AND");
                        currState = DONE;
                    } else {
                        print(prev);
                        newChar = false;
                        continue;
                    }
                    setCurr(scanner);
                    break;
                }

                case OR: {
                    prev = curr;
                    prevPrint = true;
                    setCurr(scanner);
                    prevPrint = false;
                    // |
                    if (curr == '|') {
                        System.out.println("OR");
                        currState = DONE;
                    } else {
                        print(prev);
                        newChar = false;
                        continue;
                    }
                    setCurr(scanner);
                    break;
                }

                case COMMENT: {
                    prev = curr;
                    prevPrint = true;
                    setCurr(scanner);
                    prevPrint = false;
                    // Single line comment terminated at newline
                    if (curr == '/') {
                        while(curr != '\n'){
                            setCurr(scanner);
                        }
                        currState = DONE;
                    // Multiline comment terminated at */
                    } else if(curr == '*'){
                        multiComment = true;
                        while(curr != '/' || !commentEnd){
                            setCurr(scanner);
                            if(curr == '*')
                                commentEnd = true;
                            else if(curr != '/')
                                commentEnd = false;
                        }
                        multiComment = false;
                        currState = DONE;
                        setCurr(scanner);
                    } else {
                        print(prev);
                        newChar = false;
                        continue;
                    }
                    break;
                }

                case QUOTE: {
                    stringLiteral = true;
                    setCurr(scanner);
                    while(curr != '"' && curr != '\n'){
                        token += curr;
                        setCurr(scanner);
                    }
                    if(curr == '"'){
                        stringLiteral = false;
                        System.out.println("STRING_LITERAL(" + token + ")");
                    }
                    currState = DONE;
                    setCurr(scanner);
                    break;
                }
            }
        }
    }

    private static void print(int index){
        switch (index){
            // !
            case '!':{
                System.out.println("BANG");
                break;
            }
            // &
            case '&':{
                System.out.println("BWAND");
                break;
            }
            // (
            case '(':{
                System.out.println("LPAREN");
                break;
            }
            // )
            case ')':{
                System.out.println("RPAREN");
                break;
            }
            // *
            case '*':{
                System.out.println("STAR");
                break;
            }
            // +
            case '+':{
                System.out.println("PLUS");
                break;
            }
            // ,
            case ',':{
                System.out.println("COMMA");
                break;
            }
            // -
            case '-':{
                System.out.println("MINUS");
                break;
            }
            // .
            case '.':{
                System.out.println("PERIOD");
                break;
            }
            // /
            case '/':{
                System.out.println("FORWARDSLASH");
                break;
            }
            // ;
            case ';':{
                System.out.println("SEMICOLON");
                break;
            }
            // <
            case '<':{
                System.out.println("LESSTHAN");
                break;
            }
            // =
            case '=':{
                System.out.println("ASSIGN");
                break;
            }
            // >
            case '>':{
                System.out.println("GREATERTHAN");
                break;
            }
            // [
            case '[': {
                System.out.println("LSQUARE");
                break;
            }
            // ]
            case ']':{
                System.out.println("RSQUARE");
                break;
            }
            // ^
            case '^':{
                System.out.println("XOR");
                break;
            }
            // {
            case '{':{
                System.out.println("LBRACE");
                break;
            }
            // |
            case '|':{
                System.out.println("BWOR");
                break;
            }
            // }
            case '}':{
                System.out.println("RBRACE");
                break;
            }
            // ~
            case '~':{
                System.out.println("COMP");
                break;
            }
            // Other
            default: {
                // This should never happen
                System.out.println("Illegal Token");
                break;
            }
        }
    }

    public static void setCurr(Scanner scanner){
        if(newChar) {
            try {
                scanner.nextChar = (char) scanner.fileReader.read();
            } catch (Exception e) {
                System.out.println("IO Error");
            }
            if ((int)scanner.nextChar  > 127 || (int)scanner.nextChar  < 0) {
                if (prevPrint) print(prev);
                if (multiComment) System.out.println("Comment not terminated at end of input.");
                if (stringLiteral) System.out.println("String not terminated at end of line.");
                System.out.println("EOF");
                System.exit(0);
            }
        }
        curr = scanner.nextChar;
        //System.out.println(curr);
    }

}

