package frontend.parse;

import java.util.LinkedList;
import frontend.parse.ast.*;

public class MiniJavaParser implements MiniJavaParserConstants {
    private static final class LookaheadSuccess extends Error { }
    private static final class JJCalls {
        int gen;
        Token first;
        int arg;
        JJCalls next;
    }

    /** Generated Token Manager. */
    private final MiniJavaParserTokenManager token_source;
    private final int[] jj_la1 = new int[41];
    private final JJCalls[] jj_2_rtns = new JJCalls[16];
    private final java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
    private final int[] jj_lasttokens = new int[100];

    /** Current token. */
    private Token token;
    /** Next token. */
    private Token jj_nt;
    private int jj_ntk;
    private Token jj_scanpos, jj_lastpos;
    private int jj_la;
    private int jj_gen;
    private boolean jj_rescan = false;
    private int jj_gc = 0;
    private int[] jj_expentry;
    private int jj_kind = -1;
    private int jj_endpos;

    private static final LookaheadSuccess jj_ls = new LookaheadSuccess();

    private static final int[] jj_la1_0 = new int[] {
        0x0,
        0x0,
        0x400,
        0x0,
        0x8000000,
        0x0,
        0x8000000,
        0x8000000,
        0x0,
        0x8000000,
        0x100000,
        0x0,
        0x2000000,
        0x0,
        0x400,
        0x400,
        0x0,
        0x400,
        0x400,
        0x0,
        0x1000000,
        0x800000,
        0x600000,
        0x600000,
        0xc0000,
        0xc0000,
        0x22000,
        0x22000,
        0x18000,
        0x18000,
        0x6000,
        0x4000,
        0x40,
        0x1100,
        0x40,
        0x100,
        0x2000000,
        0x6040,
        0x0,
        0x0,
        0x0,
    };

    private static final int[] jj_la1_1 = new int[] {
        0x1,
        0x1,
        0xc803fa0,
        0x2,
        0x800c000,
        0x800c000,
        0x0,
        0x800c000,
        0x800c000,
        0x0,
        0x0,
        0x8,
        0x0,
        0x800c000,
        0xc803fa0,
        0xc803fa0,
        0x800c000,
        0xc803fa0,
        0xa0,
        0xc803f00,
        0x0,
        0x0,
        0x0,
        0x0,
        0x0,
        0x0,
        0x0,
        0x0,
        0x0,
        0x0,
        0x0,
        0x0,
        0xc803f00,
        0x0,
        0x0,
        0x0,
        0x0,
        0xc803f00,
        0xc801f00,
        0x8000000,
        0x800c000,
    };

