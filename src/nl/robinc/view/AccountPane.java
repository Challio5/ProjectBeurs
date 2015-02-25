package nl.robinc.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import nl.robinc.model.Gebruiker;
import nl.robinc.model.Vereniging;
import nl.robinc.model.converter.VerenigingConverter;

public class AccountPane extends VBox{
	
	private TextField usernameField;
	private PasswordField passwordField;
	private Button loginButton;
	
	private ComboBox<String> comboBox;
	private ComboBox<Vereniging> verenigingBox;
	
	private Label accountLabel;
	
	private Label nameLabel;
	private Label balanceLabel;
	
	private Button depositButton;
	
	public AccountPane() {
		this.setPadding(new Insets(10));
		this.setSpacing(4);
		this.setAlignment(Pos.CENTER);
		
		// Inlogscherm
		usernameField = new TextField();
		usernameField.setPromptText("username");
		
		passwordField = new PasswordField();
		passwordField.setPromptText("password");
		
		loginButton = new Button("login");
		
		// Accountscherm
		comboBox = new ComboBox<>();
		comboBox.getItems().addAll("Overzicht", "Kopen", "Verkopen");
		
		verenigingBox = new ComboBox<>();
		verenigingBox.setConverter(new VerenigingConverter());
		
		accountLabel = new Label("Account");
		accountLabel.setFont(new Font("robotica", 20));
		
		nameLabel = new Label();
		balanceLabel = new Label();
		
		depositButton = new Button("deposit");

		this.showLogin();
	}
	
	public void showLogin() {
		// Clear de huidige nodes
		this.getChildren().clear();
		
		// Voeg de nieuwe nodes toe
		this.getChildren().add(usernameField);
		this.getChildren().add(passwordField);
		this.getChildren().add(loginButton);
	}
	
	public void showAccount(Gebruiker gebruiker) {		
		// Maak de label gelijk aan de naam van de gebruiker
		nameLabel.setText(gebruiker.getNaam());
		
		// Bind de huidige balans van de gebruiker aan de balans string
		balanceLabel.textProperty().bind(
				new SimpleStringProperty("balans €").concat(gebruiker.balansProperty().asString()));
		
		// Selecteer de eerste voor een overzicht van de aandelen van gebruiker
		comboBox.getSelectionModel().selectFirst();
		
		// Clear de huidige nodes
		this.getChildren().clear();
		
		// Voeg de nieuwe nodes toe
		this.getChildren().add(comboBox);
		this.getChildren().add(accountLabel);
		this.getChildren().add(nameLabel);
		this.getChildren().add(balanceLabel);
		this.getChildren().add(depositButton);
	}
	
	public void addVerenigingBox(boolean add) {
		if(add) {
			this.getChildren().add(1, verenigingBox);
			verenigingBox.getSelectionModel().selectFirst();
		} else {
			if(this.getChildren().contains(verenigingBox)) {
				this.getChildren().remove(1);
			}
		}
	}
	
	// Getters
	public TextField getUsernameField() {
		return usernameField;
	}

	public PasswordField getPasswordField() {
		return passwordField;
	}

	public Button getLoginButton() {
		return loginButton;
	}

	public ComboBox<String> getComboBox() {
		return comboBox;
	}

	public ComboBox<Vereniging> getVerenigingBox() {
		return verenigingBox;
	}

	public Button getDepositButton() {
		return depositButton;
	}
}
