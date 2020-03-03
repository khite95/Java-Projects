//+++++++++++++++++++++++++++++ Game +++++++++++++++++++++++++++++
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;

/**
 * Game.java - implementation of a solitaire game.
 * 
 * @author rdb (original)
 * Modified by mb, jb, rdb for different games and for general improvements.
 * Last modified
 * 03/01/15 rdb: made checkstyle-compatible
 */

public class Game extends JPanel implements NewFrame
{
    //----------------------- class variables -------------------
    protected static int   pause = 1;  // 1/4-seconds to pause in auto play
    
    static int nodeCount = 0;       // Count the nodes in the tree
    static int treeDepth = 0;       // Keep track of the deepest actual depth
    static int maxTreeDepth = 100;  // Max depth we'll allow tree to have??
    static boolean userMode = true; // Set if in user play mode
    
    private static Game thisGame = null;   // reference to the Game instance

    /**
     * PileName enum for play options.
     */
    public static enum PileName
    { DRAW, PLAY1, PLAY2, PLAY3, PLAY4, DISCARD };
    
    //----------------------- instance variables -------------------------------
    private Pile     _drawPile = null; 
    private Pile[]   _play;              // play piles
    private Pile     _discards = null;
    
    private Stack<Card> dp, p1, p2, p3, p4 , d;
    
    // labels for tree nodes
    private JLabel[]   _pileLabels = null; // 0 entry for draw pile 
    
    private ArrayList<Card> _playDeck = null;  // deckto play with
    private CardListener _cardListener;
    
    private FrameTimer _timer;
    private final int   qtrSecond = 250;  // 1/2 sec
    private final int   minDelay = 30;
    private boolean    _autoPlay = false;
    private Random     _rng = null;
    
    //----- positioning variables ----------------------------------------------
    /////////////////
    // you don't need to use these variables or their values
    /////////////////
    private   int   drawX  = 50;
    private   int   discardX  = 640;
    private   int   pileX = 200;
    private   int   pileY = 40;
    private   int   pileDx = 100;
    private   int   pileYOffset = 30;
    
    private   int labelY = 5;
    private   int labelW = 60;
    private   int labelH = 20;
    
    /////////////////////////////////////////////////////////////////////
    // Need instance variables for your tree
    //    - root of the tree
    //    - the current node of the tree while you are "playing" by traversing
    //////////////////////////////////////////////////////////////////////
    //--- tree data ---
    private TreeNode root = null;
    private TreeNode curNode; //keeping track of which state the game is in
        
    ///////////////////////////////////////////////////////////////////
    // More instance variables may be useful, depending on your design.
    ///////////////////////////////////////////////////////////////////
    protected static GameState gs;
    protected Move move;
    private String ms;
    private Boolean empty = false;

    //++++++++++++++++++++++++++++ constructors ++++++++++++++++++++++++++++
    
    //---------------------- Game( JPanel ) --------------------------------
    /**
     * Starts the game.
     */
    public Game()
    {
        thisGame = this;
        _cardListener = new CardListener();
        createDeck();     
        initializePiles();
        replay();
    }
    
    //+++++++++++++++++ public methods that need editing +++++++++++++++++++    
    //------------------------ buildTree() -------------------------------------
    /**
     * Starts the recursive call to build the tree.
     * Creates root node (since it is special) then lets recursion take over.
     */
    public void buildTree()
    {
        userMode = false;
        replay();  // reset game to last shuffled deck
        
        long startTime = System.currentTimeMillis();
 
        treeDepth = 0;
        nodeCount = 0;
        
        ////////////////////////////////////////////////////
        // Build your tree here. 
        //   You need to use recursion to do it cleanly.
        // You'll need to use multiple methods
        // 
        //////////////////////////////////////////////////
        root = new TreeNode(); // Replace this
        
        
        
        
        
        
        float secs = ( System.currentTimeMillis() - startTime ) / 1000.0f;
        System.out.println( "Build time: " + secs );
        System.out.println( "Tree score: " + root.getScore() );
        
        // After building the tree, the cards are left where the last leaf
        //    was generated. Need to restore to initial state.
        replay();
    }
    
