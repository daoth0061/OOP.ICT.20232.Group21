package DataStructure;

public class MyQueue<E extends Comparable<E>> extends MyLinkedList<E> {
    @Override
    public void insert(E element) {
        enqueue(element);
    }

    public void enqueue(E element) {
        super.insert(element);
    }

    public E dequeue() {
        if (head == null) return null;
        E element = head.element;
        delete(element);
        return element;
    }
}
