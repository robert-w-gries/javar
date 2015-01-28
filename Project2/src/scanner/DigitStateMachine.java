package scanner;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 1/25/15
 * Time: 6:47 PM
 *
 */
public class DigitStateMachine {
    private static State state;
    private static String token;

    private enum State {
        START, // starting state
        IS_OCT_OR_HEX, // found a 0 at start, determining if hex or octal
        INT, // building an int
        OCTAL, // building an octal
        HEX, // building a hex
        ERROR, // impossible sequence, ex. START + non-digit, should never happen
        ACCEPT_INT, // completed a valid int
        ACCEPT_OCTAL, // completed a valid octal number
        ACCEPT_HEX, // completed a valid hex number
        ILLEGAL, // illegal character found, results in "illegal token."
        INVALID_NUM, // invalid character found in middle of octal/int, results in "Invalid character in number."
        INVALID_HEX, // invalid character found in middle of hex, results in "Invalid character in hex number."
        SCAN, // error message to be printed, scan to end of token
        DONE // at end of token, print error message
    }

    private enum Class {
        INVALID, // all illegal characters
        LETTERS, // all letters except a-f, x, A-f, X
        ZERO, // 0
        OCTAL, // 1-7
        DIGIT, // 8-9
        HEX, // a-f, A-F
        X, // x, X
        ACCEPT // all white space and special chars
    }

    private static final Class[] char_classes = new Class[128];

    static {
        for (int i = 0; i < 128; ++i) char_classes[i] = Class.INVALID;
        for (char c : Scanner.WHITE_SPACES) char_classes[c] = Class.ACCEPT;
        for (char c : Scanner.SPECIAL_CHARS) char_classes[c] = Class.ACCEPT;
        for (char c : Scanner.LETTERS) char_classes[c] = Class.LETTERS;
        char_classes['0'] = Class.ZERO;
        for (char i = '1'; i <= '7'; ++i) char_classes[i] = Class.OCTAL;
        for (char i = '8'; i <= '9'; ++i) char_classes[i] = Class.DIGIT;
        for (char i = 'A'; i <= 'F'; ++i) {
            char_classes[i] = Class.HEX;
            char_classes[i + ('a' - 'A')] = Class.HEX;
        }
        char_classes['x'] = Class.X;
        char_classes['X'] = Class.X;
    }

    // [CURRENT STATE][CHARACTER CLASS] -> NEXT STATE
    private static final State[][] stateMachine = new State[14][8];

