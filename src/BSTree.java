import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * The {@code BSTree} class represents an ordered symbol table
 * of generic key-value pairs, using <a
 * href="https://en.wikipedia.org/wiki/Binary_search_tree">
 * binary search tree</a>. The full implementation can be seen
 * <a href="http://algs4.cs.princeton.edu/32bst/BST.java.html">
 * here</a> (from <a
 * href="https://www.coursera.org/learn/algorithms-part1/home">
 * Algorithm lecture</a> by Robert Sedgewick and Kevin Wayne)
 * and this is a collection of notes and part of the core code
 * when I am attending this lecture.
 * 
 * @author chaonan99
 */

public class BSTree<Key extends Comparable<Key>, Value> {
    private Node root;
    
    private class Node {
        private Key key;
        private Value value;
        private int count;
        private Node left, right;
        public Node(Key key, Value value) {
            this.key = key;
            this.value = value;
            this.count = 1;
        }
    }
    
    /**
     * Initializes an empty symbol table.
     */
    public BSTree() {
    }
    
    /**
     * Associate value with key. Override previous value if the
     * key already exists.
     *
     * @param key key to put the value
     * @param value new value for the key
     */
    public void put(Key key, Value value) {
        root = put(root, key, value);
    }
    
    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key, val);
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = put(x.left, key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else x.value = val;
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }
    
    /**
     * Find the largest key not greater than a given key.<br>
     * <br>Method: given key <tt>k</tt>
     * <ul>
     * <li>if <tt>k</tt> equals the key at the root, the floor is
     * <tt>k</tt>.</li>
     * <li>if <tt>k</tt> is less than the key at the root, the
     * floor is in the left subtree.</li>
     * <li>if <tt>k</tt> is greater than the key at the root, the
     * floor is in the right subtree, only when there is any key
     * <tt>k</tt> in right subtree, otherwise is <tt>k</tt> itself.
     * </li>
     * <ul><br>
     *
     * @param key the given key.
     * @return the largest key not greater than the parameter,
     * null if all key in the tree is larger than the given key.
     */
    public Key floor(Key key) {
        Node xNode = floor(root, key);
        if (xNode == null) return null;
        return xNode.key;
    }
    
    private Node floor(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        else if (cmp < 0) return floor(x.left, key);
        
        Node tNode = floor(x.right, key);
        if (tNode == null) return x;
        else return tNode;
    }
    
    /**
     * Find the smallest key not smaller than a given key. Method
     * is similar to {@link BSTree#floor(Key key)}.
     *
     * @param key the given key.
     * @return the largest key not greater than the parameter,
     * null if all key in the tree is larger than the given key.
     */
    public Key ceil(Key key) {
        Node xNode = ceil(root, key);
        if (xNode == null) return null;
        return xNode.key;
    }
    
    private Node ceil(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        else if (cmp > 0) return ceil(x.right, key);
        
        Node tNode = ceil(x.left, key);
        if (tNode == null) return x;
        else return tNode;
    }
    
    /**
     * Get the number of keys that are smaller than the given
     * key.
     *
     * @param key the key to rank.
     * @return the the number of keys that are smaller than the
     * given key.
     */
    public int rank(Key key) {
        return rank(root, key);
    }
    
    private int rank(Node x, Key key) {
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return rank(x.left, key);
        else if (cmp > 0) return 1 + size(x.left) + rank(x.right, key);
        else return size(x.left);
    }
    
    /**
     * Get the value corresponding to given key, or null if no such
     * key.
     *
     * @param key to find value.
     * @return the value corresponding to given key, or null if no
     * such key.
     */
    public Value get(Key key) {
        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else return x.value;
        }
        return null;
    }
    
    /**
     * Check if the tree contains the given key.
     * 
     * @return {@code true} if the tree contains {@code key},
     * {@code false} otherwise.
     */
    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException(
                "argument to contains() is null");
        return get(key) != null;
    }
    
    /**
     * Get the size of the whole tree, yields keys in ascending
     * order.
     *
     * @return the size of the whole tree.
     */
    public int size() {
        return size(root);
    }
    
    private int size(Node x) {
        if (x == null) return 0;
        return x.count;
    }
    
    public boolean isEmpty() {
        return size() == 0;
    }
    
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException(
                "Symbol table underflow");
        root = deleteMin(root);
    }
    
    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }
    
    /**
     * Returns the smallest key in the tree.
     *
     * @return the smallest key in the tree.
     * @throws NoSuchElementException if the symbol table is empty.
     */
    public Key min() {
        if (isEmpty()) throw new NoSuchElementException(
                "called min() with empty symbol table");
        return min(root).key;
    }
    
    private Node min(Node x) {
        if (x.left == null) return x;
        return min(x.left);
    }
    
    /**
     * Implement Hibbard deletion for BST.<br><br>
     * Method:
     * <ul>
     * <li><b>Case 1</b> 0 child. Delete {@code t} by setting parent
     * link to null.</li>
     * <li><b>Case 2</b> 1 child. Delete {@code t} by replacing
     * parent link.</li>
     * <li><b>Case 3</b> 2 children.
     *  <ul>
     *  <li> Find successor {@code x} of {@code t}.</li>
     *  <li> Delete the minimum in {@code t}'s right subtree, but
     *  do not garbage collect the minimum.</li>
     *  <li> Put x in t's spot.</li>
     *  </ul>
     * </li>
     * </ul>
     * <center>
     * <img src="http://algs4.cs.princeton.edu/32bst/images/bst-delete.png"
     * alt="Hibbard deletion with two children" style="float:middle">
     * </center>
     * 
     * @param key the key to delete.
     */
    public void delete(Key key) {
        root = delete(root, key);
    }
    
    private Node delete(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return delete(x.left, key);
        else if (cmp > 0) return delete(x.right, key);
        else {
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;
            
            // Pretty tricky.
            Node tNode = x;
            x = min(tNode.right);
            x.right = deleteMin(tNode.right);
            x.left = tNode.left;
        }
        x.count = size(x.left) + size(x.right) + 1;
        return x;
    }
    
    /**
     * Inorder traversal of the whole tree.<br><br>
     * Method:
     * <ul>
     * <li>Traverse left subtree.</li>
     * <li>Enqueue key.</li>
     * <li>Traverse right subtree.</li>
     * </ul>
     * 
     * @return Iterator for traverse.
     */
    public Iterable<Key> keys() {
        LinkedList<Key> list = new LinkedList<>();
        inorder(root, list);
        return list;
    }
    
    private void inorder(Node x, LinkedList<Key> qKeys) {
        if (x == null) return;
        inorder(x.left, qKeys);
        qKeys.add(x.key);
        inorder(x.right, qKeys);
    }
    
    /**
     * Level order traversal of the whole tree.<br><br>
     * Method: 
     * Maintain two queues. First enqueue root node, then each time
     * dequeue a node and enqueue its two child if not null.
     * 
     * @return Iterator for traverse.
     */
    public Iterable<Key> levelOrder() {
        LinkedList<Node> listNode = new LinkedList<>();
        LinkedList<Key> listKey = new LinkedList<>();
        listNode.add(root);
        listKey.add(root.key);
        while (!listNode.isEmpty()) {
            
            Node x = listNode.removeFirst();
            if (x.left != null) {
                listNode.add(x.left);
                listKey.add(x.left.key);
            }
            if (x.right != null) {
                listNode.add(x.right);
                listKey.add(x.right.key);
            }
        }
        return listKey;
    }
    
    public static void main(String[] args) {
        // Unit test
        BSTree<String, Integer> bsTree = new BSTree<>();
        bsTree.put("G", 2);
        bsTree.put("L", 3);
        bsTree.put("C", 1);
        System.out.println(bsTree.size() == 3);
        System.out.println(bsTree.ceil("Q") == null);
        System.out.println(bsTree.floor("Q") == "L");
        System.out.println(bsTree.rank("H") == 2);
        System.out.println(bsTree.get("H") == null);
        System.out.println(bsTree.get("G") == 2);
        
        System.out.println(bsTree.size() == 3);
        for (String s : bsTree.keys()) {
            System.out.print(s);
        }
        System.out.println("");
        for (String s : bsTree.levelOrder()) {
            System.out.print(s);
        }
        System.out.println("");
            
        bsTree.deleteMin();
        bsTree.delete("G");
        System.out.println(bsTree.contains("C") == false);
        System.out.println(bsTree.contains("L") == true);
    }
}
