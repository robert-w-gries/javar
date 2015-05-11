package frontend.parse.ast;

import frontend.translate.Exp;
import frontend.translate.Translate;

/**
 * Statement abstract class.
 */
public abstract class Stmt extends Absyn{

    public Stmt(){

    }

    /**
     * Visitor pattern dispatch.
     */
    public abstract void accept(Visitor v);

    public abstract Exp accept(Translate t);
}
