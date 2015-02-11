package Absyn;
/**
 * Boolean TRUE
 */
public class TrueExpr extends Expr{
    public TrueExpr(){
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
