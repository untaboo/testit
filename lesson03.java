package main.java.com.getjavajob.training.algo2004.gerbulov.lesson03;

import static java.lang.Math.*;

import java.util.Arrays;

public class DynamicArray<E> {
    private final static int DEFAULT_CAPACITY = 10;
    private E[] elementData;
    private int size;

    public DynamicArray(int initialCapacity) {
        if (initialCapacity > 0) {
            elementData = (E[]) new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            elementData = null;
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
        elementData[size++] = element;
        return true;
    }

    private E[] grow() {
        int newSize = (int) ceil(size * 1.5);
        return elementData = Arrays.copyOf(elementData, newSize);
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    private void rangeCheckForAdd(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    public void add(int index, E element) {
        rangeCheckForAdd(index);
        size++;
        if (size >= elementData.length) {
            grow();
        }
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = element;
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
        for (int i = 0; i < size - 1; i++) {
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

    public E[] toArray() {
        return Arrays.copyOf(this.elementData, size());
    }

    public static void main(String[] args) {
        DynamicArray<String> arr = new DynamicArray<>(0);
        arr.add("1");
        arr.add("2");
        arr.add("3");
        arr.add("4");
        arr.remove("3");
        arr.add("5");
        arr.add(3, "7");
        arr.add("6");
        System.out.println(arr.set(4, "8"));
        System.out.println("indexof" + arr.indexOf("8"));
        System.out.println(Arrays.toString(arr.toArray()));
        System.out.println(arr.size());
    }
}
