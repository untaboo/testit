package main.java.com.getjavajob.training.algo2004.gerbulov.lesson03;

import static main.java.com.getjavajob.training.algo2004.util.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class DynamicArray<E> {
    private final static int DEFAULT_CAPACITY = 10;
    private E[] elementData;
    private int size;
    private int modCount;

    public static void main(String[] args) {
        DynamicArray<String> arrayIt = new DynamicArray<>(1);
        arrayIt.add("1");
        arrayIt.add("2");
        arrayIt.add("3");
        arrayIt.add("4");
        System.out.println("arrayIt = " + arrayIt.size);
        ListIterator<String> trl = arrayIt.listIterator(0);
        while (trl.hasNext()) {
            System.out.println(trl.next());
            System.out.println(trl.previous());
        }
    }

    public DynamicArray(int initialCapacity) {
        if (initialCapacity > 0) {
            elementData = (E[]) new Object[initialCapacity];
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
    }

    public DynamicArray() {
        this(DEFAULT_CAPACITY);
    }

    public boolean add(E element) {
        if (elementData == null) {
            elementData = (E[]) new Object[1];
        }
        if (size == elementData.length) {
            grow();
        }
        modCount++;
        elementData[size++] = element;
        return true;
    }

    private E[] grow() {
        int newSize = (int) (size * 1.5);
        if (size == newSize) {
            newSize++;
        }
        return elementData = Arrays.copyOf(elementData, newSize);
    }

    private void rangeCheckForAdd(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Out of bound");
        }
    }

    public void add(int index, E element) {
        if (index == 0 && size() == 0) {
            size = 1;
        }
        rangeCheckForAdd(index);
        modCount++;
        size++;
        if (size >= elementData.length) {
            grow();
        }
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = element;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DynamicArray)) {
            return false;
        }
        DynamicArray<?> array = (DynamicArray<?>) o;
        return size == array.size &&
                Arrays.equals(elementData, array.elementData);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(size);
        result = 31 * result + Arrays.hashCode(elementData);
        return result;
    }

    public E set(int index, E element) {
        rangeCheckForAdd(index);
        E oldValue = elementData[index];
        elementData[index] = element;
        return oldValue;
    }

    public E get(int index) {
        rangeCheckForAdd(index);
        return elementData[index];
    }

    public E remove(int index) {
        rangeCheckForAdd(index);
        int numMoved = size - index - 1;
        E oldValue = elementData[index];
        System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        elementData[--size] = null;
        return oldValue;
    }

    public boolean remove(E element) {
        for (int i = 0; i < size; i++) {
            if (elementData[i] == element) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    public int size() {
        return size;
    }

    public int indexOf(E element) {
        for (int i = 0; i < size - 1; i++) {
            if (elementData[i] == element) {
                return i;
            }
        }
        return -1;
    }

    public boolean contains(E element) {
        for (int i = 0; i < size - 1; i++) {
            if (elementData[i] == element) {
                return true;
            }
        }
        return false;
    }

    public Object[] toArray() {
        Object[] newArray = new Object[size];
        System.arraycopy(elementData, 0, newArray, 0, size);
        return newArray;
    }

    public String toString() {
        Object[] newArray = new Object[size];
        System.arraycopy(elementData, 0, newArray, 0, size);
        return Arrays.toString(newArray);
    }

    public ListItr listIterator(int index) {
        rangeCheckForAdd(index);
        return new ListItr(index);
    }

    public ListItr listIterator() {
        rangeCheckForAdd(0);
        return new ListItr(0);
    }

    class ListItr implements ListIterator<E> {
        int cursor;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such
        int expectedModCount = modCount;

        public ListItr(int index) {
            this.cursor = index;
        }

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        public E next() {
            int index = cursor;
            if (index >= size) {
                throw new NoSuchElementException();
            }
            if (index >= elementData.length) {
                throw new ConcurrentModificationException();
            }
            cursor = index + 1;
            return (E) elementData[lastRet = index];
        }

        @Override
        public boolean hasPrevious() {
            return cursor != 0;
        }

        @Override
        public E previous() {
            checkForComodification();
            int index = cursor - 1;
            if (index < 0) {
                throw new NoSuchElementException();
            }
            if (index >= elementData.length) {
                throw new ConcurrentModificationException();
            }
            cursor = index;
            return (E) elementData[lastRet = index];
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void remove() {
            if (lastRet < 0) {
                throw new IllegalStateException();
            }
            checkForComodification();
            try {
                DynamicArray.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void set(E element) {
            if (lastRet < 0) {
                throw new IllegalStateException();
            }
            checkForComodification();

            try {
                DynamicArray.this.set(lastRet, element);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void add(E element) {
            checkForComodification();
            try {
                int i = cursor;
                DynamicArray.this.add(i, element);
                cursor = i + 1;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        final void checkForComodification() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
