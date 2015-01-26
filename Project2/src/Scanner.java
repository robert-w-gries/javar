/**

 Scanner.java

 Performs lexical analysis to convert MiniJava code into lexical tokens.

 @author Jake Chitel
 @author Rob Gries
 @author Luke Mivshak

 @version 1.0

 */

public class Scanner {

    public static int[] char_classes = new int[256];

    public static final int LETTERS = 1,
            DIGITS = 2,
            SPECIAL = 3,
            SPACE = 4,
            INVALID = 0;

    public static final int INVALID_TOKEN = 0;

    public char nextChar;
    public java.util.Scanner fileScanner;

    public void scan(String file) {

        fileScanner = new java.util.Scanner(file);

        nextChar = (char) fileScanner.nextByte();

        while (fileScanner.hasNext()) {
            int char_class = char_classes[nextChar];
            switch (char_class) {
                case INVALID:
                    invalidStateMachine();
                    continue;
                case LETTERS:
                    LetterStateMachine.handle(this);
                    break;
                case DIGITS:
                    DigitStateMachine.handle(this);
                    break;
                case SPECIAL:
                    SpecialStateMachine.handle(this);
                    break;
                case SPACE:
                    break;
                default:
                    throw new RuntimeException("Impossible error");
            }

            nextChar = (char) fileScanner.nextByte();
        }

    }

    public void invalidStateMachine() {
        while (fileScanner.hasNext()) {
            nextChar = (char) fileScanner.nextByte();
            if (char_classes[nextChar] == SPACE || char_classes[nextChar] == SPECIAL) {
                System.out.println("Illegal token.");
                return;
            }
        }
    }

    private static void init_char_class() {

        // valid letters
        for (char i = 'a'; i <= 'z'; i++) {
            char_classes[i] = LETTERS;
            char_classes[i + ('A' - 'a')] = LETTERS;
        }
        char_classes['_'] = LETTERS;

        // valid digits
        for (char i = '0'; i <= '9'; i++) {
            char_classes[i] = DIGITS;
        }

        // white space character class
        char_classes[' '] = SPACE;
        char_classes['\t'] = SPACE;
        char_classes['\n'] = SPACE;
        char_classes['\r'] = SPACE;

        // Valid non-alphanumeric characters
        char_classes['&'] = SPECIAL;
        char_classes['|'] = SPECIAL;
        char_classes['^'] = SPECIAL;
        char_classes['~'] = SPECIAL;
        char_classes['+'] = SPECIAL;
        char_classes['-'] = SPECIAL;
        char_classes['*'] = SPECIAL;
        char_classes['/'] = SPECIAL;
        char_classes['<'] = SPECIAL;
        char_classes['>'] = SPECIAL;
        char_classes['='] = SPECIAL;
        char_classes['!'] = SPECIAL;
        char_classes['('] = SPECIAL;
        char_classes[')'] = SPECIAL;
        char_classes['{'] = SPECIAL;
        char_classes['}'] = SPECIAL;
        char_classes['['] = SPECIAL;
        char_classes[']'] = SPECIAL;
        char_classes[','] = SPECIAL;
        char_classes['.'] = SPECIAL;
        char_classes[';'] = SPECIAL;

    }

    public static void main(String[] args) {

        init_char_class();

        // scan all input files
        for (String arg : args) {
            Scanner scanner = new Scanner();
            scanner.scan(arg);
        }

    }

}