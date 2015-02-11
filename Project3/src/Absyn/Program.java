package Absyn;
/**
 * Goal ::= MainClass ( ClassDeclaration )*
 */
public class Program extends Absyn{

    public java.util.AbstractList<ClassDecl> classes;

    public Program(java.util.AbstractList<ClassDecl> classes){
        super();
        this.classes = classes;
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Visitor v){
        v.visit(this);
        return;
    }

}
