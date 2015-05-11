package frontend.parse.ast;

/**
 * Declarations for formal method parameters.
 */
public class Formal extends Absyn{
    public Type type;
    public String name;
    public frontend.typecheck.Type checktype;

    public Formal(Type type, java.lang.String name){
        this.type = type;
        this.name = name;
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
