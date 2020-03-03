import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
/**
 * SquareArray.java.
 * Holds all the squares in a 2D array.
 * 
 * @author Kenneth Hite
 * 6/24/16 CS416
 */
public class SquareArray extends JPanel
{
    private Square[][] square;
    private char[][] values;
    private String[][] stuck;
    private Color clear;
    private int count;
    private int[][] ar;
    private GUI g;
    private int back = 0;
    private int rec = 0;
    
    /**
     * Constructor.
     * 
     * @param cA char
     * @param gui Gui
     */
    public SquareArray( char[][] cA, GUI gui ) 
    { 
        super( );
        clear = new Color( 0f, 0f, 0f, 0.0f );
        this.setBorder( new LineBorder( Color.BLACK, 2 ) );
        this.setLayout( new GridLayout( 9, 9 ) );
        values = new char[ 9 ][ 9 ];
        square = new Square[ 9 ][ 9 ];
        stuck = new String[ 9 ][ 9 ];
        ar = new int[ 9 ][ 9 ];
        values = cA;
        g = gui;
        addSquares( );
    }
    
    /**
     * Make board.
     * 
     * @param cA char
     */
    public void makeBoard( char[][] cA )
    {
        values = cA;
        newSquares( );    
    }
    
    /**
     * New Squares.
     */
    public void newSquares( )
    {
        rec = 0;
        back = 0;
        for ( int row = 0; row < 9; row++ ) 
        {
            for ( int col = 0; col < 9; col++ ) 
            {
                if( values[ row ][ col ] == Character.MIN_VALUE  )
                {
                    stuck[ row ][ col ] = "0";
                    ar[ row ][ col ] = 0;
                    square[ row ][ col ].setForeground( clear );
                }
                else
                {
                    stuck[ row ][ col ] = null;
                    ar[ row ][ col ] = 
                        Character.getNumericValue( values[ row ][ col ] ); 
                    square[ row ][ col ].setForeground( Color.BLACK );
                }
                square[ row ][ col ].setText( String.valueOf 
                    ( values[ row ][ col ] ) ); 
                //System.out.println( stuck[ row ][ col ] + "\n" );
                //System.out.print( ar[ row ][ col  ] + " " );
                //Array2DtoString
            }
        }
    }
    
    /**
     * Add squares.
     */
    public void addSquares( )
    {
        for ( int row = 0; row < 9; row++ ) 
        {
            for ( int col = 0; col < 9; col++ ) 
            {
                square[ row ][ col ] = new Square( );
                if( values[ row ][ col ] == Character.MIN_VALUE  )
                {
                    stuck[ row ][ col ] = "0";
                    ar[ row ][ col ] = 0;
                }
                else
                {
                    stuck[ row ][ col ] = null;
                    ar[ row ][ col ] = 
                        Character.getNumericValue( values[ row ][ col ] ); 
                }
                square[ row ][ col ].setText( String.valueOf 
                    ( values[ row ][ col ] ) ); 
                this.add( square[ row ][ col ], BorderLayout.CENTER );
                //System.out.println( stuck[ row ][ col ] + "\n" );
                //System.out.print( ar[ row ][ col  ] + " " );
                //Array2DtoString
            }
        }
    }
    
    /**
     * Solve.
     * 
     * @param row int
     * @param col int
     * @param jump int
     */  
    public void solve( int row, int col, int jump )
    {
        stuck = solveHelper( row, col, jump );       
    }
    /**
     * Done.
     */
    public void done( )
    {
        for ( int r2 = 0; r2 < 9; r2++ ) 
        {
            for ( int c2 = 0; c2 < 9; c2++ ) 
            {
                if( stuck[ r2 ][ c2 ] != null  )
                {
                    square[ r2 ][ c2 ].setForeground( Color.RED );
                }
            }
        }
    }
    
