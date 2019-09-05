package server;

import model.Message;
import model.MessageDecoder;
import model.MessageEncoder;

import javax.websocket.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@javax.websocket.server.ServerEndpoint(
        value = "/chat",
        encoders = MessageEncoder.class,
        decoders = MessageDecoder.class
)
public class ServerEndpoint {

    private Session session;
    private static Set<Session> sessions = new HashSet<>();

    @OnOpen
    public void onOpen (Session session) {
        System.out.println("Opened new session with " + session);

        this.session = session;
        sessions.add(session);
    }

    @OnMessage
    public void onMessage (Session session, Message message) {
        System.out.println(message);
        broadcast(session, message);
    }

    @OnClose
    public void onClose (Session session) {
        sessions.remove(session);
        Message message = new Message();
    }

    @OnError
    public void onError (Session session, Throwable throwable) {

    }

    private static void broadcast(Session curSession, Message message) {
        sessions.forEach(session -> {
            synchronized (session) {
                try {
                    if (!curSession.equals(session)) {
                        session.getBasicRemote().sendObject(message);
                    }
                } catch (IOException | EncodeException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
