package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.Helper;
import com.mygdx.uiutils.FontController;

public class GameHud implements Disposable {

    public Stage stage;
    private Viewport viewport;
    GameScreen gameScreen;


    private FontController fontController;


    //---------------------styles-------------------------
    TextButton.TextButtonStyle textButtonStyle;
    ImageButton.ImageButtonStyle imageButtonStyle;
    ImageButton.ImageButtonStyle imageButtonStylem;
    Label.LabelStyle hudLabelStyle;
    /// HUD menu
    private Table rootTable;
    private Skin skin;
    // player life
    ImageButton health1;
    ImageButton health2;
    ImageButton health3;
    ImageButton health4;
    ImageButton health5;
    ImageButton health6;

    //score && time tracking variables
    private Integer worldTimer;
    private float timeCount;
    private static Integer score;
    private boolean timeUp;

    private TextureRegionDrawable health;
    private TextureRegionDrawable powerUp;


    //Scene2D Widgets
    private Label countdownLabel, timeLabel, linkLabel,timerTextLabel;
    private static Label scoreLabel;


    // HUD menu element size constants
    private final static float healthElementSize = 32f;
    private final static float healthElementPadding = 2f;
    private final static float powerUpElementSize = 32f;
    private final static float powerUpElementPadding = 2f;
    private float topRightHudButtonWidth = 70f;
    private float topRightHudButtonheight = 24f;


    //-------------------------------------overlay tables
    private Table rootTableOverlay;


    //-----------------------------pause group-------------

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

    private int screenWidth;
    private int screenHeight;

    // player parameters
    private Player player;
    private ImageButton playerAvatarImageButton;
    private Label currentObjectiveName;


    public GameHud(SpriteBatch sb, GameScreen gameScreen) {
        //define tracking variables
        worldTimer = 250;
        timeCount = 0;
        score = 0;
        skin = new Skin();
        // get scrren width and scrren height from gdx graphics
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        this.gameScreen = gameScreen;
        //player = gameScreen.getPlayer();

        //setup the HUD viewport using a new camera seperate from gamecam
        //define stage using that viewport and games spritebatch
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, sb);


        addUiStyles();
        createBottomHUD();
        createTopHUD();

    }


    private void createTopHUD() {


        final Label timerLabel = new Label("Time: ",hudLabelStyle);
        timerTextLabel = new Label("00 ",hudLabelStyle);
        final TextButton pauseButton = new TextButton("Pause",textButtonStyle);
        final TextButton inventoryButton = new TextButton("Inventory",textButtonStyle);

        rootTableOverlay = new Table().pad(2);
        rootTableOverlay.setFillParent(true);
//        rootTableOverlay.setDebug(true);


        Table topControllCell = new Table();
        topControllCell.add(timerLabel).expandX().left();
        topControllCell.add(timerTextLabel).expandX();
        topControllCell.row();
        topControllCell.add(pauseButton).size(topRightHudButtonWidth,topRightHudButtonheight).expand().pad(2).left();
        topControllCell.row();
        topControllCell.add(inventoryButton).size(topRightHudButtonWidth,topRightHudButtonheight).expand().pad(2).left();
        //----------------------------

        Table topRightHudTable = new Table();
        topRightHudTable.add(topControllCell).pad(2);

        rootTableOverlay.top().right();
        rootTableOverlay.add(topRightHudTable);

        rootTableOverlay.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {

                if(actor ==  pauseButton )
                {
                    System.out.println("pauseButton  is clicked");
                    if(!isPause)
                        System.out.println("pauseGame()");
                    pauseGame();

                }

                if(actor == inventoryButton )
                {
                    System.out.println("inventoryButton  is clicked");
                    gotToInventoryScreen();

                }
            }
        });

        stage.addActor(rootTableOverlay);

    }


    public void pauseGame(){

        Drawable checkboxOff = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("hudimages/unchecked-checkbox.png"))));
        Drawable checkboxOn = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("hudimages/checked-checkbox.png"))));

        Pixmap pixmapBckg = new Pixmap(100, 8, Pixmap.Format.RGBA8888);
        pixmapBckg.setColor(Color.GOLD);
//        pixmapBckg.setColor(0,0,0,1f);
        pixmapBckg.fill();

