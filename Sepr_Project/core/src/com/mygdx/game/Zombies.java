package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

//TODO some of these methods should be moved to character as they will be shared by any character and can be inherited
public class Zombies extends Characters{
	private static int[] hitBox ={384,705,160,799};
	private Sprite curScreen;
	
	public Zombies(Sprite screen) {
		super(1f,hitBox, "Zombie1.png");
		curScreen = screen;
	}
	//TODO Need to add animations from the resource manager, position on the map, size etc. Use Vector2 and Map Class 
	public Zombies(String id, int attackRadius, int health, int damage) {
		super(5f, hitBox, "Zombie1.png");
		setId(id);
		setAttackRadius(attackRadius);
		setHealth(health);
		setDamage(damage);
	}
	
	
	private String id;
	private int attackRadius;
	private int health;
	//Used for calculating health after receiving damage.
	private int previousHealth;
	private int damage;
	//Zombies stats increase depending on the difficulty of the game.
	public boolean isHard = false;
	public boolean isDead;
	//Discuss whether Boss should be considered as Zombie or as a new class.
	public boolean isBoss = false;
	//This multiplier can be changed if needed for better game balance.
	public float hardModeMultiplier = 1.5f;
	//Used to calculate player damage
	private Player player;
	

	public boolean isHard() {
			return isHard;
	}

	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
			this.id = id;
	}
	
	public int getAttackRadius() {
			return attackRadius;
	}
	
	public void setAttackRadius(int attackRadius) {
			if (isHard) {
				this.attackRadius = Math.round(attackRadius*hardModeMultiplier);
			} else {
				this.attackRadius = attackRadius;
			}
	}
	
	public int getHealth() {
			return health;
	}
	
	public void setHealth(int health) {
	    	if (isHard) {
	    		this.health = Math.round(health*hardModeMultiplier);
	    	} else {
	    		this.health = health;
	    }   
	}
	
	public int getDamage() {
			return damage;
	}
	
	public void setDamage(int damage) {
			if (isHard) {
				this.damage = Math.round(damage*hardModeMultiplier);
			} else {
				this.damage = damage;
			}
	}
	
	public void receiveDamage() {
			previousHealth = this.health;
			//TODO have getDamage() in Player class
			this.health -= player.getDamage();
			isDead();
	}
	
	//Set as dead if health falls to 0 or below 0
	//TODO remove zombie sprite if isDead
	public boolean isDead() {
			if (this.health <= 0) {
				return true;
			} else {
				return false;
			}
	}
	//TODO Add random movements.
	
	
}
