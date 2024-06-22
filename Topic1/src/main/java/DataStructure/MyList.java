package DataStructure;

public class MyList<E extends Comparable<E>> extends AbstractList<E> {
    private E[] array;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    @SuppressWarnings("unchecked")
    public MyList() {
        array = (E[]) new Comparable[DEFAULT_CAPACITY];
        size = 0;
    }

    private void ensureCapacity() {
        if (size == array.length) {
            @SuppressWarnings("unchecked")
            E[] newArray = (E[]) new Comparable[array.length * 2];
            System.arraycopy(array, 0, newArray, 0, array.length);
            array = newArray;
        }
    }

    @Override
    public void insert(E element) {
        ensureCapacity();
        array[size++] = element;
    }

    @Override
    public void sort() {
        mergeSort(0, size - 1);
    }

    private void mergeSort(int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(left, mid);
            mergeSort(mid + 1, right);
            merge(left, mid, right);
        }
    }

    private void merge(int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        @SuppressWarnings("unchecked")
        E[] L = (E[]) new Comparable[n1];
        @SuppressWarnings("unchecked")
        E[] R = (E[]) new Comparable[n2];

        System.arraycopy(array, left, L, 0, n1);
        System.arraycopy(array, mid + 1, R, 0, n2);

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (L[i].compareTo(R[j]) <= 0) {
                array[k] = L[i];
                i++;
            } else {
                array[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            array[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            array[k] = R[j];
            j++;
            k++;
        }
    }

    @Override
    public int find(E element) {
        for (int i = 0; i < size; i++) {
            if (array[i].compareTo(element) == 0) {
                return i;
            }
        }
        return -1;
    }
    @Override
    public int size() {
        return size;
    }

    @Override
    public void delete(E element) {
        int index = find(element);
        if (index != -1) {
            System.arraycopy(array, index + 1, array, index, size - index - 1);
            size--;
        }
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        return array[index];
    }

    @Override
    public void set(int index, E element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        array[index] = element;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(array[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
