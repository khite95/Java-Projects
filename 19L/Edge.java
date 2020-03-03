/**
 * Edge -- Represent a edge in a graph.
 *
 * @author rdb 
 * 10/31/10
 * 
 * derived from code written by Jonathan Brown Fall 2010
 */
public class Edge
{
    //----------------------- Instance Variables ----------------------
    protected Node    start, end;
    protected boolean highlighted;
    
    //---------------------------- constructor -----------------------
    /**
     * Creates a new edge object.
     * 
     * @param s Node      start node of edge
     * @param e Node      end node
     */
    public Edge( Node s, Node e )     
    {
        start = s;
        end = e;
        highlighted = false;
    }
    //-------------------------- getEnd() -----------------------------
    /**
     * Returns the edges's end node.
     * @return Node
     */
    public Node getEnd()
    {
        return end;
    }
    
    //-------------------------- getStart() ---------------------------
    /**
     * Returns the edges's start node.
     * @return Node
     */
    public Node getStart()
    {
        return start;
    }         
    //---------------------------- setHighlighted( true ) -----------------
    /**
     * Sets the edge's highlighted flag.
     * 
     * @param v boolean        true means it should be highlighted
     */
    public void setHighlighted( boolean v )
    {
        highlighted = v;
    }
    
    //--------------------------- highlighted() ---------------------------
    /**
     * Returns the highlighted status of the edge.
     * @return boolean
     */
    public boolean highlighted()
    {
        return highlighted;
    }   
    //--------------------------- toString() ---------------------------
    /**
     * Returns a string representing the edge: start,end.
     * @return String
     */
    public String toString()
    {
        return "<" + start +"->" + end + ">";
    }   
}
