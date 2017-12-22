import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class Outcast {
    private final WordNet wn;
    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        wn = wordnet;
    }
    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int nl = nouns.length;
        int[] sumDist = new int[nl];
        for (int i = 0; i < nl - 1; i++) {
            for (int j = i + 1; j < nl; j++) {
                int dist = wn.distance(nouns[i], nouns[j]);
                sumDist[i] += dist;
                sumDist[j] += dist;
            }
        }
        int maxDist = 0;
        int outCastID = -1;
        for (int i = 0; i < nl; i++) {
            if (maxDist < sumDist[i]) {
                maxDist = sumDist[i];
                outCastID = i;
            }
        }
        return nouns[outCastID];
    }
    // see test client below
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}