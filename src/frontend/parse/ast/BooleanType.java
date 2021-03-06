package frontend.parse.ast;

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
    }

    @Override
    public frontend.typecheck.Type accept(TypeVisitor v) {
        return v.visit(this);
    }
}
