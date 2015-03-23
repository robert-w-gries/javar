package Tree;
/**
 * Implements a move instruction.
 */
public class MOVE extends Tree.Stm{
    public Tree.Exp dst;

    public Tree.Exp src;

    public MOVE(Tree.Exp d, Tree.Exp s){
         //TODO codavaj!!
    }

    public void accept(Tree.IntVisitor v, int d){
        return; //TODO codavaj!!
    }

}
