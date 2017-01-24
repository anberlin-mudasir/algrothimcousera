// 2017-01-20

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first = null, last = null;
    private int size = 0;

    private class Node {
        Item item;
        Node next;
        Node before;

        public Node(Item item) {
            this.item = item;
            this.next = null;
            this.before = null;
        }

        public Node(Item item, Node next, Node before) {
            this.item = item;
            this.next = next;
            this.before = before;
        }
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() { return current != null; }
        public void remove() {
            throw new UnsupportedOperationException();
        }
        public Item next() {
            if (current == null) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public Deque() {
        // construct an empty deque
    }

    public boolean isEmpty() {
        // is the deque empty?
        return size == 0;
    }

    public int size() {
        // return the number of items on the deque
        return size;
    }

    public void addFirst(Item item) {
        // add the item to the front
        if (item == null) throw new NullPointerException();
        if (isEmpty()) first = last = new Node(item);
        else {
            Node oldFirst = first;
            first = new Node(item, oldFirst, null);
            oldFirst.before = first;
        }
        size++;
    }

    public void addLast(Item item) {
        // add the item to the end
        if (item == null) throw new NullPointerException();
        if (isEmpty()) first = last = new Node(item);
        else {
            Node oldLast = last;
            last = new Node(item, null, oldLast);
            oldLast.next = last;
        }
        size++;
    }

    public Item removeFirst() {
        // remove and return the item from the front
        if (isEmpty()) throw new NoSuchElementException();
        Item item = first.item;
        first = first.next;
        size--;
        if (isEmpty()) last = null;
        else first.before = null;
        return item;
    }

    public Item removeLast() {
        // remove and return the item from the end
        if (isEmpty()) throw new NoSuchElementException();
        Item item = last.item;
        last = last.before;
        size--;
        if (isEmpty()) first = null;
        else last.next = null;
        return item;
    }

    public Iterator<Item> iterator() {
        // return an iterator over items in order from front to end
        return new DequeIterator();
    }

    public static void main(String[] args) {
        // unit testing (optional)
        Deque<Integer> q = new Deque<Integer>();
        q.addFirst(1);
        q.addFirst(4);
        q.addLast(3);
        System.out.println(q.removeLast());  // 3
        System.out.println(q.removeLast());  // 1
        System.out.println(q.removeFirst());  // 4
        // System.out.println(q.removeFirst());  // throw expection
    }
}
