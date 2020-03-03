//++++++++++++++++++++++++++++++ Node +++++++++++++++++++++++++++++++++
import java.util.*;
/**
 * Node -- Represent a node in a directed graph.
 *
 * @author rdb 
 * 10/31/10
 * 
 * derived from code written by Jonathan Brown for GraphPanel Fall 2010
 */

public class Node
{
    protected String              id;
    protected HashMap<Node, Edge> edges; // key is the "end" node
    protected boolean             visited;
    //----------------- Constructors --------------------------
    /**
     * Creates a new node with the specified id.
     * Sets the background as white.
     * 
     * @param nid String      id of node.
     */
    public Node( String nid )     
    {
        id = nid;
        edges = new HashMap<Node, Edge>();
        visited = false;
    }
    
    //-------------------------- addEdge( Edge ) ---------------------
    /**
     * Adds an edge that starts at this node.
     * @param edge Edge
     */
    public void addEdge( Edge edge )
    {
        if ( edge.getStart() == this )
            edges.put( edge.getEnd(), edge );
        else
            System.err.println( "Error: can't add " + edge + " to " + this );
    }
    
    //------------------------ setVisited( boolean ) ------------------
    /**
     * Sets the node's visited state.
     * @param v boolean
     */
    public void setVisited( boolean v )
    {
        visited = v;
    }
    
    //--------------------------- visited() ----------------------------
    /**
     * Returns the visited state of the node.
     * @return boolean
     */
    public boolean visited()
    {
        return visited;
    }
    
    //----------------------------- getId() ----------------------------
    /**
     * Returns the id of this node.
     * @return String
     */
    public String getId()
    {
        return id;
    }
    
    //----------------------------- getEdge( Node ) --------------------
    /**
     * Returns edge from this node to the end node, if there is one.
     * @param end Node
     * @return Edge
     */
    public Edge getEdge( Node end )
    {
        return edges.get( end );
    }
    
    //----------------------- toString() -------------------------------
    /**
     * Returns a String representation of the Node. 
     *    This version is just the id.
     * @return String
     */
    public String toString()
    {
        return id;
    }
    
    //----------------------- longString() -----------------------------
    /**
     * Returns the extended information about the node:
     *      nodeId:  end1 end2 ... endk
     * where 
     *      endk are ids of nodes that are at ends of edges from this node.
     * @return String
     */
    public String longString()
    {
        String s = "";
        for ( Edge e: edges.values() )
            s += e.getEnd() + " ";
        
        // this in a string expression invokes this.toString()
        return  this + ": " + s;
    }
    
    //----------------------------- getEdges() -------------------------
    /**
     * Returns an Collection of the edges from this node.
     * @return Collection<Edge>
     */
    public Collection<Edge> getEdges()
    {
        return  edges.values();
    }
}
