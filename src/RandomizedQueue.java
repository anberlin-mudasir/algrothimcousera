// 2017-01-20

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static int minCapacity = 1;
    private Item[] items;
    private int size = 0;

    @SuppressWarnings("unchecked")
    public RandomizedQueue() {
        // construct an empty randomized queue
        items = (Item[]) new Object[minCapacity];
    }
    public boolean isEmpty() {
        // is the queue empty?
        return size == 0;
    }
    public int size() {
        // return the number of items on the queue
        return size;
    }
    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++)
            copy[i] = items[i];
        items = copy;
    }
    public void enqueue(Item item) {
        // add the item
        if (item == null) throw new NullPointerException();
        if (size == items.length) resize(2 * items.length);
        items[size++] = item;
    }
    public Item dequeue() {
        // remove and return a random item
        if (isEmpty()) throw new NoSuchElementException();
        int ind = StdRandom.uniform(size);
        Item item = items[ind];
        items[ind] = items[--size];
        items[size] = null;
        if (size > minCapacity && size == items.length/4) resize(items.length/2);
        return item;
    }
    public Item sample() {
        // return (but do not remove) a random item
        if (isEmpty()) throw new NoSuchElementException();
        int ind = StdRandom.uniform(size);
        return items[ind];
    }
    private class RandomizedQueueIterator implements Iterator<Item> {
        private int[] order;
        private int current;
        public RandomizedQueueIterator() {
            current = 0;
            order = new int[size];
            for (int i = 0; i < size; i++)
                order[i] = i;
            StdRandom.shuffle(order);
        }
        public boolean hasNext() { return current < size; }
        public void remove() {
            throw new UnsupportedOperationException();
        }
        public Item next() {
            if (current >= size) throw new NoSuchElementException();
            Item item = items[order[current++]];
            return item;
        }
    }
    public Iterator<Item> iterator() {
        // return an independent iterator over items in random order
        return new RandomizedQueueIterator();
    }
    public static void main(String[] args) {
        // unit testing (optional)
        
    }
}
