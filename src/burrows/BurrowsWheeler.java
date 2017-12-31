import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {
    private static class ArrayIndexSorter implements Comparator<Integer> {
        private final Character[] array;
        public ArrayIndexSorter(Character[] array) {
            this.array = array;
        }
        public Integer[] createIndexArray() {
            Integer[] index = new Integer[array.length];
            for (int i = 0; i < array.length; i++)
                index[i] = i;
            return index;
        }
        public int compare(Integer a, Integer b) {
            return array[a].compareTo(array[b]);
        }
    }
    // apply Burrows-Wheeler transform, reading from standard input and writing to standard output
    public static void transform() {
        String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();
        CircularSuffixArray csa = new CircularSuffixArray(s);
        int l = csa.length();
        int first = 0;
        for (int i = 0; i < l; i++) {
            if (csa.index(i) == 0)
                first = i;
        }
        BinaryStdOut.write(first);
        for (int i = 0; i < l; i++)
            BinaryStdOut.write(input[(csa.index(i) - 1 + l) % l]);
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        ArrayList<Character> t = new ArrayList<>();
        while (!BinaryStdIn.isEmpty())
            t.add(BinaryStdIn.readChar());
        int l = t.size();
        ArrayIndexSorter comparator = new ArrayIndexSorter(t.toArray(new Character[0]));
        Integer[] next = comparator.createIndexArray();
        Arrays.sort(next, comparator);
        char[] result = new char[l];
        for (int i = 0, n = first; i < l; i++, n = next[n])
            BinaryStdOut.write(t.get(next[n]));
        BinaryStdOut.close();
    }

    // if args[0] is '-', apply Burrows-Wheeler transform
    // if args[0] is '+', apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) transform();
        else if (args[0].equals("+")) inverseTransform();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}