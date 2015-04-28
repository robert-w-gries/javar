package RegAlloc;

import Temp.Temp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 4/27/15
 * Time: 11:03 PM
 */
public class InterferenceGraph extends Graph.Graph<Temp> {

    private Map<Temp, Node> tempNodeMap;

    public InterferenceGraph() {
        tempNodeMap = new HashMap<>();
    }

    public void addEdge(Temp t1, Temp t2) {
        Node n1, n2;
        if (!tempNodeMap.containsKey(t1)) {
            n1 = newNode(t1);
            tempNodeMap.put(t1, n1);
        } else n1 = tempNodeMap.get(t1);

        if (!tempNodeMap.containsKey(t2)) {
            n2 = newNode(t2);
            tempNodeMap.put(t2, n2);
        } else n2 = tempNodeMap.get(t2);

        n1.addSucc(n2);
    }


    public Node tnode(Temp temp) {

    }

    public Temp gtemp(Node node) {

    }

    public List<Move> moves() {

    }

    public int spillCost(Node node) {
        return 1;
    }
}
