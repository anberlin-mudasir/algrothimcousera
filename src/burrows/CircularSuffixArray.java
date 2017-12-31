import java.util.Comparator;
import java.util.Arrays;
import edu.princeton.cs.algs4.StdOut;

public class CircularSuffixArray {
    private String str;
    private Integer[] sortedIndex;
    private class CircularSuffixOrder implements Comparator<Integer> {
        public int compare(Integer a, Integer b) {
            int l = length();
            for (int i = 0; i < l; i++) {
                int pa = (i + a) % l;
                int pb = (i + b) % l;
                if (str.charAt(pa) > str.charAt(pb))
                    return 1;
                else if (str.charAt(pa) < str.charAt(pb))
                    return -1;
            }
            return 0;
        }
    }

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) throw new IllegalArgumentException("Null input string");
        str = s;
        sortedIndex = new Integer[str.length()];
        for (int i = 0; i < str.length(); i++)
            sortedIndex[i] = i;
        Arrays.sort(sortedIndex, new CircularSuffixOrder());
    }
    // length of s
    public int length() {
        return str.length();
    }
    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= length()) throw new IllegalArgumentException("Index out of range");
        return sortedIndex[i];
    }
    // unit testing (required)
    public static void main(String[] args) {
        CircularSuffixArray csa = new CircularSuffixArray("ABRACADABRA!");
        StdOut.println(csa.length());
        for (int i = 0; i < csa.length(); i++)
            StdOut.println(csa.index(i));
    }
}