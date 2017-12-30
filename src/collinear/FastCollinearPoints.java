import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private ArrayList<LineSegment> lineSegments;
    public FastCollinearPoints(Point[] points) {
        // finds all line segments containing 4 or more points
        if (points == null) throw new NullPointerException();
        Point[] pCopy = points.clone();
        Arrays.sort(pCopy);
        for (int i = 0; i < pCopy.length - 1; i++)
            if (pCopy[i].compareTo(pCopy[i+1]) == 0)
                throw new IllegalArgumentException();

        lineSegments = new ArrayList<LineSegment>();
        for (int i = 0; i < pCopy.length - 3; i++) {
            Point[] tP = new Point[pCopy.length];
            System.arraycopy(pCopy, 0, tP, 0, tP.length);
            Arrays.sort(tP, pCopy[i].slopeOrder());
            for (int j = 1; j < tP.length - 2; j++) {
                if (isCL(tP[0], tP[j], tP[j+1], tP[j+2]))
                {
                    Point start = tP[j];
                    for (; j < tP.length - 3 &&
                            isCL(tP[0], tP[j+1], tP[j+2], tP[j+3]); j++);
                    Point end = tP[j+2];
                    if (tP[0].compareTo(start) < 0 && tP[0].compareTo(end) < 0)
                        lineSegments.add(new LineSegment(tP[0], end));
                }
            }
        }
    }
    private boolean isCL(Point p, Point q, Point r, Point s) {
        Comparator<Point> pCom = p.slopeOrder();
        return pCom.compare(q, r) == 0 && pCom.compare(q, s) == 0;
    }
    public int numberOfSegments() {
        // the number of line segments
        return lineSegments.size();
    }
    public LineSegment[] segments() {
        // the line segments
        return lineSegments.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}