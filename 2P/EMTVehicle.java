import java.awt.*;
import javax.swing.*;
import java.util.*;
/**
 * EMTVehicle.java:
 *   
 * A very simple representation of an emt vehicle going to emergency
 * sites and hospitals.
 * 
 * This class is responsible for:
 *   1. remembering "home" position; one specified at creation time.
 *   2. having a public interface method that allows the dispatcher
 *      to tell it where to go next.
 *   3. being able to move itself by steps to that "goal" position
 *      at some specified speed. 
 *   4. given a goal position and a speed,   
 *       -computing the number of frames it will take to get there
 *       -computing the floating point incremental x and y
 *        changes needed for all but the last step
 *       -maintaining a floating point representation for the
 *        "precisely correct" position for each step.
 *       -converting the "correct" position to the closest
 *        pixel (int) location at each step; this means using
 *        (int)Math.round( floatX or floatY ), not just casting
 *        the float values to int.
 *   5. Stopping when it gets to the goal position and having some
 *      way that the dispatcher can find out that it got to its
 *      goal location.
 *   6. Having a public interface method that allows the dispatcher
 *      to get its home position or to tell it to go to its home
 *      position.
 * 
 * 
 * @author Kenneth Hite
 * 06/10/16 CS416
 */
public class EMTVehicle extends JComponent implements Animated
{ 
    //---------------------- instance variables ---------------------
    private int     _goalX;     // if moving, this is target location
    private int     _goalY;
    private int     _speed;     // current speed
    private double  _locX;
    private double  _locY;
    private double  _stepX;
    private double  _stepY;
    private int     _nSteps;
    
    private int     _homeX;
    private int     _homeY;
    
    // EMTVehicle display variables
    private JRectangle body;
    private int bodyW = 40, bodyH = 20;
    private int bodyX = 0;
    private int bodyY = 0;
    
    private int wheelY = 15;
    private int wheelDiam = 10;
    private JEllipse wheel1;
    private int wheel1X = 5;
    
    private JEllipse wheel2;
    private int wheel2X = 25;  
    private boolean traveled;
    /**
     * Main constructor.
     * @param x  int x-coord of location
     * @param y  int y-coord of location
     * @param bodyColor  main vehicle color
     */
    public EMTVehicle ( int x, int y, Color bodyColor ) 
    {
        _homeX = x;
        _homeY = y;
        wheel1 = new JEllipse( Color.BLACK );
        wheel1.setSize( wheelDiam, wheelDiam );
        wheel1.setLocation( wheel1X , wheelY  );
        this.add( wheel1 );
        wheel2 = new JEllipse( Color.black );
        wheel2.setLocation( wheel2X , wheelY );
        wheel2.setSize( wheelDiam , wheelDiam );
        this.add( wheel2 );
        body = new JRectangle( bodyColor );
        body.setSize( bodyW , bodyH );
        body.setLocation( bodyX, bodyY );
        this.add( body );
        setLocation( _homeX, _homeY );
        traveled = false;
    }
     /** Another constructor.
     * override add method to compute and set bounds information as 
     * components are added to the object.
     * @param loc Point.
     * @param col Color.
     */
    public EMTVehicle ( Point loc, Color col ) 
    {  
        this( loc.x, loc.y, col );
    }

    private Rectangle  _bounds = null;   
    /**
     * override add method to compute and set bounds information as.
     * components are added to the object.
     * @param comp JComponent  component to be added
     */
    public void add( JComponent comp )
    {
        super.add( comp );
        if ( _bounds == null )
            _bounds = new Rectangle( comp.getBounds() );
        else
            _bounds = _bounds.union( comp.getBounds() );
        super.setBounds( _bounds ); 
    }
    /**
     * travel to the specified location at the specified speed.
     * @param x int  x-pos of target location
     * @param y int  y-pos of target location
     * @param speed int  steps per frame to move
     */
    public void travelTo( int x, int y, int speed )
    {
        //////////////////////////////////////////////////////////////
        // Enable animation (setAnimated method)
        // compute distance from cur location to target
        // divide distance by speed to get n, # of "complete" steps
        // compute dx, dy (as float or double): 
        //                          step to take at each frame
        //  Hint: vehicle must travel from curX to goalX in n steps,
        //          so dx is ( goalX - curX ) / n; same for dy
        // define instance variables for cur location as float|double
        // save all this information in instance variables, to be used
        //     in newFrame method.
        //////////////////////////////////////////////////////////////
        traveled = false;
        _locX = this.getX();
        _locY = this.getY();
        _speed = speed;
        _goalX = x;
        _goalY = y;
        double distanceX = _goalX - _locX;
        double distanceY = _goalY - _locY;
        double disX = distanceX * distanceX;
        double disY = distanceY * distanceY;
        double disH = disX + disY;
        double dist = Math.sqrt( disH );
        float d1 = (float) dist;
        int rounded = Math.round( d1 );
        int steps = (int) d1 / speed;
        double dx = distanceX / steps;
        double dy = distanceY / steps;
        int estimated = (int) dx;
        _nSteps = steps;
        _stepX = dx;
        _stepY = dy;

    }
   /**
     * invoked for each frame of animation.
     * update the position of the vehicle; check if it has reached the
     * goal position -- if it has, turn off animation.
     */
    public void newFrame() 
    {
        if ( !_animated )
        {
            return;
        }
        if( _nSteps > 0 ) 
        {
            this.setLocation( this.getX() + (int) _stepX, this.getY() + 
                             (int) _stepY );  
            _nSteps = _nSteps - 1;
        }
        if( _nSteps == 0 )
        {
            traveled = true;
     
            _nSteps = -1;
        }
        this.repaint();
    } 
    /** 
     * isAnimated.
     * @return _animated boolean
     */
    public boolean isAnimated()
    {
        return _animated;
    }
    /** 
     * target reached.
     * @return traveled boolean
     */
    public boolean isTargetReached()
    {
        return traveled;
    }
    /** 
     * set Animate.
     * 
     * @param onOff boolean
     */
    public void setAnimated( boolean onOff )
    {
        _animated = onOff;
    }
    /** 
     * reset.
     */
    public void traveledReset()
    {
        traveled = false;
    }
    private boolean _animated = false;
    /**
     * main.
     * 
     * @param args String[ ]
     */
    public static void main( String[] args )
    {     
        JFrame testFrame = new JFrame();
        testFrame.setSize( 700, 500 );  // define window size
        
        testFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        JPanel testPanel = new JPanel( (LayoutManager) null );
        testFrame.add( testPanel );
        EMTVehicle d2 = new EMTVehicle( new Point( 300, 300 ),
                                        Color.BLUE );
        d2.travelTo( 300 , 300 , 10 );
        d2.setAnimated( true );
        testPanel.add( d2 );
        
        testFrame.setVisible( true ); 
    }
}
