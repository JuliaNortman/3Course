package nurbs;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public class Point extends Sphere {
    private static final double DEFAULT_RADIUS = 8;
    private static int windowHeight = 600;

    private double x;
    private double y;
    private double z;

    public Point(double x, double y, double z, Color color) {
        super(DEFAULT_RADIUS);
        this.x = x;
        this.y = y;
        this.z = z;
        draw(color);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public static void setWindowHeight(int windowHeight) {
        Point.windowHeight = windowHeight;
    }

    private void draw(Color color) {
        this.translateXProperty().set(x);
        this.translateYProperty().set(windowHeight - y);
        this.translateZProperty().set(z);

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(color);
        this.setMaterial(material);
    }
}