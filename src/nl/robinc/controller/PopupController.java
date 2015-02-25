package nl.robinc.controller;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.robinc.random.AandeelDataGenerator;
import nl.robinc.random.GebruikerDataGenerator;
import nl.robinc.random.VerenigingDataGenerator;
import nl.robinc.view.popup.BalansPopup;
import nl.robinc.view.popup.DataPopup;
import nl.robinc.view.popup.MessagePopup;

public class PopupController {

	// Model
	private GebruikerDataGenerator gebruikerGen;
	private VerenigingDataGenerator verenigingGen;
	private AandeelDataGenerator aandeelGen;
	
	// View
	private Stage popupStage;
	private BalansPopup balansPopup;
	private DataPopup dataPopup;
	private MessagePopup messagePopup;
	
	// Control
	private Controller control;
	
	public PopupController(Stage popupStage, Controller control) {
		// Model
		aandeelGen = new AandeelDataGenerator();
		gebruikerGen = new GebruikerDataGenerator();
		verenigingGen = new VerenigingDataGenerator();
		
		// View
		this.popupStage = popupStage;
		balansPopup = new BalansPopup();
		dataPopup = new DataPopup();
		messagePopup = new MessagePopup();
		
		// Control
		this.control = control;
		
		// Koppel actionevents
		balansPopup.getDepositButton().setOnAction(this::depositAction);
		dataPopup.getSubmit().setOnAction(this::dataAction);
		//messagePopup.getSend().setOnAction(this::messageAction);
	}
	
	public void setBalansPopup() {
		popupStage.setScene(new Scene(balansPopup));
		popupStage.setTitle("Deposit");
		popupStage.show();
	}
	
	public void setDataPopup() {
		popupStage.setScene(new Scene(dataPopup));
		popupStage.setTitle("Generator");
		popupStage.show();
	}
	
	public void setMessagePopup() {
		popupStage.setScene(new Scene(messagePopup));
		popupStage.setTitle("Message");
		popupStage.show();
	}
	
	private void depositAction(ActionEvent event) {
		double balans = control.getGebruiker().getBalans() + 
				Double.parseDouble(balansPopup.getDepositField().getText());
		control.getGebruiker().setBalans(balans);
	}
	
	private void dataAction(ActionEvent event) {
		int count = Math.abs(Integer.parseInt(dataPopup.getAmount().getText()));
		
		switch(dataPopup.getType().getSelectionModel().getSelectedItem()) {
		
		case "Gebruikers":
			gebruikerGen.generateGebruiker(count);
			break;
		
		case "Aandelen":
			aandeelGen.generateAandeel(count);
			break;
			
		case "Verenigingen":
			verenigingGen.generateVereniging(count);
			break;
		}
	}
	
	/*
	private void messageAction(ActionEvent event) {
		switch(messagePopup.getType().getSelectionModel().getSelectedItem()) {
		case "Gebruiker":
			GebruikerClient gebruikerClient = new GebruikerClient();
			new Thread(gebruikerClient).start();

			gebruikerClient.stateProperty().addListener((observable, oldValue, newValue) -> {
				if(newValue == Worker.State.SUCCEEDED) {
					System.out.println("MessagePopup");
				}
			}); 
		break;
		
		case "Vereniging":
			VerenigingClient verenigingClient = new VerenigingClient();
			new Thread(verenigingClient).start();

			verenigingClient.stateProperty().addListener((observable, oldValue, newValue) -> {
				if(newValue == Worker.State.SUCCEEDED) {
					System.out.println("MessagePopup");
				}
			}); 
		break;
		
		case "Aandeel":
			AandeelClient aandeelClient = new AandeelClient();
			new Thread(aandeelClient).start();

			aandeelClient.stateProperty().addListener((observable, oldValue, newValue) -> {
				if(newValue == Worker.State.SUCCEEDED) {
					System.out.println("MessagePopup");
				}
			}); 
		break;
		}
	}
	*/
}
