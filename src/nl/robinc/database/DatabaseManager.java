package nl.robinc.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseManager {
    
	private Connection connection;
	
	private DatabaseManager() {
		try {
			Properties databaseProperties = new Properties();
			databaseProperties.load(new FileInputStream("/Users/Rob/SDK/Properties/beurs.properties"));
			
			String url = databaseProperties.getProperty("jdbc.url");
			String user = databaseProperties.getProperty("jdbc.username");
			String password = databaseProperties.getProperty("jdbc.password");
			String driver = databaseProperties.getProperty("jdbc.drivers");
			
			System.setProperty("jdbc.driver", driver);
			connection = DriverManager.getConnection(url, user, password);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    
    private static class DatabaseManagerHolder {
        private static final DatabaseManager INSTANCE = new DatabaseManager();
    }
	
    public static DatabaseManager getInstance() {
        return DatabaseManagerHolder.INSTANCE;
    }
    
    public Connection getConnection() {
    	return connection;
    }
}
