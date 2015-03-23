package Tree;
/**
 * Implements a binary operator.
 */
public class BINOP extends Tree.Exp{
    public static final int AND=4;

    public static final int ARSHIFT=8;

    public int binop;

    public static final int BITAND=9;

    public static final int BITOR=10;

    public static final int BITXOR=11;

    public static final int DIV=3;

    public Tree.Exp left;

    public static final int LSHIFT=6;

    public static final int MINUS=1;

    public static final int MUL=2;

    public static final int OR=5;

    public static final int PLUS=0;

    public Tree.Exp right;

    public static final int RSHIFT=7;

    public BINOP(int b, Tree.Exp l, Tree.Exp r){
         //TODO codavaj!!
    }

    public void accept(Tree.IntVisitor v, int d){
        return; //TODO codavaj!!
    }

}
