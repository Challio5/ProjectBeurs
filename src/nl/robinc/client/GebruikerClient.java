package nl.robinc.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import nl.robinc.model.Gebruiker;
import nl.robinc.request.ActionType;
import nl.robinc.request.ModelType;
import nl.robinc.request.ParameterType;

public class GebruikerClient extends Task<ObservableList<Gebruiker>> {
	
	private ModelType dataType;
	private ActionType actionType;
	private ParameterType parameterType;
	
	private String[] parameters;

	public GebruikerClient(ActionType action,
			ParameterType parameterType, String[] parameters) {
		this.dataType = ModelType.GEBRUIKER;
		this.actionType = action;
		this.parameterType = parameterType;
		this.parameters = parameters;
	}
	
	@Override
	protected ObservableList<Gebruiker> call() throws Exception {
		ObservableList<Gebruiker> gebruikerLijst = FXCollections.observableArrayList();
		
		try {
			// Maak socket en streams aan
			// System.out.println("CLIENT Maak verbinding met server");
			Socket client = new Socket("localhost", 50000);
				
			BufferedReader input = new BufferedReader(
									new InputStreamReader(
										client.getInputStream()));
				
			DataOutputStream output = new DataOutputStream(
										client.getOutputStream());
			
			
			// Voeg parameters toe
			String request = "";
			for(int i = 0; i < parameters.length; i++) {
				request += parameters[i] + '|';
			}
			request += "\r\n";
			
			// Verstuur flags
			output.writeBytes(dataType.name() + "\r\n");
			output.writeBytes(actionType.name() + "\r\n");
			output.writeBytes(parameterType.name() + "\r\n");
			
			// Verstuur request
			output.writeBytes(request);
			// System.out.print("CLIENT Aanvraag voor aanbieding gestuurd: " 
			//		+ dataType + actionType + parameterType + request);
			
			// Ontvang reply
			String message = input.readLine();
			String[] attributes = message.split("\\|");
			// System.out.println("CLIENT reply ontvangen: " + message);
			
			// Sluit de verbinding
			output.close();
			client.close();
			
			// System.out.println("CLIENT Verbinding gesloten");
			
			// Key samen met vereniging, aantal en prijs
			if(!attributes[0].equals("ParErr")) {
				for(int i = 0; i < (attributes.length / 5); i++) {
					gebruikerLijst.add(new Gebruiker(Integer.parseInt(attributes[0 + i * 5]), 
							attributes[1 + i * 5], attributes[2 + i * 5], attributes[3 + i * 5], 
							Double.parseDouble(attributes[4 + i * 5])));
				}
			}
			
			// System.out.println("CLIENT Ontvangen: " + gebruiker);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return gebruikerLijst;
	}
	
}
