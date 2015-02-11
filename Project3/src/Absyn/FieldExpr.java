package Absyn;
/**
 * Field lookup on object reference.
 */
public class FieldExpr extends Absyn.AssignableExpr{

    public Absyn.Expr target;
    public java.lang.String field;

    public FieldExpr(Absyn.Expr target, java.lang.String field)
    {   this.target = target;   this.field = field;   }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        v.visit(this);
        return;
    }

}
