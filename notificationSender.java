import java.io.*;
import java.net.*;
import java.util.Scanner;
  
// notificationReciever class
public class notificationSender
{
    public static void main(String[] args) throws IOException 
    {
        try
        {
            Scanner scn = new Scanner(System.in);
              
            // getting localhost ip
            InetAddress ip = InetAddress.getByName("localhost");
      
            // establish the connection with server port 5056
            Socket s = new Socket(ip, 5056);
      
            // obtaining input and out streams
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            dos.writeUTF("BROADCAST");
       		System.out.println("MESSAGE");
            String received = scn.nextLine();

            System.out.println("Sent: " + received);
            dos.writeUTF(received);
      
            // the following loop performs the exchange of
            // information between notificationReciever and notificationReciever handler

              
            // closing resources
           // scn.close();
            dis.close();
            dos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}