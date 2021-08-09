import java.awt.*;

// Barrier - the component of wall
public class Barrier {
    // The image used to represent the barrier
    private Image barrierImage = Toolkit.getDefaultToolkit().getImage("img/barrier.jpg");

    // The coordinates of the barrier
    private int row;
    private int col;

    // Constructor
    public Barrier(int row, int col) {
        this.row = row;
        this.col = col;
    }

    // The method used to draw the barrier on screen
    public void draw(Graphics g) {
        g.drawImage(barrierImage,
		            col*SnakeFrame.BLOCK_WIDTH+1,
		            row*SnakeFrame.BLOCK_HEIGHT+1,
					SnakeFrame.BLOCK_WIDTH-1, 
					SnakeFrame.BLOCK_HEIGHT-1, 
					null);
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
