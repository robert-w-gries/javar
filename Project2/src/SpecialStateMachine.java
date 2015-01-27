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
                            SINGLE_COMMENT_CLOSE = 7,
                            MULTI_COMMENT_CLOSE = 8;

    private static int[] special_types = new int[34];

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

    private static int[][] next_state = new int[9][34];

    static {
        for (int i = 0; i < 33; i++) {
            next_state[START][i] = DONE;
            i++;
        }
        next_state[START][33] = ERROR;
        next_state[START][0] = OREQ;
        next_state[START][17] = OREQ;
        next_state[START][18] = OREQ;
        next_state[START][19] = OREQ;
        next_state[START][5] = BAND;
        next_state[START][29] = BOR;
        next_state[START][14] = COMMENT_OPEN;
        next_state[START][9] = MULTI_COMMENT_CLOSE;
    }

    private static String token;

    private static char next, prev;

    private static boolean commentComplete = true;



    public static void handle(Scanner scanner) {

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

        switch(next_state[currState][next]){
            case DONE:{
                print(next);
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
            case 9:{
                System.out.println("STAR");
                break;
            }
            case 10:{
                System.out.println("PLUS");
                break;
            }
            case 11:{
                System.out.println("COMMA");
                break;
            }
            case 12:{
                System.out.println("MINUS");
                break;
            }case 13:{
                System.out.println("PERIOD");
                break;
            }
            case 16:{
                System.out.println("SEMICOLON");
                break;
            }
            case 17:{
                System.out.println("LESSTHAN");
                break;
            }
            case 18:{
                System.out.println("EQUAL");
                break;
            }
            case 19:{
                System.out.println("GREATERTHAN");
                break;
            }
            case 22:{
                System.out.println("LSQUARE");
                break;
            }
            case 23:{
                System.out.println("FORWARDSLASH");
                break;
            }
            case 24:{
                System.out.println("RSQUARE");
                break;
            }
            case 25:{
                System.out.println("XOR");
                break;
            }
            case 28:{
                System.out.println("LBRACE");
                break;
            }
            case 29:{
                System.out.println("BWOR");
                break;
            }
            case 30:{
                System.out.println("RBRACE");
                break;
            }
            case 31:{
                System.out.println("COMP");
                break;
            }
        }
    }
}
