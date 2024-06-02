package DataStructure;
public class MyStack<E extends Comparable<E>> {
    private MyLinkedList<E> linkedList;

    public MyStack() {
        linkedList = new MyLinkedList<E>();
    }

    public void add(E element) {
        linkedList.add(element);
    }

    public E remove() {
        if (linkedList.size() == 0) {
            throw new IllegalStateException("Stack is empty");
        }
        return linkedList.remove(linkedList.size() - 1);
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
