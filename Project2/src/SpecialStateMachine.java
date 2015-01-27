/**
 * Created by rgries on 1/25/15.
 */
public class SpecialStateMachine {

    private static int currState = 0;

    private static final int START  = 0,
                            DONE  = 1,
                            ERROR = 2,
                            OREQ = 3,
                            BAND = 4,
                            BOR = 5,
                            COMMENT_OPEN = 6,
                            STRING_START = 7;

    /*
     *   There are only 4 blocks of special chars in the ASCII table,
     *   33 '!' - 47 '/'    58 ':' - 64 '@'     91 '[' - 96 '`' and
     *   123 '{' - 126 '~'      These blocks are taken and placed into
     *   the array after cutting out the other chars. The 32nd index
     *   just represents non-special chars.
     *
     *   ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ----
     *  | 0  | 1  | 2  | 3  | 4  | 5  | 6  | 7  | 8  | 9  | 10 | 11 | 12 |
     *  | !  | "  | #  | $  | %  | &  | '  | (  | )  | *  | +  | ,  | -  |
     *   ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ----
     *  | 13 | 14 | 15 | 16 | 17 | 18 | 19 | 20 | 21 | 22 | 23 | 24 | 25 |
     *  | .  | /  | :  | ;  | <  | =  | >  | ?  | @  | [  | \  | ]  | ^  |
     *   ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ----
     *  | 26 | 27 | 28 | 29 | 30 | 31 | 32 | 33 |
     *  | _  | `  | {  | |  | }  | ~  | '  |!Spc|
     *   ---- ---- ---- ---- ---- ---- ---- ----
     *
     */

    // Only have mappings from the start state, all other cases are handled
    // in a separate method or are just one or two chars to worry about.
    private static int[][] next_state = new int[1][34];

    static {
        for (int i = 0; i < 33; i++) {
            next_state[START][i] = DONE;
            i++;
        }
        next_state[START][33] = ERROR;
        next_state[START][0] = OREQ;
        next_state[START][18] = OREQ;
        next_state[START][5] = BAND;
        next_state[START][29] = BOR;
        next_state[START][14] = COMMENT_OPEN;
        next_state[START][1] = STRING_START;

    }

    // One char buffer to check things like = vs ==
    private static char next, prev;

    private static String token = "";

    public static void handle(Scanner scanner) {

        try {
            setNext(scanner);
        }catch(Exception e){
            System.out.println("EOF");
        }

        switch(next_state[currState][next]){
            case DONE:{
                print(next);
            }

            case OREQ:{
                prev = next;
                try {
                    setNext(scanner);
                }catch(Exception e){
                    System.out.println("EOF");
                }
                // =
                if(next == 18){
                    eq_print(prev);
                }else{
                    print(prev);
                    if(next != 33){
                        print(next);
                    }else{
                        // Return char to scanner buffer
                    }
                }
            }

            case BAND:{
                prev = next;
                try {
                    setNext(scanner);
                }catch(Exception e){
                    System.out.println("EOF");
                }
                // &
                if(next == 5){
                    System.out.println("AND");
                }else{
                    print(prev);
                    if(next != 33){
                        print(next);
                    }else{
                        // Return char to scanner buffer
                    }
                }
            }

            case BOR:{
                prev = next;
                try {
                    setNext(scanner);
                }catch(Exception e){
                    System.out.println("EOF");
                }
                // |
                if(next == 29){
                    System.out.println("OR");
                }else{
                    print(prev);
                    if(next != 33){
                        print(next);
                    }else{
                        // Return char to scanner buffer
                    }
                }
            }

            case COMMENT_OPEN:{
                prev = next;
                try {
                    setNext(scanner);
                }catch(Exception e){
                    System.out.println("EOF");
                }
                // /
                if(next == 14){
                    singleWait(scanner);
                // *
                }else if (next == 9){
                    multiWait(scanner);
                }else{
                    print(prev);
                    if(next != 33){
                        print(next);
                    }else{
                        // Return char to scanner buffer
                    }
                }
            }

            case STRING_START:{
                boolean valid = true;
                prev = next;
                while(valid){
                    try {
                        next = scanner.nextChar;
                    }catch(Exception e) {
                        System.out.println("String not terminated at end of line.");
                        System.out.println("EOF");
                        break;
                    }
                    if(next == '\n'){
                        System.out.println("String not terminated at end of line.");
                        valid = false;
                    }else if(next == '"'){
                        System.out.println("STRING_LITERAL(" + token + ")");
                        valid = false;
                    }else{
                        token += next;
                    }
                }
            }
        }
    }

