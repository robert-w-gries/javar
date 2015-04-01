package Tree;
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

    public void accept(Tree.IntVisitor v){
        v.visit(this);
    }

}
