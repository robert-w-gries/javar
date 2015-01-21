/**
 * Program.java
 *
 * A program to be interpreted.  Abstract Syntax Tree classes are
 *    defined in Straightline.java, and the interpreter is in
 *    Interpreter.java.  See Appel chapter 1 for details.
 */

public class Program{

    // a := 5 + 3 ; b := ( print ( a , a - 1 ) , 10 * a ) ; print ( b )
    Stm program =
	new CompoundStm(
	    new AssignStm(
		"a",
		new OpExp(
		    new NumExp(5),
		    OpExp.PLUS, 
		    new NumExp(3))),
	    new CompoundStm(
		new AssignStm(
		    "b",
		    new EseqExp(
			new PrintStm(
			    new PairExpList(
				new IdExp("a"),
				new LastExpList(
				    new OpExp(
					new IdExp("a"),
					OpExp.MINUS,
					new NumExp(1))))),
			new OpExp(
			    new NumExp(10), 
			    OpExp.TIMES,
			    new IdExp("a")))),
		new PrintStm(
		    new LastExpList(
			new IdExp("b")))));


} // Program
