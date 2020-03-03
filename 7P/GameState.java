//+++++++++++++++++++++++++++++++++ GameState ++++++++++++++++++++++++
import java.util.*;

/**
  * GameState class for Aces Up. 
  * 
  * This is a real skeleton containing only a couple of signatures needed
  * for producing consistent output for testing.
  * 
  * A major decision on your part is between:
  *   a. Actually copying state variables: the 6 card piles.
  *   b. Defining an incremental state definition:
  *        Given the starting piles, just save state change information
  *        -- the Move made.
  * 
  * @author Kenneth Hite
  */

public class GameState
{
    // rdb note: 
    //   If you implement by saving information about the piles, do NOT 
    //      blindly copy them. 
    //   We first copied the Pile objects. This turned out to be
    //    a bad idea; the Pile objects include CardStack objects, which
    //    also needed to be copied. CardStack objects extend Stack<Card>,
    //    but they add in lots of graphics behavior and overhead. We don't
    //    need any of the graphics as we build a play tree with 
    //    upto 5000+ nodes all of which have to copy all 6 of the Piles and
    //    all of which become Java graphics components.
    //    It is significantly faster to use Stack<Card>.
    //   Even though the Card objects are also graphics objects, there are
    //     never more than 52 of them no matter how many nodes there are int
    //     the tree.
     
    //------------------------- class variables ----------------------------
    
    //------------------------ instance variables --------------------------
    
    ////////////////////////////////////////////////////
    // You need not use these variables. They are here only
    // to insure that the code compiles.
    //
    private int _drawSize = 0;
    private int[] _pileSizes = { 0, 0, 0, 0 };
    private int _discardSize = 0;
    
    //---------- If you explicitly store card piles, you should declare them
    //---------- like this:
    private Stack<Card>    _drawPile;
    private Stack<Card>   _pile1, _pile2, _pile3, _pile4;
    private Stack<Card>    _discardPile;
    
    
    //-------------------- constructor(s) --------------------------------
    /**
     * constructor.
     * 
     * @param drawP Stack<Card>
     * @param p1 Stack<Card>
     * @param p2 Stack<Card>
     * @param p3 Stack<Card>
     * @param p4 Stack<Card>
     * @param dp Stack<Card>
     * 
     */
    public GameState( Stack<Card> drawP, Stack<Card> p1, Stack<Card> p2,
                          Stack<Card> p3, Stack<Card> p4, Stack<Card> dp )
    {
        setPiles( drawP, p1, p2, p3, p4, dp );
    }
    /**
     * setPiles.
     * 
     * @param drawP Stack<Card>
     * @param p1 Stack<Card>
     * @param p2 Stack<Card>
     * @param p3 Stack<Card>
     * @param p4 Stack<Card>
     * @param dp Stack<Card>
     * 
     */
    public void setPiles( Stack<Card> drawP, Stack<Card> p1, Stack<Card> p2,
                          Stack<Card> p3, Stack<Card> p4, Stack<Card> dp )
    {
        _drawPile = drawP;
        _pile1 = p1;
        _pile2 = p2;
        _pile3 = p3;
        _pile4 = p4;
        _discardPile = dp;
    }
    
    //--------------- other methods --------------------------------------
    
    
    
    
    
    
    
   
    //----------------------- toString() -----------------------------------
    /**
     * Returns a count of cards in each pile separated by spaces. 
     * @return String
     */
    public String toString()
    {
        return "Piles["  
            + _drawSize + " " 
            + _pileSizes[ 0 ] + " " 
            + _pileSizes[ 1 ] + " " 
            + _pileSizes[ 2 ] + " " 
            + _pileSizes[ 3 ] + " "
            + _discardSize + "]";
    }
}