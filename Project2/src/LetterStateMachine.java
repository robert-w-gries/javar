/**
 * Created by rgries on 1/25/15.
 */
public class LetterStateMachine {

    private static int state;
    private static String token;

    private static final int START = 0,
            ACCEPT_ID = 1,
            RESERVED_CLASS = 2,
            RESERVED_PUBLIC = 3,
            RESERVED_STATIC = 4,
            RESERVED_VOID = 5,
            RESERVED_MAIN = 6,
            RESERVED_STRING = 7,
            RESERVED_EXTENDS = 8,
            RESERVED_RETURN = 9,
            RESERVED_INT = 10,
            RESERVED_BOOLEAN = 11,
            RESERVED_IF = 12,
            RESERVED_ELSE = 13,
            RESERVED_WHILE = 14,
            RESERVED_LENGTH = 15,
            RESERVED_TRUE = 16,
            RESERVED_FALSE = 17,
            RESERVED_THIS = 18,
            RESERVED_NEW = 19,
            RESERVED_SYNCHRONIZED = 20,
            RESERVED_XINU_PRINT = 21,
            RESERVED_XINU_PRINTLN = 22,
            RESERVED_XINU_PRINTINT = 23,
            RESERVED_XINU_READINT = 24,
            RESERVED_XINU_THREADCREATE = 25,
            RESERVED_XINU_YIELD = 26,
            RESERVED_XINU_SLEEP = 27,
            ACCEPT_RESERVED = 28,
            VALID_ID = 29,
            ERROR = 30;

    public static void handle(Scanner scanner) {

        state = START;
        token = "" + scanner.nextChar;

        while (!isDone(state)) {

            switch (state) {

                case START: {
                    break;
                }

                case ACCEPT_RESERVED: {
                    System.out.println(token.toUpperCase());
                    break;
                }

                case ERROR: {
                    System.out.println("Illegal token.");
                    break;
                }

                default: {
                    state = ERROR;
                    break;
                }

            }
        }

    }

    public static boolean isDone(int state) {
        return state == ACCEPT_ID || state == ACCEPT_RESERVED || state == VALID_ID || state == ERROR;
    }
}
