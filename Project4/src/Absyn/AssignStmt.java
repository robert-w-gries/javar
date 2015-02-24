package Absyn;

import Types.Type;
/**
 * Assignment Statements.
 */
public class AssignStmt extends Stmt{

    public AssignableExpr leftExpr;
    public Expr rightExpr;

    public AssignStmt(AssignableExpr lhs, Expr rhs){
        leftExpr = lhs;
        rightExpr = rhs; 
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Visitor v){
        v.visit(this);
        return; 
    }

    @Override
    public Types.Type accept(TypeVisitor v) {
        v.visit(this);
        return null;
    }
}
