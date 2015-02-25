package nl.robinc.test;

import nl.robinc.client.AandeelClient;
import nl.robinc.model.Aandeel;
import nl.robinc.request.ActionType;
import nl.robinc.request.ParameterType;

public class AandeelTest {

	private String[] parameters = {"1"};
	private String[] vergen = {"1", "1"};
	
	public AandeelTest() {
		this.getNone();
		this.getGebruiker();
		this.getVereniging();
		this.getAandeel();
		this.getAanbieding();
		this.getVerGen();
	}
	
	public void getNone() {
		AandeelClient aandeelClient = new AandeelClient(ActionType.GET, ParameterType.NONE, parameters);
		aandeelClient.setOnSucceeded(e -> {
			System.out.println("AdeelTest None:");
			for(Aandeel aandeel : aandeelClient.getValue()) {
				System.out.println(aandeel);
			}
			System.out.println("------------------");	
		});
		new Thread(aandeelClient).start();
	}
	
	public void getGebruiker() {
		AandeelClient aandeelClient = new AandeelClient(ActionType.GET, ParameterType.GEBRUIKER, parameters);
		aandeelClient.setOnSucceeded(e -> {
			System.out.println("AdeelTest Gebruiker:");
			for(Aandeel aandeel : aandeelClient.getValue()) {
				System.out.println(aandeel);
			}
			System.out.println("------------------");	
		});
		new Thread(aandeelClient).start();
	}

	public void getVereniging() {
		AandeelClient aandeelClient = new AandeelClient(ActionType.GET, ParameterType.VERENIGING, parameters);
		aandeelClient.setOnSucceeded(e -> {
			System.out.println("AdeelTest Vereniging:");
			for(Aandeel aandeel : aandeelClient.getValue()) {
				System.out.println(aandeel);
			}
			System.out.println("------------------");	
		});
		new Thread(aandeelClient).start();
	}
	
	public void getAandeel() {
		AandeelClient aandeelClient = new AandeelClient(ActionType.GET, ParameterType.AANDEEL, parameters);
		aandeelClient.setOnSucceeded(e -> {
			System.out.println("AdeelTest Aandeel:");
			for(Aandeel aandeel : aandeelClient.getValue()) {
				System.out.println(aandeel);
			}
			System.out.println("------------------");	
		});
		new Thread(aandeelClient).start();
	}
	
	public void getAanbieding() {
		AandeelClient aandeelClient = new AandeelClient(ActionType.GET, ParameterType.AANBIEDING, parameters);
		aandeelClient.setOnSucceeded(e -> {
			System.out.println("AdeelTest Aanbieding:");
			for(Aandeel aandeel : aandeelClient.getValue()) {
				System.out.println(aandeel);
			}
			System.out.println("------------------");	
		});
		new Thread(aandeelClient).start();
	}
	
	public void getVerGen() {
		AandeelClient aandeelClient = new AandeelClient(ActionType.GET, ParameterType.VERGEN, vergen);
		aandeelClient.setOnSucceeded(e -> {
			System.out.println("AdeelTest VerGen:");
			for(Aandeel aandeel : aandeelClient.getValue()) {
				System.out.println(aandeel);
			}
			System.out.println("------------------");	
		});
		new Thread(aandeelClient).start();
	}

	public void add() {
	}
	
	public void remove() {
	}
}
