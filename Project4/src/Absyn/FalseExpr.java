package Absyn;

/**
 * Boolean FALSE.
 */
public class FalseExpr extends Expr{
    public FalseExpr(){
         //TODO codavaj!!
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
}
