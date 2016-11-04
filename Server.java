   // File Name Server.java
/*
This is the java file for the server. This should be run first before any client

It can be run using "java Server <port no>""
*/


import java.net.*;
import java.io.*;
import java.lang.*;
import java.util.*;


public class Server extends Thread{
   private ServerSocket serverSocket;
   
   public Server(int port) throws IOException{
      serverSocket = new ServerSocket(port);
   }

   public void run(){
      boolean connected = true;
      while(connected){
         try{
            System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
            Thread[] nodes_reciever = new Thread[100]; //threads of each client
            ArrayList<Socket> clients = new ArrayList<Socket>(100); //keeps track of all clients connected in an arraylist
            int count = 0;
            while(true){ //infinite loop of waiting for any connection in any number of clients
            	Socket client = serverSocket.accept(); //there is a connection from a client
      		 	System.out.println("Just connected to " + client.getRemoteSocketAddress());
      		 	clients.add(client); //add the newly connected client to the list of clients
      		 	nodes_reciever[count] = new Thread(new Echoer(client,clients)); //instantiate a thread for listening message from the clients
      		 	nodes_reciever[count].start();
      		 	count++;
            }   
         }catch(SocketTimeoutException s){
            System.out.println("Socket timed out!");
            break;
         }catch(IOException e){
            //e.printStackTrace();
            System.out.println("Usage: java Server <port no.>");
            break;
         }
      } 
   }
   public static void main(String [] args){
      try{
         int port = Integer.parseInt(args[0]);
         Thread t = new Server(port);
         t.start();
      }catch(IOException e){
         //e.printStackTrace();
         System.out.println("Usage: java Server <port no.>");
      }catch(ArrayIndexOutOfBoundsException e){
         System.out.println("Usage: java Server <port no.> ");
      }
   }
}
