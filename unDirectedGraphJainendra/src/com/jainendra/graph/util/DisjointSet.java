package com.jainendra.graph.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class DisjointSet<T> {
	private final Map<T, Node<T>> elements = new HashMap<T, Node<T>>();

	public DisjointSet() {
	}

	public DisjointSet(Collection<? extends T> elems) {
		for (T elem : elems)
			add(elem);
	}

	public boolean add(T elem) {
		if (elements.containsKey(elem))
			return false;

		elements.put(elem, new Node<T>(elem, elem));
		return true;
	}

	public Node<T> find(T elem) {
		if (!elements.containsKey(elem)) {
			return null;
		}

		return find0(elem);
	}

	private Node<T> find0(T elem) {
		Node<T> info = elements.get(elem);

		if (info.parent.equals(elem))
			return info;

		info = find0(info.parent);
		return info;
	}

	public void union(T one, T two) {
		Node<T> nodeOne = find(one);
		Node<T> nodeTwo = find(two);

		if (nodeOne == nodeTwo)
			return;

		if (nodeOne.rank > nodeTwo.rank) {
			nodeTwo.parent = nodeOne.parent;
			nodeOne.size = nodeOne.size + nodeTwo.size;
		} else if (nodeOne.rank < nodeTwo.rank) {
			nodeOne.parent = nodeTwo.parent;
			nodeTwo.size = nodeOne.size + nodeTwo.size;
		} else {
			nodeTwo.parent = nodeOne.parent;
			nodeOne.size = nodeOne.size + nodeTwo.size;
			nodeOne.rank++;
		}
	}

	public static final class Node<T> {
		public T elem;
		public T parent;
		public int size = 0;
		public int rank = 0;

		Node(T elem, T parent) {
			this.elem = elem;
			this.parent = parent;
			this.size = 1;
		}
	}
}