    static {
        //START STATE
        setNextState(State.START, Class.INVALID, State.ERROR); // should never happen
        setNextState(State.START, Class.LETTERS, State.ERROR); // should never happen
        setNextState(State.START, Class.ZERO, State.IS_OCT_OR_HEX); // 0### or 0x### => Octal or Hex
        setNextState(State.START, Class.OCTAL, State.INT); // ### => Int
        setNextState(State.START, Class.DIGIT, State.INT); // ### => Int
        setNextState(State.START, Class.HEX, State.ERROR); // should never happen
        setNextState(State.START, Class.X, State.ERROR); // should never happen
        setNextState(State.START, Class.ACCEPT, State.ERROR); // should never happen
        //IS_OCT_OR_HEX STATE
        setNextState(State.IS_OCT_OR_HEX, Class.INVALID, State.ILLEGAL); // "Illegal token."
        setNextState(State.IS_OCT_OR_HEX, Class.LETTERS, State.INVALID_NUM); // 0g.. => "Invalid character in number."
        setNextState(State.IS_OCT_OR_HEX, Class.ZERO, State.OCTAL); // 00### => Octal
        setNextState(State.IS_OCT_OR_HEX, Class.OCTAL, State.OCTAL); // 0### => Octal
        setNextState(State.IS_OCT_OR_HEX, Class.DIGIT, State.INVALID_NUM); // 08.. => "Invalid character in number."
        setNextState(State.IS_OCT_OR_HEX, Class.HEX, State.INVALID_NUM); // 0a.. => "Invalid character in number."
        setNextState(State.IS_OCT_OR_HEX, Class.X, State.HEX); // 0x### => Hex
        setNextState(State.IS_OCT_OR_HEX, Class.ACCEPT, State.ACCEPT_INT); // 0 => completed Int
        //INT STATE
        setNextState(State.INT, Class.INVALID, State.ILLEGAL); // "Illegal token."
        setNextState(State.INT, Class.LETTERS, State.INVALID_NUM); // #g.. => "Invalid character in number."
        setNextState(State.INT, Class.ZERO, State.INT); // #0## => Int
        setNextState(State.INT, Class.OCTAL, State.INT); // ### => Int
        setNextState(State.INT, Class.DIGIT, State.INT); // ### => Int
        setNextState(State.INT, Class.HEX, State.INVALID_NUM); // #a.. => "Invalid character in number."
        setNextState(State.INT, Class.X, State.INVALID_NUM); // #x.. => "Invalid character in number."
        setNextState(State.INT, Class.ACCEPT, State.ACCEPT_INT); // ### => completed Int
        //OCTAL STATE
        setNextState(State.OCTAL, Class.INVALID, State.ILLEGAL); // "Illegal token."
        setNextState(State.OCTAL, Class.LETTERS, State.INVALID_NUM); // 0#g.. => "Invalid character in number."
        setNextState(State.OCTAL, Class.ZERO, State.OCTAL); // 0#0# => Octal
        setNextState(State.OCTAL, Class.OCTAL, State.OCTAL); // 0### => Octal
        setNextState(State.OCTAL, Class.DIGIT, State.INVALID_NUM); // 0#8.. => "Invalid character in number."
        setNextState(State.OCTAL, Class.HEX, State.INVALID_NUM); // 0#a.. => "Invalid character in number."
        setNextState(State.OCTAL, Class.X, State.INVALID_NUM); // 0#x.. => "Invalid character in number."
        setNextState(State.OCTAL, Class.ACCEPT, State.ACCEPT_OCTAL); // 0###, => completed Octal
        //HEX STATE
        setNextState(State.HEX, Class.INVALID, State.ILLEGAL); // "Illegal token."
        setNextState(State.HEX, Class.LETTERS, State.INVALID_HEX); // 0x#g.. => "Invalid character in hex number."
        setNextState(State.HEX, Class.ZERO, State.HEX); // 0x#0# => Hex
        setNextState(State.HEX, Class.OCTAL, State.HEX); // 0x### => Hex
        setNextState(State.HEX, Class.DIGIT, State.HEX); // 0x#8# => Hex
        setNextState(State.HEX, Class.HEX, State.HEX); // 0x#a# => Hex
        setNextState(State.HEX, Class.X, State.INVALID_HEX); // 0x#x.. => "Invalid character in hex number."
        setNextState(State.HEX, Class.ACCEPT, State.ACCEPT_HEX); // 0x###, => completed Hex
        //ERROR STATE
        setNextState(State.ERROR, Class.INVALID, State.ERROR); // Exception will be thrown
        setNextState(State.ERROR, Class.LETTERS, State.ERROR); // Exception will be thrown
        setNextState(State.ERROR, Class.ZERO, State.ERROR); // Exception will be thrown
        setNextState(State.ERROR, Class.OCTAL, State.ERROR); // Exception will be thrown
        setNextState(State.ERROR, Class.DIGIT, State.ERROR); // Exception will be thrown
        setNextState(State.ERROR, Class.HEX, State.ERROR); // Exception will be thrown
        setNextState(State.ERROR, Class.X, State.ERROR); // Exception will be thrown
        setNextState(State.ERROR, Class.ACCEPT, State.ERROR); // Exception will be thrown
        //ACCEPT_INT STATE
        setNextState(State.ACCEPT_INT, Class.INVALID, State.ERROR); // should never be reached
        setNextState(State.ACCEPT_INT, Class.LETTERS, State.ERROR); // should never be reached
        setNextState(State.ACCEPT_INT, Class.ZERO, State.ERROR); // should never be reached
        setNextState(State.ACCEPT_INT, Class.OCTAL, State.ERROR); // should never be reached
        setNextState(State.ACCEPT_INT, Class.DIGIT, State.ERROR); // should never be reached
        setNextState(State.ACCEPT_INT, Class.HEX, State.ERROR); // should never be reached
        setNextState(State.ACCEPT_INT, Class.X, State.ERROR); // should never be reached
        setNextState(State.ACCEPT_INT, Class.ACCEPT, State.ERROR); // should never be reached
        //ACCEPT_OCTAL STATE
        setNextState(State.ACCEPT_OCTAL, Class.INVALID, State.ERROR); // should never be reached
        setNextState(State.ACCEPT_OCTAL, Class.LETTERS, State.ERROR); // should never be reached
        setNextState(State.ACCEPT_OCTAL, Class.ZERO, State.ERROR); // should never be reached
        setNextState(State.ACCEPT_OCTAL, Class.OCTAL, State.ERROR); // should never be reached
        setNextState(State.ACCEPT_OCTAL, Class.DIGIT, State.ERROR); // should never be reached
        setNextState(State.ACCEPT_OCTAL, Class.HEX, State.ERROR); // should never be reached
        setNextState(State.ACCEPT_OCTAL, Class.X, State.ERROR); // should never be reached
        setNextState(State.ACCEPT_OCTAL, Class.ACCEPT, State.ERROR); // should never be reached
        //ACCEPT_HEX STATE
        setNextState(State.ACCEPT_HEX, Class.INVALID, State.ERROR); // should never be reached
        setNextState(State.ACCEPT_HEX, Class.LETTERS, State.ERROR); // should never be reached
        setNextState(State.ACCEPT_HEX, Class.ZERO, State.ERROR); // should never be reached
        setNextState(State.ACCEPT_HEX, Class.OCTAL, State.ERROR); // should never be reached
        setNextState(State.ACCEPT_HEX, Class.DIGIT, State.ERROR); // should never be reached
        setNextState(State.ACCEPT_HEX, Class.HEX, State.ERROR); // should never be reached
        setNextState(State.ACCEPT_HEX, Class.X, State.ERROR); // should never be reached
        setNextState(State.ACCEPT_HEX, Class.ACCEPT, State.ERROR); // should never be reached
        //ILLEGAL STATE
        setNextState(State.ILLEGAL, Class.INVALID, State.SCAN); // scan until end of token
        setNextState(State.ILLEGAL, Class.LETTERS, State.SCAN); // scan until end of token
        setNextState(State.ILLEGAL, Class.ZERO, State.SCAN); // scan until end of token
        setNextState(State.ILLEGAL, Class.OCTAL, State.SCAN); // scan until end of token
        setNextState(State.ILLEGAL, Class.DIGIT, State.SCAN); // scan until end of token
        setNextState(State.ILLEGAL, Class.HEX, State.SCAN); // scan until end of token
        setNextState(State.ILLEGAL, Class.X, State.SCAN); // scan until end of token
        setNextState(State.ILLEGAL, Class.ACCEPT, State.DONE); // end of token, done
        //INVALID_NUM STATE
        setNextState(State.INVALID_NUM, Class.INVALID, State.SCAN); // scan until end of token
        setNextState(State.INVALID_NUM, Class.LETTERS, State.SCAN); // scan until end of token
        setNextState(State.INVALID_NUM, Class.ZERO, State.SCAN); // scan until end of token
        setNextState(State.INVALID_NUM, Class.OCTAL, State.SCAN); // scan until end of token
        setNextState(State.INVALID_NUM, Class.DIGIT, State.SCAN); // scan until end of token
        setNextState(State.INVALID_NUM, Class.HEX, State.SCAN); // scan until end of token
        setNextState(State.INVALID_NUM, Class.X, State.SCAN); // scan until end of token
        setNextState(State.INVALID_NUM, Class.ACCEPT, State.DONE); // end of token, done
        //INVALID_HEX STATE
        setNextState(State.INVALID_HEX, Class.INVALID, State.SCAN); // scan until end of token
        setNextState(State.INVALID_HEX, Class.LETTERS, State.SCAN); // scan until end of token
        setNextState(State.INVALID_HEX, Class.ZERO, State.SCAN); // scan until end of token
        setNextState(State.INVALID_HEX, Class.OCTAL, State.SCAN); // scan until end of token
        setNextState(State.INVALID_HEX, Class.DIGIT, State.SCAN); // scan until end of token
        setNextState(State.INVALID_HEX, Class.HEX, State.SCAN); // scan until end of token
        setNextState(State.INVALID_HEX, Class.X, State.SCAN); // scan until end of token
        setNextState(State.INVALID_HEX, Class.ACCEPT, State.DONE); // end of token, done
        //SCAN STATE
        setNextState(State.SCAN, Class.INVALID, State.SCAN); // scan until end of token
        setNextState(State.SCAN, Class.LETTERS, State.SCAN); // scan until end of token
        setNextState(State.SCAN, Class.ZERO, State.SCAN); // scan until end of token
        setNextState(State.SCAN, Class.OCTAL, State.SCAN); // scan until end of token
        setNextState(State.SCAN, Class.DIGIT, State.SCAN); // scan until end of token
        setNextState(State.SCAN, Class.HEX, State.SCAN); // scan until end of token
        setNextState(State.SCAN, Class.X, State.SCAN); // scan until end of token
        setNextState(State.SCAN, Class.ACCEPT, State.DONE); // end of token, done
        //DONE STATE
        setNextState(State.DONE, Class.INVALID, State.ERROR); // should never be reached
        setNextState(State.DONE, Class.LETTERS, State.ERROR); // should never be reached
        setNextState(State.DONE, Class.ZERO, State.ERROR); // should never be reached
        setNextState(State.DONE, Class.OCTAL, State.ERROR); // should never be reached
        setNextState(State.DONE, Class.DIGIT, State.ERROR); // should never be reached
        setNextState(State.DONE, Class.HEX, State.ERROR); // should never be reached
        setNextState(State.DONE, Class.X, State.ERROR); // should never be reached
        setNextState(State.DONE, Class.ACCEPT, State.ERROR); // should never be reached
    }

