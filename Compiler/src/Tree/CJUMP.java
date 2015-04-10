package Tree;

import java.util.LinkedList;
import java.util.List;

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

    @Override
    public List<Exp> kids() {
        List<Exp> exps = new LinkedList<Exp>();
        exps.add(left);
        exps.add(right);
        return exps;
    }

    @Override
    public Stm build(List<Exp> kids) {
        return new CJUMP(relop, kids.get(0), kids.get(1), iftrue, iffalse);
    }

    public void accept(Tree.IntVisitor v) {
        v.visit(this);
    }

    public static RelOperation notRel(RelOperation relop){
        switch (relop) {
            case EQ: return RelOperation.NE;
            case NE: return RelOperation.EQ;
            case LT: return RelOperation.GE;
            case GT: return RelOperation.LE;
            case LE: return RelOperation.GT;
            case GE: return RelOperation.LT;
            case ULT: return RelOperation.UGE;
            case ULE: return RelOperation.UGT;
            case UGT: return RelOperation.ULE;
            default: return RelOperation.ULT; // UGE
        }
    }

    public static int swapRel(int relop){
        return 0; //TODO
    }

}
