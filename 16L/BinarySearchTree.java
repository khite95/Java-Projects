//++++++++++++++++++++++++++ BinarySearchTree ++++++++++++++++++++++
import java.util.*;
import java.io.*;
/**
 * BinarySearchTree -- this class uses a private inner class for
 *      tree nodes. (Although in this version it is public so I can 
 *      do a prefix walk in DrawPanel.)
 *
 * @author rdb
 * April 2, 2008
 * 
 * Modified: April 6
 *      added iterator and node deletion code.
 * 
 * modified mlb Nov 2008 for BSTDeleteApp
 * Modified rdb Mar 30, 2009 
 *   -reorganized delete code to better match notes; 
 *   -corrected errors in parent setting -- there needed to be some parent
 *      changes in addToFarRight and addToFarLeft.
 *   -added some extraneous code to check that all code gets executed.
 *   -figured out 1 data generation and order of deletion that 
 *    executes all paths in the remove node code (if the code is correct)
 *       Generate 14 data elements using seed 2 (these are now the
 *          defaults in TreeApp)
 *       Delete nodes in this order:
 *            no, gd, un, wb, hm, aw, re, pv, [sg, fp, gk, kh, ka, ke]
 *       When you hit the bracket, all the rest are root nodes.
 * 05/04/09 rdb: Added delete( String ) method;
 *               revised delete( Data ) method to call new one.
 * 03/31/10 rdb: Made removeNode( Node ) package access so can be called
 *               from TreeApp for deleting by mouse pick
 * 03/30/11 rdb: Added inOrderString() and inOrderString( Node )
 * 03/23/14 rdb: Added unit testing in main for addToFarRight and 
 *               addToFarLeft, along with checkParentLinks and some size
 *               tests.
 */

public class BinarySearchTree 
{
    //-------------------- instance variables ---------------------
    private Node   _root;
    private int    _size;
    private char[] coverage;
    
    //-------------------- constructors --------------------------
    /**
     * Construct an empty tree with no nodes.
     */
    public BinarySearchTree()
    {
        _root = null;
        coverage = new char[ 13 ];
        for ( int i = 0; i < coverage.length; i++ )
            coverage[ i ] = ' ';
    }
    /**
     * Construct a tree with a root .
     * @param rootData Data
     */
    public BinarySearchTree( Data rootData )
    {
        this();
        _root = new Node( rootData );
    }
    //+++++++++++++++++++++++ inner class Node ++++++++++++++++++++++
    /**
     * The Node class does not have to be seen outside this class, so
     * it is private.
     */
    static public class Node
    {
        //-------------- instance variables ---------------------------
        Data data;
        Node left;
        Node right;
        Node parent;
        
        //--------------- constructor --------------------------------
        /**
         * Constructor.
         * @param d Data
         */
        public Node( Data d )
        {
            data = d;
            left = null;
            right = null;
            parent = null;
        }
        //--------------- size() -------------------------------------
        /**
         * Return tree size.
         * @return int
         */
        public int size()
        {
            return size( this );
        }
        //--------------- size( Node ) -------------------------------
        /**
         * Return size of subtree rooted at n.
         * @param n Node
         * @return int
         */
        public int size( Node n )
        {
            if ( n == null )
                return 0;
            else 
                return 1 + size( n.left ) + size( n.right );
        }
    }
    //-------------------- removeNode( Node ) ------------------------
    /**
     * Remove a specific node from the tree.
     * @param n Node        node to be deleted
     */
    void removeNode( Node n )
    {
        if ( _root == n ) // n is the root
            removeRoot();
        else if ( n.parent.left == n )
            removeLeft( n.parent, n );
        else
            removeRight( n.parent, n );
        _size--;
    }

