import junit.framework.TestCase;

public class GridTest extends TestCase {
  private final Grid grid = new Grid();

  public void testRestart() {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        assertTrue(grid.setCell(i, j, Grid.Cell.X));
        assertFalse(grid.setCell(i, j, Grid.Cell.X));
        if (i != 2 || j != 2) assertTrue(grid.canMakeNextStep());
      }
    }
    assertFalse(grid.canMakeNextStep());
    grid.restart();
    assertTrue(grid.canMakeNextStep());
    assertTrue(grid.setCell(0, 0, Grid.Cell.X));
  }

  public void testGetWinner() {
    for (int i = 0; i < 3; i++) {

      for (int j = 0; j < 3; j++) {
        grid.setCell(i, j, Grid.Cell.X);
        if (j != 2) assertNotSame(grid.getWinner(), Grid.Cell.X);
      }
      assertEquals(grid.getWinner(), Grid.Cell.X);
      grid.restart();
    }

    for (int i = 0; i < 3; i++) {
      grid.setCell(i, i, Grid.Cell.O);
      if (i != 2) assertNotSame(grid.getWinner(), Grid.Cell.O);
    }
    assertEquals(grid.getWinner(), Grid.Cell.O);
  }

  public void testSetCell() {
    for (int i = 0; i < 3; i++) {

      for (int j = 0; j < 3; j++) {
        assertNotSame(grid.getGrid()[j][i], Grid.Cell.X);
        grid.setCell(i, j, Grid.Cell.X);
        assertEquals(grid.getGrid()[j][i], Grid.Cell.X);
      }
    }
  }
}
