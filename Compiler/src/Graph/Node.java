package Graph;

/**
 * Created by rgries on 5/5/15.
 */
public abstract class Node<T> {

    private T value;

    public Node(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

}
