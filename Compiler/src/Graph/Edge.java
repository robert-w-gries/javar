package Graph;

/**
 * Created by rgries on 5/5/15.
 *
 */

public abstract class Edge<T extends Node> {

    public enum Direction {
        LEFT,       // pointing from n2 to n1
        RIGHT,      // pointing from n1 to n2
        UNDIRECTED  // normal behavior, no direction
    }

    private final Direction dir;
    private T n1, n2;

    Edge(T n1, T n2) {
        this(n1, n2, Direction.UNDIRECTED);
    }

    Edge(T n1, T n2, Direction dir) {
        this.dir = dir;
        this.n1 = n1;
        this.n2 = n2;
    }

    public T getN1() {
        return n1;
    }

    public T getN2() {
        return n2;
    }

    public void setN1(T n1) { this.n1 = n1; }

    public void setN2(T n2) { this.n2 = n2; }

    public Direction getDir() {
        return dir;
    }
}