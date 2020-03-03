//------------------------ GUI -------------------------------
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

/**
 * GUI for assignment 5P.
 * 
 * @author rdb
 * 
 */
public class GUI extends JPanel //implements Animated
{
    //---------------- class variables ------------------------------
    static boolean showUpdates = true;  // true => show updates as recursing
    static boolean showSome = false;    // true => show 1 of a 1,000
    static int maxDelay = 500;
    private SquareArray board = null;
    private String file;
    
    //--------------- instance variables ----------------------------
    static int             _pauseDelay = 250;  // delay after each move
    
    //------------------ constructor ---------------------------------
    /**
     * The GUI constructor uses BorderLayout.
     * @param inFile String   initial puzzle file
     */
    public GUI( String inFile )     
    {
        this.setLayout( new BorderLayout() );
        
        buildGUI();
        file = inFile;
        
        makeDisplay( inFile );
    }
    //---------------------------- makeDisplay -------------------------
     /**
     * Make the puzzle display.
     * @param fileName String   the name of the puzzle file
     */
    private void makeDisplay( String fileName )
    { 
        ////////////////////////////////////////////////////////////
        // create the sudoku puzzle display and add it to this JPanel
        ////////////////////////////////////////////////////////////
        char[ ][ ] textArray = new char[ 9 ][ 9 ];
        int[ ][ ] intArray = new int[ 9 ][ 9 ];
        File f = new File( fileName );
        try 
        {
            Scanner scanner = new Scanner( f );
            for( int row = 0; scanner.hasNextLine( ) && row < 9; row++ ) 
            {
                textArray[ row ] = scanner.nextLine( ).toCharArray( );
            }
            scanner.close();
        } 
        catch ( FileNotFoundException e ) 
        {
            e.printStackTrace();
        }
        for( int r = 0; r < 9; r++ )
        {
            for( int c = 0 ; c < 9; c++ )
            {
                if( textArray[ r ][ c ] == '-' )
                {
                    textArray[ r ][ c ] = Character.MIN_VALUE;
                }
            }
        }
        if( board != null )
        {
            board.makeBoard( textArray );
        }
        else
        {
            board = new SquareArray( textArray, this );
        }
        add( board, BorderLayout.CENTER );
        updateDisplay( this, _pauseDelay );
        validate();  // In my solution a simple repaint() didn't work
                     // here. validate() is a Container method that says
                     // that the entire contents has changed and needs
                     // to be recreated. This worked. You might need it.
    }
    //------------------ openPuzzleFile() --------------------
    /**
     * Open a puzzle input file using a JFileChooser to get the file name.
     */
    private void openPuzzleFile()
    {
        String prompt = "Choose a Sudoku board file";
        String fileName = Utilities.getFileName( prompt );
        if ( fileName != null && fileName.length() > 0 )
        {
            file = fileName;
            makeDisplay( file );
        }
    }
    //---------------------- solve ---------------------------------
    /**
     * Start the recursive solution of the puzzle. You will need
     * a helper function to do the solution recursively; feel free to
     * change this method name/parameters if you wish. It's here so the
     * Button handler has something to call. 
     */
    private void solve()
    {
        makeDisplay( file );
        if( board != null )
        {
            updateDisplay( this, _pauseDelay );
            System.out.println( file );
            board.solve( 0, 0, 0 );
            updateDisplay( this, _pauseDelay );
        }
    }
    