    /** Constructor. */
    public MiniJavaParser(java.io.Reader stream) {
        token_source = new MiniJavaParserTokenManager(new SimpleCharStream(stream, 1, 1));
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 41; i++) jj_la1[i] = -1;
        for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
    }

    /**************************************************
     * The MiniJava language grammar starts here      *
     **************************************************/

    // Goal ::= MainClassDeclaration ( ClassDeclaration | ThreadDeclaration )* <EOF>
    public final Program Goal() throws ParseException {
        // Variable declarations go here
        LinkedList<ClassDecl> classList = new LinkedList<ClassDecl>();
        MainClassDeclaration(classList);
        label_1: while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case 32: break;
                default:
                    jj_la1[0] = jj_gen;
                    break label_1;
            }
            if (jj_2_1(4)) {
                ClassDeclaration(classList);
            } else {
                switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                    case 32:
                        ThreadDeclaration(classList);
                        break;
                    default:
                        jj_la1[1] = jj_gen;
                        jj_consume_token(-1);
                        throw new ParseException();
                }
            }
        }
        jj_consume_token(0);
        return new Program(classList);
    }

    // ClassName ::= "class" <ID>
    private final String ClassName() throws ParseException {
        Token className;
        jj_consume_token(32);
        className = jj_consume_token(ID);
        return className.image;
    }

    // MainClassDeclaration ::= ClassName "{" "private" "static" "void" "main" "(" "String" "[" "]" <ID> ")" "{" ( VarDeclaration )* ( Statement )* "}" "}"
    private final void MainClassDeclaration(LinkedList<ClassDecl> classList) throws ParseException {
        String name = ClassName();
        Token argsId;
        LinkedList<VarDecl> localsList = new LinkedList<VarDecl>();
        LinkedList<Stmt> statements = new LinkedList<Stmt>();
        jj_consume_token(10);
        jj_consume_token(27);
        jj_consume_token(28);
        jj_consume_token(29);
        jj_consume_token(30);
        jj_consume_token(6);
        jj_consume_token(31);
        jj_consume_token(8);
        jj_consume_token(9);
        argsId = jj_consume_token(ID);
        jj_consume_token(7);
        jj_consume_token(10);
        label_2: while (true) {
            if (!jj_2_2(3)) break label_2;
            VarDeclaration(localsList);
        }
        label_3: while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case 10:
                case 37:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case INT:
                case STRING:
                case ID:
                    break;
                default:
                    jj_la1[2] = jj_gen;
                    break label_3;
            }
            Statement(statements);
        }
        jj_consume_token(11);
        jj_consume_token(11);
        LinkedList<MethodDecl> methods = new LinkedList<MethodDecl>();
        LinkedList<Formal> params = new LinkedList<Formal>();
        Formal args = new Formal(new ArrayType(new IdentifierType("String")), argsId.image);
        params.add(args);
        MethodDecl mainMethod = new MethodDecl(null, false, "main", params, localsList, statements, new IntegerLiteral(0));
        methods.add(mainMethod);
        classList.add(new ClassDecl(name, null, new LinkedList<VarDecl>(), methods));
    }

    // ClassDeclaration ::= ClassName ( "extends" <ID> )? "{" ( FieldDeclaration | MethodDeclaration | VoidDeclaration )* "}"
    private final void ClassDeclaration(LinkedList<ClassDecl> classList) throws ParseException {
        String name = ClassName();
        Token parent = null;
        LinkedList<VarDecl> fields = new LinkedList<VarDecl>();
        LinkedList<MethodDecl> methods = new LinkedList<MethodDecl>();
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case 33:
                jj_consume_token(33);
                parent = jj_consume_token(ID);
                break;
            default:
                jj_la1[3] = jj_gen;
        }
        jj_consume_token(10);
        label_4: while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case 27:
                case 46:
                case 47:
                case ID:
                    break;
                default:
                    jj_la1[4] = jj_gen;
                    break label_4;
            }
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case 46:
            case 47:
            case ID:
                FieldDeclaration(fields);
                break;
            default:
                jj_la1[5] = jj_gen;
                if (jj_2_3(2)) {
                    MethodDeclaration(methods);
                } else {
                    switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                    case 27:
                        VoidDeclaration(methods);
                        break;
                    default:
                        jj_la1[6] = jj_gen;
                        jj_consume_token(-1);
                        throw new ParseException();
                    }
                }
            }
        }
        jj_consume_token(11);
        classList.add(new ClassDecl(name, parent == null ? null : parent.image, fields, methods));
    }

    // ThreadDeclaration ::= ClassName "extends" "Thread" "{" ( FieldDeclaration | MethodDeclaration | VoidDeclaration )* "}"
    private final void ThreadDeclaration(LinkedList<ClassDecl> classList) throws ParseException {
        String name;
        LinkedList<VarDecl> fields = new LinkedList<VarDecl>();
        LinkedList<MethodDecl> methods = new LinkedList<MethodDecl>();
        name = ClassName();
        jj_consume_token(33);
        jj_consume_token(34);
        jj_consume_token(10);
        label_5:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case 27:
            case 46:
            case 47:
            case ID:
                break;
            default:
                jj_la1[7] = jj_gen;
                break label_5;
            }
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case 46:
            case 47:
            case ID:
                FieldDeclaration(fields);
                break;
            default:
                jj_la1[8] = jj_gen;
                if (jj_2_4(2)) {
                    MethodDeclaration(methods);
                } else {
                    switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                    case 27:
                        VoidDeclaration(methods);
                        break;
                    default:
                        jj_la1[9] = jj_gen;
                        jj_consume_token(-1);
                        throw new ParseException();
                    }
                }
            }
        }
        jj_consume_token(11);
        classList.add(new ThreadDecl(name, fields, methods));
    }

    // VarDeclaration ::= Type <ID> ( "=" Expression )? ";"
    private final void VarDeclaration(LinkedList<VarDecl> vars) throws ParseException {
        Type type;
        Token id;
        Expr init = null;
        type = Type();
        id = jj_consume_token(ID);
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
        case 20:
            jj_consume_token(20);
            init = Expression();
            break;
        default:
            jj_la1[10] = jj_gen;
        }
        jj_consume_token(26);
        vars.add(new VarDecl(type, id.image, init));
    }

    // MethodDeclaration ::= "private" ( "synchronized" )? Type <ID> "(" ( Type <ID> ( "," Type <ID> )* )? ")" "{" ( VarDeclaration )* ( Statement )* "return" Expression ";" "}"
    private final void MethodDeclaration(LinkedList<MethodDecl> methods) throws ParseException {
        Type returnType;
        Token id;
        boolean sync = false;
        Type paramType;
        Token paramId;
        LinkedList<Formal> params = new LinkedList<Formal>();
        LinkedList<VarDecl> locals = new LinkedList<VarDecl>();
        LinkedList<Stmt> statements = new LinkedList<Stmt>();
        Expr returnVal;
        jj_consume_token(27);
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
        case 35:
            jj_consume_token(35);
            sync = true;
            break;
        default:
            jj_la1[11] = jj_gen;
        }
        returnType = Type();
        id = jj_consume_token(ID);
        jj_consume_token(6);
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
        case 46:
        case 47:
        case ID:
            paramType = Type();
            paramId = jj_consume_token(ID);
            params.add(new Formal(paramType, paramId.image));
            label_6:
            while (true) {
                switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case 25:
                    break;
                default:
                    jj_la1[12] = jj_gen;
                    break label_6;
                }
                jj_consume_token(25);
                paramType = Type();
                paramId = jj_consume_token(ID);
                params.add(new Formal(paramType, paramId.image));
            }
            break;
        default:
            jj_la1[13] = jj_gen;
        }
        jj_consume_token(7);
        jj_consume_token(10);
        label_7:
        while (true) {
            if (jj_2_5(2)) {
                ;
            } else {
                break label_7;
            }
            VarDeclaration(locals);
        }
        label_8:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case 10:
            case 37:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case INT:
            case STRING:
            case ID:
                break;
            default:
                jj_la1[14] = jj_gen;
                break label_8;
            }
            Statement(statements);
        }
        jj_consume_token(36);
        returnVal = Expression();
        jj_consume_token(26);
        jj_consume_token(11);
        methods.add(new MethodDecl(returnType, sync, id.image, params, locals, statements, returnVal));
    }

    // VoidDeclaration ::= "private" "void" <ID> "(" ")" "{" ( VarDeclaration )* ( Statement )* "}"
    private final void VoidDeclaration(LinkedList<MethodDecl> methods) throws ParseException {
        Token id;
        LinkedList<VarDecl> locals = new LinkedList<VarDecl>();
        LinkedList<Stmt> statements = new LinkedList<Stmt>();
        jj_consume_token(27);
        jj_consume_token(29);
        id = jj_consume_token(ID);
        jj_consume_token(6);
        jj_consume_token(7);
        jj_consume_token(10);
        label_9:
        while (true) {
            if (!jj_2_6(2)) {
                break label_9;
            }
            VarDeclaration(locals);
        }
        label_10:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case 10:
            case 37:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case INT:
            case STRING:
            case ID:
                break;
            default:
                jj_la1[15] = jj_gen;
                break label_10;
            }
            Statement(statements);
        }
        jj_consume_token(11);
        methods.add(new VoidDecl(id.image, locals, statements));
    }

    // FieldDeclaration ::= Type <ID> ";"
    private final void FieldDeclaration(LinkedList<VarDecl> fields) throws ParseException {
        Type type;
        Token id;
        type = Type();
        id = jj_consume_token(ID);
        jj_consume_token(26);
        fields.add(new VarDecl(type, id.image, null));
    }

    // Type ::= <ID> ( "[" "]" )*
    //      |   "boolean" ( "[" "]" )*
    //      |   "int" ( "[" "]" )*
    private final Type Type() throws ParseException {
        Token id = null;
        int dimensions = 0;
        boolean isBool = false, isInt = false;
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
        case ID:
            id = jj_consume_token(ID);
            break;
        case 47:
            jj_consume_token(47);
            isBool = true;
            break;
        case 46:
            jj_consume_token(46);
            isInt = true;
            break;
        default:
            jj_la1[16] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
        }
        label_11:
        while (true) {
            if (jj_2_7(2)) {
                ;
            } else {
                break label_11;
            }
            jj_consume_token(8);
            jj_consume_token(9);
            dimensions++;
        }
        Type t;
        if (isBool) t = new BooleanType();
        else if (isInt) t = new IntegerType();
        else t = new IdentifierType(id.image);
        for (int i = 0; i < dimensions; ++i) {
            t = new ArrayType(t);
        }
        return t;
    }

    // Statement ::= "{" ( Statement )* "}"
    //           |   "if" "(" Expression ")" Statement ( "else" Statement )?
    //           |   "while" "(" Expression ")" Statement
    //           |   "Xinu" "." <ID> "(" ( Expression ( "," Expression )* )? ")" ";"
    //           |   Expression "=" Expression ";"
    private final void Statement(LinkedList<Stmt> statements) throws ParseException {
        LinkedList<Stmt> innerStatements = new LinkedList<Stmt>();
        Expr innerExpr1 = null, innerExpr2 = null;
        LinkedList<Expr> innerExpressions = new LinkedList<Expr>();
        Token xinuId = null;
        String type;
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
        case 10:
            jj_consume_token(10);
            label_12:
            while (true) {
                switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case 10:
                case 37:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case INT:
                case STRING:
                case ID:
                    break;
                default:
                    jj_la1[17] = jj_gen;
                    break label_12;
                }
                Statement(innerStatements);
            }
            jj_consume_token(11);
            type = "block";
            break;
        case 37:
            jj_consume_token(37);
            jj_consume_token(6);
            innerExpr1 = Expression();
            jj_consume_token(7);
            Statement(innerStatements);
            if (jj_2_8(2)) {
                jj_consume_token(38);
                Statement(innerStatements);
            }
            type = "if";
            break;
        case 39:
            jj_consume_token(39);
            jj_consume_token(6);
            innerExpr1 = Expression();
            jj_consume_token(7);
            Statement(innerStatements);
            type = "while";
            break;
        default:
            jj_la1[18] = jj_gen;
            if (jj_2_9(3)) {
                jj_consume_token(40);
                jj_consume_token(12);
                xinuId = jj_consume_token(ID);
                MethodCall(innerExpressions);
                jj_consume_token(26);
                type = "xinu";
            } else {
                switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case INT:
                case STRING:
                case ID:
                    innerExpr1 = AssignableExpression();
                    jj_consume_token(20);
                    innerExpr2 = Expression();
                    jj_consume_token(26);
                    type = "assign";
                    break;
                default:
                    jj_la1[19] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
                }
            }
        }

        if (type.equals("block")) {
            statements.add(new BlockStmt(innerStatements));
        } else if (type.equals("if")) {
            if (innerStatements.size() == 1) statements.add(new IfStmt(innerExpr1, innerStatements.get(0), null));
            else statements.add(new IfStmt(innerExpr1, innerStatements.get(0), innerStatements.get(1)));
        } else if (type.equals("while")) {
            statements.add(new WhileStmt(innerExpr1, innerStatements.get(0)));
        } else if (type.equals("xinu")) {
            statements.add(new XinuCallStmt(xinuId.image, innerExpressions));
        } else if (type.equals("assign")) {
            statements.add(new AssignStmt((AssignableExpr)innerExpr1, innerExpr2));
        }
    }

    // Expression ::= Expression1 ( "||" Expression1 )*
    private final Expr Expression() throws ParseException {
        Expr left, right = null;
        LinkedList<Expr> exprs = new LinkedList<Expr>();
        left = Expression1();
        label_13:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case 24:
                break;
            default:
                jj_la1[20] = jj_gen;
                break label_13;
            }
            jj_consume_token(24);
            right = Expression1();
            exprs.add(right);
        }
        for (Expr expr : exprs) {
            left = new OrExpr(left, expr);
        }
        return left;
    }

    // Expression1 ::= Expression2 ( "&&" Expression2 )*
    private final Expr Expression1() throws ParseException {
        Expr left, right = null;
        LinkedList<Expr> exprs = new LinkedList<Expr>();
        left = Expression2();
        label_14:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case 23:
                break;
            default:
                jj_la1[21] = jj_gen;
                break label_14;
            }
            jj_consume_token(23);
            right = Expression2();
            exprs.add(right);
        }
        for (Expr expr : exprs) {
            left = new AndExpr(left, expr);
        }
        return left;
    }

    // Expression2 ::= Expression3 ( "==" Expression3 )*
    //             |   Expression3 ( "!=" Expression3 )*
    private final Expr Expression2() throws ParseException {
        Expr left, right = null;
        LinkedList<Expr> exprs = new LinkedList<Expr>();
        LinkedList<String> types = new LinkedList<String>();
        left = Expression3();
        label_15:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case 21:
            case 22:
                break;
            default:
                jj_la1[22] = jj_gen;
                break label_15;
            }
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case 21:
                jj_consume_token(21);
                right = Expression3();
                exprs.add(right); types.add("equal");
                break;
            case 22:
                jj_consume_token(22);
                right = Expression3();
                exprs.add(right); types.add("notequal");
                break;
            default:
                jj_la1[23] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
            }
        }
        for (int i = 0; i < exprs.size(); ++i) {
            if (types.get(i).equals("equal")) {
                left = new EqualExpr(left, exprs.get(i));
            } else if (types.get(i).equals("notequal")) {
                left = new NotEqExpr(left, exprs.get(i));
            }
        }
        return left;
    }

    // Expression3 ::= Expression4 ( "<" Expression4 )*
    //             |   Expression4 ( ">" Expression4 )*
    private final Expr Expression3() throws ParseException {
        Expr left, right = null;
        LinkedList<Expr> exprs = new LinkedList<Expr>();
        LinkedList<String> types = new LinkedList<String>();
        left = Expression4();
        label_16:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case 18:
            case 19:
                break;
            default:
                jj_la1[24] = jj_gen;
                break label_16;
            }
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case 18:
                jj_consume_token(18);
                right = Expression4();
                exprs.add(right); types.add("less");
                break;
            case 19:
                jj_consume_token(19);
                right = Expression4();
                exprs.add(right); types.add("greater");
                break;
            default:
                jj_la1[25] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
            }
        }
        for (int i = 0; i < exprs.size(); ++i) {
            if (types.get(i).equals("less")) {
                left = new LesserExpr(left, exprs.get(i));
            } else if (types.get(i).equals("greater")) {
                left = new GreaterExpr(left, exprs.get(i));
            }
        }
        return left;
    }

    // Expression4 ::= Expression5 ( "+" Expression5 )*
    //             |   Expression5 ( "-" Expression5 )*
    private final Expr Expression4() throws ParseException {
                Expr left, right = null;
        LinkedList<Expr> exprs = new LinkedList<Expr>();
        LinkedList<String> types = new LinkedList<String>();
        left = Expression5();
        label_17:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case 13:
            case 17:
                break;
            default:
                jj_la1[26] = jj_gen;
                break label_17;
            }
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case 17:
                jj_consume_token(17);
                right = Expression5();
                exprs.add(right); types.add("plus");
                break;
            case 13:
                jj_consume_token(13);
                right = Expression5();
                exprs.add(right); types.add("minus");
                break;
            default:
                jj_la1[27] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
            }
        }
        for (int i = 0; i < exprs.size(); ++i) {
            if (types.get(i).equals("plus")) {
                left = new AddExpr(left, exprs.get(i));
            } else if (types.get(i).equals("minus")) {
                left = new SubExpr(left, exprs.get(i));
            }
        }
        return left;
    }

    // Expression5 ::= Expression6 ( "*" Expression6 )*
    //             |   Expression6 ( "/" Expression6 )*
    private final Expr Expression5() throws ParseException {
        Expr left, right = null;
        LinkedList<Expr> exprs = new LinkedList<Expr>();
        LinkedList<String> types = new LinkedList<String>();
        left = Expression6();
        label_18:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case 15:
            case 16:
                break;
            default:
                jj_la1[28] = jj_gen;
                break label_18;
            }
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case 15:
                jj_consume_token(15);
                right = Expression6();
                exprs.add(right); types.add("times");
                break;
            case 16:
                jj_consume_token(16);
                right = Expression6();
                exprs.add(right); types.add("div");
                break;
            default:
                jj_la1[29] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
            }
        }
        for (int i = 0; i < exprs.size(); ++i) {
            if (types.get(i).equals("times")) {
                left = new MulExpr(left, exprs.get(i));
            } else if (types.get(i).equals("div")) {
                left = new DivExpr(left, exprs.get(i));
            }
        }
        return left;
    }

    // Expression6 ::=  ( "-" )+ Expression7
    //             |    ( "!" )+ Expression7
    //             |   Expression7
    private final Expr Expression6() throws ParseException {
        Expr expr;
        LinkedList<String> types = new LinkedList<String>();
        label_19:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case 13:
            case 14:
                break;
            default:
                jj_la1[30] = jj_gen;
                break label_19;
            }
            if (jj_2_10(2)) {
                jj_consume_token(13);
                types.add("neg");
            } else {
                switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case 14:
                    jj_consume_token(14);
                    types.add("not");
                    break;
                default:
                    jj_la1[31] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
                }
            }
        }
        expr = Expression7();
        for (String type : types) {
            if (type.equals("neg")) expr = new NegExpr(expr);
            else if (type.equals("not")) expr = new NotExpr(expr);
        }
        return expr;
    }

    // Expression7 ::=  ( "(" Expression ")" | Expression8 ) ( "." <ID> ( MethodCall )? | "[" Expression "]" )*
    private final Expr Expression7() throws ParseException {
        Expr left, right = null;
        LinkedList<Expr> exprs = new LinkedList<Expr>();
        Token id = null;
        LinkedList<Token> ids = new LinkedList<Token>();
        LinkedList<LinkedList<Expr>> paramses = new LinkedList<LinkedList<Expr>>();
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
        case 6:
            jj_consume_token(6);
            left = Expression();
            jj_consume_token(7);
            break;
        case 40:
        case 41:
        case 42:
        case 43:
        case 44:
        case 45:
        case INT:
        case STRING:
        case ID:
            left = Expression8();
            break;
        default:
            jj_la1[32] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
        }
        label_20:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case 8:
            case 12:
                break;
            default:
                jj_la1[33] = jj_gen;
                break label_20;
            }
            if (jj_2_11(2)) {
                jj_consume_token(12);
                id = jj_consume_token(ID);
                ids.add(id);
                paramses.add(new LinkedList<Expr>());
                exprs.add(null);
                switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case 6:
                    MethodCall(paramses.getLast());
                    if (paramses.getLast().size() == 0) paramses.getLast().add(null);
                    break;
                default:
                    jj_la1[34] = jj_gen;
                }
            } else if (jj_2_12(2)) {
                jj_consume_token(8);
                right = Expression();
                exprs.add(right); ids.add(null); paramses.add(null);
                jj_consume_token(9);
            } else {
                jj_consume_token(-1);
                throw new ParseException();
            }
        }
        for (int i = 0; i < exprs.size(); i++) {
            if (exprs.get(i) != null) {
                left = new ArrayExpr(left, exprs.get(i));
            } else if (paramses.get(i).size() == 0) {
                left = new FieldExpr(left, ids.get(i).image);
            } else {
                LinkedList<Expr> params = paramses.get(i);
                if (params.get(0) == null) params = new LinkedList<Expr>();
                left = new CallExpr(left, ids.get(i).image, params);
            }
        }
        return left;
    }

    private final Expr ArrayExpression() throws ParseException {
        Expr left, right = null;
        LinkedList<Expr> exprs = new LinkedList<Expr>();
        left = Expression8();
        label_21:
        while (true) {
            jj_consume_token(8);
            right = Expression();
            exprs.add(right);
            jj_consume_token(9);
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case 8: break;
            default:
                jj_la1[35] = jj_gen;
                break label_21;
            }
        }
        for (Expr expr : exprs) {
            left = new ArrayExpr(left, expr);
        }
        return left;
    }

    // MethodCall ::= "(" ( Expression ( "," Expression )* )? ")"
    private final void MethodCall(LinkedList<Expr> exprs) throws ParseException {
        Expr expr;
        jj_consume_token(6);
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
        case 6:
        case 13:
        case 14:
        case 40:
        case 41:
        case 42:
        case 43:
        case 44:
        case 45:
        case INT:
        case STRING:
        case ID:
            expr = Expression();
            exprs.add(expr);
            label_22:
            while (true) {
                switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case 25:
                    ;
                    break;
                default:
                    jj_la1[36] = jj_gen;
                    break label_22;
                }
                jj_consume_token(25);
                expr = Expression();
                exprs.add(expr);
            }
            break;
        default:
            jj_la1[37] = jj_gen;
        }
        jj_consume_token(7);
    }

    // Expression8 ::=  NewDecl "(" ")"
    //             |    NewDecl "[" Expression "]" ( "[" "]" )*
    //             |    "Xinu" "." <ID> "(" ( Expression ( "," Expression )* )? ")"
    //             |    <ID>
    //             |    <INT>
    //             |    <STRING>
    //             |    "true"
    //             |    "false"
    //             |    "this"
    //             |    "null"
    private final Expr Expression8() throws ParseException {
        Type type = null;
        Expr expr = null;
        Token xinuId = null;
        LinkedList<Expr> exprs = new LinkedList<Expr>();
        Token terminal = null;
        String exprType;
        if (jj_2_14(3)) {
            type = NewDecl();
            jj_consume_token(6);
            jj_consume_token(7);
            exprType = "newobj";
        } else if (jj_2_15(3)) {
            type = NewDecl();
            jj_consume_token(8);
            expr = Expression();
            exprs.add(expr);
            jj_consume_token(9);
            label_23:
            while (true) {
                if (!jj_2_13(2)) break label_23;
                jj_consume_token(8);
                exprs.add(null);
                jj_consume_token(9);
            }
            exprType = "newarr";
        } else {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case 40:
                    jj_consume_token(40);
                    jj_consume_token(12);
                    xinuId = jj_consume_token(ID);
                    MethodCall(exprs);
                    exprType = "xinu";
                    break;
                case ID:
                    terminal = jj_consume_token(ID);
                    exprType = "id";
                    break;
                case INT:
                    terminal = jj_consume_token(INT);
                    exprType = "int";
                    break;
                case STRING:
                    terminal = jj_consume_token(STRING);
                    exprType = "string";
                    break;
                case 41:
                    jj_consume_token(41);
                    exprType = "true";
                    break;
                case 42:
                    jj_consume_token(42);
                    exprType = "false";
                    break;
                case 43:
                    jj_consume_token(43);
                    exprType = "this";
                    break;
                case 44:
                    jj_consume_token(44);
                    exprType = "null";
                    break;
                default:
                    jj_la1[38] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }

        if (exprType.equals("newobj")) {
            return new NewObjectExpr(type);
        } else if (exprType.equals("newarr")) {
            return new NewArrayExpr(type, exprs);
        } else if (exprType.equals("xinu")) {
            return new XinuCallExpr(xinuId.image, exprs);
        } else if (exprType.equals("id")) {
            return new IdentifierExpr(terminal.image);
        } else if (exprType.equals("int")) {
            return new IntegerLiteral(new Integer(terminal.toString()));
        } else if (exprType.equals("string")) {
            return new StringLiteral(terminal.toString());
        } else if (exprType.equals("true")) {
            return new TrueExpr();
        } else if (exprType.equals("false")) {
            return new FalseExpr();
        } else if (exprType.equals("this")) {
            return new ThisExpr();
        } else {
            return new NullExpr();
        }
    }

    private final Expr AssignableExpression() throws ParseException {
        Expr expr;
        Token id;
        if (jj_2_16(2)) {
            expr = ArrayExpression();
        } else {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case ID:
                    id = jj_consume_token(ID);
                    expr = new IdentifierExpr(id.image);
                    break;
                default:
                    jj_la1[39] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
        return expr;
    }

    // NewDecl ::= "new" Type
    private final Type NewDecl() throws ParseException {
        Token id;
        Type type;
        jj_consume_token(45);
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case ID:
                id = jj_consume_token(ID);
                type = new IdentifierType(id.image);
                break;
            case 46:
                jj_consume_token(46);
                type = new IntegerType();
                break;
            case 47:
                jj_consume_token(47);
                type = new BooleanType();
                break;
            default:
                jj_la1[40] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
        }
        return type;
    }

    private boolean jj_2_1(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        try { return !jj_3_1(); }
        catch(LookaheadSuccess ls) { return true; }
        finally { jj_save(0, xla); }
    }

    private boolean jj_2_2(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        try { return !jj_3_2(); }
        catch(LookaheadSuccess ls) { return true; }
        finally { jj_save(1, xla); }
    }

    private boolean jj_2_3(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        try { return !jj_3_3(); }
        catch(LookaheadSuccess ls) { return true; }
        finally { jj_save(2, xla); }
    }

    private boolean jj_2_4(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        try { return !jj_3_4(); }
        catch(LookaheadSuccess ls) { return true; }
        finally { jj_save(3, xla); }
    }

    private boolean jj_2_5(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        try { return !jj_3_5(); }
        catch(LookaheadSuccess ls) { return true; }
        finally { jj_save(4, xla); }
    }

    private boolean jj_2_6(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        try { return !jj_3_6(); }
        catch(LookaheadSuccess ls) { return true; }
        finally { jj_save(5, xla); }
    }

    private boolean jj_2_7(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        try { return !jj_3_7(); }
        catch(LookaheadSuccess ls) { return true; }
        finally { jj_save(6, xla); }
    }

    private boolean jj_2_8(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        try { return !jj_3_8(); }
        catch(LookaheadSuccess ls) { return true; }
        finally { jj_save(7, xla); }
    }

    private boolean jj_2_9(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        try { return !jj_3_9(); }
        catch(LookaheadSuccess ls) { return true; }
        finally { jj_save(8, xla); }
    }

    private boolean jj_2_10(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        try { return !jj_3_10(); }
        catch(LookaheadSuccess ls) { return true; }
        finally { jj_save(9, xla); }
    }

    private boolean jj_2_11(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        try { return !jj_3_11(); }
        catch(LookaheadSuccess ls) { return true; }
        finally { jj_save(10, xla); }
    }

    private boolean jj_2_12(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        try { return !jj_3_12(); }
        catch(LookaheadSuccess ls) { return true; }
        finally { jj_save(11, xla); }
    }

    private boolean jj_2_13(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        try { return !jj_3_13(); }
        catch(LookaheadSuccess ls) { return true; }
        finally { jj_save(12, xla); }
    }

    private boolean jj_2_14(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        try { return !jj_3_14(); }
        catch(LookaheadSuccess ls) { return true; }
        finally { jj_save(13, xla); }
    }

    private boolean jj_2_15(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        try { return !jj_3_15(); }
        catch(LookaheadSuccess ls) { return true; }
        finally { jj_save(14, xla); }
    }

    private boolean jj_2_16(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        try { return !jj_3_16(); }
        catch(LookaheadSuccess ls) { return true; }
        finally { jj_save(15, xla); }
    }

    private boolean jj_3R_60() {
        if (jj_scan_token(44)) return true;
        return false;
    }

    private boolean jj_3R_59() {
        if (jj_scan_token(43)) return true;
        return false;
    }

    private boolean jj_3R_58() {
        if (jj_scan_token(42)) return true;
        return false;
    }

    private boolean jj_3R_57() {
        if (jj_scan_token(41)) return true;
        return false;
    }

    private boolean jj_3R_56() {
        if (jj_scan_token(STRING)) return true;
        return false;
    }

    private boolean jj_3R_55() {
        if (jj_scan_token(INT)) return true;
        return false;
    }

    private boolean jj_3R_54() {
        if (jj_scan_token(ID)) return true;
        return false;
    }

    private boolean jj_3R_62() {
        if (jj_scan_token(27)) return true;
        return false;
    }

    private boolean jj_3R_53() {
        if (jj_scan_token(40)) return true;
        if (jj_scan_token(12)) return true;
        return false;
    }

    private boolean jj_3_15() {
        if (jj_3R_29()) return true;
        if (jj_scan_token(8)) return true;
        return false;
    }

    private boolean jj_3R_31() {
        if (jj_scan_token(32)) return true;
        if (jj_scan_token(ID)) return true;
        return false;
    }

    private boolean jj_3_14() {
        if (jj_3R_29()) return true;
        if (jj_scan_token(6)) return true;
        return false;
    }

    private boolean jj_3R_41() {
        if (jj_3R_52()) return true;
        return false;
    }

    private boolean jj_3R_45() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3_14()) {
            jj_scanpos = xsp;
            if (jj_3_15()) {
                jj_scanpos = xsp;
                if (jj_3R_53()) {
                    jj_scanpos = xsp;
                    if (jj_3R_54()) {
                        jj_scanpos = xsp;
                        if (jj_3R_55()) {
                            jj_scanpos = xsp;
                            if (jj_3R_56()) {
                                jj_scanpos = xsp;
                                if (jj_3R_57()) {
                                    jj_scanpos = xsp;
                                    if (jj_3R_58()) {
                                        jj_scanpos = xsp;
                                        if (jj_3R_59()) {
                                            jj_scanpos = xsp;
                                            if (jj_3R_60()) return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean jj_3_5() {
        if (jj_3R_25()) return true;
        return false;
    }

    private boolean jj_3R_36() {
        if (jj_scan_token(35)) return true;
        return false;
    }

    private boolean jj_3R_35() {
        if (jj_scan_token(20)) return true;
        return false;
    }

    private boolean jj_3_4() {
        if (jj_3R_26()) return true;
        return false;
    }

    private boolean jj_3_1() {
        if (jj_3R_24()) return true;
        return false;
    }

    private boolean jj_3_13() {
        if (jj_scan_token(8)) return true;
        if (jj_scan_token(9)) return true;
        return false;
    }

    private boolean jj_3R_66() {
        if (jj_3R_67()) return true;
        return false;
    }

    private boolean jj_3R_46() {
        if (jj_scan_token(8)) return true;
        return false;
    }

    private boolean jj_3R_26() {
        if (jj_scan_token(27)) return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_36()) jj_scanpos = xsp;
        if (jj_3R_34()) return true;
        return false;
    }

    private boolean jj_3R_28() {
        if (jj_3R_41()) return true;
        return false;
    }

    private boolean jj_3_3() {
        if (jj_3R_26()) return true;
        return false;
    }

    private boolean jj_3R_50() {
        if (jj_scan_token(46)) return true;
        return false;
    }

    private boolean jj_3R_72() {
        if (jj_3R_45()) return true;
        return false;
    }

    private boolean jj_3R_25() {
        if (jj_3R_34()) return true;
        if (jj_scan_token(ID)) return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_35()) jj_scanpos = xsp;
        if (jj_scan_token(26)) return true;
        return false;
    }

    private boolean jj_3R_65() {
        if (jj_3R_66()) return true;
        return false;
    }

    private boolean jj_3R_40() {
        if (jj_3R_51()) return true;
        return false;
    }

    private boolean jj_3_9() {
        if (jj_scan_token(40)) return true;
        if (jj_scan_token(12)) return true;
        if (jj_scan_token(ID)) return true;
        return false;
    }

    private boolean jj_3R_39() {
        if (jj_scan_token(39)) return true;
        return false;
    }

    private boolean jj_3R_32() {
        if (jj_scan_token(33)) return true;
        if (jj_scan_token(ID)) return true;
        return false;
    }

    private boolean jj_3R_38() {
        if (jj_scan_token(37)) return true;
        return false;
    }

    private boolean jj_3R_30() {
        if (jj_3R_45()) return true;
        Token xsp;
        if (jj_3R_46()) return true;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_46()) { jj_scanpos = xsp; break; }
        }
        return false;
    }

    private boolean jj_3R_37() {
        if (jj_scan_token(10)) return true;
        return false;
    }

    private boolean jj_3R_70() {
        if (jj_scan_token(14)) return true;
        return false;
    }

    private boolean jj_3R_27() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_37()) {
        jj_scanpos = xsp;
        if (jj_3R_38()) {
        jj_scanpos = xsp;
        if (jj_3R_39()) {
        jj_scanpos = xsp;
        if (jj_3_9()) {
        jj_scanpos = xsp;
        if (jj_3R_40()) return true;
        }
        }
        }
        }
        return false;
    }

    private boolean jj_3R_47() {
        if (jj_3R_61()) return true;
        return false;
    }

    private boolean jj_3R_33() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_47()) {
        jj_scanpos = xsp;
        if (jj_3_3()) {
        jj_scanpos = xsp;
        if (jj_3R_48()) return true;
        }
        }
        return false;
    }

    private boolean jj_3R_42() {
        if (jj_scan_token(ID)) return true;
        return false;
    }

    private boolean jj_3R_49() {
        if (jj_scan_token(47)) return true;
        return false;
    }

    private boolean jj_3R_44() {
        if (jj_scan_token(47)) return true;
        return false;
    }

    private boolean jj_3R_43() {
        if (jj_scan_token(46)) return true;
        return false;
    }

    private boolean jj_3R_64() {
        if (jj_3R_65()) return true;
        return false;
    }

    private boolean jj_3R_29() {
        if (jj_scan_token(45)) return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_42()) {
        jj_scanpos = xsp;
        if (jj_3R_43()) {
        jj_scanpos = xsp;
        if (jj_3R_44()) return true;
        }
        }
        return false;
    }

    private boolean jj_3R_24() {
        if (jj_3R_31()) return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_32()) jj_scanpos = xsp;
        if (jj_scan_token(10)) return true;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_33()) { jj_scanpos = xsp; break; }
        }
        if (jj_scan_token(11)) return true;
        return false;
    }

    private boolean jj_3_11() {
        if (jj_scan_token(12)) return true;
        if (jj_scan_token(ID)) return true;
        return false;
    }

    private boolean jj_3R_71() {
        if (jj_scan_token(6)) return true;
        return false;
    }

    private boolean jj_3_12() {
        if (jj_scan_token(8)) return true;
        if (jj_3R_28()) return true;
        return false;
    }

    private boolean jj_3R_69() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_71()) {
        jj_scanpos = xsp;
        if (jj_3R_72()) return true;
        }
        return false;
    }

    private boolean jj_3_2() {
        if (jj_3R_25()) return true;
        return false;
    }

    private boolean jj_3_7() {
        if (jj_scan_token(8)) return true;
        if (jj_scan_token(9)) return true;
        return false;
    }

    private boolean jj_3_8() {
        if (jj_scan_token(38)) return true;
        if (jj_3R_27()) return true;
        return false;
    }

    private boolean jj_3R_63() {
        if (jj_scan_token(ID)) return true;
        return false;
    }

    private boolean jj_3_16() {
        if (jj_3R_30()) return true;
        return false;
    }

    private boolean jj_3R_34() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(59)) {
        jj_scanpos = xsp;
        if (jj_3R_49()) {
        jj_scanpos = xsp;
        if (jj_3R_50()) return true;
        }
        }
        while (true) {
            xsp = jj_scanpos;
            if (jj_3_7()) { jj_scanpos = xsp; break; }
        }
        return false;
    }

    private boolean jj_3R_51() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3_16()) {
        jj_scanpos = xsp;
        if (jj_3R_63()) return true;
        }
        return false;
    }

    private boolean jj_3R_48() {
        if (jj_3R_62()) return true;
        return false;
    }

    private boolean jj_3R_52() {
        if (jj_3R_64()) return true;
        return false;
    }

    private boolean jj_3R_68() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3_10()) {
        jj_scanpos = xsp;
        if (jj_3R_70()) return true;
        }
        return false;
    }

    private boolean jj_3R_61() {
        if (jj_3R_34()) return true;
        return false;
    }

    private boolean jj_3_10() {
        if (jj_scan_token(13)) return true;
        return false;
    }

    private boolean jj_3R_67() {
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_68()) { jj_scanpos = xsp; break; }
        }
        if (jj_3R_69()) return true;
        return false;
    }

    private boolean jj_3_6() {
        if (jj_3R_25()) return true;
        return false;
    }

    private Token jj_consume_token(int kind) throws ParseException {
        Token oldToken;
        if ((oldToken = token).next != null) token = token.next;
        else token = token.next = token_source.getNextToken();
        jj_ntk = -1;
        if (token.kind == kind) {
            jj_gen++;
            if (++jj_gc > 100) {
                jj_gc = 0;
                for (int i = 0; i < jj_2_rtns.length; i++) {
                    JJCalls c = jj_2_rtns[i];
                    while (c != null) {
                        if (c.gen < jj_gen) c.first = null;
                        c = c.next;
                    }
                }
            }
            return token;
        }
        token = oldToken;
        jj_kind = kind;
        throw generateParseException();
    }

    private boolean jj_scan_token(int kind) {
        if (jj_scanpos == jj_lastpos) {
            jj_la--;
            if (jj_scanpos.next == null) {
                jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
            } else {
                jj_lastpos = jj_scanpos = jj_scanpos.next;
            }
        } else {
            jj_scanpos = jj_scanpos.next;
        }
        if (jj_rescan) {
            int i = 0; Token tok = token;
            while (tok != null && tok != jj_scanpos) {
                i++; tok = tok.next;
            }
            if (tok != null) jj_add_error_token(kind, i);
        }
        if (jj_scanpos.kind != kind) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
        return false;
    }

    private int jj_ntk() {
        if ((jj_nt=token.next) == null) return (jj_ntk = (token.next=token_source.getNextToken()).kind);
        else return (jj_ntk = jj_nt.kind);
    }

    private void jj_add_error_token(int kind, int pos) {
        if (pos >= 100) return;
        if (pos == jj_endpos + 1) {
            jj_lasttokens[jj_endpos++] = kind;
        } else if (jj_endpos != 0) {
            jj_expentry = new int[jj_endpos];
            for (int i = 0; i < jj_endpos; i++) {
                jj_expentry[i] = jj_lasttokens[i];
            }
            jj_entries_loop: for (java.util.Iterator it = jj_expentries.iterator(); it.hasNext();) {
                int[] oldentry = (int[])(it.next());
                if (oldentry.length == jj_expentry.length) {
                    for (int i = 0; i < jj_expentry.length; i++) {
                        if (oldentry[i] != jj_expentry[i]) {
                            continue jj_entries_loop;
                        }
                    }
                    jj_expentries.add(jj_expentry);
                    break jj_entries_loop;
                }
            }
            if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
        }
    }

    /** Generate ParseException. */
    private ParseException generateParseException() {
        jj_expentries.clear();
        boolean[] la1tokens = new boolean[63];
        if (jj_kind >= 0) {
            la1tokens[jj_kind] = true;
            jj_kind = -1;
        }
        for (int i = 0; i < 41; i++) {
            if (jj_la1[i] == jj_gen) {
                for (int j = 0; j < 32; j++) {
                    if ((jj_la1_0[i] & (1<<j)) != 0) {
                        la1tokens[j] = true;
                    }
                    if ((jj_la1_1[i] & (1<<j)) != 0) {
                        la1tokens[32+j] = true;
                    }
                }
            }
        }
        for (int i = 0; i < 63; i++) {
            if (la1tokens[i]) {
                jj_expentry = new int[1];
                jj_expentry[0] = i;
                jj_expentries.add(jj_expentry);
            }
        }
        jj_endpos = 0;
        jj_rescan_token();
        jj_add_error_token(0, 0);
        int[][] exptokseq = new int[jj_expentries.size()][];
        for (int i = 0; i < jj_expentries.size(); i++) {
            exptokseq[i] = (int[])jj_expentries.get(i);
        }
        return new ParseException(token, exptokseq);
    }

    private void jj_rescan_token() {
        jj_rescan = true;
        for (int i = 0; i < 16; i++) {
            try {
                JJCalls p = jj_2_rtns[i];
                do {
                    if (p.gen > jj_gen) {
                        jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
                        switch (i) {
                            case 0: jj_3_1(); break;
                            case 1: jj_3_2(); break;
                            case 2: jj_3_3(); break;
                            case 3: jj_3_4(); break;
                            case 4: jj_3_5(); break;
                            case 5: jj_3_6(); break;
                            case 6: jj_3_7(); break;
                            case 7: jj_3_8(); break;
                            case 8: jj_3_9(); break;
                            case 9: jj_3_10(); break;
                            case 10: jj_3_11(); break;
                            case 11: jj_3_12(); break;
                            case 12: jj_3_13(); break;
                            case 13: jj_3_14(); break;
                            case 14: jj_3_15(); break;
                            case 15: jj_3_16(); break;
                        }
                    }
                    p = p.next;
                } while (p != null);
            } catch(LookaheadSuccess ls) { }
        }
        jj_rescan = false;
    }

    private void jj_save(int index, int xla) {
        JJCalls p = jj_2_rtns[index];
        while (p.gen > jj_gen) {
            if (p.next == null) {
                p = p.next = new JJCalls();
                break;
            }
            p = p.next;
        }
        p.gen = jj_gen + xla - jj_la;
        p.first = token;
        p.arg = xla;
    }
}
