package low.budget.snake.xenzia;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

/**
 *
 * @author Farhan Nasif Nizami
 */
public class GamePanel extends JPanel implements ActionListener {

    static final int screen_width = 1000;
    static final int screen_height = 600;

    static final int unitsize = 20;
    static final int gameunits = (screen_height * screen_width) / unitsize;
    static final int delay = 100;

    final int x[] = new int[gameunits];
    final int y[] = new int[gameunits];

    int body = 6;
    int foodeaten = 0;

    int foodx;
    int foody;

    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random rnd;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkFood();
            checkCollision();
        }
        repaint();
    }

    GamePanel() {
        rnd = new Random();
        setPreferredSize(new Dimension(screen_width, screen_height));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new CustomKeyAdapter());
        startGame();
    }

    public void startGame() {
        newFood();
        running = true;
        timer = new Timer(delay, this);
        timer.start();
    }

    public void newFood()
    {
        foodx = rnd.nextInt((int) (screen_width / unitsize)) * unitsize;
        foody = rnd.nextInt((int) (screen_height / unitsize)) * unitsize;
    }

    public void paintComponent(Graphics gg) {
        super.paintComponent(gg);
        draw(gg);

    }

    public void draw(Graphics g) {

        if (running) {

            g.setColor(Color.red);
            g.fillOval(foodx, foody, unitsize, unitsize);

            for (int i = 0; i < body; i++) {
                if (i == 0) {
                    g.setColor(Color.GREEN);
                    g.fillRect(x[i], y[i], unitsize, unitsize);

                    g.setColor(Color.blue);
                    if (direction == 'R') {
                        g.fillRect(x[i] + 10, y[i] + 12, 5, 5);
                        g.fillRect(x[i] + 10, y[i] + 3, 5, 5);
                    } else if (direction == 'L') {
                        g.fillRect(x[i] + 5, y[i] + 12, 5, 5);
                        g.fillRect(x[i] + 5, y[i] + 3, 5, 5);
                    } else if (direction == 'U') {
                        g.fillRect(x[i] + 3, y[i] + 5, 5, 5);
                        g.fillRect(x[i] + 12, y[i] + 5, 5, 5);
                    }
                    else
                    {
                        g.fillRect(x[i] + 3, y[i] + 10, 5, 5);
                        g.fillRect(x[i] + 12, y[i] + 10, 5, 5);
                    }

                } else {
                    g.setColor(Color.GREEN);
                    g.fillRect(x[i], y[i], unitsize, unitsize);
                }
            }

            g.setColor(Color.WHITE);
            g.setFont(new Font("Serif", Font.BOLD, 30));
            FontMetrics mtx = getFontMetrics(g.getFont());
            g.drawString("Score: " + foodeaten, 20, g.getFont().getSize());
        } else {
            gameOver(g);
        }
    }

    public void move() {
        for (int i = body; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        if (direction == 'U') {
            y[0] -= unitsize;
        }
        if (direction == 'D') {
            y[0] += unitsize;
        }
        if (direction == 'L') {
            x[0] -= unitsize;
        }
        if (direction == 'R') {
            x[0] += unitsize;
        }
    }

    public void checkFood() {

        if (x[0] == foodx && y[0] == foody) {
            body++;
            foodeaten++;
            newFood();
        }

    }

    public void checkCollision() {

        // head body collision
        for (int i = body; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
            }
        }

        //head border collision
        if (x[0] < 0) {
            running = false;
        }
        if (x[0] > screen_width) {
            running = false;
        }
        if (y[0] < 0) {
            running = false;
        }
        if (y[0] > screen_height) {
            running = false;
        }

        if (running == false) {
            timer.stop();
        }
    }
    
    void parentFrameDelete()
    {
        
       GameFrame topFrame = (GameFrame) SwingUtilities.getWindowAncestor(this);
       topFrame.dispose();
    }

    public void gameOver(Graphics g) {

        g.setColor(Color.red);
        g.setFont(new Font("Serif", Font.BOLD, 75));
        FontMetrics mtx = getFontMetrics(g.getFont());
        g.drawString("Game Over", (screen_width - mtx.stringWidth("Game Over")) / 2, screen_height / 2);

        g.setColor(Color.red);
        g.setFont(new Font("Serif", Font.BOLD, 40));
        FontMetrics mtx2 = getFontMetrics(g.getFont());
        g.drawString("Score: " + foodeaten, (screen_width - mtx2.stringWidth("Score: " + foodeaten)) / 2, screen_height / 2 + 60);

        g.setColor(Color.red);
        g.setFont(new Font("Serif", Font.BOLD, 30));
        FontMetrics mtx3 = getFontMetrics(g.getFont());
        g.drawString("Press Space to Play Again", (screen_width - mtx3.stringWidth("Press Space to Play Again")) / 2, screen_height - 40);
    }

    public class CustomKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
                if (direction != 'R') {
                    direction = 'L';
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
                if (direction != 'L') {
                    direction = 'R';
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
                if (direction != 'D') {
                    direction = 'U';
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
                if (direction != 'U') {
                    direction = 'D';
                }
            }

            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                new GameFrame();
                parentFrameDelete();
            }
        }
    }
}
