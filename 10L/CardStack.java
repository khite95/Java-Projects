//------------------------- CardStack ------------------------------
import javax.swing.border.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * CardStack -- a stack of cards
 * -
 * Built on Stack.
 * 
 * @author rdb
 * March 8, 2009
 * 
 * mlb 10/09
 * rdb 02/27/13 Changed constructor param from JPanel to Container
 * 
 */

public class CardStack extends Stack<Card> implements CardGroup
{
    //--------------- instance variables ------------------------ 
    private int    _xLoc;
    private int    _yLoc;
    private int    _xOffset = 0;
    private int    _yOffset = 0;
    private JLabel _base;
    private Container _parent;
    private JLabel _label;
    
    //--------------------- constructors -------------------------
    /**
     * construct a CardStack to be displayed in parent at x, y.
     * @param parent Container  containing AWT object
     * @param x int  x-coord of this stack
     * @param y int  y-coord
     */ 
    public CardStack( Container parent,  int x, int y )
    {
        super(); 
        
        _parent = parent;
        _xLoc = x;
        _yLoc = y;       
        setLocation( _xLoc, _yLoc );
        
        _label = new JLabel( );
        _label.setSize( Card.width, Card.height );
        _label.setLocation( _xLoc, _yLoc );
        _label.setBorder( new LineBorder( Color.BLACK, 1 ) );
        _label.setText( " Empty" );
        _label.setOpaque( true );
        _label.setBackground( Color.LIGHT_GRAY );
        _parent.add( _label );
        
    }
    
    //------------------ push ---------------------------------
    /**
     * push a card onto the stack.
     * @param c Card  card to be pushed
     * @return Card
     */ 
    public Card push( Card c )
    {
        super.push( c );
        return c;
    }
    
    //--------------------- setXOffset( int )--------------------------
    /**
     * set x offset for showing the edges of the cards in the stack.
     * @param m int offset in x
     */ 
    public void setXOffset( int m )
    {
        _xOffset = m;  
    }    
    
    //---------------------setYOffset( int )-----------------------
    /**
     * set y offset for showing the edges of the cards in the stack.
     * @param m int offset in y
     */ 
    public void setYOffset( int m )
    {
        _yOffset = m;  
    }
        
    //------------------- getXOffset() --------------------------
    /**
     * return the x location for the display of this card stack.
     * @return int  the x offset
     */   
    public int getXOffset()
    {
        return _xOffset;
    }
    
    //------------------- getYOffset() --------------------------
    /**
     * return the y location for the display of this card stack.
     * @return int  the x offset
     */
    public int getYOffset()
    {
        return _yOffset;
    }
    
    //--------------------- displayCards( int, boolean  ) ------------------
    /**
     * show the faces of the top "num" cards in the stack. 
     * @param num int  which cards to show
     * @param face boolean  true => show face
     */ 
    public void displayCards( int num, boolean face )
    {
        int size = size();
        if (  size == 0 )
            return;
        int show = Math.min( num, size );  // number to show
        
        if ( num < 0 )
            show = size;
        
        int count = 0;
        // place from bottom of stack
        for ( int c = size( ) - 1; c >= 0; c-- )
        {
            Card card = get( c );  
            if ( c > show - 1 || !face )
                card.setFaceUp( false );
            else
                card.setFaceUp( true );     
            card.setLocation( _xLoc + _xOffset * count,
                             _yLoc + _yOffset * count++ );  
            //////////////////////////////////////////////////////////
            // Need to invoke setComponentZOrder method of our _parent
            //   container in order to ensure that cards are displayed 
            //   so that the top of the stack is displayed last.
            // 1st parameter to setComponentZOrder is the component whose
            //    order is being specified (the Card object in our case).
            // The 2nd parameter specifies the "Z" index of the component 
            //   where large Z values are painted first -- therefore 
            //      low index Z components paint OVER high index ones.
            // Components with the same index are painted in the order
            //   in which their Z index has been specified.
            // Hence, the "later" ones draw over "earlier" ones.
            //
            // Assign Z index of the Cards from the bottom of the stack 
            //   to the top and we give them all the same Z-index. Hence, 
            //   the top of the stack is drawn last. The index parameter 
            //   must be between 0 and N-1, where N is the number of 
            //   components in the container. The easiest index to use 
            //   is 0, as long as there are no other components that 
            //   need to be drawn over the cards in this stack. 
            //   This is the case for this problem.
            //
            ///////////////////////////////////////////////////////////
            
            // Add line here to set the Z-index for this Card to 0
            //for( Card cd: c )
            //{
            //    _parent.setComponentZOrder( c, cd.get( c ) );
            //}
            _parent.setComponentZOrder( card, 0 );
        }
    }
    
    //------------------- get( int ) -------------------------
    /**
     * returns the i-th entry for display purposes; we think of entry 0
     * as being the top of the stack, but the implementation of the java
     * Stack class (probably built on top of ArrayList), puts the top of
     * the Stack as element stack.size() - 1. We invert the index based
     * on this knowledge. This one way that we can re-use an existing 
     * class whose semantics is not exactly what we want.
     * @param i int The desired index
     * @return Card  or null
     */
    public Card get( int i )
    {
        return super.get( size() - i - 1 );
    }
    
    //------------------- setLocation( int, int ) ----------------------
    /**
     * set the location of the stack, which means changing the locations
     *   of all the cards in the stack.
     * @param x int  x-location
     * @param y int  y-location 
     */
    public void setLocation( int x, int y )
    {
        _xLoc = x;
        _yLoc = y;
        if ( _base != null )
            _base.setLocation( x, y );
        for ( int i = 0; i < this.size(); i++ )
            get( i ).setLocation( x + _xOffset * i, y + i  * _yOffset );
        
    }
    
    //------------------- getXLocation() --------------------------
    /**
     * return the x location for the display of this card stack.
     * @return int   x loc 
     */   
    public int getXLocation()
    {
        return _xLoc;
    }
    
    //------------------- getYLocation() --------------------------
    /**
     * return the y location for the display of this card stack.
     * @return int   y loc 
     */
    public int getYLocation()
    {
        return _yLoc;
    }
}