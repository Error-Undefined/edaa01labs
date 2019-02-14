package bst;

public class BinarySearchTree<E extends Comparable<? super E>> {

	BinaryNode<E> root;
	int size;
	private boolean hasAdded = false;

	/**
	 * Constructs an empty binary searchtree.
	 */

	public BinarySearchTree() {
		root = null;
		size = 0;
	}

	/**
	 * Inserts the specified element in the tree if no duplicate exists.
	 * 
	 * @param x
	 *            element to be inserted
	 * @return true if the the element was inserted
	 */
	public boolean add(E x) {
		if (root == null) {
			root = new BinaryNode<E>(x);
			size++;
			return true;
		}

		hasAdded = false; // Bättre sätt att se om ett element blev insatt?
		add(root, x);
		if (hasAdded) {
			size++;
			return true;
		}
		return false;
	}

	/**
	 * Computes the height of tree.
	 * 
	 * @return the height of the tree
	 */
	public int height() {
		return height(root);
	}

	/**
	 * Returns the number of elements in this tree.
	 * 
	 * @return the number of elements in this tree
	 */
	public int size() {
		int s = size;
		return s;
	}

	/**
	 * Print tree contents inorder.
	 */
	public void printTree() {
		StringBuilder sb = new StringBuilder();
		printTree(root, sb);
		System.out.println(sb);
	}

	/**
	 * Builds a complete tree from the elements in the tree.
	 */
	public void rebuild() {
		@SuppressWarnings("unchecked")
		E[] a = (E[]) new Comparable[size];
		toArray(root, a, 0);
		this.root = buildTree(a, 0, size - 1);
	}

	/*
	 * Adds all elements from the tree rooted at n in inorder to the array a
	 * starting at a[index]. Returns the index of the last inserted element + 1 (the
	 * first empty position in a).
	 */
	private int toArray(BinaryNode<E> n, E[] a, int index) {
		if (n == null) {
			return index;
		} else {
			index = toArray(n.left, a, index);
			a[index] = n.element;
			index++;
			index = toArray(n.right, a, index);
		}
		return index;
	}

	/*
	 * Builds a complete tree from the elements a[first]..a[last]. Elements in the
	 * array a are assumed to be in ascending order. Returns the root of tree.
	 */
	private BinaryNode<E> buildTree(E[] a, int first, int last) {
		int mid = (first + last) / 2;

		BinaryNode<E> node = new BinaryNode<E>(a[mid]);

		if (mid > first) {
			node.left = buildTree(a, first, mid - 1);
		}
		if (mid < last) {
			node.right = buildTree(a, mid + 1, last);
		}

		return node;
	}

	/*
	 * Private method for printing out the tree.
	 */
	private void printTree(BinaryNode<E> node, StringBuilder sb) {
		if (node.left != null) {
			printTree(node.left, sb);
		}
		sb.append(node.element.toString());
		sb.append("\n");
		if (node.right != null) {
			printTree(node.right, sb);
		}
	}

	/*
	 * Private recursive method for adding a node.
	 */
	private BinaryNode<E> add(BinaryNode<E> node, E x) {
		// The element wasn't in the tree. Create & add.
		if (node == null) {
			node = new BinaryNode<E>(x);
			hasAdded = true;
			return node;
		} else {
			if (x.compareTo(node.element) < 0) {
				node.left = add(node.left, x);
			} else if (x.compareTo(node.element) > 0) {
				node.right = add(node.right, x);
			}
		}
		return node;
	}

	/*
	 * Private recursive method for calculating the height of the tree
	 */
	private int height(BinaryNode<E> current) {
		if (current == null) {
			return 0;
		} else {
			return 1 + (height(current.left) > height(current.right) ? height(current.left) : height(current.right));
		}

	}

	static class BinaryNode<E> {
		E element;
		BinaryNode<E> left;
		BinaryNode<E> right;

		private BinaryNode(E element) {
			this.element = element;
		}
	}

}
