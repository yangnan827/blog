package com.yangnan.blog.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CopyOnWriteMap<K, V> implements Map<K, V>, Cloneable {

    private volatile Map<K, V> map;

    public CopyOnWriteMap() {
        map = new HashMap<K, V>();
    }

    public CopyOnWriteMap(int initCapacity, float factor) {
        map = new HashMap<>(initCapacity, factor);
    }

    public CopyOnWriteMap(int initCapacity) {
        map = new HashMap<>(initCapacity);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return map.get(key);
    }

    @Override
    public synchronized V put(K key, V value) {
        HashMap<K, V> newMap = new HashMap<K, V>(map);
        V put = newMap.put(key, value);
        map = newMap;
        return put;
    }

    @Override
    public synchronized V remove(Object key) {
        HashMap<K, V> newMap = new HashMap<K, V>(map);
        V remove = newMap.remove(key);
        map = newMap;
        return remove;
    }

    @Override
    public synchronized void putAll(Map<? extends K, ? extends V> m) {
        HashMap<K, V> newMap = new HashMap<K, V>(map);
        newMap.putAll(m);
        map = newMap;
    }

    @Override
    public synchronized void clear() {
        this.map = new HashMap<K, V>();
    }

    @Override
    public synchronized Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public synchronized Collection<V> values() {
        return map.values();
    }

    @Override
    public synchronized Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }
}
