package com.si;

import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Player extends Common implements Constants {
	private int w;
	private int h;
	private final String bgImg = "src/images/player1.png";

	public Player() {
		ImageIcon imageIcon = new ImageIcon(bgImg);
		w = imageIcon.getImage().getWidth(null);
		h = imageIcon.getImage().getHeight(null);
		setImage(imageIcon.getImage());
		setX(START_X);
		setY(START_Y);
	}

	public Player(int x, int y) {
		ImageIcon imageIcon = new ImageIcon(bgImg);
		w = imageIcon.getImage().getWidth(null);
		h = imageIcon.getImage().getHeight(null);
		setImage(imageIcon.getImage());
		setX(x);
		setY(y);
	}

	public void move() {
		x += dx;
		y += dy;

		if (x <= 2) {
			x = 2;
		}

		if (x >= GAME_BOARD_WIDTH - 2 * w) {
			x = GAME_BOARD_WIDTH - 2 * w;
		}

		if (y <= 2) {
			y = 2;
		}

		if (y >= GAME_BOARD_HEIGHT - 2 * h) {
			y = GAME_BOARD_HEIGHT - 2 * h;
		}
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_LEFT) {
			dx = -2;
		}

		if (key == KeyEvent.VK_RIGHT) {
			dx = 2;
		}

	//	if (key == KeyEvent.VK_UP) {
	//		dy = -2;
	//	}

	//	if (key == KeyEvent.VK_DOWN) {
	//		dy = 2;
	//	}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_LEFT) {
			dx = 0;
		}

		if (key == KeyEvent.VK_RIGHT) {
			dx = 0;
		}

	//	if (key == KeyEvent.VK_UP) {
	//		dy = 0;
	//	}

	//	if (key == KeyEvent.VK_DOWN) {
	//		dy = 0;
	//	}

	}
}
