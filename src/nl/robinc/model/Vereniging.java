package nl.robinc.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Vereniging {
	
	// Primary key 
	private final int PRIMARYKEY;
	
	// De naam van de vereniging
	private StringProperty naam;

	// Constructor voor het wegschrijven van een vereniging
	public Vereniging(String naam) {
		this.PRIMARYKEY = 0;
		
		this.naam = new SimpleStringProperty(naam);
	}
	
	// Constructor voor het ophalen van een vereniging
	public Vereniging(int nummer, String naam) {
		this.PRIMARYKEY = nummer;
		
		this.naam = new SimpleStringProperty(naam);
	}

	// Propertys	
	public StringProperty naamProperty() {
		return naam;
	}
	
	// Setters
	public void setNaam(String naam) {
		this.naam.set(naam);
	}
	
	// Getters
	public int getPRIMARYKEY() {
		return PRIMARYKEY;
	}
	
	public String getNaam() {
		return naam.get();
	}

	// Tostring
	@Override
	public String toString() {
		return "Vereniging [PRIMARYKEY=" + PRIMARYKEY + ", naam=" + naam + "]";
	}
}
