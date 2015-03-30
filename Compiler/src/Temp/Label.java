package Temp;

import Symbol.Symbol;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 3/22/15
 * Time: 11:17 PM
 */
public class Label {

    private static int labelCount = 0;
    private String name;

    public Label() {
        this.name = "L" + labelCount++;
    }

    public Label(String s) {
        this.name = s;
    }

    public String toString() {
        return name;
    }

}
