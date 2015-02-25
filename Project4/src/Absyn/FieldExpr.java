package Absyn;

/**
 * Field lookup on object reference.
 */
public class FieldExpr extends AssignableExpr{
    public Expr target;
    public String field;

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
    public Types.Type accept(TypeVisitor v) {
        return v.visit(this);
    }
}
