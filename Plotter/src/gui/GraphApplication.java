package gui;

import java.util.List;

import ast.Function;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import syntax.Parser;
import syntax.Parser.ParseException;
import syntax.TokenScanner;
import syntax.TokenScanner.ScannerException;

public class GraphApplication extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 600, 400);

		stage.setTitle("Grapher");
		stage.setResizable(false);
		stage.setScene(scene);

		HBox top = new HBox();
		top.setAlignment(Pos.CENTER);

		TextField function = new TextField();
		function.setMinWidth(290);
		function.setMaxWidth(290);
		function.setPromptText("Function");

		TextField lowerBound = new TextField();
		lowerBound.setMinWidth(100);
		lowerBound.setMaxWidth(100);
		lowerBound.setPromptText("Lower bound");

		TextField upperBound = new TextField();
		upperBound.setMinWidth(100);
		upperBound.setMaxWidth(100);
		upperBound.setPromptText("Upper bound");

		Button plotButton = new Button("Plot!");
		plotButton.setMinWidth(60);
		plotButton.setMaxWidth(60);
		plotButton.setDefaultButton(true);
		
		Button reset=new Button("Reset");
		reset.setMinWidth(60);
		reset.setMaxWidth(60);

		top.getChildren().addAll(function, lowerBound, upperBound, plotButton, reset);
		root.setTop(top);

		NumberAxis xAxis = new NumberAxis();
		NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("x");
		yAxis.setLabel("f(x)");

		LineChart<Number, Number> plot = new LineChart<Number, Number>(xAxis, yAxis);
		plot.setTitle("plot");
		root.setCenter(plot);

		plotButton.setOnAction(e -> plot(function, lowerBound, upperBound, plot));
		reset.setOnAction(e->reset(plot));

		stage.show();
	}

	@SuppressWarnings("unchecked")
	private void plot(TextField f, TextField l, TextField u, LineChart<Number, Number> c) {
		
		c.setCreateSymbols(false);
		
		String function = f.getText();
		double lower = Double.parseDouble(l.getText());
		double upper = Double.parseDouble(u.getText());

		int points = 1000;

		TokenScanner tc = new TokenScanner();

		List<String> tokens = null;
		Function func = null;

		Series<Number, Number> series = new XYChart.Series<Number, Number>();
		

		try {
			tokens = tc.scan(function);
		} catch (ScannerException e) {
			e.printStackTrace();
			return;
		}

		Parser p = new Parser();

		try {
			func = p.parse(tokens);
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}

		series.setName("f(x)="+func.toString());
		
		double[] xValues = new double[points];
		double[] yValues = new double[points];
		double step = (upper - lower) / points;

		for (int i = 0; i < xValues.length; i++) {
			xValues[i] = lower + step * i;
			yValues[i] = func.value(xValues[i]);
			
			series.getData().add(new XYChart.Data<Number, Number>(xValues[i],yValues[i]));
		}
		
		c.getData().addAll(series);
	}
	
	private void reset(LineChart<Number, Number> plot) {
		plot.getData().clear();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}
