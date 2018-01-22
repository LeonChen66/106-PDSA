//import java.awt.geom.Point2D;
import java.util.*;

public class RangeSearch {
    // please use the Point2D from algs4.jar
    private KdTree tree = new KdTree();
    public void init(Point2D[] points) {
        for(int i=0;i<points.length;i++){
            tree.insert(points[i]);
        }
    }

    // the result should be sorted accordingly to their x coordinates
    public Point2D[] query(Point2D ll, Point2D ur) {
        RectHV rect = new RectHV(ll.x(),ll.y(),ur.x(),ur.y());
        Queue<Point2D> query_points = tree.range(rect);
        Point2D[] result = new Point2D[query_points.size()];
        for(int i=0;i<result.length;i++){
            result[i] = query_points.dequeue();
        }
        Arrays.sort(result,Point2D.X_ORDER);
        Arrays.sort(result,Point2D.Y_ORDER);
//        StdOut.print(Arrays.toString(result));
        return result;
    }
    public class KdTree {

        private int size;
        private KdNode root;

        /* Inner node of the 2d-tree */
        private class KdNode {
            private Point2D p;    // point created for the node
            private RectHV r;     // outer rectangle surrounding the node
            private KdNode left;  // left is also down
            private KdNode right; // right is also up

            public KdNode(Point2D p, RectHV r) {
                this.p = p;
                this.r = r;
            }
        }

        /* Construct an empty 2d-treeset of points */
        public KdTree() {
            size = 0;
            root = null;
        }

        /* Returns true if there are no points in the set, false otherwise */
        public boolean isEmpty() {
            return size == 0;
        }

        /* Returns the number of points in the set */
        public int size() {
            return size;
        }

        /* Inserts the point p into the 2d-tree.
         * See helper function for details. */
        public void insert(Point2D p) {
            // last param is true if comparing by x-value
            //RectHV baseRect = new RectHV(0.0, 0.0, 1.0, 1.0);
            //root = insert(root, p, baseRect, true);
            root = insert(root, p, 0.0, 0.0, 1.0, 1.0, true);
        }

        /* Returns true if the 2d-tree contains the input p, false otherwise.
         * See helper function for details. */
        public boolean contains(Point2D p) {
            return contains(root, p, true);
        }

        /* Draws all of the points and lines to standard draw.
         * See helper function for details. */
        public void draw() {
            draw(root, true);
        }

        /* Returns a queue of all points in the 2d-tree inside the input rectangle.
         * See helper function for details. */
        public Queue<Point2D> range(RectHV rect) {
            Queue<Point2D> q = new Queue<Point2D>();
            range(root, rect, q);
            return q;
        }

        /* Returns the closest points in the 2d-tree to the input point.
         * See helper function for details. */
        public Point2D nearest(Point2D p) {
            if (root == null) return null;
            return nearest(root, p, root.p, true);
        }


        private KdNode insert(KdNode node, Point2D p, double x0, double y0,
                              double x1, double y1, boolean xcmp) {
            // Insert when you reach an empty location
            if (node == null) {
                size++;
                RectHV r = new RectHV(x0, y0, x1, y1);
                return new KdNode(p, r);
            }
            // If the point already exists, just return
            else if (node.p.x() == p.x() && node.p.y() == p.y()) return node;
            // The current node is vertical: compare x-coordinates
            if (xcmp) {
                double cmp = p.x() - node.p.x();
                if (cmp < 0)
                    node.left = insert(node.left, p, x0, y0, node.p.x(), y1, !xcmp);
                else
                    node.right = insert(node.right, p, node.p.x(), y0, x1, y1, !xcmp);
            }
            // The current node is horizontal: compare y-coordinates
            else {
                double cmp = p.y() - node.p.y();
                if (cmp < 0)
                    node.left = insert(node.left, p, x0, y0, x1, node.p.y(), !xcmp);
                else
                    node.right = insert(node.right, p, x0, node.p.y(), x1, y1, !xcmp);
            }
            return node;
        }

        private boolean contains(KdNode node, Point2D p, boolean xcmp) {
            // false if you didn't find it
            if (node == null) return false;
                // true if you found it
            else if (node.p.x() == p.x() && node.p.y() == p.y()) return true;
            else {
                // The current node is vertical: compare x-coordinates
                if (xcmp) {
                    double cmp = p.x() - node.p.x();
                    if (cmp < 0) return contains(node.left, p, !xcmp);
                    else return contains(node.right, p, !xcmp);
                }
                // The current node is horizontal: compare y-coordinates
                else {
                    double cmp = p.y() - node.p.y();
                    if (cmp < 0) return contains(node.left, p, !xcmp);
                    else return contains(node.right, p, !xcmp);
                }
            }
        }

