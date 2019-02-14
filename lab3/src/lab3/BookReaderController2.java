package lab3;

import java.io.File;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import textproc.GeneralWordCounter;
import textproc.WordCountComparator;

public class BookReaderController2 extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();

		root.setTop(menuBar());

		Scene scene = new Scene(root, 500, 500);
		
		primaryStage.setTitle("BookReader");
		primaryStage.setScene(scene);
		primaryStage.show();

		Scanner scan = new Scanner(new File("../lab1/undantagsord.txt"));

		Set<String> stopwords = new HashSet<String>();
		while (scan.hasNext()) {
			stopwords.add(scan.next());
		}

		scan.close();

		Scanner s = new Scanner(new File("../lab1/nilsholg.txt"));
		
		ObservableList<Map.Entry<String, Integer>> wordList = read(s,stopwords);
		ListView<Map.Entry<String, Integer>> listView = new ListView<Map.Entry<String, Integer>>(wordList);

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
		b2.setSelected(true);

		b1.setOnAction(event -> {
			wordList.sort(new WordCountComparator());
			b2.setSelected(false);
		});
		b2.setOnAction(event -> {
			wordList.sort(new AlphabeticComparator());
			b1.setSelected(false);
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
	}
	
	private ObservableList<Map.Entry<String, Integer>> read(Scanner s, Set<String> stopwords) {
		GeneralWordCounter w = new GeneralWordCounter(stopwords);

		s.useDelimiter("(\\s|,|\\.|:|;|!|\\?|'|\\\")+"); 

		while (s.hasNext()) {
			String word = s.next().toLowerCase();
			w.process(word);
		}
		
		s.close();
		
		ObservableList<Map.Entry<String, Integer>> wordList=FXCollections.observableArrayList(w.getWords());
		return wordList;
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	private MenuBar menuBar() {
		MenuBar menu = new MenuBar();
		Menu fileMenu = new Menu("File");
		MenuItem[] items = new MenuItem[2];

		items[0] = new MenuItem("Open");
		items[1] = new MenuItem("Quit");

		items[0].setOnAction(event -> {
			
		});
		items[1].setOnAction(event -> {
			Alert alert = new Alert(AlertType.WARNING, "Are you sure you want to exit?", ButtonType.YES, ButtonType.NO);
			alert.showAndWait();
			if (alert.getResult().getButtonData().equals(ButtonBar.ButtonData.YES)) {
				System.exit(0);
			}
		});

		fileMenu.getItems().addAll(items);
		menu.getMenus().add(fileMenu);
		return menu;
	}

	private class AlphabeticComparator implements Comparator<Entry<String, Integer>> {

		@Override
		public int compare(Entry<String, Integer> arg0, Entry<String, Integer> arg1) {
			return arg0.getKey().compareTo(arg1.getKey());
		}
	}

}
