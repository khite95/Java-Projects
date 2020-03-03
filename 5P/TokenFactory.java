import java.util.*;
/**
 * TokenFactory: a factory class, which makes tokens from String fields.
 * This is an example of a Factory pattern.
 *
 * @author Kenneth Hite
 * CS416 7/1/2016
 */
public class TokenFactory
{
    /**
     * constructor.
     * 
     * @param s String
     * @return EToken
     */
    public static EToken makeToken( String s )
    {
        char[] operator = { '+', '-', '/', '*', '%', '(', ')', '=' };
        char[] letter = { 'a', 'b', 'c', 'd', 'f', 'g', 'h', 'i', 'j', 'k',
            'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
            'v', 'w', 'x', 'y', 'z' };
        String token = s;
        float value;
        int stringLength = s.length();
        try
        {
            
            value = Float.parseFloat( token );
            EToken e1 = new Number( value );
            //is a number
            return e1;
        }
        catch ( NumberFormatException e )
        {
            // it's not a number
            if( stringLength == 1 )
            {
                for( int i = 0; i < operator.length; i++ )
                {
                    if( operator[ i ] == s.charAt( 0 ) )
                    {
                        //is an Operator
                        EToken e2 = new Operator( s );
                        return e2;
                    }
                }
            } 
            //not an Operator
            Boolean validID = Character.isJavaIdentifierStart( s.charAt( 0 ) );
            Boolean validID2 = true;
            for( int j = 0; j < stringLength; j++ )
            {
                if( !Character.isJavaIdentifierPart( s.charAt( j ) ) )
                {
                    validID2 = false;
                }
            }
            if( validID && validID2 )
            {
                EToken e3 = new Variable( s );
                return e3;
            }
            else
            {
                if( !validID2 )
                {
                    System.out.println( "Token " + s + " "
                                           + "is invalid because "
                                           + "there is an "
                                           + "invalid character." );
                }
                else if( !validID )
                {
                    System.out.println( "Token " + s 
                                           + " is invalid the first "
                                           + "character contains a digit." );
                }
            }
            return null;
        }
    }
    
}