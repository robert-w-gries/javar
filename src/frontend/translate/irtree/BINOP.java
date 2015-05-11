package frontend.translate.irtree;

import java.util.LinkedList;
import java.util.List;

/**
 * Implements a binary operator.
 */
public class BINOP extends Exp {

    public static enum Operation {
        PLUS, MINUS, MUL, DIV,
        AND, OR,
        LSHIFT, RSHIFT, ARSHIFT,
        BITAND, BITOR, BITXOR
    }

    public Operation binop;
    public Exp left;
    public Exp right;

    public BINOP(Operation b, Exp l, Exp r) {
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

    public void accept(IntVisitor v){
        v.visit(this);
    }

}
