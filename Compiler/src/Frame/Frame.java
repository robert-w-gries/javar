package Frame;

import java.util.LinkedList;

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

    abstract public Temp.Temp FP();
    abstract public int wordSize();
    abstract public Access allocFormal();
    abstract public Access allocLocal();

}
