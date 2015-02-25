package nl.robinc.view.popup;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class DataPopup extends VBox {

	private ComboBox<String> type;
	private TextField amount;
	private Button submit;

	public DataPopup() {
		this.setPadding(new Insets(10));
		this.setSpacing(5);
		this.setAlignment(Pos.CENTER);
		
		type = new ComboBox<>();
		type.getItems().setAll("Gebruikers", "Aandelen", "Verenigingen");
		type.getSelectionModel().selectFirst();
		
		amount = new TextField();
		amount.setPromptText("amount");
		
		submit = new Button("submit");
		
		this.getChildren().add(type);
		this.getChildren().add(amount);
		this.getChildren().add(submit);
	}

	public ComboBox<String> getType() {
		return type;
	}

	public TextField getAmount() {
		return amount;
	}

	public Button getSubmit() {
		return submit;
	}
}
