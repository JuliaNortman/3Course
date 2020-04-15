package hedgehoggame.serverside.messages;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MessageMoveDecoder implements Decoder.Text<MessageMove>{

	private static Gson gson = new Gson();
	
	@Override
	public void init(EndpointConfig config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MessageMove decode(String s) throws DecodeException {
		return gson.fromJson(s, MessageMove.class);
	}

	@Override
	public boolean willDecode(String s) {
		return (s != null);
	}
	

}