    //-------------- undo() ----------------------------------------------------
    /**
     * Undo a move.
     *    Only works if the tree is built;
     * Returns a string if a problem occurs
     *    "Undoes" the move by setting the game state.
     * @return String
     */
    public String undo()
    {
        if ( root == null )
            return "Tree is not built";
        else if ( curNode == root )
            return "At root node, no moves to undo";
        else
        {
            ///////////////////////////////////////////////////////
            // Each click of "Undo" should restore the previous 
            //    state of the game.
            ///////////////////////////////////////////////////////
            
            
            
            
            update();
            updateState( );
            return null;
        }
    }

    //----------------- cardPicked( int ) --------------------------------
    /**
     * Execute a user specified move.
     * @param moveFromId int    0 => drawPile, 1-4 => play Piles
     *                          5 is discard; illegal move by definition
     */
    private void cardPicked( int moveFromId )
    {
        if ( root != null )
        {
            Reporter.report( "User moves not allowed in tree mode." );
            return;
        }
        ///////////////////////////////////////////////////////////
        // Here you should create a Move instance based on the pile
        //   that has been picked.
        // If the move is legal you should execute the move and
        //   afterwards call:
        //
        //       updateLabels( null ); // no tree, but still have count labels
        //       update();
        //////////////////////////////////////////////////////////  
        if( moveFromId == 0 || moveFromId == 5 )
        {
            if( _drawPile.getStack( ).size( ) > 0 )
            {
                for( int i = 0; i < 4; i++ )
                {
                    _play[ i ].getStack( ).push( _drawPile.getStack( ).pop( ) );
                }
            }
        }
        else 
        {
            if( moveFromId == 1 )
            {
                if( checkDiscard( _play[ 0 ].getStack( ).peek( ) ) )
                {
                    _discards.getStack( ).push( 
                        _play[ 0 ].getStack( ).pop( ) );
                }
                else
                {
                    checkEmpty( moveFromId );
                }
            }
            if( moveFromId == 2 )
            {
                if( checkDiscard( _play[ 1 ].getStack( ).peek( ) ) )
                {
                    _discards.getStack( ).push( 
                        _play[ 1 ].getStack( ).pop( ) );
                }
                else
                {
                    checkEmpty( moveFromId );
                }
            }
            if( moveFromId == 3 )
            {
                if( checkDiscard( _play[ 2 ].getStack( ).peek( ) ) )
                {
                    _discards.getStack( ).push( 
                        _play[ 2 ].getStack( ).pop( ) );
                }
                else
                {
                    checkEmpty( moveFromId );
                }
            }
            if( moveFromId == 4 )
            {
                if( checkDiscard( _play[ 3 ].getStack( ).peek( ) ) )
                {
                    _discards.getStack( ).push( 
                        _play[ 3 ].getStack( ).pop( ) );
                }
                else
                {
                    checkEmpty( moveFromId );
                }
            }
        }
        
        //updatelabels( null );         
        update( );
        updateState( );
        
        
            
    }
    //------------- makeAMove() ---------------------------------------
    /**
     * Determines if a move is available and, if so, makes it. It is called
     *    from the oneMove() Move button handler and from newFrame while in 
     *    AutoMove mode. 
     * If a tree is not built, it needs to invoke code to find a valid move
     *    from the current state.
     * If a tree is built, it chooses the best move from the current state
     *    information in the tree.
     *    
     * @return String  null: there was a move and it has been done.
     *                 non-null: a message informing that no move found
     */
    private String makeAMove()
    {
        ms = null;
        /////////////////////////////////////////////////////////
        //
        // Add your move code here. See method comments.
        //
        /////////////////////////////////////////////////////////
        Boolean moved = false;
            
        if( _play[ 0 ].getStack( ).size( ) > 0 )
        {
            if( checkDiscard( _play[ 0 ].getStack( ).peek( ) ) )
            {
                _discards.getStack( ).push( 
                                           _play[ 0 ].getStack( ).pop( ) );
                moved = true;
                Reporter.logMoves( 'd', 
                         _discards.getStack( ).peek( ).toString( ) );
            }
        }
        if( _play[ 1 ].getStack( ).size( ) > 0 && !moved )
        {
            if( checkDiscard( _play[ 1 ].getStack( ).peek( ) ) )
            {
                _discards.getStack( ).push( 
                                           _play[ 1 ].getStack( ).pop( ) );
                moved = true;
                Reporter.logMoves( 'd', 
                         _discards.getStack( ).peek( ).toString( ) );
            }
        }
        if( _play[ 2 ].getStack( ).size( ) > 0 && !moved )
        {
            if( checkDiscard( _play[ 2 ].getStack( ).peek( ) ) )
            {
                _discards.getStack( ).push( 
                                           _play[ 2 ].getStack( ).pop( ) );
                moved = true;
                Reporter.logMoves( 'd', 
                         _discards.getStack( ).peek( ).toString( ) );
            }
        }
        if( _play[ 3 ].getStack( ).size( ) > 0 && !moved )
        {
            if( checkDiscard( _play[ 3 ].getStack( ).peek( ) ) )
            {
                _discards.getStack( ).push( 
                                           _play[ 3 ].getStack( ).pop( ) );
                moved = true;
                Reporter.logMoves( 'd', 
                         _discards.getStack( ).peek( ).toString( ) );
            }
        }
        if( !moved )
        {
            int size = 0;
            for( int i = 0; i < _play.length; i++ )
            {
                if( _play[ i ].getStack( ).size( ) > 
                     _play[ size ].getStack( ).size( ) 
                    && _play[ i ].getStack( ).size( ) > 1 )
                {
                    size = i;
                }
            }
            checkEmpty( size + 1 );
        }
        if( !empty && !moved )
        {    
            if( _drawPile.getStack( ).size( ) > 0 )
            {
                for( int i = 0; i < 4; i++ )
                {
                    _play[ i ].getStack( ).push( _drawPile.getStack( ).pop( ) );
                }
                Reporter.logMoves( 'D', "Draw" );
            }
            else
            {
                ms = "No move found";
            }
        }
        empty = false;
        //updatelabels( null );         
        update( );
        updateState( );
        return ms;
    }
    //---------------- updateLabels( TreeNode ) ------------------------------
    /**
     * Update the labels for potential moves with the subtree "score" for
     *    all next possible moves.
     * @param cur TreeNode
     */
    private void updateLabels( TreeNode cur )
    {        
        String[] labels = new String[ 6 ];
        labels[ 0 ] = _drawPile.size() + ":";
        labels[ 1 ] = _play[ 0 ].size() + ":";
        labels[ 2 ] = _play[ 1 ].size() + ":";
        labels[ 3 ] = _play[ 2 ].size() + ":";
        labels[ 4 ] = _play[ 3 ].size() + ":";
        labels[ 5 ] = _discards.size() + ":" ;
        
        if ( cur != null ) // no tree
        {
            ////////////////////////////////////////////////////////////
            //
            // Check each child of the current node: it should be either
            //    a draw or a play from one of the play piles.
            // Each child's node score should be appended to its label. 
            //    
            ////////////////////////////////////////////////////////////
            
            
            
            
            
        }
        
        for ( int i = 0; i < _pileLabels.length; i++ )
            _pileLabels[ i ].setText( labels[ i ] );
    }
        

