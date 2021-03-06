package arch;

import backend.assem.Instr;
import frontend.translate.irtree.Stm;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 3/22/15
 * Time: 11:14 PM
 */
public abstract class Frame {

    protected Label name;
    protected LinkedList<Access> formals;
    protected LinkedList<Access> actuals;

    public Label getName() {
        return name;
    }

    public void setName(Label name) {
        this.name = name;
    }

    public abstract Temp FP();
    public abstract int wordSize();
    public abstract Access allocFormal();
    public abstract Access allocLocal();
    public abstract void printFrame(java.io.PrintWriter printOut);

    /* Methods for Assembler */
    public abstract void procEntryExit1(List<Stm> stms);
    public abstract void procEntryExit2(List<Instr> instrs);
    public abstract void procEntryExit3(List<Instr> instr);
    public abstract List<Instr> codeGen(List<Stm> stms);
    public abstract boolean isRealRegister(Temp temp);
    public abstract int numRegs();
    public abstract int numColors();

}