    public static void print(int index){
        switch (index){
            // !
            case 0:{
                System.out.println("BANG");
                break;
            }
            // &
            case 5:{
                System.out.println("BWAND");
                break;
            }
            // (
            case 7:{
                System.out.println("LPAREN");
                break;
            }
            // )
            case 8:{
                System.out.println("RPAREN");
                break;
            }
            // *
            case 9:{
                System.out.println("STAR");
                break;
            }
            // +
            case 10:{
                System.out.println("PLUS");
                break;
            }
            // ,
            case 11:{
                System.out.println("COMMA");
                break;
            }
            // -
            case 12:{
                System.out.println("MINUS");
                break;
            }
            // .
            case 13:{
                System.out.println("PERIOD");
                break;
            }
            // /
            case 14:{
                System.out.println("FORWARDSLASH");
                break;
            }
            // ;
            case 16:{
                System.out.println("SEMICOLON");
                break;
            }
            // <
            case 17:{
                System.out.println("LESSTHAN");
                break;
            }
            // =
            case 18:{
                System.out.println("ASSIGN");
                break;
            }
            // >
            case 19:{
                System.out.println("GREATERTHAN");
                break;
            }
            // [
            case 22: {
                System.out.println("LSQUARE");
                break;
            }
            // ]
            case 24:{
                System.out.println("RSQUARE");
                break;
            }
            // ^
            case 25:{
                System.out.println("XOR");
                break;
            }
            // {
            case 28:{
                System.out.println("LBRACE");
                break;
            }
            // |
            case 29:{
                System.out.println("BWOR");
                break;
            }
            // }
            case 30:{
                System.out.println("RBRACE");
                break;
            }
            // ~
            case 31:{
                System.out.println("COMP");
                break;
            }
            // Other
            default: {
                System.out.println("Illegal Token");
            }
        }
    }

    public static void eq_print(int prev){

        switch (prev) {
            // !=
            case 0: {
                System.out.println("NOTEQUAL");
                break;
            }
            // ==
            case 18: {
                System.out.println("EQUAL");
                break;
            }
            // Other
            default: {
                System.out.println("Illegal Token");
            }
        }
    }

    public static void setNext(Scanner scanner){

        next = scanner.nextChar;

        if(next >= '!' && next <= '/'){
            next -= 33;
        }else if(next >= ':'  && next <= '@'){
            next -= 43;
        }else if(next >= '[' && next <= '`'){
            next -= 69;
        }else if(next >= '{' && next <= '~'){
            next -= 95;
        }else{
            next = 32;
        }

    }

    public static void singleWait(Scanner scanner){
        while(next != '\n'){
            try {
                next = scanner.nextChar;
            }catch(Exception e){
                System.out.println("EOF");
            }

        }
    }

    public static void multiWait(Scanner scanner){
        boolean ready = false;
        while(true){
            try {
                next = scanner.nextChar;
            }catch(Exception e){
                System.out.println("Comment not terminated at end of input.");
                System.out.println("EOF");
                break;
            }
            if(next == '*'){
                ready = true;
            }else if((next == '/') && ready){
                break;
            }else{
                ready = false;
            }
        }
    }

  /*  public static boolean isLetter(char c){
        return ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'));
    }*/
}

