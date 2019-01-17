package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/*
MainScreen is the entry point, this extends Game
This allows the application to easily have multiple screens.
 */

public class MainScreen extends Game {
	

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

	  SpriteBatch batch;

	    public void create() {
	        batch = new SpriteBatch();
	        //this.setScreen(new GameScreen(this));
	    }
	  
	  //SEPRGame game is a place holder for a
	  //game object for the main game
	  public MainScreen () {
	      background = new Texture("backgroundSEPR.png");
	      startButtonActive = new Texture("startActivated.png");
	      startButtonInActive = new Texture("startUnactivated.png");
	      loadButtonActive = new Texture("loadActivated.png");
	      loadButtonInActive = new Texture("loadUnactivated.png");
	      quitButtonActive = new Texture("quitActivated.png");
	      quitButtonInActive = new Texture("quitUnactivated.png");

	  }

	 
	  public void render(float delta) {

	      batch.begin();

	    
	    batch.draw(background, 0, 0, 500, 500);
	    
	    int x = Gdx.input.getX();
	    int y = 500 - Gdx.input.getY();
	    
	    //decides if the mouse is within button region
	    //if is within region, changes button to active state
	    if (x < (startX + btnWidth) && x > startX &&
	            y < (startY + btnHeight) && y > startY)
	    {
	      batch.draw(startButtonActive, startX, startY, btnWidth, btnHeight);
	    } else {
	      batch.draw(startButtonInActive, startX, startY, btnWidth, btnHeight);
	    }


	    if (x < (loadX + btnWidth) && x > loadX &&
	            y < (loadY + btnHeight) && y > loadY)
	    {
	      batch.draw(loadButtonActive, loadX, loadY, btnWidth, btnHeight);
	    } else {
	      batch.draw(loadButtonInActive, loadX, loadY, btnWidth, btnHeight);
	    }


	    if (x < (quitX + btnWidth) && x > quitX &&
	            y < (quitY + btnHeight) && y > quitY)
	    {
	      batch.draw(quitButtonActive, quitX, quitY, btnWidth, btnHeight);
	    } else {
	      batch.draw(quitButtonInActive, quitX, quitY, btnWidth, btnHeight);
	    }

	    batch.end();

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
