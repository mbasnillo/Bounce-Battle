package com.miles.epik.yanni;
import java.net.DatagramSocket;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.miles.epik.window.Game;

public class GameServer extends Thread {
	
	private DatagramSocket socket;
	private Game game;
	
	public GameServer(Game game){
		this.game = game;
		try {
			this.socket = new DatagramSocket(2000);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
	}
	
	public void run(){
		while(true){
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			String message =  new String(packet.getData());
			System.out.println("CLIENT > " + new String(packet.getData()));
			if(message.trim().equalsIgnoreCase("ping")){
				sendData("pong".getBytes(), packet.getAddress(), packet.getPort());
			}
		}
	}
	
	public void sendData(byte[] data, InetAddress ipAdd, int port){
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAdd, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
