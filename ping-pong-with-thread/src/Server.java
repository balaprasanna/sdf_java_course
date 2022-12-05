import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {

        ServerSocket server = new ServerSocket(12345);
        System.out.println("Started Server on 12345.");

        while (true) {
            Socket socket = null;
            try {
                socket = server.accept();
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                Thread myPingPongHandler = new ClientHandler(socket, dis, dos);
                myPingPongHandler.start();
            } catch (IOException e) {
                socket.close();
                System.out.println("IO/Error " + e.getMessage());
            }
        }

    }
}
