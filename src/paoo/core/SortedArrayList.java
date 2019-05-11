package paoo.core;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

public class SortedArrayList<T> implements Iterable<T> {
    public SortedArrayList(Comparator<T> comparator) {
        data = new Object[CHUNK_SIZE];
        size = 0;
        capacity = CHUNK_SIZE;
        this.comparator = comparator;
    }

    public void add(T value) {
        if(size == capacity) {
            capacity += CHUNK_SIZE;
            Object[] newData = new Object[capacity];
            if (size >= 0) System.arraycopy(data, 0, newData, 0, size);
            data = newData;
        }
        int index = 0;
        while ((index < size) && (comparator.compare(value, (T) data[index]) > 0)) {
            ++index;
        }
        if (size - index >= 0) System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = value;
        ++size;
    }

    public boolean remove(T o) {
        int index = 0;
        T item = (T)data[index];
        while(index < size && comparator.compare(o, item) < 0) {
            ++index;
            item = (T)data[index];
        }
        if(index < size) {
            for(int i = index; i < size - 1; ++i) {
                data[i] = data[i + 1];
            }
            --size;
            return true;
        }
        return false;
    }

    public boolean removeAll(Collection<T> c) {
        boolean result = true;
        for(T o : c) {
            if(!remove(o)) {
                result = false;
            }
        }
        return result;
    }

    public void clear() {
        size = 0;
    }

    int size() { return size; }

    public boolean isEmpty() {
        return size == 0;
    }

    T get(int i) {
        return (T)data[i];
    }


    private class SortedArrayListIterator implements Iterator<T> {
        SortedArrayListIterator(int index) {
            this.index = index;
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public T next() {
            T value = (T)data[index];
            ++index;
            return value;
        }

        private int index;
    }

    @Override
    public Iterator<T> iterator() {
        return new SortedArrayListIterator(0);
    }

    private static final int CHUNK_SIZE = 50;

    private Comparator<T> comparator;
    private Object[] data;
    private int capacity;
    private int size;
}
