package DataStructure;

public class MyLinkedList<E extends Comparable<E>> extends AbstractList<E> {
    protected Node<E> head;
    protected Node<E> tail;
    protected int size;

    protected static class Node<E> {
        E element;
        Node<E> next;

        Node(E element) {
            this.element = element;
        }
    }

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void insert(E element) {
        Node<E> newNode = new Node<>(element);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void sort() {
        if (size > 1) {
            boolean wasChanged;
            do {
                Node<E> current = head;
                Node<E> previous = null;
                Node<E> next = head.next;
                wasChanged = false;

                while (next != null) {
                    if (current.element.compareTo(next.element) > 0) {
                        wasChanged = true;
                        if (previous != null) {
                            Node<E> temp = next.next;
                            previous.next = next;
                            next.next = current;
                            current.next = temp;
                        } else {
                            Node<E> temp = next.next;
                            head = next;
                            next.next = current;
                            current.next = temp;
                        }
                        previous = next;
                        next = current.next;
                    } else {
                        previous = current;
                        current = next;
                        next = next.next;
                    }
                }
            } while (wasChanged);
        }
    }

    @Override
    public int find(E element) {
        Node<E> current = head;
        int index = 0;
        while (current != null) {
            if (current.element.compareTo(element) == 0) {
                return index;
            }
            current = current.next;
            index++;
        }
        return -1;
    }

    @Override
    public void delete(E element) {
        if (head == null) return;
        if (head.element.compareTo(element) == 0) {
            head = head.next;
            size--;
            return;
        }

        Node<E> current = head;
        while (current.next != null) {
            if (current.next.element.compareTo(element) == 0) {
                current.next = current.next.next;
                size--;
                return;
            }
            current = current.next;
        }
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size) return null;
        Node<E> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.element;
    }

    @Override
    public void set(int index, E element) {
        if (index < 0 || index >= size) return;
        Node<E> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        current.element = element;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<E> current = head;
        while (current != null) {
            sb.append(current.element).append(" -> ");
            current = current.next;
        }
        sb.append("null");
        return sb.toString();
    }
}
