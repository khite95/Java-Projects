/**
 * Operator.java.
 * 
 * @author Kenneth Hite
 * CS416 7/1/2016
 * 
 */
public class Operator extends EToken
{ 
    private String operator;
    /** 
     * Operator constructor.
     * 
     * @param string String.
     */
    public Operator( String string )
    {
        operator = string;  
    }

    /** 
     * getOP.
     *
     * @return String
     */
    public String getOP()
    {
        return operator;
    }
    
    /** 
     * print.
     * 
     * @return String
     */
    public String printable()
    {
        String format = "<" + operator + ">";
        return format;
    }

}