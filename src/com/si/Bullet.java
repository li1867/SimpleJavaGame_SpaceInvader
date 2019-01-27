package com.si;
import javax.swing.ImageIcon;

public class Bullet extends Common {

    private final String shootImg = "src/images/bullet.png";
    private final int H_X = 6;
    private final int V_Y = 1;

    public Bullet() {
    }
    public Bullet(int x, int y) {
        initShoot(x, y);
    }

    private void initShoot(int x, int y) {
        ImageIcon imageIcon = new ImageIcon(shootImg);
        setImage(imageIcon.getImage());
        setX(x + H_X);
        setY(y - V_Y);
    }
}