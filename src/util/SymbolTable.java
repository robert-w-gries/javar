package util;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by rgries on 2/22/15.
 *
 */
public class SymbolTable<E> {

    private HashMap<Symbol, Stack<E>> symMap;
    private Stack<Symbol> symStack;

    public SymbolTable() {

        symMap = new HashMap<>();
        symStack = new Stack<>();

    }

    void put(Symbol sym, E value) {
        Stack<E> s = symMap.get(sym);
        if (s == null) {
            symMap.put(sym, new Stack<>());
            s = symMap.get(sym);
        }
        s.push(value);
        symStack.push(sym);
    }

    public void put(String str, E value) {
        this.put(Symbol.symbol(str), value);
    }

    E get(Symbol sym) {
        if (symMap.get(sym) == null) return null;
        return symMap.get(sym).peek();
    }

    public E get(String str) { return this.get(Symbol.symbol(str)); }

    public void beginScope() {
        symStack.push(Symbol.symbol(""));
    }

    public void endScope() {

        // loop through all of the symbols from current scope and remove them from the symbol table
        while (!symStack.peek().toString().equals("")) {
            Symbol sym = symStack.pop();
            Stack<E> stack = symMap.get(sym);
            stack.pop();
            if (stack.empty()) symMap.remove(sym);
        }

        // pop the beginScope marker
        symStack.pop();

    }

    public Collection<E> values() {
        return symMap.values().stream().map(Stack::peek).collect(Collectors.toList());
    }
}
