package edu.ve.unimet.supermetromendeley;

/**
 * Lista enlazada simple con inserción al final.
 * Permite almacenar elementos en orden de inserción y acceder al primer nodo.
 * 
 * @author biancazullo
 */
public class List<T> {
    public class Node<T> {
        public T value;
        public Node<T> next;
        
        public Node(T value) {
            this.value = value;
            this.next = null;
        }
    }
    
    Node<T> first;
    Node<T> last;
    int size;
    
    public List() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }
    
    public void insert(T value) {
        Node<T> node = new Node(value);
        
        if(this.first == null) {
            this.first = node;
            this.last = this.first;
        } else {
            this.last.next = node;
            this.last = node;
        }
        
        this.size++;
    }
    
    public Node<T> getFirstNode() {
        return this.first;
    }
}
