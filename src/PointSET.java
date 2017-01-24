import java.util.ArrayList;
import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    private TreeSet<Point2D> rbTree;
    public PointSET() {
        // construct an empty set of points
        rbTree = new TreeSet<>();
    }
    public boolean isEmpty() {
        // is the set empty?
        return size() == 0;
    }
    public int size() {
        // number of points in the set
        return rbTree.size();
    }
    public void insert(Point2D p) {
        // add the point to the set (if it is not already in the set)
        rbTree.add(p);
    }
    public boolean contains(Point2D p) {
        // does the set contain point p?
        return rbTree.contains(p);
    }
    public void draw() {
        // draw all points to standard draw
        for (Point2D point2d : rbTree) {
            StdDraw.point(point2d.x(), point2d.y());
        }
    }
    public Iterable<Point2D> range(RectHV rect) {
        // all points that are inside the rectangle
        ArrayList<Point2D> list = new ArrayList<>();
        for (Point2D point2d : rbTree) {
            if (rect.contains(point2d)) {
                list.add(point2d);
            }
        }
        return list;
    }
    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to point p; null if the set is empty
        double minDist = Double.POSITIVE_INFINITY;
        Point2D minPoint = null;
        for (Point2D point2d : rbTree) {
            double dist = p.distanceTo(point2d);
            if (dist < minDist) {
                minDist = dist;
                minPoint = point2d;
            }
        }
        return minPoint;
    }

    public static void main(String[] args) {
        // unit testing of the methods (optional)
    }
 }