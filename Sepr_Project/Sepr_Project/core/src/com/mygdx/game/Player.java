package com.mygdx.game;

import java.awt.Point;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player extends Characters {
	
	private static final int FRAME_COLS = 4, FRAME_ROWS = 2;
	
	private final Weapon weapon;

	private boolean isArmed;
	private boolean orientationUp; // If mouse is above = True
	private final int INITIALHEALTH = 100;
	
	private Animation<TextureRegion> fresherWalkLeftAnimation;
	private Texture fresherWalkLeftSheet;
	private Animation<TextureRegion> fresherWalkRightAnimation;
	private Texture fresherWalkRightSheet;
	private Animation<TextureRegion> fresherAttackLeftAnimation; 
	private Texture fresherAttackLeftSheet;
	private Animation<TextureRegion> fresherAttackRightAnimation; 
	private Texture fresherAttackRightSheet;
	private Animation<TextureRegion> sesherWalkLeftAnimation;
	private Texture sesherWalkLeftSheet;
	private Animation<TextureRegion> sesherWalkRightAnimation;
	private Texture sesherWalkRightSheet;
	private Animation<TextureRegion> sesherAttackLeftAnimation; 
	private Texture sesherAttackLeftSheet;
	private Animation<TextureRegion> sesherAttackRightAnimation; 
	private Texture sesherAttackRightSheet;
	
	private String animationDirection;
	private Boolean isAttacking;
	private Boolean isWalking;
	private int attackTimer;
	
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
		this.damage = 100;
		this.dmgMod = 1f;
		this.spdMod = 1f;
		if (type == "Sesher") {
			this.dmgMod = 2f;
			this.spdMod = 1.5f;
			this.injMod = 1f;
		}

		fresherWalkLeftAnimation = loadAnimation(fresherWalkLeftSheet, Gdx.files.internal("FresherWalkLeft.png"), 4, 2);
		fresherWalkRightAnimation = loadAnimation(fresherWalkRightSheet, Gdx.files.internal("FresherWalkRight.png"), 4, 2);
		fresherAttackLeftAnimation = loadAnimation(fresherAttackLeftSheet, Gdx.files.internal("FresherAttackLeft.png"), 4, 1);
		fresherAttackRightAnimation = loadAnimation(fresherAttackRightSheet, Gdx.files.internal("FresherAttackRight.png"), 4, 1);
		sesherWalkLeftAnimation = loadAnimation(sesherWalkLeftSheet, Gdx.files.internal("SesherWalkLeft.png"), 5, 2);
		sesherWalkRightAnimation = loadAnimation(sesherWalkRightSheet, Gdx.files.internal("SesherWalkRight.png"), 5, 2);
		sesherAttackLeftAnimation = loadAnimation(sesherAttackLeftSheet, Gdx.files.internal("SesherAttackLeft.png"), 4, 2);
		sesherAttackRightAnimation = loadAnimation(sesherAttackRightSheet, Gdx.files.internal("SesherAttackRight.png"), 4, 2);
		
		animationDirection = "Left";
		attackTimer = 0;
		isWalking = false;
		isAttacking = false;
	}

	private Animation<TextureRegion> loadAnimation(Texture sheet, FileHandle file, int frameCols, int frameRows) {
		
		// Load the sprite sheet as a Texture
		sheet = new Texture(file);

		// Use the split utility method to create a 2D array of TextureRegions. This is 
		// possible because this sprite sheet contains frames of equal size and they are 
		// all aligned.
		TextureRegion[][] tmp = TextureRegion.split(sheet, 
				sheet.getWidth() / frameCols,
				sheet.getHeight() / frameRows);

		// Place the regions into a 1D array in the correct order, starting from the top 
		// left, going across first. The Animation constructor requires a 1D array.
		TextureRegion[] walkFrames = new TextureRegion[frameCols * frameRows];
		int index = 0;
		for (int i = 0; i < frameRows; i++) {
			for (int j = 0; j < frameCols; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}

		// Initialize the Animation with the frame interval and array of frames
		return new Animation<TextureRegion>(0.1f, walkFrames);
		
	}
	
	/**
	 * update player status and response to events react on collision add velocity/
	 * gravity type value update animation i,e damage animation, weapon animation
	 * trigger
	 * 
	 */
	@Override
	public void update() {
		super.update();
		
	}

	@Override
	public void injured(int x) {

		if (type == "Sesher") {

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
	 * Checks if player is attacking and does the attack
	 * 
	 * @param zombies
	 */
	//
	public List<Zombies> attack(List<Zombies> zombies) {
		boolean mouse = mov.getMouseClick();
		if (mouse) {
			//TODO attack animation
			for (Zombies zombie : zombies) {
				if (closeZombie(zombie)) {
					zombie.injured(this.getDamage());
					;
				}
			}
		}
		//TODO stop attack animation
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
	
	/**
	 * Checks if the character is touching an Item.
	 * 
	 * @param item
	 *            Item to be touched
	 * @return true if touching else false
	 */
	public boolean touchPowerUp(Item item) {
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> db859414463acaf11d0624312b80788b2730a7ce
		//System.out.println("checking");
		//System.out.println(item.getWidth());
		//System.out.println(item.getHeight());
		
		Point pupXY = new Point((int) (item.getX() + item.getWidth() / 2),
				(int) (item.getY() + item.getHeight() / 2));
<<<<<<< HEAD
=======
		Point chrXY = new Point(this.getCoord());
		int difX = (int) (chrXY.getX() - item.getX());
		int difY = (int) (chrXY.getY() - item.getY());
		int offsetX = (int) (-this.getHitBoxWidth() - item.getWidth()) / 2;
		int offsetY = (int) (-this.getHitBoxHeight() - item.getHeight()) / 2;

		if ((Math.abs(difX) + offsetX) < 0 && Math.abs(difY) +offsetY < 0) {
			//System.out.println("PowerupFound");
=======
		Point pupXY = new Point((int) (item.getImage().getX() + item.getImage().getWidth() / 2),
				(int) (item.getImage().getY() + item.getImage().getHeight() / 2));
>>>>>>> db859414463acaf11d0624312b80788b2730a7ce
		Point chrXY = new Point(this.getCoord());
		int difX = (int) (chrXY.getX() - item.getX());
		int difY = (int) (chrXY.getY() - item.getY());
		int offsetX = (int) (-this.getHitBoxWidth() - item.getWidth()) / 2;
		int offsetY = (int) (-this.getHitBoxHeight() - item.getHeight()) / 2;

		if ((Math.abs(difX) + offsetX) < 0 && Math.abs(difY) +offsetY < 0) {
>>>>>>> 1b7c7ca95b9b941eee4bf1fc1e236cf682e12894
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
	
	public void resetHealth() {
		this.health = getINITIALHEALTH();
	}

	public int getINITIALHEALTH() {
		return INITIALHEALTH;
	}

	public void drawAnimation(SpriteBatch batch, float stateTime) {
		
		TextureRegion currentFrame;
		
		if(animationDirection == "Left") {
			currentFrame = fresherWalkLeftAnimation.getKeyFrame(0, true);
		}
		else {
			currentFrame = fresherWalkRightAnimation.getKeyFrame(0, true);
		}
		if (type == "Sesher") {
			if(animationDirection == "Left") {
				currentFrame = sesherWalkLeftAnimation.getKeyFrame(0, true);
			}
			else {
				currentFrame = sesherWalkRightAnimation.getKeyFrame(0, true);
			}
		}

		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			isAttacking = true;
			attackTimer = 0;
		}		
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			isWalking = true;
			animationDirection = "Left";
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			isWalking = true;
			animationDirection = "Right";
		} else {
			isWalking = false;
		}
		
		if (type == "Sesher") {
			if (isAttacking) {
				attackTimer += 1;
				if(attackTimer > 10) {
					isAttacking = false;
					attackTimer = 0;
				}
				if(animationDirection == "Left") {
					currentFrame = sesherAttackLeftAnimation.getKeyFrame(stateTime, true);
				}
				else {
					currentFrame = sesherAttackRightAnimation.getKeyFrame(stateTime, true);
				}
			}
			else if(isWalking) {
				if(animationDirection == "Left") {
					currentFrame = sesherWalkLeftAnimation.getKeyFrame(stateTime, true);
				}else {
					currentFrame = sesherWalkRightAnimation.getKeyFrame(stateTime, true);	
				}
			}
		} else {
			if (isAttacking) {
				attackTimer += 1;
				if(attackTimer > 20) {
					isAttacking = false;
					attackTimer = 0;
				}
				if(animationDirection == "Left") {
					currentFrame = fresherAttackLeftAnimation.getKeyFrame(stateTime, true);
				}
				else {
					currentFrame = fresherAttackRightAnimation.getKeyFrame(stateTime, true);
				}
			}
			else if(isWalking) {
				if(animationDirection == "Left") {
					currentFrame = fresherWalkLeftAnimation.getKeyFrame(stateTime, true);
				}else {
					currentFrame = fresherWalkRightAnimation.getKeyFrame(stateTime, true);	
				}
			}
		}
		
		batch.draw(currentFrame, this.getX() + 30, this.getY() + 17);
		
	}

	
	
}
