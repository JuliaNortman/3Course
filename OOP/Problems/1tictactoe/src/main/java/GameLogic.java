import javafx.stage.Stage;

import javax.jms.JMSException;
import java.awt.*;

public class GameLogic {

  private MessageManager messageManager;
  private Grid.Cell myCell;
  private Grid.Cell anotherCell;
  private Grid grid = new Grid();
  private Viewer viewer;
  private boolean myTurn;

  public GameLogic(boolean isServer, Stage stage) throws JMSException {
    messageManager = new MessageManager(isServer, this);

    viewer = new Viewer(this, stage);
    myTurn = isServer;
    if (myTurn) viewer.setText("   Play!");
    else viewer.setText("   Wait...");
    myCell = isServer ? Grid.Cell.X : Grid.Cell.O;
    anotherCell = isServer ? Grid.Cell.O : Grid.Cell.X;
    waitAnotherPlayer();
  }

  public void close() throws JMSException {
    sendPoint(-1, -1);
    messageManager.end();
  }

  public void clicked(int x, int y) {
    if (!myTurn) return;
    if (grid.setCell(x, y, myCell)) {
      viewer.setCell(x, y, myCell);
      viewer.setText("   Wait...");
      messageManager.sendPoint(x, y);
      myTurn = false;
      if (grid.getWinner() == myCell) {
        playerWin();
      } else if (!grid.canMakeNextStep()) {
        nobodyWin();
      }
    }
  }

  public void waitAnotherPlayer() {
    messageManager.getPoint();
  }

  public void nobodyWin() {
    viewer.setText("   nobody Win");
    restart();
  }

  public void playerWin() {
    viewer.setText("   YOU WIN :)");
    restart();
  }

  public void playerLose() {
    viewer.setText("   YOU LOSE :(");
    restart();
  }

  public void restart() {
    viewer.setButtonsDisabled(true);
    Thread thread =
        new Thread(
            () -> {
              try {
                Thread.sleep(3000);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
              viewer.restart();
              grid.restart();
              if (myTurn) viewer.setText("   Play!");
              else viewer.setText("   Wait...");
            });
    thread.start();
  }

  public void anotherPlayerStep(Point p) {
    if (p.x < 3 && p.x >= 0 && p.y < 3 && p.y >= 0) {
      grid.setCell(p.x, p.y, anotherCell);
      viewer.setCell(p.x, p.y, anotherCell);
      myTurn = true;
      viewer.setText("   Play!");
      System.out.println(grid.getWinner());
      if (grid.getWinner() == anotherCell) playerLose();
      else if (!grid.canMakeNextStep()) nobodyWin();
    } else {
      playerWin();
    }
    waitAnotherPlayer();
  }

  public void sendPoint(int x, int y) {
    messageManager.sendPoint(x, y);
  }
}
