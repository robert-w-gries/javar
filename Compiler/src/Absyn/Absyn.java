package Absyn;

import Translate.*;

/**
 * Parent class of all abstract syntax tree nodes.
 */
public abstract class Absyn implements Visitable {

    Absyn(){}

    /**
     * Visitor pattern dispatch.
     */
    public abstract void accept(Visitor v);

    public Exp accept(Translate t) { return t.visit(this);}

}
