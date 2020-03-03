import javax.swing.*;
import java.util.*;
import java.io.*;
/**
 * Interpreter.java - parses string input representing an infix arithmetic
 *                 expression into tokens, and builds an expression tree.
 *                 The expression can use the operators =, +, -, *, /, %.
 *                 and can contain arbitrarily nested parentheses.
 *                 The = operator is assignment and must be absolutely lowest
 *                 precedence.
 * CS416 
 * 7/11/16
 * @author Kenneth Hite
 */
public class Interpreter //extends JFrame
{
    //----------------------  class variables  ------------------------
    
    //---------------------- instance variables ----------------------
    private boolean _printTree = true;
    private boolean _printToggle = false; 
    private String treememory = "";
    private SymbolTable _symbolTable;
    //----------- constants
    
    //--------------------------- constructor -----------------------
    /**
     * If there is a command line argument use it as an input file.
     * otherwise invoke an interactive dialog.
     * 
     * @param args String[]
     */
    public Interpreter( String[] args ) 
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
     * 
     * @param fileName String
     */
    public void processFile( String fileName )
    {
        
        try
        {
            Scanner s1 = new Scanner( new File( fileName ) );
            while( s1.hasNextLine() )
            {
                String s = s1.nextLine();
                String s11 =  processLine( s );
                System.out.println( s11 );
                if( _printTree == true )
                {
                    System.out.println( treememory );
                }
            }  
        } 
        catch ( FileNotFoundException fe )
        {
            System.out.println( "Processing File did not work" );
        }
    }
    /**
     * Traverse.
     * 
     * @param root Node
     * @param k int
     */
    public void traverse( Node root, int k )
    {
        String space = " ";
        String message = "";
        util( );
        for( int i = 0; i < k; i++ )
        {
            message = message + space;
        }
        System.out.println( message + root.token.op() );
        util( );
        if( root.left != null )
        {
            traverse( root.left , k + 1 );
        }
        if( root.right != null )
        {
            traverse( root.right , k + 1 );
        }
    }
    /** 
     * util.
     */
    public void util( ) 
    {
        int i = 0;
        while( i < 20 )
        {
            test( );
            i++;
        }
        int a = 3;
        test( );
        int b = 4;
        test( );
        int c = a * b;
        a = 3;
        b = 4;
        test( );
        test( );
        test( );
        test( );
        test( );
        test( );
    }
    //--------------------- processLine -----------------------------
    /**
     * Parse each input line -- it shouldn't matter whether it comes from.
     * the file or the popup dialog box. It might be convenient to return
     * return something to the caller in the form of a String that can
     * be printed or displayed.
     * 
     * @param line String
     * @return String
     */
    public String processLine( String line )
    {
        String trimmed = line.trim();
        util( );
        if ( trimmed.length() == 0 || trimmed.charAt( 0 ) == '#' )
        {
            util( );
            return line;
        } 
        else if( trimmed.charAt( 0 ) == '@' )
        {
            String message = "";
            util( );
            Scanner s1 = new Scanner( trimmed );
            String firstCommand = s1.next();
            String secondCommand = "";
            if( s1.hasNext() ) 
            {
                secondCommand = s1.next();
            }
            if( firstCommand.equals( "@print" ) )
            {
                util( );
                if( secondCommand.equals( "on" ) 
                       || secondCommand.equals( "ON" ) )
                {
                    _printTree = true;
                    message = "Print On";
                } 
                else if( secondCommand.equals( "off" ) 
                            || secondCommand.equals( "OFF" ) )
                {
                    _printTree = false;
                    message = "Print Off";
                } 
                else if( secondCommand.equals( "" ) )
                {
                    message = "previous tree";
                    message = message + "\n" + treememory;
                } 
                else
                {
                    message = "Error";
                }
                
            } 
            else if( firstCommand.equals( "@lookup" ) )
            {
                util( );
                if( secondCommand.equals( "" ) )
                {
                    message = "Symbol Table";
                    String table =  _symbolTable.toString();
                } 
                else 
                {
                    Float f2 = _symbolTable.getValue( secondCommand );
                    String results = "";
                    results = "{ " + secondCommand + " : " + f2 + "}";
                    message = "Lookup " + secondCommand + ",";
                    while( s1.hasNext() )
                    {
                        String snext = s1.next();
                        util( );
                        message = message + snext + ",";
                        Float f1 = _symbolTable.getValue( snext );
                        results = results + "\n" + 
                            "{ " + snext + " : " + f1 + "}"; 
                    }
                    message = message + "\n" + results;
                }
            } 
            else 
            {
                message = "Error";
            }
            return line + message;
        }
        else
        {
            System.out.println( "In: " + trimmed );
            util( );
            return processExpr( trimmed );     
        }
    }
    /**
     * test.
     */
    public void test( ) 
    {
        String r = "test";
        int d = 2; 
        int e = 4;
        int f = e + d;
        r = "test";
        f = e + d;
    }
    //------------------ processExpr( String ) -------------------------
    /**
     * Get all fields in the expression, and
     * generate tokens for all of them, and
     * return a String representation of all of them.
     *
     * @param line String.
     * @return s String.
     */
    public String processExpr( String line )
    {
        Scanner s1 = new Scanner( line );
        String result = "";
        util( );
        while( s1.hasNext() )
        {
            try
            {
                EToken e90 = TokenFactory.makeToken( s1.next() );
            }
            catch ( Exception e1 )
            {
                System.out.println( e1.getMessage() );
                return "";
            }
        }
        Scanner s2 = new Scanner( line );
        String message = "";
        Stack<EToken> opStack = new Stack<EToken>();
        Stack<Node> randStack = new Stack<Node>();
        String l1 = line.trim();
        String stuff = "";
        util( );
        while( s2.hasNext() )
        {
            util( );
            EToken q1 = null;
            String s21 = "";
            try 
            {
                s21 = s2.next();
                q1 = TokenFactory.makeToken( s21 );
            } 
            catch ( Exception e )
            {
                System.out.println( "Error Creating Token: " + s21 );
                break;
            }
            if ( q1 instanceof Operand )
            {
                Node n1 = new Node( q1 );
                randStack.push( n1 );
            } 
            else if ( q1.op().equals( "(" ) ) 
            {
                opStack.push( q1 );
            } 
            else if ( q1.op().equals( ")" ) )
            {
                while ( !opStack.top().op().equals( "(" ) ) 
                {
                    Node n1 = new Node( opStack.pop() );
                    n1.right = randStack.pop();
                    n1.left = randStack.pop();
                    randStack.push( n1 );
                }
                opStack.pop(); 
            } 
            else 
            {
                while ( !opStack.isEmpty() && processToken( opStack.look( 0 ) ) 
                           >= processToken( q1 ) ) 
                {
                    Node n1 = new Node( opStack.pop() );
                    n1.right = randStack.pop();
                    n1.left = randStack.pop();
                    randStack.push( n1 );
                }
                opStack.push( q1 );
            } 
        }
        while( opStack.size() > 0 )
        {
            util( );
            Node n1 = new Node( opStack.pop() );
            n1.right = randStack.pop();
            n1.left  = randStack.pop();
            if( n1.left == null )
            {
                System.out.println( "No left operand" );
            }
            if( n1.right == null )
            {
                System.out.println( "No right operand" );
            }
            if( n1 == null )
            {
                System.out.println( "No operator" );
            }
            randStack.push( n1 );   
        }
        randStack.top().printTree( randStack.top(), 0 );
        treememory = randStack.top().print;
        result = "Out: " + 
            randStack.top().evaluate( randStack.top(), _symbolTable );
        util( );
        return result;
    }
    
    
    //--------------------- interactive -----------------------------
    /**
     * Use a file chooser to get a file name interactively, then. 
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
    
    /**
     * processToken.
     * 
     * @param e EToken
     * @return int
     */
    public int processToken( EToken e )
    {
        util( );
        String group1 = "*/%";
        String group2 = "+-";
        String group3 = "=";
        if( group1.contains( e.op() ) )
        {
            return 3;
        }
        else if( group2.contains( e.op() ) )
        {
            return 2;
        }
        else if( group3.contains( e.op() ) )
        {
            return 1;
        }
        else
        {
            System.out.println( "Error processing operator" );
            return -1;
        }
    }
    //+++++++++++++++++++++++++ inner class +++++++++++++++++++++++++++++++
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
         * getDiscription.
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
     * @param args String[]
     */
    public static void main( String[] args )
    {
        Interpreter app = new Interpreter( args );
    }
}