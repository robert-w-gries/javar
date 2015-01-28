package scanner; /**
 * Created by rgries on 1/25/15.
 */

import java.io.IOException;
import java.util.ArrayList;

public class LetterStateMachine {

    private enum State {
        START, VALID_ID, CHECK_RESERVED,    // non-terminal states
        ACCEPT_ID, ACCEPT_RESERVED, ERROR,  // terminal states
        DONE                                // end state before exiting scanner.LetterStateMachine
    }

    private static ArrayList<String> reservedWords = new ArrayList<String>();
    private static ArrayList<String> xinuReservedWords = new ArrayList<String>();

    static {
        reservedWords.add("class");
        reservedWords.add("public");
        reservedWords.add("static");
        reservedWords.add("void");
        reservedWords.add("main");
        reservedWords.add("String");
        reservedWords.add("extends");
        reservedWords.add("return");
        reservedWords.add("int");
        reservedWords.add("boolean");
        reservedWords.add("if");
        reservedWords.add("else");
        reservedWords.add("while");
        reservedWords.add("length");
        reservedWords.add("true");
        reservedWords.add("false");
        reservedWords.add("this");
        reservedWords.add("new");
        reservedWords.add("synchronized");
        xinuReservedWords.add("Xinu.print");
        xinuReservedWords.add("Xinu.println");
        xinuReservedWords.add("Xinu.printint");
        xinuReservedWords.add("Xinu.readint");
        xinuReservedWords.add("Xinu.threadCreate");
        xinuReservedWords.add("Xinu.yield");
        xinuReservedWords.add("Xinu.sleep");
    }

    public static void handle(Scanner scanner) throws IOException {

        State currState = State.START;
        String token = "";

        while (currState != State.DONE) {

            switch (currState) {

                case START: {

                    // tokens that begin with underscores are invalid
                    if (scanner.nextChar == '_') {
                        currState = State.ERROR;
                    } else {
                        currState = State.VALID_ID;
                    }

                    break;

                }

                case CHECK_RESERVED: {

                    // if token is in reservedWords list, then accept it as reserved
                    if (reservedWords.contains(token) || xinuReservedWords.contains(token)) {
                        currState = State.ACCEPT_RESERVED;
                    } else {
                        currState = State.ACCEPT_ID;
                    }

                    break;

                }

                case ACCEPT_ID: {

                    System.out.println("ID(" + token + ")");
                    currState = State.DONE;
                    break;

                }

                case ACCEPT_RESERVED: {
                    if (xinuReservedWords.contains(token)) {
                        System.out.println(token.substring(5).toUpperCase());
                    } else {
                        System.out.println(token.toUpperCase());
                    }
                    currState = State.DONE;
                    break;

                }

                case ERROR: {
                    // loop until white space is reached
                    while (Scanner.char_classes[scanner.nextChar] != Scanner.ClassType.SPACE &&
                            (int) scanner.nextChar != -1) {
                        scanner.nextChar = (char) scanner.fileReader.read();
                    }

                    System.out.println("Illegal token.");
                    currState = State.DONE;

                    break;
                }

                case VALID_ID: {

                    // add current character to token
                    token = token + scanner.nextChar;

                    // read in the next byte
                    scanner.nextChar = (char) scanner.fileReader.read();

                    if ((int) scanner.nextChar == -1) {
                        return;
                    }
                    if (token.equals("Xinu") && scanner.nextChar == '.') {
                        break;
                    }

                    // stop reading in identifier characters when these cases are reached
                    if (Scanner.char_classes[scanner.nextChar] == Scanner.ClassType.SPACE ||
                            Scanner.char_classes[scanner.nextChar] == Scanner.ClassType.SPECIAL) {

                        currState = State.CHECK_RESERVED;

                    } else if (Scanner.char_classes[scanner.nextChar] == Scanner.ClassType.INVALID) {

                        currState = State.ERROR;

                    }

                    break;

                }

                default: {

                    break;

                }

            } // end switch

        }

    }

    public static boolean isDone(State state) {
        return state == State.ACCEPT_ID || state == State.ACCEPT_RESERVED || state == State.ERROR;
    }

}
