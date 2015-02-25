package nl.robinc.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Gebruiker {
	// Primary key
	private final int PRIMARYKEY;
	
	// Gebruikersnaam en wachtwoord van de gebruiker
	private StringProperty gebruikersnaam;
	private StringProperty wachtwoord;
	
	// Persoonlijke gegevens van de gebruiker
	private StringProperty naam;
	
	// Balans van de gebruiker
	private DoubleProperty balans;
	
	// Constructor voor het aanmaken van een gebruiker
	public Gebruiker(String gebruikersnaam, String wachtwoord, 
			String naam, double balans) {
		this.PRIMARYKEY = 0;
		this.gebruikersnaam = new SimpleStringProperty(gebruikersnaam);
		this.wachtwoord = new SimpleStringProperty(wachtwoord);
		this.naam = new SimpleStringProperty(naam);
		this.balans = new SimpleDoubleProperty(balans);
	}
	
	// Constructor voor het ophalen van een gebruiker
	public Gebruiker(int nummer, String gebruikersnaam, String wachtwoord, 
			String naam, double balans) {
		this.PRIMARYKEY = nummer;
		this.gebruikersnaam = new SimpleStringProperty(gebruikersnaam);
		this.wachtwoord = new SimpleStringProperty(wachtwoord);
		this.naam = new SimpleStringProperty(naam);
		this.balans = new SimpleDoubleProperty(balans);
	}

	// Propertys
	public StringProperty gebruikersnaamProperty() {
		return gebruikersnaam;
	}

	public StringProperty wachtwoordProperty() {
		return wachtwoord;
	}

	public StringProperty naamProperty() {
		return naam;
	}
	
	public DoubleProperty balansProperty() {
		return balans;
	}

	// Setters
	public void setGebruikersnaam(String gebruikersnaam) {
		this.gebruikersnaam.set(gebruikersnaam);
	}

	public void setWachtwoord(String wachtwoord) {
		this.wachtwoord.set(wachtwoord);
	}

	public void setNaam(String naam) {
		this.naam.set(naam);
	}

	public void setBalans(double balans) {
		this.balans.set(balans);
	}
	
	// Getters
	public int getPRIMARYKEY() {
		return PRIMARYKEY;
	}
	
	public String getGebruikersnaam() {
		return gebruikersnaam.get();
	}

	public String getWachtwoord() {
		return wachtwoord.get();
	}

	public String getNaam() {
		return naam.get();
	}
	
	public double getBalans() {
		return balans.get();
	}

	// Tostring
	@Override
	public String toString() {
		return "Gebruiker [PRIMARYKEY=" + PRIMARYKEY + ", gebruikersnaam="
				+ gebruikersnaam + ", wachtwoord=" + wachtwoord + ", naam="
				+ naam + ", balans=" + balans + "]";
	}
}
