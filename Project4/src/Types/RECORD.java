package Types;

import java.util.HashMap;
import java.util.Map;

/**
 * Type for aggregate records.
 */
public class RECORD extends Type implements java.lang.Iterable<FIELD> {

    private Map<String, FIELD> fieldMap;
    private int index;

    public RECORD() {
        fieldMap = new HashMap<String, FIELD>();
        index = 0;
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Visitor v) {
        return; //TODO codavaj!!
    }

    public boolean coerceTo(Type t) {
        return false; //TODO codavaj!!
    }

    public FIELD get(String name) {
        return fieldMap.get(name);
    }

    public java.util.Iterator<FIELD> iterator() {
        return fieldMap.values().iterator();
    }

    public FIELD put(Type type, String name) {
        FIELD field = new FIELD(type, name, index++);
        fieldMap.put(name, field);
        return field;
    }

    public java.lang.String toString() {
        return null; //TODO codavaj!!
    }
}
