import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.FontMetrics;
import java.awt.Dimension;
import java.util.ArrayList;

// SnakeFrame - A class to represent the snake game screen
public class SnakeFrame extends Frame {
    // Size of a cell
    public static final int BLOCK_WIDTH = 30;
    public static final int BLOCK_HEIGHT = 30;

    // Map size
    public static final int ROW = 25;
    public static final int COL = 25;

    // Score for each pass
    private static final int LEVEL_UP_REQUIEMENT[] = { 10, 15, 20 };

    // Array for wall design
    // Level 0
    // - length of walls is 8
    // - start from (12, 20)
    // - wall type is 1 (i.e. vertical)
    // Level 1
    // - length of walls is 6
    // - start from (20, 8)
    // - wall type is 0 (i.e. horizontal)
    // Level 2
    // - length of walls is 8
    // - start from (5, 12)
    // - wall type is 0 (i.e. horizontal)
    private static final int WALL_DESIGN[] = { 8, 12, 20, 1, 6, 20, 8, 0, 9, 5, 12, 0 };

    // Score equals to length of snake
    private int score = 1;
    // Current level of the game
    private int level = 0;
    // Indicate whether the game is over
    private boolean gameover = false;

    // Create a snake object
    private Snake snake = new Snake(this);
    // Create an egg
    private Egg egg = new Egg();
    // Create an EvilFarmer
    private EvilFarmer evilFarmer = new EvilFarmer();
    // Create an ArrayList of Barriers (i.e. a wall)
    public ArrayList<Barrier> wall = new ArrayList<Barrier>();

    // A static variable of SnakeFrame
    private static SnakeFrame sf = null;
    private MyPaintThread paintThread = new MyPaintThread();
    private Image offScreenImage = null;

    // Accessor of instance variable score
    public int getScore() {
        return score;
    }

    // Accessor of instance variable level
    public int getLevel() {
        return level;
    }

    // Mutator of instance variable score
    public void setScore(int score) {
        this.score = score;
    }

    // Mutator of instance variable bGameOver
    public void gameOver() {
        gameover = true;
    }

    // Accessor test
    public boolean getGameOver() {return gameover;}

    // main method of the game
    public static void main(String[] args) {
        sf = new SnakeFrame();
        sf.launch();
        sf.buildWall(WALL_DESIGN[0], WALL_DESIGN[1], WALL_DESIGN[2], WALL_DESIGN[3]);
    }

    /* TODO: buildWall method */
    public void buildWall(int len, int x, int y, int type) {
        // Add a row or column of barrier in the map
        // len: length of the wall
        // x: row of the first barrier
        // y: column of the first barrier
        // type: 1 for vertical wall, 0 for horizontal wall
        // Requirement: it's okay if barrier overlaps with snake,
        // but you need to make sure that it would not conflict with egg and farmer.

        // If horizontal wall of length len should be added
        //   Add walls from (x, y) to (?, ?) except those positions
        //   with an egg or a farmer.
        // If vertical wall of length len should be added
        //   Add walls from (x, y) to (?, ?) except those positions
        //   with an egg or a farmer.

        /* YOUR CODE HERE */

        if (type == 1) {
            for (int i = 0; i < len; ++i)
                wall.add(i, new Barrier(x+i,y));
        }
        else {
            for (int i = 0; i < len; ++i)
                wall.add(i, new Barrier(x,y+i));
        }
    }

    /* TODO: levelUp method */
    public void levelUp() {
        // Check if the score is greater than or equal to the level up requirement of the current level.
        // If so,
        //    increases the level by 1 and check whether max level is reached.
        //    If maximum level is reached,
        //      Set game over to true
        //    Otherwise,
        //      Build a wall according to WALL_DESIGN

        /* YOUR CODE HERE */

        if (score < LEVEL_UP_REQUIEMENT[0]) {

            wall.clear();
            sf.buildWall(WALL_DESIGN[0], WALL_DESIGN[1], WALL_DESIGN[2], WALL_DESIGN[3]);

            level = 0;
        }

        if ((score < LEVEL_UP_REQUIEMENT[1]) && (score >= LEVEL_UP_REQUIEMENT[0])){
            wall.clear();
            sf.buildWall(WALL_DESIGN[0], WALL_DESIGN[1], WALL_DESIGN[2], WALL_DESIGN[3]);
            sf.buildWall(WALL_DESIGN[4], WALL_DESIGN[5], WALL_DESIGN[6], WALL_DESIGN[7]);

            level = 1;
        }

        if ((score < LEVEL_UP_REQUIEMENT[2]) && (score >= LEVEL_UP_REQUIEMENT[1])) {

            wall.clear();
            sf.buildWall(WALL_DESIGN[0], WALL_DESIGN[1], WALL_DESIGN[2], WALL_DESIGN[3]);
            sf.buildWall(WALL_DESIGN[4], WALL_DESIGN[5], WALL_DESIGN[6], WALL_DESIGN[7]);
            sf.buildWall(WALL_DESIGN[8], WALL_DESIGN[9], WALL_DESIGN[10], WALL_DESIGN[11]);

            level = 2;
        }

        if (score >= LEVEL_UP_REQUIEMENT[2]) {
            gameOver();
        }
    }

