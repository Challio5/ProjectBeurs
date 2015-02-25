package nl.robinc.view.popup;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class BalansPopup extends VBox {

	private TextField depositField;
	private Button depositButton;
	
	public BalansPopup() {		
		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(10));
		this.setSpacing(5);
		
		depositField = new TextField();
		depositButton = new Button("submit");
		
		this.getChildren().add(depositField);
		this.getChildren().add(depositButton);
	}

	public Button getDepositButton() {
		return depositButton;
	}

	public TextField getDepositField() {
		return depositField;
	}
}
