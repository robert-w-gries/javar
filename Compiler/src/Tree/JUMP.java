package Tree;
/**
 * Jump (transfer control) to address e.
 */
public class JUMP extends Tree.Stm{
    public Tree.Exp exp;

    public java.util.LinkedList<Temp.Label> targets;

    public JUMP(Tree.Exp e, java.util.LinkedList<Temp.Label> t){
         //TODO codavaj!!
    }

    public JUMP(Temp.Label target){
         //TODO codavaj!!
    }

    public void accept(Tree.IntVisitor v, int d){
        return; //TODO codavaj!!
    }

}
