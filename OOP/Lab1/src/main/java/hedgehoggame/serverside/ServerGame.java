package hedgehoggame.serverside;

import java.io.IOException;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import hedgehoggame.gamelogic.Game;
import hedgehoggame.serverside.messages.MessageField;
import hedgehoggame.serverside.messages.MessageFieldEncoder;
import hedgehoggame.serverside.messages.MessageMove;
import hedgehoggame.serverside.messages.MessageMoveDecoder;

@ServerEndpoint(
		value = "/hedgehog",
		decoders = MessageMoveDecoder.class,
		encoders = MessageFieldEncoder.class)
public class ServerGame {
	
	private Game game;
	private Session session;
	
	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		game = new Game(6, 6);
		game.init();
		broadcast(new MessageField(game.getField()));
	}
	
	@OnMessage
    public void onMessage(MessageMove move) throws IOException {
       game.move(move.getDirection());
       broadcast(new MessageField(game.getField()));
    }

    @OnClose
    public void onClose() throws IOException {
       //WebSocket connection closes
    }

    @OnError
    public void onError(Throwable throwable) {
    }
    
    private void broadcast(MessageField messageField) {
    	try {
			session.getBasicRemote().sendObject(messageField);
		} catch (IOException | EncodeException e) {
			e.printStackTrace();
		}
    }
}
