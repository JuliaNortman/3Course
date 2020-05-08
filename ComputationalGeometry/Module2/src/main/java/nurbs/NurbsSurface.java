package nurbs;

import javafx.geometry.Point3D;

import java.util.ArrayList;

public class NurbsSurface {

    public static final double STEP = 0.005;

    //size is control_points_quantity + degree + 2;
    private double[] nodeSupportU = new double[]{0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1, 1.1,1.2};
    private double[] nodeSupportV = new double[]{0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1, 1.1,1.2};
    private int n = 1;
    private int m = 3;
    public static final int DEGREE_P = 3;
    public static final int DEGREE_Q = 3;

    public static Point3D[][] controlPoints = new Point3D[][]
            {
                    {
                            new Point3D(1, 1, 0), new Point3D(2, 2, 0), new Point3D(3, 2, 1), new Point3D(4, 4, 2)
                    },
                    {
                            new Point3D(5, 1, 2), new Point3D(6, 4, 3), new Point3D(7, 2, 4), new Point3D(9, 1, 5)
                    },
            };

    public ArrayList<Point3D> generatePoints(){
        ArrayList<Point3D> surfacePoints = new ArrayList<>();

        for (double u = nodeSupportU[0]; u < nodeSupportU[nodeSupportU.length - 1]; u += STEP){
            for (double v = nodeSupportV[0]; v < nodeSupportV[nodeSupportV.length - 1]; v += STEP){
                surfacePoints.add(s(u, v));
            }
        }

        return surfacePoints;
    }

    public Point3D s(double u, double v){
        double denominator = 0.0;
        Point3D pointComputed = new Point3D(0,0, 0);

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                double coef = nU(i, DEGREE_P,u) * nV(j, DEGREE_Q,v) * w(i,j);
                pointComputed = pointComputed.add(controlPoints[i][j].multiply(coef));
                denominator += (coef);
            }
        }

        return pointComputed.multiply(1.0 / denominator);
    }


    /**
     * B-spline basis function.
     * Calculated in a recursive way by the Cox and de Boor formula.
     *
     * Defined for u and for v separately because of different knot vectors.
     * */
    public double nU(int i, int p, double u){
        if (p == 0){
            if ((u >= nodeSupportU[i]) && (u <= nodeSupportU[i+1])){
                return 1.0;
            } else {
                return 0;
            }
        }

        return (((u - nodeSupportU[i]) * nU(i, p - 1, u)) / (nodeSupportU[i + p] - nodeSupportU[i]))
                + (((nodeSupportU[i+p+1] - u) * nU(i+1, p-1, u)) / (nodeSupportU[i+p+1] - nodeSupportU[i+1]));
    }

    /**
     * B-spline basis function.
     * Calculated in a recursive way by the Cox and de Boor formula.
     * v - parameter
     * p - degree
     * i - index of point (see usage in "s" function)
     *
     * Defined for u and for v separately because of different knot vectors.
     * */
    public double nV(int i, int p, double v){
        if (p == 0){
            if ((v >= nodeSupportV[i]) && (v <= nodeSupportV[i+1])){
                return 1.0;
            } else {
                return 0;
            }
        }

        return (((v - nodeSupportV[i]) * nV(i, p - 1, v)) / (nodeSupportV[i + p] - nodeSupportV[i]))
                + (((nodeSupportV[i+p+1] - v) * nV(i+1, p-1, v)) / (nodeSupportV[i+p+1] - nodeSupportV[i+1]));
    }

    /**
     * Weight function for each control point.
     * Must be set by user as a matrix.
     * For simplicity just returning 1.0
     * */
    public double w(int i, int j){
        return 1.0;
    }
}
