public class Grid {
  enum Cell {
    space,
    X,
    O
  }

  Grid() {
    restart();
  }

  public Cell[][] getGrid() {
    return grid;
  }

  private Cell[][] grid = new Cell[3][3];

  public void restart() {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        grid[i][j] = Cell.space;
      }
    }
  }


  public Cell getWinner() {
    for (int i = 0; i < 3; i++) {
      if (grid[i][0] == grid[i][1] && grid[i][0] == grid[i][2] && grid[i][0] != Cell.space)
        return grid[i][0];
      if (grid[0][i] == grid[1][i] && grid[0][i] == grid[2][i] && grid[0][i] != Cell.space)
        return grid[0][i];
    }
    if (grid[0][0] == grid[1][1] && grid[0][0] == grid[2][2] && grid[1][1] != Cell.space)
      return grid[1][1];
    if (grid[2][0] == grid[1][1] && grid[2][0] == grid[0][2] && grid[1][1] != Cell.space)
      return grid[1][1];
    return Cell.space;
  }

  public Boolean canMakeNextStep() {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (grid[i][j] == Cell.space) return true;
      }
    }
    return false;
  }

  public Boolean setCell(int x, int y, Cell cell) {
    if (grid[y][x] != Cell.space) return false;
    grid[y][x] = cell;
    return true;
  }
}
