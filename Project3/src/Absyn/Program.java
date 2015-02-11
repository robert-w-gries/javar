package Absyn;
/**
 * Goal ::= MainClass ( ClassDeclaration )*
 */
public class Program extends Absyn.Absyn{

    public java.util.AbstractList<Absyn.ClassDecl> classes;

    public Program(java.util.AbstractList<Absyn.ClassDecl> classes)
    {   this.classes = classes;   }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        v.visit(this);
        return;
    }

}
