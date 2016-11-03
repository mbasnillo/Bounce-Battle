import java.net.*;
import java.io.*;
import java.lang.*;

public class Reciever implements Runnable {
	private Socket server;
    private Socket[] nodes;
    private int id;

	public Reciever(Socket server,Socket[] nodes,int id){
		this.server = server;
        this.nodes = nodes;
        this.id = id;
	}

    public void run() {
    	//Socket server = serverSocket.accept();
        
        /* Read data from the ClientSocket */
      
            while(true){
                try{
                    DataInputStream in = new DataInputStream(server.getInputStream());
                    System.out.println(in.readUTF());
                    //if(in.readUTF() == "quit()") break;
                }catch(IOException e){
                }
            }

        
    }

}