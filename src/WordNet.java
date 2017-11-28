import java.io.File;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinearProbingHashST;

public class WordNet {
    private LinearProbingHashST<String, Integer> wordDict;
    private Digraph digraph;
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets.isEmpty() || hypernyms.isEmpty()) {
            throw new IllegalArgumentException();
        }
        wordDict = new LinearProbingHashST<>();
        
        File synsetsFile = new File(synsets);
        File hypernymsFile = new File(hypernyms);
        In synsetsIn = new In(synsetsFile);
        In hypernymsIn = new In(hypernymsFile);
        for (String line: synsetsIn.readAllLines()) {
            if (!line.isEmpty()) {
                String[] items = line.split(",");
                Integer id = Integer.parseInt(items[0]);
                for (String word: items[1].split(" ")) {
                    wordDict.put(word, id);
                }
            }
        }
        digraph = new Digraph(wordDict.size());
        for (String line: hypernymsIn.readAllLines()) {
            String[] items = line.split(",");
            digraph.addEdge(Integer.parseInt(items[0]), Integer.parseInt(items[1]));
        }
        for (int i: digraph.adj(5)) {
            System.out.println(i);
        }
    }
    
    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return wordDict.keys();
    }
    
    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return wordDict.get(word) == null;
    }
    
    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        int idA = wordDict.get(nounA);
        int idB = wordDict.get(nounB);
        
        return idA;
    }
    
    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
//    public String sap(String nounA, String nounB) {
//        
//    }

    public static void main(String[] args) {
        new WordNet("input/wordnet/synsets15.txt", "input/wordnet/hypernyms15Path.txt");
    }
}
