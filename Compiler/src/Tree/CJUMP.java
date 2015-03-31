package Tree;
/**
 * Implements a conditional jump.
 */
public class CJUMP extends Tree.Stm {

    public static final int EQ=0;
    public static final int NE=1;
    public static final int LT=2;
    public static final int GT=3;
    public static final int LE=4;
    public static final int GE=5;
    public static final int ULT=6;
    public static final int ULE=7;
    public static final int UGT=8;
    public static final int UGE=9;

    public Temp.Label iffalse;
    public Temp.Label iftrue;

    public Tree.Exp left;
    public int relop;
    public Tree.Exp right;

    public CJUMP(int rel, Tree.Exp l, Tree.Exp r, Temp.Label t, Temp.Label f) {

        iftrue = t;
        iffalse = f;

        relop = rel;
        left = l;
        right = r;

    }

    public void accept(Tree.IntVisitor v, int d) {
        v.visit(this, d);
    }

    public static int notRel(int relop){
        return 0; //TODO
    }

    public static int swapRel(int relop){
        return 0; //TODO
    }

}
