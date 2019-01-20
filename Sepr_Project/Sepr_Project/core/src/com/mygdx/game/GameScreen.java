package com.mygdx.game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.uiutils.FontController;

/*
GameScreen
 */
public class GameScreen implements Screen {

	private int screenWidth;
	private int screenHeight;
	private GameHud gameHud;

	private final Main game;
	private Texture bckgImage;
	private OrthographicCamera camera;

	private Stage stage;

	private Skin skin;


	// player parameters
	private Sprite playerSpr;
	private Player player;
	private boolean stick = false;
	private float stateTime;

	// mike parameters
	private Mike mike;
	private boolean won = false;
	private Sprite victorySprite;
	private Sprite stickSprite;
	

	// zombie parameters
	private Sprite zombieSprite;
	private Zombies zombie;
	private List<Zombies> zombies;
	// Building parameters
	private Sprite building;
	private static HashMap<String, Sprite> buildings;
	private Boolean start = true;
	private String old;
	//powerUps parameters;
	private PowerUps redVK;
	private PowerUps blueVK;
	private PowerUps ylwVK;
	private Texture redTex;
	private Texture bluTex;
	private Texture ylwTex;
	private HashMap<Integer,PowerUps> powerUps = new HashMap<Integer, PowerUps>();// where keys are powerups ids and values are powerups.

	// updating Hud parameters
	private String objective;
	private String[] exp;// stores names of explored buildings.

	public GameScreen(final Main game, String playerType) {
		this.game = game;
		this.stage = new Stage();// this can be alse game.stage;
		skin = new Skin();
		Gdx.input.setInputProcessor(stage);
		// create overlay stage
		bckgImage = new Texture((Gdx.files.internal(("lakeside_way.png"))));

		// get scrren width and scrren height from gdx graphics
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		// camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, screenWidth, screenHeight);

		gameHud = new GameHud(game.batch, this);

		// this.img = new Texture("MaleFresher.png");
		// this.playerSpr = new Sprite(this.img);
		// this.player = new Player(this.playerSpr, "Sesher", null);
		// this.img = new Texture("Zombie1.png");
		// this.zmbSpr = new Sprite(this.img);
		// this.zmb = new Zombies(this.zmbSpr, "none", 1, 100, 1);
		// this.zombies = new ArrayList<Zombies>();
		// this.zombies.add(this.zmb);
		// add player to screen
		addPlayer(playerType);
		GameScreen.buildings = new HashMap<String, Sprite>();
		this.zombies = new ArrayList<Zombies>();
		this.exp = new String[2];
		//create powerups 
		this.redTex= new Texture(Gdx.files.internal("items/redVK.png"));
		this.bluTex= new Texture(Gdx.files.internal("items/blueVK.png"));
		this.ylwTex= new Texture(Gdx.files.internal("items/yellowVK.png"));
		redVK= new PowerUps(new Sprite(redTex),PowerUpType.REDVK.getEffect());
		blueVK= new PowerUps(new Sprite(bluTex),PowerUpType.BLUEVK.getEffect());
		ylwVK= new PowerUps(new Sprite(ylwTex),PowerUpType.YLWVK.getEffect());
		redVK.active = false;
		blueVK.active = false;
		ylwVK.active = false;
		TextureRegion victoryTextureRegion = new TextureRegion(new Texture(Gdx.files.internal("victory.png")));
		victorySprite = new Sprite();
		victorySprite.setRegion(victoryTextureRegion);
		victorySprite.setSize(420,180);
		victorySprite.setPosition(Gdx.graphics.getWidth()/2 - victorySprite.getWidth()/2,Gdx.graphics.getHeight()/2 - victorySprite.getHeight()/2 );
		TextureRegion stickTextureRegion = new TextureRegion(new Texture(Gdx.files.internal("items/mikesStick.png")));
		stickSprite = new Sprite();
		stickSprite.setRegion(stickTextureRegion);
		stickSprite.setSize(30,50);
		stickSprite.setPosition(200,400);
		stickSprite.setAlpha(0f);
		changeScreen("CompSci");


		stateTime = 0f;
	}

	private void addPlayer(String playerType) {

		TextureRegion playerTextureRegion = new TextureRegion(new Texture(Gdx.files.internal("MaleFresher.png")));

		Sprite playerSprite = new Sprite();
		playerSprite.setRegion(playerTextureRegion);

		player = new Player(playerSprite, playerType, null);

	}

	private void addMike() {
		TextureRegion mikeTextureRegion = new TextureRegion(new Texture(Gdx.files.internal("mike.png")));

		Sprite mikeSprite = new Sprite();
		mikeSprite.setRegion(mikeTextureRegion);

		mike = new Mike(mikeSprite);

		mike.setPosition(314, 257);

	}

