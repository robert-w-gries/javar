package Tree;
/**
 * Implements an expression sequence, statment is evaluated for side effects and the expression is evaluated as result.
 */
public class ESEQ extends Tree.Exp{
    public Tree.Exp exp;

    public Tree.Stm stm;

    public ESEQ(Tree.Stm s, Tree.Exp e){
         //TODO codavaj!!
    }

    public void accept(Tree.IntVisitor v, int d){
        return; //TODO codavaj!!
    }

}
