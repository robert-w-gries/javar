package Semant;

import java.io.PrintWriter;
import java.util.AbstractList;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 2/18/15
 * Time: 10:42 PM
 */
public class PrintVisitor implements Types.Visitor, Absyn.Visitor {

    /* Absyn.Visitor implementations */

    private java.io.PrintWriter printOut;
    private int numTabs = 0;

    public PrintVisitor(){
        printOut = new PrintWriter(System.out);
    }

    public PrintVisitor(java.io.PrintWriter out){
        printOut = out;
    }

    private void printTabs() {

        // print the indents
        for (int i = 0; i < numTabs; i++) {
            printOut.print(" ");

        }

    }

    private void printClass(String className) {

        printTabs();

        printOut.print(className + "(");
        numTabs++;

    }

    private void printClassLine(String className) {

        printTabs();

        printOut.println(className + "(");
        numTabs++;

    }

    private void decrementTab() {

        numTabs--;
        printOut.print(")");

    }

    /**
     * Description copied from interface:
     * Visitor pattern dispatch.
     */
    public void visit(AbstractList list) {

        // print abstract list without newline
        // AbstractList can have multiple closing parentheses
        printClass("AbstractList");

        // loop through elements of abstract list and accept them
        for (int i = 0; i < list.size(); i++) {
            printOut.println();
            ((Absyn.Absyn)list.get(i)).accept(this);
        }

        decrementTab();
    }

    public void visit(Absyn.AddExpr ast) {

        printClassLine("AddExpr");

        ast.leftExpr.accept(this);
        printOut.println();
        ast.rightExpr.accept(this);

        decrementTab();
    }

    public void visit(Absyn.AndExpr ast) {

        printClassLine("AndExpr");

        ast.leftExpr.accept(this);
        printOut.println();
        ast.rightExpr.accept(this);

        decrementTab();
    }

    public void visit(Absyn.ArrayExpr ast) {

        printClassLine("ArrayExpr");

        ast.targetExpr.accept(this);
        printOut.println();
        ast.index.accept(this);

        decrementTab();
    }

    public void visit(Absyn.ArrayType ast) {

        printOut.print("ArrayType(");

        ast.baseType.accept(this);

        printOut.print(")");
    }

    public void visit(Absyn.AssignStmt ast) {

        printClassLine("AssignStmt");

        ast.leftExpr.accept(this);
        printOut.println();
        ast.rightExpr.accept(this);

        decrementTab();
    }

    public void visit(Absyn.BlockStmt ast) {

        printClassLine("BlockStmt");

        visit(ast.stmtList);

        decrementTab();
    }

    public void visit(Absyn.BooleanType ast) {

        printOut.print("BooleanType");
    }

    public void visit(Absyn.CallExpr ast) {

        printClass("CallExpr");

        printOut.println();
        ast.targetExpr.accept(this);
        printOut.println();
        printTabs();
        printOut.println(ast.methodString);
        visit(ast.argsList);
        printOut.println();
        printTabs();
        printOut.print(ast.id);
        decrementTab();
    }

    public void visit(Absyn.ClassDecl ast) {

        // print classdecl without newline
        // ClassDecl prints name of class and its parent on same line
        printClass("ClassDecl");
        printOut.print(ast.name + " " + ast.parent);
        printOut.println();

        // print all fields of the class
        visit(ast.fields);
        printOut.println();

        // print all methods of the class
        visit(ast.methods);

        //print the type checker class
        printOut.println();
        visit(ast.type);

        decrementTab();
    }

    public void visit(Absyn.DivExpr ast) {

        printClassLine("DivExpr");

        ast.leftExpr.accept(this);
        printOut.println();
        ast.rightExpr.accept(this);

        decrementTab();
    }

    public void visit(Absyn.EqualExpr ast) {

        printClassLine("EqualExpr");

        ast.leftExpr.accept(this);
        printOut.println();
        ast.rightExpr.accept(this);

        decrementTab();
    }

    public void visit(Absyn.FalseExpr ast){

        printTabs();
        printOut.print("FalseExpr");
    }

    public void visit(Absyn.FieldExpr ast){

        printClassLine("FieldExpr");

        ast.target.accept(this);
        printOut.println();

        printTabs();
        printOut.print(ast.field);

        printOut.println();
        printTabs();
        printOut.print(ast.id);
        decrementTab();
    }

