package com.mygdx.game;

import java.awt.Point;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

//TODO some of these methods should be moved to character as they will be shared by any character and can be inherited
public class Zombies extends Characters{
	//private static int[] hitBox ={384,705,160,799};
	
	public Zombies(final Texture img) {
		super(img);
		initialize();
	}
	public Zombies(final Sprite sprite, String type, final int id, final int attackRadius,final float multiplier) {
		super(sprite,type);
		this.setId(id);
		this.hardMod = multiplier;
		this.setAttackRadius(Math.round(attackRadius * hardMod));
		initialize();
	}
	
	protected void initialize() {
		super.initialize();
		if (type == "Boss") {
			this.damage = Math.round(20 *this.hardMod);
			this.health = Math.round(100 * this.hardMod);
		}else {
			this.damage = Math.round(10 *this.hardMod);
			this.health = Math.round(15 * this.hardMod);
			this.hitBoxDim = new int[] {37,69,16,78};
					
		}
		this.setAttackRadius(100);
		this.setPosition(200, 200);
	}
	//TODO Need to add animations from the resource manager, position on the map, size etc. Use Vector2 and Map Class 	
	
	private int id;
	private int attackRadius;
	
	//Used for calculating health after receiving damage.
	//Zombies stats increase depending on the difficulty of the game.
	private boolean isHard = false;
	
	private float hardMod = 1;
	

	public boolean isHard() {
			return isHard;
	}

	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
			this.id = id;
	}
	
	public int getAttackRadius() {
			return attackRadius;
	}
	
	public void setAttackRadius(int attackRadius) {
			this.attackRadius = attackRadius;
			
	}
	

	
	@Override
	public void setDamage(int damage) {
			super.setDamage(Math.round(damage*hardMod));
	}
	
	public void getMovement(Player character) {
		Point xy= new Point(mov.getZombieMovement(this,character)); 
		this.setPosition(Math.round(xy.getX()), Math.round(xy.getY()));
	}
	public Player attack(Player chr) {
		boolean mouse = mov.getMouseClick();
		if(closeZombie(chr)) {
			chr.injured(this.getDamage());
			
		}
		//TODO Make Player bounce back and go in and out of invisibility if injured
		return chr;
	}
	
	private boolean closeZombie(Player chr) {
		Point chrXY = new Point(chr.getCoord());
		Point zomXY = new Point(this.getCoord());
		int rng = this.getRange();
		int difX = (int)(chrXY.getX() - zomXY.getX());
		int difY = (int)(chrXY.getY() - zomXY.getY());
		int offsetX = (int)(-this.getHitBoxWidth()- chr.getHitBoxWidth())/2;
		int offsetY = (int)(-this.getHitBoxHeight()- chr.getHitBoxHeight())/2;
		if((Math.abs(difX) + offsetX)< rng && Math.abs(difY) +offsetY<rng) {
			return true;
		}
		return false;	
	}
}

