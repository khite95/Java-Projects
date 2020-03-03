//++++++++++++++++++++++++++++ Path +++++++++++++++++++++++++++++++++++
import java.util.*;
/**
 * Path.java - represents a edge from one node in a graph to another.
 *             the edge is stored as the edges.
 *
 * @author rdb
 * 10/31/10
 */

public class Path
{
    //-------------- instance variables -------------------------------
    private ArrayList<Node> nodesOnPath;
    
    //---------------- constructor ------------------------------------
    /**
     * No-arg constructor.
     */
    public Path()
    {
        nodesOnPath = new ArrayList<Node>();
    }
    //------------------ add( Node ) ----------------------------------
    /**
     * Add a node to the end of the path.
     * @param node Node
     */
    public void add( Node node )
    {
        nodesOnPath.add( node );
    }
    //------------------ add( int, Node ) -----------------------------
    /**
     * Add a node to a specific position in the path.
     * @param p int       position
     * @param node Node   node being added
     */
    public void add( int p, Node node )
    {
        nodesOnPath.add( p, node );
    }
    //------------------ addAll( Path ) -------------------------------
    /**
     * Add all nodes in path passed as parameter to the end of this path.
     * @param path Path    
     */
    public void addAll( Path path )
    {
        for ( Node n: path.nodesOnPath )
            nodesOnPath.add( n );
    }
    //------------------ remove( Node ) -------------------------------
    /**
     * Remove a specified node from the path.
     * @param node Node    node to be removed.
     * @return boolean
     */
    public boolean remove( Node node )
    {
        return nodesOnPath.remove( node );
    }
    //------------------ remove( int ) --------------------------------
    /**
     * Remove a node from a specified position in the path.
     * @param which int    the position to delete
     * @return Node        return the removed Node
     */
    public Node remove( int which )
    {
        return nodesOnPath.remove( which );
    }
    //------------------ getPath() ---------------------------------
    /**
     * Return the path as array list of nodes.
     * @return ArrayList<Node> 
     */
    public ArrayList<Node> getPath() 
    {
        return nodesOnPath;
    }
    //------------------ getStart() -----------------------------------
    /**
     * Return start of path.
     * @return Node
     */
    Node getStart() 
    {
        if ( nodesOnPath.size() > 0 )
            return nodesOnPath.get( 0 );
        else
            return null;
    }
    //------------------ getEnd() -----------------------------------
    /**
     * Return end of Path.
     * @return Node
     */
    Node getEnd() 
    {
        if ( nodesOnPath.size() > 0 )
            return nodesOnPath.get( nodesOnPath.size() - 1 );
        else
            return null;
    }
    //------------------ size() -----------------------------------
    /**
     * Return # nodes in the path.
     * @return int
     */
    int size() 
    {
        return nodesOnPath.size();
    }
    //------------------- toString() --------------------------------
    /**
     * Make a short strin gversion of Path info: start, end, length.
     * @return String
     */
    public String toString()
    {
        int len = nodesOnPath.size();
        return "Path(" + len + "): " + nodesOnPath.get( 0 ).getId() + " -> " +
            nodesOnPath.get( len - 1 ).getId();
    }
    //------------------- longString() --------------------------------
    /**
     * Make a longer string version of Path info: list of all nodes on path.
     * @return String
     */
    public String longString()
    {
        int len = nodesOnPath.size();
        String s = "Path(" + len + "): ";
        for ( Node n: nodesOnPath )
        {
            s += n.getId() + "->";
        }
        return s.substring( 0, s.length() - 2 ); // delete last ->
    }
}