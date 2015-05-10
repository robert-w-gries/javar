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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Main {
    private static boolean parse = false;

    public static void usage() {
        System.err.println("Usage:  [-options] (file.java | -)");
        System.exit(-2);
    }

    public static void main(String args[]) throws Parse.ParseException {
        Reader reader = null;

        for (String arg : args) {
            switch (arg) {
                case "-":
                    InputStreamReader isr =
                            new InputStreamReader(System.in);
                    reader = new BufferedReader(isr);
                    break;
                case "--parse":
                    parse = true;
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
        }
        if (null == reader) usage();

        LinkedList<Translate.Frag> frags = null;
        if (parse) {
            new MiniJavaParser(reader);
            Program program = MiniJavaParser.Goal(); // parse MiniJava source file
            new TypeChecker().visit(program); // typecheck the output program
            Translate.Translate translate = new Translate.Translate();
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
                if (dropfirst) dropfirst = false;
                else writer.println(frag);
            } else {
                ProcFrag p = (ProcFrag)frag;
                p.frame.procEntryExit2(p.code);
                new RegAlloc(p.frame, p.code);
                p.frame.procEntryExit3(p.code);
                // Remove redundant MOVEs
                for (Iterator<Instr> it = p.code.iterator(); it.hasNext();) {
                    Instr i = it.next();
                    if (i instanceof MOVE && ((MOVE)i).src().equals(((MOVE)i).dst())) it.remove();
                }
                for (Instr i : p.code) {
                    writer.println(i.formatTemp(p.frame));
                }
            }
        }
        writer.flush();
    }

    private static void proccessFrags(LinkedList<Frag> frags) {
        for (Frag frag : frags) {
            if (frag instanceof ProcFrag) {
                Translate.ProcFrag p = (Translate.ProcFrag) frag;
                List<Stm> traced = new LinkedList<>();
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
