import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import java.util.Arrays;
import java.util.LinkedList;

public class BaseballElimination {
    private final int teamCount;
    private final SeparateChainingHashST<String, Integer> teamNames;
    private final String[] idToNames;
    private final int[] winCounts;
    private final int[] loseCounts;
    private final int[] remainCounts;
    private final int[][] matchMatrix;
    private static final double Inf = Double.POSITIVE_INFINITY;
    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In inFile = new In(filename);
        teamCount = inFile.readInt();
        teamNames = new SeparateChainingHashST<>();
        idToNames = new String[teamCount];
        winCounts = new int[teamCount];
        loseCounts = new int[teamCount];
        remainCounts = new int[teamCount];
        matchMatrix = new int[teamCount][teamCount];
        for (int i = 0; i < teamCount; i++) {
            String s = inFile.readString();
            teamNames.put(s, i);
            idToNames[i] = s;
            winCounts[i] = inFile.readInt();
            loseCounts[i] = inFile.readInt();
            remainCounts[i] = inFile.readInt();
            for (int j = 0; j < teamCount; j++) {
                matchMatrix[i][j] = inFile.readInt();
            }
        }
    }

    // number of teams
    public int numberOfTeams() {
        return teamCount;
    }

    // all teams
    public Iterable<String> teams() {
        return teamNames.keys();
    }

    private int getTeam(String team) {
        Integer teamID = teamNames.get(team);
        if (teamID == null)
            throw new IllegalArgumentException("Team doesn't exist!!!");
        return teamID;
    }

    // number of wins for given team
    public int wins(String team) {
        return winCounts[getTeam(team)];
    }

    // number of losses for given team
    public int losses(String team) {
        return loseCounts[getTeam(team)];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        return remainCounts[getTeam(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        return matchMatrix[getTeam(team1)][getTeam(team2)];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        return certificateOfElimination(team) != null;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        int id = getTeam(team);
        int tNumber = teamCount - 1;
        int[] teamFN = new int[tNumber];
        int flag = 0;
        for (int i = 0; i < teamCount; i++) {
            if (i == id) {
                flag = 1;
                continue;
            }
            teamFN[i-flag] = i;
        }
        int mNumber = (tNumber - 1)*tNumber / 2;
        int[] matchVec = new int[mNumber];
        int[] team1Vec = new int[mNumber];
        int[] team2Vec = new int[mNumber];
        int t = 0;
        for (int i = 0; i < tNumber; i++) {
            for (int j = 0; j < i; j++) {
                matchVec[t] = matchMatrix[teamFN[i]][teamFN[j]];
                team1Vec[t] = i;
                team2Vec[t++] = j;
            }
        }

        int vNumber = 1 + teamCount + mNumber;
        FlowNetwork fn = new FlowNetwork(vNumber);
        for (int i = 0; i < mNumber; i++) {
            fn.addEdge(new FlowEdge(0, i+1, matchVec[i], 0));
            fn.addEdge(new FlowEdge(i+1, team1Vec[i] + mNumber + 1, Inf, 0));
            fn.addEdge(new FlowEdge(i+1, team2Vec[i] + mNumber + 1, Inf, 0));
        }
        int baseWin = winCounts[id] + remainCounts[id];
        for (int i = 0; i < tNumber; i++) {
            int winCap = baseWin - winCounts[teamFN[i]];
            if (winCap < 0)
                return Arrays.asList(idToNames[teamFN[i]]);
            fn.addEdge(new FlowEdge(i + mNumber + 1, vNumber - 1, baseWin - winCounts[teamFN[i]], 0));
        }

        FordFulkerson ff = new FordFulkerson(fn, 0, vNumber - 1);
        LinkedList<String> minEliminates = new LinkedList<String>();
        for (int i = 0; i < tNumber; i++)
            if (ff.inCut(i + mNumber + 1))
                minEliminates.add(idToNames[teamFN[i]]);

        if (minEliminates.size() == 0)
            return null;
        return minEliminates;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
