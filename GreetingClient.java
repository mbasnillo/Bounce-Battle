import java.net.*;
import java.io.*;
import java.util.Scanner;

public class GreetingClient
{
   public static void main(String [] args)
   {
  
      try
      {
   	  String serverName = args[0]; //get IP address of server from first param
	      int port = Integer.parseInt(args[1]); //get port from second param
	      String name = args[2]; //get message from the third param
	     
	      String message = "";
         /* Open a ClientSocket and connect to ServerSocket */
         System.out.println("Connecting to " + serverName + " on port " + port);
         //insert missing line here for creating a new socket for client and binding it to a port
         Socket client = new Socket(serverName, port);

	      
         System.out.println("Just connected to " + client.getRemoteSocketAddress());

         OutputStream outToServer = client.getOutputStream();
         DataOutputStream out = new DataOutputStream(outToServer);

         Thread send2Server = new Thread(new SendToServer(client,out,name));
         Thread reciever = new Thread(new Reciever(client,name));

         send2Server.start();
         reciever.run();

         client.close();

      }catch(IOException e)
      {
         //e.printStackTrace();
      	System.out.println("Cannot find Server");
      }catch(ArrayIndexOutOfBoundsException e)
      {
         System.out.println("Usage: java GreetingClient <server ip> <port no.> '<your message to the server>'");
      }
   }
}




