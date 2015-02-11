package Absyn;
/**
 * Class types.
 */
public class IdentifierType extends Type{
    public String id;

    public IdentifierType(java.lang.String id){
         this.id = id;
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Visitor v){
        return; //TODO codavaj!!
    }

}
