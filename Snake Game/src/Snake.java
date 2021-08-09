import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

// Snake - A class to represent the snake in the game
public class Snake {
    // Static variables used to represent the width and height of the frame
    private static final int BLOCK_WIDTH = SnakeFrame.BLOCK_WIDTH;
    private static final int BLOCK_HEIGHT = SnakeFrame.BLOCK_HEIGHT;

    // Static array used to represent the color of the snake
    private static final Color SNAKECOLOR[] = { Color.BLACK, Color.magenta, Color.GREEN };

    // Reference variable to the head node of the snake
    private Node head = null;
    // Reference variable to the tail node of the snake
    private Node tail = null;
    // Reference variable to the frame of the snake game
    private SnakeFrame sf;
    // A boolean variable used to represent whether the snake is alive
    private boolean alive;
    // Initial position of the snake
    private Node node = new Node(3,4,Direction.D);
    // Length of the snake
    private int length = 0;
    // Life of the snake
    private int life = 0;

    // Constructor
    public Snake(SnakeFrame sf) {
        head = node;
        tail = node;
        head.pre = null;
        tail.next = null;
        length++;
        this.sf = sf;
        alive = true;
    }


    // The method used to draw the snake on screen
    public void draw(Graphics g) {
        if(head == null)
            return ;
        if(alive) {
            move();
            for (Node node = head; node != null; node = node.next)
                node.draw(g);
        }
    }

    // The method used to move the snake
    public void move() {
        addNodeInHead();
        // Check alive
        if (!checkDead())
            deleteNodeInTail();
    }

    // Accessor of instance variable alive
    public boolean ifAlive() {
        return alive;
    }

    // Accessor of instance variable length
    public int getLength() {
        return length;
    }

    // Mutator test
    public void setLength(int value) {
        length = value;
    }

    // Accessor of instance variable life
    public int getLife() {
        return life;
    }



    /* TODO: reverse method (OPTIONAL) */
    private void reverse() {
        // When the snake has spare lives and it runs into a barrier,
        // move the snake reversely so that the player is able to keep playing the game.
        /* YOUR CODE HERE */
    }

    /* TODO: checkDead method */
    private boolean checkDead() {
        // Check head with boundary
        if(head.row < 4 || head.row > SnakeFrame.ROW-1 || head.col < 0 || head.col > SnakeFrame.COL-1) {
            life--;
            if(life < 0) {
                alive=false;
                return true;
            } else {
                reverse();
                return false;
            }
        }

        // Check whether the snake bites itself
        for(Node node = head.next; node != null; node = node.next) {
            if(head.row == node.row && head.col == node.col) {
                life--;
                if(life < 0) {
                    this.alive = false;
                    return true;
                }
                else {
                    reverse();
                    return false;
                }
            }
        }

        // Check whether the snake hits the wall or not.
        // If so,
        //   Decreases the life by one, and checks if life is less than 0 or not.
        //   If life is less than 0,
        //     Sets alive to false and
        //     Returns true.
        //   Otherwise,
        //     Calls reverse method and
        //     Returns false.

        /* YOUR CODE HERE */

        for (int i = 12; i < 20; ++i) { //level 0
            if(head.row == i && head.col == 20) {
                life--;
                if(life < 0) {
                    alive=false;
                    return true;
                } else {
                    reverse();
                    return false;
                }
            }
        }

        if (sf.getLevel() == 1) { //level 1
            for (int i = 8; i < 14; ++i) {
                if(head.row == 20 && head.col == i) {
                    life--;
                    if(life < 0) {
                        alive=false;
                        return true;
                    } else {
                        reverse();
                        return false;
                    }
                }
            }
        }

        if (sf.getLevel() == 2) { //level 2
            for (int i = 12; i < 21; ++i) {
                if(head.row == 5 && head.col == i) {
                    life--;
                    if(life < 0) {
                        alive=false;
                        return true;
                    } else {
                        reverse();
                        return false;
                    }
                }
            }
        }

        return false;
    }

    // Remove the last node
    private void deleteNodeInTail() {
        Node node = tail.pre;
        tail = null;
        node.next = null;
        tail = node;
    }

    // Add a new node to the head
    private void addNodeInHead() {
        Node node = null;
        switch(head.dir){
            case L:
                node = new Node(head.row,head.col-1, head.dir);
                break;
            case U:
                node = new Node(head.row-1, head.col, head.dir);
                break;
            case R:
                node = new Node(head.row,head.col+1, head.dir);
                break;
            case D:
                node = new Node(head.row+1, head.col, head.dir);
                break;
        }

        node.next = head;
        head.pre = node;
        head = node;
        head.pre = null;
    }

    // A method to handle keypressed events
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch(key) {
            case KeyEvent.VK_LEFT :
                if(head.dir != Direction.R)
                   head.dir = Direction.L;
                break;
            case KeyEvent.VK_UP :
                if(head.dir != Direction.D)
                   head.dir = Direction.U;
                break;
            case KeyEvent.VK_RIGHT :
                if(head.dir != Direction.L)
                   head.dir = Direction.R;
                break;
            case KeyEvent.VK_DOWN :
                if(head.dir != Direction.U)
                   head.dir = Direction.D;
                break;
        }
    }

    // This is for collision detection
    public Rectangle getRect() {
        return new Rectangle(head.col*BLOCK_WIDTH,
                             head.row*BLOCK_HEIGHT,
                              BLOCK_WIDTH,
                              BLOCK_HEIGHT);
    }

    /* TODO: eatEgg method */
    public boolean eatEgg(EvilFarmer farmer) {
        // Check if the snake meets a farmer.
        // If so,
        //   Shows the farmer, and checks if the farmer is an evil farmer.
        //   If the farmer is an evil farmer,
        //     Decreases the length of the snake by half and
        //     returns true.
        //     Hint: the method deleteNodeInTail() may be useful for decreasing the length of snake.
        //   If the farmer is a kind farmer,
        //     Increase the life of snake by 1, and
        //     returns true.
        // If the snake does not meet a farmer,
        //   returns false.

        /* YOUR CODE HERE */

        int sLength = 0;

        if(getRect().intersects(farmer.getRect())) {
            if (farmer.getEvil() == true) {
                int maxL = getLength()/2;
                for (int i = 1; i <= maxL; i++) {
                    deleteNodeInTail();
                    sLength++;
                }

                setLength(getLength()-sLength);

                farmer.reGenerate(sf);
                return true;
            }
            else {
                life += 1;
                farmer.reGenerate(sf);
                return true;
            }
        }
        else
            return false;
    }

    // Method to check whether the snake eats an egg. If so, update the states of snake and egg
    public boolean eatEgg(Egg egg) {
        if(getRect().intersects(egg.getRect())) {
            addNodeInHead();
            egg.reGenerate(sf);
            length++;
            return true;
        }
        else
            return false;
    }

    // Inner class for Node
    public class Node {
        // The coordinates of node.
        private int row;
        private int col;

        // Direction
        private Direction dir;

        // Previous and next Node references
        private Node pre;
        private Node next;

        // Constructor
        public Node(int row, int col, Direction dir) {
            this.row = row;
            this.col = col;
            this.dir = dir;
        }

        // The method used to draw the node on screen
        public void draw(Graphics g) {
            Color c = g.getColor();
            g.setColor(SNAKECOLOR[sf.getLevel()]);
            g.fillRect(col*BLOCK_WIDTH, row*BLOCK_HEIGHT, BLOCK_WIDTH, BLOCK_HEIGHT);
            g.setColor(c);
        }
    }
}
