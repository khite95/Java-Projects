//+++++++++++++++++++++++++++++++ GraphPanel ++++++++++++++++++++++++++
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
/**
 * Basic class that draws the lines that connect the nodes; these are the
 * direct paths between the nodes.
 * 
 * @author jb
 * Created 7/27/10
 */

public class GraphPanel extends JPanel
{
    //---------------------- instance variables ----------------------
    private ArrayList<GEdge> edges;
    private Path                  curPath;
    
    //+++++++++++++++++++ Constructors ++++++++++++++++++++++++++++++++
    /**
     * Creates a new edge panel.
     */
    public GraphPanel(  )     
    {
        super();
        setLayout( null );
        edges = new ArrayList<GEdge>();
        curPath = null;
    }
    //+++++++++++++++++++ Public Methods ++++++++++++++++++++++++++++++
    
    //----------------- paintComponent ( Graphics ) -------------------
    /**
     * Calls a method that draws the edges.
     * @param context Graphics
     */
    public void paintComponent ( Graphics context )
    {
        super.paintComponent( context );
        Graphics2D context2D = (Graphics2D) context;
        drawEdges( context2D );
        drawPath( context2D );
    }
    
    //--------------------------- addEdge( Edge ) ---------------------
    /**
     * Adds an edge to be drawn.
     * 
     * @param e Edge              Edge to be added
     */
    public void addEdge( Edge e )
    {
        edges.add( (GEdge) e );
    }
    
    //--------------------------- clear( ) -----------------------
    /**
     * Clear all graphical components and the edges array list.
     */
    public void clear()
    {
        edges.clear();       
        this.removeAll();
    }
    
    //------------------------- setPath( Path ) -----------------------
    /**
     * Defines the edge to be drawn if there is one; null is valid.
     * 
     * @param p Path
     */
    public void setPath( Path p )
    {
        curPath = p;
    }
  
    //+++++++++++++++++++++ Private Methods ++++++++++++++++++++++++++++
    
    //------------------- drawPaths( Graphics2D context) --------------
    /**
     * Draws the paths.
     * @param context 
     */
    private void drawEdges( Graphics2D context )
    {
        for ( GEdge e: edges )
        {
            e.draw( context );
        }
    }
    //------------------- drawPath( Graphics2D context) --------------
    /**
     * Draws the edge if there is one.
     * 
     * @param context Graphics2D
     */
    private void drawPath( Graphics2D context )
    {
        GEdge edge;
        if ( curPath != null && curPath.size() > 1 )
        {         
            ArrayList<Node> nodes = curPath.getPath();
            GNode start = (GNode) nodes.get( 0 );
            for ( int i = 1; i < curPath.size(); i++ )
            {
                GNode end = (GNode) nodes.get( i );
                edge = (GEdge) start.getEdge( end );
                edge.setHighlighted( true );
                edge.draw( context );
                edge.setHighlighted( false );
                start = end;
            }
        }        
    }   
}