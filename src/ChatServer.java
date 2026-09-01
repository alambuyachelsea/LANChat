package src;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import org.java_websocket.WebSocket;
import  org.java_websocket.server.WebSocketServer;
import  org.java_websocket.handshake.ClientHandshake;


public class ChatServer extends WebSocketServer {

    private List<WebSocket> clients = new ArrayList<>();

    public ChatServer (int port) {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {

        clients.add(conn);
        System.out.println("New Client connected");
        conn.send("Welcome to the LAN Chat");
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        clients.remove(conn);
        System.out.println("Client disconnected");
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        
        System.out.println("New client Message: " + message);

        for (WebSocket client : clients ){

            client.send(message);

        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.err.println(ex);
    }

    @Override
    public void onStart() {
        System.out.println("Chat server is live on port " + getPort());
    }

    public static void main(String[] args) {
        ChatServer cs = new ChatServer(8888);
        cs.start();
    }

    


}