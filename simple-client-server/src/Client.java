import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Client
 */
public class Client {

    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
        System.out.println("Starting Client !!!");
        int PORT = 12000;
        
        Socket socket  = new Socket("localhost", PORT);
        
        // Get Output Stream from socket
        OutputStream os = socket.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(os);
        DataOutputStream dos = new DataOutputStream(bos);
        

        InputStream is = socket.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(is);
        DataInputStream dis = new DataInputStream(bis);

        // dos.writeUTF("hello world");
        // dos.flush();
        
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        
        while (!line.equalsIgnoreCase("close")) {
            System.out.println("Sending from client: "+line);
            dos.writeUTF(line);
            dos.flush();
            
            String fromServer = dis.readUTF();
            System.out.println("From server -> "+ fromServer);
            line = sc.nextLine();
        }
        
        socket.close();
        sc.close();
    }
}