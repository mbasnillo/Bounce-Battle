/*
This is a thread for the clients to continously send messages to the server
*/

import java.net.*;
import java.io.*;
import java.lang.*;
import java.util.*;

public class Sender implements Runnable {
	private Socket server;
    private DataOutputStream out;
    private String name;
    private String message;

	public Sender(Socket server,DataOutputStream out,String name){
		this.server = server;
        this.out = out;
        this.name = name;
	}

    public void run() {
    	//Socket server = serverSocket.accept();
        
        /* Read data from the ClientSocket */
       Scanner sc = new Scanner(System.in);
         while(message!="quit()"){ //infinite loop of listening
            try{
             //System.out.print(name+" : ");
             message = sc.nextLine();
             if(message.equals("quit()")){
                System.out.println(name+" disconnected");
                out.writeUTF(name+" disconnected");
                break;
             }
             out.writeUTF(name +" : " +message);
            }catch(IOException e){
            }
         }
    }

}