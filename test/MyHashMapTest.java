import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import com.danylo.myMap.myHashMap.MyHashMap;

import java.util.*;


public class MyHashMapTest {
    @Test
    public void testGet() {
        MyHashMap<Integer, String> map = new MyHashMap<>();
        map.put(1, "a");
        map.put(2, "b");
        assertEquals("a", map.get(1));
        assertEquals("b", map.get(2));
        assertEquals(null, map.get(3));
    }

    @Test
    public void testPut() {
        MyHashMap<Integer, String> map = new MyHashMap<>();
        map.put(1, "a");
        map.put(2, "b");
        assertEquals("a", map.get(1));
        assertEquals("b", map.get(2));
        assertEquals(2, map.size());
        map.put(1, "1");
        assertEquals(2, map.size());
        assertEquals("1", map.get(1));
        assertEquals(null, map.get(3));
        map.put(null, "null");
        assertEquals(3, map.size());
        assertEquals("null", map.get(null));
        map.put(null, "0");
        assertEquals(3, map.size());
        assertEquals("0", map.get(null));
    }

    @Test
    public void testContainsKey() {
        MyHashMap<Integer, String> map = new MyHashMap<>();
        map.put(1, "a");
        map.put(2, "b");
        map.put(56, "c");
        assertTrue(map.containsKey(1));
        assertTrue(map.containsKey(2));
        assertTrue(map.containsKey(56));
        assertFalse(map.containsKey(4));
        assertFalse(map.containsKey(null));
        map.put(null, "0");
        assertTrue(map.containsKey(null));
    }

    @Test
    public void testContainsValue() {
        MyHashMap<Integer, String> map = new MyHashMap<>();
        map.put(10, "a");
        map.put(23, "b");
        map.put(56, "c");
        map.put(126, null);
        assertTrue(map.containsValue("a"));
        assertTrue(map.containsValue("b"));
        assertTrue(map.containsValue("c"));
        assertFalse(map.containsValue("d"));
        assertTrue(map.containsValue(null));
    }

    @Test
    public void testEnlarge() {
        MyHashMap<Integer, String> map = new MyHashMap<>();
        for (int i = 0; i < 36; i++) {
            map.put(i, i + "a");
        }
        assertTrue(map.containsKey(35));
        assertEquals("15a", map.get(15));
        assertEquals("35a", map.get(35));
        assertEquals(36, map.size());
    }

    @Test
    public void testRemove() {
        MyHashMap<Integer, String> map = new MyHashMap<>();
        map.put(0, "0");
        map.put(1, "a");
        map.put(2, "b");
        map.put(3, "c");
        map.put(13, "13");
        map.put(14, "14");
        map.put(17, "17");
        map.put(18, "18");
        map.put(33, "33");
        map.put(34, "34");
        map.put(null, "35");
        assertEquals(11, map.size());
        assertEquals("a", map.remove(1));
        assertEquals("b", map.remove(2));
        assertEquals("c", map.remove(3));
        assertEquals("35", map.remove(null));
        assertTrue(map.containsKey(17));
        assertTrue(map.containsKey(18));
        assertTrue(map.containsKey(33));
        assertTrue(map.containsKey(34));
        assertFalse(map.containsKey(1));
        assertFalse(map.containsKey(2));
        assertFalse(map.containsKey(3));
        assertEquals(7, map.size());
        assertEquals(null, map.remove(20));
        assertEquals(null, map.remove(null));
        assertEquals(7, map.size());
    }

    @Test
    public void testPutAll() {
        MyHashMap<Integer, String> myMap = new MyHashMap<>();
        myMap.put(1, "a");
        myMap.put(2, "b");
        myMap.put(3, "c");
        HashMap<Integer, String> map = new HashMap<>();
        map.put(2, "2b");
        map.put(3, "2c");
        map.put(13, "13");
        map.put(14, "14");
        map.put(17, "17");
        myMap.putAll(map);
        assertTrue(myMap.containsKey(17));
        assertTrue(myMap.containsKey(14));
        assertTrue(myMap.containsKey(13));
        assertEquals("a", myMap.get(1));
        assertEquals("2b", myMap.get(2));
        assertEquals("2c", myMap.get(3));
        assertEquals(6, myMap.size());
    }

    @Test
    public void testKeySet() {
        MyHashMap<Integer, String> map = new MyHashMap<>();
        map.put(1, "a");
        map.put(2, "b");
        map.put(3, "c");
        map.put(13, "13");
        map.put(14, "14");
        map.put(17, "17");
        map.put(18, "18");
        map.put(33, "33");
        map.put(34, "34");
        map.put(null, "0");
        Set<Integer> set = map.keySet();
        assertEquals(10, set.size());
        assertTrue(set.contains(1));
        assertTrue(set.contains(2));
        assertTrue(set.contains(3));
        assertTrue(set.contains(13));
        assertTrue(set.contains(14));
        assertTrue(set.contains(17));
        assertTrue(set.contains(18));
        assertTrue(set.contains(33));
        assertTrue(set.contains(34));
        assertTrue(set.contains(null));
        assertFalse(set.contains(4));
        assertFalse(set.contains(35));
    }

    @Test
    public void testValues() {
        MyHashMap<Integer, String> map = new MyHashMap<>();
        map.put(1, "a");
        map.put(2, "b");
        map.put(3, "c");
        map.put(13, "13");
        map.put(14, "14");
        map.put(17, "17");
        map.put(18, "a");
        map.put(33, "33");
        map.put(34, "b");
        map.put(null, null);
        Collection<String> list = map.values();
        assertEquals(10, list.size());
        assertTrue(list.contains("a"));
        assertTrue(list.contains("b"));
        assertTrue(list.contains("c"));
        assertTrue(list.contains("13"));
        assertTrue(list.contains("14"));
        assertTrue(list.contains("17"));
        assertTrue(list.contains("a"));
        assertTrue(list.contains("33"));
        assertTrue(list.contains("b"));
        assertTrue(list.contains(null));
        assertFalse(list.contains("4"));
        assertFalse(list.contains("35"));
    }

    @Test
    public void testEntrySet() {
        MyHashMap<Integer, String> map = new MyHashMap<>();
        map.put(1, "a");
        map.put(2, "b");
        map.put(3, "c");
        map.put(13, "13");
        map.put(14, "14");
        map.put(17, "17");
        map.put(18, "a");
        map.put(33, "33");
        map.put(34, "b");
        Set<Map.Entry<Integer, String>> set = map.entrySet();
        HashMap<Integer, String> hashMap = new HashMap<>();
        for (Map.Entry<Integer, String> entry : set) {
            hashMap.put(entry.getKey(), entry.getValue());
        }
        assertEquals(9, hashMap.size());
        assertEquals("a", hashMap.get(1));
        assertEquals("b", hashMap.get(2));
        assertEquals("c", hashMap.get(3));
        assertEquals("13", hashMap.get(13));
        assertEquals("14", hashMap.get(14));
        assertEquals("17", hashMap.get(17));
        assertEquals("a", hashMap.get(18));
        assertEquals("33", hashMap.get(33));
        assertEquals("b", hashMap.get(34));
    }
 }
