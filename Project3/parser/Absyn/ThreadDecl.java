package Absyn;
/**
 * Thread Class Declaration Blocks
 */
public class ThreadDecl extends Absyn.ClassDecl{
    public ThreadDecl(java.lang.String name, java.util.LinkedList<Absyn.VarDecl> fields, java.util.LinkedList<Absyn.MethodDecl> methods){
         //TODO codavaj!!
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        return; //TODO codavaj!!
    }

}
