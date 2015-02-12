package Absyn;

import java.io.PrintWriter;

/**
 * Visitor prints AST in reparseable form.
 */
public class PrintVisitor implements Visitor{

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
    public void visit(java.util.AbstractList list) {

        // print abstract list without newline
        // AbstractList can have multiple closing parentheses
        printClass("AbstractList");

        // loop through elements of abstract list and accept them
        for (int i = 0; i < list.size(); i++) {
            printOut.println();
            ((Absyn)list.get(i)).accept(this);
        }

        decrementTab();

        return;
    }

    public void visit(AddExpr ast) {

        printClassLine("AddExpr");

        ast.leftExpr.accept(this);
        printOut.println();
        ast.rightExpr.accept(this);

        decrementTab();

        return;

    }

    public void visit(AndExpr ast) {

        printClassLine("AndExpr");

        ast.leftExpr.accept(this);
        printOut.println();
        ast.rightExpr.accept(this);

        decrementTab();

        return;
    }

    public void visit(ArrayExpr ast) {

        printClassLine("ArrayExpr");

        ast.targetExpr.accept(this);
        printOut.println();
        ast.index.accept(this);

        decrementTab();

        return;
    }

    public void visit(ArrayType ast) {

        printOut.print("ArrayType(");

        ast.baseType.accept(this);

        printOut.print(")");

        return;
    }

    public void visit(AssignStmt ast) {

        printClassLine("AssignStmt");

        ast.leftExpr.accept(this);
        printOut.println();
        ast.rightExpr.accept(this);

        decrementTab();

        return;
    }

    public void visit(BlockStmt ast) {

        printClassLine("BlockStmt");

        visit(ast.stmtList);

        decrementTab();

        return;
    }

    public void visit(BooleanType ast) {

        printOut.print("BooleanType");

        return;
    }

    //TODO: test
    public void visit(CallExpr ast) {

        printClass("CallExpr");

        ast.targetExpr.accept(this);
        printOut.println(ast.methodString);
        visit(ast.argsList);

        return;

    }

    //TODO: test fields
    public void visit(ClassDecl ast) {

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

        decrementTab();

        return;
    }

    // TODO: test multiple division signs in a row
    public void visit(DivExpr ast) {

        printClassLine("DivExpr");

        ast.leftExpr.accept(this);
        printOut.println();
        ast.rightExpr.accept(this);

        decrementTab();

        return;

    }

    public void visit(EqualExpr ast) {

        printClassLine("EqualExpr");

        ast.leftExpr.accept(this);
        printOut.println();
        ast.rightExpr.accept(this);

        decrementTab();

        return;
    }

    public void visit(FalseExpr ast){

        printTabs();
        printOut.print("FalseExpr");

        return;
    }

    public void visit(FieldExpr ast){

        printClassLine("FieldExpr");

        ast.target.accept(this);

        printTabs();
        printOut.print(ast.field);

        decrementTab();

        return;
    }

    public void visit(Formal ast) {

        printClass("Formal");
        ast.type.accept(this);
        printOut.print(" " + ast.name);

        decrementTab();

        return;

    }

    public void visit(GreaterExpr ast){

        printClassLine("GreaterExpr");

        ast.leftExpr.accept(this);
        printOut.println();
        ast.rightExpr.accept(this);

        decrementTab();

        return;
    }

    public void visit(IdentifierExpr ast){

        printTabs();
        printOut.print("IdentifierExpr(" + ast.id + ")");

        return;

    }

    public void visit(IdentifierType ast){

        if (ast.id.equals("public_static_void")) {
            printOut.print(ast.id);
        } else {
            printOut.print("IdentifierType(" + ast.id + ")");
        }

        return;

    }

    public void visit(IfStmt ast){

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

        return;
    }

    public void visit(IntegerLiteral ast){

        printTabs();
        printOut.print("IntegerLiteral(" + ast.value + ")");

        return;
    }

    public void visit(IntegerType ast){

        printOut.print("IntegerType");
        return;

    }