    //////////////////////////////////////////////////////////////////////
    //
    // You'll need one or more methods for each of the following major
    // tasks:
    //   1. Creating and executing Move objects: sharable whenever possible by 
    //         user play mode
    //         ad hoc program mode
    //         tree program mode
    //   2. Creating GameState objects
    //   3. Determining what moves are possible given the current state
    //   4. Building the tree; this is most easily done recursively.
    //   5. Accessing the tree and state information.
    //   6. Utility methods to keep others short.
    /////////////////////////////////////////////////////////////////////

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /***********************************************************************/
    ////////////////////////////////////////////////////////////////////////
    // The methods below are complete; you should not need
    //    to change any of them.
    ////////////////////////////////////////////////////////////////////////

    //++++++++++++++++++++++++++++ public methods +++++++++++++++++++++++++++   
    //----------------------------- newSeed() --------------------------------
    /**
     * Create a new Random number generator with this new seed.
     * @param seed int
     */
    public static void newSeed( int seed )
    {     
        thisGame._rng = new Random( seed );
        thisGame.newGame();
    }
    //----------------------------- update() --------------------------------
    /**
     * Update the display components as needed.
     */
    public void update()
    {      
        _drawPile.getStack().displayCards( 0, false );
        
        for ( int i = 0; i < _play.length; i++ )
            _play[ i ].getStack().displayCards( -1, true );
        
        // show all cards on the _discards stack 
        _discards.getStack().displayCards( -1, true );
        
        this.repaint();    
    }
    //-------------------------- newFrame() --------------------------------
    /**
     * This handles FrameTimer event in AutoPlay mode.
     * All we want to do is issue a new Play command when event occurs.
     * and restart the timer -- until no plays are possible.
     */
    public void newFrame() 
    {
        if ( !_autoPlay )
            return;
        String msg = makeAMove();
        _timer.stop();
        if ( _autoPlay && msg == null )  // reset timer
        {
            _timer.setInitialDelay( pause * qtrSecond + minDelay );
            _timer.start();
        }
        updateLabels( curNode );
        if ( msg != null )
        {
            _autoPlay = false;
        }
    }
    
