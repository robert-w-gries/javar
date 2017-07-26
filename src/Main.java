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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {

    private static Arch targetArch;

    private static void usage() {
        System.err.println("Usage: --mips [inputfiles]");
        System.exit(-2);
    }

    private static void printArchitectures() {
        System.err.println("Incorrect target architecture specified. Please select from:");
        System.err.println("Mips");
        System.exit(-2);
    }

    public static void main(String args[]) throws ParseException {

        // need target architecture and at least one input file
        if (args.length < 2) {
            usage();
        }

        // check for command options
        ArrayList<String> fileArgs = new ArrayList<>();
        for (String arg : args) {

            switch (arg) {

                // set target architecture of compiler
                case "--mips":
                    targetArch = new MipsArch();
                    break;

                default:
                    fileArgs.add(arg);
                    break;

            }

        }

        // Error if target architecture not selected
        if (targetArch == null) {
            usage();
            printArchitectures();
        }

        Path cwd = Paths.get(System.getProperty("user.dir"));


        // compile each file
        for (String file : fileArgs) {

            // read the source file
            Reader reader = null;
            try {
                Path path = cwd.resolve(file);
                reader = new FileReader(path.toString());
            } catch (FileNotFoundException fnfe) {
                System.err.println("File Not Found: " + file);
                System.exit(-1);
            }

            // parse source code
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
