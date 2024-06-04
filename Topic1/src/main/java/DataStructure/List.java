package DataStructure;

public interface List<E extends Comparable<E>> {
    void insert(E element);
    void sort();
    int find(E element);
    void delete(E element);
    E get(int index);
    void set(int index, E element);
}
