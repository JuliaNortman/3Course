package hedgehoggame.serverside;

import hedgehoggame.gamelogic.Cell;

public class MessageMove {
	private Cell to;

	public MessageMove(Cell to) {
		this.to = to;
	}

	public Cell getTo() {
		return to;
	}

	public void setTo(Cell to) {
		this.to = to;
	}

}
