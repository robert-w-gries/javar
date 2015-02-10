package Absyn;

/**
 * Boolean types.
 */
public class BooleanType extends Absyn.Type{

    public BooleanType(){
         
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        v.visit(this);
        return; 
    }

}
