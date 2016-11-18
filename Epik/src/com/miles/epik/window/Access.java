package com.miles.epik.window;

import javax.swing.JTable;

public class Access {
	UDPClient udp;
	Window window;
	
	public Access(){
	}
	
	public void setWindow(Window window){
		System.out.println("WINDOW SET!");
		this.window = window;
	}
	
	public Window getWindow(){
		return this.window;
	}
	
}
