package Tree;
/**
 * Sequence: statement 1 followed by statment 2.
 */
public class SEQ extends Tree.Stm{
    public Tree.Stm left;

    public Tree.Stm right;

    public SEQ(Tree.Stm l, Tree.Stm r){
         //TODO codavaj!!
    }

    public void accept(Tree.IntVisitor v, int d){
        return; //TODO codavaj!!
    }

}
