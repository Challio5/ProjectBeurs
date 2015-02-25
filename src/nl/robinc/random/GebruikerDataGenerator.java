package nl.robinc.random;

import nl.robinc.database.dao.GebruikerDao;
import nl.robinc.model.Gebruiker;
import nl.robinc.view.Terminal;

public class GebruikerDataGenerator {
	
	// Arrays met random waarden
	String[] gebruikersnamen;
	String[] wachtwoorden;
	String[] voornamen;
	String[] achternamen;
	
	// Daos voor het wegschrijven naar de database
	private GebruikerDao gebruikerDao;
	
	public GebruikerDataGenerator() {	
		gebruikersnamen = new String[] {};
		wachtwoorden = new String[] {};
		voornamen = new String[] {"Henk", "Klaas", "Jan", "Sjaak", "Tonny", "Tom", "Hans", "Joost", "Chef", "John"};
		achternamen = new String[] {"Jansen", "Klaassen", "Voshaar", "Uilenbelt", "Rutte", "Bonhof", "Braam", "Braakman", "Galgenbeld", "Spoolder"};
		
		gebruikerDao = new GebruikerDao();
	}
	
	public void generateGebruiker(int count) {
		for(int i = 0; i < count - 1; i++) {
			String gebruikersnaam = this.generateGebruikersnaam();
			String wachtwoord = this.generateWachtwoord();
			String voornaam = this.generateVoornaam();
			String achternaam = this.generateAchternaam();
			
			Gebruiker gebruiker = new Gebruiker(gebruikersnaam, wachtwoord, voornaam + " " + achternaam, 0.0);
			Terminal.println(gebruiker.toString());
			gebruikerDao.addGebruiker(gebruiker);
		}
	}
	
	private String generateGebruikersnaam() {
		StringBuffer buffer = new StringBuffer();
		// Lengte van de gebuikersnaam
		int length = (int) (Math.random() * 10 + 5);
		for(int j = 0; j < length; j++) {
			char letter = (char) ((Math.random() * 100000) % 26 + 97);
			buffer.append(letter);
		}
		
		return buffer.toString();
	}
	
	private String generateWachtwoord() {
		StringBuffer buffer = new StringBuffer();
		// Lengte van het wachtwoord
		int length = (int) (Math.random() * 10 + 5);
		for(int j = 0; j < length; j ++) {
			char letter = (char) ((Math.random() * 100000) % 26 + 97);
			buffer.append(letter);
		}
		
		return buffer.toString();
	}
	
	private String generateVoornaam() {
		int index = (int) (Math.random() * 100000 % voornamen.length);
		return voornamen[index];
	}
	
	private String generateAchternaam() {
		int index = (int) (Math.random() * 100000 % achternamen.length);
		return achternamen[index];
	}
}
