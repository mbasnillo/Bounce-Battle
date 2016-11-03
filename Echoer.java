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
            
            while(true){
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
            if(clients.get(i) != client){
                OutputStream outToClient = clients.get(i).getOutputStream();
                DataOutputStream out = new DataOutputStream(outToClient);
                out.writeUTF(message);
            }
        }
    }

}