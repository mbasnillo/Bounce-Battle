/*
This is a thread for the server to continously recieve messages from the cleints
and broadcast it to other client
*/


import java.net.*;
import java.io.*;
import java.lang.*;
import java.util.*;

public class Echoer implements Runnable {
	private Socket client;
    private ArrayList<Socket> clients;
    private int id;

	public Echoer(Socket client,ArrayList<Socket> clients){
		this.client = client;
        this.clients = clients;
	}


    public void run() {
            while(true){ //infinite loop of listening
                try{
                    DataInputStream in = new DataInputStream(client.getInputStream());
                    String message = in.readUTF();
                    System.out.println(message);
                    broadcast(message,client);
                }catch(IOException e){
                }
            }
    }
    public void broadcast(String message,Socket client) throws IOException{
        for(int i = 0; i < clients.size(); i++){
                try{
                    OutputStream outToClient = clients.get(i).getOutputStream();
                    DataOutputStream out = new DataOutputStream(outToClient);
                    out.writeUTF(message);
                    System.out.println(i+"\n");

                }catch(IOException e){
                }
            if(clients.get(i) != client){ //skip broadcast to the sender
                OutputStream outToClient = clients.get(i).getOutputStream();
                DataOutputStream out = new DataOutputStream(outToClient);
                out.writeUTF(message);
            }
        }
    }

}