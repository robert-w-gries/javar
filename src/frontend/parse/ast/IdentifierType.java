package frontend.parse.ast;

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
    }

    @Override
    public frontend.typecheck.Type accept(TypeVisitor v) {
        return v.visit(this);
    }
}
