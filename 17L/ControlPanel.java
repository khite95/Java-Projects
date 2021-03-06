/** 
 * ControlPanel.java:
 * A panel of sliders and other controls for the application
 *
 * @author Matthew Plumlee
 * modified by rdb 
 * 
 * CS416 Spring 2008
 */

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class ControlPanel extends JPanel 
{
    //---------------- instance variables ---------------------------
    private Component  _parent;
    private TreeApp    _app;
    
    //------------------- constructor -------------------------------
    /**
     * Container parent of the control panel is passed as an argument
     * and a reference to the application class.
     */
    public ControlPanel( Component parent, TreeApp app ) 
    {
        super( new GridLayout(0, 1) );
        _parent = parent;
        _app    = app;
        
        //create sliders
        add( new DataSizeSlider() ); 
        add( new NodeWidthSlider() );
        
        //create misc check boxes and quit button
        JPanel buttonRow = new JPanel();
        
        buttonRow.add( new SeedSpinner() );
        
        add( buttonRow );
    }
    //+++++++++++++++++++++++ SeedSpinner class ++++++++++++++++++++
    /**
     * private inner class to update spinner for recursive depth parameter.
     */
    private class SeedSpinner extends LabeledSpinner 
        implements ChangeListener
    {
        //---------------- constructor ---------------------------------
        public SeedSpinner () 
        {
            super( "Random seed", 0, _app.maxSeed, _app.rngSeed );
            addChangeListener( this );
        }
        //------------------------- valueChanged( int ) ----------------
        public void stateChanged( ChangeEvent ev ) 
        {
            _app.rngSeed = (Integer)((JSpinner)ev.getSource()).getValue();
        }
    }
    
    //+++++++++++++++++++++++ DataSizeSlider class ++++++++++++++++++++
    /**
     * private inner class to update the child size ratio with a slider
     */
    private class DataSizeSlider extends LabeledSlider
                                         implements ChangeListener
    {
        //---------------- constructor ---------------------------------
        public DataSizeSlider () 
        {
            super( "Data size", _app.minDataSize, _app.maxDataSize, 
                  _app.defaultDataSize );
            JSlider slider = getJSlider();
            slider.setMajorTickSpacing( 5 );
            slider.setMinorTickSpacing( 1 );
            addChangeListener( this );   // DataSizeSlider is its own listener
        }
        //------------------- stateChanged( ChangeEvent ev ) -----------
        public void stateChanged( ChangeEvent ev ) 
        {
            _app.dataSize = ((JSlider)ev.getSource()).getValue();
        }
    }
    //+++++++++++++++++++++++ NodeWidthSlider class ++++++++++++++++++++
    /**
     * private inner class to update the child size ratio with a slider
     */
    private class NodeWidthSlider extends LabeledSlider 
                                  implements ChangeListener
    {
        //---------------- constructor ---------------------------------
        public NodeWidthSlider () 
        {
            super( "Node width", _app.minNodeWidth, _app.maxNodeWidth, 
                  _app.defaultNodeWidth );
            JSlider slider = getJSlider();
            slider.setMajorTickSpacing( 5 );
            slider.setMinorTickSpacing( 1 );
            addChangeListener( this );   // it is its own listener
        }
        //------------------- stateChanged( int ) ----------------------
        public void stateChanged( ChangeEvent ev ) 
        {
            _app.setNodeWidth( ((JSlider)ev.getSource()).getValue() );
        }
    }
}
