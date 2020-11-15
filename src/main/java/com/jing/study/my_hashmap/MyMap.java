package com.jing.study.my_hashmap;

public interface MyMap<K,V> {



    V put(K key,V value);
    V get(K key);
    void delete(K key);
    int size();
}
