package JuegoJodaBro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Main extends JFrame {
    private int characterX = 50;  // Coordenada X del personaje
    private int characterY = 300;  // Coordenada Y del personaje
    private int characterWidth = 50;  // Ancho del personaje
    private int backgroundX = 0;  // Coordenada X del fondo
    private Image characterImage;  // Imagen del personaje
    private Image backgroundImage;  // Imagen de fondo
    private boolean facingRight = true;  // Indica si el personaje está mirando a la derecha
    private boolean jumping = false;  // Indica si el personaje está saltando
    private int jumpHeight = 550;  // Altura del salto
    private int jumpCount = 0;  // Contador para controlar la altura del salto
    private int jumpSpeed = 4;  // Velocidad del salto
    private int moveSpeed = 4;  // Velocidad de movimiento horizontal
    private List<DeadlyObject> deadlyObjects;
    private List<Platform> platforms;

    Toolkit mipantalla = Toolkit.getDefaultToolkit();
    Dimension tamanoPantalla = mipantalla.getScreenSize();

    int alturaPantalla = tamanoPantalla.height;
    int anchoPantalla = tamanoPantalla.width;

    public Main() {
        // Cargar la imagen de fondo
        try {
            backgroundImage = new ImageIcon("src/img/fondofinal.jpg").getImage();
            // Configurar el tamaño del JFrame según el tamaño de la imagen de fondo
            backgroundImage = resizeBackground(backgroundImage, 3200, 600);
            setSize(backgroundImage.getWidth(null), backgroundImage.getHeight(null));
        } catch (Exception e) {
            e.printStackTrace();
            // Si hay un problema al cargar la imagen de fondo, establecer un tamaño predeterminado
            setSize(800, 600);
        }
        setPreferredSize(new Dimension(800, 600));
        setLocation((anchoPantalla - 800) / 2, (alturaPantalla - 600) / 2);

        // Cargar la imagen del personaje
        try {
            characterImage = new ImageIcon("src/img/YodaBuenoAndando.gif").getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Crear la lista de objetos mortales
        deadlyObjects = new ArrayList<>();
        deadlyObjects.add(new DeadlyObject(944, 475, "src/img/gifplanta.gif"));
        deadlyObjects.add(new DeadlyObject(444, 475, "src/img/gifplanta.gif"));

        // Crear la lista de plataformas
        platforms = new ArrayList<>();
        platforms.add(new Platform(111, 444, 111, 22, "src/img/suelo.png"));
        platforms.add(new Platform(333, 388, 83, 22, "src/img/suelo.png"));

        setTitle("Mi Juego");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLocation(anchoPantalla / 4, alturaPantalla / 4);

        // Agregar un KeyListener para mover y saltar el personaje
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // Mueve el personaje con las teclas de flecha
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    characterX -= moveSpeed;
                    facingRight = false;
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    characterX += moveSpeed;
                    facingRight = true;
                }

                // Salto cuando se presiona la tecla de espacio o la tecla de flecha hacia arriba
                if ((e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP) && !jumping) {
                    jumping = true;
                    jumpCount = 0;  // Reiniciar el contador al comenzar un nuevo salto
                }

                // Vuelve a dibujar la pantalla
                repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        // Asegurarse de que el JFrame pueda recibir eventos de teclado
        setFocusable(true);

        // Configurar un temporizador para manejar el salto, la gravedad y los objetos mortales
        Timer timer = new Timer(10, (e) -> {
            if (jumping) {
                // Aplicar una aceleración inicial al salto
                if (jumpCount < jumpHeight / 2) {
                    characterY -= jumpSpeed;  // Ajusta la velocidad inicial del salto
                } else {
                    characterY += jumpSpeed;  // Aplicar desaceleración al descender
                }

                // Avanzar en el eje x mientras salta
                if (facingRight) {
                    characterX += moveSpeed;
                } else {
                    characterX -= moveSpeed;
                }

                jumpCount += jumpSpeed;

                // Si se alcanza la altura máxima del salto, detener el salto
                if (jumpCount >= jumpHeight) {
                    jumping = false;
                }
            } else {
                // Si no está saltando, aplicar la gravedad
                if (characterY < 485) {
                    characterY += 3;
                }
            }

            // Ajusta la posición del fondo en función de la posición del personaje
            if (facingRight && characterX > getWidth() / 2) {
                backgroundX += moveSpeed;  // Avanza el fondo
                characterX = getWidth() / 2;  // Centra el personaje
            }

            // Verificar la colisión con los objetos mortales
            for (DeadlyObject deadlyObject : deadlyObjects) {
                if (collisionWithDeadlyObject(deadlyObject)) {
                    // Acciones cuando hay una colisión (por ejemplo, terminar el juego)
                    // Puedes mostrar un mensaje de "Game Over" y cerrar la aplicación.
                    JOptionPane.showMessageDialog(this, "Game Over!");
                    System.exit(0);  // Cierra la aplicación
                }
            }
            if (backgroundX >= 2293) {
                // El jugador ha perdido
                JOptionPane.showMessageDialog(this, "¡Has ganado!");
                System.exit(0);  // Cierra la aplicación
            }

            // Vuelve a dibujar la pantalla
            repaint();
        });
        timer.start();
    }

    private Image resizeBackground(Image originalBackground, int width, int height) {
        return originalBackground.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    // Método para verificar la colisión con un objeto mortal específico
    private boolean collisionWithDeadlyObject(DeadlyObject deadlyObject) {
        Rectangle characterBounds = new Rectangle(characterX, characterY, characterWidth, characterWidth);
        Rectangle deadlyObjectBounds = new Rectangle(deadlyObject.getX() - backgroundX, deadlyObject.getY(),
                deadlyObject.getWidth(), deadlyObject.getHeight());

        boolean collision = characterBounds.intersects(deadlyObjectBounds);

        // Verifica si el objeto mortal ha sido saltado y si el jugador está encima de la planta
        if (collision && !deadlyObject.isJumpedOver() && characterY + characterWidth <= deadlyObject.getY()) {
            deadlyObject.setJumpedOver(true);
            return false;  // Evita la colisión después de saltar
        }

        return collision;
    }

    private Image resizeImage(Image originalImage, int width, int height) {
        return originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    private boolean collisionWithPlatform(Platform platform) {
        return platform.collidesWith(characterX, characterY, characterWidth, backgroundX);
    }

    @Override
    public void paint(Graphics g) {
        // Crear un buffer de imagen
        BufferedImage buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = buffer.createGraphics();

        // Dibujar el fondo en el buffer de imagen
        g2d.drawImage(backgroundImage, -backgroundX, 0, this);

        // Dibujar las plantas estáticas en el buffer de imagen
        for (DeadlyObject deadlyObject : deadlyObjects) {
            g2d.drawImage(deadlyObject.getImage(), deadlyObject.getX() - backgroundX, deadlyObject.getY(),
                    deadlyObject.getWidth(), deadlyObject.getHeight(), this);
        }

        // Dibujar las plataformas en el buffer de imagen
        for (Platform platform : platforms) {
            // Verificar la colisión con las plataformas
            if (collisionWithPlatform(platform)) {
                jumping = false;  // Si colisiona con una plataforma, detener el salto
                jumpCount = 0;    // Reiniciar el contador de salto
                characterY = platform.getY() - characterWidth;  // Ajustar la posición del personaje al nivel de la plataforma
            }

            // Dibujar la plataforma usando las coordenadas y dimensiones almacenadas
            platform.draw(g2d, backgroundX);
        }

        // Dibujar el personaje en el buffer de imagen
        if (facingRight) {
            g2d.drawImage(characterImage, characterX, characterY, characterWidth, characterWidth, this);
        } else {
            // Si el personaje está mirando a la izquierda, invertir la imagen
            g2d.drawImage(characterImage, characterX + characterWidth, characterY, -characterWidth, characterWidth, this);
        }

        // Dibujar el buffer de imagen en el JFrame
        g.drawImage(buffer, 0, 0, this);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main game = new Main();
            game.pack();
            game.setVisible(true);
        });
    }
}
