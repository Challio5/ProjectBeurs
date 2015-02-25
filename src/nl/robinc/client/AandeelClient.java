package nl.robinc.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import nl.robinc.model.Aandeel;
import nl.robinc.model.Gebruiker;
import nl.robinc.model.Vereniging;
import nl.robinc.request.ActionType;
import nl.robinc.request.ModelType;
import nl.robinc.request.ParameterType;

public class AandeelClient extends Task<ObservableList<Aandeel>> {

	private ModelType dataType;
	private ActionType actionType;
	private ParameterType parameterType;
	
	private String[] parameters;

	public AandeelClient(ActionType action,
			ParameterType parameterType, String[] parameters) {
		this.dataType = ModelType.AANDEEL;
		this.actionType = action;
		this.parameterType = parameterType;
		this.parameters = parameters;
	}
	
	@Override
	protected ObservableList<Aandeel> call() throws Exception {
		ObservableList<Aandeel> aandeelLijst = FXCollections.observableArrayList();
		
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
				for(int i = 0; i < (attributes.length / 9); i++) {
					aandeelLijst.add(new Aandeel(Integer.parseInt(attributes[0 + i * 9]),
							new Gebruiker(Integer.parseInt(attributes[1 + i * 9]), attributes[2 + i * 9], 
									attributes[3 + i * 9], attributes[4 + i * 9], Double.parseDouble(attributes[5 + i * 9])),
							new Vereniging(Integer.parseInt(attributes[6 + i * 9]), attributes[7 + i * 9]),
							Integer.parseInt(attributes[8 + i * 9])));
				}
			}
			
			// System.out.println("CLIENT Ontvangen: " + aandeel);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return aandeelLijst;
	}

}
