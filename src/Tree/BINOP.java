package Tree;

import java.util.LinkedList;
import java.util.List;

/**
 * Implements a binary operator.
 */
public class BINOP extends Tree.Exp {

    public static enum Operation {
        PLUS, MINUS, MUL, DIV,
        AND, OR,
        LSHIFT, RSHIFT, ARSHIFT,
        BITAND, BITOR, BITXOR
    }

    public Operation binop;
    public Tree.Exp left;
    public Tree.Exp right;

    public BINOP(Operation b, Tree.Exp l, Tree.Exp r) {
        left = l;
        binop = b;
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
    public Exp build(List<Exp> kids) {
        return new BINOP(binop, kids.get(0), kids.get(1));
    }

    public void accept(Tree.IntVisitor v){
        v.visit(this);
    }

}
