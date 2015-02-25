package nl.robinc.test;

import nl.robinc.client.AanbiedingClient;
import nl.robinc.model.Aanbieding;
import nl.robinc.request.ActionType;
import nl.robinc.request.ParameterType;

public class AanbiedingTest {
	
	private String[] parameters = {"1"};
	private String[] vereniging = {"Archimedes"};
	
	public AanbiedingTest() {
		this.getNone();
		this.getGebruiker();
		this.getVereniging();
		this.getAandeel();
		this.getAanbieding();
		this.getVerGen();
	}
	
	public void getNone() {
		AanbiedingClient aanbiedingClient = new AanbiedingClient(ActionType.GET, ParameterType.NONE, parameters);
		aanbiedingClient.setOnSucceeded(e -> {
			System.out.println("AbieTest None:");
			for(Aanbieding aanbieding : aanbiedingClient.getValue()) {
				System.out.println(aanbieding);
			}
			System.out.println("------------------");		
		});
		new Thread(aanbiedingClient).start();
	}

	public void getGebruiker() {
		AanbiedingClient aanbiedingClient = new AanbiedingClient(ActionType.GET, ParameterType.GEBRUIKER, vereniging);
		aanbiedingClient.setOnSucceeded(e -> {
			System.out.println("AbieTest Gebruiker:");
			for(Aanbieding aanbieding : aanbiedingClient.getValue()) {
				System.out.println(aanbieding);
			}
			System.out.println("------------------");	
		});
		new Thread(aanbiedingClient).start();
	}

	public void getVereniging() {
		AanbiedingClient aanbiedingClient = new AanbiedingClient(ActionType.GET, ParameterType.VERENIGING, vereniging);
		aanbiedingClient.setOnSucceeded(e -> {
			System.out.println("AbieTest Vereniging:");
			for(Aanbieding aanbieding : aanbiedingClient.getValue()) {
				System.out.println(aanbieding);
			}
			System.out.println("------------------");	
		});
		new Thread(aanbiedingClient).start();
	}
	
	public void getAandeel() {
		AanbiedingClient aanbiedingClient = new AanbiedingClient(ActionType.GET, ParameterType.AANDEEL, parameters);
		aanbiedingClient.setOnSucceeded(e -> {
			System.out.println("AbieTest Aandeel:");
			for(Aanbieding aanbieding : aanbiedingClient.getValue()) {
				System.out.println(aanbieding);
			}
			System.out.println("------------------");	
		});
		new Thread(aanbiedingClient).start();
	}
	
	public void getAanbieding() {
		AanbiedingClient aanbiedingClient = new AanbiedingClient(ActionType.GET, ParameterType.AANBIEDING, parameters);
		aanbiedingClient.setOnSucceeded(e -> {
			System.out.println("AbieTest Aanbieding:");
			for(Aanbieding aanbieding : aanbiedingClient.getValue()) {
				System.out.println(aanbieding);
			}
			System.out.println("------------------");	
		});
		new Thread(aanbiedingClient).start();
	}
	
	public void getVerGen() {
		AanbiedingClient aanbiedingClient = new AanbiedingClient(ActionType.GET, ParameterType.VERGEN, parameters);
		aanbiedingClient.setOnSucceeded(e -> {
			System.out.println("AbieTest VerGen:");
			for(Aanbieding aanbieding : aanbiedingClient.getValue()) {
				System.out.println(aanbieding);
			}
			System.out.println("------------------");	
		});
		new Thread(aanbiedingClient).start();
	}
	
	public void add() {
	}
	
	public void remove() {
	}
}
