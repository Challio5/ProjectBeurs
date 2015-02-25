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
import nl.robinc.model.Gebruiker;


public class GebruikerDao {
	// De databasemanager voor het beheer van de database
	private DatabaseManager manager;
	
	// Constructor voor het ophalen van de databasemananger instantie
	public GebruikerDao() {
		manager = DatabaseManager.getInstance();
	}
	
	// Opvragen van de verenigingsnummers
	public List<Integer> getGebruikerNummers() {
		// Lijst voor de op te halen verenigingsnummers
		List<Integer> gebruikernummers = new ArrayList<>();
		
		// Connectie met de database
		Connection connection = manager.getConnection();
			
		// Probeert de verenigingsnummers uit de database te halen
		try {
			// Sql string voor het ophalen van het verenigingsnummers
			String query = "select gebruikersnummer from gebruiker";	
			
			// Statement met het aanmelden van parameters
			PreparedStatement gebruikerQuery = connection.prepareStatement(query);	
			
			// Resultset met de resultaten van de query
			ResultSet gebruikerSet = gebruikerQuery.executeQuery();
				
			// De opgehaalde gegevens van de gebruiker
			while(gebruikerSet.next()) {
				int gebruikernummer = gebruikerSet.getInt("gebruikersnummer");
				gebruikernummers.add(gebruikernummer);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
			
		return gebruikernummers;
	}
	
	// LoginCheck
	public Gebruiker loginCheck(String username, String password) {
		// De op te halen gebruiker
		Gebruiker gebruiker = null;
		
		// Connectie met de database
		Connection connection = manager.getConnection();
		
		// Probeert de gebruiker uit de database te halen
		try {
			// Sql string voor het ophalen van de gebruiker
			String query = "select * from gebruiker "
					+ "where gebruikersnaam = ? and wachtwoord = ?";
			
			// Statement met het aanmelden van parameters
			PreparedStatement gebruikerQuery = connection.prepareStatement(query);
			gebruikerQuery.setString(1, username);
			gebruikerQuery.setString(2, password);
			
			// Resultset met de resultaten van de query
			ResultSet gebruikerSet = gebruikerQuery.executeQuery();
			while(gebruikerSet.next()) {
				// De opgehaalde gegevens van de gebruiker
				final int PRIMARYKEY = gebruikerSet.getInt("gebruikersnummer");
				
				String gebruikersnaam = gebruikerSet.getString("gebruikersnaam");
				String wachtwoord = gebruikerSet.getString("wachtwoord");
				String naam = gebruikerSet.getString("naam");
				double balans = gebruikerSet.getDouble("balans");
				
				// Samenstellen van de gegevens tot een gebruiker
				gebruiker = new Gebruiker(PRIMARYKEY, gebruikersnaam, wachtwoord, naam, balans);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return gebruiker;
	}
		
	// Opvragen van een gebruiker
	public Gebruiker getGebruiker(int gebruikersnummer) {
		// De op te halen gebruiker
		Gebruiker gebruiker = null;
		
		// Connectie met de database
		Connection connection = manager.getConnection();
		
		// Probeert de gebruiker uit de database te halen
		try {
			// Sql string voor het ophalen van de gebruiker
			String query = "select * from gebruiker "
					+ "where gebruikersnummer = ? ";
			
			// Statement met het aanmelden van parameters
			PreparedStatement gebruikerQuery = connection.prepareStatement(query);
			gebruikerQuery.setInt(1, gebruikersnummer);
			
			// Resultset met de resultaten van de query
			ResultSet gebruikerSet = gebruikerQuery.executeQuery();
			gebruikerSet.next();
			
			// De opgehaalde gegevens van de gebruiker
			final int PRIMARYKEY = gebruikerSet.getInt("gebruikersnummer");
			String gebruikersnaam = gebruikerSet.getString("gebruikersnaam");
			String wachtwoord = gebruikerSet.getString("wachtwoord");
			String naam = gebruikerSet.getString("naam");
			double balans = gebruikerSet.getDouble("balans");
			
			// Samenstellen van de gegevens tot een gebruiker
			gebruiker = new Gebruiker(PRIMARYKEY, gebruikersnaam, wachtwoord, naam, balans);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return gebruiker;
	}
	
	// Opvragen van alle gebruikers
	public ObservableList<Gebruiker> getGebruiker() {
		// Lijst voor de op te halen gebruikers
		ObservableList<Gebruiker> gebruikersLijst = FXCollections.observableArrayList(
				gebruiker -> {
					return new Observable[] {
						gebruiker.gebruikersnaamProperty(),
						gebruiker.wachtwoordProperty(),
						gebruiker.naamProperty(),
						gebruiker.balansProperty()
					};
				});
		
		// Connectie met de database
		Connection connection = manager.getConnection();
		
		try {
			// Sql string voor het ophalen van alle gebruikers
			String query = "select * from gebruiker";
			
			// Het uit te voeren statement
			PreparedStatement gebruikerQuery = connection.prepareStatement(query);
			
			// Resultset met de tuple uit de database
			ResultSet gebruikersSet = gebruikerQuery.executeQuery();
			
			while(gebruikersSet.next()) {
				// De opgehaalde gegevens van de gebruiker
				final int PRIMARYKEY = gebruikersSet.getInt("gebruikersnummer");
				
				String gebruikersnaam = gebruikersSet.getString("gebruikersnaam");
				String wachtwoord = gebruikersSet.getString("wachtwoord");
				String naam = gebruikersSet.getString("naam");
				double balans = gebruikersSet.getDouble("balans");
				
				// Samenstellen en toevoegen van de gegevens tot een gebruiker
				gebruikersLijst.add(new Gebruiker(PRIMARYKEY, gebruikersnaam, wachtwoord, naam, balans));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return gebruikersLijst;
	}
	
	// Toevoegen van een gebruiker
	public void addGebruiker(Gebruiker gebruiker) {
		// new or existing
		if(gebruiker.getPRIMARYKEY() == 0) {
			this.addNewGebruiker(gebruiker);	
		}
		else {
			this.addExistingGebruiker(gebruiker);
		}
	}
	
	private void addNewGebruiker(Gebruiker gebruiker) {
		// insert into
		String insert = "insert into gebruiker "
				+ "(gebruikersnaam, wachtwoord, naam, balans) "
				+ "values "
				+ "(?, ?, ?, ?)";
		
		Connection connection = manager.getConnection();
		try {
			PreparedStatement insertStatement = connection.prepareStatement(insert);
			
			insertStatement.setString(1, gebruiker.getGebruikersnaam());
			insertStatement.setString(2, gebruiker.getWachtwoord());
			insertStatement.setString(3, gebruiker.getNaam());
			insertStatement.setDouble(4, gebruiker.getBalans());
			
			insertStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void addExistingGebruiker(Gebruiker gebruiker) {
		// update
		String update = "update gebruiker "
				+ "set gebruikersnaam = ?, wachtwoord = ?, naam = ?, balans = ? "
				+ "where gebruikersnummer = ?";
		
		Connection connection = manager.getConnection();
		try {
			PreparedStatement updateStatement = connection.prepareStatement(update);
			
			updateStatement.setString(1, gebruiker.getGebruikersnaam());
			updateStatement.setString(2, gebruiker.getWachtwoord());
			updateStatement.setString(3, gebruiker.getNaam());
			updateStatement.setDouble(4, gebruiker.getBalans());
			updateStatement.setInt(5, gebruiker.getPRIMARYKEY());
			
			updateStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	// Verwijderen van een gebruiker
	public void removeGebruiker(int key) {
		// delete where gebruikersnummer
		String delete = "delete from gebruiker "
				+ "where gebruikersnummer = ?";
		
		Connection connection = manager.getConnection();
		
		try {
			PreparedStatement deleteStatement = connection.prepareStatement(delete);
			deleteStatement.setInt(1, key);
			
			deleteStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
