package com.danylo.myMap.myHashMap;


import java.util.*;

public class MyHashMap<K, V> implements Map<K, V> {
    private static class Node<K,V> implements Map.Entry<K,V> {
        K key;
        V value;
        Node<K, V> next;
        Node(K key, V value) {
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
    private Node<K, V>[] array = new Node[16];

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
        if (key == null) {
            return array[0] != null && array[0].key == null;
        }
        int index = getIndex(key);
        Node node = array[index];
        if (node != null) {
            for (; node != null; node = node.next) {
                if (node.key.equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        for (Node<K, V> node : array) {
            if (node != null) {
                for(; node != null; node = node.next) {
                    if (value != null) {
                        if (value.equals(node.value)) {
                            return true;
                        }
                    }
                    else {
                        if (node.value == null) {
                            return true;
                        }
                    }

                }
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        if (key == null) {
            return getNull();
        }
        int index = getIndex(key);
        V out = null;
        if (array[index] != null) {
            Node<K, V> node = array[index];
            for(; node != null; node = node.next) {
                if (node.key.equals(key)) {
                    out = node.value;
                    break;
                }
            }
        }
        return out;
    }

    @Override
    public V put(K key, V value) {
        if (size == array.length * 2) {
            enlarge();
        }
        if (key == null) {
            return putNull(value);
        }
        V out = value;
        int index = getIndex(key);
        if (array[index] == null) {
            array[index] = new Node<K, V>(key, value);
            size++;
        }
        else {
            for (Node <K, V> node = array[index]; node != null; node = node.next) {
                if (key.equals(node.key)) {
                    out = node.value;
                    node.value = value;
                    break;
                }
                else if (node.next == null){
                    node.next = new Node<K, V>(key, value);
                    size++;
                    break;
                }
            }
        }
        return out;
    }

    @Override
    public V remove(Object key) {
        if (key == null) {
            return removeNull();
        }
        int index = getIndex(key);
        Node<K, V> node = array[index];
        V out = null;
        if (node != null) {
            if (node.key.equals(key)) {
                out = node.value;
                array[index] = node.next ;
                size--;
            }
            else {
                while (node.next != null) {
                    Node prev = node;
                    node = node.next;
                    if (key.equals(node.key)) {
                        out = node.value;
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
        Set<? extends Map.Entry<? extends K, ? extends V>> entrySet = m.entrySet();
        for(Map.Entry<? extends K, ? extends V> entry : entrySet) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        for (int i = 0; i < array.length; i++) {
            array[i] = null;
        }
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> out = new HashSet<>();
        for (Node<K, V> node : array) {
            for (; node != null; node = node.next) {
                out.add(node.key);
            }
        }
        return out;
    }

    @Override
    public Collection<V> values() {
        ArrayList<V> out = new ArrayList<V>();
        for (Node<K, V> node : array) {
            for (; node != null; node = node.next) {
                out.add(node.value);
            }
        }
        return out;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> out = new HashSet<>();
        for (Node<K, V> node : array) {
            for (; node != null; node = node.next) {
                out.add(new Node<K, V>(node.key, node.value));
            }
        }
        return out;
    }

    private int getIndex(Object key) {
       return Math.abs(key.hashCode() % array.length);
    }

    private void enlarge() {
        Node<K, V>[] newArray = new Node[array.length * 2];
        for (int i = 0; i < array.length; i++) {
            Node<K, V> node = array[i];
            if (node != null) {
                while (true) {
                    int index = Math.abs(node.key.hashCode() % newArray.length);
                    Node<K, V> newNode = new Node<K, V>(node.key, node.value);
                    if (newArray[index] == null) {
                        newArray[index] = newNode;
                    } else {
                        Node nextNode = newArray[index];
                        while (nextNode.next != null) {
                            nextNode = nextNode.next;
                        }
                        nextNode.next = newNode;
                    }
                    if (node.next != null) {
                        node = node.next;
                    } else {
                        array[i] = null;
                        break;
                    }
                }
            }
        }
        array = newArray;
    }

    private V putNull(V value) {
        Node<K, V> nullNode = new Node<K, V>(null, value);
        V out = value;
        if (array[0] == null) {
            array[0] = nullNode;
            size++;
        }
        else {
            Node<K, V> node = array[0];
            if (node.key == null) {
                out = node.value;
                node.value = value;
            }
            else {
                nullNode.next = node;
                array[0] = nullNode;
                size++;
            }
        }
        return out;
    }

    private V getNull() {
        V out = null;
        if (array[0] != null && array[0].key == null) {
            out = array[0].value;
        }
        return out;
    }

    private V removeNull() {
        Node<K, V> node = array[0];
        V out = null;
        if (node != null) {
            if (node.key == null) {
                out = node.value;
                array[0] = node.next;
                size--;
            }
        }
        return out;
    }

}
