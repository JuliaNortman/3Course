<<<<<<< HEAD
package hedgehoggame.gamelogic;

import java.util.Arrays;
import java.util.Random;

import org.apache.commons.lang3.tuple.ImmutablePair;

public class Game {
	private final Cell[] field; //game field
	private final int width; //x dimension
	private final int height; //y dimension
	private boolean gameEnd; //true if game is over
	private int appleNum; //number of apples available

	private ImmutablePair<Integer, Integer> hedgehogPosition;
	
	public Game(int xDimension, int yDimension) {
		this.width = xDimension;
		this.height = yDimension;
		field = new Cell[width*height];
	}
	
	public Cell[] getField() {
		return this.field.clone();
	}
	
	public ImmutablePair<Integer, Integer> getHedgehogPosition() {
		return this.hedgehogPosition;
	}
	

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public boolean getGameEnd() {
		return gameEnd;
	}
	
	public int getAppleNum() {
		return appleNum;
	}
	
	public void init() {
		Arrays.fill(field, Cell.EMPTY);
		
		setCell(Cell.HEDGEHOG, height-1, 0);
		hedgehogPosition = new ImmutablePair<Integer, Integer>(height-1, 0);
		int density = width*height/5;
		for(int k = 0; k < density; ++k) {
			int j = new Random().nextInt(width);
			int i = new Random().nextInt(height);		
			while(getCell(i, j) != Cell.EMPTY) {
				j = new Random().nextInt(width);
				i = new Random().nextInt(height);		
			}
			setCell(Cell.APPLE, i, j);
			appleNum++;
		}
	}
	
	public int getCellIndex(int i, int j) {
		if(i < 0 || i > height-1) {
			throw new IllegalArgumentException("Index i=" + i + " is out of array bounds");
		}
		if(j < 0 || j > width-1) {
			throw new IllegalArgumentException("Index j=" + j + " is out of array bounds");
		}
		return i*width + j;
	}
	
	public void setCell(Cell cell, int i, int j) {
		field[getCellIndex(i, j)] = cell;
	}
	
	public Cell getCell(int i, int j) {
		return field[getCellIndex(i, j)];
	}
	
	public void decreaseAppleNum() {
		appleNum--;
		if(appleNum == 0) {
			gameEnd = true;
		}
	}
	
	public void moveTop() {
		int curX = hedgehogPosition.getLeft();
		int curY = hedgehogPosition.getRight();
		
		if(curX != 0) {
			setCell(Cell.EMPTY, curX, curY);
			if(getCell(curX-1, curY) == Cell.APPLE) {
				decreaseAppleNum();
			}
			setCell(Cell.HEDGEHOG, curX-1, curY);
			hedgehogPosition = new ImmutablePair<Integer, Integer>(curX-1, curY);
		}
	}
	
	public void moveBottom() {
		int curX = hedgehogPosition.getKey();
		int curY = hedgehogPosition.getValue();
		
		if(curX != height-1) {
			if(getCell(curX+1, curY) == Cell.APPLE) {
				decreaseAppleNum();
			}
			setCell(Cell.EMPTY, curX, curY);
			setCell(Cell.HEDGEHOG, curX+1, curY);
			hedgehogPosition = new ImmutablePair<Integer, Integer>(curX+1, curY);
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
			hedgehogPosition = new ImmutablePair<Integer, Integer>(curX, curY-1);
		}
	}
	
