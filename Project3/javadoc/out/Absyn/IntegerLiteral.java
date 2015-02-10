package Absyn;
/**
 * Integer Literals.
 */
public class IntegerLiteral extends Absyn.Expr{
    public IntegerLiteral(int value){
         //TODO codavaj!!
    }

    public IntegerLiteral(java.lang.Integer value){
         //TODO codavaj!!
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        return; //TODO codavaj!!
    }

}
