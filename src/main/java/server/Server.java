package server;

import javax.websocket.DeploymentException;
import java.util.Scanner;

public class Server {
    public static void main (String ...args) {

        org.glassfish.tyrus.server.Server server = new org.glassfish.tyrus.server.Server("localhost", 8025, "/ws", null, ServerEndpoint.class);

        try {
            server.start();
            System.out.println("Press any key to stop server...");
            new Scanner(System.in).nextLine();
        } catch (DeploymentException e) {
            throw new RuntimeException(e);
        } finally {
            server.stop();
        }
    }
}
