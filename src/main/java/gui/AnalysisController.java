package gui;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import org.w3c.dom.events.EventTarget;
import org.w3c.dom.views.AbstractView;

import db.DBManager;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class AnalysisController extends Controller implements Initializable {
	@FXML
	private BarChart<String, Integer> barchart;
	@FXML
	private CategoryAxis barchartX;
	@FXML
	private NumberAxis barchartY;
	@FXML
	private Label examsLabel;
	@FXML
	private LineChart<String, Double> lineChart;
	@FXML
	private CategoryAxis lineChartX;
	@FXML
	private NumberAxis lineChartY;

	private DBManager db = new DBManager();
	private XYChart.Series<String, Integer> data;
	private XYChart.Series<String, Double> lineData;

	// Event Listener on BarChart[#barchart].onMouseEntered
	@FXML
	public void mouseBar(MouseEvent event) {

	}

	public void initialize(URL location, ResourceBundle resources) {
		barchartX.setLabel("Grade");
		barchartY.setLabel("Frequency");
		examsLabel.setText(String.valueOf(db.totalExams()));
		fillBarChart();
		fillLineChart();
	}

	public void fillBarChart() {
		data = new XYChart.Series<String, Integer>();
		data.setName("Grade distribution");
		Map<Double, Integer> map = db.getFrequencyOfGrades();
		for (Double grade : map.keySet()) {
			data.getData().add(new XYChart.Data<String, Integer>(String.valueOf(grade), map.get(grade)));
		}
		barchart.getData().add(data);
	}

	public void fillLineChart() {
		lineData = new XYChart.Series<String, Double>();
		lineData.setName("Average grade per semester");
		Map<Integer, Double> map = db.getAveragePerSemester();
		for (Integer semester : map.keySet()) {
			lineData.getData().add(new XYChart.Data<String, Double>(String.valueOf(semester), map.get(semester)));
		}
		lineChart.getData().add(lineData);

		for (XYChart.Series<String, Double> series : lineChart.getData()) {
			for (XYChart.Data<String, Double> item : series.getData()) {
				Tooltip tooltip = new Tooltip(item.getXValue() + ":\n" + item.getYValue());
				tooltip.setStyle("-fx-show-duration: 10s;");
				tooltip.setStyle("-fx-show-duration: 10s;");
				Tooltip.install(item.getNode(), tooltip);
				item.getNode().setOnMouseEntered(event -> item.getNode().getStyleClass().add("onHover"));
				item.getNode().setOnMouseExited(event -> item.getNode().getStyleClass().remove("onHover"));
			}
		}
	}

	// Event Listener on Menu[#menuOverall].onAction
	@FXML
	public void openOverall(ActionEvent event) {
		openOverall();
	}

	// Event Listener on Menu[#menuGrades].onAction
	@FXML
	public void openGrades(ActionEvent event) {
		openGrades();
	}

	// Event Listener on Menu[#menuAnalysis].onAction
	@FXML
	public void openAnalysis(ActionEvent event) {
		openAnalysis();
	}

}
