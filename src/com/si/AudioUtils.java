package com.si;

public class AudioUtils {

	public static void shoot() {
		Thread boomAudio = new Thread(new Audio("audio/shoot.mp3"));
		boomAudio.start();
	}

	public static void boom() {
		Thread audio = new Thread(new Audio("audio/bomb.wav"));
		audio.start();
	}
	
	public static void playerDeath(){
		Thread aduio = new Thread(new Audio("audio/player_death.mp3"));
		aduio.start();
	}

	public static void start() {
		Thread audio = new Thread(new Audio("audio/start.wav"));
		audio.start();
	}

	public static void main(String[] args) {
		// shoot();
		// boom();
		// start();
		playerDeath();
	}
}
