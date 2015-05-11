package frontend.parse.ast;

import frontend.translate.Exp;
import frontend.translate.Translate;

/**
 * While Statements.
 */
public class WhileStmt extends Stmt{
    public Expr test;
    public Stmt body;

    public WhileStmt(Expr test, Stmt body){
        this.test = test;
        this.body = body;
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
