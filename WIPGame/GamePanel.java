import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;

    final int tileSize = originalTileSize * scale; // 48x48 tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    final int[] bulletSize = { tileSize / 4, tileSize / 2 }; // width x height (12,24)

    // FPS
    int fps = 60;

    ArrayList<Square> bulletsOnScreen = new ArrayList<>();
    int bulletDelay = 0;

    int[] playerWidXHi = { tileSize, tileSize };
    Square player = new Square(100, screenHeight - (tileSize + 10), 5, 0, playerWidXHi);

    int[] enemyWidXHi = { tileSize, tileSize };
    Square enemy = new Square(200, 200, 0, 0, playerWidXHi);
    ArrayList<Square> enemiesOnScreen = new ArrayList<>();

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / fps; // 0.01666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        enemiesOnScreen.add(enemy);

        while (gameThread != null) {

            // 1 UPDATE: info such as character's pos
            update();

            // 2 DRAW: draw screen with updated info
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void update() {

        if (keyH.rightPressed && player.getXPos() < (screenWidth - tileSize)) {
            player.setXPos(player.getXPos() + player.getXVel());
        }

        if (keyH.leftPressed && player.getXPos() > 0) {
            player.setXPos(player.getXPos() - player.getXVel());
        }

        if (keyH.spacePressed) {
            if (bulletDelay <= 0) {
                addBullet();
                bulletDelay = 50;
            }

        }
        bulletDelay--;

        for (int i = 0; i < bulletsOnScreen.size(); i++) {
            bulletsOnScreen.get(i).setYPos(bulletsOnScreen.get(i).getYPos() + bulletsOnScreen.get(i).getYVel());
            bulletsOnScreen.get(i).updateHitbox(bulletsOnScreen.get(i).getXPos(), bulletsOnScreen.get(i).getYPos());
        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.white);

        g2.fillRect(player.getXPos(), player.getYPos(), tileSize, tileSize);

        for (int i = 0; i < enemiesOnScreen.size(); i++) {
            g2.setColor(Color.red);
            g2.fillRect(enemy.getXPos(), enemy.getYPos(), tileSize, tileSize);
        }

        for (int i = 0; i < bulletsOnScreen.size(); i++) {
            if (bulletsOnScreen.get(i).getYPos() >= 0 + (-1 * bulletSize[1])) {

                g2.setColor(Color.gray);
                g2.fillRect(bulletsOnScreen.get(i).getXPos(), bulletsOnScreen.get(i).getYPos(), bulletSize[0],
                        bulletSize[1]);

                // VISUALIZING HIT BOX OF BULLET
                g2.setColor(Color.blue);
                g2.fillRect(bulletsOnScreen.get(i).getHitbox().visualizeHitbox()[0],
                        bulletsOnScreen.get(i).getHitbox().visualizeHitbox()[1], bulletSize[0],
                        bulletSize[1]);

                if (enemiesOnScreen.get(0) != null
                        && bulletsOnScreen.get(i).getHitbox().doesCollide(enemiesOnScreen.get(0).getHitbox())) {
                    bulletsOnScreen.remove(bulletsOnScreen.get(i));
                    enemiesOnScreen.remove(enemiesOnScreen.get(0));
                }

            } else {
                bulletsOnScreen.remove(bulletsOnScreen.get(i));
            }
        }

        g2.dispose();

    }

    public void addBullet() {
        Square bullet = new Square(player.getXPos() + 18, player.getYPos(), 0, -3, bulletSize);
        bulletsOnScreen.add(bullet);
    }

}
