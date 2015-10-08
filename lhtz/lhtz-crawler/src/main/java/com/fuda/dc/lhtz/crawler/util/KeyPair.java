package com.fuda.dc.lhtz.crawler.util;

/**
 * k-v对类
 * 
 * @author kevin
 *
 * @param <K>
 * @param <V>
 */
public class KeyPair<K, V> {
	private K key;
	private V value;
	
	public KeyPair(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	public K getKey() {
		return key;
	}
	public void setKey(K key) {
		this.key = key;
	}
	public V getValue() {
		return value;
	}
	public void setValue(V value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(key);
		sb.append(", ");
		sb.append(value);
		return sb.toString();
	}
}
