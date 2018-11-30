package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class GameScreen {
	
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
	
	public Integer getMouseCoordinatesX() {
		return Gdx.input.getX();
	}
	
	public Integer getMouseCoordinatesY() {
		return Gdx.input.getY();
	}
	
	
	

}
