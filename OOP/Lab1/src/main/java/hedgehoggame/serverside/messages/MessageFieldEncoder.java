package hedgehoggame.serverside.messages;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageFieldEncoder implements Encoder.Text<MessageField>{

	@Override
	public void init(EndpointConfig config) {}

	@Override
	public void destroy() {}

	@Override
	public String encode(MessageField message) throws EncodeException {
		try {
			return new ObjectMapper().writeValueAsString(message);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
	}

	
}
