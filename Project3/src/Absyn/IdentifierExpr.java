package Absyn;
/**
 * Reference to an identifier.
 */
public class IdentifierExpr extends Absyn.AssignableExpr{

    public java.lang.String id;

    public IdentifierExpr(java.lang.String id)
    {   this.id = id;   }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        v.visit(this);
        return;
    }

}
