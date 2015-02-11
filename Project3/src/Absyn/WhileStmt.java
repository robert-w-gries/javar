package Absyn;
/**
 * While Statements.
 */
public class WhileStmt extends Absyn.Stmt{

    public Absyn.Expr test;
    public Absyn.Stmt body;

    public WhileStmt(Absyn.Expr test, Absyn.Stmt body)
    {   this.test = test;   this.body = body;   }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        v.visit(this);
        return;
    }

}
