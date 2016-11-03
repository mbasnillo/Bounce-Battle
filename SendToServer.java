import java.net.*;
import java.io.*;
import java.lang.*;
import java.util.*;

public class SendToServer implements Runnable {
	private Socket server;
    private DataOutputStream out;
    private String name;
    private String message;

	public SendToServer(Socket server,DataOutputStream out,String name){
		this.server = server;
        this.out = out;
        this.name = name;
	}

    public void run() {
    	//Socket server = serverSocket.accept();
        
        /* Read data from the ClientSocket */
       Scanner sc = new Scanner(System.in);
         while(message!="quit()"){
            try{
             //System.out.print(name+" : ");
             message = sc.nextLine();
             out.writeUTF(name +" : " +message);
            }catch(IOException e){
                
            }
         }
    }

}