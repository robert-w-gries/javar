package frontend.parse.ast;

/**
 * Expressions that can appear on the left hand side of an assignment.
 */
public abstract class AssignableExpr extends Expr {

    AssignableExpr(){
         
    }

    /**
     * Visitor pattern dispatch.
     */
    public abstract void accept(Visitor v);

}
