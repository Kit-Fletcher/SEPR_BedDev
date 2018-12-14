package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player extends Sprite {

	private final Weapon weapon;

	private final String type;

	private int health;

	private boolean isPlayerAlive;

	private boolean isArmed;

	private int damage = 10;

	private final int PLAYER_WIDTH = 100;
	private final int PLAYER_HEIGHT = 100;

	public Player(final Sprite sprite, final String type, final Weapon weapon) {
		super(sprite);
		this.weapon = weapon;
		this.type = type;// Fresher or Gresher.

		if (type == "Gresher") {
			this.damage = 20;
		}

		initialize();
	}

	/*
	 * set default value of player attributes
	 */
	private void initialize() {

		this.health = 100;
		this.isPlayerAlive = true;
		this.isArmed = true;
		this.setSize(PLAYER_WIDTH, PLAYER_HEIGHT);
		this.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
	}

	@Override
	public void draw(final Batch batch) {
		update(Gdx.graphics.getDeltaTime());
		super.draw(batch);
	}

	/*
	 * update player status and response to events react on collision add velocity/
	 * gravity type value update animation i,e damage animation, weapon animation
	 * trigger
	 * 
	 */

	public void update(final float delta) {

		if (getHealth() == 0)
			isPlayerAlive = false;

		if (isArmed) {

			// code to show armor
			// should be added as a animation
		}

	}

	public int getHealth() {
		return health;
	}

	public void setHealth(final int health) {
		this.health = health;
	}

	public boolean isPlayerAlive() {
		return isPlayerAlive;
	}

	public void setPlayerAlive(final boolean playerAlive) {
		isPlayerAlive = playerAlive;
	}

	public boolean isArmed() {
		return isArmed;
	}

	public void setArmed(final boolean armed) {
		isArmed = armed;
	}

	public void injured(int x) {

		final int currentHealth = getHealth();
		if (type == "Gresher") {

			x = x * 2;

		}

		if (currentHealth - x >= 0) {

			setHealth(currentHealth - x);
		}
	}

	public void injured() {

		final int currentHealth = getHealth();

		if (currentHealth - damage >= 0) {

			setHealth(currentHealth - damage);
		}

	}

}

