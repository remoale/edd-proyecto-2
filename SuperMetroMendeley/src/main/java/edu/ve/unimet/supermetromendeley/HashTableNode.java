package edu.ve.unimet.supermetromendeley;

/**
 * Nodo usado en la tabla hash para implementar encadenamiento.
 * Almacena una clave, su valor asociado y una referencia al siguiente nodo.
 * 
 * @author biancazullo
 */
public class HashTableNode<K, V> {
    public K key;
    public V value;
    public HashTableNode<K, V> next;
    
    public HashTableNode(K key, V value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }
}
