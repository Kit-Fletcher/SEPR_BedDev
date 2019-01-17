package com.mygdx.game;

import java.util.ArrayList;
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
import com.mygdx.Helper;
import com.mygdx.uiutils.FontController;

/*
GameScreen: pass the power ups icons to game screen.
 */
public class GameScreen implements Screen {

	private int screenWidth;
	private int screenHeight;

	private final MainScreen game;
	private Texture bckgImage;
	private OrthographicCamera camera;

	private Stage stage;

	/// HUD menu
	private Table rootTable;
	private Skin skin;

	private FontController fontController;
	private FontController topControllerFontController;

	// -------------------------------------overlay tables
	private Table rootTableOverlay;

	// HUD menu element size constants
	private final static float healthElementSize = 32f;
	private final static float healthElementPadding = 2f;
	private final static float powerUpElementSize = 32f;
	private final static float powerUpElementPadding = 2f;
	private float topRightHudButtonWidth = 70f;
	private float topRightHudButtonheight = 24f;

	// ---------------------styles-------------------------
	TextButton.TextButtonStyle textButtonStyle;
	ImageButton.ImageButtonStyle imageButtonStyle;
	ImageButton.ImageButtonStyle imageButtonStylem;
	Label.LabelStyle hudLabelStyle;

	// -----------------------------pause group-------------

	private static final float MIN_VOLUME = 0;
	private static final float MAX_VOLUME = 10;

	private boolean isPause;
	private Group pauseGroup;
	private Label resulationValue;
	private Label volumeValue;
	private CharSequence resulationString = "1280 X 720";
	private CharSequence volumeLabelValue = "8";
	private Slider volumeValueSlider;
	private float volume;
	private TextureRegionDrawable health;
	private TextureRegionDrawable powerUp;

	// player parameters
	private Sprite playerSpr;
	private Player player;
	private Zombies zmb;
	private Texture img;
	private List<Zombies> zombies;
	private Sprite zmbSpr;
	// player life
	private ImageButton health1;
	private ImageButton health2;
	private ImageButton health3;
	private ImageButton health4;
	private ImageButton health5;
	private ImageButton health6;

	public GameScreen(final MainScreen game) {
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

		addUiStyles();
		createBottomHUD();
		createTopHUD();

		// add player to screen
		this.img = new Texture("MaleFresher.png");
		this.playerSpr = new Sprite(this.img);
		this.player = new Player(this.playerSpr, "Gresher", null);
		this.img = new Texture("Zombie1.png");
		this.zmbSpr = new Sprite(this.img);
		this.zmb = new Zombies(this.zmbSpr, "none", 1, 100, 1);
		this.zombies = new ArrayList<Zombies>();
		this.zombies.add(this.zmb);

	}

	private void addPlayer() {

		TextureRegion playerTextureRegion = new TextureRegion(new Texture(Gdx.files.internal("MaleFresher.png")));

		Sprite playerSprite = new Sprite();
		playerSprite.setRegion(playerTextureRegion);

		player = new Player(playerSprite, "Fresher");

	}