    /////////////////////////////////////////////////////////////////
    // DO NOT CHANGE ANY CODE PRIOR TO THIS POINT
    /////////////////////////////////////////////////////////////////
    //---------------------- addToFarRight( node, node ) ----------------
    /**
     * Add subtree Node as the right most descendant of the 1st argument.
     * @param n Node        root of tree to which subtree must be added
     * @param subtree Node  subtree to be added to far right of subtree
     */
    private void addToFarRight( Node n, Node subtree )
    {
        ///////////////////////////////////////////////////////////////
        // 1a .implement code from the notes
        //    But, you need a little extra code to update the parent 
        //    references in the children of n:  n.left and n.right 
        //     -- of course, only if n.left and/or n.right are not null
        ///////////////////////////////////////////////////////////////
        while( n.right != null )
        {
            n = n.right;  
        }
        n.right = subtree;
        
        subtree.parent = n;
        subtree.parent.left = n.left;
        subtree.parent.right = n.right;
    
    
    
    
    }
    //----------------------- addToFarLeft( Node, Node ) --------------
    /**
     * Add subtree Node as the left most descendant of the 1st argument.
     * @param n Node        root of tree to which subtree must be added
     * @param subtree Node  subtree to be added to far left of subtree
     */
    private void addToFarLeft( Node n, Node subtree )
    {
        ////////////////////////////////////////////////////////////////
        // 1b. implement code from the notes
        //    But, you need a little extra code to update the parent 
        //    references in the children of n:  n.left and n.right 
        //     -- of course, only if n.left and/or n.right are not null
        ///////////////////////////////////////////////////////////////
        while( n.left != null )
        {
            n = n.left;
        }
        n.left = subtree;
                
        subtree.parent = n;
        subtree.parent.left = n.left;
        subtree.parent.right = n.right;
        
        
        
        
    }

    //-------------------- removeRight( Node, Node ) -------------------
    /**
     * Remove a node that is the right child of its parent.
     * @param parent Node
     * @param n      Node
     */
    private void removeRight( Node parent, Node n )
    {
        ///////////////////////////////////////////////////////////////
        // 2. implement code from the notes
        //    But, you need a little extra code to update the parent 
        //    references in the children of n:  n.left and n.right 
        //     -- of course, only if n.left and/or n.right are not null
        ///////////////////////////////////////////////////////////////
        if ( n.right == null )
        {
            parent.right = n.left;
            if ( n.left != null )
            {
                n.left.parent = parent;
            }
        }
        else 
        { // make right new subtree root
            parent.right = n.right;
            
            n.right.parent = parent;
            if ( n.left != null )
            {
                addToFarLeft( parent.right, n.left );
            }
        }
        
        //n.parent = parent;
        //n.parent.left = parent.left;
        //n.parent.right = parent.right;
        
        
        
        
    }
    //-------------------- removeLeft( Node, Node ) --------------------
    /**
     * Remove a node that is the left child of its parent.
     * @param parent Node
     * @param n      Node
     */
    private void removeLeft( Node parent, Node n )
    {
        ////////////////////////////////////////////////////////////////
        // 3. implement code from the notes
        //    But, you need a little extra code to update the parent 
        //    references in the children of n:  n.left and n.right 
        //     -- of course, only if n.left and/or n.right are not null
        ////////////////////////////////////////////////////////////////
        if ( n.left == null )
        {
            parent.left = n.right;
            if ( n.right != null )
            {
                n.right.parent = parent;
            }
        }
        else 
        { // make right new subtree root
            parent.left = n.left;
            n.left.parent = parent;
            if ( n.right != null )
            {
                addToFarRight( parent.left, n.right );
            }
        }
        
        //n.parent = parent;
        //n.parent.left = parent.left;
        //n.parent.right = parent.right;
    
    
    
    
    
    }
    //-------------------- removeRoot( ) ------------------------------
    /**
     * Remove the root node.
     */
    private void removeRoot()
    {
        ////////////////////////////////////////////////////////////////
        // 4. implement code from the notes
        //    But, you need a little extra code to update the parent 
        //    references in the children of n:  n.left and n.right 
        //     -- of course, only if n.left and/or n.right are not null
        ////////////////////////////////////////////////////////////////
        if( _root.left == null )
        {
            _root = _root.right;
        }
        else if( _root.right == null )
        {
            _root = _root.left;
        }
        else
        {
            // make left the new root
            addToFarRight( _root.left, _root.right );
            _root = _root.left;
        }
    
    
    
    
    
    
    
    
    
    }    
    
