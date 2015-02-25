package nl.robinc.random;

import java.util.List;
import java.util.Random;

import nl.robinc.database.dao.AandeelDao;
import nl.robinc.database.dao.GebruikerDao;
import nl.robinc.database.dao.VerenigingDao;
import nl.robinc.model.Aandeel;
import nl.robinc.model.Gebruiker;
import nl.robinc.model.Vereniging;
import nl.robinc.view.Terminal;

public class AandeelDataGenerator {
	
	// Getallen generator
	private Random generator;
	
	// Daos
	private AandeelDao aandeelDao;
	private GebruikerDao gebruikerDao;
	private VerenigingDao verenigingDao;
	
	// Aandeelgegevens
	private List<Integer> gebruikernummers;
	private List<Integer> verenigingsnummers;

	public AandeelDataGenerator() {
		generator = new Random();
		
		aandeelDao = new AandeelDao();
		gebruikerDao = new GebruikerDao();
		verenigingDao = new VerenigingDao();
	}
	
	public void generateAandeel(int count) {
		gebruikernummers = gebruikerDao.getGebruikerNummers();
		verenigingsnummers = verenigingDao.getVerenigingNummers();
			
		int gebruikernummer = this.generateGebruikersnummers();
		int verenigingsnummer = this.generateVerenigingsnummers();
			
		Gebruiker gebruiker = gebruikerDao.getGebruiker(gebruikernummer);
		Vereniging vereniging = verenigingDao.getVereniging(verenigingsnummer);
			
		Aandeel aandeel = new Aandeel(gebruiker, vereniging, count);
		Terminal.println(aandeel.toString());
		aandeelDao.addAandeel(aandeel);
	}
	
	private int generateGebruikersnummers() {
		int index = generator.nextInt(gebruikernummers.size());
		return gebruikernummers.get(index);
	}
	
	private int generateVerenigingsnummers() {
		int index = generator.nextInt(verenigingsnummers.size());
		return verenigingsnummers.get(index);
	}
}
