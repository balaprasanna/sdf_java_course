import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Client
 */
public class Client {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            Scanner sc = new Scanner(System.in);
            String fromClient = sc.nextLine();
            String fromServer = "";
            while (!fromClient.equals("close") && fromClient != null) {
                dos.writeUTF("Client sends ->" + fromClient);
                dos.flush();
                System.out.println("Sent this msg " + fromClient);

                fromServer = dis.readUTF();
                System.out.println("Got: !" + fromServer);
                fromClient = sc.nextLine();
            }

            try {
                // closing resources
                socket.close(); // when line is "close"
                dis.close();
                dos.close();

            } catch (IOException e) {
                System.out.println("IO Error");
            }

        } catch (IOException e) {
            System.out.println("Error I/O" + e.getMessage());
        }
    }
}