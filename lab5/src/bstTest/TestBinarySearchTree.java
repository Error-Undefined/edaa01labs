package bstTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bst.*;

public class TestBinarySearchTree {

	private BinarySearchTree<Integer> bst;

	@Before
	public void setUp() throws Exception {
		bst = new BinarySearchTree<Integer>();
	}

	@After
	public void tearDown() throws Exception {
		bst = null;
	}

	/**
	 * Test the add and size methods.
	 */
	@Test
	public void testAddAndSize() {
		assertEquals("New tree should be empty", bst.size(), 0);
		assertTrue("Adding an element should return true", bst.add(3));
		assertEquals("Tree size should change to 1", bst.size(), 1);

		assertFalse("Adding the same element should return false", bst.add(3));
		assertEquals("Size shouldn't change when adding the same element", bst.size(), 1);

		// Testing on a bigger tree with more elements
		bst.add(4);
		bst.add(15);
		bst.add(31);
		bst.add(6);
		bst.add(0);
		assertEquals("Tree should now have size 6", bst.size(), 6);
		assertTrue("Tree should return true when adding a new node", bst.add(5));

		assertFalse("Adding an element already in the tree should return false", bst.add(31));
		assertEquals("Size shouldn't change when adding the same element, but should now be 7", bst.size(), 7);
	}

	/**
	 * Test printing the tree recursively.
	 */
	@Test
	public void testPrintTree() {
		bst.add(4);
		bst.add(15);
		bst.add(31);
		bst.add(6);
		bst.add(0);
		bst.add(5);
		bst.printTree();
		// Tree should be printed inorder: 0 4 5 6 15 31.
	}

	/**
	 * Tests that the height of the tree is calculated right.
	 */
	@Test
	public void testHeight() {
		// Test height on empty tree
		assertEquals("Height of empty tree is 0",bst.height(),0);
		
		// Create a tree of height 4
		bst.add(4);
		bst.add(15);
		bst.add(31);
		bst.add(6);
		bst.add(0);
		bst.add(5);
		
		assertEquals("Height of tree should be 4",bst.height(),4);
	}
}
