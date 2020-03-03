/**
 * DrawPanel: template for JPanel class for a Swing based application.
 * 
 * Last modified: rdb 01/22/13 to use ArrayList of AShapes
 */

import java.awt.*;
import javax.swing.*;
import java.util.*;

public class DrawPanel extends JPanel
{
    //------ instance variables for contents of panel -------
    ArrayList<AShape> aObjects;    // all A-objects that need to be drawn
    
    //------ Constructor ------------
    public DrawPanel()
    {
        super();
        setLayout( null );   // need this in order to draw in the panel.
        this.setBackground( Color.GRAY );
        
        // Create graphical AShapes and put into a list
        aObjects = new ArrayList<AShape>();
        
        JSnowMan man = new JSnowMan( 50, 50 );
        this.add( man );
        man = new JSnowMan( 250, 50 );
        this.add( man );
        man = new JSnowMan( 50, 300 );
        this.add( man );
        
        // create two JPolygon objects; nothing will appear until
        //    JPolygon implementation is completed
        int[] houseX = { 0, 0, 15, 15, 25, 25, 40, 40, 20 };
        int[] houseY = { 20, 60, 60, 30, 30, 60, 60, 20, 0 };
        
        JPolygon house = new JPolygon( houseX, houseY );
        house.setFrameColor( Color.BLACK );
        house.setFillColor( Color.GREEN );
        this.add( house );
        house.setLocation( 400, 100 );
        
        int[] hexagonX = { 0, 0, 20, 40, 40, 20  };
        int[] hexagonY = { 20, 40, 60, 40, 20, 0 };
        JPolygon hex = new JPolygon( hexagonX, hexagonY );
        hex.setLocation( 500, 100 );
        this.add( hex );
        
        int[] blobX = { 10, 40, 50, 70, 80, 100  };
        int[] blobY = { 40, 50, 60, 70, 80, 90 };
        JPolygon blob = new JPolygon( blobX, blobY );
        blob.setLocation( 200, 200 );
        blob.setFillColor( Color.BLUE );
        this.add( blob );
        
        int[] blob2X = { 0, 10, 30, 40, 50, 70  };
        int[] blob2Y = { 10, 20, 40, 50, 60, 200 };
        JPolygon blob2 = new JPolygon( blob2X, blob2Y );
        blob2.setLocation( 300, 300 );
        blob2.setFillColor( Color.YELLOW );
        this.add( blob2 );
        
        AEllipse a = new AEllipse( 500, 300 );
        aObjects.add( a );
        ARectangle r = new ARectangle( 400, 300 );
        aObjects.add( r );
    }
    //--------- paintComponent( Graphics ) ---------------
    public void paintComponent( Graphics aBrush )
    {
        super.paintComponent( aBrush );
        
        // draw each AShape on the panel
        Graphics2D brush2D = (Graphics2D) aBrush;
        for ( AShape shape: aObjects )
            shape.display( brush2D );
    }
}