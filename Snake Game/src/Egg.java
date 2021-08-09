import java.awt.*;
import java.util.Random;

// Egg - A class to represent an egg in the game
public class Egg {
    // The coordinates of the egg
    protected int row;
    protected int col;

    // The random generator, which will be used to randomly generate the coordinates of egg
    private static final Random RANDOMGEN = new Random();

    private Color color = Color.RED;

    private static final int LEVELS[] = { 0, 1, 2 };

    // Constructor that takes two parameters used to initialize instance variables row and col
    public Egg(int row, int col) {
        this.row = row;
        //System.out.println(this.row);
        this.col = col;
        //System.out.println(this.col);
    }

    // Default constructor
    public Egg() {
        this(RANDOMGEN.nextInt(SnakeFrame.ROW - 4) + 4,
             RANDOMGEN.nextInt(SnakeFrame.COL));
    }

    // TODO: reGenerate method
    public void reGenerate(SnakeFrame sf) {
        // Randomly generate the coordinates of egg such that the egg is
        // not coincided with any wall.

        // The following statements demonstrate how to randomly generate the
        // coordinates of egg.

        /* YOUR CODE HERE */

        while (true) {

            boolean collide0 = false;
            boolean collide1 = false;
            boolean collide2 = false;

            row = (RANDOMGEN.nextInt(SnakeFrame.ROW-4))+4;
            col = (RANDOMGEN.nextInt(SnakeFrame.COL));

            for (int i = 12; i < 20; ++i) {
                if (row == i && col == 20) {
                    collide0 = true;
                }
            }

            if (sf.getLevel() >= LEVELS[1]) {
                for (int i = 8; i < 14; ++i) {
                    if (row == 20 && col == i) {
                        collide1 = true;
                    }
                }
            }

            if (sf.getLevel() == LEVELS[2]) {
                for (int i = 12; i < 21; ++i) {
                    if (row == 5 && col == i) {
                        collide1 = true;
                    }
                }
            }

            if (collide0 == false && collide1 == false && collide2 == false)
                break;
        }

    }

    // The method used to draw the Egg on screen.
    public void draw(Graphics g) {
        Color c = g.getColor();
        g.setColor(color);
        g.fillOval(col*SnakeFrame.BLOCK_WIDTH,
                   row*SnakeFrame.BLOCK_HEIGHT,
                    SnakeFrame.BLOCK_WIDTH,
                    SnakeFrame.BLOCK_HEIGHT);
        g.setColor(c);
        if(color == Color.RED)
            color = Color.BLUE;
        else
            color = Color.RED;
    }

    // Accessor of instance variable row
    public int getRow() {
        return row;
    }

    // Accessor of instance variable col
    public int getCol() {
        return col;
    }

    // This is for collision detection
    public Rectangle getRect() {
        return new Rectangle(col*SnakeFrame.BLOCK_WIDTH,
                             row*SnakeFrame.BLOCK_HEIGHT,
                              SnakeFrame.BLOCK_WIDTH,
                              SnakeFrame.BLOCK_HEIGHT);
    }
}
