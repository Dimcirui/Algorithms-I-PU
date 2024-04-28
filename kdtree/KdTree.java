import java.util.ArrayList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private int size;
    private Node root;

    private static class Node {
        Point2D pt;
        boolean isVertical;
        RectHV rc;
        Node left, right;

        public Node(Point2D p, boolean vertical, Node prev) {
            pt = p;
            isVertical = vertical;

            if (prev == null) rc = new RectHV(0, 0, 1, 1);
            else {
                double x0 = prev.rc.xmin();
                double x1 = prev.rc.xmax();
                double y0 = prev.rc.ymin();
                double y1 = prev.rc.ymax();

                double cmp = prev.compareTo(p);
                if (isVertical) {
                    if (cmp > 0) y1 = prev.pt.y();
                    else y0 = prev.pt.y();
                }
                else {
                    if (cmp > 0) x1 = prev.pt.x();
                    else x0 = prev.pt.x();
                }

                rc = new RectHV(x0, y0, x1, y1);
            }
        }

        private double compareTo(Point2D that) {
            double cmp;
            if (this.isVertical) cmp = this.pt.x() - that.x();
            else cmp = this.pt.y() - that.y();
            return cmp;
        }
    }

    // construct an empty set of points 
    public KdTree() {
        size = 0;
        root = null;
    }

    // is the set empty? 
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set 
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        root = insertPoint(p, root, true, null);
    }

    // insert a point after n
    private Node insertPoint(Point2D p, Node n, boolean isVertical, Node prev) {
        if (n == null) {
            size++;
            return new Node(p, isVertical, prev);
        }
        if (n.pt.compareTo(p) == 0) return n;

        // p < n.pt
        if (n.compareTo(p) > 0) n.left = insertPoint(p, n.left, !isVertical, n);
        else n.right = insertPoint(p, n.right, !isVertical, n);

        return n;
    }

    // does the set contain point p? 
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        if (searchPoint(p, root) == null) return false;
        return true;
    }

    private Node searchPoint(Point2D p, Node n) {
        if (n == null) return null;
        if (n.pt.compareTo(p) == 0) return n;

        // p < n.pt
        if (n.compareTo(p) > 0) return searchPoint(p, n.left);
        else return searchPoint(p, n.right);
    }

    // draw all points to standard draw 
    public void draw() {
        if (isEmpty()) return;
        drawPoint(root, true);
    }

    private void drawPoint(Node n, boolean isVertical) {
        if (n.left != null) drawPoint(n.left, !isVertical);
        if (n.right != null) drawPoint(n.right, !isVertical);

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.025);
        StdDraw.point(n.pt.x(), n.pt.y());

        double x0, y0, x1, y1;
        if (isVertical) {
            StdDraw.setPenColor(StdDraw.RED);
            x0 = n.pt.x();
            x1 = x0;
            y0 = n.rc.ymin();
            y1 = n.rc.ymax();
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            x0 = n.rc.xmin();
            x1 = n.rc.xmax();
            y0 = n.pt.y();
            y1 = y0;
        }
        StdDraw.setPenRadius();
        StdDraw.line(x0, y0, x1, y1);
    }

    // all points that are inside the rectangle (or on the boundary) 
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;

        ArrayList<Point2D> list = new ArrayList<>();
        rangeAll(root, rect, list);
        return list;
    }

    private void rangeAll(Node n, RectHV r, ArrayList<Point2D> list) {
        if (n == null) return;

        if (r.intersects(n.rc)) {
            if (r.contains(n.pt)) list.add(n.pt);
            rangeAll(n.left, r, list);
            rangeAll(n.right, r, list);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;

        return findNearest(p, root, root.pt);
    }

    // find nearest neighbor of p
    private Point2D findNearest(Point2D p, Node n, Point2D nearPt) {
        if (n == null) return nearPt;

        if (n.rc.distanceSquaredTo(p) < p.distanceSquaredTo(nearPt)) {
            if (p.distanceSquaredTo(n.pt) < p.distanceSquaredTo(nearPt)) nearPt = n.pt;
            // p < n.pt
            if (n.compareTo(p) > 0) {
                nearPt = findNearest(p, n.left, nearPt);
                nearPt = findNearest(p, n.right, nearPt);
            }
            else {
                nearPt = findNearest(p, n.right, nearPt);
                nearPt = findNearest(p, n.left, nearPt);
            }
        }

        return nearPt;
    }

    // unit testing of the methods (optional) 
    public static void main(String[] args) {

    }
}
