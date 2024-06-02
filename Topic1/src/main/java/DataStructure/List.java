package DataStructure;

public interface List<E> {
    void add(E element);
    E remove(int index);
    E get(int index);
    int size();
    void sort();
}

