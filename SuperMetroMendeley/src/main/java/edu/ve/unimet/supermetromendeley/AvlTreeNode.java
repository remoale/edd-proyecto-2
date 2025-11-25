package edu.ve.unimet.supermetromendeley;

/**
 * Nodo del Árbol AVL para claves String.
 *
 * @author remo
 * @since Nov 23, 2025
 */
public class AvlTreeNode {
    public String key;
    public AvlTreeNode left;
    public AvlTreeNode right;
    public int height;

    public AvlTreeNode(String key) {
        this.key = key;
        this.height = 1;
    }
}
