package nl.robinc.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.robinc.database.DatabaseManager;
import nl.robinc.model.Vereniging;

public class VerenigingDao {
	private DatabaseManager manager;
	
	public VerenigingDao() {
		manager = DatabaseManager.getInstance();
	}
	
	// Opvragen van de verenigingsnummers
	public List<Integer> getVerenigingNummers() {
		// Lijst voor de op te halen verenigingsnummers
		List<Integer> verenigingnummers = new ArrayList<>();
		
		// Connectie met de database
		Connection connection = manager.getConnection();
			
		// Probeert de verenigingsnummers uit de database te halen
		try {
			// Sql string voor het ophalen van het verenigingsnummers
			String query = "select verenigingsnummer from vereniging";	
			
			// Statement met het aanmelden van parameters
			PreparedStatement gebruikerQuery = connection.prepareStatement(query);	
			
			// Resultset met de resultaten van de query
			ResultSet gebruikerSet = gebruikerQuery.executeQuery();
				
			// De opgehaalde gegevens van de gebruiker
			while(gebruikerSet.next()) {
				int verenigingnummer = gebruikerSet.getInt("verenigingsnummer");
				verenigingnummers.add(verenigingnummer);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
			
		return verenigingnummers;
	}
		
	// Opvragen van een gebruiker
	public Vereniging getVereniging(int verenigingsNummer) {
		// De op te halen gebruiker
		Vereniging vereniging = null;
		
		// Connectie met de database
		Connection connection = manager.getConnection();
		
		// Probeert de gebruiker uit de database te halen
		try {
			// Sql string voor het ophalen van de gebruiker
			String query = "select * from vereniging "
					+ "where verenigingsnummer = ? ";
			
			// Statement met het aanmelden van parameters
			PreparedStatement verenigingQuery = connection.prepareStatement(query);
			verenigingQuery.setInt(1, verenigingsNummer);
			
			// Resultset met de resultaten van de query
			ResultSet verenigingSet = verenigingQuery.executeQuery();
			
			while(verenigingSet.next()) {
				// De opgehaalde gegevens van de gebruiker
				final int PRIMARYKEY = verenigingSet.getInt("verenigingsnummer");
				
				String verenigingsnaam = verenigingSet.getString("verenigingsnaam");
				
				// Samenstellen van de gegevens tot een gebruiker
				vereniging = new Vereniging(PRIMARYKEY, verenigingsnaam);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return vereniging;
	}
	
	public ObservableList<Vereniging> getVereniging() {
		// De op te halen gebruiker
		ObservableList<Vereniging> verenigingenLijst = FXCollections.observableArrayList(
				vereniging -> {
					return new Observable[] {
						vereniging.naamProperty()	
					};
				});
		
		// Connectie met de database
		Connection connection = manager.getConnection();
		
		// Probeert de gebruiker uit de database te halen
		try {
			// Sql string voor het ophalen van de gebruiker
			String query = "select * from vereniging ";
			
			// Statement met het aanmelden van parameters
			PreparedStatement verenigingQuery = connection.prepareStatement(query);
			
			// Resultset met de resultaten van de query
			ResultSet verenigingSet = verenigingQuery.executeQuery();
			
			while(verenigingSet.next()) {
				// De opgehaalde gegevens van de gebruiker
				final int PRIMARYKEY = verenigingSet.getInt("verenigingsnummer");
				
				String verenigingsnaam = verenigingSet.getString("verenigingsnaam");
				
				// Samenstellen van de gegevens tot een gebruiker
				verenigingenLijst.add(new Vereniging(PRIMARYKEY, verenigingsnaam));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return verenigingenLijst;
	}
	
	// Toevoegen van een gebruiker
	public void addVereniging(Vereniging vereniging) {
		// new or existing
		if(vereniging.getPRIMARYKEY() == 0) {
			this.addNewVereniging(vereniging);
		} else {
			this.addExistingVereniging(vereniging);
		}
	}
	
	private void addNewVereniging(Vereniging vereniging) {
		// insert into
		Connection connection = manager.getConnection();
		
		try {
			String sqlString = "insert into vereniging "
					+ "(verenigingsnaam) "
					+ "values"
					+ "(?)";
			
			PreparedStatement statement = connection.prepareStatement(sqlString);
			
			statement.setString(1, vereniging.getNaam());
			
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void addExistingVereniging(Vereniging vereniging) {
		// update
		String update = "update vereniging "
				+ "set verenigingsnaam = ?"
				+ "where verenigingsnummer = ?";
		
		Connection connection = manager.getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(update);
			
			statement.setString(1, vereniging.getNaam());
			statement.setInt(2, vereniging.getPRIMARYKEY());
			
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Verwijderen van een gebruiker
	public void removeVereniging(int key) {
		// delete where gebruikersnummer
		String delete = "delete from vereniging "
				+ "where verenigingsnummer = ?";
		
		Connection connection = manager.getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(delete);
			statement.setInt(1, key);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
