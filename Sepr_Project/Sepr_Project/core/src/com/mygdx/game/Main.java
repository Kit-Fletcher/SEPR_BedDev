package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/*
MainScreen is the entry point, this extends Game
This allows the application to easily have multiple screens.
 */

public class Main extends Game {
	
	  SpriteBatch batch;

	    public void create() {
	        batch = new SpriteBatch();
	        setScreen(new MainScreen(this));
	    }

    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

    }

    public void dispose() {
        batch.dispose();
    }
}
