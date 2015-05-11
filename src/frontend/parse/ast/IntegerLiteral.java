package frontend.parse.ast;

import frontend.translate.Exp;
import frontend.translate.Translate;

/**
 * Integer Literals.
 */
public class IntegerLiteral extends Expr{
    public int value;

    public IntegerLiteral(int value){
         this.value = value;
    }

    public IntegerLiteral(java.lang.Integer value){
         this.value = value;
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Visitor v){
        v.visit(this);
    }

    @Override
    public frontend.typecheck.Type accept(TypeVisitor v) {
        return v.visit(this);
    }

    public Exp accept(Translate t) { return t.visit(this);}
}
