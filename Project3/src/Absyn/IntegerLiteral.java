package Absyn;
/**
 * Integer Literals.
 */
public class IntegerLiteral extends Expr{
    public int value;

    public IntegerLiteral(int value){
         this.value = value;
    }

    public IntegerLiteral(java.lang.Integer value){
         this.value = value;
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Visitor v){
        v.visit(this);
        return;
    }

}
