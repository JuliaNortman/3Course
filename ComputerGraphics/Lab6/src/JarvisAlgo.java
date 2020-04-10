import java.awt.geom.Point2D;
import java.util.ArrayList;



public class JarvisAlgo {
    ArrayList<Point2D> points;
    public static final double INF = 9999999;
    public static final double EPS = 0.00001;

    public JarvisAlgo (ArrayList<Point2D> points){
        this.points = points;
    }

    public ArrayList<Point2D> getConvexHall(){
        ArrayList<Point2D> convexHull = new ArrayList<>();

        int downPointIndex = 0;
        for (int i=0; i<points.size(); i++){
            if (points.get(i).getY() < points.get(downPointIndex).getY()){
                downPointIndex = i;
            }
        }

        boolean[] used = new boolean[points.size()];
        convexHull.add(points.get(downPointIndex));

        int prevOrigin = -1;
        int originIndex = downPointIndex;
        for (int i=0; i<points.size(); i++){
            if (used[i]) {
                continue;
            }

            int nextOrigin = findPointIndexWithBiggestAngle(points, prevOrigin, originIndex , used);
            used[originIndex] = true;
            used[downPointIndex] = false;
            if ((originIndex < 0) || (nextOrigin == downPointIndex)){
                break;
            }
            else {
                convexHull.add(points.get(nextOrigin));
                prevOrigin = originIndex;
                originIndex = nextOrigin;
            }
        }

        return convexHull;
    }

    public static double lineSegmentLength(Point2D a, Point2D b){
        return Math.pow(Math.pow(b.getX() - a.getX(), 2) + Math.pow(b.getY() - a.getY(), 2) ,0.5);
    }

    public static double angleBetweenTwoVectorsWithTheSameOrigin(Point2D a, Point2D b1, Point2D b2){
        double vectModab1 = Math.pow(Math.pow(b1.getX() - a.getX(), 2) + Math.pow(b1.getY() - a.getY(), 2), 0.5);
        double vectModab2 = Math.pow(Math.pow(b2.getX() - a.getX(), 2) + Math.pow(b2.getY() - a.getY(), 2), 0.5);

        return Math.acos(((b1.getX() - a.getX())*(b2.getX() - a.getX()) + (b1.getY() - a.getY())*(b2.getY() - a.getY()))
                / (vectModab1 * vectModab2));
    }

    public static int findPointIndexWithBiggestAngle(ArrayList<Point2D> points, int prevPointIndex, int originPointIndex,
                                                     boolean[] used){
        int index = 0;
        double angle = 0.0;
        boolean unusedRemain = false;

        double prevDistance = 0.0;
        int prevPointConsideredIndex = 0;

        for (int i=0; i<points.size(); i++){
            if (used[i] || i==prevPointIndex || i==originPointIndex){
                continue;
            }

            unusedRemain = true;
            double curAngle = 0.0;
            if (prevPointIndex < 0){
                curAngle = angleBetweenTwoVectorsWithTheSameOrigin(points.get(originPointIndex),
                        new Point2D.Double(INF, points.get(originPointIndex).getY()),
                        points.get(i));
            } else {
                curAngle = angleBetweenTwoVectorsWithTheSameOrigin(points.get(originPointIndex),
                        points.get(prevPointIndex), points.get(i));
            }
            double curDistance = lineSegmentLength(points.get(originPointIndex), points.get(i));

            if (Math.abs(curAngle - angle) < EPS){ //equality check
                if (curDistance >= prevDistance){
                    if (prevPointConsideredIndex >= 0){
                        used[prevPointConsideredIndex] = true;
                    }
                    prevPointConsideredIndex = index;
                    index = i;
                    angle = curAngle;
                    prevDistance = curDistance;
                }
            }
            else if (curAngle > angle){
                prevPointConsideredIndex = index;
                index = i;
                angle = curAngle;
                prevDistance = curDistance;
            }
        }

        return unusedRemain ? index : -1;
    }
}