    /////////////////////////////////////////////////////////////////
    // DO NOT CHANGE ANY CODE AFTER TO THIS POINT
    /////////////////////////////////////////////////////////////////

    //-------------------- delete( Data ) ------------------------------
    /**
     * Find the node in the tree whose data field contains a key that 
     *  matches the key contained in the Data parameter.
     * @param d Data
     * @return Data
     */
    public Data delete( Data d )
    {
        return delete( d.key );
    }
    
    //-------------------- delete( String ) ----------------------------
    /**
     * Find the node in the tree whose data field contains a key that 
     *  matches the string passed as an argument.
     * @param k String
     * @return Data
     */
    public Data delete( String k )
    {
        Data foundData = null;
        Node found = findNode( k );
        if ( found != null )
        {
            foundData = found.data;
            removeNode( found );
        }
        return foundData;
    }
    //--------------------- root() ----------------------------------
    /**
     * Return root of the tree; this is package access so that DrawPanel
     * can do a prefix walk of the tree. Would be better to have multiple
     * iterators.
     * @return BinarySearchTree.Node
     */
    BinarySearchTree.Node root()
    {
        return _root;
    }
    //------------------ iterator() ---------------------------------
    /**
     * Create an iterator for the tree.
     * @return Iterator<Data>
     */
    public Iterator<Data> iterator()
    {
        return new InfixIterator();
    }
    
    //------------------ inOrderPrint() ---------------
    /**
     * Traverse tree in in-order fashion to print the nodes of the tree
     * to the PrintStream parameter.
     * Method uses a private utility method to print a subtree rooted
     * at a particular node.
     */
    public void inOrderPrint( )
    {
        System.out.println( inOrderString() );
    }
    //------------------ inOrderString() ---------------
    /**
     * Traverse tree in in-order fashion to print the nodes of the tree
     * to the PrintStream parameter.
     * This method uses a private utility method to print a subtree rooted
     * at a particular node.
     * @return String
     */
    public String inOrderString( )
    {
        return inOrderString( _root );
    }
    //------------------ inOrderString( Node ) ---------------
    /**
     * Print the subtree rooted at "node" in in-order fashion.
     * @param n Node   subtree to be printed
     * @return String
     */
    private String inOrderString(  Node n )
    {
        String s = "";
        if ( n != null )
        {
            s = inOrderString(  n.left )
                + " " + n.data + " "
                + inOrderString( n.right );
        }
        return s;
    }
    
    //-------------------- height() ------------------------------------
    /**
     * Return the depth of the tree.
     * @return int
     */
    public int height()
    {
        return height( _root );
    }
    
    //-------------------- height(Node) --------------------------------
    /**
     * Return the depth of the subtree rooted at node.
     * @param node Node
     * @return int
     */
    private int height( Node node )
    {
        if ( node == null ) 
            return 0;
        else 
        {
            int leftHeight = height( node.left );
            int rightHeight = height( node.right );
            return Math.max( leftHeight, rightHeight ) + 1;
        }         
    }
    
