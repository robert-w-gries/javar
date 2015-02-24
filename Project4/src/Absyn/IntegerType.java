package Absyn;

/**
 * Integer types.
 */
public class IntegerType extends Type {

    public IntegerType() {

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
