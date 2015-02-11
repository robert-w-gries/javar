package Absyn;
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

}
