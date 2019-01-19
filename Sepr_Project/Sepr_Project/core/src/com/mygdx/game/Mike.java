package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Mike extends Sprite {
	
	private DialogueStore combatIntro;
	private DialogueStore fresherIntro;
	private DialogueStore generalIntro1;
	private DialogueStore generalIntro2;
	private DialogueStore generalIntro3;
	private DialogueStore backOnTrack;
	private DialogueStore greeting;
	private DialogueStore itemFound;
	private DialogueStore questIntro;
	
	private boolean firstTime = true;
	private boolean fresher = true;
	private boolean general1 = true;
	private boolean general2 = true;
	private boolean general3 = true;
	private boolean backOnTr = true;
	private boolean item = true;
	private boolean quest = true;
	private long startTime = System.currentTimeMillis();
	
	public Mike(final Sprite sprite) {
		super(sprite);
		initialize();
		
		
		combatIntro = new DialogueStore("It appears everyone has fallen to the virus. Try clicking the left mouse button to attack.", Gdx.audio.newSound(Gdx.files.internal("mikeCombatIntro.mp3")));
		fresherIntro = new DialogueStore("", Gdx.audio.newSound(Gdx.files.internal("mikeFresherIntro.mp3")));
		generalIntro1 = new DialogueStore("", Gdx.audio.newSound(Gdx.files.internal("mikeGeneralIntroPart1.mp3")));
		generalIntro2 = new DialogueStore("", Gdx.audio.newSound(Gdx.files.internal("mikeGeneralIntroPart2.mp3")));
		generalIntro3 = new DialogueStore("", Gdx.audio.newSound(Gdx.files.internal("mikeGeneralIntroPart3.mp3")));
		backOnTrack = new DialogueStore("", Gdx.audio.newSound(Gdx.files.internal("mikeGetBackOnTrack.mp3")));
		greeting = new DialogueStore("", Gdx.audio.newSound(Gdx.files.internal("mikeGreeting.mp3")));
		itemFound = new DialogueStore("", Gdx.audio.newSound(Gdx.files.internal("mikeItemFound.mp3")));
		questIntro = new DialogueStore("", Gdx.audio.newSound(Gdx.files.internal("mikeQuestIntro.mp3")));
		
		firstTime = true;
	}
	protected void initialize() {
		this.setSize(30, 75);
	}
	
	public void speak(Player chr) {

		if(firstTime) {
			greeting.playSound();
			firstTime = false;
		}
		if(fresher == true && chr.type == "Fresher" && System.currentTimeMillis()-startTime >3000) {
			fresherIntro.playSound();
			fresher = false;
		}else {
			//generalIntro1.playSound();
			//generalIntro2.playSound();
				
		}
			
		
		
	}
	
	
	
}
