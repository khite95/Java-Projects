import java.util.*;
/**
 * Move class for Aces Up.
 * 
 * Encapsulate the information for a Move.
 * @author Kenneth Hite
 */
public class Move
{    
    //------------------------ instance variables --------------------------
    ////////////////////////////////////////////////////
    // You need not use this variables. It is here so start code compiles.
    private Card card = null;
    
    private Stack<Card>    _drawPile;
    private Stack<Card>[]  _piles;
    private Stack<Card>    _discardPile;
    
    //-------------------- toString() ---------------------------------
    /**
     * Return string representation for a Pile; needs to be compact.
     * @return String
     */
    public String toString()
    {
        if ( card != null )
            return card.toString(); 
        else
            return "Draw";
    }  
}