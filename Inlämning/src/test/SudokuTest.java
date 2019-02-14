package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sudoku.Sudoku;

public class SudokuTest {
	private Sudoku s;

	@Before
	public void setUp() throws Exception {
		this.s = new Sudoku();
	}

	@After
	public void tearDown() throws Exception {
		this.s = null;
	}

	/**
	 * Test solving an empty sudoku.
	 */
	@Test
	public void testEmptySolve() {
		assertTrue(s.sudokuSolve());
	}

	/**
	 * Test solving a sudoku with lots of numbers.
	 */
	@Test
	public void testSolvable() {
		s.setElement(8, 0, 2);
		s.setElement(9, 0, 5);
		s.setElement(6, 0, 7);
		s.setElement(2, 0, 8);
		s.setElement(5, 1, 8);
		s.setElement(1, 2, 0);
		s.setElement(2, 2, 2);
		s.setElement(5, 2, 3);
		s.setElement(2, 3, 3);
		s.setElement(1, 3, 4);
		s.setElement(9, 3, 7);
		s.setElement(5, 4, 1);
		s.setElement(6, 4, 6);
		s.setElement(6, 5, 0);
		s.setElement(2, 5, 7);
		s.setElement(8, 5, 8);
		s.setElement(4, 6, 0);
		s.setElement(1, 6, 1);
		s.setElement(6, 6, 3);
		s.setElement(8, 6, 5);
		s.setElement(8, 7, 0);
		s.setElement(6, 7, 1);
		s.setElement(3, 7, 4);
		s.setElement(1, 7, 6);
		s.setElement(4, 8, 6);
		
		// Print the sudokus to get a visual understanding of it
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(s.getElement(i, j));
			}
			System.out.print("\n");
		}
		
		assertTrue(s.sudokuSolve());
		System.out.println("---------");
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(s.getElement(i, j));
			}
			System.out.print("\n");
		}
	}

	/**
	 * Test solving a sudoku that can't be solved.
	 */
	@Test
	public void testUnsolvable() {
		s.setElement(5, 0, 0);
		s.setElement(5, 5, 0);
		assertFalse(s.sudokuSolve());
	}

	/**
	 * Test solving a sudoku with illegal inputs.
	 */
	@Test
	public void testIllegalSudoku() {
		s.setElement(3, 0, 0);
		s.setElement(3, 1, 1);
		assertFalse(s.sudokuSolve());
	}
	
	/**
	 * Test inserting some illegal arguments.
	 */
	@Test
	public void testIllegalArgumentException() {
		try {
			s.setElement(1, -1, 0);
			fail("Sudoku should throw IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			// Sudoku class sucessfully threw an exception
		}
		try {
			s.getElement( 2, 10);
			fail("Sudoku should throw IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			// Sudoku class sucessfully threw an exception
		}
		try {
			s.setElement(35, 2, 34);
			fail("Sudoku should throw IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			// Sudoku class sucessfully threw an exception
		}
	}

}