    public static void handle(Scanner scanner) throws IOException {
        state = State.START;
        token = "";
        while (scanner.nextChar <= 127) {
            state = nextState(state, scanner.nextChar);
            switch (state) {
                case INT:
                case OCTAL:
                case HEX:
                case IS_OCT_OR_HEX:
                    token += scanner.nextChar;
                    break;
                case ACCEPT_INT:
                    System.out.println("INTEGER_LITERAL(" + token + ")");
                    return;
                case ACCEPT_OCTAL:
                    System.out.println("OCTAL_LITERAL(" + token + ")");
                    return;
                case ACCEPT_HEX:
                    System.out.println("HEXADECIMAL_LITERAL(" + token + ")");
                    return;
                case ILLEGAL:
                    System.out.println("Illegal token.");
                    break;
                case INVALID_NUM:
                    System.out.println("Invalid character in number.");
                    break;
                case INVALID_HEX:
                    System.out.println("Invalid character in hex number.");
                    break;
                case DONE:
                    return;
                case ERROR:
                    throw new RuntimeException("Impossible state machine sequence");
            }

            scanner.nextChar = (char) scanner.fileReader.read();
        }
    }

    private static State nextState(State currState, char nextChar) {
        return stateMachine[currState.ordinal()][char_classes[nextChar].ordinal()];
    }

    private static void setNextState(State currState, Class nextClass, State nextState) {
        stateMachine[currState.ordinal()][nextClass.ordinal()] = nextState;
    }
}
