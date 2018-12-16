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
	
	public HashMap<String, Integer> getControls(){
		return controls;
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
	
	// note that mouse coordinates are measured from top left of screen and sprites from bottom left
	public Integer getMouseCoordinatesX() {
		return Gdx.input.getX();
	}
	
	public Integer getMouseCoordinatesY() {
		return Gdx.input.getY();
	}
	
	
	

}
