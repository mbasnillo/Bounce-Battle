package com.miles.epik.window;

import java.util.HashMap;

import javax.swing.JTable;

public class Access {
	UDPClient udp;
	Window window;
	HashMap<String, Boolean>  players= new HashMap<String, Boolean>();
	HashMap<String, Integer> score = new HashMap<String, Integer>();
	JTable scoreBoard;
	
	public Access(){
	}
	
	public void setWindow(Window window){
		System.out.println("WINDOW SET!");
		this.window = window;
	}
	
	
	public Window getWindow(){
		return this.window;
	}
	
	public HashMap<String, Integer> getScore(){
		return this.score;
	}
	
	public void addPlayer(String name){
		if(!players.containsKey(name)){			
			players.put(name,true);
		}
		if(!score.containsKey(name)){
			score.put(name,0);
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
	
	public void kill(String name){
		players.put(name, false);
		//JOptionPane.showMessageDialog(null, name+ " has been killed");
	}
	
}
