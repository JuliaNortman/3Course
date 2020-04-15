package hedgehoggame.serverside.messages;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageMoveDecoder implements Decoder.Text<MessageMove>{
	
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
		try {
			return new ObjectMapper().readValue(s, MessageMove.class);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean willDecode(String s) {
		return (s != null);
	}
	

}
