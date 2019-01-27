package com.si;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GameBoard extends JPanel implements Runnable, Constants {
	private static final long serialVersionUID = 1L;
	private int score;
	private int lifeCount = FeaturesProperties.getNumberOfDefenderLives();
	private ArrayList<Enemy> enemies;
	private ArrayList<Barrier> barriers;
	private Player player;
	private Bullet bullet;
	private final int ENEMY_START_X = 150;
	private final int ENEMY_START_Y = 5;
	private int dire = -1;
	private int deathCount = 0;
	private boolean isOver = true;
	private final String eImg = "src/images/explosion.png";
	private String tipMsg = "Game Over";
	private Thread thread;
	private Dimension dimension;

	public GameBoard() {
		dimension = new Dimension(GAME_BOARD_WIDTH, GAME_BOARD_HEIGHT);
		addKeyListener(new MyKeyAdapter());
		setFocusable(true);
		setBackground(Color.black);
		init();
		setDoubleBuffered(true);
	}

	@Override
	public void addNotify() {
		super.addNotify();
		init();
	}

	public void init() {

		enemies = new ArrayList<>();
		for (int i = 0; i < FeaturesProperties.getRowsOfInvaders(); i++) {
			for (int j = 0; j < FeaturesProperties.getColumnsOfInvaders(); j++) {
				Enemy enemy = new Enemy(ENEMY_START_X + 39 * j, ENEMY_START_Y + 34 * i);
				enemies.add(enemy);
			}
		}
		player = new Player();
		bullet = new Bullet();
		barriers = new ArrayList<>();
		for (int i = 0; i < BARRIER_COUNT; i++) {
			barriers.add(new Barrier(100 + 100 * i, 400));
		}

		if (thread == null || !isOver) {
			thread = new Thread(this);
			thread.start();
		}
	}

	private void drawLifeCount(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("", Font.BOLD, 16));
		g.drawString("LIVES ", 5, START_Y + 55);
		for (int j = 0; j < lifeCount; j++) {
			g.drawImage(player.getImage(), 60 + 48 * j, START_Y + 30, this);
		}
	}

	private void drawScoring(Graphics g) {
		g.setColor(Color.GREEN);
		g.setFont(new Font("", Font.BOLD, 16));
		g.drawString("SCORE   " + score, 5, 18);
	}

	public void drawEnemies(Graphics g) {
		for (Enemy e : enemies) {
			if (e.isVisible()) {
				g.drawImage(e.getImage(), e.getX(), e.getY(), this);
			}
			if (e.isDead()) {
				e.die();
				if (e.isFlag()) {
					AudioUtils.boom();
					score += 200;
					e.setFlag(false);
				}
			}
		}
	}

	public void drawBarriers(Graphics g) {
		for (Barrier barrier : barriers) {
			if (barrier.isVisible()) {
				g.drawImage(barrier.getImage(), barrier.getX(), barrier.getY(), this);
			}
			if (barrier.isDead()) {
				barrier.die();
			}
		}
	}

	public void drawPlayer(Graphics g) {
		if (player.isVisible()) {
			g.drawImage(player.getImage(), player.getX(), player.getY(), this);
		}
		if (player.isDead() && lifeCount == 0) {
			ImageIcon imageIcon = new ImageIcon(eImg);
			player.setImage(imageIcon.getImage());
			player.die();
			isOver = false;
		}
	}

	public void drawBullet(Graphics g) {
		if (bullet.isVisible()) {
			g.drawImage(bullet.getImage(), bullet.getX(), bullet.getY(), this);
		}
	}

	public void drawBomb(Graphics g) {
		for (Enemy e : enemies) {
			Enemy.Bomb b = e.getBomb();
			if (!b.isDestroyed()) {
				g.drawImage(b.getImage(), b.getX(), b.getY(), this);
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.black);
		g.fillRect(0, 0, dimension.width, dimension.height);
		if (isOver) {
			drawEnemies(g);
			drawPlayer(g);
			drawBullet(g);
			drawBomb(g);
			drawBarriers(g);
			drawScoring(g);
			drawLifeCount(g);
		}
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public void gameOver() {
		Graphics g = this.getGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, GAME_BOARD_WIDTH, GAME_BOARD_HEIGHT);
		g.setColor(new Color(0, 32, 48));
		g.fillRect(50, GAME_BOARD_WIDTH / 2 - 30, GAME_BOARD_WIDTH - 100, 50);
		g.setColor(Color.white);
		g.drawRect(50, GAME_BOARD_WIDTH / 2 - 30, GAME_BOARD_WIDTH - 100, 50);
		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metr = this.getFontMetrics(small);
		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(tipMsg, (GAME_BOARD_WIDTH - metr.stringWidth(tipMsg)) / 2, GAME_BOARD_WIDTH / 2);
	}

	public void cycle() {
		if (deathCount == FeaturesProperties.getColumnsOfInvaders() * FeaturesProperties.getRowsOfInvaders()) {
			isOver = false;
			tipMsg = "Game won!";
		}

		// player
		player.move();
		// bullet
		if (bullet.isVisible()) {
			int shotX = bullet.getX();
			int shotY = bullet.getY();
			for (Enemy e : enemies) {
				int alienX = e.getX();
				int alienY = e.getY();
				if (e.isVisible() && bullet.isVisible()) {
					if (shotX >= (alienX) && shotX <= (alienX + ALIEN_WIDTH) && shotY >= (alienY) && shotY <= (alienY + ALIEN_HEIGHT)) {
						ImageIcon ii = new ImageIcon(eImg);
						e.setImage(ii.getImage());
						e.setDead(true);
						deathCount++;
						bullet.die();
					}
				}
			}

			int y = bullet.getY();
			y -= 4;
			if (y < 0) {
				bullet.die();
			} else {
				bullet.setY(y);
			}
		}

		for (Enemy e : enemies) {
			int x = e.getX();
			if (x >= GAME_BOARD_WIDTH - BORDER_RIGHT && dire != -1) {
				dire = -1;
				Iterator<Enemy> iterator = enemies.iterator();
				while (iterator.hasNext()) {

					Enemy a2 = (Enemy) iterator.next();
					a2.setY(a2.getY() + GO_DOWN_RATE);
				}
			}

			if (x <= BORDER_LEFT && dire != 1) {
				dire = 1;
				Iterator<Enemy> iterator2 = enemies.iterator();
				while (iterator2.hasNext()) {
					Enemy a = (Enemy) iterator2.next();
					a.setY(a.getY() + GO_DOWN_RATE);
				}
			}
		}

		Iterator<Enemy> iterator = enemies.iterator();
		while (iterator.hasNext()) {
			Enemy e = (Enemy) iterator.next();
			if (e.isVisible()) {
				int y = e.getY();
				if (y > GAME_BOARD_BOTTOM - ALIEN_HEIGHT) {
					isOver = false;
					tipMsg = "Invasion!";
				}
				e.move(dire);
			}
		}
		Random generator = new Random();
		for (Enemy e : enemies) {
			int shoot = generator.nextInt(500);
			Enemy.Bomb b = e.getBomb();
			if (shoot == BOMB_LIFE_COUNT && e.isVisible() && b.isDestroyed()) {
				b.setDestroyed(false);
				b.setX(e.getX());
				b.setY(e.getY());
			}
			int bombX = b.getX();
			int bombY = b.getY();
			int playerX = player.getX() + 16;
			int playerY = player.getY();
			if (player.isVisible() && !b.isDestroyed()) {
				if (bombX >= (playerX) && bombX <= (playerX + PLAYER_WIDTH) && bombY >= (playerY) && bombY <= (playerY + PLAYER_HEIGHT)) {
					player.setDead(true);
					AudioUtils.playerDeath();
					lifeCount--;
					b.setDestroyed(true);
				}
			}
			if (!b.isDestroyed()) {
				b.setY(b.getY() + 1);
				if (b.getY() >= GAME_BOARD_BOTTOM - BOMB_HEIGHT) {
					b.setDestroyed(true);
				}
			}

			for (final Barrier barrier : barriers) {
				if (barrier.isVisible() && !b.isDestroyed()) {
					if (bombX >= (barrier.getX()) && bombX <= (barrier.getX() + barrier.getW()) && bombY >= (barrier.getY()) && bombY <= (barrier.getY() + barrier.getH())) {
						barrier.lifeCountMinus();
						barrier.checkDie();
						b.setDestroyed(true);
					}
				}
			}
		}
	}

	@Override
	public void run() {
		long timeStamp, timeDiff, sleepTime;
		timeStamp = System.currentTimeMillis();
		while (isOver) {
			repaint();
			cycle();
			timeDiff = System.currentTimeMillis() - timeStamp;
			sleepTime = TIME_DELAY - timeDiff;
			if (sleepTime < 0) {
				sleepTime = 2;
			}
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
			}
			timeStamp = System.currentTimeMillis();
		}
		gameOver();
	}

	private class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			player.keyReleased(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			player.keyPressed(e);
			int x = player.getX() + 16;
			int y = player.getY();
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_SPACE) {
				if (isOver) {
					if (!bullet.isVisible()) {
						bullet = new Bullet(x, y);
						AudioUtils.shoot();
					}
				}
			}
		}
	}
}
