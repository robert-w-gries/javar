package util;

/**
 * Created by rgries on 2/22/15
 */
public class Symbol {

    private String name;
    private static java.util.HashMap<String, Symbol> symDictionary = new java.util.HashMap<>();

    private Symbol(String n) {
        name = n;
    }

    public String toString() {
        return name;
    }

    public static Symbol symbol(String n) {

        // returns a String from the pool of internal string in the String class
        String uniqueString = n.intern();

        // check if the unique String object already exists in the dictionary
        Symbol s = symDictionary.get(uniqueString);
        if (s == null) {
            s = new Symbol(uniqueString);
            symDictionary.put(uniqueString, s);
        }

        // return the symbol, if null then the Symbol already exists in the Symbol Table
        return s;

    }

}
