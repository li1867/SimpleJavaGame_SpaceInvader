package com.si;

import javax.swing.JFrame;

public class SpaceInvaders extends JFrame implements Constants {

	private static final long serialVersionUID = 1L;

	public SpaceInvaders() {
		this.setTitle(GAME_NAME);
		this.setSize(GAME_BOARD_WIDTH, GAME_BOARD_HEIGHT);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.add(new GameBoard());
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new SpaceInvaders();
	}
}
