/**
 * Created by rgries on 1/25/15.
 */
public class DigitStateMachine {
    private static int state;
    private static String token;

    private static final int START = 0,
            ACCEPT_INT = 1,
            ACCEPT_OCTAL = 2,
            ACCEPT_HEX = 3,
            ERROR = 4,
            IS_OCT_OR_HEX = 5,
            INT = 6,
            IS_HEX = 7,
            HEX = 8,
            OCTAL = 9;

    private static final int CLASS_INVALID = 0,
            CLASS_0 = 1,
            CLASS_OCTAL = 2,
            CLASS_HEX = 3,
            CLASS_DIGITS = 4,
            CLASS_X = 5;

    private static final int[] char_classes = new int[128];

    static {
        char_classes['0'] = CLASS_0;
        for (int i = 1; i <= 7; ++i) char_classes[i + '0'] = CLASS_OCTAL;
        for (int i = 8; i <= 9; ++i) char_classes[i + '0'] = CLASS_DIGITS;
        for (char i = 'A'; i <= 'F'; ++i) {
            char_classes[i] = CLASS_HEX;
            char_classes[i + ('A' - 'a')] = CLASS_HEX;
        }
        char_classes['x'] = CLASS_X;
        char_classes['X'] = CLASS_X;
    }

    // [CURRENT STATE][CHARACTER CLASS] -> NEXT STATE
    private static final int[][] stateMachine = new int[40][6];

    static {
        //START STATE
        stateMachine[START][CLASS_INVALID] = ERROR;
        stateMachine[START][CLASS_0] = IS_OCT_OR_HEX;
        stateMachine[START][CLASS_OCTAL] = INT;
        stateMachine[START][CLASS_DIGITS] = INT;
        stateMachine[START][CLASS_X] = ERROR;

        //IS_OCT_OR_HEX STATE
        stateMachine[IS_OCT_OR_HEX][CLASS_0] = OCTAL;
        stateMachine[IS_OCT_OR_HEX][CLASS_X] = HEX;
        stateMachine[IS_OCT_OR_HEX][CLASS_OCTAL] = OCTAL;
        stateMachine[IS_OCT_OR_HEX][CLASS_DIGITS] = ERROR;
        stateMachine[IS_OCT_OR_HEX][CLASS_HEX] = ERROR;
    }

    public static void handle(Scanner scanner) {
        state = 0;
        token = "" + scanner.nextChar;

        while (!isDone(state)) {
            state = stateMachine[scanner.nextChar][state];
            if (state == ACCEPT_INT) {
                System.out.println("INTEGER_LITERAL(" + token + ")");
            } else if (state == ACCEPT_OCTAL) {
                System.out.println("OCTAL_LITERAL(" + token + ")");
            } else if (state == ACCEPT_HEX) {
                System.out.println("HEXADECIMAL_LITERAL(" + token + ")");
            }

            scanner.nextChar = (char) scanner.fileScanner.nextByte();
            if (Scanner.char_classes[scanner.nextChar] != Scanner.DIGITS) {
                if (scanner.nextChar == 'x') continue;
                else {
                    state = ERROR;
                }
            }
        }

    }

    private static boolean isDone(int state) {
        return state == ACCEPT_INT || state == ACCEPT_HEX ||
                state == ACCEPT_OCTAL || state == ERROR;
    }
}
