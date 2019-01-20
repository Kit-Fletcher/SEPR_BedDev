package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

/*
 * Powerups class. 3 different types are currently implemented. 
 */
public class PowerUps extends Item {

	public PowerUps(Sprite sprite, String type, int x, int y) {
		super(sprite, type, x ,y );
		
	}
	
	public PowerUps(Sprite sprite, String type) {
		super(sprite, type );
		
	}	

	/*
	 * Applies the effect based on the PowerUp type.
	 * @throws IllegalArgumentException when the powerUp type doesn't match know ones.
	 * @param Player
	 */

	public void applyEffect(Player currentPlayer) {

		if (this.getType() ==PowerUpType.REDVK.getEffect() ) {
			currentPlayer.resetHealth();
		} else if (this.getType() == PowerUpType.BLUEVK.getEffect()) {
			currentPlayer.setDamage(currentPlayer.getDamage() - 2);
		} else if (this.getType() == PowerUpType.YLWVK.getEffect()) {
			currentPlayer.setSpeed(currentPlayer.getSpeed() * 2);
		} else {

			throw new IllegalArgumentException("Powerup type must match one of the enums");
		}
	}

}