    //-------------------- find( String ) -------------------------
    /**
     * Given a key value, search the tree to find the node that has
     * that key value, if it exists.
     * 
     * Return the Data object from the node or null
     * @param key String
     * @return Data
     */
    public Data find( String key )
    {
        Data found = null;
        Node cur = _root;
        while ( cur != null && found == null )
        {
            int cmp = key.compareTo( cur.data.key );
            if ( cmp == 0 )
                found = cur.data;
            else if ( cmp < 0 )
                cur = cur.left;
            else 
                cur = cur.right;
        }
        return found;
    }
    //-------------------- findNode( String ) -------------------------
    /**
     * Given a key value, search the tree to find the node that has
     * that key value, if it exists.
     * 
     * Return the Data object from the node or null.
     * @param key String
     * @return Node
     */
    public Node findNode( String key )
    {
        Data found = null;
        Node cur = _root;
        while ( cur != null && found == null )
        {
            int cmp = key.compareTo( cur.data.key );
            if ( cmp == 0 )
                found = cur.data;
            else if ( cmp < 0 )
                cur = cur.left;
            else 
                cur = cur.right;
        }
        return cur;
    }
    
    //--------------------- add -----------------------------------
    /**
     * Add a node to the tree in its proper position determined by the
     * "key" field of the Data object. This method uses the addNode 
     * recursive utility method.
     * @param data Data
     */
    public void add( Data data )
    {
        Node newNode = new Node( data );
        if ( _root == null )
            _root = newNode;
        else
            addNode( _root, newNode );
        _size++;
    }
    
    //------------------ addNode( Node, Node ) -----------------------
    /**
     * A recursive method to add a new Node (2nd argument) to the subtree
     * rooted at the first argument.
     * @param parent Node   root of tree into which the new Node must go.
     * @param newOne Node   Node to be added
     */
    private void addNode( Node parent, Node newOne )
    {
        if ( newOne.data.compareTo( parent.data ) < 0 )
        {
            if ( parent.left != null )
                addNode( parent.left, newOne );
            else 
            {
                parent.left = newOne;
                newOne.parent = parent;
            }
        }
        else
        {
            if ( parent.right != null )
                addNode( parent.right, newOne );
            else 
            {
                parent.right = newOne;
                newOne.parent = parent;
            }
        }
    }
    
    //-------------------------- size() -------------------------
    /**
     * Return tree size.
     * @return int
     */
    public int size()
    {
        return _size;
    }
    //-------------------------- toString() -------------------------
    /**
     * Generate a string representation of the tree.
     * @return String
     */
    public String toString()
    {
        return toString( _root, "  ", "  " ) ;        
    }
    
    /**
     * Recursively generate a string representation for a Node of a tree.
     * indent is increased for increasing depth.
     * branch is a short character string that prefixes each node indicating
     *     whether this node is a left (L) or right (R) child of its parent.
     * @param n Node  subtree root
     * @param indent String
     * @param branch String
     * @return String
     */
    private String toString( Node n, String indent, String branch )
    {
        String s = "";
        if ( n != null )
        {
            String prefix = indent.substring( 0, indent.length() - 2 ) + branch;
            s += prefix + n.data.toString() + "\n";
            if ( n.left != null )
                s += toString( n.left, indent + "  ", "L " );
            if ( n.right != null )
                s += toString( n.right, indent + "  ", "R " );
        }
        return s;
    }
    //----------------- reportParentError -----------------------------
    /**
     * ReportError first flushes System.out and then sends error to
     * System.err.
     * @param prefix String
     * @param parent Node
     * @param badParent Node
     */
    static void reportParentError( String prefix, Node parent, Node badParent )
    {
        System.out.println();
        System.out.flush();
        System.err.print( "****ERROR****: " + prefix + "( " );
        if ( parent.data != null )
            System.err.print( parent.data + " ) != " );
        else
            System.err.print( "nullData ) != " );
        if ( badParent == null )
            System.err.print( "null" );
        else if ( badParent.data == null )
            System.err.print( "nullData" );
        else 
            System.err.print( badParent.data );
        System.err.println();
    }
    //------------------ checkParentLinks( BST ) ----------------------
    /**
     * For a complete check of parent links, the root parent should be
     *  checked to make sure it is null.
     * @param bst BinarySearchTree
     */
    static void checkParentLinks( BinarySearchTree bst )
    {
        Node root = bst.root();
        if ( root.parent != null )
        {
            System.err.print( "parent( root ) != null. -> " 
                            + root.parent.data );
        }
        checkParentLinks( root );
    }
                          
