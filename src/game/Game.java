    
package game;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import animation.AbstractAnimation;

import java.util.ArrayList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComponent;

/**
 * This class implements the game that contains multiple animated objects,
 * including the ship, the bullets and the asteroids, to achieve the movements and interactions
 * between these object on a window.
 */
public class Game extends AbstractAnimation implements KeyListener{
    
    // The width of the window in pixels
    private static final int WINDOW_WIDTH = 600;
    // The height of the window in pixels
    private static final int WINDOW_HEIGHT = 600;
    
    // Create Ship Object
    private Ship ship = new Ship(this);
   
    // Create Asteroid Object and its properties
    private ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
    private int asteroidNumber;
    private int asteroidSpeed;
    
    // Increase 10 points hitting each asteroid at the initial level
    private int scoreIncrease = 10;
    
    private Score score = new Score();
    // Create a JLabel score board to keep track of the score
    private static JLabel scoreBoard = new JLabel();
    
    public Game() {

        // Allow the game to receive key input
        setFocusable(true);
        addKeyListener (this);
        
        // Initialize the properties of the asteroids
        asteroidNumber = 5;
        asteroidSpeed = 2;
        scoreIncrease = 10;
        createAsteroids();
    }
    
    /**
     * 
     */
    private void createAsteroids() {
        for (int i = 0; i < asteroidNumber; i++) {
            int a = (int)Math.round(Math.random()*3);
            int x = 0;
            int y = 0;
            if (a == 0) {
                x = (int)Math.ceil(Math.random()*WINDOW_WIDTH);
                y = 0;
            }
            else if (a == 1) {
                x = (int)Math.ceil(Math.random()*WINDOW_WIDTH);
                y = WINDOW_HEIGHT;
                
            }
            else if (a == 2) {
                x = 0;
                y = (int)Math.ceil(Math.random()*WINDOW_HEIGHT);
            }
            else {
                x = WINDOW_WIDTH;
                y = (int)Math.ceil(Math.random()*WINDOW_HEIGHT);
            }
            double direction = Math.random()*2*Math.PI;
            
            Asteroid asteroid = new Asteroid(x,y,direction,asteroidSpeed,this);
            asteroids.add(asteroid);
        }
        System.out.println(asteroids.size());
    }
    private int counter = 1;
    
    /**
     * gets called in every frame
     */
    protected void nextFrame() {
        ship.nextFrame();
        
        // Show the score board
        scoreBoard.setText("Score: " + score.getScore());
        
        for (Asteroid asteroid : asteroids) {
            asteroid.nextFrame();
        }
        
        //call nextFrame() for each valid bullet
        ArrayList<Bullet> bullets = ship.getBullets();
        for(int i=0;i<bullets.size();i++) {
            Bullet b = bullets.get(i);
            b.nextFrame();
        }
        
        //decreases the speed and rotation of the ship per 3 frames.
        if(counter%3==0) {
            ship.setMoveAmount( ship.getMoveAmount() * 0.9);
            ship.setRotate(ship.getRoate() * 0.3);
            counter=0;
        }
        counter++;
        
        //loop through every asteroid on the screen
        for (int i = 0; i < asteroids.size(); i++) {
            Asteroid a = asteroids.get(i);
            //if ship collided with asteroid, end the game
            if (checkCollision (ship, a)) {
                System.out.println("GAME OVER");
                stop();
            } 
            //loop through every valid bullet and check for collision with asteroid
            for (int j = 0; j < ship.getBullets().size(); j++) {
                Bullet b = ship.getBullets().get(j);
                if (checkHit (b, a)) {
                    asteroids.remove(a);
                    //set the collided bullet as invalid
                    b.setValid(false);
                    //Increase 10 points for each asteroid
                    score.increaseScore(scoreIncrease);
                    scoreBoard.setText("Score: " + score.getScore());
                    break;
                }   
            }
        }

        if (asteroids.size() == 0) {
            
            asteroidNumber = (int)(asteroidNumber*1.5);
            asteroidSpeed = (int)(asteroidSpeed*1.5);
            
            // Each asteroid worths more points as the difficulty level increases
            scoreIncrease = scoreIncrease + 10;
            createAsteroids();
        } 
    }
    
