import javax.swing.*;
import java.util.*;
import java.io.*;
/**
 * Lexer.java - parses string input representing an infix arithmetic
 *              expression into tokens.
 *              The expression can use the operators =, +, -, *, /, %.
 *              and can contain arbitrarily nested parentheses.
 *              The = operator is assignment and must be absolutely lowest
 *              precedence.
 * March 2009
 * rdb
 * March 2016
 * ah: Simplified to do just lexical analysis.
 * 
 * @author Kenneth Hite
 * CS416 7/1/2016
 */
public class Lexer //extends JFrame
{
    //----------------------  class variables  ------------------------
    
    //---------------------- instance variables ----------------------
    
    private SymbolTable  _symbolTable;
    
    //----------- constants
    private String   dashes = " ---------------------------- ";
    private String   endOutputBlock =
        "====================================================================";
    
    //--------------------------- constructor -----------------------
    /**
     * Create the Lexer.
     * If there is a command line argument use it as an input file.
     * otherwise invoke an interactive dialog.
     * 
     * @param args String[ ]
     */
    public Lexer( String[] args )
    {
        _symbolTable = SymbolTable.instance();
        
        if ( args.length > 0 )
            processFile( args[ 0 ] );
        else
            interactive();
    }
    //--------------------- processFile -----------------------------
    /**
     * Given a String containing a file name, open the file and read it.
     * Each line should represent an expression to be parsed.
     * For each line, process it, and print the result to System.out.
     * 
     * @param fileName String
     */
    public void processFile( String fileName )
    {
        try
        {
            Scanner scan = new Scanner( new File( fileName ) );
            while( scan.hasNextLine( ) )
            {
                String s = scan.nextLine( );
                System.out.println( processLine( s ) );
            }    
        } 
        catch ( FileNotFoundException e )
        {
            System.out.println( "File not found" );
        }
    }
    
    //--------------------- processLine -----------------------------
    /**
     * Parse each input line and do the right thing; return the.
     *    "results" which can be null.
     * 
     * @param line String
     * @return String
     */
    public String processLine( String line )
    {
        System.out.println(  "Input: " + line );
        String trimmed = line.trim();
        if ( trimmed.length() == 0 || trimmed.charAt( 0 ) == '#' )
            return line;
        else
            return processExpr( trimmed );
    }
    //--------------------- interactive -----------------------------
    /**
     * Use a file chooser to get an file name interactively, then
     * go into a loop prompting for expressions to be entered one
     * at a time.
     */
    public void interactive()
    {
        JFileChooser fChooser = new JFileChooser( "." );
        fChooser.setFileFilter( new TextFilter() );
        int choice = fChooser.showDialog( null, "Pick a file of expressions" );
        if ( choice == JFileChooser.APPROVE_OPTION )
        {
            File file = fChooser.getSelectedFile();
            if ( file != null )
                processFile( file.getName() );
        }
        
        String prompt = "Enter an arithmetic expression: ";
        String expr = JOptionPane.showInputDialog( null, prompt );
        while ( expr != null && expr.length() != 0 )
        {
            String result = processLine( expr );
            JOptionPane.showMessageDialog( null, expr + "\n" + result );
            expr = JOptionPane.showInputDialog( null, prompt );
        }
    }
    //------------------ processExpr( String ) -------------------------
    /**
     * Get all fields in the expression, and
     * generate tokens for all of them, and
     * return a String representation of all of them.
     * 
     * @param line String
     * @return String
     */
    public String processExpr( String line )
    {
        Scanner scan = new Scanner( line );
        String expression = "";
        while( scan.hasNext( ) )
        {
            try
            {
                EToken eToken = TokenFactory.makeToken( scan.next() );
                if( eToken != null )
                {
                    if( !scan.hasNext( ) )
                    {
                        expression += eToken;
                    }
                    else
                    {
                        expression += eToken + ",";
                    }
                }
            } 
            catch ( Exception e )
            {
                System.out.println( e.getMessage() );
                return "";
            }
        }
        return expression;
    }
    
    //---------------------------- TextFilter -----------------------------
    /**
     * This class is used with FileChooser to limit the choice of files.
     * to those that end in *.txt
     */
    public class TextFilter extends javax.swing.filechooser.FileFilter
    {
        /**
         * accept.
         * 
         * @param f File
         * @return boolean
         */
        public boolean accept( File f )
        {
            if ( f.isDirectory() || f.getName().matches( ".*txt" ) )
                return true;
            return false;
        }
        /**
         * getDescription.
         * 
         * @return String
         */
        public String getDescription()
        {
            return "*.txt files";
        }
    }
    //--------------------- main -----------------------------------------
    /**
     * main.
     * 
     * @param  args String[ ]
     */
    public static void main( String[] args )
    {
        Lexer app = new Lexer( args );
    }
}