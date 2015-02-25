package nl.robinc.random;

import nl.robinc.database.dao.GebruikerDao;
import nl.robinc.database.dao.VerenigingDao;
import nl.robinc.model.Gebruiker;
import nl.robinc.model.Vereniging;

public class VerenigingDataGenerator {

	// Dao
	private VerenigingDao verenigingDao;
	private GebruikerDao gebruikerDao;
	
	// Lijst met namen
	private String[] namenLijst;
	
	// Constructor
	public VerenigingDataGenerator() {
		verenigingDao = new VerenigingDao();
		gebruikerDao = new GebruikerDao();
		namenLijst = new String[] {"Archimedes", "Balans", "Construct", "Cementi", 
				"Connect Terzake", "IManage", "ISLINK", "Syntaxis", "TriasLo", "Watt"};
	}
	
	public void generateAllVerenigingen() {
		for(int i = 0; i < namenLijst.length; i++) {
			// Vereniging
			Vereniging vereniging = new Vereniging(namenLijst[i]);
			
			// Gebruiker voor de vereniging
			Gebruiker gebruiker = new Gebruiker(namenLijst[i], namenLijst[i], "voorzitter", 100.0);
			
			// Voeg toe aan de database
			verenigingDao.addVereniging(vereniging);
			gebruikerDao.addGebruiker(gebruiker);
		}
	}
	
	public void generateVereniging(int count) {
		if(count > namenLijst.length) {
			count = namenLijst.length;
		}
		
		for(int i = 0; i < count; i++) {
			// Vereniging
			Vereniging vereniging = new Vereniging(namenLijst[i]);
			
			// Gebruiker voor de vereniging
			Gebruiker gebruiker = new Gebruiker(namenLijst[i], namenLijst[i], "voorzitter", 100.0);
			
			// Voeg toe aan de database
			verenigingDao.addVereniging(vereniging);
			gebruikerDao.addGebruiker(gebruiker);
		}
	}
}
