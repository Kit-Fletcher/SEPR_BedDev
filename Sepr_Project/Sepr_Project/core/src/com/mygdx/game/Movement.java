package com.mygdx.game;

import java.awt.Point;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Movement {
	private static HashMap<String, Integer> controls;
	private int rand =0;
	private int randDir = (int)Math.round((Math.random() *8 + 1));
	public Movement() {
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
	 * @param character The players Player class
	 * @return character updated with new position
	 */
	public Point getPlayerMovement(Player character) {
	    
		if(Gdx.input.isKeyPressed(controls.get("LEFT"))) {
            character.translateX(-character.getSpeed());
        }
        if(Gdx.input.isKeyPressed(controls.get("RIGHT"))){
        	character.translateX(character.getSpeed());
        }
        if(Gdx.input.isKeyPressed(controls.get("UP"))){
        	character.translateY(character.getSpeed());    
        }
        if(Gdx.input.isKeyPressed(controls.get("DOWN"))){
            character.translateY(-character.getSpeed());       
        }
        character.checkBounds();
        Point xy = new Point(Math.round(character.getX()), Math.round(character.getY()));
        return xy;
	}

	public Point getZombieMovement(Zombies zombie, Player character) {
		
		int difX = (int)Math.round(character.getCoord().getX() - zombie.getCoord().getX());
		int difY = (int)Math.round(character.getCoord().getY() - zombie.getCoord().getY());
		if(((Math.abs(difX)<zombie.getAttackRadius())) && (Math.abs(difY)<zombie.getAttackRadius()) ) {
			if(Math.abs(difX)<=zombie.getSpeed() && Math.abs(difY)<=zombie.getSpeed()) {
				
			}else if(Math.abs(difX)<=zombie.getSpeed()) {
				zombie.translateY(zombie.getSpeed()* (difY/Math.abs(difY)));
			}else if( Math.abs(difY)<=zombie.getSpeed()) {
				zombie.translateX(zombie.getSpeed()* (difX/Math.abs(difX)));
			}else {
				zombie.translateX((zombie.getSpeed()/2)* (difX/Math.abs(difX)));
				zombie.translateY((zombie.getSpeed()/2)* (difY/Math.abs(difY)));
			}
			
		}else {
			if(rand ==0) {
				rand = (int)(Math.round((Math.random()*60 + 10)));
				randDir = (int)(Math.round((Math.random() *8 + 1)));
			}else {
				rand -= 1;
				if(randDir == 1) {
					zombie.translateX(-zombie.getSpeed());
				}else if(randDir == 2){
					zombie.translateX(zombie.getSpeed());
				}else if(randDir == 3) {
					zombie.translateY(-zombie.getSpeed());
				}else if (randDir == 4){
					zombie.translateY(zombie.getSpeed());
				}else if (randDir == 5){
					zombie.translateY(zombie.getSpeed()/2);
					zombie.translateX(zombie.getSpeed()/2);
				}else if (randDir == 6){
					zombie.translateY(zombie.getSpeed()/2);
					zombie.translateX(-zombie.getSpeed()/2);
				}else if (randDir == 7){
					zombie.translateY(-zombie.getSpeed()/2);
					zombie.translateX(zombie.getSpeed()/2);
				}else if (randDir == 8){
					zombie.translateY(-zombie.getSpeed()/2);
					zombie.translateX(-zombie.getSpeed()/2);
				}
			}
		}
		zombie.checkBounds();
		Point xy = new Point(Math.round(zombie.getX()), Math.round(zombie.getY()));
        return xy;
	}
	// note that mouse coordinates are measured from top left of screen and sprites from bottom left
	public Integer getMouseCoordinatesX() {
		return Gdx.input.getX();
	}
	
	public Integer getMouseCoordinatesY() {
		return Gdx.input.getY();
	}
	
	public boolean getMouseClick() {
		 if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
			 return true;
		 }
		 return false;
	}
	
	public HashMap<String, Integer> getcontrols(){
		return controls;
	}

}
