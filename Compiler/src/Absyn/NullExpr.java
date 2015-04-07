package Absyn;

/**
 * The "null" expression.
 */
public class NullExpr extends Expr {
    public NullExpr() {}

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Visitor v){
        v.visit(this);
    }

    @Override
    public Types.Type accept(TypeVisitor v) {
        return v.visit(this);
    }
}
