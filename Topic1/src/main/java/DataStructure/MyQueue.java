package DataStructure;

public class MyQueue<E extends Comparable<E>> {
    private MyLinkedList<E> linkedList;

    public MyQueue() {
        linkedList = new MyLinkedList<>();
    }

    public void add(E element) {
        linkedList.add(element);
    }

    public E remove() {
        if (linkedList.size() == 0) {
            throw new IllegalStateException("Queue is empty");
        }
        return linkedList.remove(0);
    }

    public E get(int index) {
        return linkedList.get(index);
    }

    public int size() {
        return linkedList.size();
    }

    public void sort() {
        linkedList.sort();
    }

    @Override
    public String toString() {
        return linkedList.toString();
    }
}
