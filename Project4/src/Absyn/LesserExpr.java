package Absyn;
/**
 * Less-Then Comparison Expressions.
 */
public class LesserExpr extends BinOpExpr{
    public LesserExpr(Expr e1, Expr e2){
         super(e1, e2);
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
