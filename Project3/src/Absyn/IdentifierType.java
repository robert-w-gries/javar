package Absyn;
/**
 * Class types.
 */
public class IdentifierType extends Absyn.Type{

    public java.lang.String id;

    public IdentifierType(java.lang.String id)
    {   this.id = id;   }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        v.visit(this);
        return;
    }

}
