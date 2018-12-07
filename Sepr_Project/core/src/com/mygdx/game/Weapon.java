package com.mygdx.game;

public class Weapon {
	
	public Weapon(int range, int damage) {
		setRange(range);
		setDamage(damage);
	}
	
	private int range;
	private int damage;
	
	public int getRange() {
		return range;
	}
	
	public void setRange(int range) {
		this.range = range;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	
}
