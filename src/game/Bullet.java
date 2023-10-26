package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import animation.AbstractAnimation;

public class Bullet extends AbstractAnimation{
    
    // The diameter of the ball, in pixels
    private static final int BULLET_SIZE = 6;

    private double x;
    private double y;
    private double direction;
    private boolean valid = true; 

    private double moveAmount;

    private AbstractAnimation animation;
    
    private Ellipse2D bullet;
    
    /**
     * creates a dot as a bullet
     * @param x
     * @param y
     * @param direction
     * @param moveAmount
     * @param animation
     */
    public Bullet(double x, double y, double direction, double moveAmount, AbstractAnimation animation) {
        //adjustments to be created at the top of the ship
        this.x = x-2.5+8*Math.sin(direction);
        this.y = y-2.5+8*Math.cos(direction)*-1;
      
        this.direction = direction;
        this.moveAmount = moveAmount;
        this.animation = animation;
        bullet = new Ellipse2D.Double(x, y, BULLET_SIZE, BULLET_SIZE);
    }
    
    /**
     * generate animation by updating the positions in every frame
     */
    public void nextFrame() {
        //if valid bullet, keeps moving
        if(valid) {
            y += moveAmount*Math.cos(direction)*-1;
            x += moveAmount*Math.sin(direction);
        }
        //if goes out of the screen, invalid
        if(x<0 || y<0 ||x>animation.getWidth() || y>animation.getHeight()) {
            valid = false;
        }
        bullet.setFrame(x, y, BULLET_SIZE, BULLET_SIZE);
        
    }
    /**
     * return true if the bullet is valid
     */
    public boolean isValid() {
        return valid;
    }
    public void setValid(boolean b) {
        valid = b;
    }
    /**
     * paint the bullet in pink color
     * @param g
     */
    public void paint(Graphics2D g) {
        g.setColor(Color.PINK);
        g.fill(bullet);
    }
    /**
     * get the shape (bounding box) of a bullet
     * @return
     */
    public Shape getShape() {
        return bullet.getBounds2D();
    }

}
