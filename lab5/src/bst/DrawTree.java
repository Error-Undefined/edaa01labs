package bst;

public class DrawTree {
	public static void main(String[] args) {
		BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
		bst.add(3);
		bst.add(1);
		bst.add(6);
		bst.add(2);
		bst.add(4);
		bst.add(15);
		bst.add(10);
		BSTVisualizer v = new BSTVisualizer("Testdraw", 300, 300);
		v.drawTree(bst);
		BSTVisualizer v1 = new BSTVisualizer("Testdraw", 300, 300);
		bst.rebuild();
		v1.drawTree(bst);
		BSTVisualizer v2 = new BSTVisualizer("Testdraw", 300, 300);
		BSTVisualizer v3 = new BSTVisualizer("Testdraw", 300, 300);

		BinarySearchTree<Integer> bst2 = new BinarySearchTree<Integer>();
		for (int i = 1; i < 63; i++) {
			bst2.add(i);
		}
		v2.drawTree(bst2);
		bst2.rebuild();
		v3.drawTree(bst2);
	}
}
