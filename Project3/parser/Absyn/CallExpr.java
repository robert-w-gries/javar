package Absyn;

/**
 * Method Call.
 */
public class CallExpr extends Absyn.Expr{

    public Absyn.Expr targetExpr;
    public java.lang.String methodString;
    public java.util.LinkedList<Absyn.Expr> argsList;

    public CallExpr(Absyn.Expr target, java.lang.String method, java.util.LinkedList<Absyn.Expr> args){
        targetExpr = target;
        methodString = method;
        argsList = args; 
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        v.visit(this);
        return; 
    }

}
