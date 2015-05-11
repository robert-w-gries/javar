package frontend.parse.ast;

import frontend.translate.Exp;
import frontend.translate.Translate;

/**
 * If Statements.
 */
public class IfStmt extends Stmt{
    public Expr test;
    public Stmt thenStm, elseStm;

    public IfStmt(Expr test, Stmt thenStm, Stmt elseStm){
        this.test = test;
        this.thenStm = thenStm;
        this.elseStm = elseStm;
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
