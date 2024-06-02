package DataStructure;

public class MyList<E extends Comparable<E>> {
    private MyArrayList<E> arrayList;

    public MyList() {
        arrayList = new MyArrayList<>();
    }

    public void add(E element) {
        arrayList.add(element);
    }

    public E remove(int index) {
        return arrayList.remove(index);
    }

    public E get(int index) {
        return arrayList.get(index);
    }

    public int size() {
        return arrayList.size();
    }

    public void sort() {
        arrayList.sort();
    }

    @Override
    public String toString() {
        return arrayList.toString();
    }
}
