   // File Name GreetingServer.java

import java.net.*;
import java.io.*;
import java.lang.*;


public class GreetingServer extends Thread
{
   private ServerSocket serverSocket;
   
   public GreetingServer(int port) throws IOException
   {
      //insert missing line here for binding a port to a socket
      serverSocket = new ServerSocket(port);
      //serverSocket.setSoTimeout(20000);
   }

   public void run()
   {
      boolean connected = true;
      while(connected)
      {
         try
         {
            System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
            Thread[] nodes_sender = new Thread[100];
            Thread[] nodes_reciever = new Thread[100];
            Socket[] servers = new Socket[100];
            int count = 0;
            while(true){
            	Socket server = serverSocket.accept();
     		 	System.out.println("Just connected to " + server.getRemoteSocketAddress());
     		 	servers[count] = server;
     		 	nodes_sender[count] = new Thread(new Sender(server,servers,count));
     		 	nodes_sender[count].start();
     		 	// nodes_reciever[count] = new Thread(new Reciever(server,servers,count));
     		 	// nodes_reciever[count].start();
     		 	count++;
            }
    		
            
            /* Start accepting data from the ServerSocket */
            //insert missing line for accepting connection from client here]
            
            

            
            //DataOutputStream out = new DataOutputStream(server.getOutputStream());
           
            /* Send data to the ClientSocket */
            //out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress() + "\nGoodbye!");
            //server.close();
           // connected = false;
            //System.out.println("Server ended the connection to "+ server.getRemoteSocketAddress());
         }catch(SocketTimeoutException s)
         {
            System.out.println("Socket timed out!");
            break;
         }catch(IOException e)
         {
            //e.printStackTrace();
            System.out.println("Usage: java GreetingServer <port no.>");
            break;
         }
      } 
   }
   public static void main(String [] args)
   {
      
      try
      {
         int port = Integer.parseInt(args[0]);
         Thread t = new GreetingServer(port);
         t.start();
      }catch(IOException e)
      {
         //e.printStackTrace();
         System.out.println("Usage: java GreetingServer <port no.>");
      }catch(ArrayIndexOutOfBoundsException e)
      {
         System.out.println("Usage: java GreetingServer <port no.> ");
      }
   }
}