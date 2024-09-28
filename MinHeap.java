import java.util.ArrayList;

/**
 * Your implementation of a MinHeap.
 *
 * @author SRIHASA PENCHIKALA
 * @version 1.0
 * @userid spenchikala3
 * @GTID 903873663
 *
 * Collaborators: None
 *
 * Resources: None
 */
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     * To initialize the backing array, create a Comparable array and then cast
     * it to a T array.
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * size of the passed in ArrayList (not INITIAL_CAPACITY). Index 0 should
     * remain empty, indices 1 to n should contain the data in proper order, and
     * the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Cannot build head from null data");
        }
        backingArray = (T[]) new Comparable[2 * data.size() + 1];
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) != null) {
                backingArray[i + 1] = data.get(i);
                size++;
            } else {
                throw new IllegalArgumentException("Cannot add null data");
            }
        }
        for (int i = size / 2; i > 0; i--) {
            downHeap(i);
        }
    }

    /**
     * Helper method to downHeap the heap.
     *
     * @param i the index to downHeap
     */
    public void downHeap(int i) {
        T parent = backingArray[i];
        T left = backingArray[i * 2];
        T right = backingArray[i * 2 + 1];

        if (right != null && right.compareTo(parent) < 0 && right.compareTo(left) < 0) {
            backingArray[i] = right;
            backingArray[i * 2 + 1] = parent;

            if ((size / 2) >= (i * 2 + 1)) {
                downHeap(i * 2 + 1);
            }
        } else if (left != null && left.compareTo(parent) < 0) {
            backingArray[i] = left;
            backingArray[i * 2] = parent;

            if ((size / 2) >= (i * 2)) {
                downHeap(i * 2);
            }
        }
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     * The order property of the heap must be maintained after adding. You can
     * assume that no duplicate data will be passed in.
     * 
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Camnot add null data");
        }

        if (size + 1 == backingArray.length) {
            T[] tempArray = (T[]) new Comparable[backingArray.length * 2];
            for (int i = 0; i < backingArray.length; i++) {
                tempArray[i] = backingArray[i];
            }
            backingArray = tempArray;
        }

        if (size == 0) {
            backingArray[1] = data;
            size++;
            return;
        }
        backingArray[size + 1] = data;
        int curr = size + 1;
        int parent = curr / 2;

        while (curr > 1 && backingArray[curr].compareTo(backingArray[parent]) < 0) {
            T temp = backingArray[curr];
            backingArray[curr] = backingArray[parent];
            backingArray[parent] = temp;
            curr = parent;
            parent = curr / 2;
        }
        size++;
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     * The order property of the heap must be maintained after removing.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Cannot remove from empty heap");
        } else {
            T removed = backingArray[1];
            backingArray[1] = backingArray[size];
            backingArray[size] = null;
            downHeap(1);

            if (backingArray.length > 3) {
                downHeap(1);
            }
            size--;
            return removed;
        }
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Heap is empty");
        } else {
            return backingArray[1];
        }
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