//        new Texture(Gdx.files.internal("metor.png"))

        TextureRegion textureRegionOverlay = new TextureRegion(new Texture(pixmapBckg));

        isPause = true;
        pauseGroup = new Group();
        final Image semiTransparentBG= new Image(new Texture(Gdx.files.internal("hudimages/statbckg.jpeg")));
        semiTransparentBG.setSize(screenWidth/1.5f,screenHeight/1.5f);
        semiTransparentBG.setPosition(screenWidth/6,screenHeight/4);
        Color color=semiTransparentBG.getColor();
        semiTransparentBG.setColor(new Color(color.r,color.g,color.b,.85f));
        pauseGroup.addActor(semiTransparentBG);


        // slider style

        Slider.SliderStyle sliderStyle = new Slider.SliderStyle();
        sliderStyle.knob = checkboxOff ;
        sliderStyle.knobDown = checkboxOn;
        sliderStyle.knobOver = checkboxOff;
        sliderStyle.background = new TextureRegionDrawable(textureRegionOverlay);


        //crate all other pause UI buttons with listener and add to pauseGroup

        Label resulationLabel = new Label("Resulation",hudLabelStyle);
        Label volumeLabel = new Label("Volume",hudLabelStyle);
        resulationValue = new Label(resulationString,hudLabelStyle);
        volumeValue = new Label(volumeLabelValue,hudLabelStyle);

        volumeValueSlider = new Slider(MIN_VOLUME, MAX_VOLUME, 1, false, sliderStyle);

        CheckBox.CheckBoxStyle checkBoxStyle = new CheckBox.CheckBoxStyle();
        checkBoxStyle.font = fontController.getFont("playtime.ttf");
        checkBoxStyle.fontColor = Color.RED;

        checkBoxStyle.checkboxOff = checkboxOff;
        checkBoxStyle.checkboxOn = checkboxOn;

        CheckBox fullScreenCheckBox = new CheckBox("Full Screen",checkBoxStyle);
//        resulationLabel.setPosition(semiTransparentBG.getX()+16,screenHeight/4+semiTransparentBG.getHeight()-16);

        Table configTable = new Table();
        configTable.setPosition(screenWidth/2,semiTransparentBG.getHeight()-16);
        configTable.add(resulationLabel).padRight(screenWidth/12);
        configTable.add(resulationValue);
        configTable.row();
        configTable.add(volumeLabel).padRight(screenWidth/12);
        configTable.add(volumeValueSlider);
        configTable.add(volumeValue).padRight(screenWidth/12);
        configTable.row();
        configTable.add(fullScreenCheckBox).padRight(screenWidth/12);

        pauseGroup.addActor(configTable);

        final TextButton backButton = new TextButton("Back",textButtonStyle);
        backButton.setSize(topRightHudButtonWidth,topRightHudButtonheight);
        backButton.setPosition(screenWidth/6+semiTransparentBG.getWidth()/2-topRightHudButtonWidth/2,screenHeight/3);
        pauseGroup.addActor(backButton);


        pauseGroup.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                System.out.println("Clicked! Is checked: Pause ");
//                textButton.setText("Command");
                // g.setScreen( new GameScreen());
                //   buttonFlag = true;
                if(actor == backButton )
                {
                    Helper.println("Pause screen touched ");
                    resumeGame();
                }

            }
        });

        volumeValueSlider.addListener(new ChangeListener()
        {

            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                Slider slider = (Slider) actor;

                float value = slider.getValue();

                if (value == 0)
                {
                    //appProperties.setVolume(0);
                    volume = 0;
                }
                else
                {
                    //appProperties.setVolume((int) value);
                    volume = value;
                }

                updateVolumeText();
            }

        });

        stage.addActor(pauseGroup);

    }


    private void updateVolumeText()
    {
        // float value = appProperties.getVolume();

        volumeValueSlider.setValue(volume);

        volumeValue.setText(String.valueOf(volume));
        volumeValue.invalidate();
    }


    private void gotToInventoryScreen() {

        Helper.println("go to inventory");
        gameScreen.getGame().setScreen(new Inventory(gameScreen.getGame(),gameScreen));
    }


    private void createBottomHUD() {

        // setup drawables

        health = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("hudimages/life.png"))));
        powerUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("hudimages/power-up.png"))));


        rootTable = new Table();
        rootTable.setFillParent(true);
//        rootTable.setDebug(true);




        skin.add("textButtonStyle", textButtonStyle);


        skin.add("default", imageButtonStyle);


        skin.add("imageButtonStylem", imageButtonStylem);


        playerAvatarImageButton = new ImageButton(imageButtonStylem);





        Label healthLabel = new Label("Health: ", hudLabelStyle);
        healthLabel.setWrap(true);

        Label powerUpLabel = new Label("Explored: ", hudLabelStyle);
        powerUpLabel.setWrap(true);

        Label currentObjectiveLabel = new Label("Current Objective ", hudLabelStyle);
        currentObjectiveLabel.setWrap(true);

        currentObjectiveName = new Label("Explore", hudLabelStyle);
        currentObjectiveName.setWrap(true);

        health1 = new ImageButton(health,health);
        health2 = new ImageButton(health,health);
        health3 = new ImageButton(health,health);
        health4 = new ImageButton(health,health);
        health5 = new ImageButton(health,health);
        health6 = new ImageButton(health,health);

        ImageButton powerUp1 = new ImageButton(powerUp,powerUp);
        ImageButton powerUp2 = new ImageButton(powerUp,powerUp);
        ImageButton powerUp3 = new ImageButton(powerUp,powerUp);
        ImageButton powerUp4 = new ImageButton(powerUp,powerUp);
        ImageButton powerUp5 = new ImageButton(powerUp,powerUp);
        ImageButton powerUp6 = new ImageButton(powerUp,powerUp);


        Table playerAvatarTable = new Table();
        Table healthPowerUpTable = new Table();
        Table currentObjectiveTable = new Table();


        rootTable.add(playerAvatarTable).bottom();
        rootTable.add(healthPowerUpTable).bottom();
        rootTable.add(currentObjectiveTable).bottom();
        rootTable.bottom();

        //--------------------------------
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

        statusCell.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("hudimages/statbckg.jpeg")))));
