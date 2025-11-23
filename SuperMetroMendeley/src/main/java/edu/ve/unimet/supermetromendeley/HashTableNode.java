/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.ve.unimet.supermetromendeley;

/**
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
