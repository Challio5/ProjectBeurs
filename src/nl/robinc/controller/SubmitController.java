package nl.robinc.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import nl.robinc.database.dao.AanbiedingDao;
import nl.robinc.database.dao.AandeelDao;
import nl.robinc.database.dao.GebruikerDao;
import nl.robinc.model.Aanbieding;
import nl.robinc.model.Aandeel;
import nl.robinc.model.Gebruiker;
import nl.robinc.view.BeursPane;

public class SubmitController {
	
	// Model
	private GebruikerDao gebruikerDao;
	private AandeelDao aandeelDao;
	private AanbiedingDao aanbiedingDao;
	
	// View
	private BeursPane view;
	
	// Controller
	private Controller control;
	
	public SubmitController(BeursPane view, Controller control) {
		gebruikerDao = new GebruikerDao();
		aandeelDao = new AandeelDao();
		aanbiedingDao = new AanbiedingDao();
		
		this.view = view;
		this.control = control;
		
		view.getSubmitPane().getSubmitOrder().setOnAction(this::submitOrderAction);
		view.getSubmitPane().getBuyOrder().setOnAction(this::buyOrderAction);
		view.getSubmitPane().getDeleteOrder().setOnAction(this::deleteOrderAction);
		view.getSubmitPane().getSubmitGebruiker().setOnAction(this::submitGebruikerAction);
	}
	
	private void submitOrderAction(ActionEvent event) {
		// Haal de informatie uit de GUI
		Aandeel aandeel = view.getAandeelPane().getSelectionModel().getSelectedItem();
		int aantal = 0;
		double prijs = 0.0;
		
		// Probeer het aantal te parsen, anders verkeerde invoer en clear het veld
		try {
			aantal = Integer.parseInt(view.getSubmitPane().getVerkoopAantal().getText());
		} catch(NumberFormatException exception) {
			System.err.println("Kan: " + view.getSubmitPane().getVerkoopAantal().getText() + " niet parsen");
			view.getSubmitPane().getVerkoopAantal().clear();
			return;
		}
		
		// Probeer de prijs te parsen, anders verkeerde invoer en clear het veld
		try {
			prijs = Double.parseDouble(view.getSubmitPane().getPrijs().getText());
		} catch(NumberFormatException exception) {
			System.err.println("Kan: " + view.getSubmitPane().getPrijs().getText() + " niet parsen");
			view.getSubmitPane().getPrijs().clear();
			return;
		}
		
		// Check of het aantal niet te hoog is		
		if(aantal > aandeel.getAantal()) {
			System.err.println("Aanbieding bevat teveel aandelen");
			return;
		}
		
		// Maak de aanbieding en voeg toe aan de database
		Aanbieding aanbieding = new Aanbieding(control.getGebruiker(), aandeel.getVereniging(), aantal, prijs);
		aanbiedingDao.addAanbieding(aanbieding);
		
		// Clear alle velden
		view.getSubmitPane().getVerkoopAantal().clear();
		view.getSubmitPane().getPrijs().clear();
	}
	
