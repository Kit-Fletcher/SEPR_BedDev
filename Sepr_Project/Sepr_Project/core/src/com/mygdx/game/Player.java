package com.mygdx.game;

import java.awt.Point;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player extends Characters {

	private final Weapon weapon;

	private boolean isArmed;
	private boolean orientationUp; // If mouse is above = True

	public Player(final Sprite img, String type) {
		super(img, type);
		this.weapon = null;
		initialize();

	}

	public Player(final Sprite sprite, String type, final Weapon weapon) {
		super(sprite, type);

		this.weapon = weapon;

		initialize();
	}

	/**
	 * set default value of player attributes
	 */
	@Override
	protected void initialize() {
		super.initialize();
		this.hitBoxDim = new int[] { 31, 62, 16, 81 };
		this.speed = 2;
		this.health = 100;
		this.damage = 10;
		this.dmgMod = 1f;
		this.spdMod = 1f;
		if (type == "Gresher") {
			this.dmgMod = 2f;
			this.spdMod = 1.5f;
			this.injMod = 1f;
		}

	}

	/**
	 * update player status and response to events react on collision add velocity/
	 * gravity type value update animation i,e damage animation, weapon animation
	 * trigger
	 * 
	 */
	@Override
	public void update(final float delta) {
		super.update(Gdx.graphics.getDeltaTime());

		if (isArmed) {

			// code to show armor
			// should be added as animation
		}

	}

	@Override
	public void injured(int x) {

		if (type == "Gresher") {

			x = (int) (x * this.injMod);

		}

		super.injured(x);
	}

	public boolean isArmed() {
		return isArmed;
	}

	public void setArmed(final boolean armed) {
		isArmed = armed;
	}

	@Override
	public int getDamage() {
		if (this.weapon == null) {
			return (int) (super.getDamage() * this.dmgMod);
		} else {
			return (int) (this.weapon.getDamage() * this.dmgMod);
		}
	}


	/**
	 * Moves the sprite around the screen based on user inputs
	 */
	public void getMovement() {

		Point xy = new Point(mov.getPlayerMovement(this));
		this.setPosition(Math.round(xy.getX()), Math.round(xy.getY()));
		if (Gdx.graphics.getHeight() - mov.getMouseCoordinatesY() > this.getCoord().getY()) {
			orientationUp = true;
		} else {
			orientationUp = false;
		}
		// TODO change sprite to looking away if orientationUp is true - cosmetic
	}

	public boolean getOrientationUp() {
		return orientationUp;
	}

	/**
	 * poll as much as movement to check if attacking possible cooldown after
	 * attacks though Also this enters a for loop so maybe have it threaded
	 * depending how long loop takes.
	 * 
	 * @param zombies
	 */
	//
	public List<Zombies> attack(List<Zombies> zombies) {
		boolean mouse = mov.getMouseClick();
		if (mouse) {
			for (Zombies zombie : zombies) {
				if (closeZombie(zombie)) {
					zombie.injured(this.getDamage());
					;
				}
			}
		}
		// TODO Make zombie bounce back and go in and out of invisibility if injured
		return zombies;
	}

	/**
	 * Checks whether a zombie is in range
	 * 
	 * @param zombie
	 * @return true if its within range
	 */
	private boolean closeZombie(Zombies zombie) {
		Point zomXY = new Point(zombie.getCoord());
		Point chrXY = new Point(this.getCoord());
		int rng = this.getRange();
		int difX = (int) (chrXY.getX() - zomXY.getX());
		int difY = (int) (chrXY.getY() - zomXY.getY());
		int offsetX = (-this.getHitBoxWidth() - zombie.getHitBoxWidth()) / 2;
		int offsetY = (-this.getHitBoxHeight() - zombie.getHitBoxHeight()) / 2;
		if (this.getOrientationUp() && difY <= 0) {
			if ((Math.abs(difX) + offsetX) < rng && Math.abs(difY) + offsetY < rng) {
				return true;
			}

		} else if (this.getOrientationUp() == false && difY >= 0) {
			if (Math.abs(difX) + offsetX < rng && difY + offsetY < rng) {
				return true;
			}
		}
		return false;

	}

	/**
	 * Checks if the character is touching a building
	 * 
	 * @param building
	 *            sprite of the building to be touched
	 * @return true if touching else false
	 */
	public boolean touchBuilding(Sprite building) {
		Point builXY = new Point((int) (building.getX() + building.getWidth() / 2),
				(int) (building.getY() + building.getHeight() / 2));
		Point chrXY = new Point(this.getCoord());
		int difX = (int) (chrXY.getX() - builXY.getX());
		int difY = (int) (chrXY.getY() - builXY.getY());
		int offsetX = (int) (-this.getHitBoxWidth() - building.getWidth()) / 2;
		int offsetY = (int) (-this.getHitBoxHeight() - building.getHeight()) / 2;

		if ((Math.abs(difX) + offsetX) < 0 && Math.abs(difY) +offsetY < 0) {
			return true;

		}
		return false;

	}

	@Override
	public int getRange() {
		if (this.weapon == null) {
			return range;
		} else {
			return this.weapon.getRange();
		}
	}

	@Override
	public int getSpeed() {
		return (int) (this.speed * spdMod);
	}

}
