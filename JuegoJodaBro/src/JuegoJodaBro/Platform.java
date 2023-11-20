package JuegoJodaBro;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Platform {
    private int x, y, width, height;
    private final int fixedX;  // La posición X fija para la plataforma

    // Constructor
    public Platform(int fixedX, int y, int width, int height) {
        this.fixedX = fixedX;
        this.x = fixedX;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public int getAdjustedX(int backgroundX) {
        return x - backgroundX;
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

    public void draw(Graphics2D g2d, int backgroundX) {
        g2d.setColor(Color.BLUE);
        g2d.fillRect(getAdjustedX(backgroundX), y, width, height);
    }

    // Método para verificar la colisión con el personaje, teniendo en cuenta el fondo
    public boolean collidesWith(int characterX, int characterY, int characterWidth, int backgroundX) {
        Rectangle characterBounds = new Rectangle(characterX, characterY, characterWidth, characterWidth);
        Rectangle platformBounds = new Rectangle(getAdjustedX(backgroundX), y, width, height);
        return characterBounds.intersects(platformBounds);
    }
}
