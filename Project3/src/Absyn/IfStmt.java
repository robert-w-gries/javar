package Absyn;
/**
 * If Statements.
 */
public class IfStmt extends Absyn.Stmt{

    public Absyn.Expr test;
    public Absyn.Stmt thenStm;
    public Absyn.Stmt elseStm;

    public IfStmt(Absyn.Expr test, Absyn.Stmt thenStm, Absyn.Stmt elseStm)
    {   this.test = test;   this.thenStm = thenStm;   this.elseStm = elseStm;   }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        v.visit(this);
        return;
    }

}
