import arch.Arch;
import arch.mips.MipsArch;
import frontend.parse.ast.Program;
import backend.assem.Instr;
import frontend.parse.MiniJavaParser;
import frontend.translate.Translate;
import frontend.typecheck.TypeChecker;
import frontend.translate.Frag;
import frontend.parse.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {

    private static Arch targetArch;

    private static void usage() {
        System.err.println("Usage: java Main --target targetArch [inputfiles]");
        System.exit(-2);
    }

    private static void printArchitectures() {
        System.err.println("Incorrect target architecture specified. Please select from:");
        System.err.println("Mips");
        System.exit(-2);
    }

    public static void main(String args[]) throws ParseException {

        // need target architecture and at least one input file
        if (args.length < 3) {
            usage();
        }

        // check for command options
        ArrayList<String> fileArgs = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {

            switch (args[i]) {

                // set target architecture of compiler
                case "--target":
                    // make sure an argument follows the option
                    if (args.length < i+1) {
                        usage();
                    }

                    if (args[i+1].equalsIgnoreCase("mips")) {
                        targetArch = new MipsArch();
                    } else {
                        printArchitectures();
                    }

                    i++;
                    break;

                default:
                    fileArgs.add(args[i]);
                    break;

            }

        }

        // Error if target architecture not selected
        if (targetArch == null) {
            usage();
        }

        // compile each file
        for (String file : fileArgs) {

            // read the source file
            Reader reader = null;
            try {
                reader = new FileReader(file);
            } catch (FileNotFoundException fnfe) {
                System.err.println("File Not Found: " + file);
                System.exit(-1);
            }

            // parse source file
            new MiniJavaParser(reader);
            Program program = MiniJavaParser.Goal();

            // type check the program
            new TypeChecker().visit(program);

            // translate the program to intermediate representation
            Translate translate = new Translate();
            translate.visit(program);
            LinkedList<Frag> frags = (LinkedList<Frag>) translate.results();

            // backend assembly
            List<Instr> finalProgram = targetArch.assemble(frags);

            //Print out final program
            PrintWriter writer = new PrintWriter(System.out);
            finalProgram.forEach(writer::println);
            writer.flush();

        }

    }

}
