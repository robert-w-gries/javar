import Absyn.Program;
import Assem.Instr;
import Assem.MOVE;
import Parse.MiniJavaParser;
import RegAlloc.RegAlloc;
import Semant.TypeChecker;
import Translate.DataFrag;
import Translate.Frag;
import Translate.ProcFrag;
import Tree.Stm;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void usage() {
        System.err.println("Usage: java Main file.java");
        System.exit(-2);
    }

    public static void main(String args[]) throws Parse.ParseException {
        if (args.length != 1) usage();

        Reader reader = null;
        try {
            reader = new FileReader(args[0]);
        } catch (FileNotFoundException fnfe) {
            System.err.println("File Not Found: " + args[0]);
            System.exit(-1);
        }

        // parse source file
        new MiniJavaParser(reader);
        Program program = MiniJavaParser.Goal();
        // type check the program
        new TypeChecker().visit(program);
        // translate the program to intermediate representation
        Translate.Translate translate = new Translate.Translate();
        translate.visit(program);
        LinkedList<Translate.Frag> frags = (LinkedList<Frag>)translate.results();
        // canonicalize IR fragments, generate assembly
        proccessFrags(frags);

        // output writer for final assembly
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
                // register allocation
                new RegAlloc(p.frame, p.code);
                p.frame.procEntryExit3(p.code);
                // Remove redundant MOVEs
                for (Iterator<Instr> it = p.code.iterator(); it.hasNext(); ) {
                    Instr i = it.next();
                    if (i instanceof MOVE && ((MOVE)i).src().equals(((MOVE)i).dst())) it.remove();
                }
                // print instructions
                for (Instr i : p.code) {
                    writer.println(i.formatTemp(p.frame));
                }
            }
        }
        writer.flush();
    }

    private static void proccessFrags(LinkedList<Frag> frags) {
        frags.stream().filter(frag -> frag instanceof ProcFrag).forEach(frag -> {
            ProcFrag p = (ProcFrag)frag;
            List<Stm> traced = new LinkedList<>();
            if (p.body != null) {
                LinkedList<Stm> stms = Canon.Canon.linearize(p.body);
                Canon.BasicBlocks blocks = new Canon.BasicBlocks(stms);
                new Canon.TraceSchedule(blocks, traced);
            }
            p.frame.procEntryExit1(traced);
            p.code = p.frame.codeGen(traced);
        });
    }
}