        private void draw(KdNode node, boolean drawVert) {
            if (node == null) return;
            // Draw point
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            node.p.draw();
            // Draw vertical line with x-coordinates of the point and y-coordinates
            // of the parent rectangle
            if (drawVert) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.setPenRadius();
                StdDraw.line(node.p.x(), node.r.ymin(), node.p.x(), node.r.ymax());
            }
            // Draw horizontal line with y-coordinates of the point and x-coordinates
            // of the parent rectangle
            else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.setPenRadius();
                StdDraw.line(node.r.xmin(), node.p.y(), node.r.xmax(), node.p.y());
            }
            // Draw subtrees
            draw(node.left, !drawVert);
            draw(node.right, !drawVert);
        }

        private void range(KdNode node, RectHV rect, Queue<Point2D> q) {
            if (node == null) return;
            // If the current point is in the input rectangle, enqueue that point
            if (rect.contains(node.p)) {
                q.enqueue(node.p);
            }
            // Check the left and right subtrees if the input rectangle intersects
            // the current rectangle
            if (rect.intersects(node.r)) {
                range(node.left, rect, q);
                range(node.right, rect, q);
            }
        }

        private Point2D nearest(KdNode node, Point2D p, Point2D c, boolean xcmp) {
            Point2D closest = c;
            if (node == null) return closest;
            if (node.p.distanceSquaredTo(p) < closest.distanceSquaredTo(p))
                closest = node.p;
            if (node.r.distanceSquaredTo(p) < closest.distanceSquaredTo(p)) {
                // Find which subtree the p is in
                KdNode near;
                KdNode far;
                if ((xcmp && (p.x() < node.p.x())) || (!xcmp && (p.y() < node.p.y()))) {
                    near = node.left;
                    far = node.right;
                }
                else {
                    near = node.right;
                    far = node.left;
                }
                closest = nearest(near, p, closest, !xcmp);
                closest = nearest(far, p, closest, !xcmp);
            }
            return closest;
        }
    }
    public class RectHV {
        private final double xmin, ymin;   // minimum x- and y-coordinates
        private final double xmax, ymax;   // maximum x- and y-coordinates

        // construct the axis-aligned rectangle [xmin, xmax] x [ymin, ymax]
        public RectHV(double xmin, double ymin, double xmax, double ymax) {
            if (xmax < xmin || ymax < ymin) {
                throw new IllegalArgumentException("Invalid rectangle");
            }
            this.xmin = xmin;
            this.ymin = ymin;
            this.xmax = xmax;
            this.ymax = ymax;
        }

        // accessor methods for 4 coordinates
        public double xmin() { return xmin; }
        public double ymin() { return ymin; }
        public double xmax() { return xmax; }
        public double ymax() { return ymax; }

        // width and height of rectangle
        public double width()  { return xmax - xmin; }
        public double height() { return ymax - ymin; }

        // does this axis-aligned rectangle intersect that one?
        public boolean intersects(RectHV that) {
            return this.xmax >= that.xmin && this.ymax >= that.ymin
                    && that.xmax >= this.xmin && that.ymax >= this.ymin;
        }

        // draw this axis-aligned rectangle
        public void draw() {
            StdDraw.line(xmin, ymin, xmax, ymin);
            StdDraw.line(xmax, ymin, xmax, ymax);
            StdDraw.line(xmax, ymax, xmin, ymax);
            StdDraw.line(xmin, ymax, xmin, ymin);
        }

        // distance from p to closest point on this axis-aligned rectangle
        public double distanceTo(Point2D p) {
            return Math.sqrt(this.distanceSquaredTo(p));
        }

        // distance squared from p to closest point on this axis-aligned rectangle
        public double distanceSquaredTo(Point2D p) {
            double dx = 0.0, dy = 0.0;
            if      (p.x() < xmin) dx = p.x() - xmin;
            else if (p.x() > xmax) dx = p.x() - xmax;
            if      (p.y() < ymin) dy = p.y() - ymin;
            else if (p.y() > ymax) dy = p.y() - ymax;
            return dx*dx + dy*dy;
        }

        // does this axis-aligned rectangle contain p?
        public boolean contains(Point2D p) {
            return (p.x() >= xmin) && (p.x() <= xmax)
                    && (p.y() >= ymin) && (p.y() <= ymax);
        }

        // are the two axis-aligned rectangles equal?
        public boolean equals(Object y) {
            if (y == this) return true;
            if (y == null) return false;
            if (y.getClass() != this.getClass()) return false;
            RectHV that = (RectHV) y;
            if (this.xmin != that.xmin) return false;
            if (this.ymin != that.ymin) return false;
            if (this.xmax != that.xmax) return false;
            if (this.ymax != that.ymax) return false;
            return true;
        }

        // return a string representation of this axis-aligned rectangle
        public String toString() {
            return "[" + xmin + ", " + xmax + "] x [" + ymin + ", " + ymax + "]";
        }

    }

    public static void main(String[] args) {
        In br = new In(args[0]);
        Point2D[] test = new Point2D[20];
        for(int i=0;i<20;i++) {
            test[i] = new Point2D(br.readDouble(),br.readDouble());
        }
        RangeSearch range = new RangeSearch();
        range.init(test);
        Point2D ll = new Point2D(0.2,0.3);
        Point2D ur = new Point2D(0.7,0.8);
        range.query(ll,ur);
    }
}

