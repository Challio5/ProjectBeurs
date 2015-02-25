package nl.robinc.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javafx.collections.ObservableList;
import nl.robinc.database.dao.AanbiedingDao;
import nl.robinc.model.Aanbieding;
import nl.robinc.model.Gebruiker;
import nl.robinc.model.Vereniging;
import nl.robinc.request.ActionType;
import nl.robinc.request.ModelType;
import nl.robinc.request.ParameterType;

public class ServerAanbieding implements Runnable{
	
	private Socket socket;
	
	private BufferedReader reader;
	private DataOutputStream writer;
	
	private AanbiedingDao dao;
	
	public ServerAanbieding(Socket socket, BufferedReader reader, DataOutputStream writer) {
		this.socket = socket;
		
		this.reader = reader;
		this.writer = writer;
		
		dao = new AanbiedingDao();
	}
	
	@Override
	public void run() {
	
		try {
			ActionType actionType = ActionType.valueOf(reader.readLine());
			ParameterType parameterType = ParameterType.valueOf(reader.readLine());
			
			String message = reader.readLine();
			String[] parameters = message.split("\\|");
			
			// System.out.println("SERVER Aanbiedingrequest ontvangen: " + actionType +
			//		parameterType + message);
	
			switch (actionType) {
			case GET:
				switch(parameterType) {
				case NONE:
					ObservableList<Aanbieding> aanbiedingLijst = dao.getAanbieding();
					for(Aanbieding aanbieding : aanbiedingLijst) {
						String reply = this.generateAanbiedingString(aanbieding) + '|';
						writer.writeBytes(reply);
					}
					break;
				case GEBRUIKER:
					writer.writeBytes("ParErr|Gebruiker");
					// System.err.println("SERVER Geen gebruiker parameter");
					break;
				case VERENIGING:
					String verenigingsnaam = parameters[0];
					Aanbieding aanbieding = dao.getAanbieding(verenigingsnaam).get(0);
					String reply = this.generateAanbiedingString(aanbieding);
					writer.writeBytes(reply);
					break;
				case AANDEEL:
					writer.writeBytes("ParErr|Aandeel");
					// System.err.println("SERVER Geen aandeel parameter");
					break;
				case AANBIEDING:
					int gebruikersnummer = Integer.parseInt(parameters[0]);
					Aanbieding aanbieding1 = dao.getAanbieding(gebruikersnummer).get(0);
					String reply1 = this.generateAanbiedingString(aanbieding1);
					writer.writeBytes(reply1);
					break;
				case VERGEN:
					writer.writeBytes("ParErr|Vergen");
					// System.err.println("SERVER Geen vereniging/gebruiker parameter");
					break;
				default: 
					System.err.println("SERVER Onbekend parametertype: " + parameterType);
				}
				break;
			
			case ADD:
				Aanbieding aanbieding1 = new Aanbieding(Integer.parseInt(parameters[0]),
						new Gebruiker(Integer.parseInt(parameters[1]), parameters[2], 
								parameters[3], parameters[4], Double.parseDouble(parameters[5])),
						new Vereniging(Integer.parseInt(parameters[6]), parameters[7]),
						Integer.parseInt(parameters[8]),
						Double.parseDouble(parameters[9]));
				dao.addAanbieding(aanbieding1);
				
				// Reply
				String reply1 = "Okay|" + ModelType.AANBIEDING + '|' + ActionType.ADD;
				writer.writeBytes(reply1);
			break;
			
			case REMOVE:
				dao.removeAanbieding(Integer.parseInt(parameters[0]));
				
				// Reply
				String reply2 = "Okay|" + ModelType.AANBIEDING + '|' + ActionType.REMOVE;
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
	
	private String generateAanbiedingString(Aanbieding aanbieding) {
		String aanbiedingString = "" +
				aanbieding.getPRIMARYKEY() + '|' +
				this.generateGebruikerString(aanbieding.getGebruiker()) + '|' +
				this.generateVerenigingString(aanbieding.getVereniging()) + '|' +
				aanbieding.getAantal() + '|' +
				aanbieding.getPrijs();
		
		return aanbiedingString;
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
}
