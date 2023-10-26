package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import animation.AbstractAnimation;

public class Ship{
    private static final int SHIP_WIDTH = 10;
    private static final int SHIP_HEIGHT = 17;
    private static final int ACCEL_AMOUNT = 3;
    
    private AbstractAnimation animation;
    private Polygon ship;
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    
    private double x;
    private double y;
    private double z;
    private double moveAmount = 0;
    private double rotateRadian=0;

    
    /**
     * creates a ship at (300,400) with the given height and width 
     * @param animation
     */
    public Ship(AbstractAnimation animation) {
        //creates a triangle
        ship = new Polygon();
        ship.addPoint(SHIP_WIDTH*-1, SHIP_HEIGHT);
        ship.addPoint(0, -1*SHIP_HEIGHT);
        ship.addPoint(SHIP_WIDTH, SHIP_HEIGHT);
        this.animation = animation;
        
        //starting position
        x = 300;
        y = 400;
        z = 0;
    }
    
    /**
     * paint the ship
     * @param g
     */
    public void paint(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.draw(getShape());
    }
    
    /**
     * gets the shape of the ship
     * @return
     */
    public Shape getShape() {

        AffineTransform at1 = new AffineTransform();
        
        //translate and rotate with x,y,z values
        at1.translate(x, y);
        at1.rotate(z);
        
        AffineTransform at = at1;
        
        // Create a ship
        return at.createTransformedShape(ship);
    }
    public void nextFrame() {
        calculateXY();
    }
    
    /**
     * @return list of bullets that are valid
     */
    public ArrayList<Bullet> getBullets() {
        for(int i=0;i<bullets.size();i++) {
            Bullet b = bullets.get(i);
            //if out of the screen, remove from bullets list
            if(!b.isValid()) {
                bullets.remove(b);
            }
        }
        return bullets;
    }
    /**
     * get the acceleration amount
     * @return
     */
    public double getMoveAmount() {
        return moveAmount;
    }
    /**
     * sets the acceleration amount to the parameter value
     * @param amount
     */
    public void setMoveAmount(double amount) {
        moveAmount = amount;
    }
    /**
     * get the rotation angle in Radian
     * @return
     */
    public double getRoate() {
        return rotateRadian;
    }
    /**
     * set the rotation angle to the parameter value in Radian
     * @param amount
     */
    public void setRotate(double amount) {
        rotateRadian = amount;
    }
    
    /**
     * calculates the x,y,z coordinates which manage the direction, rotation,
     * the movement of the ship and wrapping around the screen
     */
    public void calculateXY() {
        //calculates the coordinates considering the rotation angle
        y += moveAmount*Math.cos(z)*-1;
        x += moveAmount*Math.sin(z);
        z += rotateRadian;
        
        //wrap around
        int screenW = animation.getWidth();
        int screenH = animation.getHeight();
        y %=(screenH+1);
        x %=(screenW+1);
        if (x < 0)
            x += screenW;
        if (y < 0)
            y += screenH;
    }
    
    /**
     * increase the move amount by the acceleration amount
     * up to 20
     */
    public void thrust() {
        moveAmount+=ACCEL_AMOUNT;
        if(moveAmount>20) moveAmount=20;
    }
    
    

    /**
     * increase the rotation angle by .5/PI and up to 1.2/PI
     * to rotate the ship to the right
     */
    public void rotateRight() {
        rotateRadian += 0.5/Math.PI;
        if(rotateRadian>(1.2/Math.PI)) rotateRadian = 1.2/Math.PI;
    }

    /**
     * decrease the rotation angle by .5/PI and up to -1.2/PI
     * to rotate the ship to the left
     */
    public void rotateLeft() {
        rotateRadian -= 0.5/Math.PI;
        if(rotateRadian<(-1.2/Math.PI)) rotateRadian = -1.2/Math.PI;
    }

    /**
     * fires a bullet
     */
    public void shoot() {
        //bullet starts from the position the ship is at and to its direction
        //but with 5 faster speed
        Bullet b = new Bullet(x,y,z,moveAmount+5,animation);
        //add to the existing bullets' list
        bullets.add(b);
    }
    
    /**
     * goes to a random location on screen
     */
    public void hyperspace() {
        //calculated to appear at the location inside the screen size
        double a = Math.floor(Math.random()*animation.getWidth()-(SHIP_WIDTH*3)+SHIP_WIDTH*2);
        double b = Math.floor(Math.random()*animation.getHeight()-(SHIP_HEIGHT*3)+SHIP_HEIGHT*2);
        x=a;
        y=b;
    }
}
