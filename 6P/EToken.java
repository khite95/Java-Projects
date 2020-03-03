/**
 * EToken - an abstract class representing a token in an expression.
 *             subclasses are Operator and Operand
 *
 * CS416 7/11/16
 * @author Kenneth Hite
 */
public abstract class EToken
{
    /** 
     * Non-standard toString.
     *
     * @return String
     */
    abstract public String printable();

    /** 
     * toString.
     * 
     * @return String
     */
    public String toString()
    {
        return printable();
    }

    /** 
     * getop.
     *
     * @return String
     */
    abstract public String getop();

    /**
     * op.
     * 
     * @return String
     */
    public String op()
    {
        return getop();
    }

    //------------------ main unit test ------------------------
    /** 
     * some basic unit tests.
     *
     * @param args String[]
     */
    public static void main( String[] args )
    {
        try
        {
            EToken plus  = TokenFactory.makeToken( "+" );
            EToken times = TokenFactory.makeToken( "*" );
            EToken a     = TokenFactory.makeToken( "a" );
            EToken one   = TokenFactory.makeToken( "1" );
        }
        catch ( Exception e )
        {
            System.out.println( "Bad token: " + e );
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
        d = 2; 
        e = 4;
        f = e + d;
    }
}
