package nl.robinc.random;

import javafx.collections.ObservableList;
import nl.robinc.database.dao.AanbiedingDao;
import nl.robinc.database.dao.AandeelDao;
import nl.robinc.database.dao.DatabaseDao;
import nl.robinc.database.dao.GebruikerDao;
import nl.robinc.database.dao.VerenigingDao;
import nl.robinc.model.Aanbieding;
import nl.robinc.model.Aandeel;
import nl.robinc.model.Gebruiker;
import nl.robinc.model.Vereniging;

public class StartupDataGenerator {
	
	// Databasedao
	private DatabaseDao dao;
	
	// Generators
	private GebruikerDataGenerator gebGen;
	private VerenigingDataGenerator verGen;
	
	// Dao 
	private GebruikerDao gebruikerDao;
	private VerenigingDao verenigingDao;
	private AandeelDao aandeelDao;
	private AanbiedingDao aanbiedingDao;
	
	// Constructor
	public StartupDataGenerator() {
		dao = new DatabaseDao();
		
		gebGen = new GebruikerDataGenerator();
		verGen = new VerenigingDataGenerator();
		
		gebruikerDao = new GebruikerDao();
		verenigingDao = new VerenigingDao();
		aandeelDao = new AandeelDao();
		aanbiedingDao = new AanbiedingDao();
	}
	
	// Methode om de database in zijn originele positie te brengen
	public void startup() {
		// Delete alle data in de database
		dao.deleteData();
		
		// Genereer de verenigingen
		this.setupVerenigingen();
		
		// Genereer de overige gebruikers
		this.setupGebruikers();
	}
	
	private void setupVerenigingen() {
		// Genereer verenigingen met bijbehorende account
		verGen.generateAllVerenigingen();
		
		// Vraag de verenigingen met hun accounts op
		ObservableList<Vereniging> verenigingenLijst = verenigingDao.getVereniging();
		ObservableList<Gebruiker> gebruikersLijst = gebruikerDao.getGebruiker();
		
		// Voor alle 10 de verenigingen worden 5000 aandelen gegenereerd
		for(int i = 0; i < 10; i++) {
			Gebruiker gebruiker = gebruikersLijst.get(i);
			Vereniging vereniging = verenigingenLijst.get(i);
			
			// Genereer de aandelen en aanbiedingen
			Aandeel aandeel = new Aandeel(gebruiker, vereniging, 5000);
			Aanbieding aanbieding = new Aanbieding(gebruiker, vereniging, 5000, 5.0);
			
			// Voeg de aandelen toe en zet ze te koop
			aandeelDao.addAandeel(aandeel);
			aanbiedingDao.addAanbieding(aanbieding);
		}
	}
	
	private void setupGebruikers() {
		// Genereer de gebruikers
		gebGen.generateGebruiker(10);
	}
}
