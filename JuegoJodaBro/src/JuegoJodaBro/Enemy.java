package JuegoJodaBro;
import java.awt.*;

import javax.swing.ImageIcon;

public class Enemy {
	   private int x;
	    private int y;
	    private int width;
	    private int height;
	    private Image image;
	    private boolean jumpedOver;

	    public Enemy(int x, int y, String imagePath) {
	        this.x = x;
	        this.y = y;
	        this.width = 150;  // Ajusta el ancho según sea necesario
	        this.height = 150; // Ajusta la altura según sea necesario
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
	    public void draw(Graphics g) {
	        // Dibuja el enemigo en el contexto gráfico proporcionado (g)
	        g.drawImage(image, x, y, width, height, null);
	    }
	    public void update() {
	        // Lógica de actualización del enemigo

	        // Mover hacia la derecha entre las coordenadas 1500 y 2000
	        if (x < 2000) {
	            x += 2;  // Ajusta la velocidad de movimiento según sea necesario
	        } else {
	            // Cuando llega a 2000, reiniciar en 1500
	            x = 1500;
	        }
	    }

    public boolean collidesWithPlayer(int playerX, int playerY, int playerWidth, int playerHeight) {
        Rectangle enemyBounds = new Rectangle(x, y, width, height);
        Rectangle playerBounds = new Rectangle(playerX, playerY, playerWidth, playerHeight);
        return enemyBounds.intersects(playerBounds);
    }


}