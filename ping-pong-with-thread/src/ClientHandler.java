import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread {
    
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    public ClientHandler(Socket socket, DataInputStream dis, DataOutputStream dos){
        this.socket = socket;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
	public void run()
	{
        String fromClient;
        try {
            fromClient = this.dis.readUTF();
            System.out.println("Got: !" + fromClient);
           
            while (!fromClient.equals("close") && fromClient != null) {
                this.dos.writeUTF("pong from Thread !! ->" + Thread.currentThread().getName());
                this.dos.flush();
                
                fromClient = this.dis.readUTF();
                System.out.println("Got: !" + fromClient);
            }
           
            try
            {
                // closing resources
                socket.close(); // when line is "close"
                this.dis.close();
                this.dos.close();
                  
            }catch(IOException e){
                System.out.println("IO Error");
            }

        } catch (IOException e) {
            System.out.println("IO Error");
        }

    }
    
}
