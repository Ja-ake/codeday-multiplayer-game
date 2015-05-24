package com.jakespringer.codeday.util;

public class Tuple<K, V> {
	public K right;
	public V left;
	
	public Tuple() {
		
	}
	
	public Tuple(K k, V v) {
		right = k;
		left = v;
	}
}
