package Absyn;
/**
 * Declarations for formal method parameters.
 */
public class Formal extends Absyn.Absyn{

    public Absyn.Type type;
    public java.lang.String name;

    public Formal(Absyn.Type type, java.lang.String name)
    {   this.type = type;   this.name = name;   }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        v.visit(this);
        return;
    }

}
