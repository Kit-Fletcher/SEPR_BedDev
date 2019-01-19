package com.mygdx.game;

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
	
	public Mike() {
		combatIntro = new DialogueStore("It appears everyone has fallen to the virus. Try clicking the left mouse button to attack.", Gdx.audio.newSound(Gdx.files.internal("mikeCombnatIntro.mp3")));
		fresherIntro = new DialogueStore("", Gdx.audio.newSound(Gdx.files.internal("mikeFresherIntro.mp3")));
		generalIntro1 = new DialogueStore("", Gdx.audio.newSound(Gdx.files.internal("mikeGeneralIntroPart1.mp3")));
		generalIntro2 = new DialogueStore("", Gdx.audio.newSound(Gdx.files.internal("mikeGeneralIntroPart2.mp3")));
		generalIntro3 = new DialogueStore("", Gdx.audio.newSound(Gdx.files.internal("mikeGeneralIntroPart3.mp3")));
		backOnTrack = new DialogueStore("", Gdx.audio.newSound(Gdx.files.internal("mikeGetBackOnTrack.mp3")));
		greeting = new DialogueStore("", Gdx.audio.newSound(Gdx.files.internal("mikeGreeting.mp3")));
		itemFound = new DialogueStore("", Gdx.audio.newSound(Gdx.files.internal("mikeItemFound.mp3")));
		questIntro = new DialogueStore("", Gdx.audio.newSound(Gdx.files.internal("mikeQuestIntro")));
	}
	
	
	
}
