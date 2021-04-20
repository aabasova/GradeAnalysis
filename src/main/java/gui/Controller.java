package gui;

import java.io.IOException;

import application.App;
import db.DBManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class Controller {
	protected DBManager db = new DBManager();

	public void openView(String viewName) {
		try {
			db.disconnect();
			App.setRoot(viewName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void openOverall() {
		openView("MainView");
	}

	public void openGrades() {
		openView("Grades");
	}

	public void openAnalysis() {
		openView("Analysis");
	}
}
