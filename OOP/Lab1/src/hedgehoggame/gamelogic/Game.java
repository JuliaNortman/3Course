package hedgehoggame.gamelogic;

import java.util.Random;


public class Game {
	private final Cell[][] field; //game field
	private final int N; //x dimension
	private final int M; //y dimension

	private Pair<Integer, Integer> hedgehogPosition;
	
	public Game(int xDimension, int yDimension) {
		this.N = xDimension;
		this.M = yDimension;
		field = new Cell[N][M];
	}
	
	public void init() {
		int density = new Random().nextInt(N*M/5);
		for(int i = 0; i < density; ++i) {
			int x = new Random().nextInt(N);
			int y = new Random().nextInt(M);		
			while(field[x][y] != Cell.EMPTY) {
				x = new Random().nextInt(N);
				y = new Random().nextInt(M);	
			}
			field[x][y] = Cell.APPLE;
		}
		field[N-1][0] = Cell.HEDGEHOG;
		hedgehogPosition = new Pair<Integer, Integer>(N-1, 0);
	}
	
	public void moveTop() {
		int curX = hedgehogPosition.getKey();
		int curY = hedgehogPosition.getValue();
		
		if(curX != 0) {
			field[curX][curY] = Cell.EMPTY;
			field[curX-1][curY] = Cell.HEDGEHOG;
			hedgehogPosition = new Pair<Integer, Integer>(curX-1, curY);
		}
	}
	
	public void moveBottom() {
		int curX = hedgehogPosition.getKey();
		int curY = hedgehogPosition.getValue();
		
		if(curX != N-1) {
			field[curX][curY] = Cell.EMPTY;
			field[curX+1][curY] = Cell.HEDGEHOG;
			hedgehogPosition = new Pair<Integer, Integer>(curX+1, curY);
		}
	}
	
	public void moveLeft() {
		int curX = hedgehogPosition.getKey();
		int curY = hedgehogPosition.getValue();
		
		if(curY != 0) {
			field[curX][curY] = Cell.EMPTY;
			field[curX][curY-1] = Cell.HEDGEHOG;
			hedgehogPosition = new Pair<Integer, Integer>(curX, curY-1);
		}
	}
	
	public void moveRight() {
		int curX = hedgehogPosition.getKey();
		int curY = hedgehogPosition.getValue();
		
		if(curY != M-1) {
			field[curX][curY] = Cell.EMPTY;
			field[curX+1][curY] = Cell.HEDGEHOG;
			hedgehogPosition = new Pair<Integer, Integer>(curX, curY+1);
		}
	}
}
