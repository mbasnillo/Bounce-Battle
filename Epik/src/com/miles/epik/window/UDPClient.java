package com.miles.epik.window;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient implements Runnable{
	DatagramSocket socket;
	String server, name,serverData;
	final static int PORT = 4444;
	boolean running;
	boolean connected=false;
	Thread t;
	Access access;
	boolean ready;
	
	public void send(String msg){
		try{
			byte[] buf = msg.getBytes();
        	InetAddress address = InetAddress.getByName(server);
        	DatagramPacket packet = new DatagramPacket(buf, buf.length, address, PORT);
        	socket.send(packet);
        }catch(Exception e){}
	}
	public UDPClient(Access access, String server, String name){
		this.access = access;
		this.server = server;
		this.name = name;
		try{
			socket = new DatagramSocket();
		}catch(IOException e){}
	}
	public void start(){
		while(t==null){
			t = new Thread(this);
			running = true;
			t.start();
		}
	}
	
	public void run(){
		while(running){
			if (!connected){
				System.out.println("Connecting..");				
				send("CONNECT "+name);
			}
			
			//**
//			try{
//				Thread.sleep(1);
//			}catch(Exception ioe){}
						
			//Get the data from players
			byte[] buf = new byte[256];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			try{
     			socket.receive(packet);
			}catch(Exception ioe){/*lazy exception handling :)*/}
			
			serverData=new String(buf);
			serverData=serverData.trim();			

			if (!serverData.equals("")){
				System.out.println("Server Data:" +serverData);
			}

			//Study the following kids. 
			if (!connected && serverData.startsWith("CONNECTED")){
				connected=true;
				System.out.println("Connected.");
			}else if (serverData.startsWith("ROOM")){
				this.access.getWindow().updateTable(serverData.split("-")[1]);
			}

		}
	}
}
