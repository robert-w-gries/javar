package Absyn;


import Types.Type;

/**
 * Addition Expressions.
 */
public class AddExpr extends BinOpExpr {

    public AddExpr(Expr e1, Expr e2){
        super(e1, e2);
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Visitor v){
        v.visit(this);
    }

    @Override
    public Type accept(TypeVisitor v) {
        return v.visit(this);
    }

    public Translate.Exp accept(Translate.Translate t) { return t.visit(this);}

}
