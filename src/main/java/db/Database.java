package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
	private Properties properties;
	private String dbFilename;
	protected Connection connection;
	public static final String sep = System.getProperty("file.separator");
	public static final String projectdir = System.getProperty("user.dir");
	public static final String resourcesPath = projectdir + sep + "src" + sep + "main" + sep + "resources" + sep;

	private void init() {
		properties = new Properties();
		properties.setProperty("PRAGMA foreign_keys", "ON");
	}

	public Database() {
		this.dbFilename = resourcesPath + "quiz.db";
		System.out.println("Database File: " + this.dbFilename);
		init();
		this.connection();
	}

	public void connection() {
		if (connection == null) {
			try {
				Class.forName("org.sqlite.JDBC");
				this.connection = DriverManager.getConnection("jdbc:sqlite:" + dbFilename);
				this.connection.setAutoCommit(false);
			} catch (Exception e) {
				System.err.println("Problem with database file " + dbFilename);
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
			}
			System.out.println("Opened database successfully");
		} else {
			System.out.println("connectio not null");
		}
	}

	public void disconnect() {
		try {
			this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
