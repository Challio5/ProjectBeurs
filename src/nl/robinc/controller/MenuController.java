package nl.robinc.controller;

import javafx.event.ActionEvent;
import nl.robinc.database.dao.AanbiedingDao;
import nl.robinc.database.dao.DatabaseDao;
import nl.robinc.random.StartupDataGenerator;
import nl.robinc.view.BeursPane;

public class MenuController {
	
	// Model
	private DatabaseDao databaseDao;
	private AanbiedingDao aanbiedingDao;
	
	// View
	private BeursPane view;
	
	// Control
	private Controller control;
	private PopupController popupControl;
	
	public MenuController(BeursPane view, Controller control, PopupController popupControl) {
		// Model
		databaseDao = new DatabaseDao();
		aanbiedingDao = new AanbiedingDao();
		
		// View
		this.view = view;
		
		// Control
		this.control = control;
		this.popupControl = popupControl;
		
		// Koppel event method references
		// Filemenu
		view.getMenuPane().getFileMenu().getItems().get(0).setOnAction(this::logOutAction);
		view.getMenuPane().getFileMenu().getItems().get(1).setOnAction(this::closeAction);

		// Debugmenu
		view.getMenuPane().getDebugMenu().getItems().get(0).setOnAction(this::startAction);
		view.getMenuPane().getDebugMenu().getItems().get(1).setOnAction(this::generateAction);
		view.getMenuPane().getDebugMenu().getItems().get(2).setOnAction(this::deleteAction);
		view.getMenuPane().getDebugMenu().getItems().get(3).setOnAction(this::sendAction);
	}
	
	private void logOutAction(ActionEvent event) {
		// Log de gebruiker uit
		control.setGebruiker(null);
		
		// Laat inlogscherm zin voor opnieuw inloggen
		view.getAccountPane().showLogin();
		
		// Laat overzicht met aanbiedingen zien
		view.getAanbiedingPane().setItems(aanbiedingDao.getAanbieding());
		view.setAanbiedingPane();
		
		// Past de submitPane voor het toevoegen van een gebruiker
		view.getSubmitPane().setGebruikerSubmit();
	}
	
	private void closeAction(ActionEvent event) {
		System.exit(0);
	}
	
	private void startAction(ActionEvent event) {
		StartupDataGenerator gen  = new StartupDataGenerator();
		gen.startup();
	}
	
	private void generateAction(ActionEvent event) {
		popupControl.setDataPopup();
	}
	
	private void deleteAction(ActionEvent event) {
		databaseDao.deleteData();
	}
	
	private void sendAction(ActionEvent event) {
		popupControl.setMessagePopup();
	}
}
