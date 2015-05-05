package RegAlloc;

import Mips.MipsFrame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.LinkedList;

public class Main {
    static final String VERSION = "COSC 170" +
            " Register Allocator" +
            " Spring 2009" +
            " v1.1";

    public static boolean spill = true;
    public static boolean coalesce = true;

    public static void usage() {
        System.err.println("Usage:  [-options] (file.java | -)");
        System.err.println("\twhere options include:");
        System.err.println("\t-c\tColoring information dump");
        System.err.println("\t-r\tRedundant MOVEs should remain in");
        System.err.println("\t-v\tPrint version and exit");
        System.exit(-2);
    }

    public static void main(String args[]) {
        boolean removeMoves = true, colorDump = false;
        Reader reader = null;
        for (String arg : args) {
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
        }
        if (null == reader) usage();
        LinkedList<Translate.Frag> frags = null;
        try {
            new ReadAssem(reader);
            frags = ReadAssem.Program();
        } catch (ParseException p) {
            System.err.println(p.toString());
            System.exit(-1);
        }
        PrintWriter writer = new PrintWriter(System.out);
        writer.println("#include <mips.h>");
        boolean dropfirst = true;
        for (Translate.Frag frag : frags) {
            if (frag instanceof Translate.DataFrag) {
                if (dropfirst) {
                    dropfirst = false;
                } else {
                    writer.println(frag);
                }
            } else {
                Translate.ProcFrag p = (Translate.ProcFrag)frag;
                p.frame.procEntryExit2(p.code); // TODO MipsFrame.procEntryExit2
                RegAlloc alloc = new RegAlloc(p.frame, p.code);
                if (colorDump) {
                    alloc.show(writer); // TODO RegAlloc.show
                }
                p.frame.procEntryExit3(p.code); // TODO MipsFrame.procEntryExit3
                // Remove redundant MOVEs
                for (java.util.ListIterator<Assem.Instr> code =
                     p.code.listIterator(0);
                     code.hasNext(); ) {
                    Assem.Instr i = code.next();
                    if (i instanceof Assem.MOVE) {
                        Temp.Temp src = ((Assem.MOVE)i).src();
                        Temp.Temp dst = ((Assem.MOVE)i).dst();
                        if (MipsFrame.getTempName(src).equals(MipsFrame.getTempName(dst))) { // TODO RegAlloc.tempMap
                            if (removeMoves) code.remove();
                        }
                    }
                }
                for (Assem.Instr i : p.code) {
                    writer.println(i.format(p.frame, alloc)); // TODO Instr.format
                }
            }
        }
        writer.flush();
    }
}
