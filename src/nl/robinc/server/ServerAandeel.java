package nl.robinc.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javafx.collections.ObservableList;
import nl.robinc.database.dao.AandeelDao;
import nl.robinc.model.Aandeel;
import nl.robinc.model.Gebruiker;
import nl.robinc.model.Vereniging;
import nl.robinc.request.ActionType;
import nl.robinc.request.ModelType;
import nl.robinc.request.ParameterType;

public class ServerAandeel implements Runnable {
	
	private Socket socket;
	
	private BufferedReader reader;
	private DataOutputStream writer;
	
	private AandeelDao dao;
	
	public ServerAandeel(Socket socket, BufferedReader reader, DataOutputStream writer) {
		this.socket = socket;
		
		this.reader = reader;
		this.writer = writer;
		
		dao = new AandeelDao();
	}
	
	@Override
	public void run() {
	
		try {
			ActionType actionType = ActionType.valueOf(reader.readLine());
			ParameterType parameterType = ParameterType.valueOf(reader.readLine());
			
			String message = reader.readLine();
			String[] parameters = message.split("\\|");
			
			// System.out.println("SERVER Aandeelrequest ontvangen: " + actionType +
			//		parameterType + message);
	
			switch (actionType) {
			case GET:
				switch(parameterType) {
				case NONE:
					ObservableList<Aandeel> aandeelLijst1 = dao.getAandeel();
					for(Aandeel aandeel : aandeelLijst1) {
						String reply = this.generateAandeelString(aandeel) + '|';
						writer.writeBytes(reply);
					}
					break;
				case GEBRUIKER:
					int gebruikersnummer = Integer.parseInt(parameters[0]);
					ObservableList<Aandeel> aandeelLijst2 = dao.getAandelenVanGebruiker(gebruikersnummer);
					for(Aandeel aandeel : aandeelLijst2) {
						String reply = this.generateAandeelString(aandeel);
						writer.writeBytes(reply);
					}
					break;
				case VERENIGING:
					int verenigingsnummer = Integer.parseInt(parameters[0]);
					ObservableList<Aandeel> aandeelLijst3 = dao.getAandelenVanVereniging(verenigingsnummer);
					for(Aandeel aandeel : aandeelLijst3) {
						String reply = this.generateAandeelString(aandeel);
						writer.writeBytes(reply);
					}
					break;
				case AANDEEL:
					int aandeelnummer = Integer.parseInt(parameters[0]);
					Aandeel aandeel1 = dao.getAandeel(aandeelnummer);
					String reply1 = this.generateAandeelString(aandeel1);
					writer.writeBytes(reply1);
					break;
				case AANBIEDING:
					writer.writeBytes("ParErr|Aanbieding");
					break;
				case VERGEN:
					int gebnummer = Integer.parseInt(parameters[0]);
					int vernummer = Integer.parseInt(parameters[1]);
					ObservableList<Aandeel> aandeelLijst4 = dao.getAandelenVanVerenigingEnGebruiker(vernummer, gebnummer);
					for(Aandeel aandeel : aandeelLijst4) {
						String reply = this.generateAandeelString(aandeel);
						writer.writeBytes(reply);
					}
					break;
				default: 
					System.err.println("SERVER Onbekend parametertype: " + parameterType);
				}
				break;
			
			case ADD:
				Aandeel aandeel1 = new Aandeel(Integer.parseInt(parameters[0]),
						new Gebruiker(Integer.parseInt(parameters[1]), parameters[2], 
								parameters[3], parameters[4], Double.parseDouble(parameters[5])),
						new Vereniging(Integer.parseInt(parameters[6]), parameters[7]),
						Integer.parseInt(parameters[8]));
				dao.addAandeel(aandeel1);
				
				// Reply
				String reply1 = "Okay|" + ModelType.AANDEEL + '|' + ActionType.ADD;
				writer.writeBytes(reply1);
			break;
			
			case REMOVE:
				dao.removeAandeel(Integer.parseInt(parameters[0]));
				
				// Reply
				String reply2 = "Okay|" + ModelType.AANDEEL + '|' + ActionType.REMOVE;
				writer.writeBytes(reply2);
			break;
			
			default:
				System.out.println("SERVER Onbekend actiontype " + actionType);
			}
		
			// Sluit de streams en socket
			reader.close();
			writer.close();
			socket.close();
			
			// System.out.println("SERVER Sluit de verbinding");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String generateGebruikerString(Gebruiker gebruiker) {		
		String gebruikerString = "" +
				gebruiker.getPRIMARYKEY() + '|' + 
				gebruiker.getGebruikersnaam() + '|' + 
				gebruiker.getWachtwoord() + '|' + 
				gebruiker.getNaam() + '|' + 
				gebruiker.getBalans();
		
		return gebruikerString;
	}
	
	private String generateVerenigingString(Vereniging vereniging) {
		String verenigingString = "" +
				vereniging.getPRIMARYKEY() + '|' +
				vereniging.getNaam();
		
		return verenigingString;
	}
	
	private String generateAandeelString(Aandeel aandeel) {
		String aandeelString = "" +
				aandeel.getPRIMARYKEY() + '|' +
				this.generateGebruikerString(aandeel.getGebruiker()) + '|' +
				this.generateVerenigingString(aandeel.getVereniging()) + '|' +
				aandeel.getAantal();
		
		return aandeelString;
	}
}
