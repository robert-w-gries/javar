package Absyn;
/**
 * Xinu Statements.
 */
public class XinuCallStmt extends Absyn.Stmt{

    public java.lang.String method;
    public java.util.LinkedList<Absyn.Expr> args;

    public XinuCallStmt(java.lang.String method, java.util.LinkedList<Absyn.Expr> args)
    {   this.method = method;   for (int i = 0; i < args.size(); i++) this.args.set(i, args.get(i));   }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        v.visit(this);
        return;
    }

}
