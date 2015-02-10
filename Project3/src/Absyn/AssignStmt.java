package Absyn;

/**
 * Assignment Statements.
 */
public class AssignStmt extends Absyn.Stmt{

    public Absyn.AssignableExpr leftExpr;
    public Absyn.Expr rightExpr;

    public AssignStmt(Absyn.AssignableExpr lhs, Absyn.Expr rhs){
        leftExpr = lhs;
        rightExpr = rhs; 
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        v.visit(this);
        return; 
    }

}
