package nl.robinc.test;

import nl.robinc.client.GebruikerClient;
import nl.robinc.client.RemoveClient;
import nl.robinc.model.Gebruiker;
import nl.robinc.request.ActionType;
import nl.robinc.request.ModelType;
import nl.robinc.request.ParameterType;


public class GebruikerTest {

	private RemoveClient removeClient = null;
	private String[] parameters = {"1"};

	public GebruikerTest() {
		this.getNone();
		this.getGebruiker();
		this.getVereniging();
		this.getAandeel();
		this.getAanbieding();
		this.getVerGen();
	}
	
	public void getNone() {
		GebruikerClient gebruikerClient = new GebruikerClient(ActionType.GET, ParameterType.NONE, parameters);
		gebruikerClient.setOnSucceeded(e -> {
			System.out.println("GebTest None:");
			for(Gebruiker gebruiker : gebruikerClient.getValue()) {
				System.out.println(gebruiker);
			}
			System.out.println("------------------");
		});
		new Thread(gebruikerClient).start();
	}
	
	public void getGebruiker() {
		GebruikerClient gebruikerClient = new GebruikerClient(ActionType.GET, ParameterType.GEBRUIKER, parameters);
		gebruikerClient.setOnSucceeded(e -> {
			System.out.println("GebTest Gebruiker:");
			for(Gebruiker gebruiker : gebruikerClient.getValue()) {
				System.out.println(gebruiker);
			}
			System.out.println("------------------");
		});
		new Thread(gebruikerClient).start();
	}

	public void getVereniging() {
		GebruikerClient gebruikerClient = new GebruikerClient(ActionType.GET, ParameterType.VERENIGING, parameters);
		gebruikerClient.setOnSucceeded(e -> {
			System.out.println("GebTest Vereniging:");
			for(Gebruiker gebruiker : gebruikerClient.getValue()) {
				System.out.println(gebruiker);
			}
			System.out.println("------------------");
		});
		new Thread(gebruikerClient).start();
	}
	
	public void getAandeel() {
		GebruikerClient gebruikerClient = new GebruikerClient(ActionType.GET, ParameterType.AANDEEL, parameters);
		gebruikerClient.setOnSucceeded(e -> {
			System.out.println("GebTest Aandeel:");
			for(Gebruiker gebruiker : gebruikerClient.getValue()) {
				System.out.println(gebruiker);
			}
			System.out.println("------------------");
		});
		new Thread(gebruikerClient).start();
	}
	
	public void getAanbieding() {
		GebruikerClient gebruikerClient = new GebruikerClient(ActionType.GET, ParameterType.AANBIEDING, parameters);
		gebruikerClient.setOnSucceeded(e -> {
			System.out.println("GebTest Aandeel:");
			for(Gebruiker gebruiker : gebruikerClient.getValue()) {
				System.out.println(gebruiker);
			}
			System.out.println("------------------");
		});
		new Thread(gebruikerClient).start();
	}
	
	public void getVerGen() {
		GebruikerClient gebruikerClient = new GebruikerClient(ActionType.GET, ParameterType.VERGEN, parameters);
		gebruikerClient.setOnSucceeded(e -> {
			System.out.println("GebTest VerGen:");
			for(Gebruiker gebruiker : gebruikerClient.getValue()) {
				System.out.println(gebruiker);
			}
			System.out.println("------------------");
		});
		new Thread(gebruikerClient).start();
	}
	
	public void add() {
	}
	
	public void remove() {
		removeClient = new RemoveClient(ModelType.GEBRUIKER, 1);
		new Thread(removeClient).start();
	}
}
