/**
 * Node.java.
 * 
 * CS416 7/11/16
 * @author Kenneth Hite
 */
public class Node 
{
    //--------------- instance variables -----------------------------
    protected EToken token = null;
    protected Node left;
    protected Node right;
    protected String print = "";
     
    /** 
     * Node.
     *
     * @param t EToken
     */
    public Node( EToken t )
    {
        token = t;
    }
    
    /**
     * evaluate.
     * 
     * @param node Node
     * @param sym SymbolTable
     * @return String
     */
    public static String evaluate( Node node , SymbolTable sym )
    {
        test2( );
        if( node.token instanceof Variable ) 
        {
            test2( );
            float f1 = sym.getValue( node.token.getop() );
            String s22 = String.valueOf( f1 );
            return s22;
        } 
        else if( node.token instanceof  Number )
        {
            return node.token.op();
        } 
        else 
        {
            test2( );
            if( node.token.op().equals( "=" ) )
            {
                if( node.right != null && sym != null && node.left != null )
                {
                    String s1 = evaluate( node.right, sym );
                    Float f1 = Float.valueOf( s1 );
                    sym.setValue( node.left.token.op() , f1 );
                    return s1;
                }
                return ""; 
            } 
            else 
            {
                test2( );
                Float v1 = 0f;
                Float v2 = 0f;
                if( node.left != null ) 
                {
                    String s1 = evaluate( node.left, sym );
                    v1 = Float.valueOf( s1 ); 
                }
                if( node.right != null ) 
                {
                    String s1 = evaluate( node.right, sym );
                    v2 = Float.valueOf( s1 );
                }
                String op = node.token.op();
                Float finalvalue = 0f;
                if( op.equals( "+" ) ) 
                {
                    finalvalue = v1 + v2;
                } 
                else if( op.equals( "*" ) ) 
                {
                    finalvalue = v1 * v2;
                } 
                else if( op.equals( "-" ) )
                {
                    finalvalue = v1 - v2;
                }
                else if( op.equals( "/" ) ) 
                {
                    finalvalue = v1 / v2;
                } 
                else if( op.equals( "%" ) )
                {
                    finalvalue = v1 % v2;
                }
                else
                {
                    System.out.println( "Operator not found" );
                }
                test2( );
                String returnme = String.valueOf( finalvalue );
                test2( );
                return returnme;
            }
        }
    }
    
    /**
     * printTree.
     * 
     * @param node Node
     * @param depth int
     */
    public void printTree( Node node, int depth ) 
    {
        String space = "       ";
        String message = "";
        for( int i = 0; i < depth; i++ )
        {
            message = message + space;
        }
        
        if( node.left != null )
        {
            printTree( node.left , depth + 1 );
        }
        print = print + "\n" + message + node.token.op();
        if( node.right != null )
        {
            printTree( node.right , depth + 1 );
        }
    }
    /**
     * test2.
     */
    public static void test2( )
    {
        int i = 0;
        int a = 2;
        while( i < 20 )
        {
            a = 3;
            i++;
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
        c = a * b;
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
    }
}
