import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;


public class GamePanel extends JPanel implements ActionListener{

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 75;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 4;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    JButton button;
    JFrame frame;

    
    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();

    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);

    }
    public void draw(Graphics g) {
        if(running) {
            /* 
            for (int ii = 0; ii < SCREEN_HEIGHT/UNIT_SIZE; ii++) {
                g.drawLine(ii*UNIT_SIZE, 0, ii*UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, ii*UNIT_SIZE, SCREEN_WIDTH, ii*UNIT_SIZE);
            }
            */
            g.setColor(Color.yellow);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            //Snake body
            for(int mwa = 0; mwa < bodyParts; mwa++) {
                if(mwa == 0) {
                    g.setColor(new Color(120, 229, 171));
                    g.fillRect(x[mwa], y[mwa], UNIT_SIZE, UNIT_SIZE);
                }
                else {
                    //g.setColor(new Color(45,180, 0));
                    /* 
                    int red = 102 + random.nextInt(12);
                    int green = 204 + random.nextInt(24);
                    int blue = 153 + random.nextInt(36);
                    */
                    g.setColor(new Color(104, 195, 155));
                    g.fillRect(x[mwa], y[mwa], UNIT_SIZE, UNIT_SIZE);
                }
            }
        
        Color myTurquoise = new Color(48, 213, 200);
        g.setColor(myTurquoise);
        g.setFont(new Font("Courier",Font.BOLD, 24));
        FontMetrics metrics = getFontMetrics(g.getFont());
        //int ascend = metrics.getAscent();
        g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/35, g.getFont().getSize());
        }
        else {
            gameOver(g);
        }

    }
    public void newApple(){
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    public void move() {
        for (int jj = bodyParts; jj > 0; jj--) {
            x[jj] = x[jj-1];
            y[jj] = y[jj-1];
        }

        switch(direction) {

            case 'U':
                y[0] = y[0] - UNIT_SIZE; //y[0] is the head of the snake
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE; 
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE; 
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
            

        }
        
    }
    public void checkApple() {
        if((x[0] == appleX) && (y[0] == appleY)){
            bodyParts++;
            applesEaten++;
            newApple();
        }

    }
    public void checkCollisions() {
        //body head and body collision checker
        for(int XD = bodyParts; XD > 0; XD--) {
            if((x[0] == x[XD])&& (y[0] == y[XD])) {
                running = false;
            }
        }
        //head touch left boarder checker
        if(x[0] < 0) {
            running = false;
        }
        //right boarder collission checker
        if(x[0] > SCREEN_WIDTH) {
            running = false;
        }
        //top boarder collission checker
        if(y[0] < 0 ) {
            running = false;

        }
        if(y[0] > SCREEN_HEIGHT) {
            running = false;
        }
        if (!running) {
            timer.stop();
        }

    }
    public void gameOver(Graphics g) {

        //Score Display After Game Over
        Color myTurquoise = new Color(48, 213, 200);
        g.setColor(myTurquoise);
        g.setFont(new Font("Courier",Font.BOLD, 24));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        int ascend = metrics1.getAscent();
        g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+applesEaten))/2, (SCREEN_HEIGHT/2) + ascend);

        
        //Game Over text
        //Color myTurquoise = new Color(48, 213, 200);
        g.setColor(myTurquoise);
        g.setFont(new Font("Ink Free",Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);


    }
    public void retryGame(ActionEvent e) {
        button = new JButton("RETRY");
        
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bodyParts = 4;
                applesEaten = 0;
                direction = 'R';
                newApple();
                running = true;
                timer.start();
                frame.requestFocusInWindow();

            }
        });

    }
    @Override
    public void actionPerformed(ActionEvent e) {
       if (running) {
        move();
        checkApple();
        checkCollisions();
       }
       repaint();

    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U') {
                        direction = 'D';
                    }
                    break;
            
            }

        }
    }

}
