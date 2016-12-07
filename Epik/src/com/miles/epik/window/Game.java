package com.miles.epik.window;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.net.DatagramSocket;

import javax.swing.JOptionPane;

import com.miles.epik.framework.KeyInput;
import com.miles.epik.framework.ObjectId;
import com.miles.epik.objects.Player;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = 3867919631110141462L;

	public static final int PORT=4444;
	DatagramSocket socket;
	private boolean running = false;
	private Thread thread;
	String server;
	String name;
	String serverData;
	boolean connected = false;
	UDPClient udp;
	private Player user;
	Access access;
	private Color[] colors = {Color.BLUE, Color.RED, Color.GREEN};
	
	public static int WIDTH, HEIGHT;
	
	Handler handler;
	//Camera cam;
	
	
	
	private void init(){
		WIDTH = getWidth();
		HEIGHT = getHeight();
		
		handler = new Handler();
		
		//cam = new Camera(0,-70);
//		user = new Player(100, 100, handler, ObjectId.Player,name);
//		handler.addObject(user);
//		handler.addPlayer(name, user);
		int i = 1;
		for(String newName : access.getPlayers().keySet()){
			Player newPlayer;
			if(newName.equals(name)){
				newPlayer = new Player((i)*100,(i)*100, handler, ObjectId.Player, newName);
				newPlayer.setUDP(udp);
			}
			else{
				newPlayer = new Player((i)*100,(i)*100, handler, ObjectId.Player, newName);
			}
			newPlayer.setColor(colors[(i-1)%colors.length]);
			this.handler.addObject(newPlayer);
			this.handler.addPlayer(newName,newPlayer);
			i++;
		}
		//handler.addObject(new Player(200, 200, handler, ObjectId.Player, "Rip"));
		user = (Player) handler.getPlayers().get(name);
		try{
			handler.createLevel();
		}catch(IOException e){}
		this.addKeyListener(new KeyInput(handler));
	}
	
	public synchronized void start(){
		if(running) return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public void run(){

		init();
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while(running){			
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;
					
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				//System.out.println("FPS: " + frames + " TICKS: " + updates);
				frames = 0;
				updates = 0;
			}
			udp.send("POSITION-"+ user.getName()+" "+user.getX() + ","+user.getY());
//			if(Math.abs(user.getX()) > 100000 || Math.abs(user.getY()) > 100000 ){
//				running = false;
//			}
		}
	}
	
	private void tick(){
		handler.tick();
		int count = 0;
		for(String checkName : access.getPlayers().keySet()){
			if(access.getPlayers().get(checkName)){
				count++;
			}
		}
		
		if(count == 1){
			for(String checkName : access.getPlayers().keySet()){
				if(access.getPlayers().get(checkName)){
					JOptionPane.showMessageDialog(null,checkName + " HAS WON!");
					System.out.println(checkName + " HAS WON!");
					//exit();
				} 
			}
			running = false;
			
		}
	}
	
	private void render(){
		Toolkit.getDefaultToolkit().sync();
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		
		
		// Draw here
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		//g2d.translate(cam.getX(), cam.getY());
		
		handler.render(g);
		
		//g2d.translate(cam.getX(), -cam.getY());
		
		g.dispose();
		bs.show();
	}
	
	public void setServer(String server){
		this.server = server;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setUDP(UDPClient udp){
		this.udp = udp;
	}
	
	public void setAccess(Access access){
		this.access = access;
	}
	public void updatePlayer(String data){
		String nameToBeUpdated;
		String[] d = data.split(" ");
		nameToBeUpdated = d[0].trim();
		if(handler.getPlayers().containsKey(nameToBeUpdated) && !nameToBeUpdated.equals(name)){
			float newX = Float.parseFloat(d[1].split(",")[0]);
			float newY = Float.parseFloat(d[1].split(",")[1]);
			this.handler.getPlayers().get(nameToBeUpdated).setX(newX);
			this.handler.getPlayers().get(nameToBeUpdated).setY(newY);
		}
		else if(!nameToBeUpdated.equals(name)){
			System.out.println("ERRRRRROOOORRR!");
		}
	}
	public void updatePlayerVel(String data){
		String nameToBeUpdated;
		String[] d = data.split(" ");
		nameToBeUpdated = d[0].trim();
		if(handler.getPlayers().containsKey(nameToBeUpdated)){
			float newVelX = Float.parseFloat(d[1].split(",")[0]);
			this.handler.getPlayers().get(nameToBeUpdated).setVelX(newVelX);
		}
		else if(!nameToBeUpdated.equals(name)){
			System.out.println("ERRRRRROOOORRR!");
		}
	}
}
