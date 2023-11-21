package JuegoJodaBro;
import java.awt.*;

import javax.swing.ImageIcon;

public class Coin {
    private int x;
    private int y;
    private int width;
    private int height;
    private Image coinImage;

    public Coin(int x, int y, int width, int height, String imagePath) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.coinImage = new ImageIcon(imagePath).getImage();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Image getImage() {
        return coinImage;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}