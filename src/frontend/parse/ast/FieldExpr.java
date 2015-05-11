package frontend.parse.ast;

import frontend.translate.Exp;
import frontend.translate.Translate;

/**
 * Field lookup on object reference.
 */
public class FieldExpr extends AssignableExpr{
    public Expr target;
    public String field;
    public int id;

    public FieldExpr(Expr target, java.lang.String field){
         this.target = target;
        this.field = field;
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
