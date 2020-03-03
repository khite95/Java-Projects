/**
 * GEdge class that stores the start and end nodes and the edge name
 * stores the lines that connect the nodes.
 * 
 * @author jb
 * Created 7/27/10
 * 
 * History:
 * 10/27/10 rdb Added a small sphere at the end of the edge along with
 *              multicolor lines.
 */
import java.awt.geom.*;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;

public class GEdge extends Edge
{
    //----------------------- Instance Variables ----------------------
    private Line      startLine;
    private Arrow     endLine;
    private Stroke    startStroke = new BasicStroke( 2 );
    private Stroke    endStroke = new BasicStroke( 2 );
    private Color     startColor = Color.blue;
    private Color     endColor = Color.red;
    private Color     drawStartColor;
    private Color     drawEndColor;
    private GNode     startNode;
    private GNode     endNode;
    private float     startLinePerCent = 0.85f;
    
    
    //++++++++++++++++++++++++++++ Constructors +++++++++++++++++++++++
    /**
     * Creates a new edge object.
     * The line itself needs to be created using the createLine method 
     * AFTER the nodes have been placed.
     */
    public GEdge( GNode s, GNode e )     
    {
        super( s, e );
        startNode = s;
        endNode = e;
        drawStartColor = startColor;
        drawEndColor = endColor;
    }
    
    //++++++++++++++++++++++++ Public Methods ++++++++++++++++++++++++++
    //-------------------------- getEndNode() -------------------------
    /**
     * Returns the node this edge ends at
     */
    public GNode getEndNode()
    {
        return endNode;
    }
    
    //-------------------------------- getStartNode() -----------------
    /**
     * Returns the node this edge starts at
     */
    public GNode getStartNode()
    {
        return startNode;
    }
    
    //-------------------------- setHighlighted() -------------------------
    /**
     * Sets visit status of the edge; the colors are darker when visited
     */
    public void setHighlighted( boolean v )
    {
        super.setHighlighted( v );
        if ( v )
        {
            drawStartColor = startColor.darker().darker();
            drawEndColor = endColor.darker().darker();
        }
        else
        {
            drawStartColor = startColor;
            drawEndColor   = endColor;
        }
    }
    
    //--------------------------- createLine() ------------------------
    /**
     * Create the line connecting the nodes specified in this edge
     * It creates two lines, first half of the line one color
     * the other half of the line the other color
     */
    public void createLine()
    {
        float t = startLinePerCent;  // parametric coord for color change
        int startX = startNode.getX();
        int endX = endNode.getX() + endNode.getWidth();
        int midX = (int) ( t * endX + ( 1.0f - t ) * startX );
 
        int startY = startNode.getY();
        int endY = endNode.getY() + endNode.getHeight();
        int midY = (int) ( t * endY + ( 1.0f - t ) * startY );

        startLine = new Line( startX, startY, midX, midY );
        startLine.setColor( startColor );
        endLine = new Arrow( midX, midY, endX, endY );
        endLine.setColor( endColor );
    }
    
    //-------------------------- draw( Graphics2D ) -------------------
    /**
     * Draws the two lines that compose this edge
     */
    public void draw( Graphics2D context )
    {
        if ( highlighted )
        {
            startLine.setColor( Color.BLACK );
            endLine.setColor( Color.BLACK );
            startLine.setLineWidth( 2 );
            endLine.setLineWidth( 2 );
        }
        else
        {
            startLine.setColor( startColor );
            endLine.setColor( endColor );
            startLine.setLineWidth( 1 );
            endLine.setLineWidth( 1 );
        }
        startLine.draw( context );
        endLine.draw( context );
        /**
         Stroke backupStroke = context.getStroke();
         Color backupColor = context.getColor();
         
         
         context.setStroke( startStroke );
         if ( visited )
         context.setColor( drawStartColor.darker().darker() );
         else
         context.setColor( drawStartColor );
         context.draw( startLine );
         
         context.setStroke( endStroke );
         if ( visited )
         context.setColor( drawEndColor.darker().darker() );
         else
         context.setColor( drawEndColor );
         context.draw( endLine );
         
         context.draw( endBlob );
         
         context.setStroke( backupStroke );
         context.setColor( backupColor );
         /**************/
    }
}
