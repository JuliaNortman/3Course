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
	
	@OnOpen
	public void onOpen(Session session) {
		game = new Game(8, 4);
		game.init();
		System.out.println("Connected...");
		broadcast(session, new MessageField(game.getField(), 
				game.getN(), game.getM(), game.getGameEnd()));
	}
	
	@OnMessage
    public void onMessage(Session session, MessageMove move) throws IOException {
       game.move(move.getDirection());
       System.out.println("Message");
       broadcast(session, new MessageField(game.getField(), 
    		   game.getN(), game.getM(), game.getGameEnd()));
    }

    @OnClose
    public void onClose(Session session) throws IOException {
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
    	System.out.println("Error");
    }
    
    private void broadcast(Session session, MessageField messageField) {
    	try {
			session.getBasicRemote().sendObject(messageField);
		} catch (IOException | EncodeException e) {
			e.printStackTrace();
		}
    }
}
