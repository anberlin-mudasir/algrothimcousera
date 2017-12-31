import java.util.LinkedList;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
    private static final int R = 256;
    public MoveToFront() { }

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();
        LinkedList<Character> alphabet = new LinkedList<>();
        for (char i = 0; i < R; i++)
            alphabet.add(i);
        for (char c: input) {
            char index = (char) alphabet.indexOf(c);
            BinaryStdOut.write(index);
            alphabet.add(0, alphabet.remove(index));
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        LinkedList<Character> alphabet = new LinkedList<>();
        for (char i = 0; i < R; i++)
            alphabet.add(i);
        while (!BinaryStdIn.isEmpty()) {
            char index = BinaryStdIn.readChar();
            char ch = alphabet.remove(index);
            BinaryStdOut.write(ch);
            alphabet.add(0, ch);
        }
        BinaryStdOut.close();
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}