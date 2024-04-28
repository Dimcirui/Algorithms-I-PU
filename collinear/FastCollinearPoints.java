import java.util.ArrayList;
import edu.princeton.cs.algs4.MergeX;

public class FastCollinearPoints {
    private Point[] points;
    private ArrayList<LineSegment> newSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        for (Point p : points) {
            if (p == null) throw new IllegalArgumentException();
        }

        this.points = points.clone();
        MergeX.sort(this.points);

        for (int i = 0; i < this.points.length - 1; i++) {
            if (this.points[i].compareTo(this.points[i + 1]) == 0) throw new IllegalArgumentException();
        }

        newSegments = new ArrayList<LineSegment>();
        fastFindSegments(this.points);
    }

    private void fastFindSegments(Point[] pts) {
        int n = pts.length;

        for (int i = 0; i < n; i++) {
            Point p = pts[i];
            Point[] copy = pts.clone();
            MergeX.sort(copy, p.slopeOrder());

            for (int first = 1, last = 2; last < n; last++) {

                while (last < n && Double.compare(p.slopeTo(copy[first]), p.slopeTo(copy[last])) == 0) {
                    last++;
                }

                if (last - first >= 3 && p.compareTo(copy[first]) < 0) {
                    newSegments.add(new LineSegment(p, copy[last - 1]));
                }

                first = last;
            }
        }
        
    }

    // the number of line segments
    public           int numberOfSegments() {
        return newSegments.size();
    }
    // the line segments
    public LineSegment[] segments() {
        return newSegments.toArray(new LineSegment[newSegments.size()]);
    }
}