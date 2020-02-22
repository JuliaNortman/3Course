package hedgehoggame.serverside;

import hedgehoggame.gamelogic.Cell;

public class MessageField {
	private Cell[][] field;

	public MessageField(Cell[][] field) {
		this.field = field;
	}

	public Cell[][] getField() {
		return field;
	}

	public void setField(Cell[][] field) {
		this.field = field;
	}
	
}
