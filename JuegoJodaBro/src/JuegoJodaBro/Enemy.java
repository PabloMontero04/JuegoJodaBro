package JuegoJodaBro;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Enemy {
    private int x;  // Coordenada X del enemigo
    private int y;  // Coordenada Y del enemigo
    private int width;  // Ancho del enemigo
    private int height;  // Altura del enemigo
    private Image image;  // Imagen del enemigo
    private int initialX; // Nueva variable para almacenar la posición inicial
    private boolean movingRight; // Nueva variable para indicar la dirección

    // Constructor
    public Enemy(int x, int y, int width, int height, String imagePath) {
        this.x = x;
        this.y = y;
        this.initialX = x; // Almacena la posición inicial
        this.width = width;
        this.height = height;
        this.image = new ImageIcon(imagePath).getImage();
        this.movingRight = true; // Inicia moviéndose hacia la derecha
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
    
    // Método para mover el enemigo
    public void move() {
        int speed = 2; // Puedes ajustar la velocidad según sea necesario

        if (movingRight) {
            x += speed;
            if (x - initialX >= 100) {
                movingRight = false; // Cambia de dirección al llegar a 100 unidades a la derecha
            }
        } else {
            x -= speed;
            if (x <= initialX) {
                movingRight = true; // Cambia de dirección al regresar a la posición inicial
            }
        }
    }
}

