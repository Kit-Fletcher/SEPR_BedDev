package com.mygdx.game;

import java.awt.Point;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Characters {
	protected SpriteBatch batch;
	public Sprite sprite;
	public int[] hitBoxDim = {} ;
	protected Texture img;
	protected Float speed;
	protected float scale = 1f/10f;
	
	/**
	 * @param startSpeed a float number which represents how many pixels the sprite will move each render cycle
	 * @param hitBox an array defining how big the sprite is compared to its image {xleft,xright,ybottom,ytop}
	 * @param startImg the file name of the image to be used for the character
	 */
	public Characters(float startSpeed, int[] hitBox, String startImg) {
		speed = startSpeed;
		hitBoxDim = new int[4];
		hitBoxDim = hitBox;
		img = new Texture(startImg);
		batch = new SpriteBatch();
		sprite = new Sprite(img);
		sprite.setOrigin(0, 0);
		sprite.setScale(scale);
		
		 
	}
	public Sprite getSprite(){
		return sprite;
	}
	public void render() {
		batch.begin();
		sprite.draw(batch);
		batch.end();
	}
	public Point getCoord() {
		int x = Math.round(sprite.getX() + (sprite.getWidth()/2) * scale);
		int y = Math.round(sprite.getY() + (sprite.getHeight()/2) * scale);
		Point centre = new Point(x,y);
		return centre;
	}
	public int getHitBoxWidth() {
		int width = Math.round((hitBoxDim[1] - hitBoxDim[0])*scale);
		return width;
	}
	public int getHitBoxHeight() {
		int height = Math.round((hitBoxDim[3]- hitBoxDim[2])*scale);
		return height;
	}
	//Makes sure sprite is within the current screen
	public void checkBounds(Sprite screen) {
		if(sprite.getX() + hitBoxDim[0]*scale < screen.getX()) {
        	sprite.setPosition(screen.getX()-hitBoxDim[0]*scale , sprite.getY());
        }
		if((sprite.getX() + hitBoxDim[1]*scale) > (screen.getX()+screen.getWidth())) {
        	sprite.setPosition(screen.getX()+screen.getWidth() - hitBoxDim[1]*scale, sprite.getY());
        }
		if(sprite.getY() + hitBoxDim[3]*scale > (screen.getY() + screen.getHeight())) {
            sprite.setPosition(sprite.getX(), (screen.getY() + screen.getHeight()- hitBoxDim[3]*scale));	
        }
		if(sprite.getY()+ hitBoxDim[2]*scale < screen.getY()) {
           	sprite.setPosition(sprite.getX(), screen.getY() - hitBoxDim[2] * scale);
         }
		
	}
}
