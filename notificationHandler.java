import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;
  
// Server class
public class notificationHandler 
{
	 volatile static List<Socket> clients = new ArrayList<Socket>();
	 volatile static boolean engageBoolean = false;
	  
    public static void main(String[] args) throws IOException 
    {

        // server is listening on port 5056
        ServerSocket ss = new ServerSocket(5056);
          
        // running infinite loop for getting
        // client request

 	   class broadCastThread implements Runnable {
			  
			  

	       public void run(){
	    	   
	    	
	     
	    	   while(true) {
	    		   
	    		
	    		   if(engageBoolean == true) {
	    		    for(Socket x : clients) {
	    		    	try {
	    		    	 DataOutputStream dos2 = new DataOutputStream(x.getOutputStream());
	    		    	 	dos2.writeUTF("BROADCAST MESSAGE");
	    		    	}
	    		    	catch(IOException dad) {}
	    		   
	    		    
	    		    	}
	    		    engageBoolean = false;
	    	   }
	    		    }
	    	
	    	   
	    	
	       }//WHILE TRUE
	       }
	       
		
		 Thread t1 = new Thread(new broadCastThread());
		 t1.start(); 
        
 
        
        
        
        while (true) 
        {
            Socket s = null;
              
            try 
            {
                // socket object to receive incoming client requests
                s = ss.accept();
                
                
                System.out.println("A new client is connected : " + s);
                  
                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                String received = dis.readUTF();
                System.out.println("Recieved: " + received);
                if(received.equals("BROADCAST")) {
                	  String message = dis.readUTF();
                	   for(Socket x : clients) {
   	    		    	try {
   	    		    	 DataOutputStream dos2 = new DataOutputStream(x.getOutputStream());
   	    		    	 	dos2.writeUTF(message);
   	    		    	}
   	    		    	catch(IOException dad) {}
   	    		   
   	    		    
   	    		    	}
                	   s.close();
                	   dis.close();
                	   dos.close();
                	 
                }else {
                 

                  
                System.out.println("Assigning new thread for this client");
                String x = s.toString(); 
  
                // create a new thread object
                Thread t = new ConnectionToClient (s, dis, dos);
              
                // Invoking the start() method
                t.start();
                clients.add(s);
            }
            }
            catch (Exception e){
                s.close();
                e.printStackTrace();
            }
        }
   	

        
    }
    
    
    
}
  
// ClientHandler class
class ConnectionToClient  extends Thread 
{
    DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd");
    DateFormat fortime = new SimpleDateFormat("hh:mm:ss");
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
    
    
    
      
  
    // Constructor
    public ConnectionToClient (Socket s, DataInputStream dis, DataOutputStream dos) 
    {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }
  
    @Override
    public void run() 
    {
        String received;
        String toreturn;
        while (true) 
        {
            try {
            	// receive the answer from client
                received = dis.readUTF();
                System.out.println("Recieved: " + received);
                  
                if(received.equals("EXIT"))
                { 
                	break;
                }
                  
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
          
        try
        {
            // closing resources
            this.dis.close();
            this.dos.close();
              
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
}