package nl.robinc.controller;

import javafx.beans.Observable;
import javafx.event.ActionEvent;
import nl.robinc.database.dao.AanbiedingDao;
import nl.robinc.database.dao.AandeelDao;
import nl.robinc.database.dao.GebruikerDao;
import nl.robinc.model.Gebruiker;
import nl.robinc.model.Vereniging;
import nl.robinc.view.BeursPane;

public class AccountController {
	
	// Model 
	private GebruikerDao gebruikerDao;
	private AandeelDao aandeelDao;
	private AanbiedingDao aanbiedingDao;
	
	// View
	private BeursPane view;
	
	// Control
	private Controller control;
	private PopupController popupControl;
	
	public AccountController(BeursPane view, Controller control, PopupController popupControl) {
		gebruikerDao = new GebruikerDao();
		aandeelDao = new AandeelDao();
		aanbiedingDao = new AanbiedingDao();
		
		this.view = view;
		
		this.control = control;
		this.popupControl = popupControl;
		
		view.getAccountPane().getLoginButton().setOnAction(this::loginAction);
		view.getAccountPane().getDepositButton().setOnAction(this::depositAction);
		view.getAccountPane().getComboBox().getSelectionModel().selectedItemProperty().addListener(this::comboListener);
		view.getAccountPane().getVerenigingBox().getSelectionModel().selectedItemProperty().addListener(this::verenigingListener);
	}
	
	/**
	 * ActionEvent handler voor het afhandelen van een inlogactie
	 * @param event Klikevent op de loginbutton
	 */
	private void loginAction(ActionEvent event) {
		// Haal de gebruiker op
		control.setGebruiker(gebruikerDao.loginCheck(view.getAccountPane().getUsernameField().getText(), 
				view.getAccountPane().getPasswordField().getText()));
		
		// Geef door aan de andere controllers
		
		
		// Clear het passwordveld voor nieuwe invoer
		view.getAccountPane().getUsernameField().clear();
		view.getAccountPane().getPasswordField().clear();
		
		// Checkt of de gebruiker niet null is	
		if(control.getGebruiker() != null) {
			// Haal de gebruiker op
			Gebruiker gebruiker = control.getGebruiker();
			
			// Geeft de accountinformatie weer in de GUI
			view.getAccountPane().showAccount(gebruiker);
			
			// Voeg listener toe voor aanpassingen aan de balans
			gebruiker.balansProperty().addListener((balans, oldValue, newValue) -> {
				gebruiker.setBalans((double) newValue);
				gebruikerDao.addGebruiker(gebruiker);
			});	
		}
	}
	
	/**
	 * ActionEvent handler voor het afhandelen van een stortactie op de balans
	 * @param event Klikevent op de stortknop
	 */
	private void depositAction(ActionEvent event) {
		popupControl.setBalansPopup();
	}
	
	/**
	 * ChangeListener voor het detecteren van veranderingen in de overzichtcombobox
	 * Past de mogelijkheden aan voor het kopen en verkopen van aandelen 
	 * @param observable De observable die de change triggert
	 * @param oldValue Oude string waarde die geselecteerd was door de gebruiker
	 * @param newValue Niewe string waarde die geselecteerd is door de gebruiker
	 */
	private void comboListener(Observable observable, String oldValue, String newValue) {
		switch(newValue) {
		case "Overzicht":
			// Past bar aan voor het toevoegen van een aanbieding
			view.getSubmitPane().setOrderSubmit();
			view.setAandeelPane();
			view.getAccountPane().addVerenigingBox(false);
			
			// Geef alle eigen aandelen weer, klik voor verkoop
			view.getAandeelPane().getItems().setAll(
					aandeelDao.getAandelenVanGebruiker(control.getGebruiker().getPRIMARYKEY()));
			break;
		case "Kopen":
			// Past bar aan voor het opkopen van aanbiedingen
			view.getSubmitPane().setOrderBuy();
			view.setAanbiedingPane();
			view.getAccountPane().addVerenigingBox(true);
			
			// Geef aanbiedingen weer van de geselecteerde vereniging
			Vereniging vereniging = view.getAccountPane().getVerenigingBox().getSelectionModel().getSelectedItem();
			view.getAanbiedingPane().getItems().setAll(aanbiedingDao.getAanbieding(vereniging.getNaam()));
			break;
		case "Verkopen":
			// Past bar aan voor het verwijderen van eigen aanbiedingen
			view.getSubmitPane().setOrderDelete();
			view.setAanbiedingPane();
			view.getAccountPane().addVerenigingBox(false);
			
			// Geef eigen aanbiedingen weer, update bij nieuwe aanbieding
			view.getAanbiedingPane().getItems().setAll(aanbiedingDao.getAanbieding(control.getGebruiker().getPRIMARYKEY()));
			break;
		default:
			System.err.println("Onbekende waarde in overzichtCombobox");
		}
	}
	
	/**
	 * ChangeListener voor het detecteren van veranderingen in de verenigingcombobox
	 * Past de aanbiedingen aan voor de geselecteerde vereniging 
	 * @param observable De observable die de change triggert
	 * @param oldValue Oude vereniging die geselecteerd was door de gebruiker
	 * @param newValue Nieuwe vereniging die geselecteerd is door de gebruiker
	 */
	private void verenigingListener(Observable observable, Vereniging oldValue, Vereniging newValue) { 
		view.getAanbiedingPane().getItems().clear();
		view.getAanbiedingPane().getItems().addAll(aanbiedingDao.getAanbieding(newValue.getNaam())); 
	}
}