    public void visit(LesserExpr ast){

        printClassLine("LesserExpr");

        ast.leftExpr.accept(this);
        printOut.println();
        ast.rightExpr.accept(this);

        decrementTab();

        return;
    }

    public void visit(MethodDecl ast) {

        // print methodecl without newline
        // MethodDecl prints return type and name of method on same line
        printClass("MethodDecl");

        // print return type
        ast.returnType.accept(this);

        // print method name and start new line
        printOut.print(" " + ast.name);
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

        decrementTab();

        return;

    }

    public void visit(MulExpr ast){

        printClassLine("MulExpr");

        ast.leftExpr.accept(this);
        printOut.println();
        ast.rightExpr.accept(this);

        decrementTab();

        return;

    }

    public void visit(NegExpr ast){

        printClassLine("NegExpr");

        ast.expr.accept(this);

        decrementTab();

        return;

    }

    public void visit(NewArrayExpr ast) {

        printClass("NewArrayExpr");

        ast.type.accept(this);
        printOut.println();

        visit(ast.dimensions);

        decrementTab();

        return;

    }

    public void visit(NewObjectExpr ast){

        printClass("NewObjectExpr");

        ast.type.accept(this);

        decrementTab();

        return;

    }

    public void visit(NotEqExpr ast){

        printClassLine("NotEqExpr");

        ast.leftExpr.accept(this);
        printOut.println();
        ast.rightExpr.accept(this);

        decrementTab();

        return;

    }

    public void visit(NotExpr ast){

        printClassLine("NotExpr");

        ast.expr.accept(this);

        decrementTab();

        return;

    }

    public void visit(NullExpr ast){

        printTabs();
        printOut.print("NullExpr");
        return;

    }

    public void visit(OrExpr ast){

        printClassLine("OrExpr");

        ast.leftExpr.accept(this);
        printOut.println();
        ast.rightExpr.accept(this);

        decrementTab();

        return;

    }

    /**
     * Visitor pattern dispatch.
     */
    public void visit(Program ast){

        // print the visited class
        printClassLine("Program");

        // visit all the classes
        visit(ast.classes);

        // print closing parenthesis and decrement indent
        decrementTab();

        // flush output to console/file and close the printwriter
        printOut.flush();
        printOut.close();

        return;

    }

    public void visit(StringLiteral ast){

        printTabs();
        printOut.print("StringLiteral(" + ast.value + ")");
        return;

    }

    public void visit(SubExpr ast){

        printClassLine("SubExpr");

        ast.leftExpr.accept(this);
        printOut.println();
        ast.rightExpr.accept(this);

        decrementTab();

        return;

    }

    public void visit(ThisExpr ast){

        printTabs();
        printOut.println("ThisExpr");
        return;

    }

    public void visit(ThreadDecl ast){

        printClass("ThreadDecl");
        printOut.print(ast.name + " " + ast.parent);
        printOut.println();

        // print all fields of the class
        visit(ast.fields);
        printOut.println();

        // print all methods of the class
        visit(ast.methods);

        decrementTab();

        return;

    }

    public void visit(TrueExpr ast) {

        printTabs();
        printOut.print("TrueExpr");
        return;

    }

    public void visit(VarDecl ast){

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

        printOut.print(")");

        return;
    }

    public void visit(VoidDecl ast) {

        printClass("VoidDecl");

        printOut.println(ast.name);

        visit(ast.locals);
        printOut.println();
        visit(ast.params);

        decrementTab();

        return;
    }

    //TODO: test after it's fixed
    public void visit(WhileStmt ast){

        printClassLine("WhileStmt");

        ast.test.accept(this);
        printOut.println();
        ast.body.accept(this);

        decrementTab();

        return;

    }

    public void visit(XinuCallExpr ast){

        printClass("XinuCallExpr");

        printOut.println(ast.method);

        visit(ast.args);

        decrementTab();

        return;

    }

    public void visit(XinuCallStmt ast){

        printClass("XinuCallStmt");

        printOut.println(ast.method);

        visit(ast.args);

        decrementTab();

        return;

    }

}
