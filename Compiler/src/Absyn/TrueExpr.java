package Absyn;
/**
 * Boolean TRUE
 */
public class TrueExpr extends Expr{
    public TrueExpr(){}

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

    public Translate.Exp accept(Translate.Translate t) { return t.visitTrue();}
}
