import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {

    public final static String SERVER_IP = "192.168.1.13";//"10.10.11.27";
    public final static int SERVER_PORT = 7; 
    public final static byte[] BUFFER = new byte[4096]; 

    public static void main(String[] args) {
        DatagramSocket lhtd = null;
        try {
            lhtd = new DatagramSocket(); 
            System.out.println("Client started ");
            InetAddress server = InetAddress.getByName(SERVER_IP);
            while (true) {
                System.out.println("Enter your message: ");
                InputStreamReader nhap = new InputStreamReader(System.in);
                BufferedReader br = new BufferedReader(nhap); 
                String theString = br.readLine(); 
                byte[] data = theString.getBytes(); 
                DatagramPacket dp = new DatagramPacket(data, data.length, server, SERVER_PORT);
                lhtd.send(dp);
                DatagramPacket incoming = new DatagramPacket(BUFFER, BUFFER.length);
                lhtd.receive(incoming); 
                System.out.println("Received: " + new String(incoming.getData(), 0, incoming.getLength()));
            }
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            if (lhtd != null) {
                lhtd.close();
            }
        }
    }
}