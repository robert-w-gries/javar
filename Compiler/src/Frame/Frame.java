package Frame;

import Tree.Stm;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 3/22/15
 * Time: 11:14 PM
 */
public abstract class Frame {

    public Temp.Label name;
    public LinkedList<Access> formals;
    public LinkedList<Access> actuals;

    public abstract Temp.Temp FP();
    public abstract int wordSize();
    public abstract Access allocFormal();
    public abstract Access allocLocal();
    public abstract void printFrame(java.io.PrintWriter printOut);

    /* Methods for Assembler */
    public abstract void procEntryExit1(List<Stm> stms);
    public abstract List<Assem.Instr> codeGen(List<Stm> stms);

}
