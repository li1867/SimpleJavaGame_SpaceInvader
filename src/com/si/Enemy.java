package com.si;
import javax.swing.ImageIcon;

public class Enemy extends Common {

    private Bomb bomb;
    private final String bgImg = "src/images/enemy.jpg";
    private boolean flag = true;

    public Enemy(int x, int y) {
        init(x, y);
    }

    private void init(int x, int y) {
        this.x = x;
        this.y = y;
        bomb = new Bomb(x, y);
        ImageIcon ii = new ImageIcon(bgImg);
        setImage(ii.getImage());
    }

    public void move(int direction) {
        this.x += direction;
    }

    public Bomb getBomb() {
        return bomb;
    }

    
    public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}


	public class Bomb extends Common {
        private final String bombImg = "src/images/bomb.jpg";
        private boolean destroyed;

        public Bomb(int x, int y) {
            init(x, y);
        }

        private void init(int x, int y) {
            setDestroyed(true);
            this.x = x;
            this.y = y;
            ImageIcon ii = new ImageIcon(bombImg);
            setImage(ii.getImage());
        }

        public void setDestroyed(boolean destroyed) {
            this.destroyed = destroyed;
        }

        public boolean isDestroyed() {
            return destroyed;
        }
    }
}
