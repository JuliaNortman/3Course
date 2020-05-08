package nurbs;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point3D;
import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Drawer3D extends Application {
    public static int SCALE = 100;

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 700;

    private static final double CAMERA_SPEED = 200;
    private static final double ROTATION_ANGLE = 5;
    private static double anchorX;
    private static double anchorY;
    private static double anchorAngleX = 0;
    private static double anchorAngleY = 0;
    private static DoubleProperty angleX = new SimpleDoubleProperty(0);
    private static DoubleProperty angleY = new SimpleDoubleProperty(0);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        //copypast

        Point.setWindowHeight(HEIGHT);

        PointLight pointLight = new PointLight();
        pointLight.setColor(Color.WHITE);

        RotationGroup root = new RotationGroup();
        root.getChildren().add(pointLight);

        NurbsSurface nurbsSurface = new NurbsSurface();
        ArrayList<Point3D> nurbsSurfacePoints3D = nurbsSurface.generatePoints();
        for (int i = 0; i < nurbsSurfacePoints3D.size(); i++){
           Point3D pp = nurbsSurfacePoints3D.get(i);
            nurbsSurfacePoints3D.set(i, new Point3D(pp.getX(), pp.getY(), pp.getZ()).multiply(SCALE));
        }

        ArrayList<Point> nurbsSurfacePoints = new ArrayList<>();
        System.out.println(nurbsSurfacePoints3D.size());
        for (Point3D p : nurbsSurfacePoints3D){
            nurbsSurfacePoints.add(new Point(p.getX(), p.getY(), p.getZ(), Color.SILVER));
        }
        root.getChildren().addAll(nurbsSurfacePoints);
//        root.getChildren().addAll(Arrays.asList(bs.getControlPoints()).forEach(p => new Point(p.getX)));

        Camera camera = new PerspectiveCamera();
        camera.setTranslateX(-600);
        camera.setTranslateZ(-700);

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.setFill(Color.SILVER);
        scene.setCamera(camera);

        stage.setTitle("NURBS");
        stage.setScene(scene);
        initKeyboardControl(stage);
        initMouseControl(stage);
        stage.show();
    }

    private void initMouseControl(Stage stage) {
        Scene scene = stage.getScene();
        Camera camera = scene.getCamera();
        RotationGroup root = (RotationGroup) scene.getRoot();

        Rotate xRotate = new Rotate(0, Rotate.X_AXIS);
        Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);

        root.getTransforms().addAll(xRotate, yRotate);
        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);

        scene.setOnMousePressed(event -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
        });

        scene.setOnMouseDragged(event -> {
            angleY.set(anchorAngleY + anchorX - event.getSceneX());
        });

        scene.setOnScroll(event -> {
            if (event.getDeltaY() > 0) {
                camera.setTranslateZ(camera.getTranslateZ() + CAMERA_SPEED);
            } else {
                camera.setTranslateZ(camera.getTranslateZ() - CAMERA_SPEED);
            }
        });
    }

    private void initKeyboardControl(Stage stage) {
        Camera camera = stage.getScene().getCamera();
        RotationGroup root = (RotationGroup) stage.getScene().getRoot();

        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case W:
                    root.setTranslateY(root.getTranslateY() + CAMERA_SPEED);
                    break;
                case S:
                    root.setTranslateY(root.getTranslateY() - CAMERA_SPEED);
                    break;
                case D:
                    root.setTranslateX(root.getTranslateX() - CAMERA_SPEED);
                    break;
                case A:
                    root.setTranslateX(root.getTranslateX() + CAMERA_SPEED);
                    break;
                case UP:
                    root.rotateByX(-ROTATION_ANGLE);
                    break;
                case DOWN:
                    root.rotateByX(ROTATION_ANGLE);
                    break;
                case RIGHT:
                    root.rotateByY(ROTATION_ANGLE);
                    break;
                case LEFT:
                    root.rotateByY(-ROTATION_ANGLE);
                    break;
            }
        });
    }
}
