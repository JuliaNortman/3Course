package hedgehoggame.gamelogic;

import java.util.Arrays;
import java.util.Random;

public class Game {
	private final Cell[] field; //game field
	private final int N; //x dimension
	private final int M; //y dimension
	private boolean gameEnd; //true if game is over
	private int appleNum; //number of apples available

	private Pair<Integer, Integer> hedgehogPosition;
	
	public Game(int xDimension, int yDimension) {
		this.N = xDimension;
		this.M = yDimension;
		field = new Cell[N*M];
	}
	
	public Cell[] getField() {
		return this.field;
	}
	
	public Pair<Integer, Integer> getHedgehogPosition() {
		return this.hedgehogPosition;
	}
	

	public int getN() {
		return N;
	}

	public int getM() {
		return M;
	}
	
	public boolean getGameEnd() {
		return gameEnd;
	}
	
	
	public void init() {
		Arrays.fill(field, Cell.EMPTY);
		
		setCell(Cell.HEDGEHOG, M-1, 0);
		hedgehogPosition = new Pair<Integer, Integer>(M-1, 0);
		int density = N*M/5;
		for(int k = 0; k < density; ++k) {
			int j = new Random().nextInt(N);
			int i = new Random().nextInt(M);		
			while(getCell(i, j) != Cell.EMPTY) {
				j = new Random().nextInt(N);
				i = new Random().nextInt(M);		
			}
			setCell(Cell.APPLE, i, j);
			appleNum++;
		}
	}
	
	public int getCellIndex(int i, int j) {
		if(i < 0 || i > M-1) {
			throw new IllegalArgumentException("Index i=" + i + " is out of array bounds");
		}
		if(j < 0 || j > N-1) {
			throw new IllegalArgumentException("Index j=" + j + " is out of array bounds");
		}
		return i*N + j;
	}
	
	public void setCell(Cell cell, int i, int j) {
		field[getCellIndex(i, j)] = cell;
	}
	
	public Cell getCell(int i, int j) {
		return field[getCellIndex(i, j)];
	}
	
	private void decreaseAppleNum() {
		appleNum--;
		if(appleNum == 0) {
			gameEnd = true;
		}
	}
	
	public void moveTop() {
		int curX = hedgehogPosition.getKey();
		int curY = hedgehogPosition.getValue();
		
		if(curX != 0) {
			setCell(Cell.EMPTY, curX, curY);
			if(getCell(curX-1, curY) == Cell.APPLE) {
				decreaseAppleNum();
			}
			setCell(Cell.HEDGEHOG, curX-1, curY);
			hedgehogPosition = new Pair<Integer, Integer>(curX-1, curY);
		}
	}
	
	public void moveBottom() {
		int curX = hedgehogPosition.getKey();
		int curY = hedgehogPosition.getValue();
		
		if(curX != M-1) {
			if(getCell(curX+1, curY) == Cell.APPLE) {
				decreaseAppleNum();
			}
			setCell(Cell.EMPTY, curX, curY);
			setCell(Cell.HEDGEHOG, curX+1, curY);
			hedgehogPosition = new Pair<Integer, Integer>(curX+1, curY);
		}
	}
	
	public void moveLeft() {
		int curX = hedgehogPosition.getKey();
		int curY = hedgehogPosition.getValue();
		
		if(curY != 0) {
			if(getCell(curX, curY-1) == Cell.APPLE) {
				decreaseAppleNum();
			}
			setCell(Cell.EMPTY, curX, curY);
			setCell(Cell.HEDGEHOG, curX, curY-1);
			hedgehogPosition = new Pair<Integer, Integer>(curX, curY-1);
		}
	}
	
	public void moveRight() {
		int curX = hedgehogPosition.getKey();
		int curY = hedgehogPosition.getValue();
		
		if(curY != N-1) {
			if(getCell(curX, curY+1) == Cell.APPLE) {
				decreaseAppleNum();
			}
			setCell(Cell.EMPTY, curX, curY);
			setCell(Cell.HEDGEHOG, curX, curY+1);
			hedgehogPosition = new Pair<Integer, Integer>(curX, curY+1);
		}
	}
	
	public void move(Direction direction) {
		switch (direction) {
		case LEFT: {
			moveLeft();
			break;
		}
		case RIGHT: {
			moveRight();
			break;
		}
		case TOP: {
			moveTop();
			break;
		}
		case BOTTOM: {
			moveBottom();
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + direction);
		}
	}

	@Override
	public String toString() {
		return "Game [field=" + Arrays.toString(field) + "]";
	}
	
	
}
