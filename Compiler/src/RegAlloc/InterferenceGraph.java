package RegAlloc;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 4/27/15
 * Time: 11:03 PM
 */
public abstract class InterferenceGraph extends Graph.Graph {
    abstract public Node tnode(Temp.Temp temp);

    abstract public Temp.Temp gtemp(Node node);

    abstract public List<Move> moves();

    public int spillCost(Node node) {
        return 1;
    }
}
