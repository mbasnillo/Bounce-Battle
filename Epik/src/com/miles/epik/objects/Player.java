package com.miles.epik.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.miles.epik.framework.GameObject;
import com.miles.epik.framework.ObjectId;
import com.miles.epik.window.Handler;

public class Player extends GameObject {

	private float width=48, height=96;
	
	private float gravity = 0.5f;
	private final float MAX_SPEED = 15;
	private int jumpcount=2;
	private Handler handler;
	
	public Player(float x, float y, Handler handler, ObjectId id) {
		super(x, y, id);
		this.handler = handler;
	}

	public void tick(LinkedList<GameObject> object) {
		x += velX;
		y += velY;
		
		if(falling || jumping){
			velY += gravity;
			
			if(velY > MAX_SPEED){
				velY = MAX_SPEED;
			}
		}
		if(gliding){
			velY -= gravity/2;
			
			if(velY > MAX_SPEED){
				velY = MAX_SPEED;
			}
		}
		
		collision(object);
	}
	
	private void collision(LinkedList<GameObject> object){
		for(int i=0; i<handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ObjectId.Block){
				if(getBoundsTop().intersects(tempObject.getBounds())){
					y = tempObject.getY() + (height/2);
					velY = 0;
				}
				
				if(getBounds().intersects(tempObject.getBounds())){
					y = tempObject.getY() - height;
					velY = 0;
					falling = false;
					jumping = false;
					gliding = false;
				}else{
					falling = true;
				}
				
				if(getBoundsRight().intersects(tempObject.getBounds())){
					x = tempObject.getX() - width;
				}
				
				if(getBoundsLeft().intersects(tempObject.getBounds())){
					x = tempObject.getX() + 32;
				}
			}
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect((int)x, (int)y, (int)width, (int)height);
		
		/*
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.RED);
		g2d.draw(getBounds());
		g2d.draw(getBoundsRight());
		g2d.draw(getBoundsLeft());
		g2d.draw(getBoundsTop());
		*/
	}

	public Rectangle getBounds() {
		return new Rectangle((int) ((int)x+(width/2)-((width/2)/2)), (int) ((int)y+(height/2)), (int)width/2, (int)height/2);
	}
	public Rectangle getBoundsTop() {
		return new Rectangle((int) ((int)x+(width/2)-((width/2)/2)), (int)y, (int)width/2, (int)height/2);
	}
	public Rectangle getBoundsRight() {
		return new Rectangle((int) ((int)x+width-5), (int)y+5, (int)5, (int)height-10);
	}
	public Rectangle getBoundsLeft() {
		return new Rectangle((int)x, (int)y+5, (int)5, (int)height-10);
	}
	
	public void jump(){
		jumping = true;
		velY = -10;
		//jumpcount--;
	}

	public int getJumpcount() {
		return jumpcount;
	}

	public void setJumpcount(int jumpcount) {
		this.jumpcount = jumpcount;
	}
	
	
}