    public void visit(Absyn.Formal ast) {

        printClass("Formal");
        ast.type.accept(this);
        printOut.print(" " + ast.name);

        decrementTab();
    }

    public void visit(Absyn.GreaterExpr ast){

        printClassLine("GreaterExpr");

        ast.leftExpr.accept(this);
        printOut.println();
        ast.rightExpr.accept(this);

        decrementTab();
    }

    public void visit(Absyn.IdentifierExpr ast){

        printTabs();
        printOut.print("IdentifierExpr(" + ast.id + ")");
    }

    public void visit(Absyn.IdentifierType ast){

        printOut.print("IdentifierType(" + ast.id + ")");
    }

    public void visit(Absyn.IfStmt ast){

        printClassLine("IfStmt");

        ast.test.accept(this);
        printOut.println();

        ast.thenStm.accept(this);
        printOut.println();

        if (ast.elseStm == null) {
            printTabs();
            printOut.print("null");
        } else {
            ast.elseStm.accept(this);
        }

        decrementTab();
    }

    public void visit(Absyn.IntegerLiteral ast){

        printTabs();
        printOut.print("IntegerLiteral(" + ast.value + ")");
    }

    public void visit(Absyn.IntegerType ast){

        printOut.print("IntegerType");
    }

    public void visit(Absyn.LesserExpr ast){

        printClassLine("LesserExpr");

        ast.leftExpr.accept(this);
        printOut.println();
        ast.rightExpr.accept(this);

        decrementTab();
    }

    public void visit(Absyn.MethodDecl ast) {

        // print methodecl without newline
        // MethodDecl prints return type and name of method on same line
        printClass("MethodDecl");

        // print return type
        if (ast.returnType == null) {
            printOut.print("public_static_void");
        } else {
            ast.returnType.accept(this);
        }

        // print method name and start new line
        printOut.print(" " + ast.name);

        if (ast.synced) {
            printOut.print(" synchronized");
        }

        printOut.println();

        // print all the parameters of the method
        visit(ast.params);
        printOut.println();

        // print all local variables
        visit(ast.locals);
        printOut.println();

        // print all statements
        visit(ast.stmts);
        printOut.println();

        // print the return value
        ast.returnVal.accept(this);

        // print the method's FUNCTION
        printOut.println();
        visit(ast.function);

        decrementTab();
    }

    public void visit(Absyn.MulExpr ast){

        printClassLine("MulExpr");

        ast.leftExpr.accept(this);
        printOut.println();
        ast.rightExpr.accept(this);

        decrementTab();
    }

    public void visit(Absyn.NegExpr ast){

        printClassLine("NegExpr");

        ast.expr.accept(this);

        decrementTab();
    }

    public void visit(Absyn.NewArrayExpr ast) {

        printClass("NewArrayExpr");

        ast.type.accept(this);
        printOut.println();

        visit(ast.dimensions);

        decrementTab();
    }

    public void visit(Absyn.NewObjectExpr ast){

        printClass("NewObjectExpr");

        ast.type.accept(this);

        decrementTab();
    }

    public void visit(Absyn.NotEqExpr ast){

        printClassLine("NotEqExpr");

        ast.leftExpr.accept(this);
        printOut.println();
        ast.rightExpr.accept(this);

        decrementTab();
    }

    public void visit(Absyn.NotExpr ast){

        printClassLine("NotExpr");

        ast.expr.accept(this);

        decrementTab();
    }

    public void visit(Absyn.NullExpr ast){

        printTabs();
        printOut.print("NullExpr");
    }

    public void visit(Absyn.OrExpr ast){

        printClassLine("OrExpr");

        ast.leftExpr.accept(this);
        printOut.println();
        ast.rightExpr.accept(this);

        decrementTab();
    }

    /**
     * Visitor pattern dispatch.
     */
    public void visit(Absyn.Program ast){

        // print the visited class
        printClassLine("Program");

        // visit all the classes
        visit(ast.classes);

        // print closing parenthesis and decrement indent
        decrementTab();

        printOut.println();

        // flush output to console/file and close the printwriter
        printOut.flush();
        printOut.close();
    }

    public void visit(Absyn.StringLiteral ast){

        printTabs();
        printOut.print("StringLiteral(" + ast.value + ")");
    }

