package com.mygdx.game;


public class Zombies extends Characters{
	
	//TODO Need to add animations from the resource manager, position on the map, size etc. Use Vector2 and Map Class 
	public Zombies(String id, int attackRadius, int health, int damage) {
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