    //------------------------ replay() -----------------------------------
    /**
     * Starts the game over, including deleting the tree.
     *   Resets all the piles
     *   Does not shuffle; it's not a new game
     */
    public void replay()
    {
        clearPiles();
        
        deckToDrawPile( _playDeck );
        if ( userMode )
            root = null; 
        curNode = root;
        updateLabels( root );
        update();
    }
    
    //------------------------ newGame( ) --------------------------------------
    /**
     * Shuffles the current base deck according to the seed in the game.
     * This version leads to more predictable games. Each seed is independent
     * and should generate same series of results regardless of the order
     * in which the seeds are used.
     */
    public void newGame()
    {
        // copy unshuffled baseDeck to playDeck
        _playDeck.clear();
        for ( Card card: _baseDeck )
            _playDeck.add( card );
        
        if ( _rng == null )  // first shuffle 
            _rng = new Random( 0 );  // assume initial seed is 0
        Collections.shuffle( _playDeck, _rng );
        root = null;
        replay();
        update();
        updateState( );
    }
    //------------- oneMove() ---------------------------------------
    /**
     * Handle "Move" button event by calling makeAMove. 
     *    If in AutoPlay mode, will exit AutoPlay.
     *    Returns a string with no-null value if no moves possible.
     * 
     * @return String
     */
    public String oneMove()
    {
        if ( _autoPlay )
        {
            _autoPlay = false;
            _timer.stop();
        }
        return makeAMove();
    }
    //------------- autoMove() ---------------------------------------
    /**
     * Does multiple moves automatically.
     *    
     * @return String   always returns null
     */
    public String autoMove()
    {
        _autoPlay = true;
        
        if ( _timer == null )       
            _timer = new FrameTimer( qtrSecond * pause + minDelay, this );
        else
            _timer.setDelay( qtrSecond * pause + minDelay );
        _timer.start();

        return null;
    }
    //-------------- logPath() ---------------------------------------------
    /**
     * Get a string representation of the path from current tree node
     *     to the best end state. Log that string to Reporter.log.
     * @param prefix String
     * @return String
     */
    public String logPath( String prefix )
    {
        if ( root == null )
            return "Tree is not built";
        else 
        {
            String path = curNode.getPathString();
            Reporter.report( prefix + path );
            return null;
        }
    }
    //-------------- nodeStats() ------------------------------------
    /**
     * Create a String providing basic statistics about the subtree rooted
     *    at the current tree Node. Send to Reporter.log.
     * @param prefix String
     * @return String   null if success, error message if not.
     */
    public String nodeStats( String prefix )
    {
        if ( root == null )
            return "Tree is not built";
        else 
        {
            String path = curNode.getStatsString();
            Reporter.report( prefix + path );
            return null;
        }
    }
    
