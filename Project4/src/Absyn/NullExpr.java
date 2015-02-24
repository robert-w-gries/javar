package Absyn;

/**
 * The "null" expression.
 */
public class NullExpr extends Expr {
    public NullExpr() {
        //TODO codavaj!!
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Visitor v){
        v.visit(this);
        return;
    }

    @Override
    public Types.Type accept(TypeVisitor v) {
        return v.visit(this);
    }
}
