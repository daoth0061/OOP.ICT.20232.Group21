package DataStructure;

public abstract class AbstractList<E extends Comparable<E>> implements List<E> {
    public abstract void insert(E element);
    public abstract void sort();
    public abstract int find(E element);
    public abstract void delete(E element);
    public abstract E get(int index);
    public abstract void set(int index, E element);
}
