package sudoku;

public class Sudoku {

	private int[][] sudoku;

	/**
	 * Creates a new sudoku with dimentions 9x9. The sudoku is initially filled with
	 * zeroes.
	 */
	public Sudoku() {
		sudoku = new int[9][9];

		for (int i = 0; i < 9; i++) {
			for (int k = 0; k < 9; k++) {
				sudoku[i][k] = 0;
			}

		}
	}

	/**
	 * Gets the element in row row and column col.
	 * 
	 * @param row
	 *            the row of the element
	 * @param col
	 *            the column of the element
	 * @return The element in row row and column col
	 * @throws IllegalArgumentException
	 *             if row or col are less than 0 or greater than 8
	 */
	public int getElement(int row, int col) {
		if (row < 0 || col < 0 || row > 8 || col > 8) {
			throw new IllegalArgumentException();
		}
		int ret = sudoku[row][col];
		return ret;
	}

	/**
	 * Sets the value of the element in row row and column col. There is no
	 * restriction on the value; meaning that it does not have to be a valid number
	 * in a sudoku.
	 * 
	 * @param value
	 *            the element's value
	 * @param row
	 *            the row of the element
	 * @param col
	 *            the column of the element
	 * @throws IllegalArgumentException
	 *             if row or col are less than 0 or greater than 8
	 */
	public void setElement(int value, int row, int col) {
		if (row < 0 || col < 0 || row > 8 || col > 8) {
			throw new IllegalArgumentException();
		}
		sudoku[row][col] = value;
	}

	/**
	 * Solves the sudoku, if possible.
	 * 
	 * @return true if the sudoku was solvable, otherwise false
	 */
	public boolean sudokuSolve() {
		return solve(0, 0);
	}

	/* Privat rekursiv hjälpmetod för lösning av sudoku */
	private boolean solve(int row, int col) {
		// Vi har gått igenom hela sudokut och hittat en lösning.
		if (row == 9) {
			return true;
		}

		// Användaren har matat in en siffra.
		if (sudoku[row][col] != 0) {
			// Kolla om indatan är giltig.
			if (isValidInput(sudoku[row][col], row, col)) {
				// Den var giltig, kolla om siffran har giltig lösning.
				if (col == 8) {
					return solve(row + 1, 0);
				} else {
					return solve(row, col + 1);
				}
			} else {
				// Den var inte giltig, returnera false.
				return false;
			}
		}
		// Användaren har inte matat in en siffra.
		else {
			// Kolla alla tal 1-9.
			for (int i = 1; i < 10; i++) {
				// Kolla om talet i är giltigt.
				if (isValidInput(i, row, col)) {
					// Det är möjligt, fyll i och hoppa till nästa ruta. Om nästa ruta (och därmed
					// hela den rekursiva sekvensen) returnerar true så fungerade talet och vi kan
					// returnera true.
					sudoku[row][col] = i;
					if (col == 8) {
						if (solve(row + 1, 0)) {
							return true;
						}
					} else {
						if (solve(row, col + 1)) {
							return true;
						}
					}
				}
			}
			// Ingen av talen 1-9 fungerade, returnera false och återställ rutan till 0.
			sudoku[row][col] = 0;
			return false;
		}
	}

	/*
	 * Privat metod för att kolla om ett tal är giltigt.
	 */
	private boolean isValidInput(int value, int row, int col) {
		// Kolla raden
		for (int i = 0; i < 9; i++) {
			if (value == sudoku[row][i] && i != col) {
				return false;
			}
		}

		// Kolla kolonnen
		for (int i = 0; i < 9; i++) {
			if (value == sudoku[i][col] && i != row) {
				return false;
			}
		}

		int startRow = Math.floorDiv(row, 3) * 3;
		int startCol = Math.floorDiv(col, 3) * 3;

		// Kolla alla 9 rutor
		for (int i = startRow; i < startRow + 3; i++) {
			for (int k = startCol; k < startCol + 3; k++) {
				if (sudoku[i][k] == value) {
					if (i != row && k != col) { // Om vi kollar på value vill vi inte avbryta
						return false;
					}
				}
			}
		}

		return true;
	}
}
