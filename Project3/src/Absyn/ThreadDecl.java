package Absyn;
/**
 * Thread Class Declaration Blocks
 */
public class ThreadDecl extends ClassDecl{
    public ThreadDecl(java.lang.String name, java.util.LinkedList<VarDecl> fields, java.util.LinkedList<MethodDecl> methods){
         super(name, "Thread", fields, methods);
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Visitor v){
        v.visit(this);
        return;
    }

}
