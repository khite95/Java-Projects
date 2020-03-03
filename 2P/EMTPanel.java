import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
/** 
 * EMTPanel.java: Swing panel for EMT motion assignment.
 *     This class is responsible for 
 *     1. Creating and storing the hospitals (already finished).
 *     2. Creating and storing the emergency sites; the sites are 
 *        created in the JPanel, in response to a MousePressed event
 *        at the site location.
 *     3. Creating the EMTVehicle that must do the traversal
 *     4. Creating the line object that shows where the vehicle
 *        is going. It might be responsible for updating this line;
 *        or that responsibility could be delegated to another class.
 *      
 * @author Kenneth Hite
 * 06/10/16 CS416
 */

public class EMTPanel extends JPanel implements Animated, MouseListener
{ 
    //-------------------- instance variables ----------------------
    private JFrame               _frame;   
    private ArrayList<Hospital>  _hospitals;
    private ArrayList<EmergencySite> emSite;
    private EMTVehicle j;
    private Dispatcher _dispatch;
    private FrameTimer timer;
    //--------------- constants -------------------------------------
    private Color[] hospColors = { Color.WHITE, Color.RED, Color.PINK };
    private int[]   hospitalX = { 50, 500, 500, 250, 300 };
    private int[]   hospitalY = { 400, 100, 350, 400, 50 };
    private int     hospitalW = 80;
    private int     hospitalH = 60;
    //------------- Constructor ---------------------------------
    /**
     * Create and manage an EMTVehicle using Swing.
     * Create the hospital objects
     * 
     * @param frame JFrame  
     */
    public EMTPanel( JFrame frame ) 
    {
        super();
        _frame = frame;
        this.setLayout( null );
        this.addMouseListener( this );
        this.setBackground( Color.LIGHT_GRAY );
        Color emtColor = new Color( 0, 255, 0, 128 ); 
        EMTApp.numHospitals = Math.min( EMTApp.numHospitals, 
                                       hospitalX.length );
        JLine l1 = new JLine( Color.RED );
        l1.setVisible( false );
        this.add( l1 );
        j = new EMTVehicle( 10, 10, Color.RED );
        this.add( j );
        emSite = new ArrayList<EmergencySite>();
        Point p1 = new Point( 300 , 300 );
        makeHospitals();
        timer = new FrameTimer( 100, this );
        timer.setInitialDelay( 2000 );
        timer.start();
        _dispatch = new Dispatcher( _hospitals, timer,  emSite ,  j , l1 );
        _dispatch.setAnimated( true );
        
    } 
    private boolean _animated = true;
   /**
     * move emt for each new frame; this is called by the FrameTimer 
     * listener; it  gets invoked when the time interval elapses and awt 
     * creates an event.
     */
    public void newFrame()
    {
        //////////////////////////////////////////////////////////////////
        // Your Dispatcher objects newFrame needs to be called; 
        //   depending on your design decisions, other things might
        //   also be done here.
        //////////////////////////////////////////////////////////////////
        _dispatch.newFrame();
        repaint();
    }
    /**
     * setAnimated.
     * @param onOff boolean
     */
    public void setAnimated( boolean onOff )
    {
        
    }
    // You need to implement mousePressed; the others must be there but
    //   will remain "empty".
    //
    /**
     * On mousePressed, replace current site with a site at mouse position.
     *
     * @param me  MouseEvent  event data
     */
    public void mousePressed( MouseEvent me )
    {      
        ////////////////////////////////////////////////////////////////
        // Need to add a new Emergency site here
        ////////////////////////////////////////////////////////////////
        EmergencySite e1 = new EmergencySite( me.getPoint() );
        e1.setArray( emSite );
        e1.dispatch( _dispatch );
        emSite.add( e1 );
        this.add( e1 );
        repaint();       
    }
    /** 
      * Call AWT object display methods.
      * 
      * @param g Graphics  graphics context
      */
    public void paintComponent( Graphics g )
    {
        super.paintComponent( g );
        for ( Hospital h: _hospitals )
            h.display( ( Graphics2D ) g );
    }
    /** 
      * mouse.
      * 
      * @param me MouseEvent
      */
    public void mouseDragged( MouseEvent me )
    { 
        
    }
    /** 
      * mouse.
      * 
      * @param me MouseEvent 
      */
    public void mouseClicked( MouseEvent me ) 
    {
        
    }
    /** 
      * mouse.
      * 
      * @param me MouseEvent 
      */
    public void mouseEntered( MouseEvent me ) 
    {
        
    }
    /** 
      * mouse.
      * 
      * @param me MouseEvent 
      */
    public void mouseExited( MouseEvent me ) 
    {
        
    }
    /** 
      * mouse.
      * 
      * @param me MouseEvent 
      */
    public void mouseMoved( MouseEvent me ) 
    {
        
    }
    /** 
      * mouse.
      * 
      * @param me MouseEvent 
      */
    public void mouseReleased( MouseEvent me )
    {
        
    }
   /**
     * create the desired number of hospitals
     * This method is complete.
     */
    private void makeHospitals()
    {
        _hospitals = new ArrayList<Hospital>();
        for ( int s = 0; s < EMTApp.numHospitals; s++ )
        {
            int hx = hospitalX[ s ];
            int hy = hospitalY[ s ];
            Color hColor = hospColors[ s % hospColors.length ];
            Hospital h = new Hospital( hColor, hx, hy, hospitalW, hospitalH );
            _hospitals.add( h );
        }
    }
   /**
     * isAnimated.
     * @return _animated boolean
     */
    public boolean isAnimated()
    {
        return _animated; // can't turn off animation
    }
    /** 
      * main.
      * 
      * @param args String[ ]  
      */
    public static void main( String[] args )
    {
        EMTApp.main( args );
    }
}
