
package com.mygdx.game;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.uiutils.FontController;

public class Inventory implements Screen {

	private final Main game;
	private final GameScreen parent;

	private FontController fontController;
	private Texture bckgImage;
	private OrthographicCamera camera;

	private ClickListener clickListener;

	ScrollPane scrollpane;
	Skin skin;
	Stage stage;
	Table container, table1, table2, table3;
	Texture texture1, texture2, texture3;
	private Table innerContainer;

	final Pixmap pixmap0;
	final Pixmap pixmap;
	final Pixmap pixmap2;

	/*
	 * Item needs to be added with unique id as items are saved using a Hashmap.
	 * 
	 */

	private HashMap<Integer, Item> itemList = new HashMap<Integer, Item>();

	private HashMap<Integer, Table> itemTableList = new HashMap<Integer, Table>();

	public Inventory(final Main game, final GameScreen parent) {
		this.game = game;
		this.parent = parent;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		fontController = new FontController();
		fontController.addFont("playtime.ttf", "playtime.ttf");
		bckgImage = new Texture((Gdx.files.internal(("hudimages/statbckg.jpeg"))));

		pixmap0 = new Pixmap(100, 100, Pixmap.Format.RGBA8888);
		pixmap0.setColor(Color.DARK_GRAY);
		pixmap0.fill();

		pixmap = new Pixmap(100, 100, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.ORANGE);
		pixmap.fill();

		pixmap2 = new Pixmap(100, 100, Pixmap.Format.RGBA8888);
		pixmap2.setColor(Color.CORAL);
		pixmap2.fill();

		// setup skin
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		// gets user input to back to GameScreen.
		clickListener = new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(parent);
				dispose();
			}
		};
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1); // sets up the clear color (background color) of the screen.
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // instructs openGL to actually clear the screen to the newly set
													// clear color.

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.batch.draw(bckgImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		game.batch.end();

		// stage.draw();
		// stage.act(delta);

		stage.act(Gdx.graphics.getDeltaTime());
		try {
			stage.draw();
		} catch (Exception e) {
			System.out.println(e);
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
			game.setScreen(parent);
			dispose();
		}

	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {

		// table that holds the scrollpane
		container = new Table();
		container.setPosition(0, Gdx.graphics.getHeight() * .25f);
		container.setWidth(Gdx.graphics.getWidth());
		container.setHeight(Gdx.graphics.getHeight() * .75f);
		container.pad(4);

		table1 = new Table(skin);
		table1.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pixmap0))));
		table1.add(new Label("Available Items", skin)).expandX(); // Y().fillY();

		table2 = new Table(skin);
		table2.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pixmap0))));
		table2.add(new Label("Back to Game", skin)).expandX(); // Y().fillY();

		table2.setTouchable(Touchable.enabled);
		table2.addListener(clickListener);

		// inner table that is used as a makeshift list.
		innerContainer = new Table().pad(8);

		// create the scroll pane
		scrollpane = new ScrollPane(innerContainer);

		// add the scroll pane to the container with header and footer
		container.add(table1).expandX().fill();
		container.row();
		container.add(scrollpane).fill().expand();
		container.row();
		container.add(table2).expandX().fill();

		// setup stage
		stage = new Stage();

		// adding listener for the item tables
		container.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

				Actor target = event.getTarget();
				String targetName = target.getName();
				if (targetName != null) {
					Item toBeRemoved = itemList.get(Integer.valueOf(targetName));
					removeItem(toBeRemoved);
				}
			}
		});
		// add container to the stage
		stage.addActor(container);

		// setup input processor (gets clicks and stuff)
		Gdx.input.setInputProcessor(stage);

		// setup a listener for the tables with out data

		addItemsToList();

	}

	private void addItemsToList() {

		for (Map.Entry<Integer, Item> entry : itemList.entrySet()) {
			Item item = entry.getValue();
			addItemToTheList(item);
		}
	}

	public Item getItem(Integer itemId) {
		return this.itemList.get(itemId);
	}

	public void addItem(Item item) {
		this.itemList.put(item.getId(), item);
		addItemToTheList(item);
	}

	public void removeItem(Item item) {

		this.itemList.remove(item);

		Table table = this.itemTableList.get(item.getId());
		if (this.itemTableList.containsValue(table)) {
			this.itemTableList.remove(table);
			table.remove();
		}

	}

	private void addItemToTheList(Item item) {

		// tables that hold the data you want to display
		Table table = new Table(skin);
		if (item.getId() % 2 == 0)
			table.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pixmap))));
		else
			table.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pixmap2))));

		table.add(new Image(item.getImage())).size(item.getWidth(), item.getHeight());
		table.add(new Label("", skin)).width(10f).expandY().fillY();// a spacer
		table.add(new Label("Added Item From Map", skin)).expandY().fillY();
		table.setTouchable(Touchable.enabled);
		table.setName(String.valueOf(item.getId()));
		// add the table to a list so that we can remove it when needed
		this.itemTableList.put(item.getId(), table);

		innerContainer.row();
		innerContainer.add(table).expandX().fillX();// .expand().fill();

	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

}
