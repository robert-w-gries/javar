package Absyn;

/**
 * Expressions that can appear on the left hand side of an assignment.
 */
public abstract class AssignableExpr extends Absyn.Expr{

    public AssignableExpr(){
         
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        v.visit(this);
        return;
    }

}
