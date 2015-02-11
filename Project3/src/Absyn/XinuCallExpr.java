package Absyn;
/**
 * Xinu Method Call.
 */
public class XinuCallExpr extends Absyn.Expr{

    public java.lang.String method;
    public java.util.LinkedList<Absyn.Expr> args;

    public XinuCallExpr(java.lang.String method, java.util.LinkedList<Absyn.Expr> args)
    {   this.method = method;   for(int i = 0; i < args.size(); i++) this.args.set(i, args.get(i));   }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        v.visit(this);
        return;
    }

}