	private void addUiStyles() {

		// A skin can be loaded via JSON or defined programmatically, either is fine.
		// Using a skin is optional but strongly
		// recommended solely for the convenience of getting a texture, region, etc as a
		// drawable, tinted drawable, etc.

		Pixmap pixmap = new Pixmap(100, 100, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("clear", new Texture(pixmap));

		Pixmap pixmap1 = new Pixmap(100, 100, Pixmap.Format.RGBA8888);
		pixmap1.setColor(Color.CLEAR);
		pixmap1.fill();
		skin.add("white", new Texture(pixmap1));

		// Store the default libgdx font under the name "default".
		fontController = new FontController();
		fontController.addFont("playtime.ttf", "playtime.ttf");

		BitmapFont bfont = fontController.getFont("playtime.ttf");
		bfont.getData().setScale(.25f, .25f);
		skin.add("default", bfont);

		// Configure a TextButtonStyle and name it "default". Skin resources are stored
		// by type, so this doesn't overwrite the font.
		textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("clear", Color.GOLD);
		textButtonStyle.down = skin.newDrawable("clear", Color.GOLDENROD);
		// textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
		// textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);

		textButtonStyle.font = skin.getFont("default");

		imageButtonStyle = new ImageButton.ImageButtonStyle();
		imageButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
		imageButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		// imageButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
		// imageButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);

		imageButtonStylem = new ImageButton.ImageButtonStyle();
		TextureRegion minimap = new TextureRegion(new Texture(Gdx.files.internal("MaleFresher.png")));
		imageButtonStylem.up = new TextureRegionDrawable(minimap);// skin.newDrawable("white", Color.DARK_GRAY);
		// imageButtonStylem.down = skin.newDrawable("white", Color.DARK_GRAY);
		// imageButtonStylem.checked = skin.newDrawable("white", Color.BLUE);
		// imageButtonStylem.over = skin.newDrawable("white", Color.LIGHT_GRAY);

		hudLabelStyle = new Label.LabelStyle(fontController.getFont("playtime.ttf"), Color.RED);
	}

	private void createTopHUD() {

		final Label timerLabel = new Label("Time: ", hudLabelStyle);
		Label timerTextLabel = new Label("00 ", hudLabelStyle);
		final TextButton pauseButton = new TextButton("Pause", textButtonStyle);
		final TextButton inventoryButton = new TextButton("Inventory", textButtonStyle);

		rootTableOverlay = new Table().pad(2);
		rootTableOverlay.setFillParent(true);
		// rootTableOverlay.setDebug(true);

		Table topControllCell = new Table();
		topControllCell.add(timerLabel).expandX().left();
		topControllCell.add(timerTextLabel).expandX();
		topControllCell.row();
		topControllCell.add(pauseButton).size(topRightHudButtonWidth, topRightHudButtonheight).expand().pad(2).left();
		topControllCell.row();
		topControllCell.add(inventoryButton).size(topRightHudButtonWidth, topRightHudButtonheight).expand().pad(2)
				.left();
		// ----------------------------

		Table topRightHudTable = new Table();
		topRightHudTable.add(topControllCell).pad(2);

		rootTableOverlay.top().right();
		rootTableOverlay.add(topRightHudTable);

		rootTableOverlay.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {

				if (actor == pauseButton) {
					System.out.println("pauseButton  is clicked");
					if (!isPause)
						System.out.println("pauseGame()");
					pauseGame();

				}

				if (actor == inventoryButton) {
					System.out.println("inventoryButton  is clicked");
					game.setScreen(new Inventory(game));
				}
			}
		});