    //--------------------- checkParentLinks -------------------------
    /**
     * Recursively check that the parent reference of both node's children
     *    reference the node. Print to System.err, if anything is bad.
     * @param n Node
     */
    static void checkParentLinks( Node n )
    {
        if ( n == null )
            return;
        if ( n.left != null )
        {
            if ( n.left.parent != n )
                reportParentError( "Left.parent ", n, n.left.parent ); 
            checkParentLinks( n.left );
        }
        if ( n.right != null )
        {
            if ( n.right.parent != n )
                reportParentError( "Right.parent( ", n, n.right.parent );
            checkParentLinks( n.right );
        }        
    }
    //------------------ printCoverage() ---------------------------------
    /**
     * a debugging tool to print the test coverage of delete code.
     */
    public void printCoverage()
    {
        System.out.print( "DeleteTest coverage: |" );
        for ( int i = 0; i < coverage.length; i++ )
            System.out.print( coverage[ i ] );
        System.out.println( "|" );
    }
    //++++++++++++++++++++++++ inner class InfixIterator ++++++++++++++++++
    /**
     * An iterator that walks the tree in prefix order.
     */
    public class InfixIterator implements Iterator<Data>
    {
        //--------------------- instance variables ---------------------------
        private Stack<NodeVisit> stack;  // top of stack defines the next node
        private Node             nextNode;
        private Node             lastNode;
        
        //+++++++++++ private inner class NodeVisit ++++++++++++++++++++++++
        /**
         * Private inner class that controls tree traversal.
         */
        private class NodeVisit
        {
            Node node;
            int  visit;  // 0, 1, 2, or 3; but only 0, 1 and 2 in stack
            
            /** Constructor. 
              * @param n node. 
              * @param v int 
              */
            public NodeVisit( Node n, int v )
            {
                node = n; visit = v;
            }
        }
        
        //----------------------- constructor ------------------------------
        /** 
         * Constructor for an infix iterator.
         */
        public InfixIterator()
        {
            nextNode = null;
            if ( _root == null )
                stack = null;
            else
            {
                stack = new Stack<NodeVisit>();
                stack.push( new NodeVisit( _root, 0 ) );
                nextNode = getNext();
            }
            lastNode = null;
        }
        //---------------------- hasNext() -----------------------------
        /**
         * Is there another node to process? 
         * @return boolean
         */
        public boolean hasNext()
        {
            return  nextNode != null;
        }
        //----------------------- Data next() --------------------------
        /**
         * Public method for getting the next Data object to process.
         * @return Data
         */
        public Data next()
        {
            lastNode = nextNode;
            Data ret;
            if ( nextNode == null )
                throw new NoSuchElementException( "BinarySearchTree.iterator" );
            ret = nextNode.data;
            nextNode = getNext();
            return ret;
        }
        
