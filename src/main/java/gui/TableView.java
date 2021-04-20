package gui;

import java.util.TreeSet;

import data.Module;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;

public class TableView extends javafx.scene.control.TableView<ModuleData> {
	protected TableColumn<ModuleData, String> info;
	protected TableColumn<ModuleData, String> moduleColumn;
	protected TableColumn<ModuleData, Double> gradeColumn;
	protected TableColumn<ModuleData, Integer> ectsColumns;
	ObservableList<ModuleData> list = FXCollections.observableArrayList();

	public TableView(int semester, String averageGrade) {
		createColumns(semester, averageGrade);
		addColumns();
		this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		this.setFixedCellSize(25);
		this.setStyle("-fx-font-size:13");
	}

	public void createColumns(int semester, String averageGrade) {
		info = new TableColumn<ModuleData, String>(semester + ". Semester : " + averageGrade);
	
		moduleColumn = new TableColumn<ModuleData, String>("Module");
		moduleColumn.setCellValueFactory(new PropertyValueFactory<ModuleData, String>("Module"));
		gradeColumn = new TableColumn<ModuleData, Double>("Grade");
		gradeColumn.setCellValueFactory(new PropertyValueFactory<>("Grade"));
		ectsColumns = new TableColumn<ModuleData, Integer>("ECTS");
		ectsColumns.setCellValueFactory(new PropertyValueFactory<>("ECTS"));
	}

	public void addColumns() {
		info.getColumns().addAll(moduleColumn, gradeColumn, ectsColumns);
		this.getColumns().add(info);
	}

	public void addItem(TreeSet<Module> modules) {
		for (Module module : modules) {
			int ects = module.getECTS();
			double grade = module.getGrade();
			String name = module.getName();
			ModuleData md = new ModuleData(ects, grade, name);
			list.add(md);
		}
		this.setItems(list);
	}
	

}
