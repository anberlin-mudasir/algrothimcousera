import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> lineSegments;
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException();
        Point[] pCopy = points.clone();
        Arrays.sort(pCopy);
        for (int i = 0; i < pCopy.length - 1; i++)
            if (pCopy[i].compareTo(pCopy[i+1]) == 0)
                throw new IllegalArgumentException();

        lineSegments = new ArrayList<LineSegment>();
        // finds all line segments containing 4 points
        for (int i = 0; i < pCopy.length - 3; i++) {
            for (int j = i + 1; j < pCopy.length - 2; j++) {
                for (int k = j + 1; k < pCopy.length - 1; k++) {
                    for (int l = k + 1; l < pCopy.length; l++) {
                        if (isCollinear(pCopy[i], pCopy[j], pCopy[k], pCopy[l]))
                            lineSegments.add(new LineSegment(pCopy[i], pCopy[l]));
                    }
                }
            }
        }
    }
    private boolean isCollinear(Point p, Point q, Point r, Point s) {
        Comparator<Point> pComparator = p.slopeOrder();
        return pComparator.compare(q, r) == 0 && pComparator.compare(q, s) == 0;
    }
    public int numberOfSegments() {
        // the number of line segments
        return lineSegments.size();
    }
    public LineSegment[] segments() {
        // the line segments
        return lineSegments.toArray(new LineSegment[0]);
    }
}
