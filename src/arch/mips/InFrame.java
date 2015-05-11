package arch.mips;

import arch.Access;
import frontend.translate.irtree.CONST;
import frontend.translate.irtree.BINOP;
import frontend.translate.irtree.Exp;
import frontend.translate.irtree.MEM;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 3/22/15
 * Time: 11:16 PM
 */
public class InFrame extends Access {

    private int offset;

    public InFrame(int x) {
        offset = x;
    }

    public Exp exp(Exp framePtr) {
        return new MEM(new BINOP(BINOP.Operation.PLUS, framePtr, new CONST(offset)));
    }

    public int getOffset() {
        return offset;
    }

}
