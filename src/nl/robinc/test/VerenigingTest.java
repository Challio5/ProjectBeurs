package nl.robinc.test;

import nl.robinc.client.VerenigingClient;
import nl.robinc.model.Vereniging;
import nl.robinc.request.ActionType;
import nl.robinc.request.ParameterType;

public class VerenigingTest {
	
	private String[] parameters = {"1"};
	
	public VerenigingTest() {
		this.getNone();
		this.getGebruiker();
		this.getVereniging();
		this.getAandeel();
		this.getAanbieding();
		this.getVerGen();
	}
	
	public void getNone() {
		VerenigingClient verenigingClient = new VerenigingClient(ActionType.GET, ParameterType.NONE, parameters);
		verenigingClient.setOnSucceeded(e -> {
			System.out.println("VerTest None:");
			for(Vereniging vereniging : verenigingClient.getValue()) {
				System.out.println(vereniging);
			}
			System.out.println("------------------");	
		});
		new Thread(verenigingClient).start();
	}
	
	public void getGebruiker() {
		VerenigingClient verenigingClient = new VerenigingClient(ActionType.GET, ParameterType.GEBRUIKER, parameters);
		verenigingClient.setOnSucceeded(e -> {
			System.out.println("VerTest Gebruiker:");
			for(Vereniging vereniging : verenigingClient.getValue()) {
				System.out.println(vereniging);
			}
			System.out.println("------------------");	
		});
		new Thread(verenigingClient).start();
	}

	public void getVereniging() {
		VerenigingClient verenigingClient = new VerenigingClient(ActionType.GET, ParameterType.VERENIGING, parameters);
		verenigingClient.setOnSucceeded(e -> {
			System.out.println("VerTest Vereniging:");
			for(Vereniging vereniging : verenigingClient.getValue()) {
				System.out.println(vereniging);
			}
			System.out.println("------------------");	
		});
		new Thread(verenigingClient).start();
	}
	
	public void getAandeel() {
		VerenigingClient verenigingClient = new VerenigingClient(ActionType.GET, ParameterType.AANDEEL, parameters);
		verenigingClient.setOnSucceeded(e -> {
			System.out.println("VerTest Aandeel:");
			for(Vereniging vereniging : verenigingClient.getValue()) {
				System.out.println(vereniging);
			}
			System.out.println("------------------");	
		});
		new Thread(verenigingClient).start();
	}
	
	public void getAanbieding() {
		VerenigingClient verenigingClient = new VerenigingClient(ActionType.GET, ParameterType.AANBIEDING, parameters);
		verenigingClient.setOnSucceeded(e -> {
			System.out.println("VerTest Aanbieding:");
			for(Vereniging vereniging : verenigingClient.getValue()) {
				System.out.println(vereniging);
			}
			System.out.println("------------------");	
		});
		new Thread(verenigingClient).start();
	}
	
	public void getVerGen() {
		VerenigingClient verenigingClient = new VerenigingClient(ActionType.GET, ParameterType.VERGEN, parameters);
		verenigingClient.setOnSucceeded(e -> {
			System.out.println("VerTest VerGen:");
			for(Vereniging vereniging : verenigingClient.getValue()) {
				System.out.println(vereniging);
			}
			System.out.println("------------------");	
		});
		new Thread(verenigingClient).start();
	}

	public void add() {
	}
	
	public void remove() {
	}
}
