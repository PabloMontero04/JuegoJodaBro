package JuegoJodaBro;

import javax.swing.*;
import java.util.Iterator;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class main extends JFrame {
    private int characterX = 50;  // Coordenada X del personaje
    private int characterY = 300;  // Coordenada Y del personaje
    private int characterWidth = 50;  // Ancho del personaje
    private int backgroundX = 0;  // Coordenada X del fondo
    private Image characterImage;  // Imagen del personaje
    private Image backgroundImage;  // Imagen de fondo
    private boolean facingRight = true;  // Indica si el personaje está mirando a la derecha
    private boolean jumping = false;  // Indica si el personaje está saltando
    private int jumpHeight = 350;  // Altura del salto
    private int jumpCount = 0;  // Contador para controlar la altura del salto
    private int jumpSpeed = 4;  // Velocidad del salto
    private int moveSpeed = 4;  // Velocidad de movimiento horizontal
    private List<DeadlyObject> deadlyObjects;
    private List<Platform> platforms;
    private int lives = 3;  // Contador de vidas
    private Image heartImage;  // Imagen del corazón
    private List<Coin> coins;
    private int contadorMonedas = 0;
    private JLabel labelContadorMonedas;
    private JLabel labelMensaje;
    private Font customFont;


    Toolkit mipantalla = Toolkit.getDefaultToolkit();
    Dimension tamanoPantalla = mipantalla.getScreenSize();

    int alturaPantalla = tamanoPantalla.height;
    int anchoPantalla = tamanoPantalla.width;

    public main() {
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

        // Cargar la imagen del personaje y vidas 
        try {
            characterImage = new ImageIcon("src/img/YodaBuenoAndando.gif").getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Cargar la imagen del corazón
        try {
            heartImage = new ImageIcon("src/img/corazon.png").getImage();
            // Ajustar el tamaño del corazón según sea necesario
            heartImage = resizeImage(heartImage, 50, 50);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        

        // Crear la lista de objetos mortales
        deadlyObjects = new ArrayList<>();
        deadlyObjects.add(new DeadlyObject(944, 475, "src/img/gifr2.gif"));
        deadlyObjects.add(new DeadlyObject(444, 475, "src/img/gifr2.gif"));

        // Crear la lista de plataformas
        platforms = new ArrayList<>();
        platforms.add(new Platform(111, 444, 111, 22, "src/img/suelo.png"));
        platforms.add(new Platform(333, 388, 83, 22, "src/img/suelo.png"));
        
        //Monedas
        coins = new ArrayList<>();
        coins.add(new Coin(200, 400, 30, 30, "src/img/moneda.gif"));
        coins.add(new Coin(500, 350, 30, 30, "src/img/moneda.gif"));
        coins.add(new Coin(700, 300, 30, 30, "src/img/moneda.gif"));
        coins.add(new Coin(1000, 250, 30, 30, "src/img/moneda.gif"));
        coins.add(new Coin(1200, 200, 30, 30, "src/img/moneda.gif"));
        coins.add(new Coin(1500, 400, 30, 30, "src/img/moneda.gif"));
        coins.add(new Coin(1800, 350, 30, 30, "src/img/moneda.gif"));
        coins.add(new Coin(2000, 300, 30, 30, "src/img/moneda.gif"));
        coins.add(new Coin(2200, 400, 30, 30, "src/img/moneda.gif"));
        coins.add(new Coin(2400, 350, 30, 30, "src/img/moneda.gif"));
        coins.add(new Coin(2600, 300, 30, 30, "src/img/moneda.gif"));
        coins.add(new Coin(2800, 250, 30, 30, "src/img/moneda.gif"));
        coins.add(new Coin(3000, 200, 30, 30, "src/img/moneda.gif"));
        coins.add(new Coin(3200, 400, 30, 30, "src/img/moneda.gif"));
        coins.add(new Coin(3400, 350, 30, 30, "src/img/moneda.gif"));
        coins.add(new Coin(3600, 300, 30, 30, "src/img/moneda.gif"));
        
        // Cargar la fuente personalizada desde un archivo TTF (TrueType Font)
        try {
            InputStream fontStream = getClass().getResourceAsStream("/font/CHLORINP.TTF");
            if (fontStream == null) {
                // Intenta cargar la fuente sin el "/" al principio
                fontStream = getClass().getResourceAsStream("font/SuperMario256.ttf");
            }

            if (fontStream != null) {
                try {
                    customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(Font.PLAIN, 40);
                } finally {
                    fontStream.close(); // Asegúrate de cerrar el flujo
                }
            } else {
                System.err.println("No se pudo cargar la fuente personalizada. Se utilizará la fuente predeterminada.");
                customFont = new Font("Arial", Font.PLAIN, 26);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // En caso de error, se usará la fuente predeterminada
            customFont = new Font("Arial", Font.PLAIN, 36);
        }

        labelContadorMonedas = new JLabel("Monedas: " + contadorMonedas);
        labelContadorMonedas.setBounds(20, 20, 150, 30);
        labelContadorMonedas.setForeground(Color.WHITE);
        labelContadorMonedas.setFont(customFont);  // Aplicar la fuente personalizada
        add(labelContadorMonedas);

        labelMensaje = new JLabel("");
        labelMensaje.setBounds(180, 20, 400, 30);
        labelMensaje.setForeground(Color.WHITE);
        add(labelMensaje);

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
        	  // Verificar si el personaje está en los valores específicos de X y en el suelo
            if ((backgroundX >= 600 && backgroundX <= 715 || backgroundX >= 1165 && backgroundX <= 1210) && characterY >= 485) {
                // Restar una vida al personaje
                lives--;
            }
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
         // Verificar la colisión con los objetos mortales
            for (DeadlyObject deadlyObject : deadlyObjects) {
                if (collisionWithDeadlyObject(deadlyObject)) {
                    // Acciones cuando hay una colisión (por ejemplo, decrementar vidas y reiniciar posición)
                    lives--;
                    if (lives <= 0) {
                        // El jugador ha perdido
                        JOptionPane.showMessageDialog(this, "¡Game Over!");
                        System.exit(0);  // Cierra la aplicación
                    } else {
                        // Reiniciar la posición del personaje
                        characterX = 50;
                        characterY = 300;
                        jumping = false;
                        jumpCount = 0;
                    }
                }
            }
            if (backgroundX >= 2293) {
                // El jugador ha perdido
                JOptionPane.showMessageDialog(this, "¡Has ganado!");
                System.exit(0);  // Cierra la aplicación
            }
            
            Iterator<Coin> iterator = coins.iterator();
            while (iterator.hasNext()) {
                Coin coin = iterator.next();
                if (collisionWithCoin(coin)) {
                    iterator.remove();
                    contadorMonedas++;
                    actualizarMensaje("¡Has recogido una moneda! Total de monedas: " + contadorMonedas);
                }
            }
            mostrarValores();
            labelContadorMonedas.setText("Monedas: " + contadorMonedas);
            labelContadorMonedas.repaint();
            repaint();

            // Vuelve a dibujar la pantalla
            repaint();
        });
        timer.start();
    }
    // Método para mostrar los valores de backgroundX, characterX, characterY, y backgroundY
    private void mostrarValores() {
        System.out.println("backgroundX: " + backgroundX +
                ", characterX: " + characterX +
                ", characterY: " + characterY);
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
    
    private boolean collisionWithCoin(Coin coin) {
        Rectangle characterBounds = new Rectangle(characterX, characterY, characterWidth, characterWidth);
        Rectangle coinBounds = new Rectangle(coin.getX() - backgroundX, coin.getY(),
                coin.getWidth(), coin.getHeight());

        return characterBounds.intersects(coinBounds);
    }

    private void actualizarMensaje(String mensaje) {
        labelMensaje.setText(mensaje);
        labelMensaje.repaint();
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
            if ((collisionWithPlatform(platform))&& characterY <= platform.getY()) {
          
            	jumping = false;  // Si colisiona con una plataforma, detener el salto
                jumpCount = 0;    // Reiniciar el contador de salto
               characterY = platform.getY() - characterWidth;  // Ajustar la posición del personaje al nivel de la plataforma
            } else if ((collisionWithPlatform(platform))&& characterY > platform.getY()) {
            	jumping = false; 
            }

            // Dibujar la plataforma usando las coordenadas y dimensiones almacenadas
            platform.draw(g2d, backgroundX);
        }
        
        for (Coin coin : coins) {
            g2d.drawImage(coin.getImage(), coin.getX() - backgroundX, coin.getY(),
                    coin.getWidth(), coin.getHeight(), this);
        }
//hola
        // Dibujar el personaje en el buffer de imagen
        if (facingRight) {
            g2d.drawImage(characterImage, characterX, characterY, characterWidth, characterWidth, this);
        } else {
            // Si el personaje está mirando a la izquierda, invertir la imagen
            g2d.drawImage(characterImage, characterX + characterWidth, characterY, -characterWidth, characterWidth, this);
        }
        
     // Dibujar el HUD
        int hudY = 50;
        g2d.setColor(new Color(255, 255, 255, 0));
        g2d.fillRect(10, hudY, 150, 40);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(10, hudY, 150, 40);

        // Establecer la fuente personalizada para el texto del HUD
        g2d.setFont(customFont);

        g2d.setColor(Color.RED);
        g2d.drawString("Monedas: " + contadorMonedas, 20, hudY + 40);

        // Dibujar el buffer de imagen en el JFrame
        g.drawImage(buffer, 0, 0, this);

        // Dibujar los corazones en la parte superior izquierda
        int heartX = 600;
        int heartY = 55;
        for (int i = 0; i < lives; i++) {
            g2d.drawImage(heartImage, heartX + i * 40, heartY, this);
        }

        // Dibujar el buffer de imagen en el JFrame
        g.drawImage(buffer, 0, 0, this);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            main game = new main();
            game.pack();
            game.setVisible(true);
        });
    }
}