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
        v.visit(this);
        return;
    }

    @Override
    public Types.Type accept(TypeVisitor v) {
        return v.visit(this);
    }
}
