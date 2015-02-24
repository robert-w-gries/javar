package Absyn;

/**
 * Array type.
 */
public class ArrayType extends Type{

    public Type baseType;

    public ArrayType(Type base){
        baseType = base; 
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
