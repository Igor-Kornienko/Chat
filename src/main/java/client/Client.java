package client;

import com.google.gson.Gson;
import model.Message;
import org.glassfish.tyrus.client.ClientManager;

import javax.websocket.DeploymentException;
import javax.websocket.Session;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class Client {

    public static final String SERVER = "ws://localhost:8025/ws/chat";

    public static void main (String ...args) throws URISyntaxException, IOException, DeploymentException {
        ClientManager client = ClientManager.createClient();
        Gson gson = new Gson();
        String text;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welkome! Please enter your username:");
        String user = scanner.nextLine();
        Session session = client.connectToServer(ClientEndpoint.class, new URI(SERVER ));

        while (true) {
            Message message = new Message();
            message.setContent(scanner.nextLine());
            if (message.getContent().equals("exit")) break;
            message.setFrom(user);
            session.getBasicRemote().sendText(gson.toJson(message));
        }
    }
}
