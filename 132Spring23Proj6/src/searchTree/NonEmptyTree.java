package searchTree;

import java.util.Collection;

/**
 * This class represents a non-empty search tree. An instance of this class
 * should contain:
 * <ul>
 * <li>A key
 * <li>A value (that the key maps to)
 * <li>A reference to a left Tree that contains key:value pairs such that the
 * keys in the left Tree are less than the key stored in this tree node.
 * <li>A reference to a right Tree that contains key:value pairs such that the
 * keys in the right Tree are greater than the key stored in this tree node.
 * </ul>
 * 
 */
public class NonEmptyTree<K extends Comparable<K>, V> implements Tree<K, V> {

	private Tree<K, V> left, right;
	private K key;
	private V value;

	public NonEmptyTree(K key, V value, Tree<K, V> left, Tree<K, V> right) {
		this.key = key;
		this.value = value;
		this.left = left;
		this.right = right;
	}

	public V search(K key) {
		int comparison = key.compareTo(this.key);
		if (comparison < 0) {
			return this.left.search(key);
		} else if (comparison > 0) {
			return this.right.search(key);
		}
		return value;
	}

	public NonEmptyTree<K, V> insert(K key, V value) {
		int comparison = key.compareTo(this.key);
		if (comparison == 0) {
			this.value = value;
		}
		if (comparison < 0) {
			left = left.insert(key, value);
		}
		if (comparison > 0) {
			right = right.insert(key, value);
		}
		return this;
	}

	public Tree<K, V> delete(K key) {
		int comparison = key.compareTo(this.key);
		if (comparison > 0) {
			this.right = this.right.delete(key);
		}
		if (comparison < 0) {
			this.left = this.left.delete(key);
		}
		if (comparison == 0) {
			try {
				this.key = this.right.min();
				this.value = this.right.search(this.key);
				right = right.delete(this.key);
			} catch (TreeIsEmptyException e) {
				return this.left;
			}
		}
		return this;
	}

	public K max() {
		try {
			return right.max();
		} catch (TreeIsEmptyException e) {
			return key;
		}
	}

	public K min() {
		try {
			return left.min();
		} catch (TreeIsEmptyException e) {
			return key;
		}
	}

	public int size() {
		return 1 + (left.size() + right.size());
	}

	/*
	 * Just a normal depth-first traversal to get an ordered collection.
	 */
	public void addKeysToCollection(Collection<K> c) {
		left.addKeysToCollection(c);
		c.add(key);
		right.addKeysToCollection(c);
	}

	/*
	 * Checks the two bounds to see if the key is inside the range, return it if so
	 * and the 1-2 subtrees (left, right or both) recursively depending on the
	 * position of the key.
	 */
	public Tree<K, V> subTree(K fromKey, K toKey) {
		int leftbound = this.key.compareTo(fromKey);
		int rightbound = this.key.compareTo(toKey);
		if (leftbound >= 0 && rightbound <= 0) {
			Tree<K, V> empty = EmptyTree.getInstance();
			NonEmptyTree<K, V> subtree = new NonEmptyTree<K, V>(key, value, empty, empty);
			if (leftbound != 0) {
				subtree.left = this.left.subTree(fromKey, toKey);
			}
			if (rightbound != 0) {
				subtree.right = this.right.subTree(fromKey, toKey);
			}
			return subtree;
		}
		if (leftbound < 0) {
			return this.right.subTree(fromKey, toKey);
		}
		if (rightbound > 0) {
			return this.left.subTree(fromKey, toKey);
		}
		return null;
	}
}