        //----------------------- Node getNext() -----------------------
        /**
         * Get the Next node for processing private version.
         * @return Node
         */
        private Node getNext() 
        {
            Node ret = null;
            if ( stack.isEmpty() )
                return null;
            NodeVisit top = stack.peek();
            switch ( top.visit++ )  
            {
                case 0: // about to make first visit; push left onto stack
                    if ( top.node.left != null )
                    {
                        stack.push( new NodeVisit( top.node.left, 0 ) ); 
                        ret = getNext();    // invoke next recursively
                    }
                    else
                    {
                        ret = top.node;
                        top.visit++;
                    }
                    break;
                case 1:  // been down the left branch, return the node
                    // and set up to go down the right branch
                    ret = top.node;              
                    break;
                case 2:       // done the node, go down the right branch
                    if ( top.node.right != null )
                    {
                        stack.push( new NodeVisit( top.node.right, 0 ) );
                        ret = getNext();
                    }
                    else  // can pop this one off the stack and continue
                    {
                        stack.pop();
                        ret = getNext();
                    }
                    break;
                case 3: // finished right branch, pop from stack, recurse
                    if ( !stack.isEmpty() )
                        stack.pop();
                    ret = getNext();
                    break;
                default:
                    if ( !stack.isEmpty() )
                        stack.pop();
                    ret = null;
                    break;
            }
            return ret;
        }
        //------------------ remove() ----------------------------------
        /** 
         * Remove the last Node.
         */
        public void remove()
        {
            removeNode( lastNode );
            lastNode = null;
        }
    }
    //--------------------- main ---------------------------------------
    /**
     * This provides a simple "unit test" environment, primarily aimed at
     *    testing the addToFarLeft and addToFarRight methods.
     * Although these methods can be tested with data that will cause
     *    the resulting tree to no longer be valid search trees, it's
     *    fairly easy to create some examples that do not violate this.
     * Both tests
     *    1. create  2 subtrees to be added.
     *       a. a 3-node subtree with a root with left and right children
     *       b. a 1-node subtree with no children.
     *    2. create 3 "base" trees to which the subtree is to be added. 
     *       a. 1 node
     *       b. 4 left (right) series
     * 
     * The only output is printed to System.out.
     * 
     * @param args String[]   Not used.
     */
    public static void main( String[] args )
    {
        farLeftTest();
        farRightTest();
    }
    //----------------- farLeftTest ----------------------------------
    /**
     * A very minimal test for the addToFarLeft method.
     */
    private static void farLeftTest()
    {
        String[] baseTrees = { "m", "mkjhg" };
        String[] subTrees = { "bca", "b" };
        farTest( "farLeft", baseTrees, subTrees );
    }
    //----------------- farRightTest ----------------------------------
    /**
     * A very minimal test for the addToFarLeft method.
     */
    private static void farRightTest()
    {
        String[] baseTrees = { "m", "mpstv" };
        String[] subTrees = { "yxz", "y" };
        farTest( "farRight", baseTrees, subTrees );
    }
    //---------------- farTest( label, base[], subTree[] )------------
    /**
     * Do the "far" test. 
     * @param label String
     * @param baseTrees String[]
     * @param subTrees String[]
     */
    private static void farTest( String label, 
                                String[] baseTrees, String[] subTrees )
    {
        BinarySearchTree base = null;
        BinarySearchTree sub;
        
        for( int b = 0; b < baseTrees.length; b++ )
        {
            base = makeTree( baseTrees[ b ] );
            int baseSize = base.size();
            System.out.println( "-------------Base: \n" + base );
            for( int s = 0; s < subTrees.length; s++ )
            {
                sub = makeTree( subTrees[ s ] );
                int expSize = baseSize + sub.size();
                System.out.println( "+++ " + label + " add: \n" + sub );
                if ( label.equals( "farLeft" ) )
                    base.addToFarLeft( base.root(), sub.root() );
                else
                    base.addToFarRight( base.root(), sub.root() );
                System.out.println( "After add: \n" + base );                
                if ( base.root().size() != expSize )
                {
                    System.out.flush();
                    System.err.println( "***Size error: actual vs. expected "
                                  + base.root().size() + " !=  " + expSize );
                }
                checkParentLinks( base.root() );
                System.out.println( "---------------------------------" );
                base = makeTree( baseTrees[ b ] );
            }
        }
        //base.root().parent = new Node( new Data( "rootParentTest", "", 0 ) );
        //checkParentLinks( base );
    }
    //--------- makeTree ---------------------------------
    /**
     * Make a bst with a nodes from the characters in the argument.
     * 
     * @param ids String     Each character in the string becomes the id 
     *                       for a node in the tree.
     * @return BinarySearchTree
     */
    private static BinarySearchTree makeTree( String ids )
    {
        BinarySearchTree tree = new BinarySearchTree();
        for ( int c = 0; c < ids.length(); c++ )
        {
            tree.add( new Data( ids.substring( c, c + 1 ), "-", 0 ) );
        }
        return tree;
    }
}
