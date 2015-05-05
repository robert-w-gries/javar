package Temp;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 3/22/15
 * Time: 11:17 PM
 */
public class Temp {

    private static int regCount = 32;
    public int regIndex;
    private boolean isMoveRelated;

    public Temp() {
        regIndex = regCount++;
    }

    public Temp(int reg) { regIndex = reg; }

    public String toString() {
        return "t" + Integer.toString(this.regIndex);
    }

    public boolean isMoveRelated() { return isMoveRelated; }

    public void setMoveRelated(boolean m) {
        isMoveRelated = m;
    }

}
