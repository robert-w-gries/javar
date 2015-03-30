package Temp;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 3/22/15
 * Time: 11:17 PM
 */
public class Temp {

    private static int regCount = 0;
    private int regIndex;

    public Temp() {
        regIndex = regCount++;
    }

    public String toString() {
        return "t" + Integer.toString(this.regIndex);
    }

}
