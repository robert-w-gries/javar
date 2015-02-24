package Absyn;

/**
 * Boolean types.
 */
public class BooleanType extends Type{

    public BooleanType(){}

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
