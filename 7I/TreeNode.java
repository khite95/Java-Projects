//++++++++++++++++++++++++++ TreeNode ++++++++++++++++++++++++++++++++++++
import java.util.ArrayList;
/**
 * TreeNode class for Aces Up.
 *    The data is the current game state
 *    Node keeps track of its children, its parent,
 *    the move that caused the game to get to this state
 *    and the best move that leads to the best game state
 * 
 * Created 7/13/10 for general use by jb
 * Edited rdb March 2015 for Aces Up
 * @author jb rdb ???
 */

public class TreeNode
{
    //------------- instance variables ----------------------------------
    ///////////////////////////////////////////////////////////////////
    // You do not need to use all or any of the instance variables defined
    // below. They are here partly to help you identify what kinds of 
    // information is needed and also to make sure that the finished toString
    // methods compile.
    /////////////////////////////////////////////////////////////////////
    private GameState           gameState;
    private Move                bestMove;
    private int                 bestChild;
    private TreeNode            parent;
    private Move                moveToThisState;
    private int                 nodeScore = 0; // best discard size for this
                                               // node, i.e., for bestMove
    private int                 depth;         // a debugging tool
    private int                 height;        // height of this subtree
    private int                 numNodes;      // # descendant nodes.
    private Move[]              moves;         // 
    private TreeNode[]          childNodes; // simpler for getting all children
    private ArrayList<Move>     validMoves;
    
    //+++++++++++++++++++++ constructor +++++++++++++++++++++++++++++++++++

    //----------------------- child( Move ) -------------------------------
    /**
     * Returns the child node that the specified move defines.
     * @param m Move
     * @return TreeNode
     */
    public TreeNode child( Move m )
    {
        /////////////////////////////////////////////////////////
        // This method is barely specified; just enough so the 
        //     the toString methods below compile and won't crash.
        // You need to map a particular Move from this node to the
        //     child reference -- an element of the childNodes array
        //     if you use the predefined instance variables.
        ////////////////////////////////////////////////////////
        
        return null;
    }
    
    //---------------- getDrawString() ------------------------------------
    /**
     * Returns a String representing a Draw move at this state as a set of
     *   space separate cards that are part of the draw.
     * @return String.
     */
    public String getDrawString()
    {
        String cardStr = "( ";
        //////////////////////////////////////////////////////////////////
        // find the 4 top cards for the Draw pile and invoke their toString
        //   methods and concatenate the result (in left to right order)
        //   with exactly 1 blank between as in:
        //     "5H QS 2D TC"  (without the " in your string)
        //////////////////////////////////////////////////////////////////
        
        
        
        

        // The line below replaces the last character (a space) 
        //   in cardStr with )
        return cardStr.substring( 0, cardStr.length() - 1 )  + ")";
    }
    //---------------- getPathString() ------------------------------------
    /**
     * Returns a String representing a path from this node to the best 
     *     leaf node. It should be a space separated list of the 2-char
     *     id's generated by the Card.toString() method.
     * @return String.
     */
    public String getPathString()
    {
        /////////////////////////////////////////////////////////
        // This string does not have parens around it and you don't
        //    have to worry about an extra trailing space.
        
        
        
        return "pathString not implemented";        
    }
    
    //+++++++++++++++++++++++++++++ Private Methods +++++++++++++++++++++++   
    
    //--------------- toString( String ) ------------------
    /**
     * Recursive method that returns a string of all of the children
     * Each treenode is represented by the card moved to get to its point
     *     If it was a draw, it says draw instead
     * If there is only one child, it will display itself on the same line
     * If there is more than one child, it indents and displays the gamestate
     *    then indents again and acts like there was only one child.
     * @param indent String
     * @return String
     */
    private String toString( String indent )
    {
        ////////////////////////////////////////////////////////////////////
        // The starting code is a skeleton that if used correctly will 
        // produce the properly formatted representation of your tree that
        // can be used for efficient comparison of your solution with ours.
        //
        // Edit the pieces noted below.
        //////////////////////////////////////////////////////////////////
        String extraIndent = ". ";   // additional indent string at each level 
        String s = "";
        
        //---- You need to replace this with the correct reference
        //---- to YOUR collection of valid moves from this node.
        ArrayList<Move> moveSet = validMoves;
        //if only one move
        if ( moveSet.size() == 1 )
        {
            Move m = moveSet.get( 0 );
            
            //get the characters from the move and add it
            s += ">" + m.toString();
            
            //then recurse
            TreeNode child = child( m );
            if ( child != null )
                s += child.toString( indent );        
        }
        //if more than one move...
        else
        {
            //for each move
            for ( Move m: moveSet )
            {
                TreeNode child = child( m );
                //////////////////////////////////////////////////////////
                // Every  move in moveSet should have a child in the tree.
                //    If this is not true, tree needs to get fixed.
                //////////////////////////////////////////////////////////
                if ( child == null )
                    System.err.println( "Tree error: null child in print; " );
                else
                {
                    // parent score starts the line.
                    int score = child.nodeScore;
                    s += "\n" + indent + score + ":";
                    s += "{" + m.toString() + "}.";
                    s += depth + " "; // add depth
                    // and add in state information and new line
                    s += gameState.toString() + "\n" + indent + extraIndent;
                    
                    //recurse with bigger indent
                    s += child.toString( indent + extraIndent );
                }
            }
        }
        return s;
    }  

    


    //++++++++++++++++ public methods that are complete +++++++++++++++++++

    //////////////////////////////////////////////////////////////////////////
    // If you use the variable names declared in start version, you should
    // not need to change any of the methods below, which gather information
    // about the state of the node.
    /////////////////////////////////////////////////////////////////////////
    //---------------------- getScore() -------------------------------------
    /**
     * Returns the score for the best move.
     * @return int
     */
    public int getScore()
    {
        return nodeScore;
    }
    
    //---------------- getStatsString() ------------------------------------
    /**
     * Returns a String with stats information about the subtree rooted at this 
     *     node:  # nodes, depth of root, height of subtree, score of the node.
     * @return String.
     */
    public String getStatsString()
    {
        return "Score = " + nodeScore + "    depth = " + depth;
    }
    
    //------------------------ treeToString() --------------------------
    /**
     * Starts a recursive call that prints the string representing the tree
     *     rooted at this node.
     * @return String
     */
    public String treeToString()
    {
        return "+++++++++++++\n" + nodeScore + ": " + toString( "" );
    }
    
    //------------------------ toString() ------------------------------
    /**
     * Print information about this node only, not the tree rooted here.
     * @return String
     */
    public String toString()
    {
        return "Depth: " + depth + "  score: " + nodeScore + " #moves: "
            + validMoves.size() + " best: " + bestMove
            + "  parentMove: " + moveToThisState;
    }
}