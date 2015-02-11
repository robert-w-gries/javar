package Absyn;
/**
 * Integer Literals.
 */
public class IntegerLiteral extends Absyn.Expr{

    public int valueInt;

    public IntegerLiteral(int value)
    {   valueInt = value;   }

    public java.lang.Integer valueInteger;

    public IntegerLiteral(java.lang.Integer value)
    {   valueInteger = value;   }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        v.visit(this);
        return;
    }

}
