/* Copyright (C) 1997-2003, Purdue Research Foundation of Purdue University.
 * All rights reserved.  */

package Translate;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
//import Semant.*;
import Absyn.Program;
import Parse.*;
import Parse.ParseException;
import Semant.*;
//import Translate.Translate;

public class Main {

	private static final String PARSE_FLAG = "--parse", CHECK_FLAG = "--check";

	public static void main(String[] args) throws IOException, Parse.ParseException, Semant.ParseException {
		String file = null;
		boolean parse = false, check = false;

		for (String arg : args) {
			if (arg.equals(PARSE_FLAG)) parse = true;
			else if (arg.equals(CHECK_FLAG)) check = true;
			else if (file == null) file = arg;
			else printUsageAndExit();
		}

		if (file == null) printUsageAndExit();

		assert file != null; // this is to get rid of warning in intellij
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

	private static void printUsageAndExit() {
		System.err.println("Invalid usage");
		System.err.println("\tUsage: java Translate.Main [--parse] [--check] file");
		System.exit(0);
	}
}

