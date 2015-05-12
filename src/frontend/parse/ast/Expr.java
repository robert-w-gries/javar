package frontend.parse.ast;

import frontend.translate.Exp;
import frontend.translate.Translate;

/**
 * Expression abstract class.
 */
public abstract class Expr extends Absyn {
    Expr(){}

    /**
     * Visitor pattern dispatch.
     */
    public abstract void accept(Visitor v);

    public abstract Exp accept(Translate t);

}
