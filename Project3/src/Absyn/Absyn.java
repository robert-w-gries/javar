package Absyn;

/**
 * Parent class of all abstract syntax tree nodes.
 */
public abstract class Absyn implements Absyn.Visitable {

    public Absyn(){

    }

    /**
     * Visitor pattern dispatch.
     */
    public abstract void accept(Absyn.Visitor v);

}
