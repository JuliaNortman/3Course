import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static javafx.geometry.Orientation.VERTICAL;

public class Viewer {
  private VBox vBox = new VBox();
  private Label label = new Label();

  public Viewer(GameLogic gameLogic, Stage stage) {
    FlowPane root = new FlowPane(VERTICAL, 10, 10);

    Scene scene = new Scene(root, 150, 200);
    stage.setScene(scene);

    Button restartBTN = new Button("restart");
    restartBTN.setOnAction(
        actionEvent -> {
          gameLogic.sendPoint(-1, -1);
          gameLogic.playerLose();
        });

    root.getChildren().addAll(restartBTN, label, vBox);

    for (int y = 0; y < 3; y++) {
      HBox hBox = new HBox();
      for (int x = 0; x < 3; x++) {
        Button b = new Button();
        int finalJ = x;
        int finalI = y;
        b.setMinSize(40, 40);
        b.setOnAction(actionEvent -> gameLogic.clicked(finalJ, finalI));

        hBox.getChildren().add(b);
      }
      vBox.getChildren().add(hBox);
    }
  }

  public void setButtonsDisabled(Boolean disabled) {
    vBox.setDisable(disabled);
  }

  public void setText(String str) {
    Platform.runLater(() -> label.setText(str));
  }

  public void restart() {
    Platform.runLater(
        () -> {
          label.setText("");
          for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
              ((Button) ((HBox) vBox.getChildren().get(i)).getChildren().get(j)).setText("");
        });
    setButtonsDisabled(false);
  }

  public void setCell(int x, int y, Grid.Cell cell) {
    Platform.runLater(
        () ->
            ((Button) ((HBox) vBox.getChildren().get(y)).getChildren().get(x))
                .setText(String.valueOf(cell)));
  }
}
