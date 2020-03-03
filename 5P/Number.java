/**
 * Number.java.
 * 
 * @author Kenneth Hite
 * CS416 7/1/2016
 * 
 */
public class Number extends Operand
{

    private Float token;

    /** 
     * Number.
     * 
     * @param s float.
     */
    public Number( float s )
    {
        token = s;
    }

    /** 
     * getToken.
     *
     * @return token float.
     */
    public float getToken()
    {
        return token;
    }

    /** 
     * print.
     * 
     * @return returnstring String.
     */
    public String printable( ) 
    {
        int tokenint =  Math.round( token );
        String returnstring = "@" +  tokenint;
        return returnstring;
    }
        
    /** 
     * getOP.
     * 
     * @return s String.
     */
    public String getOP()
    {
        String floatString = String.valueOf( token );
        return floatString;
    }
}