package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;

public class DialogueStore {
	private String text;
	private Sound audio;
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public Sound getAudio() {
		return audio;
	}
	
	public void setAudio(Sound audio) {
		this.audio = audio;
	}
	
}
