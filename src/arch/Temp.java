package arch;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 3/22/15
 * Time: 11:17 PM
 */
public class Temp {

    private static int regCount = 32;
    private int regIndex;

    public Temp() {
        regIndex = regCount++;
    }

    public Temp(int reg) {
        regIndex = reg;
    }

    public String toString() {
        return "t" + Integer.toString(this.regIndex);
    }

    public int getRegIndex() {
        return this.regIndex;
    }

    public void setRegIndex(int regIndex) {
        this.regIndex = regIndex;
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
