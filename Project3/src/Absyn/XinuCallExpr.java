package Absyn;

import java.util.LinkedList;

/**
 * Xinu Method Call.
 */
public class XinuCallExpr extends Expr{
    public String method;
    LinkedList<Expr> args;

    public XinuCallExpr(java.lang.String method, java.util.LinkedList<Expr> args){
        this.method = method;
        this.args = args;
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Visitor v){
        return; //TODO codavaj!!
    }

}
