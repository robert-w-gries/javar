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
        isMoveRelated = false;
    }

    public Temp(int reg) {
        regIndex = reg;
        isMoveRelated = false;
    }

    public String toString() {
        return "t" + Integer.toString(this.regIndex);
    }

    public boolean isMoveRelated() { return isMoveRelated; }

    public void setMoveRelated(boolean m) {
        isMoveRelated = m;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Temp)) return false;

        Temp t = (Temp)o;

        return regIndex == t.regIndex;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(regIndex);
    }

}
