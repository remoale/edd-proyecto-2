package edu.ve.unimet.supermetromendeley;

import java.text.Collator;
import java.util.Locale;

/**
 * Árbol AVL para strings (autores y palabras clave).
 *
 * @author remo
 * @since Nov 23, 2025
 */
public class AvlTree {

    private AvlTreeNode root;
    private final Collator collator;

    public AvlTree() {
        collator = Collator.getInstance(new Locale("es", "ES"));
        collator.setStrength(Collator.PRIMARY);
    }

        public void insert(String key) {
        if (key == null) return;
        root = insert(root, key);
    }

    public boolean contains(String key) {
        AvlTreeNode current = root;
        while (current != null) {
            int cmp = collator.compare(key, current.key);
            if (cmp == 0) return true;
            else if (cmp < 0) current = current.left;
            else current = current.right;
        }
        return false;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void toInorderList(List<String> dest) {
        inorder(root, dest);
    }

    private AvlTreeNode insert(AvlTreeNode node, String key) {
        if (node == null)
            return new AvlTreeNode(key);

        int cmp = collator.compare(key, node.key);

        if (cmp < 0)
            node.left = insert(node.left, key);
        else if (cmp > 0)
            node.right = insert(node.right, key);
        else
            return node;

        updateHeight(node);
        return rebalance(node);
    }

    private void updateHeight(AvlTreeNode node) {
        int lh = (node.left != null) ? node.left.height : 0;
        int rh = (node.right != null) ? node.right.height : 0;
        node.height = Math.max(lh, rh) + 1;
    }

    private int getBalance(AvlTreeNode node) {
        if (node == null) return 0;
        int lh = (node.left != null) ? node.left.height : 0;
        int rh = (node.right != null) ? node.right.height : 0;
        return lh - rh;
    }

    private AvlTreeNode rebalance(AvlTreeNode node) {
        int balance = getBalance(node);

        // LL
        if (balance > 1 && getBalance(node.left) >= 0)
            return rotateRight(node);

        // LR
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // RR
        if (balance < -1 && getBalance(node.right) <= 0)
            return rotateLeft(node);

        // RL
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    private AvlTreeNode rotateRight(AvlTreeNode y) {
        AvlTreeNode x = y.left;
        AvlTreeNode t2 = x.right;

        x.right = y;
        y.left = t2;

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    private AvlTreeNode rotateLeft(AvlTreeNode x) {
        AvlTreeNode y = x.right;
        AvlTreeNode t2 = y.left;

        y.left = x;
        x.right = t2;

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    private void inorder(AvlTreeNode node, List<String> dest) {
        if (node == null) return;
        inorder(node.left, dest);
        dest.insert(node.key);
        inorder(node.right, dest);
    }
}