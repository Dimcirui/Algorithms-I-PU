import edu.princeton.cs.algs4.MergeX;
import java.util.ArrayList;

public class BruteCollinearPoints {
    private Point[] points;
    private ArrayList<LineSegment> newSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
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
        bruteFindSegments(this.points);
    }

    private void bruteFindSegments(Point[] pts) {
        int n = pts.length;
        double s1, s2, s3;

        for (int p = 0; p < n - 3; p++) {
            for (int q = p + 1; q < n - 2; q++) {
                s1 = pts[q].slopeTo(pts[p]);

                for (int r = q + 1; r < n - 1; r++) {
                    s2 = pts[r].slopeTo(pts[q]);
                    if (s1 != s2) continue;

                    for (int s = r + 1; s < n; s++) {
                        s3 = pts[s].slopeTo(pts[r]);
                        if (s2 != s3) continue;

                        newSegments.add(new LineSegment(pts[p], pts[s]));
                    }
                }
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
