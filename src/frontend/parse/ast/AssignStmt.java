package frontend.parse.ast;

import frontend.translate.Exp;
import frontend.translate.Translate;

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
    }

    @Override
    public frontend.typecheck.Type accept(TypeVisitor v) {
        v.visit(this);
        return null;
    }

    public Exp accept(Translate t) { return t.visit(this);}
}
