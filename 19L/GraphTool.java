//++++++++++++++++++++++++++++++ GraphTool ++++++++++++++++++++++++++++
import javax.swing.*;
import java.awt.*;
import java.awt.BorderLayout;
import java.util.*;
import java.io.File;
/**
 * GraphTool:
 * -reads in a graph file specified as the only argument
 * -creates the graph from the file and displays it.
 * 
 * @author jb
 * Created 7/27/10
 * 
 * 04/20/15 (and in 2014 and earlier in 2015)
 *      rdb: added support for batch processing; including expanding 
 *           simple graph language with:
 *                path start end   # as alternative to 3 separate commands
 *                show             # to "finalize" the graph definition in
 *                                 # preparation for path commands. This was
 *                                 # needed to interface with interactive mode.
 */

public class GraphTool extends JPanel
{
    //----------------------- Instance Variables ----------------------
    private GraphPanel    drawing;
    private int           windowSize = 600;
    private double        theta;
    private GNode[]       gnodes;
    private int           maxX = 0;
    private int           maxY = 0;
    private GNode         currentNode;
    private GUI           gp;
    private int           radius;
    private Graph         graph;
    private GNode         startNode = null, endNode = null;
    private int           msDelay = 0;
    private boolean       pauseDuringSearch = false;
    private Path          curPath;
    private Iterator<GEdge> edgePathIterator;
    private boolean       graphShown = false;  // true if batch read had a
                                               // included a "show" command
    
    //++++++++++++++++++++++++++ Constructors +++++++++++++++++++++++++
    /**
     * Reads in the graph file and creates the display.
     * 
     * @param filename String       filename for first graph
     */
    public GraphTool( String filename )     
    {
        this();
        if ( GraphLab.interactive )
        {
            this.setLayout( new BorderLayout() );
            gp = new GUI( this );
            this.add( gp, BorderLayout.NORTH );
            
            drawing = new GraphPanel();
            this.add( drawing, BorderLayout.CENTER );
        }
        
        newGraph( filename );
    }
    /**
     * Batch version of GraphTool. Does not create the GUI, does not 
     *    try to draw things when convenient.
     */
    public GraphTool()
    {
        gp = null;
        drawing = null;
        graph = null;
    }
    //------------------------- newGraph( String ) --------------------
    /**
     * Open a new graph file.
     * 
     * @param fileName String     the new file.
     */
    public void newGraph( String fileName )
    {
        if ( drawing != null )
            drawing.clear();
        graphShown = false;
        openGraphFile( fileName );
        if ( graph == null ) // no graph read
            return;
        if ( !graphShown )   // if batch file didn't "show", do it now.
            showGraph();
    }
 
    //++++++++++++++++++++++++ Public Methods +++++++++++++++++++++++++
    
    //---------------------- newGraph() ------------------------------
    /**
     * Open a new graph file. Use JFileChooser utility.
     */
    public void newGraph()
    {
        String fileName = Utilities.getFileName( "Choose a graph file" );
        if ( fileName != null )
            newGraph( fileName );
    }
     //---------------------- findAPath() ------------------------------
    /**
     * Starts the recursive algorithm to find any path from start to end.
     * Start and end node must be defined.
     */
    public void findAPath()
    {
        if ( gnodes == null )
        {
            System.out.println( "Graph not built?" );
            return;
        }
        String msg = "No path found";
        unmarkGraph();
        
        if ( startNode != null && endNode != null )
        {
            curPath = graph.findPath( startNode, endNode );
            if ( curPath == null )
                msg = "No path found: " + startNode + " -> " + endNode;
            else
                msg = "Found path: length " + curPath.longString();
        }
        else
            curPath = null;
        report( msg );
        if ( GraphLab.interactive )
            showPath();
    }
    
    //----------------------- showPath() -------------------------------
    /**
     * Highlights the found path.
     */
    public void showPath()
    {
        if ( curPath != null )
        {
            unmarkGraph();
            markPath();
        }
        repaint();
    }
    
