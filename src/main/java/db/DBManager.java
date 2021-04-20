package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import data.Module;

public class DBManager extends Database {

	public DBManager() {
		createModule();
	}

	public void createModule() {
		try {
			Statement statement = connection.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS Module" + "(Module TEXT PRIMARY KEY," + "ECTS INTEGER,"
					+ "Grade Double," + "Semester INTEGER);";
			statement.executeUpdate(sql);
			connection.commit();
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addModule(Module module) {
		try {
			Statement stmt = connection.createStatement();
			String sql = "INSERT INTO Module (Module, ECTS, Grade, Semester) VALUES('" + module.getName() + "',"
					+ module.getECTS() + "," + module.getGrade() + "," + module.getSemester() + ");";
			stmt.executeUpdate(sql);
			connection.commit();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public TreeSet<Module> getModules(int semester) {
		TreeSet<Module> modules = new TreeSet<Module>();
		try {
			PreparedStatement stmt = connection
					.prepareStatement("SELECT * FROM Module WHERE Semester = " + semester + ";");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int ects = rs.getInt("ECTS");
				double grade = rs.getDouble("Grade");
				String name = rs.getString("Module");
				modules.add(new Module(ects, grade, name, semester));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return modules;
	}

	public ArrayList<Integer> getSemester() {
		ArrayList<Integer> semester = new ArrayList<Integer>();
		try {
			PreparedStatement stmtSemester = connection
					.prepareStatement("SELECT DISTINCT Semester FROM Module ORDER BY Semester ASC;");
			ResultSet rs = stmtSemester.executeQuery();
			while (rs.next()) {
				semester.add(rs.getInt(1));
			}
			rs.close();
			stmtSemester.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return semester;
	}

	public int getECTS() {
		String sql = "SELECT SUM(ECTS) FROM Module;";
		return getAggregateIntResult(sql);
	}

	public double averageGrade() {
		double grade = this.getGradeTimesECTS();
		int ectsTotal = this.getECTS();
		return grade / ectsTotal;
	}

	public double getBestPosibleGrade() {
		double restECTS = 180 - this.getECTS();
		double grade = this.getGradeTimesECTS();
		grade += restECTS;
		return grade / 180;
	}

	public double neededAverage(double goalGrade) {
		double currentGrade = this.averageGrade();
		double restECTS = 180 - this.getECTS();
		double result = 180 * goalGrade - this.getGradeTimesECTS();
		return result / restECTS;
	}

	public double getGradeTimesECTS() {
		double grade = 0;
		try {
			Statement statement = connection.createStatement();
			String sql = "SELECT Grade,ECTS FROM Module;";
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				double ects = rs.getInt(2);
				double gr = rs.getDouble(1);
				grade += ects * gr;
			}
			rs.close();
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return grade;
	}

	public Map<Double, Integer> getFrequencyOfGrades() {
		// grade frequency
		Map<Double, Integer> map = new TreeMap<Double, Integer>();
		double[] grades = new double[] { 1, 1.3, 1.7, 2, 2.3, 2.7, 3, 3.3, 3.7, 4 };
		for (double grade : grades) {
			String sql = "SELECT COUNT(*) FROM Module WHERE Grade = " + grade + ";";
			map.put(grade, getAggregateIntResult(sql));
		}

		return map;
	}

	public Map<Integer, Double> getAveragePerSemester() {
		// semester, average grade
		Map<Integer, Double> map = new TreeMap<Integer, Double>();
		for (int semester : getSemester()) {
			String sql = "SELECT AVG(Grade) FROM Module WHERE Semester = " + semester + ";";
			map.put(semester, getAggregateDoubleResult(sql));
		}
		return map;
	}

	public int totalExams() {
		String sql = "SELECT COUNT(*) FROM Module;";
		return getAggregateIntResult(sql);
	}

	public String getAverageGrade(int semester) {
		String sql = "SELECT AVG(Grade) FROM Module WHERE Semester = " + semester + ";";
		double grade = getAggregateDoubleResult(sql);
		DecimalFormat df = new DecimalFormat("####0.00");
		return df.format(grade);
	}

	public double getAggregateDoubleResult(String sql) {
		double number = 0.0;
		try {
			Statement statement = connection.createStatement();
			number = statement.executeQuery(sql).getDouble(1);
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return number;
	}

	public int getAggregateIntResult(String sql) {
		int number = 0;
		try {
			Statement statement = connection.createStatement();
			number = statement.executeQuery(sql).getInt(1);
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return number;
	}

}
