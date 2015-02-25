package nl.robinc.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class SubmitPane extends HBox {
	// Gebruikergegevens
	private TextField voornaam;
	private TextField achternaam;
	private TextField gebruikersnaam;
	private PasswordField wachtwoord;
	private Button submitGebruiker;
	
	// Submit aanbieding
	private TextField verkoopAantal;
	private TextField prijs;
	private Button submitOrder;
	
	// Koop aanbieding
	private TextField koopAantal;
	private Button buyOrder;
	
	// Verwijder aanbeiding
	private Button deleteOrder;
	
	public SubmitPane() {
		this.setPadding(new Insets(5));
		this.setSpacing(5);
		
		// Gebruikergegevens
		voornaam = new TextField();
		achternaam = new TextField();
		gebruikersnaam = new TextField();
		wachtwoord = new PasswordField();
		
		voornaam.setPromptText("voornaam");
		achternaam.setPromptText("achternaam");
		gebruikersnaam.setPromptText("gebruikersnaam");
		wachtwoord.setPromptText("wachtwoord");
		
		submitGebruiker = new Button("submit");
		submitGebruiker.setAlignment(Pos.BASELINE_RIGHT);
		
		// Submit aanbieding
		verkoopAantal = new TextField();
		prijs = new TextField();
		submitOrder = new Button("submit");
		
		verkoopAantal.setPromptText("aantal");
		prijs.setPromptText("prijs");
		submitOrder.setAlignment(Pos.BASELINE_RIGHT);
		
		// Koop aanbieding
		koopAantal = new TextField();
		buyOrder = new Button("buy");
		
		buyOrder.setAlignment(Pos.BASELINE_RIGHT);
		
		// Verwijder aanbieding
		deleteOrder = new Button("delete");
		
		this.setGebruikerSubmit();
	}
	
	// Past de pane voor het toevoegen van een gebruiker
	public void setGebruikerSubmit() {
		this.getChildren().clear();
		
		this.getChildren().add(voornaam);
		this.getChildren().add(achternaam);
		this.getChildren().add(gebruikersnaam);
		this.getChildren().add(wachtwoord);
		this.getChildren().add(submitGebruiker);
	}
	
	// Past de pane aan voor het toevoegen van een aanbieding
	public void setOrderSubmit() {
		this.getChildren().clear();
	
		this.getChildren().add(verkoopAantal);
		this.getChildren().add(prijs);
		this.getChildren().add(submitOrder);
	}
	
	// Past de pane aan voor het opkopen van een aanbieding
	public void setOrderBuy() {
		this.getChildren().clear();
		
		this.getChildren().add(koopAantal);
		this.getChildren().add(buyOrder);
	}
	
	// Past de pane aan voor het verwijderen van een aanbieding
	public void setOrderDelete() {
		this.getChildren().clear();
		
		this.getChildren().add(deleteOrder);
	}

	// Getters
	public TextField getVoornaam() {
		return voornaam;
	}

	public TextField getAchternaam() {
		return achternaam;
	}

	public TextField getGebruikersnaam() {
		return gebruikersnaam;
	}

	public PasswordField getWachtwoord() {
		return wachtwoord;
	}

	public Button getSubmitGebruiker() {
		return submitGebruiker;
	}
	
	public TextField getVerkoopAantal() {
		return verkoopAantal;
	}

	public TextField getPrijs() {
		return prijs;
	}

	public Button getSubmitOrder() {
		return submitOrder;
	}

	public TextField getKoopAantal() {
		return koopAantal;
	}

	public Button getBuyOrder() {
		return buyOrder;
	}

	public Button getDeleteOrder() {
		return deleteOrder;
	}
}
