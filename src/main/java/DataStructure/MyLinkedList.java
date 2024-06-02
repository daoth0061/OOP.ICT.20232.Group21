package DataStructure;

public class MyLinkedList<E extends Comparable<E>> extends AbstractList<E> {
    private Node<E> head;
    private Node<E> tail;

    private static class Node<E> {
        E data;
        Node<E> next;
        Node<E> prev;

        Node(E data) {
            this.data = data;
        }
    }

    @Override
    public void add(E element) {
        Node<E> newNode = new Node<>(element);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public E remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        Node<E> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        E element = current.data;
        if (current.prev != null) {
            current.prev.next = current.next;
        } else {
            head = current.next;
        }
        if (current.next != null) {
            current.next.prev = current.prev;
        } else {
            tail = current.prev;
        }
        size--;
        return element;
    }

    @Override
    public E get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        Node<E> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    @Override
    public void sort() {
        if (size > 1) {
            bubbleSort();
        }
    }

    private void bubbleSort() {
        boolean swapped;
        do {
            swapped = false;
            Node<E> current = head;
            Node<E> prev = null;
            while (current != null && current.next != null) {
                if (current.data.compareTo(current.next.data) > 0) {
                    swap(current, prev, current.next);
                    swapped = true;
                }
                prev = current;
                current = current.next;
            }
        } while (swapped);
    }

    private void swap(Node<E> node1Prev, Node<E> node1, Node<E> node2) {
        if (node1Prev != null) {
            node1Prev.next = node2;
        } else {
            head = node2;
        }
        Node<E> tmp = node2.next;
        node2.next = node1;
        node1.next = tmp;
        if (tmp == null) {
            tail = node1;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> current = head;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }
}

