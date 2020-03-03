//+++++++++++++++++++++++++++++++ Graph.java +++++++++++++++++++++++++++
import java.util.*;
import javax.swing.*;

/**
 * Graph - a class that encapsulates the basic functionality of
 * a directed graph.
 * 
 * @author rdb
 * 10/31/10 - derived from code created by Jonathan Brown for the 
 *            Dungeon program.
 * 04/01/14 rdb: modifications for graph-oriented lab
 */

public class Graph
{
    //----------------- class variables -------------------------------
    //----------------- instance variables ----------------------------
    private HashMap<String, Node>    graph;
    private ArrayList<Node>         nodes;
    private Collection<Edge>         edges;
    
    //----------------------- constructor -----------------------------
    /**
     * No argument constructor.
     */
    public Graph()
    {
        graph = new HashMap<String, Node>();
        nodes = new ArrayList<Node>();
        edges = new ArrayList<Edge>();
    }
    //------------------ findPath( Node, Node ) ----------------------
    /**
     * Find and return a path from Node s to Node e. If no path exists
     *    return null.
     * @param s  Node     start node
     * @param e  Node     end node
     * @return Path       path from start to end or null
     */
    public Path findPath( Node s, Node e )
    {
        Path path = null;
        //////////////////////////////////////////////////////////////
        // You have 2 options for implementing your findPath 
        // algorithm:
        // 1. You can build the path on the way "up" the recursion.
        // 2. You can build the path on the way "down" the recursion.
        //------------------------------------------------------------
        // Implement just ONE of these options. 
        // For option 1, uncomment the next line:
        
        path = findAPath( s, e );
        
        // For option 2, uncomment the next 2 lines:
        
        //path = new Path();
        //findAPath( path, s, e );
    
        return path;        
    }
    //------------------ findAPath( Node, Node ) ----------------------
    /**
     * Find a path from Node s to Node e building the path on the
     *    way "up" the recursion.
     *    If path exists, return it
     *    else return null.
     * 
     * @param s  Node     start node
     * @param e  Node     end node
     * @return Path       path from start to end or null
     */
    private Path findAPath( Node s, Node e )
    {
        //////////////////////////////////////////////////////////////
        // Recursive find, building path on the way "up" the recursion
        //////////////////////////////////////////////////////////////
        Path path = null;
        if( s.visited )
        {
            return null;
        }
        if( s == e )
        {
            path = new Path( );
            path.add( e );
            return path;
        }
        edges = s.getEdges( );
        if( edges == null )
        {
            return null;
        }
        s.setVisited( true );
        for( Edge ed : edges )
        {
            path = findPath( ed.end, e );
            if( path != null )
            {
                path.add( 0, s );
                return path;
            }
        }
        s.setVisited( false );
        return null;
    }
    //--------------- findAPath( Path, Node, Node ) --------------------
    /**
     * Find a path from Node s to Node e building the path on the
     *    way "down" the recursion and passing the partial path 
     *    as a parameter.
     * If path exists, return true
     * else return false
     * 
     * @param path Path   path to this node from original start
     * @param s  Node     start node
     * @param e  Node     end node
     * @return boolean
     */
    private boolean findAPath( Path path, Node s, Node e )
    {
        //////////////////////////////////////////////////////////////
        // Recursive find, building path on the way "down" the recursion
        //////////////////////////////////////////////////////////////

 
        
        
        

        
        return false;
    }
    ////////////////////////////////////////////////////////////////////
    // You should NOT need to change anything below here.
    ////////////////////////////////////////////////////////////////////
    
    //----------------------- getAddNode( String ) --------------------
    /**
     * If the parameter is an id for an existing node, return that node
     * else add a node with that id to the graph.
     * 
     * @param id String           id field of the Node
     * @return Node
     */
    public Node getAddNode( String id )
    {
        Node n = getNode( id );
        if ( n == null )
        {
            n = new Node( id );
            addNode( id );
        }
        return n;
    }   
    //----------------------- addNode( String ) -----------------------
    /**
     * Add a node to the graph given a node id.
     * 
     * @param id String            id field of node
     */
    public void addNode( String id )
    {
        addNode( new Node( id ) );
    }   
    //----------------------- addNode( Node ) -------------------------
    /**
     * Add a node to the graph; don't add if node with the same id already
     * exists in the graph -- give an error, but continue.
     * 
     * @param n Node            
     */
    public void addNode( Node n )
    {
        nodes.add( n );
        graph.put( n.getId(), n );
    }   
    //----------------------- addEdge( Edge ) --------------------
    /**
     * Add an edge to the graph.
     * 
     * @param e Edge          edge to be added
     */
    public void addEdge( Edge e )
    {
        edges.add( e );
        e.getStart().addEdge( e );      
    }   
    //------------------ Node getNode( String ) -----------------------
    /**
     * Get a node from the graph.
     * 
     * @param id String      id of Node of graph to find and return.
     * @return Node
     */
    public Node getNode( String id )
    {
        return graph.get( id );
    }   
    //----------------------- int size( ------------------------------
    /**
     * Return # nodes in the graph.
     * 
     * @return int
     */
    public int size()
    {
        return graph.size();
    } 
    //---------------------- ArrayList getNodes() ----------------------
    /**
     * Returns a reference to the ArrayList<Node> object.
     * 
     * @return ArrayList<Node>
     */
    public ArrayList<Node> getNodes() 
    {
        return nodes;
    }
   
    //---------------------- toString() --------------------------------
    /**
     * Create a brief textual representation of the Graph that includes
     *     the number of nodes and number of edges.
     * @return String
     */
    public String toString()
    {
        return "Graph: " + graph.size() 
            + " nodes and " + edges.size() + " edges";
    }
    //---------------------- longString() -----------------------------
    /**
     * Create a longer representation of the Graph.
     *    Print 1 line for each node:
     * 
     *    nodeId:  node1 node2 .... nodek 
     * 
     * where node1, node2, ... , nodek are nodes that are an end node
     *     for an edge that starts at nodeId.
     * @return String
     */
    public String longString()
    {
        String s = "";
        for ( Node n: graph.values() )
            s += n.longString() + "\n";
        return s;
    }
    //------------------ main -----------------------------------------
    /**
     * Convenience main that invokes the application.
     * 
     * @param args String[]     command line arg is filename
     */
    public static void main( String [ ] args ) 
    {
        if ( args.length == 1 )
            new GraphLab( "GraphLab Tool", args[ 0 ] );
        else
            new GraphLab( "GraphLab Tool", "graph1.txt" );
    }
}
