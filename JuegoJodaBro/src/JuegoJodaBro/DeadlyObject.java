package JuegoJodaBro;

import javax.swing.*;
import java.awt.Image;

public class DeadlyObject {
    private int x;
    private int y;
    private int width;
    private int height;
    private Image image;
    private boolean jumpedOver;

    public DeadlyObject(int x, int y, String imagePath) {
        this.x = x;
        this.y = y;
        this.width = 50;  // Ajusta el ancho según sea necesario
        this.height = 50; // Ajusta la altura según sea necesario
        this.jumpedOver = false;

        // Cargar la imagen del objeto mortal
        try {
            image = new ImageIcon(imagePath).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        return image;
    }

    public boolean isJumpedOver() {
        return jumpedOver;
    }

    public void setJumpedOver(boolean jumpedOver) {
        this.jumpedOver = jumpedOver;
    }
}
