package hermitespline;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class HermiteSpline {
    private final Point2D[] controlPoints;
    private final RealMatrix basisMatrix;

    private static final double eps = 0.01;

    public HermiteSpline(final double[][] controlPoints) {
        this.controlPoints = new Point2D[controlPoints.length];
        for(int i = 0; i < controlPoints.length; ++i) {
            this.controlPoints[i] = new Point2D.Double(controlPoints[i][0], controlPoints[i][1]);
        }
        this.basisMatrix = new Array2DRowRealMatrix(new double[][] {
                {2, -2, 1, 1},
                {-3, 3, -2, -1},
                {0, 0, 1, 0},
                {1, 0, 0, 0}
        });
    }

    private Point2D hermiteSplineEquation(final double u, Point2D p1, Point2D p2, Point2D p1Tan, Point2D p2Tan) {
        RealMatrix parameterMatrix = new Array2DRowRealMatrix(new double[][] {
                {u*u*u, u*u, u, 1} });

        RealMatrix controlMatrix = new Array2DRowRealMatrix(new double[][] {
                {p1.getX(), p1.getY()},
                {p2.getX(), p2.getY()},
                {p1Tan.getX(), p1Tan.getY()},
                {p2Tan.getX(), p2Tan.getY()}
        });

        RealMatrix result = parameterMatrix.multiply(basisMatrix).multiply(controlMatrix);
        return new Point2D.Double(result.getEntry(0, 0), result.getEntry(0, 1));
    }

    private Point2D tangent(int i) {
        final double s = 0.5;
        if(i == 0) {
            return new Point2D.Double(s*(controlPoints[i+1].getX()-(controlPoints[0].getX()-eps)),
                    s*(controlPoints[i+1].getY()-controlPoints[0].getY()));
        }
        if(i == controlPoints.length-1) {
            return new Point2D.Double(s*(controlPoints[controlPoints.length-1].getX()+eps-(controlPoints[i-1].getX())),
                    s*(controlPoints[controlPoints.length-1].getY()-controlPoints[i-1].getY()));
        }
        return new Point2D.Double(s*(controlPoints[i+1].getX()-(controlPoints[i-1].getX())),
                s*(controlPoints[i+1].getY()-controlPoints[i-1].getY()));
    }

    public List<Point2D> HermiteSpline() {
        List<Point2D> points = new LinkedList<>();
        for(int i = 0; i < controlPoints.length-1; ++i) {
            for(int j = 0; j <= 100; ++j) {
                points.add(hermiteSplineEquation(
                        (double)j*eps, controlPoints[i], controlPoints[i+1],
                        tangent(i), tangent(i+1)));
            }
        }
        return points;
    }

    public List<Point2D> getControlPoints() {
        return Arrays.asList(controlPoints);
    }
}
