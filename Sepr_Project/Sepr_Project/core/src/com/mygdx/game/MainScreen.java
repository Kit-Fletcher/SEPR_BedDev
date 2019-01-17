package com.mygdx.game;

import java.io.Console;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MainScreen implements Screen {
	
	  SpriteBatch batch;
	
	  Main game;

	  Texture background;

	  Texture startButtonActive;
	  Texture startButtonInActive;
	  Texture loadButtonActive;
	  Texture loadButtonInActive;
	  Texture quitButtonActive;
	  Texture quitButtonInActive;
	  
	  //standard values assuming a 21:9 ratio of
	  //the button sizes
	  private static final int btnWidth = 210;
	  private static final int btnHeight = 90;

	  private static final int loadX = 100;
	  private static final int loadY = 100;
	  private static final int startX = 100;
	  private static final int startY = 200;
	  private static final int quitX = 100;
	  private static final int quitY = 300;

	
	  public MainScreen (Main game) {
		  this.game = game;
	      background = new Texture("backgroundSEPR.png");
	      startButtonActive = new Texture("startActivated.png");
	      startButtonInActive = new Texture("startUnactivated.png");
	      loadButtonActive = new Texture("loadActivated.png");
	      loadButtonInActive = new Texture("loadUnactivated.png");
	      quitButtonActive = new Texture("quitActivated.png");
	      quitButtonInActive = new Texture("quitUnactivated.png");
	  }

	
	
	
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {

		int x = Gdx.input.getX();
	    int y = 500 - Gdx.input.getY();
		
	    if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
	    	if (x < (startX + btnWidth) && x > startX &&
		            y < (startY + btnHeight) && y > startY)
		    {
	    		game.setScreen(new GameScreen(game));	
		    }
    	}
	    
	    game.batch.begin();
	    
	    game.batch.draw(background, 0, 0, 500, 500);
	    
	    //decides if the mouse is within button region
	    //if is within region, changes button to active state
	    if (x < (startX + btnWidth) && x > startX &&
	            y < (startY + btnHeight) && y > startY)
	    {
	    	game.batch.draw(startButtonActive, startX, startY, btnWidth, btnHeight);
	    	
	    	
	    } else {
	    	game.batch.draw(startButtonInActive, startX, startY, btnWidth, btnHeight);
	    }
	    

	    if (x < (loadX + btnWidth) && x > loadX &&
	            y < (loadY + btnHeight) && y > loadY)
	    {
	    	game.batch.draw(loadButtonActive, loadX, loadY, btnWidth, btnHeight);
	    } else {
	    	game.batch.draw(loadButtonInActive, loadX, loadY, btnWidth, btnHeight);
	    }


	    if (x < (quitX + btnWidth) && x > quitX &&
	            y < (quitY + btnHeight) && y > quitY)
	    {
	    	game.batch.draw(quitButtonActive, quitX, quitY, btnWidth, btnHeight);
	    } else {
	    	game.batch.draw(quitButtonInActive, quitX, quitY, btnWidth, btnHeight);
	    }

	    
	    game.batch.end();
	    
	    
	  }

	
	
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}


	
	
}
