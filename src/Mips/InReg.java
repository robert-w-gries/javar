package Mips;

import Tree.TEMP;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 3/22/15
 * Time: 11:16 PM
 */
public class InReg extends Frame.Access {

    Temp.Temp reg;

    public InReg(Temp.Temp reg) {
        this.reg = reg;
    }

    public Tree.Exp exp(Tree.Exp framePtr) {
        return new Tree.TEMP(reg);
    }

    public String toString(){
        return "InReg(" + reg.toString() + ")";
    }

}
