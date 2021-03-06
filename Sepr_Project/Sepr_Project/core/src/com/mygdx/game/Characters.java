package com.mygdx.game;

import java.awt.Point;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Characters extends Sprite {
	
	public int animationCount;
	public int[] hitBoxDim = {};
	protected int speed = 2;
	protected boolean isAlive;
	protected int health;
	protected int damage = 10;
	protected final int WIDTH = 100;
	protected final int HEIGHT = 100;
	protected final String type;
	protected final int range = 5;
	
	// Modifiers for different characters
	protected float spdMod = 1;
	protected float dmgMod = 1;
	protected float injMod = 1;
	protected Movement mov;
	protected long timeLastAttack = System.currentTimeMillis();
	private final long delayTime = 1000;// 1 second
	private Texture img;

	public Characters(final Sprite sprite, String type) {
		super(sprite);
		this.img = img;
		this.type = type;// Fresher or Sesher
		mov = new Movement();
		initialize();
	}

	protected void initialize() {
		this.animationCount = 8;
		this.health = 20;
		this.isAlive = true;
		this.setSize(WIDTH, HEIGHT);
		}

	@Override
	public void draw(final Batch batch) {
		update();
		super.draw(batch);
	}

	public void update() {
		if (getHealth() == 0) {
			isAlive = false;
		}
	}

	public int getHealth() {
		return health;
	}

	public void setDamage(int dam) {
		this.damage = dam;
	}

	public int getDamage() {
		return damage;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setHealth(final int health) {
		this.health = health;
	}

	public int[] getHitBoxDim() {
		return hitBoxDim;
	}

	public void injured(int x) {
		if(this.isAlive()) {
			if (this.timeLastAttack == 0) {
				this.timeLastAttack = System.currentTimeMillis() - delayTime;
			}
			
			if (System.currentTimeMillis() - this.timeLastAttack > delayTime) {
				this.timeLastAttack = System.currentTimeMillis();
				
				final int currentHealth = getHealth();
				
				if (currentHealth - x > 0) {
					
					this.setHealth((int) (currentHealth - x * injMod));
				} else {
					this.setHealth(0);
					this.dies();		
				}
			}
		}
	}

	public void setAlive(final boolean alive) {
		isAlive = alive;
	}

	public void dies() {
		this.rotate90(false);
	}

	public Point getCoord() {
		int x = Math.round(this.getX() + (this.getWidth() / 2));
		int y = Math.round(this.getY() + (this.getHeight() / 2));
		Point centre = new Point(x, y);
		return centre;
	}

	public int getHitBoxWidth() {
		int width = Math.round((hitBoxDim[1] - hitBoxDim[0]));
		return width;
	}

	public int getHitBoxHeight() {
		int height = Math.round((hitBoxDim[3] - hitBoxDim[2]));
		return height;
	}

	// Makes sure this is within the current screen
	public void checkBounds() {
		if (this.getX() + hitBoxDim[0] < 0) {
			this.setPosition(0 - hitBoxDim[0], this.getY());
		}
		if ((this.getX() + hitBoxDim[1]) > Gdx.graphics.getWidth()) {
			this.setPosition(Gdx.graphics.getWidth() - hitBoxDim[1], this.getY());
		}
		if (this.getY() + hitBoxDim[3] > (Gdx.graphics.getHeight())) {
			this.setPosition(this.getX(), (Gdx.graphics.getHeight() - hitBoxDim[3]));
		}
		if (this.getY() + hitBoxDim[2] < 0) {
			this.setPosition(this.getX(), -hitBoxDim[2]);
		}
	}

	public int getSpeed() {
		return this.speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getRange() {
		return this.range;
	}

	public Texture getImage() {
		return img;
	}

	public void setImage(Texture texture) {
		this.img = texture;
	}

}
