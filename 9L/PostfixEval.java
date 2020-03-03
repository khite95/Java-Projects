//+++++++++++++++++++++++++++++ PostfixEval.java +++++++++++++++++++++++++++++
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
/**
 * PostfixEval.java -- Postfix evaluation
 * This program reads a string from the user that is a postfix expression
 * that must be parsed and evaluated. The tokens in the string can be 
 * integers or operators.
 * 
 * @author rdb
 */

public class PostfixEval //extends JFrame
{
    //---------------------- class variables -------------------------
    static boolean interactive = true;
    
    //------------ error messages -----------
    private static String tooFewOperands = "Too few operands: ";
    private static String unknownOperator = "Unknown operator: ";

    //---------------------- instance variables ----------------------
    Collection<String> _dictionary;
    
    private char      _option;
    
    //--------------------------- constructor -----------------------
    /**
     * Constructor takes a title for the window.
     * @param title String
     */
    public PostfixEval( String title )     
    {    
        Scanner batchIn = null;
        String inMessage = "Enter a postfix expression";      
        String input = null; 
        if ( interactive )
            input = JOptionPane.showInputDialog( null,  inMessage );
        else
        {
            batchIn = new Scanner( System.in );
            if ( batchIn.hasNextLine() )
                input = batchIn.nextLine();
        }
        
        while ( input != null && input.length() > 0 )
        {      
            Number value = evaluateExpression( input );
            if ( value != null )
                Reporter.reportEval( input, value );

            if ( interactive )
                input = JOptionPane.showInputDialog( null,  inMessage ); 
            else if ( batchIn.hasNextLine() )
                input = batchIn.nextLine();
            else 
                input = null;
        }
    }
    //----------------- evaluateExpression( String ) -------------------
    /**
     * Parse the input string and evaluate it as a postfix expression.
     * 
     * @param expr String     postfix expression string to be evaluated
     * @return String         non-null if error occurred
     */
    private Number evaluateExpression( String expr )
    {
        ////////////////////////////////////////////////////////
        // 2a. Edit line below to use Stack<Integer>
        //
        Stack<Integer> stack = new Stack<Integer>();
        //////////////////////////////////////////////////////////
        String  errorMsg = null;
        Scanner in = new Scanner( expr );
        boolean  inputError = false;
        while ( in.hasNext() && errorMsg == null )
        {
            String   token = in.next();
            Number   left  = null;
            Number   right = null;
            Integer  value = null;
            char     tok = 'v';
            Scanner  numCheck = new Scanner( token );
            if ( numCheck.hasNextInt() )
                value = new Integer( numCheck.nextInt() );
            else 
                tok = checkValidOperator( token );
            
            switch ( tok )
            {
                case 'v':     // An integer value was read
                    System.out.println( "Value read: " + value );
                    //////////////////////////////////////////////////////////
                    // 2b. need to push the value onto stack
                    //////////////////////////////////////////////////////////
                    stack.push( value );
                    
                    break;
                case '+':
                case '-':
                case '*':
                case '/':
                    System.out.println( "Operator read: " + tok );
                    //////////////////////////////////////////////////////////
                    // 2c. If there are at least 2 operands in the stack
                    //        invoke the execute method with operator token.
                    //     else set errorMessage to tooFewOperands
                    //////////////////////////////////////////////////////////
                    if( stack.size( ) >= 2 )
                    {
                        execute( stack, tok );
                    }
                    else
                    {
                        errorMsg = tooFewOperands;
                    }
                    break;
                case '?':
                    errorMsg = unknownOperator + ": " + token;
                    break;
            } 
            // Print the state of the stack, so we track what happens
            System.out.println( "------ Stack state -----" );
            System.out.println( stack );            
        }
        
        
        Number returnValue = null;
        //////////////////////////////////////////////////////////////
        // 2d. If an error was detected above, errorMsg will not be null,
        //        invoke Reporter.fail with the input string and errorMsg
        //     else 
        //        check that stack has just one entry. 
        //        if it doesn't
        //           invoke Reporter.fail with the input string and
        //           and the tooManyOperands error message with 
        //               a toString of the stack added to the end.
        //        else
        //           pop answer from stack into returnValue        //         
        ////////////////////////////////////////////////////////////////     
        if( errorMsg != null )
        {
            Reporter.reportFail( in.toString( ), errorMsg );
        }
        else
        {
            if( stack.size( ) == 1 )
            {
                returnValue = stack.pop( );
            }
            else
            {
                Reporter.reportFail( in.toString( ), "Too many operands: " 
                                        + stack.toString( ) );
            }
        }
        return returnValue;  // will be null on error, else the value
    }
    //----------------- execute( Stack, char ) --------------------
    /**
     * Execute  method gets the Stack as 1st argument and a char
     *         representing a valid operator.
     * This method assumes (pre-conditions) that there are 2 elements
     *    on the stack and that the char is a valid operator char.
     * These tests  must be made by the caller.
     * 
     * @param stack Stack<Integer>
     * @param operator char
     */
    private void execute( Stack<Integer> stack, char operator )
    {  
        //////////////////////////////////////////////////////////
        // 2c. (i)   pop 2 elements off stack, 
        //           first is right operand, second is left, 
        //     (ii)  do the operation, assign the value to a variable
        //     (iii) push result onto the stack
        //////////////////////////////////////////////////////////
        
        // (i) pop right then left operands
        int left = stack.pop( );
        int right = stack.pop( );
        int value = 0;
        
        
        switch ( operator )
        {
            case '+':
                // (ii) compute value of left + right 
                value = left + right;
                
                break;
            case '-':
                // (ii) compute value of left - right 
                value = left - right;
                
                
                break;
            case '*':
                // (ii) compute value of left * right 
                value = left * right;
                
                break;
            case '/':
                // (ii) compute value of left / right 
                value = left / right;
                
                break;
        }           
        // (iii) push result onto stack
        stack.push( value );
        
        return;   
    }
    //----------------- checkValidOperator( String ) --------------------
    /**
     * If the parameter is a valid operator, return the char that represents
     *    that operator ( + - * / ).
     * else 
     *    return '?'
     * 
     * The input String must have length 1 and that 1 character must be 
     * a valid arithmetic operator.
     * 
     * @param token String
     * @return char              a valid operator character: + - * or /
     *                           or ? if not valid operator found
     */
    private char checkValidOperator( String token )
    {  
        if ( token.length() != 1 )
            return '?';
        else 
        {
            char ch = token.charAt( 0 );
            switch ( ch )
            {
                case '+': 
                    return '+';
                case '-': 
                    return '-';
                case '*': 
                    return '*';
                case '/': 
                    return '/';
                default:  
                    return '?';
            }
        }
    }
    //----------------------- main ----------------------------------------
    /**
     * Main test vehicle.
     * @param args String[]  cmd line args. Any argument implies batch mode.
     */
    public static void main( String[] args )
    {
        if ( args.length > 0 )
            interactive = false;
        new PostfixEval( "PostfixEval" );
    }
}
