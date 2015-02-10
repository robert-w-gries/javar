package Absyn;
/**
 * Goal ::= MainClass ( ClassDeclaration )*
 */
public class Program extends Absyn.Absyn{
    public java.util.AbstractList<Absyn.ClassDecl> classes;

    public Program(java.util.AbstractList<Absyn.ClassDecl> classes){
         //TODO codavaj!!
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        return; //TODO codavaj!!
    }

}
