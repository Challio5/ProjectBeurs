package nl.robinc.model.converter;

import java.util.HashMap;

import javafx.util.StringConverter;
import nl.robinc.model.Gebruiker;

public class GebruikerConverter extends StringConverter<Gebruiker> {

	private HashMap<String, Gebruiker> gebruikersLijst = new HashMap<>();
	
	@Override
	public Gebruiker fromString(String naam) {
		return gebruikersLijst.get(naam);
	}

	@Override
	public String toString(Gebruiker gebruiker) {
		gebruikersLijst.put(gebruiker.getNaam(), gebruiker);
		return gebruiker.getNaam();
	}

}
