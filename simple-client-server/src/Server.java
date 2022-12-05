import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;

/**
 * Server
 */
public class Server {

    public static void main(String[] args) throws IOException {
        System.out.println("Starting Server !!!");
        
        int PORT = 12000;
        ServerSocket server;
        Socket socket;
        
        server = new ServerSocket(PORT);
        socket = server.accept();
        
        InputStream is = socket.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(is);
        DataInputStream dis = new DataInputStream(bis);

        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

        String clientLine = dis.readUTF();
        while (!clientLine.equals("close") && clientLine != null) {
            if(clientLine.equalsIgnoreCase("get-cookie")) {
                dos.writeUTF("cookie-text: "+ new Cookie().RandomlyReturnACookie());
                dos.flush();    
            } else {
            System.out.println("Got -> : " + clientLine);
            dos.writeUTF("Client ACK -> " + clientLine +" @" +Calendar.getInstance().getTime().toString());
            dos.flush();
            }
            clientLine = dis.readUTF();
        }

        socket.close();
        server.close();
    }
}
