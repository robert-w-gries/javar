package Absyn;

import Types.Type;
/**
 * Boolean FALSE.
 */
public class FalseExpr extends Expr{
    public FalseExpr(){
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
