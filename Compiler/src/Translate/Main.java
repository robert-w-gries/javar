/* Copyright (C) 1997-2003, Purdue Research Foundation of Purdue University.
 * All rights reserved.  */

package Translate;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import Absyn.Program;
import Mips.MipsFrame;
import Parse.MiniJavaParser;
import Semant.ReadAbsyn;
import Semant.TypeChecker;
import Tree.Print;

public class Main {

	private static final String PARSE_FLAG = "--parse", CHECK_FLAG = "--check";

	public static void main(String[] args) throws IOException, Parse.ParseException, Semant.ParseException, ParseException {
		String file = null;
		boolean parse = false, check = false;

		for (String arg : args) {
			if (arg.equals(PARSE_FLAG)) parse = true;
			else if (arg.equals(CHECK_FLAG)) check = true;
			else if (file == null) file = arg; // if file hasn't been set yet, set it
			else printUsageAndExit(); // multiple files were entered
		}

		if (file == null) printUsageAndExit(); // no file was entered

		assert file != null; // this is to get rid of warning in intellij
		FileReader reader = new FileReader(file);
		Program program;
		TypeChecker typeChecker = new TypeChecker();

		if (parse) { // if parse flag was used, we parse and check
			new MiniJavaParser(reader);
			program = MiniJavaParser.Goal(); // parse MiniJava source file
			typeChecker.visit(program); // type check syntax tree, add type info
		} else if (check) { // if only the check flag was used, we only check
			new ReadAbsyn(reader);
			program = ReadAbsyn.Program(); // parse syntax tree file
			typeChecker.visit(program); // type check syntax tree, add type info
		} else { // if no flag was used, we use ReadTypes to parse syntax tree w/ type info
			new ReadTypes(reader);
			program = ReadTypes.Program(); // parse syntax tree file with type info
			typeChecker = new TypeChecker(ReadTypes.classEnv);
		}

		// run program through translator
		Translate translate = new Translate(typeChecker);
		translate.visit(program);

		// print the program back out
		PrintWriter writer = new PrintWriter(System.out);
		for (Frag frag : translate.results()) {
			if (frag instanceof DataFrag) {
				writer.println("DataFrag(");
				writer.println(frag);
				writer.println(")");
			} else {
				writer.println("ProcFrag(");
				ProcFrag proc = (ProcFrag)frag;
				((MipsFrame)proc.frame).printFrame(writer);
				new Print(writer, proc.body);
				writer.println();
				writer.println(")");
			}
		}
		writer.flush();
	}

	private static void printUsageAndExit() {
		System.err.println("Invalid usage");
		System.err.println("\tUsage: java Translate.Main [--parse] [--check] file");
		System.exit(0);
	}
}

