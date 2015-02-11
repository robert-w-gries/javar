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
        return;
    }

}
