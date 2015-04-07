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

    public static final Temp.Label _BADPTR = new Temp.Label("_BADPTR");
    public Temp.Label badPtr() { return _BADPTR; }

    public static final Temp.Label _BADSUB = new Temp.Label("_BADSUB");
    public Temp.Label badSub() {
        return _BADSUB;
    }

    public MipsFrame() {
        framePointer = new Temp.Temp(30);
        name = new Temp.Label();
        formals = new LinkedList<Access>();
        actuals = new LinkedList<Access>();
    }

    //TODO Add a RV() method?

    public Temp.Temp FP() { return framePointer; }

    public int wordSize() { return 4; }

    int frameOffset = 0;

    public Access allocFormal() {
        // add a formal
        InReg formal = new InReg(new Temp.Temp()); // formals are always temp registers
        formals.add(formal);

        /*
         * frameOffset starts at 0 and increments by word size for every extra
         * arg past the first 4 stored in the a registers.
         */


        // add an actual
        // there are only 4 "a" registers
        if (argIndex < 4) {
            // "a" registers start at $4
            int tempIndex = (argIndex++) + 4;
            actuals.add(new InReg(new Temp.Temp(tempIndex)));
        } else {
            // otherwise we need to allocate space on the stack for remaining args
            frameOffset += wordSize(); // Amount of bytes past the stack
            actuals.add(new InFrame(frameOffset));
            argIndex++;
        }

        // translation only cares about the formal, not the actual
        return formal;
    }

    public Access allocLocal() {
        // Since we have no escaping variables this is all that happens here
        return new InReg(new Temp.Temp());

        /* // Code to be inserted in the case a boolean arg is required or we need escapes.
           // (Should never happen)

    public Access allocLocal(boolean escape){
        if(escape){
            // This case will not happen in minijava, the passed arg to allocLocal here is a ..
            // .. formality.
            frameOffset += wordSize(); // Basically what happens in the else case in allocFormal
            return new InFrame(frameOffset);
        }else{
            return new InReg(new Temp.Temp());
        }

        */

    }

    public void printFrame(java.io.PrintWriter printOut){
        String tab = "\t\t"; // 8 spaces. Generally 2 tabs but I wont risk it.
        printOut.print(     "MipsFrame(" + "\n" + // Open frame
                            name + ":"   + "\n");
        printOut.print(     "Formals(");

        for (Access curFormal : formals) {
            printOut.println(curFormal);
            printOut.print(tab);
        }

        printOut.print(     ")"    + "\n");
        printOut.print(     "Actuals(");
        
        for (Access curActual : actuals) {
            printOut.println(curActual);
            printOut.print(tab);
        }

        printOut.print(     ")"    + "\n");
        printOut.print(     "BadPtr(" + badPtr() + ")" + "\n");
        printOut.print(     "BadSub(" + badSub() + ")" + "\n");
        printOut.println(     ")"); // Close frame
    }

}
