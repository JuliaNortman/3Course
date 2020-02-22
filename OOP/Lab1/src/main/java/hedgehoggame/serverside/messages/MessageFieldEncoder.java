package hedgehoggame.serverside.messages;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

public class MessageFieldEncoder implements Encoder.Text<MessageField>{

	private static Gson gson = new Gson();
	
	@Override
	public void init(EndpointConfig config) {
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String encode(MessageField message) throws EncodeException {
		return gson.toJson(message);
	}

	
}
