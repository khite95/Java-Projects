//+++++++++++++++++++++++++++++ GUI ++++++++++++++++++++++++++++++++++
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;

/**
 * GUI.java -- class for managing interaction tools for the application.
 *
 * @author rdb
 * 
 * 03/18/08: rdb
 * 07/13/10: jb, edited for GraphTool tool
 */

public class GUI extends JPanel 
{
    //------------------------- instance variables ---------------------
    private GraphTool    tool;       // the tool object
    
    //------------------------- constructor ----------------------------
    /**
     * GUI constructor creates the interface and waits for interaction.
     *    The GraphTool object responds to the interaction events. 
     * 
     * @param g GraphTool     Contains responder code.
     */
    public GUI( GraphTool g ) 
    {
        super();                 
        setLayout( new GridLayout( 2, 1 ) );
        tool = g;
                
        ///////////////////////////////////////////////////////
        // build the gui
        ///////////////////////////////////////////////////////
        String[] buttonLabels = 
        { 
            "New graph", "Start/End", "Find path", "Reset", "Show"//, "Shortest"
        };
        JPanel buttonMenu = makeButtonMenu( buttonLabels );
        this.add( buttonMenu );
        
        JSeparator sep = new JSeparator( SwingConstants.HORIZONTAL );
        sep.setForeground( Color.BLACK );
        this.add( sep );     
    }
    //------------------- makeButtonMenu -------------------------------
    /**
     * Create the button menu for this application.
     * 
     * @param labels String[]
     * @return JPanel
     */
    private JPanel makeButtonMenu( String[] labels )
    {
        JPanel bMenu = new JPanel(); 
        JButton button;
        for ( int i = 0; i < labels.length; i++ )
        {
            button = new JButton( labels[ i ] );
            bMenu.add( button );
            button.addActionListener( new ButtonListener( i ) );
        }
        bMenu.setSize( 40, 400 );
      
        return bMenu;
    }
    //+++++++++++++++++ ButtonListener inner class +++++++++++++++++++++
    /**
     * ButtonListener -- distributes button events to appropriate methods
     *                   of the GUI class.
     */
    public class ButtonListener implements ActionListener
    {
        int _buttonId;
        /** ButtonListener object for each button. 
         * @param buttonId int 
         */
        public ButtonListener( int buttonId )
        {
            _buttonId = buttonId;
        }
        /** Listener method.
          * @param ev ActionEvern t
          */
        public void actionPerformed( ActionEvent ev )
        {
            String msg = null;
            switch ( _buttonId )
            {
                case 0:
                    tool.newGraph();
                    break;
                case 1:
                    tool.defineStartEnd();
                    break;
                case 2:
                    tool.findAPath();
                    break;
                case 3:
                    tool.unmarkGraph();
                    break;
                case 4:
                    tool.showPath();
                    break;
                /*****************
                case 5:
                    tool.findShortestPath();
                    break;
                ********************/
            }      
        }
    }
    //----------------- main ------------------------------------------   
    /**
     * Convenience main that invokes the application.
     * 
     * @param args String[]     command line arg is filename
     */
    public static void main( String [ ] args ) 
    {
        GraphTool.main( args );
    }   
}   
