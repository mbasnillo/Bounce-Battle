package com.miles.epik.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.miles.epik.framework.GameObject;
import com.miles.epik.framework.ObjectId;
import com.miles.epik.window.Handler;

public class Player extends GameObject {

	private float width=48, height=48;
	
	private float gravity = 0.5f;
	private final float MAX_SPEED = 15;
	private int jumpcount=2;
	private Handler handler;
	private float jumpPower = 0;
	private String name;
	private Color color;
	
	private boolean is_alive = true;
	
	public Player(float x, float y, Handler handler, ObjectId id, String name) {
		super(x, y, id);
		this.handler = handler;
		this.name = name;
	}
	
	public void setColor(Color color){
		this.color=color;
	}
	
	public void getPosition(){
		System.out.println(x + " , " + y);
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
		if(y >= 2000){
			is_alive = false;
		}
		//getPosition();
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

					if(this.jumpPower > 0)this.jump(-1);
				}else{
					falling = true;
				}
				
				if(getBoundsRight().intersects(tempObject.getBounds())){
					x = tempObject.getX() - width;
				}
				
				if(getBoundsLeft().intersects(tempObject.getBounds())){
					x = tempObject.getX() + width;
				}
			}
			

			//
			if(tempObject.getId() == ObjectId.Player && tempObject != this){
				if(getBoundsLeft().intersects(tempObject.getFullBounds())){
					x = tempObject.getX() + width;
					if(-(velX) > (tempObject.getVelX())){
						tempObject.setVelX(velX*2);
						velX = -velX;
					}else if(-(velX) == tempObject.getVelX()){
						velX = 0;
						tempObject.setVelX(0);
					}else{
						velX = -2 * (tempObject.getVelX());
					}
				}
				if(getBoundsRight().intersects(tempObject.getFullBounds())){
					x = tempObject.getX() - width;
					if((velX) > -(tempObject.getVelX())){
						tempObject.setVelX(velX*2);
						velX = -velX;
					}else if(-(velX) == tempObject.getVelX()){
						velX = 0;
						tempObject.setVelX(0);
					}else{
						velX = 2 * (tempObject.getVelX());
					}
				}
				if(getBoundsTop().intersects(tempObject.getFullBounds())){
					y = tempObject.getY() + height;
				}
				if(getBounds().intersects(tempObject.getFullBounds())){
					y = tempObject.getY() - height;
					if(this.jumpPower > 0)this.jump(-1);
				}
			}
		}
	}

	public void render(Graphics g) {
		g.setColor(this.color);
		g.fillOval((int)x, (int)y, (int)width, (int)height);
		
		/*
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.RED);
		g2d.draw(getBounds());
		g2d.draw(getBoundsRight());
		g2d.draw(getBoundsLeft());
		g2d.draw(getBoundsTop());
		*/
		
	}
	
	public Rectangle getFullBounds(){
		return new Rectangle((int)x, (int)y, 48, 48);
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
	
	public void jump(float i){
		if(jumping == true){
			//do nothing
		}
		else{
			jumping = true;
			velY = -jumpPower;
		}
		System.out.println(jumpPower);
		jumpPower+= i;
		//jumpcount--;
	}
	
	public void incJumpPower(){
		this.jumpPower+=0.1;
		
	}

	public int getJumpcount() {
		return jumpcount;
	}

	public void setJumpcount(int jumpcount) {
		this.jumpcount = jumpcount;
	}
	
	public float getX(){
		return this.x;
	}
	
	public float getY(){
		return this.y;
	}
	
	public String getName(){
		return this.name;
	}
	
	public boolean getAlive(){
		return this.is_alive;
	}
	
}
