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
    private int argIndex = 0;

    public MipsFrame() {
        framePointer = new Temp.Temp(30);
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
        // add a formal
        InReg formal = new InReg(new Temp.Temp()); // formals are always temp registers
        formals.add(formal);

        // add an actual
        // there are only 4 "a" registers
        if (argIndex < 4) {
            // "a" registers start at $4
            int tempIndex = (argIndex++) + 4;
            actuals.add(new InReg(new Temp.Temp(tempIndex)));
        } else {
            // otherwise we need to allocate space on the stack for remaining args
            // TODO figure out what the frame offset is supposed to be
            int frameOffset = 0;
            actuals.add(new InFrame(frameOffset));
        }

        // translation only cares about the formal, not the actual
        return formal;
    }

    public Access allocLocal() {

        //TODO
        return new InReg(new Temp.Temp());

    }

}
