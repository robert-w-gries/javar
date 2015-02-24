package Absyn;

import java.util.LinkedList;

/**
 * Xinu Statements.
 */
public class XinuCallStmt extends Stmt{
    public String method;
    public LinkedList<Expr> args;

    public XinuCallStmt(java.lang.String method, java.util.LinkedList<Expr> args){
        this.method = method;
        this.args = args;
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
