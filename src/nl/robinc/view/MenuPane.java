package nl.robinc.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuPane extends MenuBar {

	private Menu beursMenu;
	private MenuItem logOut;
	private MenuItem close;
	
	private Menu debugMenu;

	private MenuItem start;
	private MenuItem generateData;
	private MenuItem deleteData;
	private MenuItem sendMessage;
	
	public MenuPane() {

		// Filemenu
		beursMenu = new Menu("Beurs");
		
		logOut = new MenuItem("Logout");
		close = new MenuItem("Close");
		
		beursMenu.getItems().add(logOut);
		beursMenu.getItems().add(close);
		
		// Debugmenu
		debugMenu = new Menu("Debug");
		
		start = new MenuItem("start");
		generateData = new MenuItem("genData");		
		deleteData = new MenuItem("delData");
		sendMessage = new MenuItem("sendMes");
		
		debugMenu.getItems().add(start);
		debugMenu.getItems().add(generateData);
		debugMenu.getItems().add(deleteData);
		debugMenu.getItems().add(sendMessage);
		
		// Voeg menu's toe
		this.getMenus().add(beursMenu);
		this.getMenus().add(debugMenu);
	}
	
	// Getters
	public Menu getFileMenu() {
		return beursMenu;
	}

	public Menu getDebugMenu() {
		return debugMenu;
	}
}
