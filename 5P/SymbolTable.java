import java.util.*;
/**
 * SymbolTable - this class  maintains a symbol table for variables.
 * 
 * This functionality lends itself to a class that uses the Singleton pattern.
 * That is, it enforces a restriction that only 1 instance can ever be created.
 * 
 * @author Kenneth Hite
 * CS416 7/1/2016
 */
public class SymbolTable
{
    //--------------------- class variables -------------------------------
    private static SymbolTable _theTable = null;
    
    //--------------------- instance variables ----------------------------
    // Use a Hashtable or a HashMap to store information (value) using the id 
    // as the key
    ///////////////////////////////////////////////////////////
    private Hashtable< String, Number > hTable;
    
    
    //------------- constructor --------------------------------------------
    /**
     * Note the constructor is private!
     */
    private SymbolTable()
    {
        //////// Create the hashtable or hashmap ///////////////////
        Hashtable< String, Number > numbers
            = new Hashtable< String, Number >();
        hTable = numbers;
    }
    //------------- instance -----------------------------------------------
    /**
     * user code must call a static method to get the reference to the.
     * only allowed instance -- first call creates the instance.
     * 
     * @return SymbolTable
     */
    public static SymbolTable instance()
    {
        if ( _theTable == null )
            _theTable = new SymbolTable();
        return _theTable;
    }
    
    //------------------------ setValue( String, float  ) ---------------------
    /**
     * Set an identifier's value to the specified value.
     * 
     * @param var String
     * @param num float
     */
    public void setValue( String var, float num )
    {
        /////////////////////////////////////////////////////// 
        //    Need to save information into the hash table
        //
        // You are allowed to change the signatures; for example, you
        //    can use Float or Number as the parameter type, both
        //    here and in getValue
        //////////////////////////////////////////////////////////
        Number n = new Number( num );
        hTable.put( var, n );
    }
    //------------------------ getValue( String ) ---------------------------
    /**
     * Look up an identifier in the hash table and return its value.
     * If the identifier is not in the table, add it with a value of 0
     * and return 0.
     * 
     * @param var String
     * @return float
     */
    public float getValue( String var )
    {
        /////////////////////////////////////////////////////////////
        //  Use var as hash table key get value; return it as a float
        //    or a Number (Float)
        ////////////////////////////////////////////////////////////
        if( hTable.containsKey( var ) )
        {
            Number n = hTable.get( var );
            return n.getToken();      
        } 
        else
        {
            setValue( var , 0 );
            return 0;
        }
    }
    //------------------------- toString() -------------------------------
    /**
     * toString.
     * 
     * @return String
     */
    public String toString()
    {
        String p = "";
        for ( String key : hTable.keySet( ) )
        {
            p += "{ " + key + " : " + getValue( key )
                + " }" + "\n";
        }
        return p;
    }
    //------------------------- toString() -------------------------------
    /**
     * print all.
     */
    public void printAll()
    {
        System.out.println( toString( ) );
    }
    //--------------------------- main -----------------------------------
    /**
     * Simple unit testing for SymbolTable.
     * 
     * @param args String[ ]
     */
    public static void main( String[] args )
    {
        SymbolTable st = SymbolTable.instance();
        st.setValue( "a", 4.0f );
        float val = st.getValue( "a" );
        System.out.println( "Test: should print 4: " + val );
        
        val = st.getValue( "b" );
        System.out.println( "Test: should print 0: " + val );
        
        st.setValue( "a", 6.0f );
        val = st.getValue( "a" );
        System.out.println( "Test: should print 6: " + val );
        
        System.out.println( st.toString( ) );
        st.printAll( );
    }
}