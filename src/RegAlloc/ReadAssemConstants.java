/* Generated By:JavaCC: Do not edit this line. ReadAssemConstants.java */
package RegAlloc;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface ReadAssemConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int DOT = 6;
  /** RegularExpression Id. */
  int LPAREN = 7;
  /** RegularExpression Id. */
  int RPAREN = 8;
  /** RegularExpression Id. */
  int LANGLE = 9;
  /** RegularExpression Id. */
  int RANGLE = 10;
  /** RegularExpression Id. */
  int COMMA = 11;
  /** RegularExpression Id. */
  int COLON = 12;
  /** RegularExpression Id. */
  int DQUOTE = 13;
  /** RegularExpression Id. */
  int ALIGN = 14;
  /** RegularExpression Id. */
  int ASCIZ = 15;
  /** RegularExpression Id. */
  int DATA = 16;
  /** RegularExpression Id. */
  int WORD = 17;
  /** RegularExpression Id. */
  int DATAFRAG = 18;
  /** RegularExpression Id. */
  int PROCFRAG = 19;
  /** RegularExpression Id. */
  int MIPSFRAME = 20;
  /** RegularExpression Id. */
  int ACTUALS = 21;
  /** RegularExpression Id. */
  int FORMALS = 22;
  /** RegularExpression Id. */
  int BADPTR = 23;
  /** RegularExpression Id. */
  int BADSUB = 24;
  /** RegularExpression Id. */
  int INREG = 25;
  /** RegularExpression Id. */
  int INFRAME = 26;
  /** RegularExpression Id. */
  int MAXARG = 27;
  /** RegularExpression Id. */
  int MOVE = 28;
  /** RegularExpression Id. */
  int OPER = 29;
  /** RegularExpression Id. */
  int LABEL = 30;
  /** RegularExpression Id. */
  int DEFS = 31;
  /** RegularExpression Id. */
  int USES = 32;
  /** RegularExpression Id. */
  int JUMPS = 33;
  /** RegularExpression Id. */
  int NUM = 34;
  /** RegularExpression Id. */
  int ID = 35;
  /** RegularExpression Id. */
  int STRINGVAL = 36;

  /** Lexical state. */
  int DEFAULT = 0;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\t\"",
    "\"\\n\"",
    "\"\\r\"",
    "\"\\f\"",
    "\".\"",
    "\"(\"",
    "\")\"",
    "\"<\"",
    "\">\"",
    "\",\"",
    "\":\"",
    "\"\\\"\"",
    "\"align\"",
    "\"asciiz\"",
    "\"data\"",
    "\"word\"",
    "\"DataFrag\"",
    "\"ProcFrag\"",
    "\"MipsFrame\"",
    "\"Actuals\"",
    "\"Formals\"",
    "\"BadPtr\"",
    "\"BadSub\"",
    "\"InReg\"",
    "\"InFrame\"",
    "\"maxArgOffset\"",
    "\"MOVE\"",
    "\"OPER\"",
    "\"LABEL\"",
    "\"defs\"",
    "\"uses\"",
    "\"jumps\"",
    "<NUM>",
    "<ID>",
    "<STRINGVAL>",
  };

}