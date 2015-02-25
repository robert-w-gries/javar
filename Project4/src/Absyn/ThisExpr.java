package Absyn;

/**
 * The "this" reference.
 */
public class ThisExpr extends Expr {
    public ThisExpr() {
        //TODO codavaj!!
    }

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