    /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     * The command pattern uses the following methods to create the graph.
     * The command pattern verifies everything so there is no checking that
     * has to be done here.
     */
    //----------------------- addNode( GNode ) -----------------------
    /**
     * Adds a node to the graph.
     * 
     * @param gn GNode
     */
    public void addNode( GNode gn )
    {
        graph.addNode( gn );
    }
    
    //---------------------------- getNode( String ) -------------------
    /**
     * Returns the node object with the id of the specified string.
     * 
     * @param id String
     * @return GNode
     */
    public GNode getNode( String id )
    {
        return (GNode) graph.getNode( id );
    }
    
    //----------------- getAddNode( String ) ---------------------------
    /**
     * If there is a node in the graph that matches the id, return it; 
     *     else create that node and return it.
     * @param nodeId String     desired node.
     * @return GNode
     */
    private GNode getAddNode( String nodeId )
    {
        GNode node = (GNode) graph.getNode( nodeId );
        if ( node == null )
        {
            node = new GNode( nodeId );
            if ( GraphLab.interactive )
                drawing.add( node.getComponent() );
            graph.addNode( node );
        }
        return node;
    }
    
    //-------------------------- nodeExists( String ) ---------------
    /**
     * Returns true if node with specified id exists, false otherwise.
     * 
     * @param id String
     * @return boolean
     */
    public boolean nodeExists( String id )
    {
        return graph.getNode( id ) != null;
    }
    
    //------------------------- setStartNode( GNode ) ------------------
    /**
     * Sets the start node.
     * @param gn GNode
     */
    public void setStartNode( GNode gn )
    {
        if ( startNode != null )
            startNode.setColor( Color.WHITE );
        startNode = gn;
        startNode.setColor( Color.GREEN );
        curPath = null;
    }
    
    //------------------------ setEndNode( GNode r ) ------------------
    /**
     * Sets the end node.
     * @param gn GNode
     */
    public void setEndNode( GNode gn )
    {
        if ( endNode != null )
            endNode.setColor( Color.WHITE );
        endNode = gn;
        endNode.setColor( new Color( 255, 128, 128 ) );
        curPath = null;
    }
    
    //--------------------------- markPath() --------------------------
    /**
     * set all edges on the path to visited; that is used to draw the
     * path edges in a different color.
     */
    private void markPath()
    {
        Iterator<GEdge> iter = makeEdgeIterator( curPath );
        while ( iter.hasNext() )
        {
            GEdge next = iter.next();
            if ( next != null )
                next.setHighlighted( true );
            else
                System.out.println( "Null edge in markPath iteration" );
        }
    }
    
