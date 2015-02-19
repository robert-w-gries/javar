package Parse;

import Absyn.PrintVisitor;

import java.io.FileReader;
import java.io.IOException;

/**
 * Created by rgries on 2/11/15.
 */
public class Main {

    public static void main(String[] args) {
        PrintVisitor visitor = new PrintVisitor();

        try {
            MiniJavaParser Parse = new MiniJavaParser(new FileReader(args[0]));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        try {
            visitor.visit(MiniJavaParser.Goal());
        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }
    }
}