    //-------------- saveTree( String) -----------------------------------
    /**
     * Dump a string representation of the tree to a file.
     * 
     * @param fileName   String
     * @return String
     */
    public String saveTree( String fileName )
    {
        String returnMsg = null;
        if ( root == null )
            returnMsg = "Tree is not built";
        else 
        {
            try
            {
                PrintWriter out = new PrintWriter( fileName );
                out.println( root.treeToString() );
                out.close();
            }
            catch ( FileNotFoundException fnf )
            {
                returnMsg = "Unable to open " + fileName;
            }
        }
        return returnMsg;
    }        
    //++++++++++++++++++++++++++  private methods ++++++++++++++++++++++++++

    //------------------------ initializePiles() ---------------------------
    /**
     * Intializes the piles at the start of the program.
     * Create them and set their locations.
     */
    private void initializePiles()
    {
        CardStack stack = new CardStack( this,  discardX, pileY );
        //_discards.setYOffset(11);
        _discards = new Pile( stack, PileName.DISCARD );
        
        stack = new CardStack( this,  drawX, pileY );
        //_drawPile.setYOffset(11);
        _drawPile = new Pile( stack, PileName.DRAW );
        
        int xLoc = pileX;
        _play = new Pile[ 4 ];
        for ( int i = 0; i < _play.length; i++ )
        {
            CardStack cards = new CardStack( this, xLoc, pileY );
            cards.setYOffset( pileYOffset );
            _play[ i ] = new Pile( cards, i + 1 );
            xLoc += pileDx;
        }
        
        // create labels
        _pileLabels = new JLabel[ 6 ];
        
        xLoc = drawX;
        for ( int i = 0; i < _pileLabels.length; i++ )
        {
            JLabel label = new JLabel( "-----" );
            label.setLocation( xLoc, labelY );
            label.setSize( labelW, labelH );
            label.setBorder( new LineBorder( Color.BLACK, 1 ) );
            this.add( label );
            _pileLabels[ i ] = label;
            if ( i == 0 )
                xLoc = pileX;
            else if ( i == 4 )  // next will be discard pile
                xLoc = discardX;
            else
                xLoc += pileDx;
        }
        if( gs != null )
        {
            updateState( );
        }
        else
        {       
            dp = new Stack<Card>( );
            p1 = new Stack<Card>( );
            p2 = new Stack<Card>( );
            p3 = new Stack<Card>( );
            p4 = new Stack<Card>( );
            d = new Stack<Card>( );
            toStack( );
            gs = new GameState( dp, p1, p2, p3, p4, d );
        }
        if( move == null )
        {
            move = new Move( );
        }
    }

    //------------------------ createDeck() ------------------------------------
    private ArrayList<Card> _baseDeck = null;  // unshuffled deck
    /**
     * Creates the first basedeck at game start.
     */
    private void createDeck()
    {
        _baseDeck = new ArrayList<Card>();  // original unshuffled deck
        _playDeck = new ArrayList<Card>();  // the deck we'll play with
        
        for ( Card.Suit suit: Card.Suit.values() )
        {
            for ( Card.Rank rank: Card.Rank.values() )
            {
                if ( rank != Card.Rank.A_LO ) // Ace is high in AcesUp
                {
                    Card card = new Card( rank, suit );
                    card.addListener( (MouseListener) _cardListener );
                    _baseDeck.add( 0, card );
                    _playDeck.add( 0, card );
                    this.add( card );
                }
            }
        }
        curNode = root;
    }
    
    //------------------------ deckToDrawPile( Card[] ) ----------------------
    /**
     * Copy an array of cards into the Pile representing the draw pile.
     * @param deck ArrayList<Card>
     */
    private void deckToDrawPile( ArrayList<Card> deck )
    {
        for ( int c = 0; c < 52; c++ )
            _drawPile.getStack().push( deck.get( c ) );
    }
    