		stage.addActor(rootTableOverlay);
	}

	private void createBottomHUD() {

		// setup drawables

		health = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("hudimages/life.png"))));
		powerUp = new TextureRegionDrawable(
				new TextureRegion(new Texture(Gdx.files.internal("hudimages/power-up.png"))));

		rootTable = new Table();
		rootTable.setFillParent(true);
		// rootTable.setDebug(true);

		skin.add("textButtonStyle", textButtonStyle);

		skin.add("default", imageButtonStyle);

		skin.add("imageButtonStylem", imageButtonStylem);

		final ImageButton playerAvatarImageButton = new ImageButton(imageButtonStylem);

		Label healthLabel = new Label("HEALTH: ", hudLabelStyle);
		healthLabel.setWrap(true);

		Label powerUpLabel = new Label("P-UPS: ", hudLabelStyle);
		powerUpLabel.setWrap(true);

		Label currentObjectiveLabel = new Label("Current Objective ", hudLabelStyle);
		currentObjectiveLabel.setWrap(true);

		Label currentObjectiveName = new Label("Explore", hudLabelStyle);
		currentObjectiveName.setWrap(true);

		health1 = new ImageButton(health, health);
		health2 = new ImageButton(health, health);
		health3 = new ImageButton(health, health);
		health4 = new ImageButton(health, health);
		health5 = new ImageButton(health, health);
		health6 = new ImageButton(health, health);

		ImageButton powerUp1 = new ImageButton(powerUp, powerUp);
		ImageButton powerUp2 = new ImageButton(powerUp, powerUp);
		ImageButton powerUp3 = new ImageButton(powerUp, powerUp);
		ImageButton powerUp4 = new ImageButton(powerUp, powerUp);
		ImageButton powerUp5 = new ImageButton(powerUp, powerUp);
		ImageButton powerUp6 = new ImageButton(powerUp, powerUp);

		Table playerAvatarTable = new Table();
		Table healthPowerUpTable = new Table();
		Table currentObjectiveTable = new Table();

		rootTable.add(playerAvatarTable).bottom();
		rootTable.add(healthPowerUpTable).bottom();
		rootTable.add(currentObjectiveTable).bottom();
		rootTable.bottom();

		// --------------------------------
		Table statusCell = new Table();
		statusCell.add(health1).size(healthElementSize).pad(healthElementPadding);
		statusCell.add(health2).size(healthElementSize).pad(healthElementPadding);
		statusCell.add(health3).size(healthElementSize).pad(healthElementPadding);
		statusCell.add(health4).size(healthElementSize).pad(healthElementPadding);
		statusCell.add(health5).size(healthElementSize).pad(healthElementPadding);
		statusCell.add(health6).size(healthElementSize).pad(healthElementPadding);
		statusCell.row();
		statusCell.add(powerUp1).size(powerUpElementSize).pad(powerUpElementPadding);
		statusCell.add(powerUp2).size(powerUpElementSize).pad(powerUpElementPadding);
		statusCell.add(powerUp3).size(powerUpElementSize).pad(powerUpElementPadding);
		statusCell.add(powerUp4).size(powerUpElementSize).pad(powerUpElementPadding);
		statusCell.add(powerUp5).size(powerUpElementSize).pad(powerUpElementPadding);
		statusCell.add(powerUp6).size(powerUpElementSize).pad(powerUpElementPadding);

		statusCell.setBackground(new TextureRegionDrawable(
				new TextureRegion(new Texture(Gdx.files.internal("hudimages/statbckg.jpeg")))));
		// statusCell.setBackground(skin.newDrawable("clear", Color.YELLOW));
		// --------------

		Table mapTextCell = new Table();
		// mapTextCell.setDebug(true);
		mapTextCell.add(healthLabel).expandX().center().pad(healthElementPadding);
		mapTextCell.row();
		mapTextCell.add(powerUpLabel).expandX().center().pad(powerUpElementPadding);
		mapTextCell.setBackground(new TextureRegionDrawable(
				new TextureRegion(new Texture(Gdx.files.internal("hudimages/statbckg.jpeg")))));
		// mapTextCell.setBackground(skin.newDrawable("clear", Color.YELLOW));

		// -----------------------------------
		final Table playerAvatarCellTable = new Table();
		playerAvatarCellTable.add(playerAvatarImageButton).size(screenWidth / 5, screenHeight / 4);
		playerAvatarCellTable.setBackground(new TextureRegionDrawable(
				new TextureRegion(new Texture(Gdx.files.internal("hudimages/statbckg.jpeg")))));

		Table objectiveTextCell = new Table();
		// objectiveTextCell.setDebug(true);
		objectiveTextCell.add(currentObjectiveLabel).expandX().left().pad(healthElementPadding);
		objectiveTextCell.row();
		objectiveTextCell.add(currentObjectiveName).expandX().left().pad(powerUpElementPadding);
		objectiveTextCell.setBackground(new TextureRegionDrawable(
				new TextureRegion(new Texture(Gdx.files.internal("hudimages/statbckg.jpeg")))));

		playerAvatarTable.add(playerAvatarCellTable).size(screenWidth / 5, screenHeight / 4).pad(2);

		healthPowerUpTable.add(mapTextCell).size(screenWidth / 5, screenHeight / 5).padTop(2).padBottom(2);
		healthPowerUpTable.add(statusCell).size(Gdx.graphics.getWidth() * 1.8f / 5, screenHeight / 5).padTop(2)
				.padBottom(2);
		currentObjectiveTable.add(objectiveTextCell).size(Gdx.graphics.getWidth() / 5, screenHeight / 5).pad(2);

		stage.addActor(rootTable);

	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.235f, .245f, 0.2f, .1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// tell the camera to update its matrices.
		camera.update();
		player.getMovement();
		player.attack(zombies);

		for (Zombies zombie : zombies) {
			if (zombie.isAlive) {
				zombie.getMovement(player);
				zombie.attack(player);
			}
		}
		for (int i = 0; i < zombies.size(); i++) {
			if (zombies.get(i).isAlive() == false) {
				zombies.remove(i);
			}
		}
		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.batch.draw(bckgImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		player.draw(game.batch);
		for (Zombies zombie : zombies) {
			zombie.draw(game.batch);
		}

		game.batch.end();

		/*
		 * // process user input if (Gdx.input.isTouched()) { Vector3 touchPos = new
		 * Vector3(); touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		 * camera.unproject(touchPos); shipRectangle.x = touchPos.x - 64 / 2; }
		 */

		/*
		 * to get user input in desktop
		 */
		// if (Gdx.input.isKeyPressed(Input.Keys.LEFT))

		stage.act(Gdx.graphics.getDeltaTime());
		try {
			stage.draw();
		} catch (Exception e) {
			System.out.println(e);
		}

		// WILL WORK ONCE A player IS CREATED
		if (player.getHealth() < 100 && player.getHealth() > 80) {

			health1.setVisible(true);
			health2.setVisible(true);
			health3.setVisible(true);
			health4.setVisible(true);
			health5.setVisible(true);
			health6.setVisible(false);
		} else if (player.getHealth() < 80 && player.getHealth() > 60) {

			health1.setVisible(true);
			health2.setVisible(true);
			health3.setVisible(true);
			health4.setVisible(true);
			health5.setVisible(false);
			health6.setVisible(false);
		} else if (player.getHealth() < 60 && player.getHealth() > 40) {

			health1.setVisible(true);
			health2.setVisible(true);
			health3.setVisible(true);
			health4.setVisible(false);
			health5.setVisible(false);
			health6.setVisible(false);
		} else if (player.getHealth() < 40 && player.getHealth() > 20) {

			health1.setVisible(true);
			health2.setVisible(true);
			health3.setVisible(false);
			health4.setVisible(false);
			health5.setVisible(false);
			health6.setVisible(false);
		} else if (player.getHealth() < 20 && player.getHealth() >= 10) {

			health1.setVisible(true);
			health2.setVisible(false);
			health3.setVisible(false);
			health4.setVisible(false);
			health5.setVisible(false);
			health6.setVisible(false);
		} else if (player.getHealth() < 10 && player.getHealth() >= 0) {

			health1.setVisible(false);
			health2.setVisible(false);
			health3.setVisible(false);
			health4.setVisible(false);
			health5.setVisible(false);
			health6.setVisible(false);
		}

		// Test purpose

		/*
		 * check player damage by pressing d
		 */
		if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {

			player.setHealth(player.getHealth() - 10);
			Helper.println(" current health" + player.getHealth());
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

	public void pauseGame() {

		Drawable checkboxOff = new TextureRegionDrawable(
				new TextureRegion(new Texture(Gdx.files.internal("hudimages/unchecked-checkbox.png"))));
		Drawable checkboxOn = new TextureRegionDrawable(
				new TextureRegion(new Texture(Gdx.files.internal("hudimages/checked-checkbox.png"))));

		Pixmap pixmapBckg = new Pixmap(100, 8, Pixmap.Format.RGBA8888);
		pixmapBckg.setColor(Color.GOLD);
		// pixmapBckg.setColor(0,0,0,1f);
		pixmapBckg.fill();

		// new Texture(Gdx.files.internal("metor.png"))

		TextureRegion textureRegionOverlay = new TextureRegion(new Texture(pixmapBckg));

		isPause = true;
		pauseGroup = new Group();
		final Image semiTransparentBG = new Image(new Texture(Gdx.files.internal("hudimages/statbckg.jpeg")));
		semiTransparentBG.setSize(screenWidth / 1.5f, screenHeight / 1.5f);
		semiTransparentBG.setPosition(screenWidth / 6, screenHeight / 4);
		Color color = semiTransparentBG.getColor();
		semiTransparentBG.setColor(new Color(color.r, color.g, color.b, .85f));
		pauseGroup.addActor(semiTransparentBG);

		// slider style

		Slider.SliderStyle sliderStyle = new Slider.SliderStyle();
		sliderStyle.knob = checkboxOff;
		sliderStyle.knobDown = checkboxOn;
		sliderStyle.knobOver = checkboxOff;
		sliderStyle.background = new TextureRegionDrawable(textureRegionOverlay);

		// crate all other pause UI buttons with listener and add to pauseGroup

		Label resulationLabel = new Label("Resulation", hudLabelStyle);
		Label volumeLabel = new Label("Volume", hudLabelStyle);
		resulationValue = new Label(resulationString, hudLabelStyle);
		volumeValue = new Label(volumeLabelValue, hudLabelStyle);

		volumeValueSlider = new Slider(MIN_VOLUME, MAX_VOLUME, 1, false, sliderStyle);

		CheckBox.CheckBoxStyle checkBoxStyle = new CheckBox.CheckBoxStyle();
		checkBoxStyle.font = fontController.getFont("playtime.ttf");
		checkBoxStyle.fontColor = Color.RED;

		checkBoxStyle.checkboxOff = checkboxOff;
		checkBoxStyle.checkboxOn = checkboxOn;

		CheckBox fullScreenCheckBox = new CheckBox("Full Screen", checkBoxStyle);
		// resulationLabel.setPosition(semiTransparentBG.getX()+16,screenHeight/4+semiTransparentBG.getHeight()-16);

		Table configTable = new Table();
		configTable.setPosition(screenWidth / 2, semiTransparentBG.getHeight() - 16);
		configTable.add(resulationLabel).padRight(screenWidth / 12);
		configTable.add(resulationValue);
		configTable.row();
		configTable.add(volumeLabel).padRight(screenWidth / 12);
		configTable.add(volumeValueSlider);
		configTable.add(volumeValue).padRight(screenWidth / 12);
		configTable.row();
		configTable.add(fullScreenCheckBox).padRight(screenWidth / 12);

		pauseGroup.addActor(configTable);

		final TextButton backButton = new TextButton("Back", textButtonStyle);
		backButton.setSize(topRightHudButtonWidth, topRightHudButtonheight);
		backButton.setPosition(screenWidth / 6 + semiTransparentBG.getWidth() / 2 - topRightHudButtonWidth / 2,
				screenHeight / 3);
		pauseGroup.addActor(backButton);

		pauseGroup.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("Clicked! Is checked: Pause ");
				// textButton.setText("Command");
				// g.setScreen( new GameScreen());
				// buttonFlag = true;
				if (actor == backButton) {
					Helper.println("Pause screen touched ");
					resumeGame();
				}

			}
		});

		volumeValueSlider.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Slider slider = (Slider) actor;

				float value = slider.getValue();

				if (value == 0) {
					// appProperties.setVolume(0);
					volume = 0;
				} else {
					// appProperties.setVolume((int) value);
					volume = value;
				}

				updateVelocityText();
			}

		});

		stage.addActor(pauseGroup);

	}

	private void updateVelocityText() {
		// float value = appProperties.getVolume();

		volumeValueSlider.setValue(volume);

		volumeValue.setText(String.valueOf(volume));
		volumeValue.invalidate();
	}

	public void resumeGame() {

		if (isPause) {
			isPause = false;
			pauseGroup.remove();
		}
	}

}
