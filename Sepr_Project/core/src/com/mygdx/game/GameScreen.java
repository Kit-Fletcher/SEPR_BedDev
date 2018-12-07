package com.mygdx.game;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class GameScreen {
	public static HashMap<String, Integer> controls;
	
	public GameScreen() {
		controls = new HashMap<String, Integer>();
		controls.put("UP", Input.Keys.W);
		controls.put("DOWN", Input.Keys.S);
		controls.put("LEFT", Input.Keys.A);	
		controls.put("RIGHT", Input.Keys.D);
	
	}
	/**
	 * Checks if the key is pressed
	 * @param key the value of the key you want to check;use Input.keys.'keyname' to get desired keycode
	 * @return Boolean value
	 */
	public Boolean isKeyPressed(int key) {
		if(Gdx.input.isKeyPressed(key)){
			return true;
			
		}else {
			return false;
		}
	}
	/**
	 * Checks movement keys for inputs and updates the character sprite
	 * Makes sure sprite doesn't go outside of the current screen
	 * @param character The character sprite
	 * @param screen Current screens sprite
	 * @return character updated with new position
	 */
	public Sprite getMovement(Sprite character, Sprite screen) {
	    
		if(Gdx.input.isKeyPressed(controls.get("LEFT"))){
			
            character.translateX(-5f);
            if(character.getX() < screen.getX()) {
            	character.setPosition(screen.getX(), character.getY());
            }
        }
        if(Gdx.input.isKeyPressed(controls.get("RIGHT"))){
        	character.translateX(5f);
            if((character.getX() + (character.getWidth() * character.getScaleX())) > (screen.getX()+screen.getWidth())) {
            	character.setPosition(screen.getX()+screen.getWidth()- (character.getWidth() * character.getScaleX()), character.getY());
            }
        }
        if(Gdx.input.isKeyPressed(controls.get("UP"))){
        	character.translateY(5f);
            if(character.getY() + (character.getHeight() * character.getScaleY()) > (screen.getY() + screen.getHeight())) {
                character.setPosition(character.getX(), (screen.getY() + screen.getHeight()- (character.getHeight() * character.getScaleY())));	
            }
        }
        if(Gdx.input.isKeyPressed(controls.get("DOWN"))){
             character.translateY(-5f);
             if(character.getY() < screen.getY()) {
               	character.setPosition(character.getX(), screen.getY());
             }
               
        }
		return character;
		
		
	}
	// note that mouse coordinates are measured from top left of screen and sprites from bottom left
	public Integer getMouseCoordinatesX() {
		return Gdx.input.getX();
	}
	
	public Integer getMouseCoordinatesY() {
		return Gdx.input.getY();
	}
	
	
	

}
