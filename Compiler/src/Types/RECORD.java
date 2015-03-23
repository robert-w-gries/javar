package Types;

import Semant.TypeChecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Type for aggregate records.
 */
public class RECORD extends Type implements java.lang.Iterable<FIELD> {

    private Map<String, Integer> fieldMap;
    private List<FIELD> fields;
    private int index;

    public RECORD() {
        fieldMap = new HashMap<String, Integer>();
        fields = new ArrayList<FIELD>();
        index = 0;
    }

    public RECORD(RECORD copy) {
        this();
        for (FIELD field : copy) {
            this.put(field.type, field.name);
        }
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Visitor v) {
    }

    public boolean coerceTo(Type t) {
        if (!(t instanceof RECORD)) return false;
        RECORD r = (RECORD)t;
        if (fields.size() != r.fields.size()) return false;
        for (int i : fieldMap.values()) {
            if (!this.get(i).coerceTo(r.get(i)))
                TypeChecker.errorAndExit("ERROR incompatible types: " + r.get(i) + " required, " +
                        "but " + this.get(i) + " found");
        }
        return true;
    }

    public FIELD get(String name) {
        if (!fieldMap.containsKey(name)) return null;
        return fields.get(fieldMap.get(name));
    }

    FIELD get(int i) { return fields.get(i); }

    public java.util.Iterator<FIELD> iterator() {
        return fields.iterator();
    }

    public FIELD put(Type type, String name) {
        FIELD field = new FIELD(type, name, index++);
        fieldMap.put(name, field.index);
        fields.add(field);
        return field;
    }

    public void putAll(RECORD fields) {
        for (FIELD field : fields) {
            this.put(field.type, field.name);
        }
    }

    public java.lang.String toString() {
        return "";
    }
}
