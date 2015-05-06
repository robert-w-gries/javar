package RegAlloc;

import Absyn.Program;
import Assem.Instr;
import Assem.MOVE;
import Mips.MipsFrame;
import Parse.MiniJavaParser;
import Semant.TypeChecker;
import Temp.Temp;
import Translate.DataFrag;
import Translate.Frag;
import Translate.ProcFrag;
import Tree.Stm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

public class Main {
    static final String VERSION = "COSC 170" +
            " Register Allocator" +
            " Spring 2009" +
            " v1.1";

    public static boolean spill = true;
    public static boolean coalesce = true;

    private static boolean assemOutput = false,
            canonOutput = false,
            blockOutput = false,
            traceOutput = false,
            prologOutput = false,
            parse = false;

    public static void usage() {
        System.err.println("Usage:  [-options] (file.java | -)");
        System.err.println("\twhere options include:");
        System.err.println("\t-c\tColoring information dump");
        System.err.println("\t-r\tRedundant MOVEs should remain in");
        System.err.println("\t-v\tPrint version and exit");
        System.exit(-2);
    }

    public static void main(String args[]) throws Parse.ParseException {
        boolean removeMoves = true, colorDump = false;
        Reader reader = null;
        Program program = null;
        Translate.Translate translate = null;

        /*for (String arg : args) {
            switch (arg) {
                case "-v":
                    System.out.println(VERSION);
                    System.exit(0);
                case "-c":
                    colorDump = true;
                    break;
                case "-r":
                    removeMoves = false;
                    break;
                case "-":
                    InputStreamReader isr =
                            new InputStreamReader(System.in);
                    reader = new BufferedReader(isr);
                    break;
                default:
                    try {
                        reader = new FileReader(arg);
                    } catch (FileNotFoundException fnfe) {
                        System.err.println("File Not Found: " + arg);
                        System.exit(-1);
                    }
                    break;
            }
        }*/

        for (String arg : args) {
            if (arg.equals("-a")) {
                assemOutput = true;
            } else if (arg.equals("-b")) {
                blockOutput = true;
            } else if (arg.equals("-c")) {
                canonOutput = true;
            } else if (arg.equals("-p")) {
                prologOutput = true;
            } else if (arg.equals("-t")) {
                traceOutput = true;
            } else if (arg.equals("-")) {
                InputStreamReader isr = new InputStreamReader(System.in);
                reader = new BufferedReader(isr);
            } else if (arg.equals("--parse")) {
                parse = true;
            } else {
                try {
                    reader = new FileReader(arg);
                } catch (FileNotFoundException fnfe) {
                    System.err.println("File Not Found: " + arg);
                    System.exit(-1);
                }
            }
        }
        if (null == reader) usage();

        LinkedList<Translate.Frag> frags = null;
        if (parse) {
            new MiniJavaParser(reader);
            program = MiniJavaParser.Goal(); // parse MiniJava source file
            new TypeChecker().visit(program); // typecheck the output program
            translate = new Translate.Translate();
            translate.visit(program); // translate the program to IR tree
            frags = (LinkedList<Frag>)translate.results();
            proccessFrags(frags);
        } else {
            try {
                new ReadAssem(reader);
                frags = ReadAssem.Program();
            } catch (ParseException p) {
                System.err.println(p.toString());
                System.exit(-1);
            }
        }
        PrintWriter writer = new PrintWriter(System.out);
        writer.println("#include <mips.h>");
        boolean dropfirst = true;
        for (Frag frag : frags) {
            if (frag instanceof DataFrag) {
                if (dropfirst) {
                    dropfirst = false;
                } else {
                    writer.println(frag);
                }
            } else {
                ProcFrag p = (ProcFrag)frag;
                //p.frame.procEntryExit2(p.code); // TODO MipsFrame.procEntryExit2
                RegAlloc alloc = new RegAlloc(p.frame, p.code);
                if (colorDump) {
                    //alloc.show(writer); // TODO RegAlloc.show
                }
                //p.frame.procEntryExit3(p.code); // TODO MipsFrame.procEntryExit3
                // Remove redundant MOVEs
                for (Instr code : p.code) {
                    if (code instanceof MOVE) {
                        Temp src = ((MOVE)code).src();
                        Temp dst = ((MOVE)code).dst();
                        if (MipsFrame.getTempName(src).equals(MipsFrame.getTempName(dst))) {
                            if (removeMoves) p.code.remove(code);
                        }
                    }
                }
                for (Instr i : p.code) {
                    //writer.println(i.format(p.frame, alloc)); // TODO Instr.format
                    writer.println(i.toString());
                }
            }
        }
        writer.flush();
    }

    private static void proccessFrags(LinkedList<Frag> frags) {
        for (Frag frag : frags) {
            if (frag instanceof ProcFrag) {
                Translate.ProcFrag p = (Translate.ProcFrag) frag;
                List<Stm> traced = new LinkedList<Tree.Stm>();
                if (p.body != null) {
                    LinkedList<Tree.Stm> stms = Canon.Canon.linearize(p.body);
                    Canon.BasicBlocks blocks = new Canon.BasicBlocks(stms);
                    new Canon.TraceSchedule(blocks, traced);
                }
                p.frame.procEntryExit1(traced);
                p.code = p.frame.codeGen(traced);
            }
        }
    }

}
