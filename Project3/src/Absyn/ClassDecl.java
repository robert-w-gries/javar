package Absyn;

/**
 * Class Declaration Blocks
 */
public class ClassDecl extends Absyn.Absyn{

    public java.util.LinkedList<Absyn.VarDecl> fields;
    public java.util.LinkedList<Absyn.MethodDecl> methods;
    public java.lang.String name;
    public java.lang.String parent;

    public ClassDecl(java.lang.String name, java.lang.String parent, java.util.LinkedList<Absyn.VarDecl> fields, java.util.LinkedList<Absyn.MethodDecl> methods){
        this.name = name;
        this.parent = parent;
        this.fields = fields;
        this.methods = methods 
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        v.visit(this);
        return;
    }

}
