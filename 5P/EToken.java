/**
 * EToken - an abstract class representing a token in an expression.
 *             subclasses are Operator and Operand
 * 
 * @author Kenneth Hite
 * CS416 7/1/2016
 * 
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
     * Non-standard toString.
     * 
     * @return String
     */
    public String toString()
    {
        return printable();
    }

    //------------------ main unit test ------------------------
    /**
     * some basic unit tests.
     * 
     * @param  args String[ ]
     */
    public static void main( String[] args )
    {
        try
        {
            EToken plus  = TokenFactory.makeToken( "+" );
            EToken times = TokenFactory.makeToken( "*" );
            EToken a     = TokenFactory.makeToken( "a" );
            EToken one   = TokenFactory.makeToken( "1" );

            System.out.println( a + " " + plus + " " + one + " "
                               + times + " " + a );
        }
        catch( Exception e ) 
        {
            System.out.println( "Bad token: " + e );
        }
    }
}