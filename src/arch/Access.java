package arch;

import frontend.translate.irtree.Exp;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 3/22/15
 * Time: 11:14 PM
 */
public abstract class Access {

    public abstract Exp exp(Exp framePtr);

}
