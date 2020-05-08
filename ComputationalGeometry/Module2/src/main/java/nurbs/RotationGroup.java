package nurbs;

import javafx.scene.Group;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

public class RotationGroup extends Group {
    private Rotate r;
    private Transform t = new Rotate();

    public void rotateByX(double angle) {
        r = new Rotate(angle, Rotate.X_AXIS);
        t = t.createConcatenation(r);
        this.getTransforms().clear();
        this.getTransforms().add(t);
    }

    public void rotateByY(double angle) {
        r = new Rotate(angle, Rotate.Y_AXIS);
        t = t.createConcatenation(r);
        this.getTransforms().clear();
        this.getTransforms().add(t);
    }

    void rotateByZ(double angle) {
        r = new Rotate(angle, Rotate.Z_AXIS);
        t = t.createConcatenation(r);
        this.getTransforms().clear();
        this.getTransforms().add(t);
    }
}
