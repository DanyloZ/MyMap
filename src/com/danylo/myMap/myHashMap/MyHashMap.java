package com.danylo.myMap.myHashMap;


import java.util.*;

public class MyHashMap<K, V> implements Map<K, V> {
    private static class Node {
        Object key;
        Object value;
        Node next;
        Node(Object key, Object value) {
            this.key = key;
            this.value = value;
        }
    }

    private class Entry<K, V> implements Map.Entry<K, V>{

        K key;
        V value;
        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V out = this.value;
            this.value = value;
            return out;
        }
    }

    private int size;
    private Node[] array = new Node[16];



    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        int index = getIndex(key);
        Node node = array[index];
        if (node != null) {
            while (true) {
                if (node.key.equals(key)) {
                    return true;
                }
                else if (node.next != null) {
                    node = node.next;
                }
                else {
                    break;
                }
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        for (Node node : array) {
            if (node != null) {
                while (true) {
                    if (node.value.equals(value)) {
                        return true;
                    }
                    else if (node.next != null) {
                        node = node.next;
                    }
                    else {
                        break;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        int index = getIndex(key);
        V out = null;
        if (array[index] != null) {
            Node node = array[index];
            while (true) {
                if (node.key.equals(key)) {
                    out = (V)node.value;
                    break;
                }
                else if (node.next != null) {
                    node = node.next;
                }
                else {
                    break;
                }
            }
        }
        return out;
    }

    @Override
    public V put(K key, V value) {
        enlarge();
        V out = value;
        int index = getIndex(key);
        if (array[index] == null) {
            array[index] = new Node(key, value);
            size++;
        }
        else {
            Node node = array[index];
            while (true) {
                if (node.key.equals(key)) {
                    out = (V)node.value;
                    node.value = value;
                    break;
                }
                else if (node.next != null) {
                    node = node.next;
                }
                else {
                    node.next = new Node(key, value);
                    size++;
                    break;
                }
            }
        }
        return out;
    }

    @Override
    public V remove(Object key) {
        int index = getIndex(key);
        Node node = array[index];
        V out = null;
        if (node != null) {
            if (node.key.equals(key)) {
                out = (V)node.value;
                Node temp = node.next;
                array[index] = node.next ;
                size--;
            }
            else {
                while (node.next != null) {
                    Node prev = node;
                    node = node.next;
                    if (node.key.equals(key)) {
                        out = (V)node.value;
                        node.key = null;
                        node.value = null;
                        prev.next = node.next;
                        size--;
                        break;
                    }
                }
            }
        }
       return out;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
//        Set<Map<? extends K, ? extends V>> entrySet = m.entrySet();
//        for (Entry<? extends K, ? extends V> entry : entrySet) {
//            put(entry.getKey(), entry.getValue());
//        }
    }

    @Override
    public void clear() {
        array = new Node[16];
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> out = new HashSet<>();
        for (Node node : array) {
            if (node != null) {
                out.add((K)node.key);
                while (node.next != null) {
                    node = node.next;
                    out.add((K)node.key);
                }
            }
        }
        return out;
    }

    @Override
    public Collection<V> values() {
        ArrayList<V> out = new ArrayList<V>();
        for (Node node : array) {
            if (node != null) {
                out.add((V)node.value);
                while (node.next != null) {
                    node = node.next;
                    out.add((V)node.value);
                }
            }
        }
        return out;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
//        Set<Map.Entry<K, V>> out = new HashSet<>();
//        for (Node node : array) {
//            if (node != null) {
//                out.add(new Entry<K, V>((K)node.key, (V)node.value));
//            }
//            while (node.next != null) {
//                node = node.next;
//                out.add(new Entry<K, V>((K)node.key, (V)node.value));
//            }
//        }
//        return out;
        return null;
    }


    private int getIndex(Object key) {
       return Math.abs(key.hashCode() % array.length);
    }

    private void enlarge() {
        if (size == array.length * 2) {
            Node[] newArray = new Node[array.length * 2];
            for (int i = 0; i < array.length; i++) {
                Node node = array[i];
                if (node != null) {
                    while (true) {
                        int index = Math.abs(node.key.hashCode() % newArray.length);
                        Node newNode = new Node(node.key, node.value);
                        if (newArray[index] == null) {
                            newArray[index] = newNode;
                        }
                        else {
                            Node nextNode = newArray[index];
                            while(nextNode.next != null) {
                                nextNode = nextNode.next;
                            }
                            nextNode.next = newNode;
                        }
                        if (node.next != null) {
                            node = node.next;
                        }
                        else {
                            array[i] = null;
                            break;
                        }
                    }
                }
            }
            array = newArray;
        }
    }
}
