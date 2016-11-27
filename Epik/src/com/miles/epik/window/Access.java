package com.miles.epik.window;

import java.awt.Point;
import java.util.HashMap;

public class Access {
	UDPClient udp;
	Window window;
	HashMap<String, Boolean>  players= new HashMap<String, Boolean>();
	
	public Access(){
	}
	
	public void setWindow(Window window){
		System.out.println("WINDOW SET!");
		this.window = window;
	}
	
	public Window getWindow(){
		return this.window;
	}
	
	public void addPlayer(String name){
		if(!players.containsKey(name)){			
			players.put(name,true);
		}
	}
	
	public void updatePlayer(String playerString){
		players.clear();
		String[] name = playerString.split(":");
		for(String n : name){
			addPlayer(n);
		}
	}
	
	public String  toStringPlayer(){
		return this.players.toString();
	}
	
	public HashMap<String, Boolean> getPlayers(){
		return this.players;
	}
	
}
