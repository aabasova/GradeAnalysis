package gui;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import application.App;
import db.DBManager;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;

public class MainViewController extends Controller implements Initializable {
	@FXML
	private Label labelECTS;
	@FXML
	private Label labelAverageGrade;
	@FXML
	private Label labelBestPossibleGrade;
	@FXML
	private TextField textfieldGoalGrade;
	@FXML
	private Label labelNeededAverageGrade;
	@FXML
	private Button buttonOverall;
	@FXML
	private Button buttonGrades;
	@FXML
	private Button buttonAnalysis;
	@FXML
	private ProgressIndicator progressBar;
	private DecimalFormat df = new DecimalFormat("####0.00");


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		labelECTS.setText(String.valueOf(db.getECTS()));
		labelAverageGrade.setText(String.valueOf(df.format(db.averageGrade())));
		labelBestPossibleGrade.setText(String.valueOf(df.format(db.getBestPosibleGrade())));
		setProgress();
	}

	public void setProgress() {
		Double progressValue = db.getECTS() * 1.0 / 180.0;
		progressBar.setStyle(" -fx-progress-color: #A3320B;");
		
		Timeline timeline = new Timeline();
		KeyValue kv = new KeyValue(progressBar.progressProperty(), progressValue);
		KeyFrame kf = new KeyFrame(new Duration(1500), kv);
		timeline.getKeyFrames().add(kf);
		timeline.setDelay(new Duration(1500));
		timeline.play();
		timeline.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				progressBar.setProgress(progressValue);
			}
		});
		
		progressBar.progressProperty().addListener((ov, oldValue, newValue) -> {
		     Text text = (Text) progressBar.lookup("%");
		     if(text!=null){
		        text.setText("New Text");
		     }
		});   
	}

	public void computeNeededAverage(ActionEvent event) {
		Double goalGrade = Double.parseDouble(textfieldGoalGrade.getText());
		String neededAverage = df.format(db.neededAverage(goalGrade));
		labelNeededAverageGrade.setText(neededAverage);
	}

	@FXML
	public void openOverall(ActionEvent event) {
		openOverall();
	}

	@FXML
	public void openGrades(ActionEvent event) {
		openGrades();
	}

	@FXML
	public void openAnalysis(ActionEvent event) {
		openAnalysis();
	}

	@FXML
	public void enteredMouse(MouseEvent event) {
		buttonOverall.setStyle("");
	}

}
