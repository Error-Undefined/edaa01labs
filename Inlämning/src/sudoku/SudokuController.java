package sudoku;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class SudokuController extends Application {

	// css for textboxes
	private static final String CSS1 = "-fx-border-color: black;-fx-background-color: orange;-fx-alignment: center ;";
	private static final String CSS2 = "-fx-border-color: black;-fx-background-color: white;-fx-alignment: center ;";

	private static final int BOX_GAP = 3;

	private List<OneCharTextField> list = new ArrayList<OneCharTextField>(); // List to keep track of all the textboxes
	private Sudoku sudoku; // Attribute for the sudoku module

	@Override
	public void start(Stage stage) throws Exception {

		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 300, 400);

		stage.setResizable(false);
		stage.setTitle("Sudoku Solver");
		stage.setScene(scene);

		sudoku = new Sudoku();
		
		GridPane mainPane = new GridPane();
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setHgap(BOX_GAP);
		mainPane.setVgap(BOX_GAP);

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				OneCharTextField text = new OneCharTextField("[1-9]"); // [1-9] regex for allowed chars
				TilePane subPane = new TilePane();
				subPane.setPrefSize(30, 30);
				text.setPrefSize(30, 30);

				// Color the squares with css
				if ((Math.floorDiv(i, 3) == 0 || Math.floorDiv(i, 3) == 2) && Math.floorDiv(j, 3) != 1) {
					text.setStyle(CSS1);
				} else if (Math.floorDiv(i, 3) == 1 && Math.floorDiv(j, 3) == 1) {
					text.setStyle(CSS1);
				} else {
					text.setStyle(CSS2);
				}

				list.add(text);

				text.setOnKeyPressed(e -> textBoxProperties(e, text));
				subPane.getChildren().add(text);
				mainPane.addRow(i, subPane);
			}
		}

		HBox bottom = new HBox();

		Button b1 = new Button("Solve");
		Button b2 = new Button("Clear");

		b1.setDefaultButton(true);

		bottom.getChildren().addAll(b1, b2);

		root.setCenter(mainPane);
		root.setBottom(bottom);

		b1.setOnAction(e -> sudokuSolve());
		b2.setOnAction(e -> sudokuClear());

		stage.show();
	}

	/*
	 * Add different properties to the textfields
	 */
	private void textBoxProperties(KeyEvent e, TextField text) {
		KeyCode k = e.getCode();

		// Make it so the text gets replaced on keypress because we can
		if (k.isDigitKey() && !k.equals(KeyCode.DIGIT0)) {
			text.setText(e.getText());
		}

		// Make it so we can jump between textfields with arrowkeys
		if (k.isArrowKey()) {
			int row = list.indexOf(text) % 9;
			int col = list.indexOf(text) / 9;
			int index = 0;
			if (k.equals(KeyCode.LEFT)) {
				row--;
				if (row < 0) {
					row = 8;
				}
				index = col * 9 + row;
			}
			if (k.equals(KeyCode.RIGHT)) {
				row++;
				if (row > 8) {
					row = 0;
				}
				index = col * 9 + row;
			}
			if (k.equals(KeyCode.DOWN)) {
				col++;
				if (col > 8) {
					col = 0;
				}
				index = col * 9 + row;
			}
			if (k.equals(KeyCode.UP)) {
				col--;
				if (col < 0) {
					col = 8;
				}
				index = col * 9 + row;
			}
			list.get(index).requestFocus();
		} else if (k.isDigitKey()&&k.equals(KeyCode.DIGIT0)) {
			text.setText(text.getText());
		}
	}

	/*
	 * Solve the sudoku.
	 */
	private void sudokuSolve() {

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				String s = list.get(j + i * 9).getText();

				// OneCharTextField limits the entries to the numbers 1-9
				if (!s.equals("")) {
					int value = Integer.parseInt(s);
					sudoku.setElement(value, i, j);
				} else {
					// Reset the element to 0 if there isn't any input to not use outdated inputs
					sudoku.setElement(0, i, j);
				}
			}
		}
		// Call the solve function in the if statement
		if (sudoku.sudokuSolve()) {
			int i = 0;
			int j = 0;
			for (OneCharTextField t : list) {
				t.setText(String.valueOf(sudoku.getElement(i, j)));
				j++;
				if (j == 9) {
					j = 0;
					i++;
				}
			}
		} else {
			Alert a = new Alert(AlertType.INFORMATION, "Ingen lösning hittades");
			a.show();
		}

	}

	/*
	 * Clear the sudoku.
	 */
	private void sudokuClear() {

		for (OneCharTextField t : list) {
			t.clear();
		}
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}
