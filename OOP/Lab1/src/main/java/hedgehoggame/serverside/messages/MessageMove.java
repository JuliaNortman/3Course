package hedgehoggame.serverside.messages;

import java.io.Serializable;

import hedgehoggame.gamelogic.Direction;

public class MessageMove implements Serializable{
	private Direction direction;
	
	public MessageMove() {}

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
