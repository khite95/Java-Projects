//+++++++++++++++++++++ Reporter +++++++++++++++++++++++++++++++
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;

/**
 * Reporter receives pre-defined report information at various points
 *     during execution. Depending on static settings, these
 *     reports may be presented in a JOptionPane, or just recorded in a LOG 
 *     file or formatted and printed to standard output or a file.
 * This is essentially a collection of static methods.
 * 
 * @author rdb
 * 02/09/15 
 */

public class Reporter
{
    //-------------------- class variables ---------------------------
    static boolean interactive = true;
    
    //-------------------- reportEval ------------------------------    
    /**
     * Called whenever the program wishes to report the successful evaluation.
     *
     * @param expr String       postfix string being evaluated
     * @param value Number      its value
     */
    public static void reportEval( String expr, Number value )
    {
        String msg;
        report( "Expression: " + expr + " --> " + value );
    }
    
    //-------------------- reportFail ------------------------------    
    /**
     * Called whenever the program wishes to report failed evaluation.
     *
     * @param expr String       postfix string being evaluated
     * @param msg  String       error info
     */
    public static void reportFail( String expr, String msg )
    {
        report( "Failed evaluation of |" + expr + "|\n" + msg );
    }
    
    //-------------------- log -------------------------------
    /**
     * Log a message to the standard output, but don't put it on popup box.
     * @param msg String
     */
    public static void log( String msg )
    {
        System.out.println( msg );
    }            

    //-------------------- report -------------------------------
    /**
     * Report any message.
     * @param msg String
     */
    public static void report( String msg )
    {
        if ( interactive )
            JOptionPane.showMessageDialog( null, msg );
        else
            System.out.println( msg );
    }            
}