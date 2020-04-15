package hedgehoggame.serverside.messages;

import java.io.Serializable;

import hedgehoggame.gamelogic.Cell;

public class MessageField implements Serializable {
	private Cell[] field;
	private int xDim;
	private int yDim;
	private boolean gameOver;
	public MessageField() {}
	
	public MessageField(Cell[] field, int xDim, int yDim, boolean gameOver) {
		this.field = field;
		this.xDim = xDim;
		this.yDim = yDim;
		this.gameOver = gameOver;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public int getxDim() {
		return xDim;
	}

	public void setxDim(int xDim) {
		this.xDim = xDim;
	}

	public int getyDim() {
		return yDim;
	}

	public void setyDim(int yDim) {
		this.yDim = yDim;
	}

	public Cell[] getField() {
		return field;
	}

	public void setField(Cell[] field) {
		this.field = field;
	}
	
}
