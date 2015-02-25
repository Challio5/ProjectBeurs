package nl.robinc.controller;

import javafx.stage.Stage;
import nl.robinc.database.dao.AanbiedingDao;
import nl.robinc.database.dao.VerenigingDao;
import nl.robinc.model.Gebruiker;
import nl.robinc.view.BeursPane;

public class Controller {
	// Model
	// Daos
	private VerenigingDao verenigingDao;
	private AanbiedingDao aanbiedingDao;
	
	// Ingelogde gebruiker
	private Gebruiker gebruiker;
	
	// Popupstage met controller
	private Stage popupStage;
	private PopupController popupControl;
	
	public Controller(BeursPane view) {
		// Daos
		verenigingDao = new VerenigingDao();
		aanbiedingDao = new AanbiedingDao();
		
		// Ingelogde gebruiker
		gebruiker = null;
				
		// Popupstage
		popupStage = new Stage();
		popupControl = new PopupController(popupStage, this);
		
		// Controllers
		new MenuController(view, this, popupControl);
		new AccountController(view, this, popupControl);
		new SubmitController(view, this);
		
		// Koppel het model aan de view
		view.getAanbiedingPane().getItems().addAll(aanbiedingDao.getAanbieding());
		view.getAccountPane().getVerenigingBox().getItems().addAll(verenigingDao.getVereniging());
	}

	public Gebruiker getGebruiker() {
		return gebruiker;
	}

	public void setGebruiker(Gebruiker gebruiker) {
		this.gebruiker = gebruiker;
	}
}
