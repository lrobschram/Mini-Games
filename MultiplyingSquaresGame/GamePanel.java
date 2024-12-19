package MultiplyingSquaresGame;

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

    // FPS
    int fps = 60;

    ArrayList<Square> listOfSquares = new ArrayList<>();
    int speedVary = 0;

    Thread gameThread;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
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

        Square firstSquare = new Square(2, 2);
        
        listOfSquares.add(firstSquare);

        while (gameThread != null) {

            // 1 UPDATE: info such as square's pos
            update();

            // 2 DRAW: draw screen with updated info
            repaint();

            // drawing scene at 60 fps
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

        // when a squre hits top or bottom of the screen, add a square and send in the opposite direction
        for(int i = 0; i < listOfSquares.size(); i++) {
            if (listOfSquares.get(i).getYPos() >= (screenHeight - tileSize) || listOfSquares.get(i).getYPos() <= 0) {
                listOfSquares.get(i).setYVel(-1 * listOfSquares.get(i).getYVel());
                addSquare();
            }

            // update square's y pos
            listOfSquares.get(i).setYPos(listOfSquares.get(i).getYPos() + listOfSquares.get(i).getYVel());

            // when a squre hits left or right side of the screen, add a square and send in the opposite direction
            if (listOfSquares.get(i).getXPos() >= (screenWidth - tileSize) || listOfSquares.get(i).getXPos() <= 0) {
                listOfSquares.get(i).setXVel(-1 * listOfSquares.get(i).getXVel());
                addSquare();
            }

            // update square's x pos
            listOfSquares.get(i).setXPos(listOfSquares.get(i).getXPos() + listOfSquares.get(i).getXVel());
        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        int count = 0;

        // alternate between 6 different colors
        for (Square a : listOfSquares) {
            if(count % 6 == 0) {
                g2.setColor(Color.white);
            }
            else if (count % 6 == 1) {
                g2.setColor(Color.magenta);
            }
            else if (count % 6 == 2) {
                g2.setColor(Color.red);
            }
            else if (count % 6 == 3) {
                g2.setColor(Color.blue);
            }
            else if (count % 6 == 4) {
                g2.setColor(Color.green);
            }
            else if (count % 6 == 5) {
                g2.setColor(Color.yellow);
            }

            g2.fillRect(a.getXPos(), a.getYPos(), tileSize, tileSize);
            count++;
        }

        g2.dispose();

    }

    public void addSquare() {
        if(listOfSquares.size() <= 400) {

            Random ran = new Random();
            
            // alternate between 4 different directions with a random magnitude (1 - 4)
            if(speedVary % 4 == 0) {
                Square toAdd = new Square(ran.nextInt(1,4), -1 * ran.nextInt(1,4));
                listOfSquares.add(toAdd);
                speedVary++;
            }

            else if(speedVary % 4 == 1) {
                Square toAdd = new Square(-1 * ran.nextInt(1,4), -1 * ran.nextInt(1,4));
                listOfSquares.add(toAdd);
                speedVary++;
            }

            else if(speedVary % 4 == 2) {
                Square toAdd = new Square(-1 * ran.nextInt(1,4), ran.nextInt(1,4));
                listOfSquares.add(toAdd);
                speedVary++;
            }

            else if(speedVary % 4 == 3) {
                Square toAdd = new Square(ran.nextInt(1,4), ran.nextInt(1,4));
                listOfSquares.add(toAdd);
                speedVary++;
            }
            
        }
    }

}

