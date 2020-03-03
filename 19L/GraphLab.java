//------------------------ GraphLab.java ------------------------------
import javax.swing.*;
import java.awt.*;

/**
 * GraphLab -- App class for the GraphLab code.
 * @author rdb
 * 11/01/10
 * 
 * Editted
 * 04/01/14 -- formatting, javadoc
 */

public class GraphLab extends JFrame
{
    //---------------------- class variables -------------------------
    static boolean interactive = true;
    
    //---------------------- instance variables ----------------------
    private GraphTool _appPanel;      // the app's JPanel
    
    //--------------------------- constructor ------------------------
    /**
     * Construct application.
     * 
     * @param title String       Window title.
     * @param fileName String    File name for initial graph defn. file
     */
    public GraphLab( String title, String fileName )     
    {
        super( title );
        
        this.setBackground( Color.LIGHT_GRAY );
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
        _appPanel = new GraphTool( fileName );
        this.add( _appPanel );
        //this.pack();
        
        this.setSize( _appPanel.getWidth(), _appPanel.getHeight() + 100 );
        
        this.setVisible( true );
    }
    //------------------ main -----------------------------------------
    /**
     * Main application.  
     *
     * @param args String[]     command line arg is filename
     */
    public static void main( String [ ] args ) 
    {
        if ( args.length == 1 )
            new GraphLab( "GraphLab Tool", args[ 0 ] );
        else
            new GraphLab( "GraphLab Tool", "a_graph0.txt" );
    }
}