	private void buyOrderAction(ActionEvent event) {
		// Haal de geselecteerde order op
		Aanbieding aanbieding = view.getAanbiedingPane().getSelectionModel().getSelectedItem();
		
		if(aanbieding == null) {
			System.err.println("Niets geselecteerd");
			return;
		}
		
		if(aanbieding.getGebruiker().getPRIMARYKEY() == control.getGebruiker().getPRIMARYKEY()) {
			System.err.println("Kan geen eigen aanbiedingen kopen");
			return;
		}
		
		// Haal het aantal te kopen aandelen op
		int aantal = 0;
		
		try {
			aantal = Integer.parseInt(view.getSubmitPane().getKoopAantal().getText());
		} catch(NumberFormatException exception) {
			System.err.println("Kan verkoopaantal: " + 
					view.getSubmitPane().getKoopAantal().getText() + " niet parsen");
			
			view.getSubmitPane().getKoopAantal().clear();
			return;
		} 
		
		// Haalt de waarde op van de aankoop
		double waarde = aantal * aanbieding.getPrijs();
		
		if(waarde > control.getGebruiker().getBalans()) {
			System.err.println("Te weinig geld: " + (waarde - control.getGebruiker().getBalans()));
			return;
		}
		
		// Pas de aanbieding aan of verwijder bij opkopen hele aanbieding
		if(aantal > aanbieding.getAantal()) {
			System.err.println("Meer aandelen dan aanbieding");
		} else if(aantal == aanbieding.getAantal()) {
			aanbiedingDao.removeAanbieding(aanbieding.getPRIMARYKEY());
		} else {
			aanbieding.setAantal(aanbieding.getAantal() - aantal);
			aanbiedingDao.addAanbieding(aanbieding);
		}
		
		// Verwijder de aandelen bij de verkoper
		ObservableList<Aandeel> aandelenLijst1 = aandeelDao.getAandelenVanVerenigingEnGebruiker(
				aanbieding.getVereniging().getPRIMARYKEY(), aanbieding.getGebruiker().getPRIMARYKEY());
			
		if(aandelenLijst1.size() > 1) {
			System.err.println("Dubbel aandelen van zelfde gebruiker en vereniging, size: " + 
					aandelenLijst1.size());
			return;
		}
			
		Aandeel verkoperAandeel = aandelenLijst1.get(0);
		verkoperAandeel.setAantal(verkoperAandeel.getAantal() - aantal);
				
		// Voeg aandelen toe aan gebruiker
		ObservableList<Aandeel> aandelenLijst2 = aandeelDao.getAandelenVanVerenigingEnGebruiker(
				aanbieding.getVereniging().getPRIMARYKEY(), control.getGebruiker().getPRIMARYKEY());
			
		if(aandelenLijst2.size() > 1) {
			System.err.println("Dubbel aandelen van zelfde gebruiker en vereniging" + aandelenLijst2.size());
			return;
		}
			
		// Check of gebruiker al aandelen heeft van de vereniging, anders aanmaken
		if(aandelenLijst2.isEmpty()) {
			Aandeel aandeel = new Aandeel(control.getGebruiker(), aanbieding.getVereniging(), aantal);
			aandeelDao.addAandeel(aandeel);
		} else {
			Aandeel aandeel = aandelenLijst2.get(0);
			aandeel.setAantal(aandeel.getAantal() - aantal);
			aandeelDao.addAandeel(aandeel);
		}
		
		// Verrekend de balans
		Gebruiker gebruiker = control.getGebruiker();
		gebruiker.setBalans(gebruiker.getBalans() - waarde);
		gebruikerDao.addGebruiker(gebruiker);
		
		Gebruiker verkoper = aanbieding.getGebruiker();
		verkoper.setBalans(gebruiker.getBalans() + waarde);
		gebruikerDao.addGebruiker(verkoper);
	}
	
	/**
	 * ActionEvent voor het verwijderen van een aanbieding
	 * Leest de geselecteerde aanbieding uit en verwijdert deze
	 * @param event KlikEvent druk op de submitknop
	 */
	private void deleteOrderAction(ActionEvent event) {
		// Vraag de aanbieding op met de index in de lijst
		Aanbieding aanbieding = view.getAanbiedingPane().getSelectionModel().getSelectedItem();
		int index = view.getAanbiedingPane().getSelectionModel().getSelectedIndex();
		
		// Verwijder uit de database en lijst
		aanbiedingDao.removeAanbieding(aanbieding.getPRIMARYKEY());
		view.getAanbiedingPane().getItems().remove(index);
	}
	
	/**
	 * ActionEvent voor het toevoegen van een gebruiker
	 * Leest de ingevulde waardes uit en voegt deze toe aan de database
	 * @param event KlikEvent druk op de submitknop
	 */
	private void submitGebruikerAction(ActionEvent event) {
		// Lees de accountinformatie uit de GUI
		String gebruikersnaam = view.getSubmitPane().getGebruikersnaam().getText();
		String wachtwoord = view.getSubmitPane().getWachtwoord().getText();
		
		String voornaam = view.getSubmitPane().getVoornaam().getText();
		String achternaam = view.getSubmitPane().getAchternaam().getText();
		String naam = voornaam + " " + achternaam;
		
		// Maak de gebruiker aan en voeg toe aan de database
		Gebruiker gebruiker = new Gebruiker(gebruikersnaam, wachtwoord, naam, 0.0);
		gebruikerDao.addGebruiker(gebruiker);
		
		// Clear de velden
		view.getSubmitPane().getGebruikersnaam().clear();
		view.getSubmitPane().getWachtwoord().clear();
		view.getSubmitPane().getVoornaam().clear();
		view.getSubmitPane().getAchternaam().clear();
	}
}
