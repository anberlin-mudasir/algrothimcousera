import java.util.LinkedList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private KdNode root;
    public KdTree() {
        // construct an empty set of points
    }
    private class KdNode{
        public int dimension;
        public KdNode left;
        public KdNode right;
        public Point2D point;
        public int size;
        
        public KdNode(Point2D p, int dim) {
            point = p;
            dimension = dim;
            size = 1;
        }
        
        public int nextDim() {
            return (dimension + 1) % 2;
        }
        
        public int compare(Point2D p) {
            if (dimension == 0) {
                if (this.point.x() < p.x()) return -1;
                else if (this.point.x() > p.x()) return 1;
                else if (this.point.y() < p.y()) return -1;
                else if (this.point.y() > p.y()) return 1;
                else return 0;
            } else {
                return this.point.compareTo(p);
            }
        }
    }
    
    public boolean isEmpty() {
        // is the set empty?
        return size() == 0;
    }
    
    public int size() {
        // number of points in the set
        return size(root);
    }
    
    private int size(KdNode x) {
        if (x == null) return 0;
        return x.size;
    }
    
    public void insert(Point2D p) {
        // add the point to the set (if it is not already in the set)
        root = insert(root, null, p);
    }
    private KdNode insert(KdNode x, KdNode parent, Point2D p) {
        if (parent == null && x == null) return new KdNode(p, 0);
        if (x == null) return new KdNode(p, parent.nextDim());
        int cmp = x.compare(p);
        
        if (cmp < 0) x.right = insert(x.right, x, p);
        else if (cmp > 0) x.left = insert(x.left, x, p);
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }
    
    public boolean contains(Point2D p) {
        // does the set contain point p?
        return get(root, p) != null;
    }
    private KdNode get(KdNode x, Point2D p) {
        if (x == null) return null;
        int cmp = x.compare(p);
        if (cmp < 0) return get(x.right, p);
        else if (cmp > 0) return get(x.left, p);
        else return x;
    }
    
    private Iterable<Point2D> iter() {
        LinkedList<Point2D> list = new LinkedList<>();
        inorder(root, list);
        return list;
    }
    private void inorder(KdNode x, LinkedList<Point2D> qKeys) {
        if (x == null) return;
        inorder(x.left, qKeys);
        qKeys.add(x.point);
        inorder(x.right, qKeys);
    }
    public void draw() {
        // draw all points to standard draw
        StdDraw.setPenRadius(0.01);
        for (Point2D p : iter()) {
            p.draw();
        }
        /*LinkedList<KdNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            KdNode parent = queue.removeFirst();
            
        }*/
    }
    
    public Iterable<Point2D> range(RectHV rect) {
        // all points that are inside the rectangle
        LinkedList<Point2D> list = new LinkedList<>();
        checkPoint(root, rect, list);
        return list;
    }
    private void checkPoint(KdNode node, RectHV rect, LinkedList<Point2D> l) {
        if (node == null) return;
        if (rect.contains(node.point)) l.add(node.point);
        if (node.dimension == 0) {
            if (rect.xmin() <= node.point.x()) checkPoint(node.left, rect, l);
            if (rect.xmax() >= node.point.x()) checkPoint(node.right, rect, l);
        } else {
            if (rect.ymin() <= node.point.y()) checkPoint(node.left, rect, l);
            if (rect.ymax() >= node.point.y()) checkPoint(node.right, rect, l);
        }
    }
    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to point p; null if the set is empty
        if (isEmpty()) return null;
        KdNode container = new KdNode(p, 0);
        nearest(root, p, Double.POSITIVE_INFINITY, container);
        return container.point;
    }
    private Double nearest(KdNode node, Point2D p, Double min, KdNode con) {
        if (node == null) return min;
        Double dist = node.point.distanceTo(p);
        if (dist < min) {
            min = dist;
            con.point = node.point;
        }
        Double curr, cmp;
        if (node.dimension == 0) {
            curr = node.point.x();
            cmp = p.x();
        } else {
            curr = node.point.y();
            cmp = p.y();
        }
        if (cmp < curr) {
            min = nearest(node.left, p, min, con);
            if (Math.abs(cmp - curr) < min)
                min = nearest(node.right, p, min, con);
        }
        else {
            min = nearest(node.right, p, min, con);
            if (Math.abs(cmp - curr) < min)
                min = nearest(node.left, p, min, con);
        }
        return min;
    }

    public static void main(String[] args) {
        // unit testing of the methods (optional)
    }
 }