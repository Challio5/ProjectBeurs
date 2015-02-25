package nl.robinc.model.converter;

import java.util.HashMap;

import javafx.util.StringConverter;
import nl.robinc.model.Vereniging;

public class VerenigingConverter extends StringConverter<Vereniging> {

	private HashMap<String, Vereniging> verenigingLijst = new HashMap<>();
	
	@Override
	public Vereniging fromString(String naam) {
		return verenigingLijst.get(naam);
	}

	@Override
	public String toString(Vereniging vereniging) {
		verenigingLijst.put(vereniging.getNaam(), vereniging);
		return vereniging.getNaam();
	}

}
