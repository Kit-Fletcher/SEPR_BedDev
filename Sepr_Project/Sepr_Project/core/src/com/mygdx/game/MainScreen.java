package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainScreen implements Screen {
	
	  SpriteBatch batch;
	
	  Main game;

	  Texture background;

	  Texture startFresherButtonActive;
	  Texture startFresherButtonInActive;
	  Texture startSesherButtonActive;
	  Texture startSesherButtonInActive;
	  Texture loadButtonActive;
	  Texture loadButtonInActive;
	  Texture quitButtonActive;
	  Texture quitButtonInActive;
	  Texture fresherStats;
	  Texture sesherStats;
	  
	  //standard values assuming a 21:9 ratio of
	  //the button sizes
	  private static final int btnWidth = 210;
	  private static final int btnHeight = 90;
	  private static final int offset = (Gdx.graphics.getHeight()/3 - btnHeight)/2;
	  private static final int loadX = offset ;
	  private static final int loadY = (Gdx.graphics.getHeight()/3 - btnHeight) - offset;
	  private static final int startFX = offset ;
	  private static final int startFY = (Gdx.graphics.getHeight() - btnHeight-offset) ;
	  private static final int startSX = Gdx.graphics.getWidth() -btnWidth - offset; ;
	  private static final int startSY = (Gdx.graphics.getHeight() - btnHeight-offset) ;
	  private static final int quitX = Gdx.graphics.getWidth() -btnWidth - offset;
	  private static final int quitY = (Gdx.graphics.getHeight()/3 - btnHeight) -offset;
	  private static final int fresherX = offset;
	  private static final int fresherY = (Gdx.graphics.getHeight()*2/3 - btnHeight) ;
	  private static final int sesherX = Gdx.graphics.getWidth() -btnWidth - offset ;
	  private static final int sesherY = (Gdx.graphics.getHeight()*2/3 - btnHeight) ;
	  
	
	  public MainScreen (Main game) {
		  this.game = game;
	      background = new Texture("backgroundSEPR.png");
	      startFresherButtonActive = new Texture("selectFresherActivated.png");
	      startFresherButtonInActive = new Texture("selectFresherUnactivated.png");
	      startSesherButtonActive = new Texture("selectSesherActivated.png");
	      startSesherButtonInActive = new Texture("selectSesherUnactivated.png");
	      loadButtonActive = new Texture("loadActivated.png");
	      loadButtonInActive = new Texture("loadUnactivated.png");
	      quitButtonActive = new Texture("quitActivated.png");
	      quitButtonInActive = new Texture("quitUnactivated.png");
	      fresherStats = new Texture("fresherStats.png");
	      sesherStats = new Texture("sesherStats.png");
	  }
	
	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {

		int x = Gdx.input.getX();
	    int y = Gdx.graphics.getHeight() - Gdx.input.getY();
		
	    if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
	    	if (x < (startFX + btnWidth) && x > startFX &&
		            y < (startFY + btnHeight) && y > startFY)
		    {
	    			btnStartFresher();
		    }
	    	if (x < (startSX + btnWidth) && x > startSX &&
		            y < (startSY + btnHeight) && y > startSY)
		    {
	    			btnStartSesher();
		    }
	    	if (x < (quitX + btnWidth) && x > quitX &&
		            y < (quitY + btnHeight) && y > quitY)
		    {
	    			btnQuit();
		    }
	    	if (x < (loadX + btnWidth) && x > loadX &&
		            y < (loadY + btnHeight) && y > loadY)
		    {
	    			btnLoad();
		    }
    	}
	    
	    game.batch.begin();
	    
	    game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	    
	    //decides if the mouse is within button region
	    //if is within region, changes button to active state
	    if (x < (startFX + btnWidth) && x > startFX &&
	            y < (startFY + btnHeight) && y > startFY)
	    {
	    	game.batch.draw(startFresherButtonActive, startFX, startFY, btnWidth, btnHeight);
	    	
	    	
	    } else {
	    	game.batch.draw(startFresherButtonInActive, startFX, startFY, btnWidth, btnHeight);
	    }
	    
	    if (x < (startSX + btnWidth) && x > startSX &&
	            y < (startSY + btnHeight) && y > startSY)
	    {
	    	game.batch.draw(startSesherButtonActive, startSX, startSY, btnWidth, btnHeight);
	    	
	    	
	    } else {
	    	game.batch.draw(startSesherButtonInActive, startSX, startSY, btnWidth, btnHeight);
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

	    game.batch.draw(fresherStats, fresherX, fresherY, btnWidth, btnHeight);
	    
	    game.batch.draw(sesherStats, sesherX, sesherY, btnWidth, btnHeight);
	    
	    game.batch.end();
	    
	    
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

	public void btnStartFresher() {
		game.setScreen(new GameScreen(game, "Fresher"));
	}
	
	public void btnStartSesher() {
		game.setScreen(new GameScreen(game, "Sesher"));
	}
	
	public void btnLoad() {
		
	}
	
	public void btnQuit() {
		game.dispose();
	}
	
}