	public void moveRight() {
		int curX = hedgehogPosition.getKey();
		int curY = hedgehogPosition.getValue();
		
		if(curY != width-1) {
			if(getCell(curX, curY+1) == Cell.APPLE) {
				decreaseAppleNum();
			}
			setCell(Cell.EMPTY, curX, curY);
			setCell(Cell.HEDGEHOG, curX, curY+1);
			hedgehogPosition = new ImmutablePair<Integer, Integer>(curX, curY+1);
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
=======
package hedgehoggame.gamelogic;

import java.util.Arrays;
import java.util.Random;

import org.apache.commons.lang3.tuple.ImmutablePair;

public class Game {
	private final Cell[] field; //game field
	private final int width; //x dimension
	private final int height; //y dimension
	private boolean gameEnd; //true if game is over
	private int appleNum; //number of apples available

	private ImmutablePair<Integer, Integer> hedgehogPosition;
	
	public Game(int xDimension, int yDimension) {
		this.width = xDimension;
		this.height = yDimension;
		field = new Cell[width*height];
	}
	
	public Cell[] getField() {
		return this.field.clone();
	}
	
	public ImmutablePair<Integer, Integer> getHedgehogPosition() {
		return this.hedgehogPosition;
	}
	

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public boolean getGameEnd() {
		return gameEnd;
	}
	
	public int getAppleNum() {
		return appleNum;
	}
	
	public void init() {
		Arrays.fill(field, Cell.EMPTY);
		
		setCell(Cell.HEDGEHOG, height-1, 0);
		hedgehogPosition = new ImmutablePair<Integer, Integer>(height-1, 0);
		int density = width*height/5;
		for(int k = 0; k < density; ++k) {
			int j = new Random().nextInt(width);
			int i = new Random().nextInt(height);		
			while(getCell(i, j) != Cell.EMPTY) {
				j = new Random().nextInt(width);
				i = new Random().nextInt(height);		
			}
			setCell(Cell.APPLE, i, j);
			appleNum++;
		}
	}
	
	public int getCellIndex(int i, int j) {
		if(i < 0 || i > height-1) {
			throw new IllegalArgumentException("Index i=" + i + " is out of array bounds");
		}
		if(j < 0 || j > width-1) {
			throw new IllegalArgumentException("Index j=" + j + " is out of array bounds");
		}
		return i*width + j;
	}
	
	public void setCell(Cell cell, int i, int j) {
		field[getCellIndex(i, j)] = cell;
	}
	
	public Cell getCell(int i, int j) {
		return field[getCellIndex(i, j)];
	}
	
	public void decreaseAppleNum() {
		appleNum--;
		if(appleNum == 0) {
			gameEnd = true;
		}
	}
	
	public void moveTop() {
		int curX = hedgehogPosition.getLeft();
		int curY = hedgehogPosition.getRight();
		
		if(curX != 0) {
			setCell(Cell.EMPTY, curX, curY);
			if(getCell(curX-1, curY) == Cell.APPLE) {
				decreaseAppleNum();
			}
			setCell(Cell.HEDGEHOG, curX-1, curY);
			hedgehogPosition = new ImmutablePair<Integer, Integer>(curX-1, curY);
		}
	}
	
	public void moveBottom() {
		int curX = hedgehogPosition.getKey();
		int curY = hedgehogPosition.getValue();
		
		if(curX != height-1) {
			if(getCell(curX+1, curY) == Cell.APPLE) {
				decreaseAppleNum();
			}
			setCell(Cell.EMPTY, curX, curY);
			setCell(Cell.HEDGEHOG, curX+1, curY);
			hedgehogPosition = new ImmutablePair<Integer, Integer>(curX+1, curY);
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
			hedgehogPosition = new ImmutablePair<Integer, Integer>(curX, curY-1);
		}
	}
	
	public void moveRight() {
		int curX = hedgehogPosition.getKey();
		int curY = hedgehogPosition.getValue();
		
		if(curY != width-1) {
			if(getCell(curX, curY+1) == Cell.APPLE) {
				decreaseAppleNum();
			}
			setCell(Cell.EMPTY, curX, curY);
			setCell(Cell.HEDGEHOG, curX, curY+1);
			hedgehogPosition = new ImmutablePair<Integer, Integer>(curX, curY+1);
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
>>>>>>> 6e554e09fa613dd761e7e858b30e50c13decd828
