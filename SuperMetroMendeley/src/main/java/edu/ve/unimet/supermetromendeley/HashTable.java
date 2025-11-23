/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.ve.unimet.supermetromendeley;

/**
 *
 * @author biancazullo
 */
public class HashTable<V> {
    private static final int DEFAULT_CAPACITY = 32;
    private static final double LOAD_FACTOR_THRESHOLD = 0.75;
    
    private HashTableNode<String, V>[] buckets;
    private int size;
    private int capacity;
    
    public HashTable() {
        this.capacity = DEFAULT_CAPACITY;
        this.buckets = new HashTableNode[this.capacity];
        this.size = 0;
    }
    
    // FNV-1a de 32 bits
    private int hash(String key) {
        int h = 0x811c9dc5;
        byte[] data = key.getBytes();
        
        for(byte b : data) {
            h ^= (b & 0xFF);
            h *= 0x01000193;
        }
        
        return h % capacity;
    }
    
    private void resize() {
        HashTableNode<String, V>[] oldBuckets = buckets;
        capacity *= 2;
        buckets = new HashTableNode[capacity];
        size = 0;

        for (HashTableNode<String, V> head : oldBuckets) {
            while (head != null) {
                this.put(head.key, head.value);
                head = head.next;
            }
        }
    }
    
    public void put(String key, V value) {
        if (size >= capacity * LOAD_FACTOR_THRESHOLD) {
            resize();
        }

        int index = hash(key);
        HashTableNode<String, V> head = buckets[index];

        if (head == null) {
            buckets[index] = new HashTableNode<>(key, value);
            size++;
            return;
        }

        HashTableNode<String, V> current = head;
        while (current != null) {
            if (current.key.equals(key)) {
                current.value = value;
                return;
            }
            current = current.next;
        }

        HashTableNode<String, V> newNode = new HashTableNode<>(key, value);
        newNode.next = head;
        buckets[index] = newNode;
        size++;
    }
    
    public V remove(String key) {
        int index = hash(key);
        HashTableNode<String, V> current = buckets[index];
        HashTableNode<String, V> previous = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (previous == null) {
                    buckets[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                return current.value;
            }
            previous = current;
            current = current.next;
        }

        return null;
    }
    
    public V get(String key) {
        int index = hash(key);
        HashTableNode<String, V> current = buckets[index];
        
        while(current != null) {
            if(current.key.equals(key))
                return current.value;
            
            current = current.next;
        }
        
        return null;
    }
}
