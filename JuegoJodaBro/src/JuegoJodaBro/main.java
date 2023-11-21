package JuegoJodaBro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main extends JFrame {
    private int characterX = 50;
    private int characterY = 300;
    private int characterWidth = 50;
    private int backgroundX = 0;
    private Image characterImage;
    private Image backgroundImage;
    private boolean facingRight = true;
    private boolean jumping = false;
    private int jumpHeight = 550;
    private int jumpCount = 0;
    private int jumpSpeed = 4;
    private int moveSpeed = 4;
    private List<DeadlyObject> deadlyObjects;
    private List<Platform> platforms;
    private List<Coin> coins;
    private int contadorMonedas = 0;
    private JLabel labelContadorMonedas;
    private JLabel labelMensaje;
    private Font customFont;

    public Main() {
        try {
            backgroundImage = new ImageIcon("src/img/fondofinal.jpg").getImage();
            backgroundImage = resizeBackground(backgroundImage, 3200, 600);
            setSize(backgroundImage.getWidth(null), backgroundImage.getHeight(null));
        } catch (Exception e) {
            e.printStackTrace();
            setSize(800, 600);
        }
        setPreferredSize(new Dimension(800, 600));
        setLocationRelativeTo(null);

        try {
            characterImage = new ImageIcon("src/img/YodaBuenoAndando.gif").getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }

        deadlyObjects = new ArrayList<>();
        deadlyObjects.add(new DeadlyObject(944, 475, "src/img/gifplanta.gif"));
        deadlyObjects.add(new DeadlyObject(444, 475, "src/img/gifplanta.gif"));

        platforms = new ArrayList<>();
        platforms.add(new Platform(111, 444, 111, 22, "src/img/suelo.png"));
        platforms.add(new Platform(333, 388, 83, 22, "src/img/suelo.png"));

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
            InputStream fontStream = getClass().getResourceAsStream("/font/SuperMario256.ttf");
            if (fontStream == null) {
                // Intenta cargar la fuente sin el "/" al principio
                fontStream = getClass().getResourceAsStream("font/SuperMario256.ttf");
            }

            if (fontStream != null) {
                try {
                    customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(Font.PLAIN, 26);
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
            customFont = new Font("Arial", Font.PLAIN, 26);
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

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    characterX -= moveSpeed;
                    facingRight = false;
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    characterX += moveSpeed;
                    facingRight = true;
                }

                if ((e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP) && !jumping) {
                    jumping = true;
                    jumpCount = 0;
                }

                repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        setFocusable(true);

        Timer timer = new Timer(10, (e) -> {
            if (jumping) {
                if (jumpCount < jumpHeight / 2) {
                    characterY -= jumpSpeed;
                } else {
                    characterY += jumpSpeed;
                }

                if (facingRight) {
                    characterX += moveSpeed;
                } else {
                    characterX -= moveSpeed;
                }

                jumpCount += jumpSpeed;

                if (jumpCount >= jumpHeight) {
                    jumping = false;
                }
            } else {
                if (characterY < 485) {
                    characterY += 3;
                }
            }

            if (facingRight && characterX > getWidth() / 2) {
                backgroundX += moveSpeed;
                characterX = getWidth() / 2;
            }

            for (DeadlyObject deadlyObject : deadlyObjects) {
                if (collisionWithDeadlyObject(deadlyObject)) {
                    // Código para perder una vida (si decides agregar vidas nuevamente)
                    // ...
                }
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

            if (backgroundX >= 2293) {
                JOptionPane.showMessageDialog(this, "¡Has ganado!");
                System.exit(0);
            }

            labelContadorMonedas.setText("Monedas: " + contadorMonedas);
            labelContadorMonedas.repaint();
            repaint();
        });
        timer.start();
    }

    private boolean collisionWithDeadlyObject(DeadlyObject deadlyObject) {
        Rectangle characterBounds = new Rectangle(characterX, characterY, characterWidth, characterWidth);
        Rectangle deadlyObjectBounds = new Rectangle(deadlyObject.getX() - backgroundX, deadlyObject.getY(),
                deadlyObject.getWidth(), deadlyObject.getHeight());

        boolean collision = characterBounds.intersects(deadlyObjectBounds);

        if (collision && !deadlyObject.isJumpedOver() && characterY + characterWidth <= deadlyObject.getY()) {
            deadlyObject.setJumpedOver(true);
            return false;
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

    private Image resizeBackground(Image originalBackground, int width, int height) {
        return originalBackground.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    @Override
    public void paint(Graphics g) {
        BufferedImage buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = buffer.createGraphics();

        g2d.drawImage(backgroundImage, -backgroundX, 0, this);

        for (DeadlyObject deadlyObject : deadlyObjects) {
            g2d.drawImage(deadlyObject.getImage(), deadlyObject.getX() - backgroundX, deadlyObject.getY(),
                    deadlyObject.getWidth(), deadlyObject.getHeight(), this);
        }

        for (Platform platform : platforms) {
            platform.draw(g2d, backgroundX);
        }

        for (Coin coin : coins) {
            g2d.drawImage(coin.getImage(), coin.getX() - backgroundX, coin.getY(),
                    coin.getWidth(), coin.getHeight(), this);
        }

        if (facingRight) {
            g2d.drawImage(characterImage, characterX, characterY, characterWidth, characterWidth, this);
        } else {
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
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main game = new Main();
            game.pack();
            game.setVisible(true);
        });
    }
}
