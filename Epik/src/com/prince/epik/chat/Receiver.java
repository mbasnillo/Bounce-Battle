package com.prince.epik.chat;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JTextArea;

public class Receiver implements Runnable {
	private Socket server;
    private JTextArea chatBox;

    public Receiver(Socket server, JTextArea chatBox){
        this.server = server;
        this.chatBox = chatBox;
    }

    @Override
    public void run() { 
        /* Read data from the ClientSocket */
    	System.out.println("Thread started!");
            while(true){
                try{
                    DataInputStream in = new DataInputStream(server.getInputStream());
                    chatBox.append(in.readUTF());
                }catch(IOException e){
                	//System.out.println("ERROR BESH!");
                	try{
                		this.server.close();
                	}catch(IOException a){
                		
                	}
                }
            }
    }
    
}
