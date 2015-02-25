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
import nl.robinc.model.Aandeel;
import nl.robinc.model.Gebruiker;
import nl.robinc.model.Vereniging;

public class AandeelDao {
	// Databasemanager voor het beheren van de connectie met de database
	private DatabaseManager manager;
	
	// Constructor voor het ophalen van de datatabasemanager instantie
	public AandeelDao() {
		manager = DatabaseManager.getInstance();
	}
	
	// Opvragen van de aandeelnummers
	public List<Integer> getGebruikerNummers() {
		// Lijst voor de op te halen aandeelnummers
		List<Integer> aandeelnummers = new ArrayList<>();
		
		// Connectie met de database
		Connection connection = manager.getConnection();
		
		// Probeert de aandeelnummers uit de database te halen
		try {
			// Sql string voor het ophalen van de aandeelnummers
			String query = "select aandeelnummer from aandeel";
			
			// Statement met het aanmelden van parameters
			PreparedStatement aandeelQuery = connection.prepareStatement(query);
			
			// Resultset met de resultaten van de query
			ResultSet aandeelSet = aandeelQuery.executeQuery();
			
			// De opgehaalde gegevens van de gebruiker
			while(aandeelSet.next()) {
				int gebruikernummer = aandeelSet.getInt("aandeelnummer");
				aandeelnummers.add(gebruikernummer);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return aandeelnummers;
	}
	
	// Opvragen van een aandeel
	public Aandeel getAandeel(int nummer) {
		// Het op te halen aandeel
		Aandeel aandeel = null;
		
		// De verbinding met de database
		Connection connection = manager.getConnection();
		
		try {
			// Sql statement voor het ophalen van het aandeel
			String query = "select * from aandeel "
					+ "natural join gebruiker "
					+ "natural join vereniging "
					+ "where aandeelnummer = ?";
			
			// Statement die het aandeel ophaald uit de database
			PreparedStatement aandeelStatement = connection.prepareStatement(query);
			aandeelStatement.setInt(1, nummer);
			
			// Tuple uit de database
			ResultSet aandeelSet = aandeelStatement.executeQuery();
			
			while(aandeelSet.next()) {
				final int PRIMARYKEY = aandeelSet.getInt("aandeelnummer");
				int aantal = aandeelSet.getInt("aantal");
				
				// Gebruiker
				int gebruikersnummer = aandeelSet.getInt("gebruikersnummer");
				String gebruikersnaam = aandeelSet.getString("gebruikersnaam");
				String wachtwoord = aandeelSet.getString("wachtwoord");
				String naam = aandeelSet.getString("naam");
				double balans = aandeelSet.getDouble("balans");
				
				Gebruiker gebruiker = new Gebruiker(gebruikersnummer, gebruikersnaam, 
						wachtwoord, naam, balans);
				
				// Vereniging
				int verenigingsnummer = aandeelSet.getInt("verenigingsnummer");
				String verenigingsnaam = aandeelSet.getString("verenigingsnaam");
				
				Vereniging vereniging = new Vereniging(verenigingsnummer, verenigingsnaam);
				
				aandeel = new Aandeel(PRIMARYKEY, gebruiker, vereniging, aantal);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return aandeel;
	}
	
	public ObservableList<Aandeel> getAandeel() {
		// De op te halen aandelen
		ObservableList<Aandeel> aandelenLijst = FXCollections.observableArrayList(
				aandeel -> {
					return new Observable[] {
							aandeel.gebruikerProperty(),
							aandeel.verenigingProperty(),
							aandeel.aantalProperty()
					};
				});
		
		// De verbinding met de database
		Connection connection = manager.getConnection();
		
		try {
			// Sql statement voor het ophalen van het aandeel
			String query = "select * from aandeel "
					+ "natural join gebruiker "
					+ "natural join vereniging ";
			
			// Statement die het aandeel ophaald uit de database
			PreparedStatement aandeelStatement = connection.prepareStatement(query);
			
			// Tuple uit de database
			ResultSet aandeelSet = aandeelStatement.executeQuery();
			
			while(aandeelSet.next()) {
				int PRIMARYKEY = aandeelSet.getInt("aandeelnummer");
				int aantal = aandeelSet.getInt("aantal");
				
				// Gebruiker
				int gebruikersnummer = aandeelSet.getInt("gebruikersnummer");
				String gebruikersnaam = aandeelSet.getString("gebruikersnaam");
				String wachtwoord = aandeelSet.getString("wachtwoord");
				String naam = aandeelSet.getString("naam");
				double balans = aandeelSet.getDouble("balans");
				
				Gebruiker gebruiker = new Gebruiker(gebruikersnummer, gebruikersnaam, wachtwoord,
						naam, balans);
				
				// Vereniging
				int verenigingsnummer = aandeelSet.getInt("verenigingsnummer");
				String verenigingsnaam = aandeelSet.getString("verenigingsnaam");

				Vereniging vereniging = new Vereniging(verenigingsnummer, verenigingsnaam);
				
				aandelenLijst.add(new Aandeel(PRIMARYKEY, gebruiker, vereniging, aantal));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return aandelenLijst;
	}
	
	public ObservableList<Aandeel> getAandelenVanGebruiker(int gebruikersnummer) {
		// De op te halen aandelen
		ObservableList<Aandeel> aandelenLijst = FXCollections.observableArrayList(
				aandeel -> {
					return new Observable[] {
							aandeel.gebruikerProperty(),
							aandeel.verenigingProperty(),
							aandeel.aantalProperty()
					};
				});
		
		// De verbinding met de database
		Connection connection = manager.getConnection();
		
		try {
			// Sql statement voor het ophalen van het aandeel
			String query = "select * from aandeel "
					+ "natural join gebruiker "
					+ "natural join vereniging "
					+ "where gebruikersnummer = ? ";
			
			// Statement die het aandeel ophaald uit de database
			PreparedStatement aandeelStatement = connection.prepareStatement(query);
			aandeelStatement.setInt(1, gebruikersnummer);
			
			// Tuple uit de database
			ResultSet aandeelSet = aandeelStatement.executeQuery();
			
			while(aandeelSet.next()) {
				int PRIMARYKEY = aandeelSet.getInt("aandeelnummer");
				int aantal = aandeelSet.getInt("aantal");
				
				// Gebruiker
				String gebruikersnaam = aandeelSet.getString("gebruikersnaam");
				String wachtwoord = aandeelSet.getString("wachtwoord");
				String naam = aandeelSet.getString("naam");
				double balans = aandeelSet.getDouble("balans");
				
				Gebruiker gebruiker = new Gebruiker(gebruikersnummer, gebruikersnaam, wachtwoord,
						naam, balans);
				
				// Vereniging
				int verenigingsnummer = aandeelSet.getInt("verenigingsnummer");
				String verenigingsnaam = aandeelSet.getString("verenigingsnaam");
				
				Vereniging vereniging = new Vereniging(verenigingsnummer, verenigingsnaam);
				
				aandelenLijst.add(new Aandeel(PRIMARYKEY, gebruiker, vereniging, aantal));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return aandelenLijst;
	}
	
	public ObservableList<Aandeel> getAandelenVanVereniging(int verenigingsnummer) {
		// De op te halen aandelen
		ObservableList<Aandeel> aandelenLijst = FXCollections.observableArrayList(
				aandeel -> {
					return new Observable[] {
							aandeel.gebruikerProperty(),
							aandeel.verenigingProperty(),
							aandeel.aantalProperty()
					};
				});
		
		// De verbinding met de database
		Connection connection = manager.getConnection();
		
		try {
			// Sql statement voor het ophalen van het aandeel
			String query = "select * from aandeel "
					+ "natural join gebruiker "
					+ "natural join vereniging "
					+ "where verenigingsnummer = ? ";
			
			// Statement die het aandeel ophaald uit de database
			PreparedStatement aandeelStatement = connection.prepareStatement(query);
			aandeelStatement.setInt(1, verenigingsnummer);
			
			// Tuple uit de database
			ResultSet aandeelSet = aandeelStatement.executeQuery();
			
			while(aandeelSet.next()) {
				int PRIMARYKEY = aandeelSet.getInt("aandeelnummer");
				int aantal = aandeelSet.getInt("aantal");
				
				// Gebruiker
				int gebruikersnummer = aandeelSet.getInt("gebruikersnummer");
				String gebruikersnaam = aandeelSet.getString("gebruikersnaam");
				String wachtwoord = aandeelSet.getString("wachtwoord");
				String naam = aandeelSet.getString("naam");
				double balans = aandeelSet.getDouble("balans");
				
				Gebruiker gebruiker = new Gebruiker(gebruikersnummer, gebruikersnaam, wachtwoord,
						naam, balans);
				
				// Vereniging
				String vernigingsnaam = aandeelSet.getString("verenigingsnaam");

				Vereniging vereniging = new Vereniging(verenigingsnummer, vernigingsnaam);
				
				aandelenLijst.add(new Aandeel(PRIMARYKEY, gebruiker, vereniging, aantal));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return aandelenLijst;
	}
	
	public ObservableList<Aandeel> getAandelenVanVerenigingEnGebruiker(
			int verenigingsnummer, int gebruikersnummer) {
		
		// De op te halen aandelen
		ObservableList<Aandeel> aandelenLijst = FXCollections.observableArrayList(
				aandeel -> {
					return new Observable[] {
							aandeel.gebruikerProperty(),
							aandeel.verenigingProperty(),
							aandeel.aantalProperty()
					};
				});
		
		// De verbinding met de database
		Connection connection = manager.getConnection();
		
		try {
			// Sql statement voor het ophalen van het aandeel
			String query = "select * from aandeel "
					+ "natural join gebruiker "
					+ "natural join vereniging "
					+ "where gebruikersnummer = ? and verenigingsnummer = ? ";
			
			// Statement die het aandeel ophaald uit de database
			PreparedStatement aandeelStatement = connection.prepareStatement(query);
			aandeelStatement.setInt(1, gebruikersnummer);
			aandeelStatement.setInt(2, verenigingsnummer);
			
			// Tuple uit de database
			ResultSet aandeelSet = aandeelStatement.executeQuery();
			
			while(aandeelSet.next()) {
				int PRIMARYKEY = aandeelSet.getInt("aandeelnummer");
				int aantal = aandeelSet.getInt("aantal");
				
				// Gebruiker
				String gebruikersnaam = aandeelSet.getString("gebruikersnaam");
				String wachtwoord = aandeelSet.getString("wachtwoord");
				String naam = aandeelSet.getString("naam");
				double balans = aandeelSet.getDouble("balans");
				
				Gebruiker gebruiker = new Gebruiker(gebruikersnummer, gebruikersnaam, wachtwoord,
						naam, balans);
				
				// Vereniging
				String vernigingsnaam = aandeelSet.getString("verenigingsnaam");

				Vereniging vereniging = new Vereniging(verenigingsnummer, vernigingsnaam);
				
				aandelenLijst.add(new Aandeel(PRIMARYKEY, gebruiker, vereniging, aantal));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return aandelenLijst;
	}
	
	// Toevoegen van een nieuw of bestaand aandeel
	public void addAandeel(Aandeel aandeel) {
		if(aandeel.getPRIMARYKEY() != 0) {
			this.addExistingAandeel(aandeel);
		} else {
			this.addNewAandeel(aandeel);
		}
	}
	
	// Toevoegen van een nieuw aandeel
	private void addNewAandeel(Aandeel aandeel) {
		// De verbinding met de database
		Connection connection = manager.getConnection();
		
		try {
			// Sql statement voor het wegschrijven van het aandeel
			String insert = "insert into aandeel "
					+ "(gebruikersnummer, verenigingsnummer, aantal) "
					+ "values "
					+ "(?, ?, ?)";
			
			// Statement die het aandeel wegschrijft in de database
			PreparedStatement aandeelStatement = connection.prepareStatement(insert);
			
			aandeelStatement.setInt(1, aandeel.getGebruiker().getPRIMARYKEY());
			aandeelStatement.setInt(2, aandeel.getVereniging().getPRIMARYKEY());
			aandeelStatement.setInt(3, aandeel.getAantal());
			aandeelStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Updaten van een aandeel
	private void addExistingAandeel(Aandeel aandeel) {
		// De verbinding met de database
		Connection connection = manager.getConnection();
	
		try {
			// Sql statement voor het updaten van het aandeel
			String insert = "update aandeel "
					+ "set "
					+ "gebruikersnummer = ?, verenigingsnummer = ?, aantal = ? "
					+ "where aandeelnummer = ?";
			
			PreparedStatement aandeelStatement = connection.prepareStatement(insert);
			
			aandeelStatement.setInt(1, aandeel.getGebruiker().getPRIMARYKEY());
			aandeelStatement.setInt(2, aandeel.getVereniging().getPRIMARYKEY());
			aandeelStatement.setInt(3, aandeel.getAantal());
			aandeelStatement.setInt(4, aandeel.getPRIMARYKEY());
			
			aandeelStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Verwijderen van een gebruiker
	public void removeAandeel(int key) {
		// De verbinding met de database
		Connection connection = manager.getConnection();
	
		try {
			// Sql statement voor het verwijderen van het aandeel
			String insert = "delete from aandeel "
					+ "where aandeelnummer = ?";
			
			PreparedStatement aandeelStatement = connection.prepareStatement(insert);
			aandeelStatement.setInt(1, key);
			aandeelStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
