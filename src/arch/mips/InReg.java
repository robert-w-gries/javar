package arch.mips;

import arch.Access;
import arch.Temp;
import frontend.translate.irtree.TEMP;
import frontend.translate.irtree.Exp;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 3/22/15
 * Time: 11:16 PM
 */
public class InReg extends Access {

    Temp reg;

    public InReg(Temp reg) {
        this.reg = reg;
    }

    public Exp exp(Exp framePtr) {
        return new TEMP(reg);
    }

    public String toString(){
        return "InReg(" + reg.toString() + ")";
    }

}
