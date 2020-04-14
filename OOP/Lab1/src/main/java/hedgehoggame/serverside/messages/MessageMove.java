package hedgehoggame.serverside.messages;

import hedgehoggame.gamelogic.Direction;

public class MessageMove {
	private Direction direction;

	public MessageMove(Direction direction) {
		this.direction = direction;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

}
