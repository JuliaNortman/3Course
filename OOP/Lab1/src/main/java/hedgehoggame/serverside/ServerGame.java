package hedgehoggame.serverside;

import java.io.IOException;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
	private static final Logger logger = LogManager.getLogger(ServerGame.class);
	
	@OnOpen
	public void onOpen(Session session) {
		game = new Game(8, 4);
		game.init();
		broadcast(session, new MessageField(game.getField(), 
				game.getWidth(), game.getHeight(), game.getGameEnd()));
		logger.debug("Connected...");
	}
	
	@OnMessage
    public void onMessage(Session session, MessageMove move) throws IOException {
       game.move(move.getDirection());
       broadcast(session, new MessageField(game.getField(), 
    		   game.getWidth(), game.getHeight(), game.getGameEnd()));
    }

    @OnClose
    public void onClose(Session session) throws IOException {
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
    	logger.error("Error ", throwable.getLocalizedMessage());
    }
    
    private void broadcast(Session session, MessageField messageField) {
    	try {
			session.getBasicRemote().sendObject(messageField);
		} catch (IOException | EncodeException e) {
			logger.error(e.getLocalizedMessage());
		}
    }
}
