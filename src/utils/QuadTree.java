package utils;

import java.util.ArrayList;

/*************************************************************************
 *  Compilation:  javac QuadTree.java
 *  Execution:    java QuadTree M N
 *
 *  Quad tree.
 * 
 *************************************************************************/

public class QuadTree<Key extends Comparable, Value>  {
    private Node root;

    // helper node data type
    private class Node {
        Key x, y;              // x- and y- coordinates
        Node NW, NE, SE, SW;   // four subtrees
        Value value;           // associated data

        Node(Key x, Key y, Value value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }
    }


  /***********************************************************************
    *  Insert (x, y) into appropriate quadrant
    ***********************************************************************/
    public void insert(Key x, Key y, Value value) {
        root = insert(root, x, y, value);
    }

    private Node insert(Node h, Key x, Key y, Value value) {
        if (h == null) return new Node(x, y, value);
        //// if (eq(x, h.x) && eq(y, h.y)) h.value = value;  // duplicate
        else if ( less(x, h.x) &&  less(y, h.y)) h.SW = insert(h.SW, x, y, value);
        else if ( less(x, h.x) && !less(y, h.y)) h.NW = insert(h.NW, x, y, value);
        else if (!less(x, h.x) &&  less(y, h.y)) h.SE = insert(h.SE, x, y, value);
        else if (!less(x, h.x) && !less(y, h.y)) h.NE = insert(h.NE, x, y, value);
        return h;
    }


  /***********************************************************************
    *  Range search.
    ***********************************************************************/

    public ArrayList<Value> query2D(Interval2D<Key> rect) {
       return query2D(root, rect, new ArrayList<Value>());
		     
		}

    private ArrayList<Value> query2D(Node h, Interval2D<Key> rect, ArrayList<Value> output) {
        if (h == null) return null;

        Key xmin = rect.intervalX.low;
        Key ymin = rect.intervalY.low;
        Key xmax = rect.intervalX.high;
        Key ymax = rect.intervalY.high;
        if (rect.contains(h.x, h.y))
           output.add(h.value);
        if ( less(xmin, h.x) &&  less(ymin, h.y)) query2D(h.SW, rect, output);
        if ( less(xmin, h.x) && !less(ymax, h.y)) query2D(h.NW, rect, output);
        if (!less(xmax, h.x) &&  less(ymin, h.y)) query2D(h.SE, rect, output);
        if (!less(xmax, h.x) && !less(ymax, h.y)) query2D(h.NE, rect, output);
        
        return output;
    }


   /*************************************************************************
    *  helper comparison functions
    *************************************************************************/

    private boolean less(Key k1, Key k2) { return k1.compareTo(k2) <  0; }
    private boolean eq  (Key k1, Key k2) { return k1.compareTo(k2) == 0; }



}