    /**
     * Print final result.
     */
    public void printAr( )
    {
        for ( int r3 = 0; r3 < 9; r3++ ) 
        {
            for ( int c3 = 0; c3 < 9; c3++ ) 
            {
                System.out.print( square[ r3 ][ c3 ].getText( ) + " " );
                if( c3 == 8 )
                    System.out.println( "" );
            }
        } 
        System.out.print( rec + " " + back ); 
    }
    /**
     * Solve Helper.
     * 
     * @param row int
     * @param col int
     * @param jump int
     * 
     * @return String[][]
     */  
    public String[][] solveHelper( int row, int col, int jump )
    {
        if( row == 9 || row == -1 )
        {
            done( );
            if( row == -1 )
                System.out.println( "No solution" );
            else
            {
                printAr( );return stuck;
            }
        }
        int cur = 1;
        if( stuck[ row ][ col ] != null && jump == -1 )
        {
            if( Integer.parseInt( stuck[ row ][ col ] ) > 0 )
            {
                ar[ row ][ col ] += 1; cur = ar[ row ][ col ];
                if( ar[ row ][ col ] == 10 )
                {
                    ar[ row ][ col ] = 0;
                    if( col == 0  )
                    {
                        rec++;back++;return solveHelper( row - 1, 8, jump );
                    }
                    else
                    {
                        rec++;back++;
                        return solveHelper( row, col - 1, jump );     
                    }
                }
            }
        }
        if( stuck[ row ][ col ] == null || cur == 10 )
        {
            if( jump == 1 || jump == 0 )
            {
                if( col == 8 )
                {
                    rec++; return solveHelper( row + 1, 0, jump );
                }
                else
                {
                    rec++; return solveHelper( row, col + 1, jump );   
                }
            }
            else if( jump == -1 )
            {
                if( col == 0  )
                {
                    rec++; back++; return solveHelper( row - 1, 8, jump );
                }
                else
                {
                    rec++; back++; return solveHelper( row , col - 1, jump );  
                }
            }
        }
        else
            jump = 0;
        if( GUI.showSome )
        {
            if( count >= 1000 )
            {
                count = 0;done( );
            }
        }
        while( jump == 0 )
        {
            if( ar[ row ][ col ] == 0 )
                ar[ row ][ col ] = 1;
            for( int r = 0; r < 9; r++ )
            {
                boolean hit = false;hit = false;
                for( int k = 0; k < 9; k++ )
                {
                    if( row == k )
                    {
                        if( ( ar[ row ][ col ] == ar[ row ][ k ]
                            && ar[ row ][ col ] != 0 && col != k ) )
                        {
                            cur++; count++; hit = true; k = 9;
                        }
                    }
                    else if( col == k )
                    {
                        if( ( ar[ row ][ col ] == ar[ k ][ col ] 
                            && ar[ row ][ col ] != 0 && row != k ) )    
                        {
                            cur++; count++; hit = true; k = 9;
                        }
                    }   
                    else if( ( ar[ row ][ col ] == ar[ k ][ col ] 
                        && ar[ row ][ col ] != 0 && row != k ) || 
                        ( ar[ row ][ col ] != 0 
                        && ar[ row ][ col ] == ar[ row ][ k ] 
                        && col != k ) )
                    {
                        cur++; count++; hit = true; k = 9;
                    }
                }
                if( cur > 9 )
                {
                    stuck[ row ][ col ] = "0";
                    square[ row ][ col ].setText( "" );ar[ row ][ col ] = 0;
                    square[ row ][ col ].setForeground( clear );
                    if( GUI.showUpdates && !GUI.showSome )
                        square[ row ][ col ].setForeground( Color.RED );
                    jump = -1; g.updateDisplay( this, GUI._pauseDelay );
                }
                if( jump == -1 )
                    r = 9;
                if( !hit )
                {
                    r = 9; jump = 1;  
                }
                if( cur < 10 )
                {
                    ar[ row ][ col ] = cur; 
                    stuck[ row ][ col ] = String.valueOf( cur );
                    square[ row ][ col ].setText( String.valueOf( cur ) );
                    square[ row ][ col ].setForeground( clear );
                    if( GUI.showUpdates && !GUI.showSome )
                        square[ row ][ col ].setForeground( Color.RED );
                }
                g.updateDisplay( this, GUI._pauseDelay );
            }
        }
        if( jump == 1 )
        {
            if( col == 8 )
            {
                rec++; return solveHelper( row + 1, 0, jump );
            }
            else
            {
                rec++; return solveHelper( row, col + 1, jump );     
            }
        }
        else if( col == 0 )
        {
            rec++; back++; return solveHelper( row - 1, 8, jump );
        }
        else
        {
            rec++; back++; return solveHelper( row, col - 1, jump );     
        }
    }   
}
