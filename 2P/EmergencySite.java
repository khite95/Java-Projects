import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.reflect.Array;
import java.util.*;
/**
 * EmergencySite -- represents an emergency site. 
 *   Dragging (mousePressed followed by mouseDragged) repositions it
 *     Not allowed to drag if its the current EmergencySite, easiest
 *     way to do that is make that site not draggable
 *  MouseClicked -- if clicked, this site is no longer an emergency 
 *     site; remove it from the set of sites or somehow make it known
 *     that it is not real emergency site any more.
 * 
 * @author Kenneth Hite
 * 06/10/16 CS416
 */
public class EmergencySite extends JRectangle
    implements MouseListener, MouseMotionListener, Draggable
{
    //--------------------- class variables -------------------------
    private int    size = 10;
    
    //--------------------- instance variables ----------------------
    private boolean     _visited = false;
    
    //--------------------- constants -------------------------------
    private Color  fillColor = Color.RED;
    private Color  lineColor = Color.BLACK;
    private ArrayList<EmergencySite> emSites;
    private Dispatcher dispatcher;
    private boolean last;
    private boolean disableDrag;
    
    //---------------------- constructors ---------------------------
    /**
     * create site.
     * @param p Point
     */
    public EmergencySite( Point p )
    {
        super( p.x, p.y );
        setSize( size, size );
        last = false;
        disableDrag = false;
        setFillColor( fillColor );
        setFrameColor( lineColor );
        addMouseListener( this );
        addMouseMotionListener( this );
    }
    private boolean   _draggable = true; 
    /** 
      * set draggable.
      * @param onOff boolean
      */
    public void setDraggable( boolean onOff )
    {
        _draggable = onOff;
    }
    /** 
      * countains method.
      * @param point java.awt.geom.Point2D;
      * @return getBounds.Countains(Point) boolean 
      */
    public boolean contains( java.awt.geom.Point2D point )
    {
        return getBounds().contains( point );
    }
    private Point _saveMouse;
    /** 
      * mouse pressed.
      * @param me MouseEvent
      */
    public void mousePressed( MouseEvent me )
    {
        _saveMouse = getParent().getMousePosition();
    }
    private EMTPanel em2;
    //---------------- lastSet ----------------------------------
    /** 
      * last setter.
      * @param t boolean
      */
    public void lastSet( boolean t )
    {
        last = t;
    }
    //-------------- mouseClicked -----------------------------------
    //---------------- mouseClicked ----------------------------------
    /** 
      * mouse clicked.
      * @param me MouseEvent
      */
    public void mouseClicked( MouseEvent me )
    {
        //////////////////////////////////////////////////////////////
        // On mouse click, this site should be removed from the
        //    sites array.
        // Figure out a way for this class to 
        //    - know about the sites array (so it can remove itself), OR
        //    - for this class to be able to call somebody that it 
        //      knows about who knows about the sites array, OR
        //    - to put information into this object so that some other 
        //      object will know this site is no longer a real site
        //      and should not be visited by the emt vehicle.
        //////////////////////////////////////////////////////////////
        if( last == false ) 
        {
            emSites.remove( this );
        }
        this.setColor( Color.BLUE );
    }
    /** 
      * emSites setter.
      * @param e ArrayList<EmergencySite> e
      */
    public void setList( ArrayList<EmergencySite> e )
    {
        emSites = e;
    }
    /** 
      * SetArray.
      * @param site ArrayList<EmergencySite>
      */
    public void setArray( ArrayList<EmergencySite> site )
    {
        emSites = site;
    }
    //--------------- unimplemented mouse emSitesener methods -----------
    //---------------- mouseReleased ----------------------------------
    /** 
      * Mouse Released.
      * @param me MouseEvent
      */
    public void mouseReleased( MouseEvent me )
    {
    }
    
    //---------------- mouseEntered ----------------------------------
    /** 
      * Mouse Entered.
      * @param me MouseEvent
      */
    public void mouseEntered( MouseEvent me )
    {
    }
    
    //---------------- mouseExited ----------------------------------
    /** 
      * mouse exited.
      * @param me MouseEvent
      */
    public void mouseExited( MouseEvent me )
    {
    }
    
    //---------------- setNoDrag ----------------------------------
    /** 
      * set no drag.
      * @param t boolean
      */
    public void setNoDrag ( boolean t )
    {
        disableDrag = t;
    }
    
    //+++++++++++++++++++ mouseMotionListener methods ++++++++++++++++
    //---------------- mouseDragged ----------------------------------
    /** 
      * mouse dragged.
      * @param me MouseEvent
      */
    public void mouseDragged( MouseEvent me )
    {
        if( disableDrag == false ) 
        {
            if ( last == false ) 
            {
                boolean noDrag = false;
                if ( emSites.size() > 0 ) 
                {
                    if ( emSites.get( 0 ) == this ) 
                    {
                        noDrag = true;
                    }
                }
                if ( dispatcher.getState() != 0 && noDrag == true ) 
                {
                    
                } 
                else 
                {
                    int dY = getParent().getMousePosition().y - _saveMouse.y;
                    int dX = getParent().getMousePosition().x - _saveMouse.x;
                    this.moveBy( dX, dY );
                    _saveMouse = getParent().getMousePosition();
                    getParent().repaint();
                }
            } 
            else if ( last == true ) 
            {
                int dY = getParent().getMousePosition().y - _saveMouse.y;
                int dX = getParent().getMousePosition().x - _saveMouse.x;
                this.moveBy( dX, dY );
                _saveMouse = getParent().getMousePosition();
                getParent().repaint();
            }
            
        }
    }
     /** 
      * is draggable.
      * @return _draggable boolean
      */
    public boolean isDraggable()
    {
        return _draggable;
    }
    /** 
      * mouse moved.
      * @param me MouseEvent
      */
    public void mouseMoved( MouseEvent me )
    {
    }
    /** 
      * dispatch.
      * @param d Dispatcher
      */
    public void dispatch( Dispatcher d )
    {
        dispatcher = d;
    }
    //--------------------- main -----------------------------------
    /** 
      * main.
      * @param args String[ ]
      */
    public static void main( String[] args )
    {     
        JFrame testFrame = new JFrame();
        testFrame.setSize( 700, 500 );  // define window size
        
        testFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        JPanel testPanel = new JPanel( ( LayoutManager ) null );
        testFrame.add( testPanel );
        
        EmergencySite s1 = new EmergencySite( new Point( 200, 200 ) );
        s1.lastSet( true );
        testPanel.add( s1 );
        
        EmergencySite s2 = new EmergencySite( new Point( 200, 100 ) );
        s2.lastSet( true );
        testPanel.add( s2 );
        
        testFrame.setVisible( true );       
    }
}