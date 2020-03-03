/**
 * Operand.java.
 * 
 * @author Kenneth Hite
 * CS416 7/1/2016
 * 
 */
public abstract class Operand extends EToken
{
    /**
     * Operand Constructor.
     */
    public Operand()
    {
        
    }
    
    /**
     * Non-standard toString.
     * 
     * @return String
     */
    abstract public String printable();
    
    /** 
     * Print.
     * 
     * @return s String.
     */
    public String print()
    {
        return null;
    }
    
    /** 
     * getOP.
     * 
     * @return s String.
     */
    public String getOP()
    {
        return null;
    }
}