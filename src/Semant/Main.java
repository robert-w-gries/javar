package Semant;

import Absyn.Program;
import Parse.*;

import java.io.FileReader;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 2/18/15
 * Time: 10:09 PM
 *
 * Use this Main class to either read in an AST from a file and run it through the type checker
 * or optionally, add a --parse flag to the end of the call to read in a java file and run it through the parser
 * before the type checker
 */
public class Main {

    private static final String PARSE_FLAG = "--parse";

    public static void main(String[] args) throws IOException, Parse.ParseException, ParseException {
        String file;
        boolean parse = false;

        if (args.length == 1) {
            file = args[0];
        } else if (args.length == 2 && (args[0].equals(PARSE_FLAG) || args[1].equals(PARSE_FLAG))) {
            file = args[0].equals(PARSE_FLAG) ? args[1] : args[0];
            parse = true;
        } else {
            System.err.println("Invalid usage");
            System.err.println("\tUsage: java Semant.Main [--parse] file");
            return;
        }

        FileReader reader = new FileReader(file);
        Program program;

        if (parse) {
            new MiniJavaParser(reader);
            program = MiniJavaParser.Goal();
        } else {
            new ReadAbsyn(reader);
            program = ReadAbsyn.Program();
        }

        // run program through the type checker
        TypeChecker typeChecker = new TypeChecker();
        typeChecker.visit(program);

        // print the program back out
        PrintVisitor visitor = new PrintVisitor();
        visitor.visit(program);
    }
}
