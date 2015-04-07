package Semant;

import java.util.*;

import Absyn.Formal;
import Absyn.TypeVisitor;
import Symbol.SymbolTable;
import Types.*;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 2/22/15
 * Time: 7:17 PM
 */
public class TypeChecker implements TypeVisitor {

    public enum Pass {
        PUT_CLASSES,                // first pass
        PUT_PARENT_METHOD_FIELD,    // second pass
        CYCLIC_INHERITANCE,         // third pass
        CREATE_INSTANCE,            // fourth pass
        METHOD_SCOPE_CHECK,         // fifth pass
        DONE
    }

    private Pass state;

    private SymbolTable<Type> symbolTable;

    public TypeChecker(SymbolTable<CLASS> classes) {
        symbolTable = new SymbolTable<Type>();
        for (CLASS cls : classes.values()) {
            symbolTable.put(cls.name, cls);
        }
    }

    public TypeChecker() {
        symbolTable = new SymbolTable<Type>();
        symbolTable.put("String", new STRING());
        symbolTable.put("Thread", new CLASS("Thread"));
        ((CLASS)symbolTable.get("Thread")).instance = new OBJECT((CLASS)symbolTable.get("Thread"));
        symbolTable.put("Xinu", new Xinu());
    }

    private void nextState() {
        state = Pass.values()[state.ordinal() + 1];
    }

    private void cyclicInheritanceCheck() {
        for (Type t : symbolTable.values()) {
            if (t instanceof STRING) continue;
            HashSet<CLASS> parentChain = new HashSet<CLASS>();
            for (CLASS cls = (CLASS)t; cls.parent != null; cls = cls.parent) {
                if (parentChain.contains(cls.parent)) {
                    errorAndExit("ERROR cyclic inheritance involving " + cls.name);
                }
                parentChain.add(cls);
            }
        }
    }

    private void createInstances() {
        for (Type t : symbolTable.values()) {
            if (t instanceof STRING) continue;
            CLASS cls = (CLASS)t;
            createInstance(cls);
        }
    }

