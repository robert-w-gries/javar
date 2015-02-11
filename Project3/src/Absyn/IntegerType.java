package Absyn;
/**
 * Integer types.
 */
public class IntegerType extends Absyn.Type{

    public IntegerType(){}

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        v.visit(this);
        return;
    }

}
