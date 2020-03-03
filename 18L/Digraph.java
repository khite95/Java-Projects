//++++++++++++++++++++++++++++ Digraph ++++++++++++++++++++++++++++++
import java.util.*;

/**
 * Digraph - a class that encapsulates the basic functionality of
 * a directed graph.
 *         
 * Features to be added (in addition to completing existing methods):
 *    1. Collection to hold the nodes in the graph
 *    2. Collection to hold the edges in the graph
 *    3. Methods to access the nodes and edges; 
 *       a. you might want to access an individual node 
 *          or edge  by index
 *       b. you might want to access the entire collection of nodes 
 *          and/or edges.
 * 
 * 
 * Additional features to consider for a general purpose Digraph class
 *    (but NOT for this lab).
 *    4. You might want to have alternative convenience methods, such as
 *       a. adding an Edge given 2 Nodes
 *       b. adding an Edge given 2 node ids (adding nodes if needed)
 *       c. other things?
 *    
 * @author rdb
 * 10/31/10 - derived from code created by Jonathan Brown 
 *            
 */

public class Digraph
{
    //----------------------- instance variables -----------------------
    private HashMap<String, Node> hashMap;
    private ArrayList<Edge> edge;
    //----------------------- constructor -------------------------------
    /**
     * Constructor.
     */
    public Digraph()
    {
        hashMap = new HashMap<String, Node>( );
        edge = new ArrayList<Edge>( );
    }
    //----------------------- getAddNode( String ) ----------------------
    /**
     * If the parameter is an id for an existing node, return that node
     * else add a node with that id to the graph and return the new node.
     * @param id String
     * @return Node
     */
    public Node getAddNode( String id )
    {
        Node node = getNode( id );
        if( node == null )
        {
            addNode( id );
        }
        return node;
    }   
    //----------------------- addNode( String ) ------------------------
    /**
     * Add a node to the graph given a node id. Create a Node, then call 
     *    addNode( Node ).
     * @param id String
     * @return Node
     */
    public Node addNode( String id )
    {
        Node n = new Node( id );
        addNode( n );
        return n;
    }   
    //----------------------- addNode( Node ) --------------------------
    /**
     * Add a node to graph; don't add if a node with the same id already
     * exists in the graph -- give an error, but continue.
     * @param n Node
     */
    public void addNode( Node n )
    {
        Node node = hashMap.get( n.getId( ) );
        if( node == null )
        {
            hashMap.put( n.getId( ), n );
        }
        else
        {
            System.out.println( "Error: node already added." );
        }
    }   
    //-------------------- addEdge( String, String ) -------------------
    /**
     * Add an edge to the graph, given node ids for the edge.
     * @param idFrom String
     * @param idTo   String
     * @return Edge
     */
    public Edge addEdge( String idFrom, String idTo )
    {
        Edge e = new Edge( getAddNode( idFrom ), getAddNode( idTo ) );
        edge.add( e );
        getNode( idFrom ).addEdge( e );
        return e;
    }   
    //----------------------- Node getNode( String ) ------------------
    /**
     * Get a node with specified id from the graph; return null if no such
     * node exists.
     * @param id String
     * @return Node
     */
    public Node getNode( String id )
    {
        Node node = hashMap.get( id );
        if( node == null )
        {
            return null;
        }
        else
        {
            return node;
        }
    }   
    //----------------------- int size --------------------------
    /**
     * Return # nodes in the graph.
     * @return int
     */
    public int size()
    {
        return hashMap.size( );
    } 
    //---------------------- toString() ----------------------------
    /**
     * Create a brief textual representation of the Digraph.
     * Print out the number of nodes and number of edges.
     * @return String
     */
    public String toString()
    {
        String nodePrefix = "Nodes: ";
        String edgePrefix = "    Edges: ";
        
        return nodePrefix + size( ) + edgePrefix + edge.size( );
    }
    //---------------------- longString() -----------------------------
    /**
     * Create a longer representation of the Digraph.
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
        for( String key : hashMap.keySet( ) )
        {
            Node n = hashMap.get( key );
            s += n.longString( ) + "\n";
        }
        return s;
    }
    //+++++++++++++++++++++++ main ++++++++++++++++++++++++++++++++++++
    /**
     * Convenience app invocation.
     * @param args String[]
     */
    public static void main( String [ ] args ) 
    {
        GraphLab.main( args );
    }
}
