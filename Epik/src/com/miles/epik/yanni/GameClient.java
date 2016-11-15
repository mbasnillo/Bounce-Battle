package com.miles.epik.yanni;
import java.net.DatagramSocket;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.miles.epik.window.Game;

public class GameClient extends Thread {
	
	private InetAddress ipAdd;
	private DatagramSocket socket;
	private Game game;
	
	public GameClient(Game game, String ipAdd){
		this.game = game;
		try {
			this.socket = new DatagramSocket();
			this.ipAdd = InetAddress.getByName(ipAdd);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
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
			System.out.println("SERVER > " + new String(packet.getData()));
		}
	}
	
	public void sendData(byte[] data){
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAdd, 2000);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
