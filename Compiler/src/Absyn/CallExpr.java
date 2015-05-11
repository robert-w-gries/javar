package Absyn;

/**
 * Method Call.
 */
public class CallExpr extends Expr{

    public Expr targetExpr;
    public java.lang.String methodString;
    public java.util.LinkedList<Expr> argsList;
    public int id;

    public CallExpr(Expr target, java.lang.String method, java.util.LinkedList<Expr> args){
        targetExpr = target;
        methodString = method;
        argsList = args; 
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

    public Translate.Exp accept(Translate.Translate t) { return t.visit(this);}
}
