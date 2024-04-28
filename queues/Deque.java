import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node first;
    private Node last;

    // structure of a double-linked node
    private class Node {
        Item item;
        Node pre;
        Node next;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("Null argument");

        Node node = new Node();
        node.item = item;
        node.next = first;

        if (isEmpty()) last = node;
        else first.pre = node;
        first = node;
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("Null argument");

        Node node = new Node();
        node.item = item;
        node.pre = last;

        if (isEmpty()) first = node;
        else last.next = node;
        last = node;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new java.util.NoSuchElementException("Deque is empty");

        Item item = first.item;
        first = first.next;
        size--;

        if (isEmpty()) last = null;
        else first.pre = null;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new java.util.NoSuchElementException("Deque is empty");

        Item item = last.item;
        last = last.pre;
        size--;
        if (isEmpty()) first = null;
        else last.next = null;

        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node cur = first;

        public boolean hasNext() {
            return cur != null;
        }

        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException("No elements left");

            Item item = cur.item;
            cur = cur.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> test = new Deque<>();
        test.addFirst(45);
        test.addFirst(11);
        test.addLast(14);

        for (int v : test) {
            System.out.print(v);
        }
        System.out.println();

        test.removeFirst();
        System.out.println(test.isEmpty());
        test.removeLast();
        System.out.println(test.size());
    }

}