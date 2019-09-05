package client;

import model.Message;
import model.MessageDecoder;
import model.MessageEncoder;

import javax.websocket.OnMessage;

@javax.websocket.ClientEndpoint (
        encoders = MessageEncoder.class,
        decoders = MessageDecoder.class
)
public class ClientEndpoint {

    @OnMessage
    public void onMessage(Message message){
        System.out.println(message.getFrom() + " : " + message.getContent());
    }
}
