package frontend.parse.ast;

import frontend.translate.Exp;
import frontend.translate.Translate;

/**
 * String Literals.
 */
public class StringLiteral extends Expr{
    public String value;

    public StringLiteral(java.lang.String value){
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
