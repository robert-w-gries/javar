package Absyn;
/**
 * Reference to an identifier.
 */
public class IdentifierExpr extends AssignableExpr{
    public String id;

    public IdentifierExpr(java.lang.String id){
         this.id = id;
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Visitor v){
        v.visit(this);
        return;
    }

}
