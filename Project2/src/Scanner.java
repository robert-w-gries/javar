/**

 Scanner.java

 Performs lexical analysis to convert MiniJava code
 into lexical tokens.

 @author Jake Chitel
 @author Rob Gries
 @author Luke Mivshak

 @version 1.0

 */

public class Scanner {

    public static void main(String[] args) {

        Scanner scanner = new Scanner();
        for (String arg : args) {
            scanner.scan(arg);
        }

    }

    public void scan(String file) {


    }

}