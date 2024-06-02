package DataStructure;

public abstract class AbstractList<E> implements List<E> {
    protected int size = 0;

    public int size() {
        return size;
    }

    @Override
    public abstract void add(E element);

    @Override
    public abstract E remove(int index);

    @Override
    public abstract E get(int index);

    @Override
    public abstract void sort();

    @Override
    public abstract String toString();
}


