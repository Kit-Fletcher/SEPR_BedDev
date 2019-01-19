package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;

public class DialogueStore {
	private String text;
	private Sound sound;
	
	public DialogueStore(String text, Sound sound) {
		setText(text);
		setSound(sound);
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public Sound getSound() {
		return sound;
	}
	
	public void setSound(Sound sound) {
		this.sound = sound;
	}
	
	public void playSound() {
		sound.play();
	}
}
