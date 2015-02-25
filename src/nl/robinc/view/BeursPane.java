package nl.robinc.view;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class BeursPane extends BorderPane {
	// Top voor het menu
	private MenuPane menuPane;
	
	// Midden voor aandelen of aanbiedingen
	private AanbiedingPane aanbiedingPane;
	private AandeelPane aandeelPane;
	
	// Rechts voor het account
	private AccountPane accountPane;
	
	// Onder voor de terminal en submitbar
	private VBox bottom;
	private Terminal terminal;
	private SubmitPane submitPane;
	
	public BeursPane() {
		aanbiedingPane = new AanbiedingPane();
		aandeelPane = new AandeelPane();
		
		accountPane = new AccountPane();
		menuPane = new MenuPane();
		
		bottom = new VBox();
		terminal = new Terminal();
		submitPane = new SubmitPane();
		bottom.getChildren().add(submitPane);
		bottom.getChildren().add(terminal);
		
		this.setTop(menuPane);
		this.setCenter(aanbiedingPane);
		this.setRight(accountPane);
		this.setBottom(bottom);
	}
	
	public void setAandeelPane() {
		if(!(this.getTop() instanceof AandeelPane)) {
			this.setCenter(aandeelPane);
		}
	}
	
	public void setAanbiedingPane() {
		if(!(this.getTop() instanceof AanbiedingPane)) {
			this.setCenter(aanbiedingPane);
		}
	}

	// Getters
	public MenuPane getMenuPane() {
		return menuPane;
	}
	
	public AanbiedingPane getAanbiedingPane() {
		return aanbiedingPane;
	}

	public AandeelPane getAandeelPane() {
		return aandeelPane;
	}
	
	public AccountPane getAccountPane() {
		return accountPane;
	}

	public Terminal getTerminal() {
		return terminal;
	}

	public SubmitPane getSubmitPane() {
		return submitPane;
	}
}
