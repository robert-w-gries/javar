package Absyn;
/**
 * Goal ::= MainClass ( ClassDeclaration )*
 */
public class Program extends Absyn{
    public java.util.AbstractList<ClassDecl> classes;

    public Program(java.util.AbstractList<ClassDecl> classes){
         //TODO codavaj!!
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Visitor v){
        v.visit(this);
        return;
    }

}
