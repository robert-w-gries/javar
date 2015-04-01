package Tree;
/**
 * Implements a conditional jump.
 */
public class CJUMP extends Tree.Stm {

    public static enum RelOperation {
        EQ, NE,
        LT, GT,
        LE, GE,
        ULT, ULE,
        UGT, UGE
    }

    public Temp.Label iffalse;
    public Temp.Label iftrue;

    public Tree.Exp left;
    public RelOperation relop;
    public Tree.Exp right;

    public CJUMP(RelOperation rel, Tree.Exp l, Tree.Exp r, Temp.Label t, Temp.Label f) {

        iftrue = t;
        iffalse = f;

        relop = rel;
        left = l;
        right = r;

    }

    public void accept(Tree.IntVisitor v) {
        v.visit(this);
    }

    public static int notRel(int relop){
        return 0; //TODO
    }

    public static int swapRel(int relop){
        return 0; //TODO
    }

}
