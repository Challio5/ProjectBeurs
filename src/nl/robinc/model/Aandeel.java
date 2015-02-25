package nl.robinc.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Aandeel {
	// Primary key
	private final int PRIMARYKEY;
	
	// De gebruiker van het aandeel
	private ObjectProperty<Gebruiker> gebruiker;
	private ObjectProperty<Vereniging> vereniging;
	
	// Aantal aandelen in bezit van de gebruiker
	private IntegerProperty aantal;

	// Constructor voor het opslaan van een gebruiker
	public Aandeel(Gebruiker gebruiker, Vereniging vereniging, int aantal) {
		this.PRIMARYKEY = 0;
		this.gebruiker = new SimpleObjectProperty<>(gebruiker);
		this.vereniging = new SimpleObjectProperty<>(vereniging);
		this.aantal = new SimpleIntegerProperty(aantal);
	}
	
	// Constructor voor het ophalen van een gebruiker
	public Aandeel(int nummer, Gebruiker gebruiker, Vereniging vereniging, int aantal) {
		this.PRIMARYKEY = nummer;
		this.gebruiker = new SimpleObjectProperty<>(gebruiker);
		this.vereniging = new SimpleObjectProperty<>(vereniging);
		this.aantal = new SimpleIntegerProperty(aantal);
	}
	
	// Propertys
	public ObjectProperty<Gebruiker> gebruikerProperty() {
		return gebruiker;
	}

	public ObjectProperty<Vereniging> verenigingProperty() {
		return vereniging;
	}
	
	public IntegerProperty aantalProperty() {
		return aantal;
	}

	// Setters
	public void setGebruiker(Gebruiker gebruiker) {
		this.gebruiker.set(gebruiker);
	}

	public void setVereniging(Vereniging vereniging) {
		this.vereniging.set(vereniging);
	}
	
	public void setAantal(int aantal) {
		this.aantal.set(aantal);
	}

	// Getters
	public int getPRIMARYKEY() {
		return PRIMARYKEY;
	}
	
	public Gebruiker getGebruiker() {
		return gebruiker.get();
	}

	public Vereniging getVereniging() {
		return vereniging.get();
	}

	public int getAantal() {
		return aantal.get();
	}

	// Tostring
	@Override
	public String toString() {
		return "Aandeel [PRIMARYKEY=" + PRIMARYKEY + ", gebruiker=" + gebruiker
				+ ", vereniging=" + vereniging + ", aantal=" + aantal + "]";
	}
}
