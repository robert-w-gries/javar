package Absyn;

/**
 * Array type.
 */
public class ArrayType extends Absyn.Type{

    public Absyn.Type baseType;

    public ArrayType(Absyn.Type base){
        baseType = base; 
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        v.visit(this);
        return; 
    }

}
