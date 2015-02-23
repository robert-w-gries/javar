package Symbol;

import java.util.HashMap;
import java.util.Stack;

/**
 * Created by rgries on 2/22/15.
 */
public class SymbolTable<E> {

    private HashMap<Symbol, E> symMap;
    private Stack<Symbol> symStack;

    public SymbolTable() {

        symMap = new HashMap<Symbol, E>();
        symStack = new Stack<Symbol>();

    }

    public void put(Symbol sym, E value) {
        symMap.put(sym, value);
        symStack.push(sym);
    }

    public E get(Symbol sym) {
        return symMap.get(sym);
    }

    public void beginScope() {
        symStack.push(Symbol.symbol(""));
    }

    public void endScope() {

        // loop through all of the symbols from current scope and remove them from the symbol table
        while (!symStack.peek().toString().equals("")) {
            symMap.remove(symStack.pop());
        }

        // pop the beginScope marker
        symStack.pop();

    }

}
