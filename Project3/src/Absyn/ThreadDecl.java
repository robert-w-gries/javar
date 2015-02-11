package Absyn;
/**
 * Thread Class Declaration Blocks
 */
public class ThreadDecl extends Absyn.ClassDecl{

    public java.lang.String name;
    public java.util.LinkedList<Absyn.VarDecl> fields;
    public java.util.LinkedList<Absyn.MethodDecl> methods;

    public ThreadDecl(java.lang.String name, java.util.LinkedList<Absyn.VarDecl> fields, java.util.LinkedList<Absyn.MethodDecl> methods)
    {   this.name = name;   this.fields = fields;   this.methods = methods;   }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        v.visit(this);
        return;
    }

}
