import java.awt.*;
import java.util.Random;

/*
  Create a class called EvilFarmer, which inherits Egg, and has the following
  additional private instance variables and public methods.

  Instance variables:
  -x An Image type variable farmerImage, which is defined as follows:
    private Image farmerIMG = Toolkit.getDefaultToolkit().getImage("img/farmer.jpg");
  -x A static constant variable RANDOMGEN of Random type, which references to a Random type object.
  -x A static constant variable P_EVIL of double type with value 0.5.
  -x A boolean variable called evil which indicates if it is an evil farmer or not.

  Methods:
  -x Default constructor of EvilFarmer
  -x Another constructor of EvilFarmer that takes two int parameters representing (row, col)
    coordinates of EvilFarmer and use them to initialize the inherited row and col from Egg.
    It also initializes evil according to the returned value of isEvil method.
  -x reGenerate method which takes a SnakeFrame object sf and calls reGenerate method of the
    super class and set evil according to the returned value of isEvil method.
    Note: reGenerate method returns nothing.
  -x An accessor method, getEvil, which returns the value of evil.
  -x isEvil method which randomly generate a number using constant variable R.
    If the random number is greater P_EVIL, return true. Otherwise, return false.
    Note: isEvil does not take any input.
  -x draw method, which is defined as follows:
    public void draw(Graphics g) {
        g.drawImage(farmerIMG,
                 col*SnakeFrame.BLOCK_WIDTH+1,
                 row*SnakeFrame.BLOCK_HEIGHT+1,
                 SnakeFrame.BLOCK_WIDTH-1,
                 SnakeFrame.BLOCK_HEIGHT-1,
                 null);
    }
*/

/* TODO: EvilFarmer class */
// The followings are some given code for the EvilFarmer class.

public class EvilFarmer extends Egg {

    // The image used to represent the farmer.
    private Image farmerImage = Toolkit.getDefaultToolkit().getImage("img/farmer.jpg");

    private static final Random RANDOMGEN = new Random();

    private static final double P_Evil = 0.5;

    private boolean evil;

    // Default constructor
    public EvilFarmer() {
        super();
        evil = isEvil();
    }

    // Constructor that takes two parameters used to initialize instance variables row and col
    public EvilFarmer(int row, int col) {
        super(row, col);
    }

    public boolean getEvil() {
        return evil;
    }

    public boolean isEvil() {
        Double R = RANDOMGEN.nextDouble();
        if (R > P_Evil)
            return true;
        else
            return false;
    }

    public void reGenerate(SnakeFrame sf) {
        super.reGenerate(sf);
        evil = isEvil();
    }

    public Rectangle getRect() {
        return new Rectangle(col*SnakeFrame.BLOCK_WIDTH,
                row*SnakeFrame.BLOCK_HEIGHT,
                SnakeFrame.BLOCK_WIDTH,
                SnakeFrame.BLOCK_HEIGHT);
    }

    // The method to draw the farmer on screen.
    public void draw(Graphics g) {
        g.drawImage(farmerImage,
                 col*SnakeFrame.BLOCK_WIDTH+1,
                 row*SnakeFrame.BLOCK_HEIGHT+1,
                 SnakeFrame.BLOCK_WIDTH-1,
                 SnakeFrame.BLOCK_HEIGHT-1,
                 null);
    }
}
