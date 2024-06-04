package DataStructure;

public class MyStack<E extends Comparable<E>> extends MyLinkedList<E> {
    @Override
    public void insert(E element) {
        push(element);
    }

    public void push(E element) {
        super.insert(element);
    }

    public E pop() {
        if (tail == null) return null;
        E element = tail.element;
        delete(element);
        return element;
    }
}
