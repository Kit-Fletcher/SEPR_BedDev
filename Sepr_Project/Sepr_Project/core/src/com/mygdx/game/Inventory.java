package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.uiutils.FontController;

public class Inventory implements Screen {

    private final MainScreen game;
    private FontController fontController;
    private Texture bckgImage;
    private OrthographicCamera camera;

    @Override
    public void show() {

    }
    public Inventory(final MainScreen game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        fontController = new FontController();
        fontController.addFont("playtime.ttf", "playtime.ttf");
        bckgImage = new Texture((Gdx.files.internal(("hudimages/statbckg.jpeg"))));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.235f, .245f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(bckgImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }

    }

    @Override
    public void resize(int width, int height) {

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

    }
}
