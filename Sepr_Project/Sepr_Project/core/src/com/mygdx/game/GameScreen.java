package com.mygdx.game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
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

	private FontController fontController;

	// ---------------------styles-------------------------
	TextButton.TextButtonStyle textButtonStyle;
	ImageButton.ImageButtonStyle imageButtonStyle;
	ImageButton.ImageButtonStyle imageButtonStylem;
	Label.LabelStyle hudLabelStyle;
	// player parameters
	private Sprite playerSpr;
	private Player player;
	private boolean stick;

	// mike parameters
	private Mike mike;

	// zombie parameters
	private Sprite zombieSprite;
	private Zombies zombie;
	private List<Zombies> zombies;

	// Building parameters
	private Sprite building;
	private static HashMap<String, Sprite> buildings;
	private Boolean start = true;
	private String old;

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
		changeScreen("CompSci");

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

	private void addBuilding(String name, int x, int y, int sizeX, int sizeY) {
		building = new Sprite();
		building.setSize(sizeX, sizeY);
		building.setPosition(x, y);
		buildings.put(name, building);

	}

	private void changeScreen(String name) {
		zombies.clear();
		buildings.clear();
		if (name == "CompSci") {

			bckgImage = new Texture((Gdx.files.internal(("hardware_lab.png"))));

			addBuilding("LakeSide1", 0, 435, 37, 28);
			addBuilding("LakeSide2", 0, 255, 37, 28);
			addBuilding("LakeSide3", 0, 0, 37, 28);
			if (start) {
				newRoom(new Point(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2), 0, false);
				start = false;
				addMike();

				// TODO put mike in
			} else if (stick) {
				newRoom(new Point(20, 255), 1, false);
				addZombie("mBoss");
				// TODO if this zombie dies then you win

			} else {
				// Placeholder before stick is used
				newRoom(new Point(20, 255), 1, false);
				addZombie("mBoss");
			}
			old = name;

		} else if (name == "Central") {
			bckgImage = new Texture((Gdx.files.internal(("environments/central_hall.png"))));
			addBuilding("LakeSide1", 80, 166, 33, 13);
			addBuilding("LakeSide2", 529, 166, 33, 13);
			newRoom(new Point(115, 166), 3, false);
			old = name;
		} else if (name == "Piazza") {
			bckgImage = new Texture((Gdx.files.internal((".png"))));
			newRoom(new Point(115, 166), 3, false);
			addBuilding("LakeSide1", 80, 166, 33, 13);
			addBuilding("LakeSide2", 529, 166, 33, 13);
			old = name;
			// TODO change once got a piazza image
		} else if (name.startsWith("LakeSide")) {
			if (old == "Central") {
				newRoom(new Point(435, 304), 2, false);
			} else if (old == "CompSci") {
				newRoom(new Point(215, 157), 2, false);
			} else if (old == "Piazza") {
				// TODO once piazza is ready
			}
			bckgImage = new Texture((Gdx.files.internal(("lakeside_way_odd.png"))));
			old = "LakeSide";
			mike.setAlpha(0f);
			addBuilding("CompSci", 161, 127, 50, 60);
			addBuilding("Central", 524, 297, 67, 95);
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

		// tell the camera to update its matrices.
		camera.update();
		player.getMovement();
		player.attack(zombies);
		// use this code to check where coordinates are on the screen
		// System.out.println(Gdx.input.getX() + " " + Gdx.input.getY());
		for (String name : buildings.keySet()) {
			if (player.touchBuilding(buildings.get(name))) {
				this.changeScreen(name);
				break;
			}
		}
		for (Zombies zombie : zombies) {
			if (zombie.isAlive()) {
				zombie.getMovement(player);
				zombie.attack(player);
			} else {
				if (zombie.type == "mBoss") {
					// TODO add in win thing
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
		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.batch.draw(bckgImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		player.draw(game.batch);
		mike.draw(game.batch);
		for (Zombies zombie : zombies) {
			zombie.draw(game.batch);
		}

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
		gameHud.stage.draw();

		gameHud.update(delta);

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
