import java.util.Iterator;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int DEFAULT_CAPACITY = 1;
    private Item[] arr;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 0;
        resize(DEFAULT_CAPACITY);
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Null argument");

        if (size > 0 && size == arr.length) resize(arr.length * 2);
        arr[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new java.util.NoSuchElementException("Quque is empty");

        if (size > 0 && size == arr.length / 4) resize(arr.length / 2);
        int random = StdRandom.uniformInt(size);
        Item item = arr[random];
        arr[random] = arr[--size];
        arr[size] = null;

        return item;
    }

    private void resize(int newSize) {
        Item[] newArr = (Item[]) new Object[newSize];

        for (int i = 0; i < size; i++) {
            newArr[i] = arr[i];
        }

        arr = newArr;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new java.util.NoSuchElementException("Quque is empty");

        // Item champion = null;
        // for (int i = 1; i <= size; i++) {
        //     if (StdRandom.bernoulli(1.0 / i)) champion = arr[i - 1];
        // }
        // return champion;
        return arr[StdRandom.uniformInt(size)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomArrayIterator();
    }

    private class RandomArrayIterator implements Iterator<Item> {
        private int[] rand;
        private int cur;

        public RandomArrayIterator() {
            rand = new int[size];
            for (int i = 0; i < size; i++) {
                rand[i] = i;
            }
            StdRandom.shuffle(rand);
            cur = 0;
        }

        public boolean hasNext() {
            return cur < size;
        }

        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException("No elements left");

            return arr[rand[cur++]];
        }

        public void remove() {
            throw new UnsupportedOperationException("Unsupported operation");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> test = new RandomizedQueue<>();

        System.out.println(test.isEmpty());
        test.enqueue(11);
        test.enqueue(45);
        test.enqueue(14);

        for (int v : test) {
            System.out.print(v);
        }
        System.out.println();

        test.dequeue();
        System.out.println(test.size());
        System.out.println(test.sample());

    }

}