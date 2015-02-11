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

    private void printClass(String className) {

        // print the indents
        for (int i = 0; i < numTabs; i++) {
            printOut.print(" ");

        }

        printOut.print(className + "(");
        numTabs++;

    }

    private void printClassLine(String className) {

        // print the indents
        for (int i = 0; i < numTabs; i++) {
            printOut.print(" ");

        }

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
    public void visit(java.util.AbstractList list){

        printClass("AbstractList");

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
        ast.rightExpr.accept(this);
        decrementTab();

        return;

    }

    public void visit(AndExpr ast){

        printClassLine("AndExpr");

        ast.leftExpr.accept(this);
        ast.rightExpr.accept(this);
        decrementTab();

        return;
    }

    public void visit(ArrayExpr ast){

        printClassLine("ArrayExpr");

        ast.index.accept(this);
        ast.targetExpr.accept(this);
        decrementTab();

        return;
    }

    public void visit(ArrayType ast){

        printClassLine("ArrayType");

        ast.baseType.accept(this);
        decrementTab();

        return;
    }

    public void visit(AssignStmt ast){

        printClassLine("AssignStmt");

        ast.leftExpr.accept(this);
        ast.rightExpr.accept(this);
        decrementTab();

        return;
    }

    public void visit(BlockStmt ast){

        printClassLine("BlockStmt");

        for (int i = 0; i < ast.stmtList.size(); i++) {
            ast.stmtList.get(i).accept(this);
        }

        decrementTab();

        return;
    }

    public void visit(BooleanType ast){

        printClassLine("BooleanType");

        ast.accept(this);
        decrementTab();

        return;
    }

    public void visit(CallExpr ast){

        printClass("CallExpr");

        return;
    }

    public void visit(ClassDecl ast){

        printClass("ClassDecl");
        printOut.print(ast.name + " " + ast.parent);
        printOut.println();

        for (int i = 0; i < ast.fields.size(); i++) {
            ast.fields.get(i).accept(this);
        }

        for (int i = 0; i < ast.methods.size(); i++) {
            ast.methods.get(i).accept(this);
        }

        decrementTab();

        return;
    }

    public void visit(DivExpr ast){
        return; //TODO codavaj!!
    }

    public void visit(EqualExpr ast){
        return; //TODO codavaj!!
    }

    public void visit(FalseExpr ast){
        return; //TODO codavaj!!
    }

    public void visit(FieldExpr ast){
        return; //TODO codavaj!!
    }

    public void visit(Formal ast){
        return; //TODO codavaj!!
    }

    public void visit(GreaterExpr ast){
        return; //TODO codavaj!!
    }

    public void visit(IdentifierExpr ast){
        return; //TODO codavaj!!
    }

    public void visit(IdentifierType ast){
        return;
    }

    public void visit(IfStmt ast){
        return; //TODO codavaj!!
    }

    public void visit(IntegerLiteral ast){
        return; //TODO codavaj!!
    }

    public void visit(IntegerType ast){
        return; //TODO codavaj!!
    }

    public void visit(LesserExpr ast){
        return; //TODO codavaj!!
    }

    public void visit(MethodDecl ast){

        printClass("MethodDecl");
        printOut.print(ast.returnType + ast.name);

        for (int i = 0; i < ast.params.size(); i++) {
            ast.params.get(i).accept(this);
        }

        decrementTab();

        return;
    }

    public void visit(MulExpr ast){
        return; //TODO codavaj!!
    }

    public void visit(NegExpr ast){
        return; //TODO codavaj!!
    }

    public void visit(NewArrayExpr ast){
        return; //TODO codavaj!!
    }

    public void visit(NewObjectExpr ast){
        return; //TODO codavaj!!
    }

    public void visit(NotEqExpr ast){
        return; //TODO codavaj!!
    }

    public void visit(NotExpr ast){
        return; //TODO codavaj!!
    }

    public void visit(NullExpr ast){
        return; //TODO codavaj!!
    }

    public void visit(OrExpr ast){
        return; //TODO codavaj!!
    }

    /**
     * Visitor pattern dispatch.
     */
    public void visit(Program ast){

        printClassLine("Program");

        visit(ast.classes);

        decrementTab();
        printOut.flush();

        return;

    }

    public void visit(StringLiteral ast){
        return; //TODO codavaj!!
    }

    public void visit(SubExpr ast){
        return; //TODO codavaj!!
    }

    public void visit(ThisExpr ast){
        return; //TODO codavaj!!
    }

    public void visit(ThreadDecl ast){
        return; //TODO codavaj!!
    }

    public void visit(TrueExpr ast){
        return; //TODO codavaj!!
    }

    public void visit(VarDecl ast){
        return; //TODO codavaj!!
    }

    public void visit(VoidDecl ast){
        return; //TODO codavaj!!
    }

    public void visit(WhileStmt ast){
        return; //TODO codavaj!!
    }

    public void visit(XinuCallExpr ast){
        return; //TODO codavaj!!
    }

    public void visit(XinuCallStmt ast){
        return; //TODO codavaj!!
    }

}
