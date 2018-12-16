package com.mygdx.game;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player extends Characters {
	private static int[] hitBox ={320,640,162,832};
		
	public Player(){		
		super(5f,hitBox, "MaleFresher.png");
		
	}
	// to be used for different characters
	public Player(float speed, int hitBoxX, int hitBoxY, String image) {
		super(speed, hitBox, image);
	}
	
	
	/**
	 * Checks movement keys for inputs an updates the sprite sprite
	 * Makes sure sprite doesn't go outside of the current screen
	 * @param screen Current screens sprite
	 * @param controls The current controls map
	 * @return sprite updated with new position
	 */
	// change speed to constant etc.
	public void getMovement(Sprite screen, HashMap<String, Integer> controls) {
	    
		if(Gdx.input.isKeyPressed(controls.get("LEFT"))){
			
            sprite.translateX(-speed);
            if(sprite.getX() + hitBoxDim[0]*scale < screen.getX()) {
            	sprite.setPosition(screen.getX()-hitBoxDim[0]*scale , sprite.getY());
            }
        }
        if(Gdx.input.isKeyPressed(controls.get("RIGHT"))){
        	sprite.translateX(speed);
            if((sprite.getX() + hitBoxDim[1]*scale) > (screen.getX()+screen.getWidth())) {
            	sprite.setPosition(screen.getX()+screen.getWidth() - hitBoxDim[1]*scale, sprite.getY());
            }
        }
        if(Gdx.input.isKeyPressed(controls.get("UP"))){
        	sprite.translateY(speed);
            if(sprite.getY() + hitBoxDim[3]*scale > (screen.getY() + screen.getHeight())) {
                sprite.setPosition(sprite.getX(), (screen.getY() + screen.getHeight()- hitBoxDim[3]*scale));	
            }
        }
        if(Gdx.input.isKeyPressed(controls.get("DOWN"))){
             sprite.translateY(-speed);
             if(sprite.getY()+ hitBoxDim[2]*scale < screen.getY()) {
               	sprite.setPosition(sprite.getX(), screen.getY() - hitBoxDim[2] * scale);
             }
               
        }
	}
	// checks if it is touching a zombie, currently centre of zombie has to be in its hitbox can change
	public Boolean isTouchingZom(Zombies zombie) {
		
		 if((zombie.getCoord().x < getCoord().x + getHitBoxWidth()/2) & (getCoord().x - getHitBoxWidth()/2 < zombie.getCoord().x) 
			& (zombie.getCoord().y < getCoord().y +getHitBoxHeight()/2) & (getCoord().y - getHitBoxHeight()/2< zombie.getCoord().y)){
		 		return true;
		 }
		 return false;

	}
}
