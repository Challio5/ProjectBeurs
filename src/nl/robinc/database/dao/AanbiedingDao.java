package nl.robinc.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.robinc.database.DatabaseManager;
import nl.robinc.model.Aanbieding;
import nl.robinc.model.Gebruiker;
import nl.robinc.model.Vereniging;

public class AanbiedingDao {
	// Databasemanager voor het beheren van de connectie met de database
	private DatabaseManager manager;

	// Constructor voor het ophalen van de datatabasemanager instantie
	public AanbiedingDao() {
		manager = DatabaseManager.getInstance();
	}

	// Opvragen van een aanbieding
	public ObservableList<Aanbieding> getAanbieding(int gebruikersNummer) {
		// De op te halen aanbiedingen
		ObservableList<Aanbieding> aanbiedingenLijst = FXCollections
				.observableArrayList(aanbieding -> {
					Observable[] list = {
							aanbieding.gebruikerProperty(),
							aanbieding.verenigingProperty(),
							aanbieding.aantalProperty(), 
							aanbieding.prijsProperty()
					};
					return list;
				});

		// De verbinding met de database
		Connection connection = manager.getConnection();

		try {
			// Sql statement voor het ophalen van het aanbieding
			String query = "select * from aanbieding "  + "natural join gebruiker " + 
					"natural join vereniging " + "where gebruikersnummer = ?";

			// Statement die het aanbieding ophaald uit de database
			PreparedStatement aanbiedingStatement = connection
					.prepareStatement(query);
			aanbiedingStatement.setInt(1, gebruikersNummer);

			// Tuple uit de database
			ResultSet aanbiedingSet = aanbiedingStatement.executeQuery();

			while (aanbiedingSet.next()) {
				// Aanbieding
				final int PRIMARYKEY = aanbiedingSet.getInt("aanbiedingnummer");
				int aantal = aanbiedingSet.getInt("aantal");
				double prijs = aanbiedingSet.getDouble("prijs");
				
				// Gebruiker
				final int gebruikersnummer = aanbiedingSet.getInt("gebruikersnummer");
				String gebruikersnaam = aanbiedingSet.getString("gebruikersnaam");
				String wachtwoord = aanbiedingSet.getString("wachtwoord");
				String naam = aanbiedingSet.getString("naam");
				double balans = aanbiedingSet.getDouble("balans");
				
				Gebruiker gebruiker = new Gebruiker(gebruikersnummer, gebruikersnaam, 
						wachtwoord, naam, balans);
				
				// Vereniging
				int verenigingsnummer = aanbiedingSet.getInt("verenigingsnummer");
				String verenigingsnaam = aanbiedingSet.getString("verenigingsnaam");

				Vereniging vereniging = new Vereniging(verenigingsnummer,
						verenigingsnaam);
			
				aanbiedingenLijst.add(new Aanbieding(PRIMARYKEY, gebruiker, vereniging, aantal, prijs));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return aanbiedingenLijst;
	}
	
	// Haalt alle aanbiedingen per vereniging op
	public ObservableList<Aanbieding> getAanbieding(String verenigingsNaam) {
		// De op te halen aanbiedingen
		ObservableList<Aanbieding> aanbiedingenLijst = FXCollections
				.observableArrayList(aanbieding -> {
					Observable[] list = {
							aanbieding.gebruikerProperty(),
							aanbieding.verenigingProperty(),
							aanbieding.aantalProperty(), 
							aanbieding.prijsProperty()
					};
					return list;
				});


		// De verbinding met de database
		Connection connection = manager.getConnection();

		try {
			// Sql statement voor het ophalen van het aanbieding
			String query = "select * from aanbieding " + "natural join gebruiker "+ 
					"natural join vereniging " + "where verenigingsnaam = ?";

			// Statement die het aanbieding ophaald uit de database
			PreparedStatement aanbiedingStatement = connection
					.prepareStatement(query);
			
			aanbiedingStatement.setString(1, verenigingsNaam);

			// Tuple uit de database
			ResultSet aanbiedingSet = aanbiedingStatement.executeQuery();

			while (aanbiedingSet.next()) {
				// Aanbieding
				final int PRIMARYKEY = aanbiedingSet.getInt("aanbiedingnummer");
				int aantal = aanbiedingSet.getInt("aantal");
				double prijs = aanbiedingSet.getDouble("prijs");

				// Gebruiker
				final int gebruikersnummer = aanbiedingSet.getInt("gebruikersnummer");
				String gebruikersnaam = aanbiedingSet.getString("gebruikersnaam");
				String wachtwoord = aanbiedingSet.getString("wachtwoord");
				String naam = aanbiedingSet.getString("naam");
				double balans = aanbiedingSet.getDouble("balans");
				
				Gebruiker gebruiker = new Gebruiker(gebruikersnummer, gebruikersnaam, 
						wachtwoord, naam, balans);
				
				// Vereniging
				final int verenigingsnummer = aanbiedingSet.getInt("verenigingsnummer");
				String verenigingsnaam = aanbiedingSet.getString("verenigingsnaam");

				Vereniging vereniging = new Vereniging(verenigingsnummer,
						verenigingsnaam);
			
				aanbiedingenLijst.add(new Aanbieding(PRIMARYKEY, gebruiker, 
						vereniging, aantal, prijs));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return aanbiedingenLijst;
	}

	// Haalt alle aanbieding op
	public ObservableList<Aanbieding> getAanbieding() {
		// De op te halen aandelen
		ObservableList<Aanbieding> aanbiedingenLijst = FXCollections
				.observableArrayList(aanbieding -> {
					Observable[] list = {
							aanbieding.gebruikerProperty(),
							aanbieding.verenigingProperty(),
							aanbieding.aantalProperty(), 
							aanbieding.prijsProperty()
					};
					return list;
				});


		// De verbinding met de database
		Connection connection = manager.getConnection();

		try {
			// Sql statement voor het ophalen van het aanbieding
			String query = "select * from aanbieding " + "natural join gebruiker "+ 
					"natural join vereniging";

			// Statement die het aanbieding ophaald uit de database
			PreparedStatement aanbiedingStatement = connection
					.prepareStatement(query);

			// Tuple uit de database
			ResultSet aanbiedingSet = aanbiedingStatement.executeQuery();

			while (aanbiedingSet.next()) {
				// Aanbieding
				final int PRIMARYKEY = aanbiedingSet.getInt("aanbiedingnummer");
				int aantal = aanbiedingSet.getInt("aantal");
				double prijs = aanbiedingSet.getDouble("prijs");

				// Gebruiker
				final int gebruikersnummer = aanbiedingSet.getInt("gebruikersnummer");
				String gebruikersnaam = aanbiedingSet.getString("gebruikersnaam");
				String wachtwoord = aanbiedingSet.getString("wachtwoord");
				String naam = aanbiedingSet.getString("naam");
				double balans = aanbiedingSet.getDouble("balans");
				
				Gebruiker gebruiker = new Gebruiker(gebruikersnummer, gebruikersnaam, 
						wachtwoord, naam, balans);
				
				// Vereniging
				final int verenigingsnummer = aanbiedingSet.getInt("verenigingsnummer");
				String verenigingsnaam = aanbiedingSet.getString("verenigingsnaam");

				Vereniging vereniging = new Vereniging(verenigingsnummer,
						verenigingsnaam);
			
				aanbiedingenLijst.add(new Aanbieding(PRIMARYKEY, gebruiker, 
						vereniging, aantal, prijs));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return aanbiedingenLijst;
	}

	// Toevoegen van een nieuw of bestaand aanbieding
	public void addAanbieding(Aanbieding aanbieding) {
		if (aanbieding.getPRIMARYKEY() != 0) {
			this.addExistingAanbieding(aanbieding);
		} else {
			this.addNewAanbieding(aanbieding);
		}
	}

	// Toevoegen van een nieuw aanbieding
	private void addNewAanbieding(Aanbieding aanbieding) {
		// De verbinding met de database
		Connection connection = manager.getConnection();

		try {
			// Sql statement voor het wegschrijven van het aanbieding
			String insert = "insert into aanbieding "
					+ "(gebruikersnummer, verenigingsnummer, aantal, prijs) " 
					+ "values "
					+ "(?, ?, ?, ?)";

			// Statement die het aanbieding wegschrijft in de database
			PreparedStatement aanbiedingStatement = connection
					.prepareStatement(insert);

			aanbiedingStatement.setInt(1, aanbieding.getGebruiker().getPRIMARYKEY());
			aanbiedingStatement.setInt(2, aanbieding.getVereniging().getPRIMARYKEY());
			aanbiedingStatement.setInt(3, aanbieding.getAantal());
			aanbiedingStatement.setDouble(4, aanbieding.getPrijs());
			
			aanbiedingStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Updaten van een aanbieding
	private void addExistingAanbieding(Aanbieding aanbieding) {
		// De verbinding met de database
		Connection connection = manager.getConnection();

		try {
			// Sql statement voor het updaten van het aanbieding
			String insert = "update aanbieding "
					+ "set "
					+ "gebruikersnummer = ?, verenigingsnummer = ?, aantal = ?, prijs = ? "
					+ "where aanbiedingnummer = ?";

			PreparedStatement aanbiedingStatement = connection
					.prepareStatement(insert);

			aanbiedingStatement.setInt(1, aanbieding.getGebruiker().getPRIMARYKEY());
			aanbiedingStatement.setInt(2, aanbieding.getVereniging().getPRIMARYKEY());
			aanbiedingStatement.setInt(3, aanbieding.getAantal());
			aanbiedingStatement.setDouble(4, aanbieding.getPrijs());
			
			aanbiedingStatement.setInt(5, aanbieding.getPRIMARYKEY());

			aanbiedingStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Verwijderen van een gebruiker
	public void removeAanbieding(int key) {
		// De verbinding met de database
		Connection connection = manager.getConnection();

		try {
			// Sql statement voor het verwijderen van het aanbieding
			String insert = "delete from aanbieding " + "where aanbiedingnummer = ?";

			PreparedStatement aanbiedingStatement = connection
					.prepareStatement(insert);
			
			aanbiedingStatement.setInt(1, key);
			aanbiedingStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
