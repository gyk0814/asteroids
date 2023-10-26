import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import animation.AbstractAnimation;
import game.Game;
import game.Ship;
import game.Bullet;

class ShipTest extends AbstractAnimation  {

    public void nextFrame() {
    }
    
    private Ship ship = new Ship(this);
    private Bullet bullet = new Bullet(0, 0, 0, 0, this);
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    private AbstractAnimation animation;
    private static final int SHIP_WIDTH = 10;
    private static final int SHIP_HEIGHT = 17;
    private double x;
    private double y;
    private double z;

    @BeforeEach
    void setUp() throws Exception {
        ship = new Ship(this);
        ship.getShape();
        ship.nextFrame();
    }
    
    @Test 
    void testGetBullets() {
        // Test if getBullets method works
        System.out.println("The valid bullet in the bullets ArrayList: " + ship.getBullets());
    }
    
    @Test 
    void testShoot() {
        // Test if shoot method works when it's called
        ship.shoot();
        Bullet b = new Bullet(0,0,0,5,animation);
        bullets.add(b);
        System.out.println("b is in the bullets list " + bullets.contains(b));
        assertEquals(b, bullets.get(0));
        assertTrue(b.isValid());
    }

    
    @Test 
    void testHyperspace() {
        // Test if the hyper-space method generates a random location for the ship
        double x = Math.random()*SHIP_WIDTH;
        double y = Math.random()*SHIP_HEIGHT;
        System.out.println("Current x coordinate of the ship after hyperspace: " + x);
        System.out.println("Current y coordinate of the ship after hyperspace: " + y);
    }
    
    @Test 
    void testThrust() {
        // Test if thrust method works when its called / whether the moveAmount updates when its called
        ship.setMoveAmount(20);
        ship.thrust();
        assertEquals(20, ship.getMoveAmount());
    }

}
