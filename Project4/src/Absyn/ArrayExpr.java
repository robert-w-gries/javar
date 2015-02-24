package Absyn;

import Types.Type;

/**
 * Expression indexing into an Array.
 */
public class ArrayExpr extends AssignableExpr{

    public Expr targetExpr;
    public Expr index;

    public ArrayExpr(Expr target, Expr index){
        this.targetExpr = target;
        this.index = index;
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Visitor v){
        v.visit(this);
    }


    @Override
    public Type accept(TypeVisitor v) {
        return v.visit(this);
    }
}
