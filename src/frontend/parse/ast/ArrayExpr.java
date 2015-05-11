package frontend.parse.ast;

import frontend.typecheck.Type;
import frontend.translate.Exp;
import frontend.translate.Translate;

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

    public Exp accept(Translate t) { return t.visit(this);}
}
