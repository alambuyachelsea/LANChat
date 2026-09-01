package src;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class ChatClient extends WebSocketClient {

    public ChatClient(String serverAddress) throws URISyntaxException {
        super(new URI(serverAddress));
    }

    public static Scanner scanner = new Scanner(System.in);

    @Override
    public void onOpen(ServerHandshake handshakedata) {

        System.out.println("Connection to chat server successful:");
        System.out.println("Type a message");

        try {

            while (true) {

                String message = scanner.nextLine();

                if (message.equals("/x")) {

                    close();
                }

                send(message);
                System.out.println("Message sent to server.");
                
            }
        } catch (Exception e) {
            System.err.println(e);
        }

    }

    @Override
    public void onMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {

        System.err.println("Chat connection closed");
    }

    @Override
    public void onError(Exception ex) {
        System.err.println(ex);
    }

    public static void main(String[] args) {

        try {
            System.out.println("Enter the server ip address: ");

            String ip = scanner.nextLine();

            try {
                ChatClient client = new ChatClient("ws://" + ip + ":8888" );
                client.connect();
            } catch (URISyntaxException e) {
                
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.err.println(e);
        }
 

    }

}