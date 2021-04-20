package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.App;
import data.Module;
import db.DBManager;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.ScrollPane;

public class GradesController extends Controller implements Initializable {
	@FXML
	private Menu menuOverall;
	@FXML
	private Menu menuGrades;
	@FXML
	private Menu menuAnalysis;
	@FXML
	private AnchorPane anchorPaneModules;
	@FXML
	private TextField textfieldModuleName;
	@FXML
	private TextField textfieldECTS;
	@FXML
	private ComboBox<Double> combobocGrade;
	@FXML
	private ComboBox<Integer> comboboxSemeser;
	@FXML
	private Button buttonAddModule;
	@FXML
	private ScrollPane scrollpane;
	@FXML
	private VBox vbox;

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
	

	// Event Listener on Button[#buttonAddModule].onAction
	@FXML
	public void addModule(ActionEvent event) {
		if (!textfieldECTS.getText().isEmpty() && !textfieldModuleName.getText().isEmpty()) {
			String name = textfieldModuleName.getText();
			int semester = (int) comboboxSemeser.getValue();
			double grade = combobocGrade.getValue();
			int ects = Integer.parseInt(textfieldECTS.getText());
			Module module = new Module(ects, grade, name, semester);
			
			db.addModule(module);
			updateUI();
		}
	}

	public void updateTables() {
		vbox.getChildren().removeAll(vbox.getChildren());
		gui.TableView tableView;
		for (int semester : db.getSemester()) {
			tableView = new gui.TableView(semester, db.getAverageGrade(semester));
			if (!db.getModules(semester).isEmpty()) {
				int lines = db.getModules(semester).size();
				tableView.addItem(db.getModules(semester));
		
				tableView.setMinHeight((tableView.getItems().size()+2) * tableView.getFixedCellSize());
				tableView.setPrefHeight((tableView.getItems().size()+2) * tableView.getFixedCellSize());
				
				vbox.getChildren().add(tableView);
				
			}
		}
	}

	public void clearTextFields() {
		textfieldModuleName.setText("");
		textfieldECTS.setText("");
	}
	
	public void updateUI() {
		clearTextFields();
		updateTables();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		for (int i = 1; i < 11; i++) {
			comboboxSemeser.getItems().add(i);
		}
		double[] grades = new double[] { 1, 1.3, 1.7, 2, 2.3, 2.7, 3, 3.3, 3.7, 4 };
		for (double gr : grades) {
			combobocGrade.getItems().add(gr);
		}
		combobocGrade.setValue(1.0);
		comboboxSemeser.setValue(1);

		updateTables();
	}
}
