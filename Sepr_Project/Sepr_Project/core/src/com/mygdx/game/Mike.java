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
	private boolean combat = true;
	private boolean item = true;
	private boolean quest = true;
	private long startTime = System.currentTimeMillis();
	private long offset = 0;
	
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
	
	public void speak(Player chr, String place) {
		long timeDif = System.currentTimeMillis() - startTime;
		if(firstTime) {
			greeting.playSound();
			firstTime = false;
		}
		if(fresher  && chr.type == "Fresher" && timeDif >3000) {
			fresherIntro.playSound();
			offset = 7000;
			fresher = false;
		}else if(general1 && timeDif > 3000 + offset){
			generalIntro1.playSound();
			general1 = false;
			//generalIntro2.playSound();
		
		}else if(general2 && timeDif > 16000 + offset) {
			generalIntro2.playSound();
			general2 = false;
		}else if(general3 && timeDif > 26000 + offset) {
			generalIntro3.playSound();
			general3 = false;
		}else if(quest && timeDif > 36000 + offset) {
			questIntro.playSound();
			quest = false;
		}else if(combat && timeDif > 48000 + offset && place != "CompSci") {
			combatIntro.playSound();
			combat = false;
		}
			
		
		
	}
	
	
	
}