//        statusCell.setBackground(skin.newDrawable("clear", Color.YELLOW));
//--------------

        Table mapTextCell = new Table();
//        mapTextCell.setDebug(true);
        mapTextCell.add(healthLabel).expandX().center().pad(healthElementPadding);
        mapTextCell.row();
        mapTextCell.add(powerUpLabel).expandX().center().pad(powerUpElementPadding);
        mapTextCell.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("hudimages/statbckg.jpeg")))));
//        mapTextCell.setBackground(skin.newDrawable("clear", Color.YELLOW));

        //-----------------------------------
        final Table playerAvatarCellTable = new Table();
        playerAvatarCellTable.add(playerAvatarImageButton).size(screenWidth/5, screenHeight/4);
        playerAvatarCellTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("hudimages/statbckg.jpeg")))));

        Table objectiveTextCell = new Table();
//        objectiveTextCell.setDebug(true);
        objectiveTextCell.add(currentObjectiveLabel).expandX().left().pad(healthElementPadding);
        objectiveTextCell.row();
        objectiveTextCell.add(currentObjectiveName).expandX().left().pad(powerUpElementPadding);
        objectiveTextCell.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("hudimages/statbckg.jpeg")))));

        playerAvatarTable.add(playerAvatarCellTable).size(screenWidth/5, screenHeight/4).pad(2);

        healthPowerUpTable.add(mapTextCell).size(screenWidth/5, screenHeight/5).padTop(2).padBottom(2);
        healthPowerUpTable.add(statusCell).size(Gdx.graphics.getWidth()* 1.8f/ 5, screenHeight/5).padTop(2).padBottom(2);
        currentObjectiveTable.add(objectiveTextCell).size(Gdx.graphics.getWidth()/ 5, screenHeight/5).pad(2);


        stage.addActor(rootTable);

    }

    private void addUiStyles() {


        // A skin can be loaded via JSON or defined programmatically, either is fine. Using a skin is optional but strongly
        // recommended solely for the convenience of getting a texture, region, etc as a drawable, tinted drawable, etc.

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

        BitmapFont bfont=fontController.getFont("playtime.ttf");
        bfont.getData().setScale(.25f, .25f );
        skin.add("default",bfont);






        // Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("clear", Color.GOLD);
        textButtonStyle.down = skin.newDrawable("clear", Color.GOLDENROD);
        // textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
        //  textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);

        textButtonStyle.font = skin.getFont("default");


        imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        imageButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        //     imageButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
        //  imageButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);

        imageButtonStylem = new ImageButton.ImageButtonStyle();
        TextureRegion minimap = new TextureRegion(new Texture(Gdx.files.internal("MaleFresher.png")));
        imageButtonStylem.up = new TextureRegionDrawable(minimap);//skin.newDrawable("white", Color.DARK_GRAY);
//        imageButtonStylem.down = skin.newDrawable("white", Color.DARK_GRAY);
        // imageButtonStylem.checked = skin.newDrawable("white", Color.BLUE);
        // imageButtonStylem.over = skin.newDrawable("white", Color.LIGHT_GRAY);

        hudLabelStyle = new Label.LabelStyle(fontController.getFont("playtime.ttf"), Color.RED);

    }

    public void update(float dt) {
    	player = gameScreen.getPlayer();
        timeCount += dt;
        if (timeCount >= 1) {
            if (worldTimer > 0) {
                worldTimer--;
            } else {
                timeUp = true;
            }
//            timerTextLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
 }

    public static void addScore(int value) {
        score += value;
//        scoreLabel.setText(String.format("%06d", score));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public boolean isTimeUp() {
        return timeUp;
    }


    public static Label getScoreLabel() {
        return scoreLabel;
    }

    public static Integer getScore() {
        return score;
    }

    public void resumeGame(){

        if(isPause){
            isPause=false;
            pauseGroup.remove();
        }
    }

    public Stage getStage() {
        return stage;
    }

    public void updatePlayerAvatar(TextureRegion newAvatar){

        playerAvatarImageButton.getStyle().up = new TextureRegionDrawable(newAvatar);

    }

    public void updateCurrentObjective(String currentObjective){

        currentObjectiveName.setText(currentObjective);
    }

    public void updateExplored(int visited ){

        // TODO
    }
}