	private void addZombie(String boss) {

		TextureRegion zombieTextureRegion = new TextureRegion(new Texture(Gdx.files.internal("Zombie1.png")));

		Sprite zombieSprite = new Sprite();
		zombieSprite.setRegion(zombieTextureRegion);
		if (boss == "mBoss") {
			this.zombie = new Zombies(zombieSprite, "mBoss", 1, 100, 1);

		} else {
			this.zombie = new Zombies(zombieSprite, "Pawn", 1, 100, 1);

		}

		this.zombies.add(zombie);

	}
	
	/*
	 * adds powerups to the scene. 
	 * @param PowerUps powerup 
	 * 		powerup to be added.
	 * @param float x
	 * 		position relative to screenwidth.
	 * @param float y
	 * 		position relative to screeheight.
	 */
	
    private void addPowerUp(PowerUps powerup, float x, float y) {

        powerup.setPosition(x, y);
        powerUps.put(powerup.getId(),powerup);

    }

	private void addBuilding(String name, int x, int y, int sizeX, int sizeY) {
		building = new Sprite();
		building.setSize(sizeX, sizeY);
		building.setPosition(x, y);
		buildings.put(name, building);

	}

	private void changeScreen(String name) {
		
		zombies.clear();
		buildings.clear();
		redVK.active = false;
		blueVK.active = false;
		ylwVK.active = false;
		if (name == "CompSci") {

			bckgImage = new Texture((Gdx.files.internal(("hardware_lab.png"))));
			
			
			addBuilding("LakeSide1", 0, 435, 37, 28);
			addBuilding("LakeSide2", 0, 255, 37, 28);
			addBuilding("LakeSide3", 0, 0, 37, 28);
			
			if (start) {
				newRoom(new Point(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2), 0, false);
				start = false;
				addMike();

			} else if (stick) {

				newRoom(new Point(20, 255), 0, false);

				addPowerUp(redVK, 400, 200);
				redVK.active = true;
				newRoom(new Point(20, 255), 1, false);
				addZombie("mBoss");
				// TODO if this zombie dies then you win

			} else {
				// Placeholder before stick is used
				newRoom(new Point(20, 255), 0, false);
				mike.setAlpha(1f);
				
			}
			old = name;

		} else if (name == "Central") {
			bckgImage = new Texture((Gdx.files.internal(("environments/central_hall.png"))));
			addPowerUp(blueVK, 200, 200);
			blueVK.active = true;
			addBuilding("LakeSide1", 80, 166, 33, 13);
			addBuilding("LakeSide2", 529, 166, 33, 13);
			newRoom(new Point(115, 166), 3, false);
			old = name;
		} else if (name == "Piazza") {
			bckgImage = new Texture((Gdx.files.internal(("piazza.png"))));
			newRoom(new Point(430, 46), 3, false);
			addBuilding("LakeSide1", 522, 46, 33, 33);
			addPowerUp(ylwVK, 400, 200);
			ylwVK.active = true;
			
//			addBuilding("LakeSide1", 80, 166, 33, 13);
//			addBuilding("LakeSide2", 529, 166, 33, 13);
			if(stick == false) {
				stickSprite.setAlpha(1f);
				addBuilding("Stick", (int)stickSprite.getX(), (int)stickSprite.getY(), (int)stickSprite.getWidth(), (int)stickSprite.getHeight());
				
			}else {
				stickSprite.setAlpha(0f);
			}
			old = name;
		} else if (name.startsWith("LakeSide")) {
			if (old == "Central") {
				newRoom(new Point(180, 173), 2, false);
			} else if (old == "CompSci") {
				newRoom(new Point(250, 275), 2, false);
			} else if (old == "Piazza") {
				stickSprite.setAlpha(0f);
				newRoom(new Point(425, 225), 2, false);
			}
			bckgImage = new Texture((Gdx.files.internal(("gamemap.png"))));
			old = "LakeSide";
			mike.setAlpha(0f);
			addBuilding("CompSci", 200, 375, 102, 89);
			addBuilding("Central", 103, 173, 83, 77);
			addBuilding("Piazza", 415, 325,159,90 );
		}
	}

