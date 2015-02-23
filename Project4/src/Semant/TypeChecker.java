package Semant;

import java.util.*;

import Absyn.Formal;
import Symbol.SymbolTable;
import Types.*;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 2/22/15
 * Time: 7:17 PM
 */
public class TypeChecker implements Absyn.Visitor {

    public enum Pass {
        PUT_CLASSES,                // first pass
        PUT_PARENT_METHOD_FIELD,    // second pass
        CYCLIC_INHERITANCE,         // third pass
        CREATE_INSTANCE,            // fourth pass
        METHOD_SCOPE_CHECK,         // fifth pass
        DONE
    }

    // The TypeChecker will use a state machine to change the behavior for each pass
    // The first pass only has to go as deep as the class declarations because it just needs the names
    // The second pass will need the class declarations, field declarations, and method declarations
    // The third pass actually just needs the class table to check for cyclic inheritance
    // The fourth pass will just need the class table because all the class information is now in the table
    // The fifth pass will go through the entire AST and all its nodes
    // We may have to consider implementing Types.Visitor as well
    private Pass state;

    private HashMap<String, Type> classTable;
    private SymbolTable scopeTable;

    private CLASS currentClass;
    private FIELD currentField;
    private Types.Type currentType;

    public TypeChecker() {
        classTable = new HashMap<String, Type>();
        classTable.put("String", (Type)new STRING());
        classTable.put("Thread",
        scopeTable = new SymbolTable();
    }

    private void nextState() {
        state = Pass.values()[state.ordinal() + 1];
    }

    private void cyclicInheritanceCheck() {
        HashSet<CLASS> brokenClasses = new HashSet<CLASS>();
        boolean error = false;

        for (Type t : classTable.values()) {
            if (t instanceof STRING) continue;
            CLASS cls = (CLASS)t;
            if (brokenClasses.contains(cls)) continue;
            HashSet<CLASS> parentChain = new HashSet<CLASS>();
            for (CLASS prnt = cls; prnt.parent != null; prnt = prnt.parent) {
                if (parentChain.contains(prnt.parent)) {
                    for (CLASS broke : parentChain) {
                        brokenClasses.add(broke);
                        System.err.println("ERROR cyclic inheritance involving " + broke.name);
                    }
                    error = true;
                    break;
                }
                parentChain.add(prnt);
            }
        }

        if (error) System.exit(0);
    }

    private void createInstances() {
        for (Type t : classTable.values()) {
            if (t instanceof STRING) continue;
            CLASS cls = (CLASS)t;
            createInstance(cls);
        }
    }

    private void createInstance(CLASS cls) {
        if (cls.parent != null && cls.parent.instance == null) createInstance(cls.parent);
        cls.instance = new OBJECT(cls);
        RECORD thisFields = new RECORD(cls.fields);
        RECORD thisMethods = new RECORD(cls.methods);
        if (cls.parent != null) {
            thisFields.putAll(cls.parent.instance.fields);
            RECORD superMethods = getNonOverriddenMethods(cls.parent.instance.methods, cls.methods, cls.name);
            thisMethods.putAll(superMethods);
        }
        cls.instance.fields = thisFields;
        cls.instance.methods = thisMethods;
    }

    private RECORD getNonOverriddenMethods(RECORD parentMethods, RECORD thisMethods, String className) {
        RECORD returnMethods = new RECORD();

        for (FIELD method : parentMethods) {
            FIELD override = thisMethods.get(method.name);
            if (override != null) {
                if (!method.type.equals(override.type))
                    errorAndExit("ERROR incompatible method override: " + override.name + " in class " + className);
            } else {
                returnMethods.put(method.type, method.name);
            }
        }

        return returnMethods;
    }

    private void errorAndExit(String error) {
        System.err.println(error);
        System.exit(0);
    }

    // top level visits

    @Override
    public void visit(Absyn.Program ast) {
        for (state = Pass.PUT_CLASSES; state != Pass.DONE; nextState()) {
            switch (state) {
                case PUT_CLASSES:
                case PUT_PARENT_METHOD_FIELD:
                case METHOD_SCOPE_CHECK:
                    visit(ast.classes);
                    break;
                case CYCLIC_INHERITANCE:
                    cyclicInheritanceCheck();
                    break;
                case CREATE_INSTANCE:
                    createInstances();
                    break;
            }
        }
    }

    @Override
    public void visit(AbstractList list) {
        for (Object obj : list) {
            ((Absyn.Absyn)obj).accept(this);
        }
    }

    // declarations

    @Override
    public void visit(Absyn.ClassDecl ast) {
        CLASS cls;
        switch (state) {
            case PUT_CLASSES:
                if (classTable.get(ast.name) != null) errorAndExit("ERROR duplicate class");
                cls = new CLASS(ast.name);
                cls.fields = new RECORD();
                cls.methods = new RECORD();
                classTable.put(ast.name, new CLASS(ast.name));
                break;
            case PUT_PARENT_METHOD_FIELD:
                if (classTable.get(ast.parent) == null) errorAndExit("ERROR cannot resolve parent class " + ast.parent);
                cls = (CLASS)classTable.get(ast.name);
                cls.parent = (CLASS)classTable.get(ast.parent);
                currentClass = cls;
                visit(ast.fields);
                visit(ast.methods);
                break;
            case METHOD_SCOPE_CHECK:
                visit(ast.methods);
                break;
        }
    }

    @Override
    public void visit(Absyn.ThreadDecl ast) {
        // there is no difference between the behavior of a Thread and a Class for this project
        visit((Absyn.ClassDecl)ast);
    }

    @Override
    public void visit(Absyn.VarDecl ast) {
        switch (state) {
            case PUT_PARENT_METHOD_FIELD:
                if (currentClass.fields.get(ast.name) != null)
                    errorAndExit(ast.name + " is already defined in " + currentClass.name);
                ast.type.accept(this); // sets currentType equal to the type of this field
                currentClass.fields.put(currentType, ast.name);
                break;
            case METHOD_SCOPE_CHECK:
                // i have no clue how the method scope check is going to work yet
                // TODO cry
                break;
        }
    }

    @Override
    public void visit(Absyn.MethodDecl ast) {
        switch (state) {
            case PUT_PARENT_METHOD_FIELD:
                if (currentClass.fields.get(ast.name) != null)
                    errorAndExit(ast.name + " is already defined in " + currentClass.name);
                ast.returnType.accept(this);
                Type returnType = currentType;
                RECORD params = new RECORD();
                for (Formal formal : ast.params) {
                    visit(formal);
                    params.put(currentType, formal.name);
                }
                FUNCTION func = new FUNCTION(ast.name, currentClass, params, returnType);
                currentClass.methods.put(func, ast.name);
                break;
            case METHOD_SCOPE_CHECK:
                // i have no clue how the method scope check is going to work yet
                // TODO cry
                break;
        }
    }

    @Override
    public void visit(Absyn.VoidDecl ast) {
        switch (state) {
            case PUT_PARENT_METHOD_FIELD:
                if (currentClass.fields.get(ast.name) != null)
                    errorAndExit(ast.name + " is already defined in " + currentClass.name);
                FUNCTION func = new FUNCTION(ast.name, currentClass, new RECORD(), new VOID());
                currentClass.methods.put(func, ast.name);
                break;
            case METHOD_SCOPE_CHECK:
                // i have no clue how the method scope check is going to work yet
                // TODO cry
                break;
        }
    }

    @Override
    public void visit(Absyn.Formal ast) {
        ast.type.accept(this);
    }

    // types

    @Override
    public void visit(Absyn.ArrayType ast) {
        ast.baseType.accept(this); // accept the base type, which could be another array
        currentType = new ARRAY(currentType); // wrap the current type in an array
    }

    @Override
    public void visit(Absyn.IntegerType ast) {
        currentType = new INT();
    }

    @Override
    public void visit(Absyn.IdentifierType ast) {
        if (classTable.get(ast.id) == null) errorAndExit("Cannot resolve class " + ast.id);
        currentType = classTable.get(ast.id);
    }

    @Override
    public void visit(Absyn.BooleanType ast) {
        currentType = new BOOLEAN();
    }

    // statements

    @Override
    public void visit(Absyn.XinuCallStmt ast) {
    }

    @Override
    public void visit(Absyn.WhileStmt ast) {
    }

    @Override
    public void visit(Absyn.IfStmt ast) {
    }

    @Override
    public void visit(Absyn.BlockStmt ast) {
    }

    @Override
    public void visit(Absyn.AssignStmt ast) {
    }

    // expressions

    @Override
    public void visit(Absyn.AddExpr ast) {
    }

    @Override
    public void visit(Absyn.AndExpr ast) {
    }

    @Override
    public void visit(Absyn.ArrayExpr ast) {
    }

    @Override
    public void visit(Absyn.XinuCallExpr ast) {
    }

    @Override
    public void visit(Absyn.TrueExpr ast) {
    }

    @Override
    public void visit(Absyn.ThisExpr ast) {
    }

    @Override
    public void visit(Absyn.SubExpr ast) {
    }

    @Override
    public void visit(Absyn.StringLiteral ast) {
    }

    @Override
    public void visit(Absyn.NullExpr ast) {
    }

    @Override
    public void visit(Absyn.OrExpr ast) {
    }

    @Override
    public void visit(Absyn.NotExpr ast) {
    }

    @Override
    public void visit(Absyn.NotEqExpr ast) {
    }

    @Override
    public void visit(Absyn.NewObjectExpr ast) {
    }

    @Override
    public void visit(Absyn.NewArrayExpr ast) {
    }

    @Override
    public void visit(Absyn.NegExpr ast) {
    }

    @Override
    public void visit(Absyn.MulExpr ast) {
    }

    @Override
    public void visit(Absyn.LesserExpr ast) {
    }

    @Override
    public void visit(Absyn.IntegerLiteral ast) {
    }

    @Override
    public void visit(Absyn.IdentifierExpr ast) {
    }

    @Override
    public void visit(Absyn.GreaterExpr ast) {
    }

    @Override
    public void visit(Absyn.FieldExpr ast) {
    }

    @Override
    public void visit(Absyn.EqualExpr ast) {
    }

    @Override
    public void visit(Absyn.FalseExpr ast) {
    }

    @Override
    public void visit(Absyn.DivExpr ast) {
    }

    @Override
    public void visit(Absyn.CallExpr ast) {
    }
}
