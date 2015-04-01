package Mips;

import Frame.Frame;
import Frame.Access;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 3/22/15
 * Time: 11:16 PM
 */
public class MipsFrame extends Frame {

    private Temp.Temp framePointer;

    public MipsFrame() {
        framePointer = new Temp.Temp();
        name = new Temp.Label();
        formals = new LinkedList<Access>();
        actuals = new LinkedList<Access>();
    }

    public Temp.Temp FP() {

        return framePointer;

    }

    public int wordSize() {

        return 4;

    }

    public Access allocFormal() {

        //TODO
        return new InReg();

    }

    public Access allocLocal() {

        //TODO
        return new InReg();

    }

}
