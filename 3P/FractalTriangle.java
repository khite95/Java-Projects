/** 
 * FractalTriangle.java:
 *       A pseudo-fractal triangle. Starting with an initial triangle, 
 *       each recursion draws a new triangle along each edge of its parent
 *       triangle. Class variables act as parameters to control:
 *         -whether child triangles are on the outside or inside of parent
 *         -whether the triangles should be hollow or filled
 *         -the ratio of the child size to the parent size
 *         -the offset of each child along the edge of the parent
 *         -the max depth of recursion allowed
 *         -projection point of the 3rd point of the triangle onto the
 *          line formed by the first 2 points. This is a fractional version
 *          of the percentage of the way from point 0 to point 1. This %
 *          can range from -20 to 120 
 *         -the min height of a triangle that allows recursion
 *             (this parameter can only be set at compile time)
 * 
 *       The parameters for size ratio, offset, and projection point
 *       are given as integers representing percentages; they MUST be 
 *       converted to floating point fractions in order to be used.
 * 
 *       Other (floating point) parameters used in the recursive triangle
 *       generation include 
 *          -the height of this FractalTriangle (it is needed by its children
 *           in order to compute their heights); by keeping it in floating
 *           point, we reduce accumulating roundoff error as we recurse. 
 *           Each child must access its parent's value in order to compute
 *           its height.
 *        
 *
 * @author rdb
 * CS416 Spring 2010
 * Derived from FractalShape code written by Matt Plumlee and modified
 *    previously by rdb
 */

import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.Color;
import java.util.*;

public class FractalTriangle 
{
   //---------------- class variables ------------------------------
   //---- recursive generation parameters
   public static  double   sizeRatio = 0.5;   // integer represent %
   public static  double   offset = 0.5;    // offset/100 = parametric value
                                           // child positioning offset
   public static  double   p2projection = 0.5;
                                    // parametric value of projection of
                                    // vertex p2 onto the base; can be < 0
   public static  boolean  outside = true;
   public static  boolean  fillTriangles = true;
   public static  int      maxDepth    = 6;
   public static  int      minHeight   = 7;  // min height of p2 for further
                                             //   subdivision.
   private static Color[]  colors = { Color.RED, Color.BLUE, Color.CYAN, 
      Color.GREEN, Color.MAGENTA, Color.YELLOW, Color.BLACK };
   
   //---------------- instance variables ------------------------------
   private ATriangle  _tri;
   private int        _depth;
   private FractalTriangle _parent = null;

   //---- triangle shape parameters  
   private double     _p2height;
   
   //------------------------ constructors ----------------------------
   /**
    * initial constructor. Needs an ATriangle object. It also gets 
    * passed the height of this triangle and the projection position of the
    * 3rd point onto the opposite line. These could be computed from the
    * ATriangle object, but it's pretty complicated to do and in the context
    * of this assignment, the caller got those parameters from the user,
    * so it's easiest to just pass them along.
    */
   public FractalTriangle( int depth, ATriangle tri, int height )
   {
      // set up values of instance variables
      _depth    = 1;
      _p2height = height;
      _parent = null;
      _tri = tri;
      
      ////////////////////////////////////////////////
      // Set the triangle's color to a value that depends
      // on its depth.
      //
      // Generate the children triangles for this root.
      // Make sure your code for this is as clean and
      // modular as possible.
      /////////////////////////////////////////////////////
      
      
   }
   /**
    * recursive constructor; it's private, so can only be called from methods
    * of this class. It will get called by the public FractalTriangle.
    */
   private FractalTriangle ( ATriangle tri, FractalTriangle parent, int depth )
   {
      ///////////////////////////////////////////////////////////
      // get parent's height from parent parameter to compute this height
      //
      // save other parameters in instance variables
      //
      // recursively create children -- only if recursion depth limit hasn't
      // been reached and the minimum height test passes
      ///////////////////////////////////////////////////////////
      
   
   }  
   //--------------------- display( Graphics2D ) -----------------------
   /**
    * method called by Java's repaint mechanism.
    */   
   public void display ( Graphics2D context ) 
   {
      //////////////////////////////////////////////////////
      // 1. Need to determine whether to draw the ATriangle's
      //    display method (which fills it) or its "draw" method
      //    which only does the outline.
      ///////////////////////////////////////////////////////
      _tri.display( context );
      //////////////////////////////////////////////////////
      // 2. Need to recursively invoke the display methods of
      //    all children (if they exist)
      //
      //////////////////////////////////////////////////////
   }
   ////////////////////////////////////////////////////////////////////////
   ////////////////////////////////////////////////////////////////////////
   //
   // DO NOT CHANGE ANY CODE BELOW THIS POINT!!!!
   //
   ////////////////////////////////////////////////////////////////////////
   ////////////////////////////////////////////////////////////////////////
   //----------------- makeTriangle( Point, Point ) ----------------------
   /** 
    * make the child triangle. The 2 parameters represent the parent's edge
    * that is the line along which the base of the child should be placed.
    * 
    * The base of the child should be the fraction of the base of the parent 
    *   as defined by the "sizeRatio" class variable. The base of the parent
    *   is defined by the parent's P0-P1 edge.
    * P0 for the child should be located at parametric position "offset" along
    *   the parent's edge.
    * P1 for the child is determined by the sizeRatio and it lies along the
    *   parent's edge
    * P2 for the child is determined by finding the parametric value of the
    *   perpendicular projection of the parent's P2 onto its base, finding
    *   that point along the child's base, finding the normal to the
    *   child's base at that point and locating P2 at a distance along the
    *   normal determined by the "sizeRatio" value
    */
   private ATriangle makeTriangle( Point p0, Point p1 )
   {
      Point b0, b1;   // base points
      //Point ht;       // 3rd point
      
      Line base;
      if ( outside )
         base = new Line( p1, p0 );
      else
         base = new Line( p0, p1 );
      b0 = base.getPointAt( offset );
      // the length of the new base must be sizeRatio of the length of
      //   the base, so the end point parametric value is offset+sizeRatio
      b1 = base.getPointAt( offset + sizeRatio );
      
      // the p2 projection point is relative to the b0 position, so it is
      // in the parametric space of the line from b0 to b1
      Line newBase;
      newBase = new Line( b0, b1 );

      Point p2 = newBase.getPointOnNormalAt( p2projection, 
                                            _p2height * sizeRatio );
      
      ATriangle newTri;
      newTri = new ATriangle( b0, b1, p2 );
      
      double area = _p2height * sizeRatio * newBase.length() / 2;
      newTri.setColor( colors[ ( _depth + 1 ) % colors.length ] );
      return newTri;
   }

   //------------------------------- main -------------------------------
   public static void main( String [] args ) 
   {
      FractalApp app = new FractalApp( "FractalApp demo", args );
   }
}
