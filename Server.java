import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Server {

    public final static int SERVER_PORT = 7; // Cổng mặc định của Echo Server
    public final static byte[] BUFFER = new byte[4096]; // Vùng đệm chứa dữ liệu cho gói tin nhận

    public static void main(String[] args) {
        DatagramSocket lhtd = null;
        try {
            lhtd = new DatagramSocket(SERVER_PORT); // Tạo Socket với cổng là 7
            System.out.println("Binding to port " + SERVER_PORT + ", please wait  ...");
            System.out.println("Server started ");
            System.out.println("Waiting for messages from Client ... ");

            while (true) { // Tạo gói tin nhận
                DatagramPacket incoming = new DatagramPacket(BUFFER, BUFFER.length);
                lhtd.receive(incoming); // Chờ nhận gói tin gởi đến

                // Lấy dữ liệu khỏi gói tin nhận
                
                String message = new String(incoming.getData(), 0, incoming.getLength());
                System.out.println("Received: " + message);
                StringTokenizer stringTokenizer = new StringTokenizer(message," ");  
                int max = Integer.MIN_VALUE;
                while(stringTokenizer.hasMoreTokens())
                {
                    int number = Integer.parseInt(stringTokenizer.nextToken());
                    if (number > max){max = number;}
                }
                System.out.println("Max: " + max);
                String toClient = "Max: " + max;
                byte[] data = toClient.getBytes();

                // Tạo gói tin gởi chứa dữ liệu vừa nhận được
                DatagramPacket outsending = new DatagramPacket(data, data.length,
                        incoming.getAddress(), incoming.getPort());
                lhtd.send(outsending);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (lhtd != null) {
                lhtd.close();
            }
        }
    }
}