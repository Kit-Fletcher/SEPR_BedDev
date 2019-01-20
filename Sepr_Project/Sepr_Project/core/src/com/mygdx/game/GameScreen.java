package com.mygdx.game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameScreen implements Screen {

	private GameHud gameHud;

	private final Main game;
	private Texture bckgImage;

	private Stage stage;

	// player parameters
	private Player player;
	private boolean stick = false;
	private float stateTime;

	// mike parameters
	private Mike mike;
	private boolean won = false;
	private Sprite victorySprite;
	private Sprite stickSprite;

	// zombie parameters
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

	// updating Hud parameters
	private String objective;

	public GameScreen(final Main game, String playerType) {
		this.game = game;
		this.stage = new Stage();// this can be alse game.stage;
		Gdx.input.setInputProcessor(stage);
		// create overlay stage
		bckgImage = new Texture((Gdx.files.internal(("lakeside_way.png"))));

		gameHud = new GameHud(game.batch, this);

		addPlayer(playerType);
		GameScreen.buildings = new HashMap<String, Sprite>();
		this.zombies = new ArrayList<Zombies>();
		
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
	
    private void addPowerUp(PowerUps powerup, float x, float y) {
        powerup.setPosition(x, y);
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

			} else {
				newRoom(new Point(20, 255), 0, false);
				mike.setAlpha(1f);
			}
			old = name;

		} 
		else if (name == "Central") {
			bckgImage = new Texture((Gdx.files.internal(("environments/central_hall.png"))));
			addPowerUp(blueVK, 200, 200);
			blueVK.active = true;
			addBuilding("LakeSide1", 80, 166, 33, 13);
			addBuilding("LakeSide2", 529, 166, 33, 13);
			newRoom(new Point(115, 166), 3, false);
			old = name;
		} 
		else if (name == "Piazza") {
			bckgImage = new Texture((Gdx.files.internal(("piazza.png"))));
			newRoom(new Point(430, 46), 3, false);
			addBuilding("LakeSide1", 522, 46, 33, 33);
			addPowerUp(ylwVK, 400, 200);
			ylwVK.active = true;
			
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

		player.getMovement();
		player.attack(zombies);
		
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
					redVK.applyEffect(player);
					redVK.active = false; 
				}
			}
			
			if(blueVK.active) {
				if(player.touchPowerUp(blueVK)) {
					blueVK.applyEffect(player);
					blueVK.active = false; 
				}
			}
			
			if(ylwVK.active) {
				if(player.touchPowerUp(ylwVK)) {
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
		
		game.batch.begin();
		game.batch.draw(bckgImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		if(won ==false) {	
			player.drawAnimation(game.batch, stateTime);
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

		gameHud.update(Gdx.graphics.getDeltaTime());
		gameHud.stage.draw();

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
			TextureRegion textureRegion = new TextureRegion(new Texture(Gdx.files.internal("characters_sesher/MaleSesherWalkStationary.png")));
			gameHud.updatePlayerAvatar(textureRegion);
		}
		if (stick) {
			gameHud.updateCurrentObjective(objective);
		}

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
