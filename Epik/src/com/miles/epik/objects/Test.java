package com.miles.epik.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.miles.epik.framework.GameObject;
import com.miles.epik.framework.ObjectId;
import com.miles.epik.window.Handler;

public class Test extends GameObject{
	
	private Handler handler;
	private float width=48, height=48;

	public Test(float x, float y, ObjectId id) {
		super(x, y, id);
	}

	public void tick(LinkedList<GameObject> object) {
		if(velX < 0){
			while(velX != 0){
				velX++;
			}
		}else if(velX > 0){
			while(velX != 0){
				velX--;
			}
		}
		
		if(velY < 0){
			while(velY != 0){
				velY++;
			}
		}else if(velY > 0){
			while(velY != 0){
				velY--;
			}
		}
	}
	
	private void collision(LinkedList<GameObject> object){
		for(int i=0; i<handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ObjectId.Block){
				if(getBoundsTop().intersects(tempObject.getBounds())){
					y = tempObject.getY() + (height/2);
					velY = 0;
				}
				
				if(getBoundsBot().intersects(tempObject.getBounds())){
					y = tempObject.getY() - height;
					velY = 0;
				}
				if(getBoundsRight().intersects(tempObject.getBounds())){
					x = tempObject.getX() - width;
				}
				
				if(getBoundsLeft().intersects(tempObject.getBounds())){
					x = tempObject.getX() + width;
				}
			}
		}
	}
	
	public Rectangle getBoundsBot() {
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

	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval((int)x, (int)y, 48, 48);
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 48, 48);
	}

	public Rectangle getFullBounds(){
		return new Rectangle((int)x, (int)y, 48, 48);
	}

	
}