    // Start the game
    public void launch() {
        setTitle("COMP 1022P: PA2 - The Gluttonous Snake and Farmer");
        setSize(ROW*BLOCK_HEIGHT, COL*BLOCK_WIDTH);
        setLocation(30, 40);
        setBackground(Color.WHITE);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }

        });
        setResizable(false);
        setVisible(true);
        // Listener for UI
        addKeyListener(new KeyMonitor());
        new Thread(paintThread).start();
    }

    /* TODO: infoGameover */
    private void infoGameover(Graphics g) {
        // Check if the game is over but haven't yet reached the max level.
        // If so,
        //   Display text "Game Over!!!".
        // Otherwise
        //   Display text "You Win!!!".

        /* YOUR CODE HERE */

        if((getGameOver() == true) && (getScore() < LEVEL_UP_REQUIEMENT[2])) {
            g.setColor(Color.RED);
            g.drawString("Game Over！！！", ROW / 2 * BLOCK_HEIGHT, COL / 2 * BLOCK_WIDTH);
            paintThread.dead();
        }

        if((getGameOver() == true) && getScore() == LEVEL_UP_REQUIEMENT[2]) {
            g.setColor(Color.RED);
            g.drawString("You Win！！！", ROW / 2 * BLOCK_HEIGHT, COL / 2 * BLOCK_WIDTH);
            paintThread.dead();
        }
    }

    /* TODO: update method */
    @Override
    public void update(Graphics g) {
        if(offScreenImage == null)
            offScreenImage = createImage(ROW*BLOCK_HEIGHT, COL*BLOCK_WIDTH);
        Graphics offg = offScreenImage.getGraphics();

        // Write content to virtual canvas
        paint(offg);
        // Write to canvas
        g.drawImage(offScreenImage, 0, 0, null);

        infoGameover(g);
        if(snake.eatEgg(egg))
            score+=1;


        // Check if the snake eats an egg, which is an evil farmer.
        // If so,
        //   Set the score according to the size of the snake.

        /* YOUR CODE HERE */

        if(snake.eatEgg(evilFarmer))
            if(evilFarmer.getEvil() == true) {
                score = snake.getLength();
            }
        else
            score = snake.getLength();

        levelUp();

        // Draw all the walls according to instance variable wall.

        /* YOUR CODE HERE */

        for (int i = 0; i < wall.size(); ++i) {
            //System.out.print(i);
            wall.get(i).draw(g);
        }

        evilFarmer.draw(g);
        egg.draw(g);
        snake.draw(g);

        if(!snake.ifAlive())
            gameOver();
        displaySomeInfo(g);
    }

    // Prompt message
    public void displaySomeInfo(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.BLUE);

        String str1 = "Instructions: Space Key for Pause, Key-r for Resume, Enter Key for Restart";
        Dimension d = this.getSize();
        FontMetrics fm = g.getFontMetrics();
        int x = (d.width - fm.stringWidth(str1)) / 2;
        g.drawString(str1, x,2*BLOCK_WIDTH);
        String str2 = "SCORE:"+score + "          " + "LIFE:"+snake.getLife() + "          " + "LEVEL:"+sf.level;
        x = (d.width - fm.stringWidth(str2)) / 2;
        g.drawString(str2, x,3*BLOCK_WIDTH);
        g.setColor(c);
    }

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.GRAY);
        // Draw UI
        for(int i = 0; i < ROW; i++)
            g.drawLine(0, (i+4)*BLOCK_HEIGHT, COL*BLOCK_WIDTH,(i+4)*BLOCK_HEIGHT );
        for(int i = 0; i < COL; i++)
            g.drawLine(i*BLOCK_WIDTH, 4*BLOCK_HEIGHT , i*BLOCK_WIDTH ,ROW*BLOCK_HEIGHT);
        g.setColor(Color.red);
        g.drawLine(0, (1*BLOCK_HEIGHT), COL*BLOCK_WIDTH,(1*BLOCK_HEIGHT) );
        g.drawLine(0, (int)(1.2*BLOCK_HEIGHT), COL*BLOCK_WIDTH,(int)(1.2*BLOCK_HEIGHT) );
        g.drawLine(0, (int)(3.2*BLOCK_HEIGHT), COL*BLOCK_WIDTH,(int)(3.2*BLOCK_HEIGHT) );
        g.setColor(c);
    }

    // Press SPACE to pause
    // Press KEY_R to resume
    // Press ENTER to restart
    private class MyPaintThread implements Runnable {
        private static final boolean RUNNING = true;
        private boolean pause = false;
        @Override
        public void run() {
            while(RUNNING) {
                if(!pause)
                    repaint();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void pause() {
            pause = true;
        }

        public void recover() {
            pause = false;
        }

        public void dead() {
            pause = true;
        }

        public void reStart() {
            sf.gameover = false;
            pause = false;
            snake = new Snake(sf);
            score = 1;
            level = 0;
            wall.clear();
            buildWall(WALL_DESIGN[0], WALL_DESIGN[1], WALL_DESIGN[2], WALL_DESIGN[3]);
        }
    }

    private class KeyMonitor extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_SPACE)
                paintThread.pause();
            else if(key == KeyEvent.VK_R) //RESUME
                paintThread.recover();
            else if(key == KeyEvent.VK_ENTER) //RESTART
                paintThread.reStart();
            else
                snake.keyPressed(e);
        }
    }
}
