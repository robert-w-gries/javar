package Absyn;

import java.util.LinkedList;

/**
 * Xinu Method Call.
 */
public class XinuCallExpr extends Expr{
    public String method;
    public LinkedList<Expr> args;

    public XinuCallExpr(java.lang.String method, java.util.LinkedList<Expr> args){
        this.method = method;
        this.args = args;
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Visitor v){
        v.visit(this);
    }

    @Override
    public Types.Type accept(TypeVisitor v) {
        return v.visit(this);
    }

    public Translate.Exp accept(Translate.Translate t) { return t.visit(this);}
}
