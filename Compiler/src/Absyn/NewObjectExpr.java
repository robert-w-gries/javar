package Absyn;
/**
 * Object Allocation.
 */
public class NewObjectExpr extends Expr{
    public Type type;

    public NewObjectExpr(Type type){
        this.type = type;
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Visitor v){
        v.visit(this);
    }

    @Override
    public Types.Type accept(TypeVisitor v) {
        return v.visit(this);
    }

    public Translate.Exp accept(Translate.Translate t) { return t.visit(this);}
}
