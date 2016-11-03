import java.net.*;
import java.io.*;
import java.lang.*;
import java.util.*;

public class Sender implements Runnable {
	private Socket server;
    private ArrayList<Socket> nodes;
    private int id;

	public Sender(Socket server,ArrayList<Socket> nodes,int id){
		this.server = server;
        this.nodes = nodes;
        this.id = id;
	}

    public void run() {
    	//Socket server = serverSocket.accept();
        
        /* Read data from the ClientSocket */
      
           // while(true){
                try{
                    //OutputStream outToServer = client.getOutputStream();
         			//DataOutputStream out = new DataOutputStream(outToServer);
         			
         			for(int i = 0; i < nodes.size(); i++){
         				if(i!=id){
         					OutputStream outToClient = nodes.get(i).getOutputStream();
         					DataOutputStream out = new DataOutputStream(outToClient);
         				}
         			}
                    //if(in.readUTF() == "quit()") break;
                }catch(IOException e){
                }
            //}
    }

}