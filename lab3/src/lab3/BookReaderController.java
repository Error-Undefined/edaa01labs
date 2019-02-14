package lab3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Map.Entry;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import textproc.GeneralWordCounter;

public class BookReaderController extends Application {

	private Set<String> stopwords;
	private ObservableList<Map.Entry<String, Integer>> wordList;
	private ListView<Map.Entry<String, Integer>> listView;

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane root = new BorderPane();

		Scene scene = new Scene(root, 500, 500);

		stage.setTitle("Book Reader");
		stage.setScene(scene);

		Scanner scan = new Scanner(new File("../lab1/undantagsord.txt"));
		stopwords = new HashSet<String>();
		while (scan.hasNext()) {
			stopwords.add(scan.next());
		}
		scan.close();

		File initialFile = new File("../lab1/nilsholg.txt");
		Scanner s = new Scanner(initialFile);
		GeneralWordCounter w = new GeneralWordCounter(stopwords);
		s.useDelimiter("(\\s|,|\\.|:|;|!|\\?|'|\\\")+");

		while (s.hasNext()) {
			String word = s.next().toLowerCase();
			w.process(word);
		}

		s.close();

		wordList = FXCollections.observableArrayList(w.getWords());
		listView = new ListView<Map.Entry<String, Integer>>(wordList);

		root.setCenter(listView);

		HBox hbox = new HBox();
		RadioButton b1 = new RadioButton("Frequency");
		RadioButton b2 = new RadioButton("Alphabetic");
		Button b3 = new Button("Search");
		TextField t1 = new TextField();

		HBox.setHgrow(t1, Priority.ALWAYS);

		hbox.getChildren().addAll(b1, b2, t1, b3);

		root.setBottom(hbox);

		b3.setDefaultButton(true);

		b1.setOnAction(event -> {

			wordList.sort((w1, w2) -> w2.getValue() - w1.getValue() != 0 ? w2.getValue() - w1.getValue()
					: w1.getKey().compareTo(w2.getKey()));
			// wordList.sort(new WordCountComparator());
			b2.setSelected(false);
			listView.scrollTo(0);
		});
		b2.setOnAction(event -> {
			wordList.sort((w1, w2) -> w1.getKey().compareTo(w2.getKey()));
			// wordList.sort(new AlphabeticComparator());
			b1.setSelected(false);
			listView.scrollTo(0);
		});
		b3.setOnAction(event -> {
			String input = t1.getText().trim();
			Iterator<Entry<String, Integer>> it = wordList.iterator();
			int index;
			boolean found = false;
			while (it.hasNext()) {
				if (it.next().getKey().equalsIgnoreCase(input)) {
					index = wordList.indexOf(it.next()) - 1;
					listView.scrollTo(index);
					listView.getSelectionModel().clearAndSelect(index);
					found = true;
					break;
				}
			}

			if (!found) {
				Alert alert = new Alert(AlertType.INFORMATION, "No matching word was found.");
				alert.showAndWait();
			}

		});

		MenuBar menu = menuBar();

		List<MenuItem> fileList = menu.getMenus().get(0).getItems();

		// Open
		fileList.get(0).setOnAction(e -> {
			read(root, stage);

		});

		// Quit
		fileList.get(1).setOnAction(e -> {
			Alert alert = new Alert(AlertType.WARNING, "Are you sure you want to exit?", ButtonType.YES, ButtonType.NO);
			alert.showAndWait();
			if (alert.getResult().getButtonData().equals(ButtonBar.ButtonData.YES)) {
				System.exit(0);
			}
		});

		
		root.setTop(menu);
		b1.fire();
		stage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	private MenuBar menuBar() {

		MenuBar menuBar = new MenuBar();
		Menu fileMenu = new Menu("File");

		MenuItem[] fileItems = new MenuItem[2];
		
		fileItems[0] = new MenuItem("Open");
		fileItems[1] = new MenuItem("Quit");

		fileMenu.getItems().addAll(fileItems);
		

		menuBar.getMenus().add(fileMenu);
		return menuBar;
	}

	private void read(BorderPane root, Stage stage) throws NullPointerException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Välj fil att undersöka");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text files", "*.txt"));
		File readFile = fileChooser.showOpenDialog(stage);
		Scanner s = null;
		try {
			s = new Scanner(readFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(e.hashCode());
		}
		GeneralWordCounter w = new GeneralWordCounter(stopwords);
		s.useDelimiter("(\\s|,|\\.|:|;|!|\\?|'|\\\")+");

		while (s.hasNext()) {
			String word = s.next().toLowerCase();
			w.process(word);
		}

		wordList = FXCollections.observableArrayList(w.getWords());
		listView = new ListView<Map.Entry<String, Integer>>(wordList);

		root.setCenter(listView);

	}
}
