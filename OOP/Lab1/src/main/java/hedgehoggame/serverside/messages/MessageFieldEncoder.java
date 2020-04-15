package hedgehoggame.serverside.messages;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageFieldEncoder implements Encoder.Text<MessageField>{

	
	
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
