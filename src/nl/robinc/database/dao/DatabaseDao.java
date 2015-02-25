package nl.robinc.database.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import nl.robinc.database.DatabaseManager;

public class DatabaseDao {

	private DatabaseManager manager;
	
	public DatabaseDao() {
		manager = DatabaseManager.getInstance();
	}
	
	public void deleteData() {
		Connection connection = manager.getConnection();
		try {
			Statement statement = connection.createStatement();
			
			String deleteAanbieding = "delete from aanbieding";
			String deleteAandelen = "delete from aandeel";
			String deleteVereniging = "delete from vereniging";
			String deleteGebruikers = "delete from gebruiker";
			
			statement.addBatch(deleteAanbieding);
			statement.addBatch(deleteAandelen);
			statement.addBatch(deleteVereniging);
			statement.addBatch(deleteGebruikers);
			
			statement.executeBatch();
			
			String resetAandelen = "ALTER TABLE aandeel AUTO_INCREMENT = 1";
			String resetGebruikers = "ALTER TABLE gebruiker AUTO_INCREMENT = 1";
			String resetVereniging = "ALTER TABLE vereniging AUTO_INCREMENT = 1";
			String resetAanbieding = "ALTER TABLE aanbieding AUTO_INCREMENT = 1";
			
			statement.addBatch(resetAandelen);
			statement.addBatch(resetGebruikers);
			statement.addBatch(resetVereniging);
			statement.addBatch(resetAanbieding);
			
			statement.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
