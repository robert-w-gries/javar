package Absyn;
/**
 * Method Call.
 */
public class CallExpr extends Absyn.Expr{
    public CallExpr(Absyn.Expr target, java.lang.String method, java.util.LinkedList<Absyn.Expr> args){
         //TODO codavaj!!
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        return; //TODO codavaj!!
    }

}