    /**
     * Check whether the ship collides with any asteroid.  This tests whether their shapes intersect.
     * @param first test the shape of ship
     * @param first test the shape of the asteroid
     * @return true if the shapes intersect
     */
    private boolean checkCollision(Ship ship, Asteroid asteroid) {
        return asteroid.getShape().intersects(ship.getShape().getBounds2D());
    }
    
    /**
     * Check whether the the bullet hits any asteroid.  This tests whether their shapes intersect.
     * @param first test the shape of the bullet
     * @param first test the shape of the asteroid
     * @return true if the shapes intersect
     */
    private boolean checkHit(Bullet bullet, Asteroid asteroid) {
        return asteroid.getShape().intersects(bullet.getShape().getBounds2D());
    }
    
    /**
     * Paint the animation by painting ship, bullets, and asteroids in the animation.
     * @param g the graphic context to draw on
     */
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        // Paint ship
        ship.paint((Graphics2D) g);
        
        //iterate through all the valid bullets and paint the bullet
        ArrayList<Bullet> bullets = ship.getBullets();
        for(int i=0;i<bullets.size();i++) {
            Bullet b = bullets.get(i);
            b.paint((Graphics2D) g);
        }
        // Paint Asteroids
        for (Asteroid asteroid:asteroids) {
            asteroid.paint((Graphics2D) g);
        }
    }
    
    /**
     * Control the movement of the ship when the user presses a key.
     * It notifies the animated ball about presses of up arrow, right 
     * arrow, left arrow, and the space bar.  All other keys are ignored.
     * @param e information about the key pressed
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
        //When the user presses "UP", the ship thrusts
        case KeyEvent.VK_UP:
            ship.thrust();
            break;
        //When the user presses "RIGHT", the ship rotates to the right
        case KeyEvent.VK_RIGHT:
            ship.rotateRight();
            break;
        //When the user presses "LEFT", the ship rotates to the left
        case KeyEvent.VK_LEFT:
            ship.rotateLeft();
            break;
        //When the user presses "H", the ship goes to the hyperspace
        case KeyEvent.VK_H:
            ship.hyperspace();
            break;
        //When the user presses "Space", the ship shoots
        case KeyEvent.VK_SPACE:
            ship.shoot();
            break;
        // Ignore all other keys
        default:    
        }
    }

    /**
     * This is called when the user releases the key after pressing it.
     * It does nothing.
     * @param e information about the key released
     */
    public void keyReleased(KeyEvent e) {
        // Nothing to do
    }

    /**
     * This is called when the user presses and releases a key without
     * moving the mouse in between.  Does nothing.
     * @param e information about the key typed.
     */
    public void keyTyped(KeyEvent e) {
        // Nothing to do
    }
    
    /**
     * The main method creates a window for the animation to run in,
     * initializes the animation and starts it running.
     * @param args none
     */
    public static void main(String[] args) { 
        // Create the window
        JFrame f = new JFrame();
        // Create a JPanel for score
        JPanel scorePanel = new JPanel(new BorderLayout());
        
        // Set the window's title and its size
        f.setTitle("Asteroid Game");
        f.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

        Game game = new Game();
        
        // Add the animation to the window
        Container contentPane = f.getContentPane();
        contentPane.add(scorePanel,BorderLayout.NORTH);
        contentPane.add(game, BorderLayout.CENTER);
        
        // Add the JLabel to the JPanel and set the color
        scorePanel.add(scoreBoard);
        scorePanel.setBackground(Color.WHITE);
        scoreBoard.setForeground(Color.BLACK);
        
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(true);
        // Display the window.
        f.setVisible(true);
        
        // Start the animation
        game.start();

    }

}


