package nl.robinc.view.popup;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class MessagePopup extends VBox {
	private ComboBox<String> type;
	private TextField message;
	private Button send;

	public MessagePopup() {
		this.setPadding(new Insets(10));
		this.setSpacing(5);
		this.setAlignment(Pos.CENTER);
		
		message = new TextField();
		
		type = new ComboBox<>();
		type.getItems().addAll("Gebruiker", "Vereniging", "Aandeel");
		
		send = new Button("Send");
		
		this.getChildren().add(type);
		this.getChildren().add(message);
		this.getChildren().add(send);
	}

	public ComboBox<String> getType() {
		return type;
	}

	public TextField getMessage() {
		return message;
	}

	public Button getSend() {
		return send;
	}
}