    //--------------------- buildGUI -------------------------------
    /**
     * generate the gui tools.
     *
     */
    private void buildGUI()
    {   
        JPanel northPanel = makeNorthPanel();
        add( northPanel, BorderLayout.NORTH );
    }
  //------------------------- makeNorthPanel --------------------
    /**
     * Make a panel containing buttons and slider for the GUI.
     * 
     * @return JPanel  panel containing buttons and sliders
     */
    private JPanel makeNorthPanel()
    {
        JPanel panel = new JPanel();        
        panel.setBorder( new LineBorder( Color.BLACK, 2 ) );        
        addButtons( panel );
        addToggles( panel );
        addSlider( panel );
        return panel;
    }
    //--------------------- addButtons ---------------------------
    /**
     * Add the File and Solve buttons.
     * @param panel JPanel   Container to put buttons into.
     */
    private void addButtons( JPanel panel )
    {
        // create a button to open a new file
        JButton fileButton = new JButton( "File" );
        fileButton.addActionListener( 
            new ActionListener()
            {   // all we want to do is override actionPerformed
                public void actionPerformed( ActionEvent ev )
                {  
                    openPuzzleFile();
                }
            } );
        panel.add( fileButton );

        // create a button to solve the puzzle
        JButton solveButton = new JButton( "Solve" );
        solveButton.addActionListener( 
            new ActionListener()
            {   // all we want to do is override actionPerformed
                public void actionPerformed( ActionEvent ev )
                {  
                    solve();
                }
            } );
        panel.add( solveButton );       
    }
    //--------------------- addToggles ---------------------------
    /**
     * Add the Show and ShowAFew toggle buttons.
     * @param panel JPanel   Container to put buttons into.
     */
    private void addToggles( JPanel panel )
    {
        // create a toggle to show updates
        JToggleButton showButton = new JToggleButton( "Show" );
        showButton.addActionListener( 
            new ActionListener()
            {   // all we want to do is override actionPerformed
                public void actionPerformed( ActionEvent ev )
                {  
                    showUpdates = !showUpdates;
                }
            } );
        showButton.setSelected( true );
        panel.add( showButton );
        
        // create a toggle to show updates
        JToggleButton someButton = new JToggleButton( "Show a few" );
        someButton.addActionListener( 
            new ActionListener()
            {   // all we want to do is override actionPerformed
                public void actionPerformed( ActionEvent ev )
                {  
                    showSome = !showSome;
                }
            } );
        panel.add( someButton );
    }
        
    //--------------------- addSlider ---------------------------
    /**
     * Add the delay time slider.
     * @param panel JPanel   Container to put buttons into.
     */
    private void addSlider( JPanel panel )
    {
        //------------- Delay time Slider  -----------------------------
        LabeledSlider delaySlider = 
            new LabeledSlider( "Delay", 0, maxDelay, maxDelay / 2 );
        delaySlider.addChangeListener( 
            new ChangeListener()
            {
                public void stateChanged( ChangeEvent ev )
                {
                    //System.out.println( "Slider" );
                    JSlider slider = (JSlider) ev.getSource();
                    _pauseDelay = slider.getValue();
                }
            } );
           
        panel.add( delaySlider );
    } 

    //----------------------- updateDisplay ---------------------------
    private static int hideCount = 1000;
    private static int skipCount = 0;
    /**
     * This method invokes a special Swing method that immediately does
     * a repaint of the specified region of the specified component.
     * It's just what we need to update the display during recursion
     * We'll always do the entire GUI component. This will also sleep
     * for the specified msecs.
     * 
     * @param comp JComponent  the panel to update
     * @param msec int         the delay time
     */
    protected void updateDisplay( JComponent comp, int msec )
    {
        // First check if we want to show the updates or just some
        //    some of them. 
        if ( !showUpdates )
            return;
        if ( showSome )
        {
            if ( ++skipCount < hideCount )
                return;
            skipCount = 0;
        }
                      
        comp.paintImmediately( comp.getBounds() );
        try 
        { 
            Thread.sleep( msec ); 
        } 
        catch ( InterruptedException ie ) 
        {
            System.err.println( "updateDisplay InterruptedException: " +
                               ie.getMessage() );
        }
    }
    //--------------------- array2DtoString ------------------------
    /**
     * This method is a framework for converting a 2D array of anything
     * to a String with 1 blank at the beginning of each line, 1 blank 
     * between the elements on the line and a line feed at the end 
     * of each row of the array.
     * 
     * The idea is for you to modify this method method to accept an
     * array of whatever class you build your game board and to access
     * the r,c element with the appropriate "getter" method to get the
     * value of that element in order to print it.
     * 
     * @param array int[][]   2D array to be converted to a String rep
     * @return String         String with 1 line per row of array
     */
    public static String array2DtoString( int[][] array )
    {
        StringBuffer buf = new StringBuffer();
        for ( int r = 0; r < array[ 0 ].length; r++ )
        {
            for ( int c = 0; c < array[ 0 ].length; c++ )
            {
                buf.append( " " + array[ r ][ c ] );
            }
            buf.append( "\n" );
        }
        return buf.toString();
    }    
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //------------------------------- main ----------------------------
    /** 
     * Convenience main for starting program.
     * @param argv String[]  command line arguments
     * @throws IOException  on missing file
     */
    public static void main( String [] argv ) throws IOException
    {
        if ( argv.length == 0 )
            new Sudoku( "Sudoku from GUI", "test1.txt" );
        else
            new Sudoku( "Sudoku from GUI", argv[ 0 ] );
    }
}
