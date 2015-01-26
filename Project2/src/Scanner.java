/**

 Scanner.java

 Performs lexical analysis to convert MiniJava code into lexical tokens.

 @author Jake Chitel
 @author Rob Gries
 @author Luke Mivshak

 @version 1.0

 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Scanner {

    public enum ClassType {
        LETTERS, DIGITS, SPECIAL, SPACE, INVALID
    }

    public static ClassType[] char_classes = new ClassType[256];

    public char nextChar;
    public BufferedReader fileReader;

    public void scan(String filePath) throws IOException {

        FileReader inputFile;
        try {
            inputFile = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }

        fileReader = new BufferedReader(inputFile);

        // read in a char (no actual nextChar() available
        int input = fileReader.read();
        nextChar = (char) input;

        // read until EOF
        while (input != -1) {

            ClassType char_class = char_classes[nextChar];
            switch (char_class) {

                case INVALID: {
                    invalidStateMachine();
                    break;
                }

                case LETTERS: {
                    LetterStateMachine.handle(this);
                    break;
                }

                case DIGITS: {
                    DigitStateMachine.handle(this);
                    break;
                }

                case SPECIAL: {
                    SpecialStateMachine.handle(this);
                    break;
                }

                case SPACE: {
                    break;
                }

                default: {
                    throw new RuntimeException("Impossible error");
                }

            } // end switch

            input = fileReader.read();
            nextChar = (char) input;

        }

    }

    public void invalidStateMachine() throws IOException {

        int input = (int) nextChar;
        while (input != -1) {

            nextChar = (char) fileReader.read();
            if (char_classes[nextChar] == ClassType.SPACE ||
                    char_classes[nextChar] == ClassType.SPECIAL) {

                System.out.println("Illegal token.");
                return;

            }

        }

    }

    private static void init_char_class() {

        // initialize all characters to invalid
        for (int i = 0; i < char_classes.length; i++) {
            char_classes[i] = ClassType.INVALID;
        }

        // valid letters
        for (char i = 'a'; i <= 'z'; i++) {
            char_classes[i] = ClassType.LETTERS;
            char_classes[i + ('A' - 'a')] = ClassType.LETTERS;
        }
        char_classes['_'] = ClassType.LETTERS;

        // valid digits
        for (char i = '0'; i <= '9'; i++) {
            char_classes[i] = ClassType.DIGITS;
        }

        // white space character class
        char_classes[' '] = ClassType.SPACE;
        char_classes['\t'] = ClassType.SPACE;
        char_classes['\n'] = ClassType.SPACE;
        char_classes['\r'] = ClassType.SPACE;

        // Valid non-alphanumeric characters
        char_classes['&'] = ClassType.SPECIAL;
        char_classes['|'] = ClassType.SPECIAL;
        char_classes['^'] = ClassType.SPECIAL;
        char_classes['~'] = ClassType.SPECIAL;
        char_classes['+'] = ClassType.SPECIAL;
        char_classes['-'] = ClassType.SPECIAL;
        char_classes['*'] = ClassType.SPECIAL;
        char_classes['/'] = ClassType.SPECIAL;
        char_classes['<'] = ClassType.SPECIAL;
        char_classes['>'] = ClassType.SPECIAL;
        char_classes['='] = ClassType.SPECIAL;
        char_classes['!'] = ClassType.SPECIAL;
        char_classes['('] = ClassType.SPECIAL;
        char_classes[')'] = ClassType.SPECIAL;
        char_classes['{'] = ClassType.SPECIAL;
        char_classes['}'] = ClassType.SPECIAL;
        char_classes['['] = ClassType.SPECIAL;
        char_classes[']'] = ClassType.SPECIAL;
        char_classes[','] = ClassType.SPECIAL;
        char_classes['.'] = ClassType.SPECIAL;
        char_classes[';'] = ClassType.SPECIAL;

    }

    public static void main(String[] args) {

        init_char_class();

        // scan all input files
        for (String arg : args) {

            Scanner scanner = new Scanner();
            try {
                scanner.scan(arg);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }

        }

    }

}