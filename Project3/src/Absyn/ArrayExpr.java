package Absyn;

/**
 * Expression indexing into an Array.
 */
public class ArrayExpr extends Absyn.AssignableExpr{

    public Absyn.Expr targetExpr;
    public Absyn.Expr index;

    public ArrayExpr(Absyn.Expr target, Absyn.Expr index){
        targetExpr = target;
        indexExpr = index; 
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        v.visit(this);
        return;
    }

}
