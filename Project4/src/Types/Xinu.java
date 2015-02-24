package Types;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 2/23/15
 * Time: 8:48 PM
 */
public class Xinu extends CLASS {

    public Xinu() {
        super("Xinu");
        this.fields = new RECORD();
        this.methods = new RECORD();
        this.methods.put(new FUNCTION("readint", this, new RECORD(), new INT()), "readint");
        RECORD printintParams = new RECORD();
        printintParams.put(new INT(), "number");
        this.methods.put(new FUNCTION("printint", this, printintParams, new VOID()), "printint");
        RECORD printParams = new RECORD();
        printParams.put(new STRING(), "string");
        this.methods.put(new FUNCTION("print", this, printParams, new VOID()), "print");
        this.methods.put(new FUNCTION("println", this, new RECORD(), new VOID()), "println");
        this.methods.put(new FUNCTION("yield", this, new RECORD(), new VOID()), "yield");
        RECORD sleepParams = new RECORD();
        sleepParams.put(new INT(), "milliseconds");
        this.methods.put(new FUNCTION("sleep", this, sleepParams, new VOID()), "sleep");
        RECORD threadCreateParams = new RECORD();
        threadCreateParams.put(new CLASS("Thread"), "thread");
        this.methods.put(new FUNCTION("threadCreate", this, threadCreateParams, new VOID()), "threadCreate");
    }

}
