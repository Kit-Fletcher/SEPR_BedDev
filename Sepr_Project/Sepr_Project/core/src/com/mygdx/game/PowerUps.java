package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/*
 * Powerups class. 3 different types are currently implemented. 
 */
public class PowerUps extends Item {

	public PowerUps(Sprite sprite, String type) {
		super(sprite, type);
	}
	
	/*
	 * Applies the effect based on the PowerUp type.
	 * @param Player
	 */

	public void applyEffect(Player currentPlayer) {

		if (this.getType() == "increaseHealth") {
			currentPlayer.setHealth(currentPlayer.getHealth() + 20);
		} else if (this.getType() == "decreaseDamage") {
			currentPlayer.setDamage(currentPlayer.getDamage() - 2);
		} else if (this.getType() == "increaseSpeed") {
			currentPlayer.setSpeed(currentPlayer.getSpeed() * 2);
		} else {

			System.out.println("Power up type does not match any known type");
		}
	}

}
