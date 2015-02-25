package nl.robinc.apl;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.robinc.controller.Controller;
import nl.robinc.server.Server;
import nl.robinc.test.AanbiedingTest;
import nl.robinc.test.AandeelTest;
import nl.robinc.test.GebruikerTest;
import nl.robinc.test.VerenigingTest;
import nl.robinc.view.BeursPane;

public class Beurs extends Application{

	// View
	private boolean test;
	private BeursPane view;
	
	public Beurs() {
		test = false;
		view = new BeursPane();
		
		new Controller(view);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Server server = new Server();
		new Thread(server).start();
		
		if(!test) {
			Scene scene = new Scene(view);
			stage.setScene(scene);
			stage.setTitle("Beurs");
			stage.show();
		} else {
			new GebruikerTest();
			new VerenigingTest();
			new AandeelTest();
			new AanbiedingTest();
		}
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}
