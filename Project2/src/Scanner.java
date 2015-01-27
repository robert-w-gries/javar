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
        INVALID, LETTERS, DIGITS, SPECIAL, SPACE
    }

    public static final char[] DIGITS = new char[]{
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };

    public static final char[] LETTERS = new char[]{
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '_'
    };

    public static final char[] WHITE_SPACES = new char[]{
            ' ', '\t', '\n', '\r'
    };

    public static final char[] SPECIAL_CHARS = new char[]{
            '&', '|', '^', '~', '+', '-', '*', '/', '<', '>', '=', '!', '(', ')', '{', '}', '[', ']', ',', '.', ';'
    };

    public static final ClassType[] char_classes = new ClassType[128];

    public char nextChar;
    public BufferedReader fileReader;

    public void scan(String filePath) throws IOException {
        fileReader = new BufferedReader(new FileReader(filePath));

        // read in a char (no actual nextChar() available
        int input = fileReader.read();
        nextChar = (char) input;

        // read until EOF
        while (nextChar <= 127) {

            ClassType char_class = char_classes[nextChar];
            switch (char_class) {

                case INVALID: {
                    invalidStateMachine();
                    continue;
                }

                case LETTERS: {
                    LetterStateMachine.handle(this);
                    continue;
                }

                case DIGITS: {
                    DigitStateMachine.handle(this);
                    continue;
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
        System.out.println("EOF");

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
        for (int i = 0; i < 128; ++i) char_classes[i] = ClassType.INVALID;
        // letters
        for (char c : LETTERS) {
            char_classes[c] = ClassType.LETTERS;
        }
        // digits
        for (char c : DIGITS) {
            char_classes[c] = ClassType.DIGITS;
        }
        // white space characters
        for (char c : WHITE_SPACES) {
            char_classes[c] = ClassType.SPACE;
        }
        // valid non-alphanumeric characters
        for (char c : SPECIAL_CHARS) {
            char_classes[c] = ClassType.SPECIAL;
        }

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