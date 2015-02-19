package Absyn;
/**
 * String Literals.
 */
public class StringLiteral extends Expr{
    public String value;

    public StringLiteral(java.lang.String value){
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
