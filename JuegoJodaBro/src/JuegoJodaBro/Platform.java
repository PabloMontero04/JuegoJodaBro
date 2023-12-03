package JuegoJodaBro;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Platform {
    private int x, y, width, height;
    private final int originalX;
    private final int fixedX;  // La posición X fija para la plataforma
    private Image platformImage;  // Imagen de la plataforma

    // Constructor
    public Platform(int fixedX, int y, int width, int height, String imagePath) {
        this.fixedX = fixedX;
        this.x = fixedX;
        this.y = y;
        this.width = width;
        this.height = height;
        this.originalX = fixedX;

        try {
            // Cargar la imagen de la plataforma
            platformImage = new ImageIcon(imagePath).getImage();
            // Ajustar el tamaño de la imagen según las dimensiones de la plataforma
            platformImage = resizeImage(platformImage, width, height);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
   

    // Otros métodos de la clase Platform...

    public void draw(Graphics2D g2d, int backgroundX) {
        // Dibujar la imagen de la plataforma en lugar de un color sólido
        g2d.drawImage(platformImage, getAdjustedX(backgroundX), y, width, height, null);
    }

    // Otros métodos de la clase Platform...

    private Image resizeImage(Image originalImage, int width, int height) {
        return originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
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

    // Método para verificar la colisión con el personaje, teniendo en cuenta el fondo
    public boolean collidesWith(int characterX, int characterY, int characterWidth, int backgroundX) {
        Rectangle characterBounds = new Rectangle(characterX, characterY, characterWidth, characterWidth);
        Rectangle platformBounds = new Rectangle(getAdjustedX(backgroundX), y, width, height);
        return characterBounds.intersects(platformBounds);
    }
}
