import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
/**
 * Square.java.
 * Invididual square class.
 * 
 * @author Kenneth Hite
 * 6/24/16 CS416
 */
public class Square extends JLabel 
{
    private Border border;
    
    /**
     * Constructor.
     */  
    public Square( ) 
    { 
        super( );
        this.setHorizontalAlignment( SwingConstants.CENTER );
        this.setVerticalAlignment( SwingConstants.CENTER );
        this.setSize( 50, 50 );
        border = BorderFactory.createLineBorder( Color.BLACK  );
        this.setBorder( border );
        this.setFont( new Font ( "Serif", Font.PLAIN, 20 ) );
        this.setBackground( Color.WHITE );
    }
}