    public void visit(Absyn.SubExpr ast){

        printClassLine("SubExpr");

        ast.leftExpr.accept(this);
        printOut.println();
        ast.rightExpr.accept(this);

        decrementTab();
    }

    public void visit(Absyn.ThisExpr ast){

        printTabs();
        printOut.print("ThisExpr");
    }

    public void visit(Absyn.ThreadDecl ast){

        printClass("ThreadDecl");
        printOut.print(ast.name + " " + ast.parent);
        printOut.println();

        // print all fields of the class
        visit(ast.fields);
        printOut.println();

        // print all methods of the class
        visit(ast.methods);

        // print the ThreadDecl's CLASS
        visit(ast.type);

        decrementTab();
    }

    public void visit(Absyn.TrueExpr ast) {

        printTabs();
        printOut.print("TrueExpr");
    }

    public void visit(Absyn.VarDecl ast){

        printTabs();
        printOut.print("VarDecl(");

        ast.type.accept(this);
        printOut.print(" " + ast.name);

        if (ast.init == null) {
            printOut.print(" null");
        } else {
            printOut.println();
            ast.init.accept(this);
        }

        // print the VarDecl's type
        printOut.println();
        ast.semantType.accept(this);

        printOut.print(")");
    }

    public void visit(Absyn.VoidDecl ast) {

        printClass("VoidDecl");

        printOut.println(ast.name);

        visit(ast.locals);
        printOut.println();
        visit(ast.params);

        // print this VoidDecl's FUNCTION
        visit(ast.function);

        decrementTab();
    }

    public void visit(Absyn.WhileStmt ast){

        printClassLine("WhileStmt");

        ast.test.accept(this);
        printOut.println();
        ast.body.accept(this);

        decrementTab();
    }

    public void visit(Absyn.XinuCallExpr ast){

        printClass("XinuCallExpr");

        printOut.println(ast.method);

        visit(ast.args);

        decrementTab();
    }

    public void visit(Absyn.XinuCallStmt ast){

        printClass("XinuCallStmt");

        printOut.println(ast.method);

        visit(ast.args);

        decrementTab();
    }

    /* Types.Visitor implementations */

    private boolean printFullObject = false;

    @Override
    public void visit(Types.ARRAY a) {
        printClassLine("ARRAY");
        a.element.accept(this);
        decrementTab();
    }

    @Override
    public void visit(Types.BOOLEAN b) {
        printOut.print("BOOLEAN");
    }

    @Override
    public void visit(Types.CLASS c) {
        printClass("CLASS");
        printOut.println(c.name);

        printTabs();
        if (c.parent == null) printOut.println("null");
        else printOut.println(c.parent.name);

        visit(c.methods);
        printOut.println();

        printTabs();
        visit(c.fields);
        printOut.println();

        printFullObject = true;
        visit(c.instance);

        decrementTab();
    }

    @Override
    public void visit(Types.FIELD f) {
        printOut.println();
        printClass("FIELD");
        printOut.println(f.index + " " + f.name);
        f.type.accept(this);
        decrementTab();
    }

    @Override
    public void visit(Types.FUNCTION f) {
        printClass("FUNCTION");
        printOut.println(f.name);

        visit(f.self);
        printOut.println();

        visit(f.formals);

        printOut.println();
        f.result.accept(this);

        decrementTab();
    }

    @Override
    public void visit(Types.INT i) {
        printTabs();
        printOut.print("INT");
    }

    @Override
    public void visit(Types.NIL n) {
    }

    @Override
    public void visit(Types.OBJECT o) {
        if (printFullObject) {
            printFullObject = false;
            printClass("OBJECT");
            printOut.println(o.myClass.name);
            visit(o.methods);
            printOut.println();
            visit(o.fields);
            decrementTab();
        } else {
            printClass("OBJECT");
            printOut.print(o.myClass.name);
            decrementTab();
        }
    }

    @Override
    public void visit(Types.RECORD r) {
        printClass("RECORD");
        for (Types.FIELD f : r) {
            visit(f);
        }
        decrementTab();
    }

    @Override
    public void visit(Types.STRING s) {
        printTabs();
        printOut.print("OBJECT(String)");
    }

    @Override
    public void visit(Types.VOID v) {
        printTabs();
        printOut.print("VOID");
    }
}
