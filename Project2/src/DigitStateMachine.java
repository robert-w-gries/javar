/**
 * Created by jchitel on 1/25/15.
 */
public class DigitStateMachine {
    private static State state;
    private static String token;

    private enum State {
        START,
        ACCEPT_INT,
        ACCEPT_OCTAL,
        ACCEPT_HEX,
        ERROR,
        IS_OCT_OR_HEX,
        INT,
        IS_HEX,
        HEX,
        OCTAL
    }

    private enum Class {
        INVALID,
        ZERO,
        OCTAL,
        HEX,
        DIGIT,
        X
    }

    private static final Class[] char_classes = new Class[128];

    static {
        char_classes['0'] = Class.ZERO;
        for (char i = '0'; i <= '7'; ++i) char_classes[i] = Class.OCTAL;
        for (char i = '8'; i <= '9'; ++i) char_classes[i] = Class.DIGIT;
        for (char i = 'A'; i <= 'F'; ++i) {
            char_classes[i] = Class.HEX;
            char_classes[i + ('A' - 'a')] = Class.HEX;
        }
        char_classes['x'] = Class.X;
        char_classes['X'] = Class.X;
    }

    // [CURRENT STATE][CHARACTER CLASS] -> NEXT STATE
    private static final State[][] stateMachine = new State[40][6];

    static {
        //START STATE
        stateMachine[State.START.ordinal()][Class.INVALID.ordinal()] = State.ERROR;
        stateMachine[State.START.ordinal()][Class.ZERO.ordinal()] = State.IS_OCT_OR_HEX;
        stateMachine[State.START.ordinal()][Class.OCTAL.ordinal()] = State.INT;
        stateMachine[State.START.ordinal()][Class.DIGIT.ordinal()] = State.INT;
        stateMachine[State.START.ordinal()][Class.HEX.ordinal()] = State.ERROR;
        stateMachine[State.START.ordinal()][Class.X.ordinal()] = State.ERROR;
        //IS_OCT_OR_HEX STATE
        stateMachine[State.IS_OCT_OR_HEX.ordinal()][Class.ZERO.ordinal()] = State.OCTAL;
        stateMachine[State.IS_OCT_OR_HEX.ordinal()][Class.X.ordinal()] = State.HEX;
        stateMachine[State.IS_OCT_OR_HEX.ordinal()][Class.OCTAL.ordinal()] = State.OCTAL;
        stateMachine[State.IS_OCT_OR_HEX.ordinal()][Class.DIGIT.ordinal()] = State.ERROR;
        stateMachine[State.IS_OCT_OR_HEX.ordinal()][Class.HEX.ordinal()] = State.ERROR;
    }

    public static void handle(Scanner scanner) {
        state = State.START;
        token = "" + scanner.nextChar;
        while (!isDone(state)) {
            state = nextState(state, scanner.nextChar);
            switch (state) {
                case ACCEPT_INT:
                    System.out.println("INTEGER_LITERAL(" + token + ")");
                    break;
                case ACCEPT_OCTAL:
                    System.out.println("OCTAL_LITERAL(" + token + ")");
                    break;
                case ACCEPT_HEX:
                    System.out.println("HEXADECIMAL_LITERAL(" + token + ")");
                    break;
            }
            scanner.nextChar = (char)scanner.fileScanner.nextByte();
            if (Scanner.char_classes[scanner.nextChar] != Scanner.DIGITS) {
                if (scanner.nextChar == 'x') continue;
                else {
                    state = State.ERROR;
                }
            }
        }
    }

    private static State nextState(State currState, char nextChar) {
        return stateMachine[currState.ordinal()][char_classes[nextChar].ordinal()];
    }

    private static boolean isDone(State state) {
        return state == State.ACCEPT_INT || state == State.ACCEPT_HEX ||
                state == State.ACCEPT_OCTAL || state == State.ERROR;
    }
}
