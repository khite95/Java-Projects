/**
 * GNode class that stores whether it has been visited and the edges
 * that start at this node
 * 
 * @author jb
 * Created 7/27/10
 */
import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;


public class GNode extends Node 
{
    //----------------------- Instance Variables ----------------------
    private JLabel        nodeLabel;
    
    private boolean       isVisited;
    private Color         color;
    
    //+++++++++++++++++++++++++++ Constructors ++++++++++++++++++++++++
    /**
     * Creates a new node with the specified name
     * Sets the background as white
     */
    public GNode( String n )     
    {
        super( n );
        String label = n;
        while( label.length() < 5 )
            label = " " + label + " ";
        nodeLabel = new JLabel( label );
        nodeLabel.setSize( 30, 10 );
        nodeLabel.setOpaque( true );
        nodeLabel.setBorder( new LineBorder( Color.BLACK, 1 ) );
        nodeLabel.setHorizontalTextPosition( SwingConstants.CENTER );
        setColor( Color.white );
        
        setVisited( false );
    }
    
    //+++++++++++++++++++++++++++ Public Methods ++++++++++++++++++++++
    
    //------------------------- setVisited( boolean ) -----------------
    /**
     * Sets the node as visited
     */
    public void setVisited( boolean flag )
    {
        super.setVisited( flag );
        //setBorder( visitedBorder );
        if ( flag )
            nodeLabel.setBackground( Color.YELLOW );
        else
            nodeLabel.setBackground( color );
        nodeLabel.repaint();
    }
    
    //---------------------- setColor( Color ) ------------------------
    /**
     * Sets the background color of the label
     */
    public void setColor( Color color )
    {
        this.color = color;
        nodeLabel.setBackground( color );
        nodeLabel.repaint();
    }
    
    //++++++++++++++ JLabel accessor methods to pass through ++++++++++
    //----------------------- getX() ----------------------------------
    int getX() 
    {
        return nodeLabel.getX();
    }
    //----------------------- getY() ----------------------------------
    int getY() 
    {
        return nodeLabel.getY();
    }
    //----------------------- getHeight() -----------------------------
    int getHeight() 
    {
        return nodeLabel.getHeight();
    }
    //----------------------- getWidth() ------------------------------
    int getWidth() 
    {
        return nodeLabel.getWidth();
    }
    //----------------------------- getComponent() --------------------
    /**
     * return the graphical component representing this GNode (a JLabel)
     */
    public JComponent getComponent()
    {
        return nodeLabel;
    }
}
