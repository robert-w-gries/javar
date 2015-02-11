package Absyn;
/**
 * String Literals.
 */
public class StringLiteral extends Absyn.Expr{

    public java.lang.String value;

    public StringLiteral(java.lang.String value)
    {   this.value = value;   }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        v.visit(this);
        return;
    }

}