    //--------------------- makeEdgeIterator( Path ) ------------------
    /**
     * Create a list of edges that need to be displayed.
     * 
     * @param path Path  
     * @return Iterator<GEdge> 
     */
    private Iterator<GEdge> makeEdgeIterator( Path path )
    {
        ArrayList<GEdge> edges = new ArrayList<GEdge>();
        ArrayList<Node>  nodes = path.getPath();
        if ( nodes.size() <= 1 )
            return edges.iterator();
        GNode start = (GNode)  nodes.get( 0 );
        for ( int i = 1; i < path.size(); i++ )
        {
            GNode end = (GNode) nodes.get( i );
            GEdge e = (GEdge) start.getEdge( end ); 
            if ( e != null )           
                edges.add( e );
            else
            {
                System.out.println( "Edge missing: " + start + " -> " + end );
            }
            start = end;
        }
        return edges.iterator();
    }
    //--------------------------- unmarkGraph() ------------------------
    /**
     * Resets the colors of all the edges and nodes and visited flags.
     */
    public void unmarkGraph()
    {
        int len = gnodes.length;
        for ( int i = 0; i < len; i++ )
        {
            GNode node = gnodes[ i ];
            node.setVisited( false );
            if ( GraphLab.interactive )
            {
                Collection<Edge> edges = node.getEdges();
                Iterator<Edge> iter = edges.iterator();
                
                while( iter.hasNext() )
                    ( (GEdge)iter.next() ).setHighlighted( false );
            }
        }
        if ( GraphLab.interactive )
        {
            drawing.setPath( null );
            this.repaint();
        }
    }
    //--------------------- defineStartEnd() --------------------------
    /**
     * Open a dialog box to define the start and end nodes.
     */
    public void defineStartEnd()
    {
        String prompt = "Enter start and stop nodes, separated by space";
        String input = JOptionPane.showInputDialog( null, prompt );
        if ( input == null || input.length() == 0 )
            return;
        Scanner scan = new Scanner( input );
        if ( scan.hasNext() )
        {
            String start = scan.next();
            if ( scan.hasNext() )
            {
                String end = scan.next();
                defineStartEnd( start, end );
            }
        }
        this.repaint();
    }
    //--------------- defineStartEnd( String, String) ------------------
    /**
     * Batch method for defining the start and end nodes.
     */
    public void defineStartEnd( String start, String end )
    {
        String noNodeMsg = "GNode does not exist: ";
        GNode node = getNode( start );
        if ( node == null )
            report( noNodeMsg + start );
        else
        {
            setStartNode( node );
            node = getNode( end );
            if ( node == null )
                report( noNodeMsg + end );
            else
                setEndNode( node );
        }
        curPath = null;
        unmarkGraph();
    }
    /******************************************************************/
    /**
     * Report results. For interactive execution, use a pop window;
     * for batch execution, write to System.out.
     *
     * @param msg String         Information to report.
     */
    private void report( String msg )
    {
        if ( GraphLab.interactive )
            JOptionPane.showMessageDialog( null, msg );
        else
            System.out.println( msg );
    }
    //------------------------ showGraph() -----------------------------
    /**
     * Print the graph while reading the graph file.
     */
    private void showGraph()
    {
        //store the references to the nodes into an array
        gnodes = new GNode[ graph.size() ];
        graph.getNodes().toArray( gnodes );
        
        if ( GraphLab.interactive )
        {
            calcTheta();
            setNodeLocations();
            drawNodeEdges();
        }
        System.out.println( "-----------" + graph.size() + " ----------" );
        System.out.println( graph.longString() );
        System.out.println( "------------------------------------" );
        graphShown = true;
    }
    //++++++++++++++++++++++++++ Private Methods ++++++++++++++++++++++
    
    //------------------------ parseLine ------------------------------
    /**
     * parse a line of input from the graph file. Lines are of the form:
     *    edge node1 node2.
     * 
     * where node1 and node2 are arbitrary Strings (with no spaces) that 
     *       represent a node in graph. If either node id has not yet been
     *       encountered create a Node object for it and add it to the Graph
     *       object.
     * Create an Edge from node1 to node2 and add it to the list of edges
     *    in the node1 object.
     * Print an error message for lines that don't start with "edge" or
     *    don't have 2 node fields; you can ignore extra characters after
     *    first 3 tokens on the line.
     * @param line String     input line to be parsed
     */
    private void parseLine( String line )
    {
        if ( line == null || line.trim().length() == 0 )
            return;
        String[] tokens = line.split( " +" );
        String cmd = tokens[ 0 ];
        if ( cmd.equals( "edge" ) )
        {
            if ( tokens.length < 3 )
                System.err.println( "Error: edge needs 2 args : " + line );
            else
            {
                GNode n1 = getAddNode( tokens[ 1 ] );
                for ( int r = 2; r < tokens.length; r++ )
                {
                    GNode n2 = getAddNode( tokens[ r ] );
                    GEdge edge = new GEdge( n1, n2 );
                    graph.addEdge( edge );
                    if ( GraphLab.interactive )
                        drawing.addEdge( edge );
                }
            }
        }
        else if ( cmd.equals( "show" ) )
        {
            showGraph(); // graph nodes defined; now can do path etc. commandsk
        }
        else if ( cmd.equals( "node" ) )
        {
            for ( int n = 1; n < tokens.length; n++ )
                 getAddNode( tokens[ n ] );
        }
        else if ( cmd.equals( "start" ) )
        {
            if ( tokens.length < 2 )
                System.err.println( "Error: need an argument: " + line );
            else
                setStartNode( getAddNode( tokens[ 1 ] ) );
        }
        else if ( cmd.equals( "end" ) )
        {
            if ( tokens.length < 2 )
                System.err.println( "Error: need an argument: " + line );
            else
                setEndNode( getAddNode( tokens[ 1 ] ) );
        }
        else if ( cmd.equals( "findPath" ) )
        {
            findAPath();
        }
        else if ( cmd.equals( "path" ) )
        {
            if ( tokens.length < 3 )
                System.err.println( "Error: needs start/end arguments: " + line );
            else
            {
                setStartNode( getAddNode( tokens[ 1 ] ) );
                setEndNode( getAddNode( tokens[ 2 ] ) );
                if ( !GraphLab.interactive )  // if in batch, find the path
                    findAPath();
            }
        }
        else 
            System.err.println( "Error: not a valid command: " + line );      
    }
    //------------------ openGraphFile( String filename ) --------------
    /**
     * Opens a new graph file and resets all the objects.
     * 
     * @param filename String
     */
    private void openGraphFile( String filename )
    {
        System.out.println( "\n*** Reading ------>" + filename );
        Scanner reader;
        
        try
        {
            reader = new Scanner( new File( filename ) );
            graph = new Graph();
            while ( reader.hasNextLine() )
            {
                String line = reader.nextLine().trim();
                if ( line.length() > 0 && line.charAt( 0 ) != '#' )
                    parseLine( line );
            }
        }
        catch ( java.io.FileNotFoundException e )
        {
            System.out.println( e );
            System.out.println( "*** Graph not changed ***" );
        }
    }
    
