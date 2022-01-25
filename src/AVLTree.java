// AvlTree class
//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x (unimplemented)
// boolean contains( x )  --> Return true if x is present
// boolean remove( x )    --> Return true if x was present
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void printTree( )      --> Print tree in sorted order
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

/**
 * Implements an AVL tree.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
public class AVLTree<AnyType extends Comparable<? super AnyType>>
{
    /**
     * Construct the tree.
     */
    public AVLTree( )
    {
        root = null;

    }

    /**
     * Insert into the tree; duplicates are ignored.
     * @param x the item to insert.
     */
    public void insert( AnyType x )
    {
        root = insert( x, root );
        this.totalAdded++;
        this.currentSize++;
    }

    /**
     * Remove from the tree. Nothing is done if x is not found.
     * @param x the item to remove.
     */
    public void remove( AnyType x )
    {
        root = remove( x, root );
    }


    /**
     * Internal method to remove from a subtree.
     * @param x the item to remove.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private AvlNode<AnyType> remove( AnyType x, AvlNode<AnyType> t )
    {
        if( t == null )
            return t;   // Item not found; do nothing

        int compareResult = x.compareTo( t.element );

        if( compareResult < 0 )
            t.left = remove( x, t.left );
        else if( compareResult > 0 )
            t.right = remove( x, t.right );
        else if( t.left != null && t.right != null ) // Two children
        {
            t.element = findMin( t.right ).element;
            t.right = remove( t.element, t.right );
        }
        else
            t = ( t.left != null ) ? t.left : t.right;
        return balance( t );
    }

    /**
     * Find the smallest item in the tree.
     * @return smallest item or null if empty.
     */
    public AnyType findMin( )
    {
        if( isEmpty( ) )
            throw new RuntimeException( );
        return findMin( root ).element;
    }

    public  void  deleteMin( ){
        root =  deleteMin(root);
        this.totalRemoved++;
        this.currentSize--;
    }

    /**
     * Find the largest item in the tree.
     * @return the largest item of null if empty.
     */
    public AnyType findMax( )
    {
        if( isEmpty( ) )
            throw new RuntimeException( );
        return findMax( root ).element;
    }

    /**
     * Find an item in the tree.
     * @param x the item to search for.
     * @return true if x is found.
     */
    public boolean contains( AnyType x )
    {
        return contains( x, root );
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty( )
    {
        root = null;
    }

    /**
     * Test if the tree is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( )
    {
        return root == null;
    }

    /**
     * Print the tree contents in sorted order.
     */
    public void printTree( String label)
    {
        System.out.println(label);
        if( isEmpty( ) )
            System.out.println( "Empty tree" );
        else
            printTree( root,"" );
    }

    /**
     * prints the info of the AVL Tree
     */
    public void printQueueInfo() {
        System.out.printf("AVLTree Info: \nTotal Added = %d \nTotal Removed = %d \nCurrent Size = %d \n",
                this.totalAdded, this.totalRemoved, this.currentSize);
    }

    private static final int ALLOWED_IMBALANCE = 1;

    // Assume t is either balanced or within one of being balanced
    private AvlNode<AnyType> balance( AvlNode<AnyType> t )
    {
        if( t == null )
            return t;

        if( height( t.left ) - height( t.right ) > ALLOWED_IMBALANCE )
            if( height( t.left.left ) >= height( t.left.right ) )
                t = rightRotation( t );
            else
                t = doubleRightRotation( t );
        else
        if( height( t.right ) - height( t.left ) > ALLOWED_IMBALANCE )
            if( height( t.right.right ) >= height( t.right.left ) )
                t = leftRotation( t );
            else
                t = doubleLeftRotation( t );

        t.height = Math.max( height( t.left ), height( t.right ) ) + 1;
        return t;
    }

    public void checkBalance( )
    {
        checkBalance( root );
    }

    /**
     * Checks if a tree is balanced, probably not useful bc tree should be balanced after inserts
     * @param t tree to check balance of
     * @return height of the tree
     */
    private int checkBalance( AvlNode<AnyType> t )
    {
        if( t == null )
            return -1;

        if( t != null )
        {
            int leftHeight = checkBalance( t.left );
            int rightHeight = checkBalance( t.right );
            if( Math.abs( height( t.left ) - height( t.right ) ) > 1 ||
                    height( t.left ) != leftHeight || height( t.right ) != rightHeight )
                System.out.println( "\n\n***********************OOPS!!" );
        }

        return height( t );
    }


    /**
     * Internal method to insert into a subtree.  Duplicates are allowed
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private AvlNode<AnyType> insert( AnyType x, AvlNode<AnyType> t )
    {
        if( t == null )
            return new AvlNode<>( x, null, null );

        int compareResult = x.compareTo( t.element );

        if( compareResult < 0 )
            t.left = insert( x, t.left );
        else
            t.right = insert( x, t.right );

        return balance( t );
    }

    /**
     * Internal method to find the smallest item in a subtree.
     * @param t the node that roots the tree.
     * @return node containing the smallest item.
     */
    private AvlNode<AnyType> findMin( AvlNode<AnyType> t )
    {
        if( t == null )
            return t;

        while( t.left != null )
            t = t.left;
        return t;
    }

    /**
     * deletes the minimum node in the tree and returns the new root of tree
     * @param t the root of the subtree
     * @return root of new tree
     */
    private AvlNode<AnyType> deleteMin( AvlNode<AnyType> t ) {
        if (t == null) return t; //if null node

        else if (t.left != null)
            if (t.left.left != null) t.left = deleteMin(t.left);

            else //t.left.left is null (left child is min to be deleted)
                if (t.left.right != null) t.left = t.left.right; //min has right child, delete min by replacing w/child
                else t.left = null; //min has no child, can simply be changed to null

        else if (t.right != null) t = t.right;

        else t = null;

        return balance(t);
    }

    /**
     * Internal method to find the largest item in a subtree.
     * @param t the node that roots the tree.
     * @return node containing the largest item.
     */
    private AvlNode<AnyType> findMax( AvlNode<AnyType> t )
    {
        if( t == null )
            return t;

        while( t.right != null )
            t = t.right;
        return t;
    }

    /**
     * Internal method to find an item in a subtree.
     * @param x is item to search for.
     * @param t the node that roots the tree.
     * @return true if x is found in subtree.
     */
    private boolean contains( AnyType x, AvlNode<AnyType> t )
    {
        while( t != null )
        {
            int compareResult = x.compareTo( t.element );

            if( compareResult < 0 )
                t = t.left;
            else if( compareResult > 0 )
                t = t.right;
            else
                return true;    // Match
        }

        return false;   // No match
    }

    /**
     * Internal method to print a subtree in sorted order.
     * @param t the node that roots the tree.
     */
    private void printTree( AvlNode<AnyType> t, String indent )
    {
        if( t != null )
        {
            printTree( t.right, indent+"   " );
            System.out.println( indent+ t.element + "("+ t.height  +")" );
            printTree( t.left, indent+"   " );
        }
    }

    /**
     * Return the height of node t, or -1, if null.
     */
    private int height( AvlNode<AnyType> t )
    {   if (t==null) return -1;
        return t.height;
    }

    /**
     * Rotate binary tree node with left child.
     * For AVL trees, this is a single rotation for case 1.
     * Update heights, then return new root.
     */
    private AvlNode<AnyType> rightRotation(AvlNode<AnyType> t )
    {
        AvlNode<AnyType> theLeft = t.left;
        t.left = theLeft.right;
        theLeft.right = t;
        t.height = Math.max( height( t.left ), height( t.right ) ) + 1;
        theLeft.height = Math.max( height( theLeft.left ), t.height ) + 1;
        return theLeft;
    }

    /**
     * Rotate binary tree node with right child.
     * For AVL trees, this is a single rotation for case 4.
     * Update heights, then return new root.
     */
    private AvlNode<AnyType> leftRotation(AvlNode<AnyType> t )
    {
        AvlNode<AnyType> theRight = t.right;
        t.right = theRight.left;
        theRight.left = t;
        t.height = Math.max( height( t.left ), height( t.right ) ) + 1;
        theRight.height = Math.max( height( theRight.right ), t.height ) + 1;
        return theRight;
    }

    /**
     * Double rotate binary tree node: first left child
     * with its right child; then node k3 with new left child.
     * For AVL trees, this is a double rotation for case 2.
     * Update heights, then return new root.
     */
    private AvlNode<AnyType> doubleRightRotation( AvlNode<AnyType> t )
    {
        t.left = leftRotation( t.left );
        return rightRotation( t );

    }

    /**
     * Double rotate binary tree node: first right child
     * with its left child; then node k1 with new right child.
     * For AVL trees, this is a double rotation for case 3.
     * Update heights, then return new root.
     */
    private AvlNode<AnyType> doubleLeftRotation(AvlNode<AnyType> t )
    {
        t.right = rightRotation( t.right );
        return leftRotation( t );
    }

    private static class AvlNode<AnyType>
    {
        // Constructors
        AvlNode( AnyType theElement )
        {
            this( theElement, null, null );
        }

        AvlNode( AnyType theElement, AvlNode<AnyType> lt, AvlNode<AnyType> rt )
        {
            element  = theElement;
            left     = lt;
            right    = rt;
            height   = 0;
        }

        AnyType           element;      // The data in the node
        AvlNode<AnyType>  left;         // Left child
        AvlNode<AnyType>  right;        // Right child
        int               height;       // Height
    }

    //data fields
    private AvlNode<AnyType> root;
    public int currentSize = 0;
    public int totalAdded = 0;
    public int totalRemoved = 0;


    // Test program
    public static void main( String [ ] args ) {
        AVLTree<Integer> t = new AVLTree<>();
//        AVLTree<Dwarf> t2 = new AVLTree<>();
//
//        String[] nameList = {"Snowflake", "Sneezy", "Doc", "Grumpy", "Bashful", "Dopey", "Happy", "Doc", "Grumpy", "Bashful", "Doc", "Grumpy", "Bashful"};
//        for (int i=0; i < nameList.length; i++)
//            t2.insert(new Dwarf(nameList[i]));
//
//        t2.printTree( "The Tree" );
//
//        t2.remove(new Dwarf("Bashful"));
////        System.out.println("min is" + t2.findMin());
//
//
//        t2.printTree( "The Tree after delete Bashful" );
////        System.out.println("min is" + t2.findMin());
//
//        for (int i=0; i < 8; i++) {
//            t2.deleteMin();
//            t2.printTree( "\n\n The Tree after deleteMin" );
////            System.out.println("min is" + t2.findMin());

        for (int i=0; i < 10; i++) { //check that tree is doing inserts correctly (it is)
            t.insert(i + 1);
//            t.printTree("Tree" + i);
//            t.printQueueInfo();
        }

        for (int i=0; i < 10; i++) {
            t.printTree("Tree" + i);
            System.out.println(t.findMin());
            t.deleteMin();
            t.printQueueInfo();
            System.out.println();
        }

    }
}
