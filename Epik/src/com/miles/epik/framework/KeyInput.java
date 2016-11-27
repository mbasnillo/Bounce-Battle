package com.miles.epik.framework;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.miles.epik.objects.Player;
import com.miles.epik.window.Handler;

public class KeyInput extends KeyAdapter {
	
	Handler handler;
	
	public KeyInput(Handler handler){
		this.handler = handler;
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		for(int i=0; i<handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ObjectId.Player){
				if(key == KeyEvent.VK_UP && !tempObject.isJumping()){
					Player p;
					p = (Player) tempObject;
					if(p.getJumpcount()>0){
						p.jump(10);
					}
				}
				if((key == KeyEvent.VK_UP && tempObject.isJumping()) || (key == KeyEvent.VK_RIGHT && tempObject.isJumping())){
					Player p;
					p = (Player) tempObject;
						p.incJumpPower();
				}

				if(key == KeyEvent.VK_RIGHT){
					if(tempObject.getVelX() == 0){
						tempObject.setVelX(5);
					}else{
						while(tempObject.getVelX() < 10){
							tempObject.setVelX((tempObject.getVelX())+1);
						}
					}
				}
				if(key == KeyEvent.VK_LEFT){
					if(tempObject.getVelX() == 0){
						tempObject.setVelX(-5);
					}else{
						while(tempObject.getVelY() > -10){
							tempObject.setVelX((tempObject.getVelX())-1);
						}
					}
				}
 			}
		}
		
		if(key == KeyEvent.VK_ESCAPE){
			System.exit(1);
		}
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		for(int i=0; i<handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ObjectId.Player){
				if(key == KeyEvent.VK_RIGHT)tempObject.setVelX(0);
				if(key == KeyEvent.VK_LEFT)tempObject.setVelX(0);
				//if(key == KeyEvent.VK_UP)tempObject.setVelY(0);
 			}
		}
	}
}