    private void createInstance(CLASS cls) {
        if (cls.parent != null && !cls.parent.instance.initialized) createInstance(cls.parent);
        RECORD thisFields = new RECORD(cls.fields);
        RECORD thisMethods = new RECORD(cls.methods);
        if (cls.parent != null) {
            thisFields.putAll(cls.parent.instance.fields);
            RECORD superMethods = getNonOverriddenMethods(cls.parent.instance.methods, cls.methods, cls.name);
            thisMethods.putAll(superMethods);
        }
        cls.instance.fields = thisFields;
        cls.instance.methods = thisMethods;
        cls.instance.initialized = true;
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

    public static void errorAndExit(String error) {
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
    public List<Type> visit(AbstractList list) {
        List<Type> types = new ArrayList<Type>();
        for (Object obj : list) {
            types.add(((Absyn.Absyn)obj).accept(this));
        }
        return types;
    }

    // declarations

    @Override
    public CLASS visit(Absyn.ClassDecl ast) {
        CLASS cls = null;
        switch (state) {
            case PUT_CLASSES:
                if (symbolTable.get(ast.name) != null) errorAndExit("ERROR duplicate class: " + ast.name);
                symbolTable.put(ast.name, new CLASS(ast.name));
                break;
            case PUT_PARENT_METHOD_FIELD:
                if (ast.parent != null && symbolTable.get(ast.parent) == null)
                    errorAndExit("ERROR cannot resolve parent class: " + ast.parent);
                cls = (CLASS)symbolTable.get(ast.name);
                if (ast.parent != null) cls.parent = (CLASS)symbolTable.get(ast.parent);
                for (Absyn.VarDecl v : ast.fields) {
                    if (cls.fields.get(v.name) != null)
                        errorAndExit(v.name + " is already defined in " + cls.name);
                    cls.fields.put(visit(v), v.name);
                }
                for (Absyn.MethodDecl m : ast.methods) {
                    if (cls.fields.get(m.name) != null)
                        errorAndExit(m.name + " is already defined in " + cls.name);
                    FUNCTION func = (FUNCTION)m.accept(this);
                    func.self = cls.instance;
                    cls.methods.put(func, m.name);
                }
                ast.type = (CLASS)symbolTable.get(ast.name);
                break;
            case METHOD_SCOPE_CHECK:
                cls = (CLASS)symbolTable.get(ast.name);
                symbolTable.beginScope();
                for (FIELD f : cls.instance.fields) {
                    if (symbolTable.get(f.name) == null)
                        symbolTable.put(f.name, f.type);
                }
                for (FIELD f : cls.instance.methods) {
                    symbolTable.put(f.name, f.type);
                }
                visit(ast.methods);
                symbolTable.endScope();
                break;
        }
        return cls;
    }

    @Override
    public CLASS visit(Absyn.ThreadDecl ast) {
        // there is no difference between the behavior of a Thread and a Class for this project
        return visit((Absyn.ClassDecl)ast);
    }

    @Override
    public Type visit(Absyn.VarDecl ast) {
        switch (state) {
            case PUT_PARENT_METHOD_FIELD:
                Type t = ast.type.accept(this);
                ast.semantType = t;
                return t;
            case METHOD_SCOPE_CHECK:
                Type varType = ast.type.accept(this);
                symbolTable.put(ast.name, varType);
                if (ast.init != null) {
                    Type initType = ast.init.accept(this);
                    if (!initType.coerceTo(varType))
                        errorAndExit("ERROR incompatible types: " + varType + " required, but " + initType + " found");
                }
                ast.semantType = varType;
                return varType;
        }
        return null;
    }

    @Override
    public Type visit(Absyn.MethodDecl ast) {
        Type returnType;
        switch (state) {
            case PUT_PARENT_METHOD_FIELD:
                if (ast.returnType == null) {
                    returnType = new VOID();
                } else {
                    returnType = ast.returnType.accept(this);
                }
                RECORD params = new RECORD();
                for (Formal formal : ast.params) params.put(visit(formal), formal.name);
                FUNCTION fun = new FUNCTION(ast.name, null, params, returnType);
                ast.function = fun;
                return fun;
            case METHOD_SCOPE_CHECK:
                symbolTable.beginScope();
                FUNCTION func = (FUNCTION)symbolTable.get(ast.name);
                symbolTable.put("***THIS***", func.self);
                for (Formal f : ast.params) {
                    symbolTable.put(f.name, visit(f));
                }
                visit(ast.locals);
                visit(ast.stmts);
                if (ast.returnType == null) {
                    returnType = new INT();
                } else {
                    returnType = ast.returnType.accept(this);
                }
                Type returnExprType = ast.returnVal.accept(this);
                if (!returnExprType.coerceTo(returnType))
                    errorAndExit("ERROR incompatible types: " + returnType + " required, but " + returnExprType + " found");
                symbolTable.endScope();
                break;
        }
        return null;
    }

    @Override
    public Type visit(Absyn.VoidDecl ast) {
        switch (state) {
            case PUT_PARENT_METHOD_FIELD:
                FUNCTION f = new FUNCTION(ast.name, null, new RECORD(), new VOID());
                ast.function = f;
                return f;
            case METHOD_SCOPE_CHECK:
                symbolTable.beginScope();
                visit(ast.locals);
                visit(ast.stmts);
                symbolTable.endScope();
                break;
        }
        return null;
    }

    @Override
    public Type visit(Absyn.Formal ast) {
        return ast.type.accept(this);
    }

    // types

    @Override
    public ARRAY visit(Absyn.ArrayType ast) {
        return new ARRAY(ast.baseType.accept(this));
    }

    @Override
    public INT visit(Absyn.IntegerType ast) {
        return new INT();
    }

    @Override
    public Type visit(Absyn.IdentifierType ast) {
        Type t = symbolTable.get(ast.id);
        if (t == null) {
            errorAndExit("Cannot resolve class " + ast.id);
            return null;
        }
        if (t instanceof STRING) return t;
        return ((CLASS)t).instance;
    }

    @Override
    public BOOLEAN visit(Absyn.BooleanType ast) {
        return new BOOLEAN();
    }

    // statements

    @Override
    public void visit(Absyn.XinuCallStmt ast) {
        Xinu xinu = (Xinu)symbolTable.get("Xinu");
        if (xinu.methods.get(ast.method) == null)
            errorAndExit("ERROR cannot resolve method " + ast.method);
        FUNCTION xinuFunc = (FUNCTION)xinu.methods.get(ast.method).type;
        RECORD xinuParams = xinuFunc.formals;
        RECORD callParams = new RECORD();
        for (Absyn.Expr expr : ast.args) {
            callParams.put(expr.accept(this), "");
        }
        if (!callParams.coerceTo(xinuParams))
            errorAndExit("ERROR mismatch in number of arguments");
    }

    @Override
    public void visit(Absyn.WhileStmt ast) {
        Type t = ast.test.accept(this);
        if (!(t instanceof BOOLEAN))
            errorAndExit("ERROR incompatible types: boolean required, but " + t + " found");
        ast.body.accept(this);
    }

    @Override
    public void visit(Absyn.IfStmt ast) {
        Type t = ast.test.accept(this);
        if (!(t instanceof BOOLEAN))
            errorAndExit("ERROR incompatible types: boolean required, but " + t + " found");
        ast.thenStm.accept(this);
        if (ast.elseStm != null) {
            ast.elseStm.accept(this);
        }
    }

    @Override
    public void visit(Absyn.BlockStmt ast) {
        symbolTable.beginScope();
        visit(ast.stmtList);
        symbolTable.endScope();
    }

    @Override
    public void visit(Absyn.AssignStmt ast) {
        Type left = ast.leftExpr.accept(this);
        Type right = ast.rightExpr.accept(this);
        if (!right.coerceTo(left))
            errorAndExit("ERROR incompatible types: " + left + " required, but " + right + " found");
    }

    // expressions

    private INT checkBinaryIntOperation(String op, Type left, Type right) {
        if (!right.coerceTo(left) || !left.coerceTo(right) || !(left instanceof INT))
            errorAndExit("ERROR operator "+op+" cannot be applied to "+left+", "+right);
        return new INT();
    }

    private BOOLEAN checkBinaryBooleanOperation(String op, Type left, Type right) {
        if (!right.coerceTo(left) || !left.coerceTo(right) || !(left instanceof BOOLEAN))
            errorAndExit("ERROR operator "+op+" cannot be applied to "+left+", "+right);
        return new BOOLEAN();
    }

    private BOOLEAN checkBinaryEqualityOperation(String op, Type left, Type right) {
        if (!right.coerceTo(left) || !left.coerceTo(right))
            errorAndExit("ERROR operator "+op+" cannot be applied to "+left+", "+right);
        return new BOOLEAN();
    }

    @Override
    public Type visit(Absyn.AddExpr ast) {
        Type left = ast.leftExpr.accept(this);
        Type right = ast.rightExpr.accept(this);
        return checkBinaryIntOperation("+", left, right);
    }

    @Override
    public Type visit(Absyn.AndExpr ast) {
        Type left = ast.leftExpr.accept(this);
        Type right = ast.rightExpr.accept(this);
        return checkBinaryBooleanOperation("&&", left, right);
    }

    @Override
    public Type visit(Absyn.ArrayExpr ast) {
        Type index = ast.index.accept(this);
        if (!(index instanceof INT))
            errorAndExit("ERROR incompatible types: int required, but "+index+" found");
        Type exp = ast.targetExpr.accept(this);
        if (!(exp instanceof ARRAY)) {
            errorAndExit("ERROR incompatible types: array required, but " + exp + " found");
            return null; // won't be reached because errorAndExit exits
        }
        return ((ARRAY)exp).element;
    }

    @Override
    public Type visit(Absyn.XinuCallExpr ast) {
        Xinu xinu = (Xinu)symbolTable.get("Xinu");
        if (xinu.methods.get(ast.method) == null)
            errorAndExit("ERROR cannot resolve method " + ast.method);
        FUNCTION xinuFunc = (FUNCTION)xinu.methods.get(ast.method).type;
        return xinuFunc.result;
    }

    @Override
    public Type visit(Absyn.TrueExpr ast) {
        return new BOOLEAN();
    }

    @Override
    public Type visit(Absyn.ThisExpr ast) {
        return symbolTable.get("***THIS***");
    }

    @Override
    public Type visit(Absyn.SubExpr ast) {
        Type left = ast.leftExpr.accept(this);
        Type right = ast.rightExpr.accept(this);
        return checkBinaryIntOperation("-", left, right);
    }

    @Override
    public Type visit(Absyn.StringLiteral ast) {
        return new STRING();
    }

    @Override
    public Type visit(Absyn.NullExpr ast) {
        return new NIL();
    }

    @Override
    public Type visit(Absyn.OrExpr ast) {
        Type left = ast.leftExpr.accept(this);
        Type right = ast.rightExpr.accept(this);
        return checkBinaryBooleanOperation("||", left, right);
    }

    @Override
    public Type visit(Absyn.NotExpr ast) {
        Type exp = ast.expr.accept(this);
        if (!(exp instanceof BOOLEAN))
            errorAndExit("ERROR operator ! cannot be applied to "+exp);
        return new BOOLEAN();
    }

    @Override
    public Type visit(Absyn.NotEqExpr ast) {
        Type left = ast.leftExpr.accept(this);
        Type right = ast.rightExpr.accept(this);
        return checkBinaryEqualityOperation("!=", left, right);
    }

    @Override
    public Type visit(Absyn.NewObjectExpr ast) {
        return ast.type.accept(this);
    }

    @Override
    public Type visit(Absyn.NewArrayExpr ast) {
        Type base = ast.type.accept(this);
        for (Absyn.Expr exp : ast.dimensions) {
            if (exp != null) {
                Type dim = exp.accept(this);
                if (!(dim instanceof INT))
                    errorAndExit("ERROR incompatible types: int required, but " + dim + " found");
            }
            base = new ARRAY(base);
        }
        return base;
    }

    @Override
    public Type visit(Absyn.NegExpr ast) {
        Type exp = ast.expr.accept(this);
        if (!(exp instanceof INT))
            errorAndExit("ERROR operator - cannot be applied to "+exp);
        return new INT();
    }

    @Override
    public Type visit(Absyn.MulExpr ast) {
        Type left = ast.leftExpr.accept(this);
        Type right = ast.rightExpr.accept(this);
        return checkBinaryIntOperation("*", left, right);
    }

    @Override
    public Type visit(Absyn.LesserExpr ast) {
        Type left = ast.leftExpr.accept(this);
        Type right = ast.rightExpr.accept(this);
        return checkBinaryEqualityOperation("<", left, right);
    }

    @Override
    public Type visit(Absyn.IntegerLiteral ast) {
        return new INT();
    }

    @Override
    public Type visit(Absyn.IdentifierExpr ast) {
        Type t = symbolTable.get(ast.id);
        if (t == null) errorAndExit("ERROR cannot resolve symbol " + ast.id);
        return t;
    }

    @Override
    public Type visit(Absyn.GreaterExpr ast) {
        Type left = ast.leftExpr.accept(this);
        Type right = ast.rightExpr.accept(this);
        return checkBinaryEqualityOperation(">", left, right);
    }

    @Override
    public Type visit(Absyn.FieldExpr ast) {
        Type target = ast.target.accept(this);
        if (target instanceof ARRAY && ast.field.equals("length")) {
            return new INT();
        }
        if (!(target instanceof OBJECT)) {
            errorAndExit("ERROR target not object, type " + target);
            return null; // won't be reached because errorAndExit exits
        }
        OBJECT obj = (OBJECT)target;
        FIELD f = obj.fields.get(ast.field);
        if (f == null) {
            errorAndExit("ERROR cannot resolve symbol " + ast.field);
            return null; // won't be reached because errorAndExit exits
        }
        ast.id = f.index;
        return f.type;
    }

    @Override
    public Type visit(Absyn.EqualExpr ast) {
        Type left = ast.leftExpr.accept(this);
        Type right = ast.rightExpr.accept(this);
        return checkBinaryEqualityOperation("==", left, right);
    }

    @Override
    public Type visit(Absyn.FalseExpr ast) {
        return new BOOLEAN();
    }

    @Override
    public Type visit(Absyn.DivExpr ast) {
        Type left = ast.leftExpr.accept(this);
        Type right = ast.rightExpr.accept(this);
        return checkBinaryIntOperation("/", left, right);
    }

    @Override
    public Type visit(Absyn.CallExpr ast) {
        Type target = ast.targetExpr.accept(this);
        if (!(target instanceof OBJECT)) {
            errorAndExit("ERROR target not object, type " + target);
            return null; // won't be reached because errorAndExit exits
        }
        OBJECT obj = (OBJECT)target;
        FIELD f = obj.methods.get(ast.methodString);
        if (f == null) {
            errorAndExit("ERROR cannot resolve method " + ast.methodString);
            return null; // won't be reached because errorAndExit exits
        }
        ast.id = f.index;
        FUNCTION func = (FUNCTION)f.type;
        RECORD funcParams = func.formals;
        RECORD callParams = new RECORD();
        for (Absyn.Expr expr : ast.argsList) {
            callParams.put(expr.accept(this), "");
        }
        if (!callParams.coerceTo(funcParams))
            errorAndExit("ERROR mismatch in number of arguments");
        return func.result;
    }
}
