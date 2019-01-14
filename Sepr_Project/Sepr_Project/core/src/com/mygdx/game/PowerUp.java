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
	 * /

	public void applyEffect(Player currentPlayer) {

		if (this.getType() == "HEALTH_REGEN") {
			currentPlayer.resetHealth();
		} else if (this.getType() == "DECREASE_DAMAGE") {
			currentPlayer.setDamage(currentPlayer.getDamage() - 2);
		} else if (this.getType() == "INCREASE_SPEED") {
			currentPlayer.setSpeed(10);
		} else {

			System.out.println("Power up type does not match any known type");
		}
	}

}