    /*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     * The following 3 methods display the nodes in a circle and 
     * connect the edges and then resize the display
     */
    
    //-------------------------- calcTheta() ---------------------------
    /**
     * Calculates the theta that separates each node on the display.
     */
    private void calcTheta()
    {
        radius = windowSize / 2;
        theta  = 2 * Math.PI / graph.size();
    }
    
    //------------------------ setNodeLocations() ----------------------
    /**
     * From the theta, calulates the coordinates of all the node.
     */
    private void setNodeLocations()
    {
        int len = gnodes.length;
        for ( int i = 0; i < len; i++ )
        {
            GNode gnode = gnodes[ i ];
            JComponent nc = gnode.getComponent();
            drawing.add( nc );
            int x = (int)( Math.cos( theta * ( i ) ) * radius + radius );
            int y = (int)( Math.sin( theta * ( i ) ) * radius + radius );
            nc.setSize( nc.getPreferredSize() );
            
            if ( x + nc.getWidth() > maxX )
                maxX = x + nc.getWidth();
            if ( y + nc.getHeight() > maxY )
                maxY =  y + nc.getHeight();
            
            nc.setLocation( x, y );
        }
        int extra = 40;
        Insets insets = this.getInsets();
        this.setSize( maxX + insets.left + insets.right + extra, 
                     maxY + insets.top + insets.bottom + 
                     gp.getHeight() + extra );
        this.repaint();
    }
    
    //------------------------ drawNodeEdges() -------------------------
    /**
     * Sets the location of the edges and draws the edges between nodes,
     * or more accurately, adds the edges to the panel, which draws them.
     */
    private void drawNodeEdges()
    {
        int len = gnodes.length;
        for ( int i = 0; i < len; i++ )
        {
            GNode r = gnodes[i];
            Iterator<Edge> edges  = r.getEdges().iterator();
            
            while( edges.hasNext() )
            {
                GEdge a = (GEdge)edges.next();
                a.createLine();
                drawing.addEdge( a );
            }         
        }
        this.repaint();
    }
    
    //+++++++++++++++++++++++ main +++++++++++++++++++++++++++++++++++++
    /**
     * Batch invocation.
     */
    public static void main( String [ ] args ) 
    {
        String graphFile = "a_graph0.txt";
        if ( args.length > 0 )
            graphFile = args[ 0 ];
        GraphLab.interactive = false;
        
        GraphTool tool = new GraphTool();
        tool.newGraph( graphFile );
    }
}
