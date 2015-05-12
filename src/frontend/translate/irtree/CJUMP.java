package frontend.translate.irtree;

import arch.Label;

import java.util.LinkedList;
import java.util.List;

/**
 * Implements a conditional jump.
 */
public class CJUMP extends Stm {

    public static enum RelOperation {
        EQ, NE,
        LT, GT,
        LE, GE,
        ULT, ULE,
        UGT, UGE
    }

    public Label iffalse;
    public Label iftrue;

    public Exp left;
    public RelOperation relop;
    public Exp right;

    public CJUMP(RelOperation rel, Exp l, Exp r, Label t, Label f) {

        iftrue = t;
        iffalse = f;

        relop = rel;
        left = l;
        right = r;

    }

    @Override
    public List<Exp> kids() {
        List<Exp> exps = new LinkedList<>();
        exps.add(left);
        exps.add(right);
        return exps;
    }

    @Override
    public Stm build(List<Exp> kids) {
        return new CJUMP(relop, kids.get(0), kids.get(1), iftrue, iffalse);
    }

    public void accept(IntVisitor v) {
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

}
