package nl.robinc.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Aanbieding {
	private final int PRIMARYKEY;
	
	private ObjectProperty<Gebruiker> gebruiker;
	private ObjectProperty<Vereniging> vereniging;
	
	private IntegerProperty aantal;
	private DoubleProperty prijs;
	
	public Aanbieding(int PRIMARYKEY, Gebruiker gebruiker, Vereniging vereniging, int aantal, double prijs) {
		this.PRIMARYKEY = PRIMARYKEY;
		
		this.vereniging = new SimpleObjectProperty<>(vereniging);
		this.gebruiker = new SimpleObjectProperty<>(gebruiker);
		
		this.aantal = new SimpleIntegerProperty(aantal);
		this.prijs = new SimpleDoubleProperty(prijs);
	}

	public Aanbieding(Gebruiker gebruiker, Vereniging vereniging, int aantal, double prijs) {
		this.PRIMARYKEY = 0;
		
		this.vereniging = new SimpleObjectProperty<>(vereniging);
		this.gebruiker = new SimpleObjectProperty<>(gebruiker);
		
		this.aantal = new SimpleIntegerProperty(aantal);
		this.prijs = new SimpleDoubleProperty(prijs);
	}
	
	// Properties
	public ObjectProperty<Gebruiker> gebruikerProperty() {
		return gebruiker;
	}
	public ObjectProperty<Vereniging> verenigingProperty() {
		return vereniging;
	}
	
	public IntegerProperty aantalProperty() {
		return aantal;
	}
	
	public DoubleProperty prijsProperty() {
		return prijs;
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

	public double getPrijs() {
		return prijs.get();
	}

	// Setters
	public void setAantal(int aantal) {
		this.aantal.set(aantal);
	}

	// ToString
	@Override
	public String toString() {
		return "Aanbieding [PRIMARYKEY=" + PRIMARYKEY + ", gebruiker="
				+ gebruiker + ", vereniging=" + vereniging + ", aantal="
				+ aantal + ", prijs=" + prijs + "]";
	}
}
