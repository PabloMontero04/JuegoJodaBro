package JuegoJodaBro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class Main extends JFrame {
    private int characterX = 50;  // Coordenada X del personaje
    private int characterY = 900;  // Coordenada Y del personaje
    private int backgroundX = 0;  // Coordenada X del fondo
    private Image characterImage;  // Imagen del personaje
    private Image backgroundImage;  // Imagen de fondo
    private boolean isJumping = false;  // Bandera para indicar si el personaje está saltando
    private int jumpHeight = 100;  // Altura del salto
    private int jumpCount = 0;  // Contador para controlar la altura del salto
    private boolean isFacingLeft = false;  // Bandera para indicar si el personaje está mirando hacia la izquierda

    private BufferedImage bufferImage;
    private Graphics bufferGraphics;

    private int characterSpeed = 2;  // Velocidad de movimiento del personaje

    public Main() {
        // Cargar la imagen del personaje
        try {
            characterImage = new ImageIcon("src/img/YodaBuenoAndando.gif").getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Cargar la imagen de fondo
        try {
            backgroundImage = new ImageIcon("src/img/fondofinal.jpg").getImage();
            // Configurar el tamaño del JFrame según el tamaño de la imagen de fondo
            setSize(backgroundImage.getWidth(null), backgroundImage.getHeight(null));
        } catch (Exception e) {
            e.printStackTrace();
            // Si hay un problema al cargar la imagen de fondo, establecer un tamaño predeterminado
            setSize(800, 600);
        }

        // Crear el búfer de imagen
        bufferImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        bufferGraphics = bufferImage.getGraphics();

        setTitle("Mi Juego");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        // Agregar un KeyListener para mover y saltar el personaje
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                // Mueve el personaje con las teclas de flecha
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    characterX -= characterSpeed;
                    isFacingLeft = true;
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    characterX += characterSpeed;
                    isFacingLeft = false;
                }

                // Salta con la tecla de espacio si el personaje no está actualmente saltando
                if (e.getKeyCode() == KeyEvent.VK_SPACE && !isJumping) {
                    isJumping = true;
                }

                // Ajusta la posición del fondo en función de la posición del personaje
                backgroundX = characterX - getWidth() / 4;

                // Vuelve a dibujar la pantalla
                repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        // Asegurarse de que el JFrame pueda recibir eventos de teclado
        setFocusable(true);

        // Configurar un temporizador para manejar el salto
        Timer timer = new Timer(6, e -> {
            if (isJumping) {
                // Realizar el salto
                jumpCount++;
                characterY -= 1.5;
                if (jumpCount >= jumpHeight) {
                    isJumping = false;
                    jumpCount = 0;
                }
            } else if (characterY < 900) {
                // Descender después del salto
                characterY += 3;
            }

            repaint();
        });
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        // Dibujar en el búfer de imagen
        bufferGraphics.drawImage(backgroundImage, -backgroundX, 0, this);

        // Determinar la dirección del personaje y dibujar la imagen correspondiente
        if (isFacingLeft) {
            bufferGraphics.drawImage(characterImage, characterX + 50, characterY, -50, 50, this);
        } else {
            bufferGraphics.drawImage(characterImage, characterX, characterY, 50, 50, this);
        }

        // Dibujar el búfer en la pantalla
        g.drawImage(bufferImage, 0, 0, this);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main game = new Main();
            game.setVisible(true);
        });
    }
}
