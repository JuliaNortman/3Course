import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import javax.jms.JMSException;

public class Main extends Application {

  GameLogic gameLogic;

  public static void main(String[] args) {
    Application.launch(args);
  }

  @Override
  public void start(Stage stage) {

    Button buttonS = new Button("Server");
    Button buttonC = new Button("Client");

    buttonS.setOnAction(
        e -> {
          System.out.println("clickS");
          try {
            gameLogic = new GameLogic(true, stage);
          } catch (JMSException ex) {
            ex.printStackTrace();
          }
        });
    buttonC.setOnAction(
        e -> {
          try {
            gameLogic = new GameLogic(false, stage);
          } catch (JMSException ex) {
            ex.printStackTrace();
          }
        });

    FlowPane root = new FlowPane(Orientation.HORIZONTAL, 10, 10, buttonC, buttonS);
    Scene scene = new Scene(root, 150, 200);
    stage.setScene(scene);
    stage.show();
  }

    @Override
    public void stop() throws JMSException {
      gameLogic.close();
    }
}
