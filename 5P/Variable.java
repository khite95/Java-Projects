/**
 * Variable.java.
 * 
 * @author Kenneth Hite
 * CS416 7/1/2016
 * 
 */
public class Variable extends Operand
{
    private String string;
    /** 
     * Variable constructor.
     *
     * @param s String.
     */
    public Variable( String s )
    {
        string = s;
    }

    /** 
     * print.
     *
     * @return String.
     */
    public String printable()
    {
        String returnstring = "@" +  string;
        return returnstring;
    }
    
    /** 
     * getOP.
     * 
     * @return String.
     */
    public String getOP()
    {
        return string;
    }
}