package com.si;

import java.awt.Image;

public class Common {

	private Image bgImage;
	protected int x;
	protected int y;
	protected int dx;
	protected int dy;
	protected boolean dead;
	private boolean isVisible = true;

	public void die() {
		isVisible = false;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setImage(Image image) {
		this.bgImage = image;
	}

	public Image getImage() {
		return bgImage;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public boolean isDead() {
		return this.dead;
	}
}