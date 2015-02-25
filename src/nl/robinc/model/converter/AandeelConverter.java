package nl.robinc.model.converter;

import java.util.HashMap;

import javafx.util.StringConverter;
import nl.robinc.model.Aandeel;

public class AandeelConverter extends StringConverter<Aandeel> {
	
	private HashMap<String, Aandeel> aandelenLijst = new HashMap<>();
	
	@Override
	public Aandeel fromString(String naam) {
		return aandelenLijst.get(naam);
	}

	@Override
	public String toString(Aandeel aandeel) {
		aandelenLijst.put(aandeel.getGebruiker().getNaam(), aandeel);
		return aandeel.getGebruiker().getNaam();
	}

}