	private void newRoom(Point startPos, int numZombies, Boolean boss) {
		player.setPosition((float) startPos.getX(), (float) startPos.getY());
		if (numZombies != 0) {
			for (int x = 0; x < numZombies; x++) {
				addZombie("");
			}
		}

	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		Gdx.input.setInputProcessor(gameHud.getStage());
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.235f, .245f, 0.2f, .1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
		
		// tell the camera to update its matrices.
		camera.update();
		player.getMovement();
		player.attack(zombies);
		// use this code to check where coordinates are on the screen
		//System.out.println(Gdx.input.getX() + " " + Gdx.input.getY());
		if(won == false) {
			for (String name : buildings.keySet()) {
				if (player.touchBuilding(buildings.get(name))) {
					if(name != "Stick") {
						this.changeScreen(name);
						break;
					}else {
						System.out.println("stick got");
					
						stickSprite.setAlpha(0f);
						this.stick = true;
						break;
					}
					
				}
			}
			if(redVK.active) {
				if(player.touchPowerUp(redVK)) {
					//if the powerup is consumed, apply its effect and remove it from the scene. 
					redVK.applyEffect(player);
					redVK.active = false; 
				}
			}
			if(blueVK.active) {
				if(player.touchPowerUp(blueVK)) {
					//if the powerup is consumed, apply its effect and remove it from the scene. 
					blueVK.applyEffect(player);
					blueVK.active = false; 
				}
			}
			if(ylwVK.active) {
				if(player.touchPowerUp(ylwVK)) {
					//if the powerup is consumed, apply its effect and remove it from the scene. 
					ylwVK.applyEffect(player);
					ylwVK.active = false; 
				}
			}
				
			for (Zombies zombie : zombies) {
				if (zombie.isAlive()) {
					zombie.getMovement(player);
					player = zombie.attack(player);
				} else {
					if (zombie.type == "mBoss") {
						won = true;
					}
					zombie.setAlpha((float) (zombie.getColor().a * 0.95));
				}
			}
			// removes dead zombies
			if (System.currentTimeMillis() % 10000 < 1000) {
				for (int i = 0; i < zombies.size(); i++) {
					if (zombies.get(i).isAlive() == false) {
						zombies.remove(i);
					}
				}
			}
		}
		
		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.batch.draw(bckgImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		if(won ==false) {
			
		
			player.drawAnimation(game.batch, stateTime);
			//player.draw(game.batch);
			mike.draw(game.batch);
			for (Zombies zombie : zombies) {
				zombie.draw(game.batch);
			}
			if(redVK.active) {
				redVK.draw(game.batch);
			}
			if(blueVK.active) {
				blueVK.draw(game.batch);
			}
			if(ylwVK.active) {
				ylwVK.draw(game.batch);
			}
			
			
		}else {
				victorySprite.draw(game.batch);

		}
		stickSprite.draw(game.batch);
		game.batch.end();
		mike.speak(player, old);
		stage.act(Gdx.graphics.getDeltaTime());
		try {
			stage.draw();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		if (player.isAlive == false) {
			System.out.println("closing");
			game.setScreen(new MainScreen(game));
		}

		// Set batch to now draw what the Hud camera sees.
		game.batch.setProjectionMatrix(gameHud.stage.getCamera().combined);
		gameHud.update(Gdx.graphics.getDeltaTime());
		gameHud.stage.draw();

		//gameHud.update(delta);
        if(player.getHealth() < 100 && player.getHealth() > 80){

            gameHud.health1.setVisible(true);
            gameHud.health2.setVisible(true);
            gameHud.health3.setVisible(true);
            gameHud.health4.setVisible(true);
            gameHud.health5.setVisible(true);
            gameHud.health6.setVisible(false);
        }else if(player.getHealth() < 80 && player.getHealth() > 60){

            gameHud.health1.setVisible(true);
            gameHud.health2.setVisible(true);
            gameHud.health3.setVisible(true);
            gameHud.health4.setVisible(true);
            gameHud.health5.setVisible(false);
            gameHud.health6.setVisible(false);
        }else if(player.getHealth() < 60 && player.getHealth() > 40){

            gameHud.health1.setVisible(true);
            gameHud.health2.setVisible(true);
            gameHud.health3.setVisible(true);
            gameHud.health4.setVisible(false);
            gameHud.health5.setVisible(false);
            gameHud.health6.setVisible(false);
        }else if(player.getHealth() < 40 && player.getHealth() > 20){

            gameHud.health1.setVisible(true);
            gameHud.health2.setVisible(true);
            gameHud.health3.setVisible(false);
            gameHud.health4.setVisible(false);
            gameHud.health5.setVisible(false);
            gameHud.health6.setVisible(false);
        }else if(player.getHealth() < 20 && player.getHealth() >= 10){

            gameHud.health1.setVisible(true);
            gameHud.health2.setVisible(false);
            gameHud.health3.setVisible(false);
            gameHud.health4.setVisible(false);
            gameHud.health5.setVisible(false);
            gameHud.health6.setVisible(false);
        }else if(player.getHealth() < 10 && player.getHealth() >= 0){

            gameHud.health1.setVisible(false);
            gameHud.health2.setVisible(false);
            gameHud.health3.setVisible(false);
            gameHud.health4.setVisible(false);
            gameHud.health5.setVisible(false);
            gameHud.health6.setVisible(false);
        }  
		if (player.type == "Sesher") {
			//error when ever adding sesher, xombime is a place holder
			TextureRegion textureRegion = new TextureRegion(new Texture(Gdx.files.internal("Zombie1.png")));
			gameHud.updatePlayerAvatar(textureRegion);
		}
		if (stick) {
			gameHud.updateCurrentObjective(objective);
		}

		gameHud.updateExplored(exp.length);

	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		bckgImage.dispose();
	}

	public Player getPlayer() {
		return player;
	}

	public Main getGame() {
		return game;
	}
}
