package hedgehoggame.serverside;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import hedgehoggame.gamelogic.Game;

@ServerEndpoint(value = "/hedgehog")
public class ServerGame {
	
	private Game game;
	
	@OnOpen
	public void onOpen(Session session) {
		game = new Game(6, 6);
		game.init();
		broadcast(new MessageField(game.getField()));
	}
	
	@OnMessage
    public void onMessage(MessageMove move) throws IOException {
       //Handle new messages
    }

    @OnClose
    public void onClose() throws IOException {
       //WebSocket connection closes
    }

    @OnError
    public void onError(Throwable throwable) {
    }
    
    private static void broadcast(MessageField messageField) {
    	
    }
}
