/**
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Baidu company (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.fuda.dc.lhtz.common.util;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.zip.CRC32;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 一致性hash算法
 * 
 * @author xiayanming
 * @date 2014年9月1日
 * @version 1.0
 */
public class ConsistentHash<T> {
    private final static Logger LOG = LoggerFactory.getLogger(ConsistentHash.class);

    private final HashFunction hashFunction;
    private final int numberOfReplicas;
    private final SortedMap<Integer, T> circle = new TreeMap<Integer, T>();

    public ConsistentHash(int numberOfReplicas, Collection<T> nodes) {
        this.hashFunction = new HashFunction();
        this.numberOfReplicas = numberOfReplicas;
        for (T node : nodes) {
            add(node);
        }
        LOG.info("ConsistentHash construct numberOfReplicas = " + numberOfReplicas + "; nodes = " + nodes);
    }

    public void add(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.put(hashFunction.hash(node.toString() + i), node);
        }
        LOG.info("after add node circle.isEmpty() = " + circle.isEmpty());
    }

    public void remove(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.remove(hashFunction.hash(node.toString() + i));
        }
    }

    public T get(String key) {
        if (circle.isEmpty()) {
            return null;
        }
        int hash = hashFunction.hash(key);
        if (!circle.containsKey(hash)) {
            SortedMap<Integer, T> tailMap = circle.tailMap(hash);
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        T t = circle.get(hash);
        return t;
    }

    static class HashFunction {
        public int hash(String key) {
            CRC32 checksum = new CRC32();
            checksum.update(key.getBytes());
            int crc = ((Long) (checksum.getValue())).intValue();
            return crc & 0x7fffffff; // come from xmemcached
        }
    }

}
