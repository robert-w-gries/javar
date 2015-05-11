package frontend.parse.ast;

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
    }

    @Override
    public frontend.typecheck.Type accept(TypeVisitor v) {
        return v.visit(this);
    }
}
