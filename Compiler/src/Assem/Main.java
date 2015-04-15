/* Copyright (C) 2009, Dennis Brylow
 * All rights reserved.  */

package Assem;

import Absyn.Program;
import Parse.MiniJavaParser;
import Semant.TypeChecker;
import Translate.Frag;

import java.io.*;

import java.util.List;
import java.util.LinkedList;

public class Main {
    private static boolean assemOutput = false,
            canonOutput = false,
            blockOutput = false,
            traceOutput = false,
            prologOutput = false;

    public static void usage() {
        System.err.println("Usage:  [-options] (file.java | -)");
        System.err.println("\twhere options include:");
        System.err.println("\t-a\tOutput Assembly stage");
        System.err.println("\t-b\tOutput Basic Block stage");
        System.err.println("\t-c\tOutput Canonical Linearization stage");
        System.err.println("\t-p\tOutput Prologue / Epilogue stage");
        System.err.println("\t-t\tOutput Trace Scheduling stage");
        System.exit(-2);
    }

    public static void main(String args[]) throws Parse.ParseException {
        Reader reader = null;
        Program program = null;
        Translate.Translate translate = null;

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
                new MiniJavaParser(reader);
                program = MiniJavaParser.Goal(); // parse MiniJava source file
                new TypeChecker().visit(program); // typecheck the output program
                translate = new Translate.Translate();
                translate.visit(program); // translate the program to IR tree
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

        if (program == null) {
            try {
                new ReadFrags(reader);
                frags = ReadFrags.Program();
            } catch (ParseException p) {
                System.err.println(p.toString());
                System.exit(-1);
            }
        } else {
            frags = (LinkedList<Frag>)translate.results();
        }

        process_frags(frags);
    }

    private static void process_frags(LinkedList<Translate.Frag> frags) {
        PrintWriter writer = new PrintWriter(System.out);

        for (Translate.Frag frag : frags) {
            if (frag instanceof Translate.DataFrag) {
                writer.println("DataFrag(");
                writer.println(frag);
                writer.println(")");
            } else {
                Translate.ProcFrag p = (Translate.ProcFrag)frag;
                List<Tree.Stm> traced = new LinkedList<Tree.Stm>();
                if (p.body != null) {
                    LinkedList<Tree.Stm> stms = Canon.Canon.linearize(p.body);
                    if (canonOutput) {
                        writer.println("/* **** CANONICAL TREES **** */");
                        for (Tree.Stm s : stms) {
                            new Tree.Print(writer, s);
                            writer.println();
                        }
                        writer.flush();
                    }
                    Canon.BasicBlocks blocks = new Canon.BasicBlocks(stms);
                    if (blockOutput) {
                        writer.println("/* **** BASIC BLOCKS **** */");
                        for (LinkedList<Tree.Stm> slist : blocks.blocks) {
                            for (Tree.Stm s : slist) {
                                new Tree.Print(writer, s);
                                writer.println();
                            }
                        }
                        new Tree.Print(writer, new Tree.LABEL(blocks.done));
                        writer.flush();
                    }
                    new Canon.TraceSchedule(blocks, traced);
                    if (traceOutput) {
                        writer.println("/* **** TRACE SCHEDULED **** */");
                        for (Tree.Stm s : traced) {
                            new Tree.Print(writer, s);
                            writer.println();
                        }
                        writer.flush();
                    }
                }
                p.frame.procEntryExit1(traced);
                if (prologOutput) {
                    writer.println("/* **** PROLOGUE / EPILOGUE **** */");
                    for (Tree.Stm s : traced) {
                        new Tree.Print(writer, s);
                        writer.println();
                    }
                    writer.flush();
                }
                List<Assem.Instr> code = p.frame.codeGen(traced); // TODO implement this
                if (assemOutput) {
                    for (Assem.Instr i : code) {
                        writer.println(i);
                    }
                }
                writer.println("ProcFrag(");
                p.frame.printFrame(writer); // TODO implement this
                for (Assem.Instr i : code) {
                    i.output(writer);
                }
                writer.println(")");
            }
        }
        writer.flush();
    }
}	