    //-------------------- clearPiles(  ) --------------------------
    /**
     * Clears all card piles.
     */
    private void clearPiles( )
    {
        _drawPile.clear();
        _discards.clear();
        for ( int i = 0; i < _play.length; i++ )
            _play[ i ].getStack().clear();
        updateState( );
    }
    
    //+++++++++++++++++++++++++++ Inner class ++++++++++++++++++++++++++++++++
    /**
     * Internal CardListener to handle mouse pick of a card.
     */
    public class CardListener extends MouseAdapter
    {
        //--------------------- mousePressed --------------------------
        /**
         * mousePressed -- handled.
         * @param e MouseEvent
         */ 
        public void mousePressed( MouseEvent e ) 
        {
            handleCardPick( e ); 
        }
    }
    //------------------------- handleCardPick( MouseEvent ) ---------------
    /**
     * Got a mouse event (clicked) on a card; figure out if it represents
     * a valid move and do the move if it is.
     * @param e MouseEvent
     */
    private void handleCardPick( MouseEvent e )
    {
        int   moveFromId = -1;
        
        Card  card = (Card) e.getSource();
        
        if ( card == _drawPile.peek() )
            moveFromId = 0;    // draw
        else if ( card == _discards.peek() )
            moveFromId = 5;    // draw
        else 
        {
            int foundPile = -1;
            for ( int i = 0; i < _play.length && foundPile < 0; i++ )
            {
                if ( card == _play[ i ].peek() )
                    foundPile = i;
            }
            if ( foundPile >= 0 )
                moveFromId = foundPile + 1;
        }
        
        if ( moveFromId >= 0 )
            cardPicked( moveFromId );
        else
            Reporter.report( "Invalid move: " + card );
    }    
    /**
     * updateState.
     */
    public void updateState( ) 
    {
        toStack( );
        gs.setPiles( dp, p1, p2, p3, p4, d );
    }
    /**
     * toStack.
     */
    public void toStack( )
    {
        //for( int i = 0; i < _drawPile.getStack( ).size( ); i++ )
        //{   
        if( _drawPile != null )
            dp.addAll( _drawPile.getStack( ) );
        if( _play[ 0 ] != null )
            p1.addAll( _play[ 0 ].getStack( ) );
        if( _play[ 1 ] != null )
            p2.addAll( _play[ 1 ].getStack( ) );
        if( _play[ 2 ] != null )
            p3.addAll( _play[ 2 ].getStack( ) );
        if( _play[ 3 ] != null )
            p4.addAll( _play[ 3 ].getStack( ) );
        if( _discards != null )
            d.addAll( _discards.getStack( ) );
    }
    /**
     * checkDiscard.
     * 
     * @param card Card
     * @return Boolean
     */
    public Boolean checkDiscard( Card card )
    {
        for( int i = 0; i < _play.length; i++ )
        {
            if( _play[ i ].getStack( ).size( ) > 0 )
            {
                if( _play[ i ].getStack( ).peek( ) != card )
                {
                    if( _play[ i ].getStack( ).peek( ).getSuit( ) 
                           == card.getSuit( ) )
                    {
                        if( _play[i].getStack( ).peek( ).getRank( ).ordinal( ) 
                               > card.getRank( ).ordinal( ) )
                        {
                            empty = true;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    /**
     * checkEmpty.
     * 
     * @param pileNum int
     */
    public void checkEmpty( int pileNum )
    {
        for( int i = 0; i < _play.length; i++ )
        {
            if( _play[ i ].getStack( ).size( ) <= 0 )
            {
                if( _play[ pileNum - 1 ].getStack( ).size( ) > 1 )
                {
                    _play[ i ].getStack( ).push( 
                         _play[ pileNum - 1 ].getStack( ).pop( ) );
                    empty = true;
                    Reporter.logMoves( 'p', 
                        _play[ i ].getStack( ).peek( ).toString( ) );
                    break;
                }
            }
        }
    }
            
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //--------------------------- main --------------------------------------
    /**
     * Convenience application start for DrJava.
     * @param args String[]   Not used
     */
    public static void main( String[] args )
    {
        // Invoke main class's main
        AcesUp.main( args );        
    }
}
