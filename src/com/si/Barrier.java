package com.si;

import javax.swing.ImageIcon;

public class Barrier extends Common {

	private final String bgImg = "src/images/barrier.jpg";

	private int lifeCount = 50;
	
	private int w;
	private int h;

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}

	public Barrier() {
	}

	public Barrier(int x, int y) {
		init(x, y);
	}

	private void init(int x, int y) {
		ImageIcon imageIcon = new ImageIcon(bgImg);
		setImage(imageIcon.getImage());
		w = imageIcon.getImage().getWidth(null);
		h = imageIcon.getImage().getHeight(null);
		setX(x);
		setY(y);
	}

	public void lifeCountMinus() {
		lifeCount--;
	}

	public void checkDie() {
		if (lifeCount <= 0) {
			super.setDead(true);
			super.die();
		}
	}
}
