package game;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import animation.AbstractAnimation;

public class Asteroid{
	
    // The diameter of the ball, in pixels
    private static final int ASTERIOD_WIDTH = 40;
    private static final int ASTERIOD_HEIGHT= 60;
    private int XY[][] = {{-20, 0, 20, 30, 20, 30, 10, -10, -10, -30, -20, -30}, 
			{20, 10, 20, 10, 10, -10, -20, -10, -20, -10, 0, 10}};

    private double x;
    private double y;
    private double direction;

    // The number of pixels to move on each frame of the animation.
    private double moveAmount;
    private double xMoveAmount;
    private double yMoveAmount;

    // The animation that this object is part of.
    private AbstractAnimation animation;
    
    // The shape
    private Polygon asteroid;
    
    /**
     * Creates the animated object
     * 
     * @param animation the animation this object is part of
     */
    public Asteroid(int x, int y, double direction, int moveAmount, AbstractAnimation animation) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.moveAmount = moveAmount;
        xMoveAmount = (Math.sin(direction)*moveAmount) + 0.001;
        yMoveAmount = (Math.cos(direction)*moveAmount) + 0.001;
        this.animation = animation;
        asteroid = new Polygon();
        for (int i = 0; i < XY[0].length; i++) {
    		asteroid.addPoint(XY[0][i], XY[1][i]);
    	}
    }
    
    public double[] getPosition() {
    	double[] position = {x, y};
    	return position;
    }
    
    public double[] getMoveAmount() {
    	double[] move = {moveAmount, xMoveAmount, yMoveAmount};
    	return move;
    }
    
    public double getDirection() {
    	return direction;
    }
    
    public void paint(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.draw(getShape());
    }
		
    public void nextFrame() {

        x = x + xMoveAmount;
        y = y + yMoveAmount;

        if ((x - (int)(ASTERIOD_WIDTH/2)) > animation.getWidth()) {
        	x = 0;
        }
        else if ((x + (int)(ASTERIOD_WIDTH/2)) < 0) {
            x = animation.getWidth();
        }
        
        if ((y - (int)(ASTERIOD_HEIGHT/2)) > animation.getHeight()) {
        	y = 0;
        }
        else if ((y + (int)(ASTERIOD_HEIGHT/2)) < 0) {
            y = animation.getHeight();
        }
    }
    
    public Shape getShape() {
    	AffineTransform at1 = new AffineTransform();
        at1.translate(x, y);
        
        AffineTransform at = at1;
        return at.createTransformedShape(asteroid);
    }
}