/*
This is the java file for the clients. This should be run after running the java file for the server

It can be run using "java Client <server ip> <port no.> <name>"
*/


import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client{
   public static void main(String [] args){
      try{
   	  String serverName = args[0]; //get IP address of server from first param
	      int port = Integer.parseInt(args[1]); //get port from second param
	      String name = args[2]; //get name from the third param
	      String message = "";

         /* Open a ClientSocket and connect to ServerSocket */
         System.out.println("Connecting to " + serverName + " on port " + port);
         //creating a new socket for client and binding it to a port
         Socket client = new Socket(serverName, port);
         System.out.println("Just connected to " + client.getRemoteSocketAddress());

         OutputStream outToServer = client.getOutputStream();
         DataOutputStream out = new DataOutputStream(outToServer);

         //these 2 threads runs concurrently
         Thread sender = new Thread(new Sender(client,out,name)); //this thread is for a client to send message to server
         Thread reciever = new Thread(new Reciever(client,name)); //this thread is for a client to recienve message from the server

         sender.start();
         reciever.run();

         client.close();

      }catch(IOException e){
         //e.printStackTrace();
      	System.out.println("Cannot find Server");
      }catch(ArrayIndexOutOfBoundsException e){
         System.out.println("Usage: java GreetingClient <server ip> <port no.> '<your message to the server>'");
      }
   }
}




