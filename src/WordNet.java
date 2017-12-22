import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinearProbingHashST;
import edu.princeton.cs.algs4.CC;
import java.util.LinkedList;
import java.util.ArrayList;

public class WordNet {
    private final LinearProbingHashST<String, LinkedList<Integer>> wordDict;
    private final ArrayList<String> idToSynset;
    private final SAP wnSAP;
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets.isEmpty() || hypernyms.isEmpty())
            throw new IllegalArgumentException();
        wordDict = new LinearProbingHashST<>();

        In synsetsIn = new In(synsets);
        In hypernymsIn = new In(hypernyms);
        idToSynset = new ArrayList<>();

        for (String line: synsetsIn.readAllLines()) {
            if (!line.isEmpty()) {
                String[] items = line.split(",");
                int id = Integer.parseInt(items[0]);
                for (String word: items[1].split(" ")) {
                    LinkedList<Integer> idList = wordDict.get(word);
                    if (idList == null) {
                        idList = new LinkedList<Integer>();
                        idList.add(id);
                        wordDict.put(word, idList);
                    } else {
                        idList.add(id);
                    }
                }
                idToSynset.add(items[1]);
            }
        }

        // Graph construction
        Digraph digraph = new Digraph(wordDict.size());
        Graph graph = new Graph(wordDict.size());
        for (String line: hypernymsIn.readAllLines()) {
            String[] items = line.split(",");
            for (int i = 1; i < items.length; i++) {
                digraph.addEdge(Integer.parseInt(items[0]), Integer.parseInt(items[i]));
                graph.addEdge(Integer.parseInt(items[0]), Integer.parseInt(items[i]));
            }
        }
        DirectedCycle directedCycle = new DirectedCycle(digraph);
        if (directedCycle.hasCycle())
            throw new IllegalArgumentException();
        int countRoot = 0;
        for (int v = 0; v < digraph.V(); v++) {
            if (digraph.outdegree(v) == 0)
                countRoot++;
        }
        CC cc = new CC(graph);
        if (countRoot != cc.count())
            throw new IllegalArgumentException();

        wnSAP = new SAP(digraph);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return wordDict.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null || word.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return wordDict.get(word) != null;
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null || nounA.isEmpty() || nounB.isEmpty()) {
            throw new IllegalArgumentException();
        }
        LinkedList<Integer> idA = wordDict.get(nounA);
        LinkedList<Integer> idB = wordDict.get(nounB);
        return wnSAP.length(idA, idB);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null || nounA.isEmpty() || nounB.isEmpty()) {
            throw new IllegalArgumentException();
        }
        LinkedList<Integer> idA = wordDict.get(nounA);
        LinkedList<Integer> idB = wordDict.get(nounB);
        int idAncestor = wnSAP.ancestor(idA, idB);
        return idToSynset.get(idAncestor);
    }

    public static void main(String[] args) {
        WordNet wn = new WordNet("input/wordnet/synsets15.txt", "input/wordnet/hypernyms15Tree.txt");
        System.out.println(wn.sap("h", "m"));
        System.out.println(wn.distance("h", "m"));
    }
}
