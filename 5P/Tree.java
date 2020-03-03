
/**
 * Created by Skipper on 4/4/2016.
 */
public class Tree {

    private Node root;
    private int size;

    /** Tree.
     * constructor.
     *
     */
    public Tree(){
        root = null;
        size = 0;
    }

    /** Tree.
     * constructor.
     *
     * @param rootnode Node.
     */
    public Tree( Node rootnode ){
        root = rootnode;
        size = 1;
    }


}
