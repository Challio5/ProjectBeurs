package nl.robinc.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javafx.collections.ObservableList;
import nl.robinc.database.dao.GebruikerDao;
import nl.robinc.model.Gebruiker;
import nl.robinc.request.ActionType;
import nl.robinc.request.ModelType;
import nl.robinc.request.ParameterType;

public class ServerGebruiker implements Runnable {
	
	private Socket socket;
	
	private BufferedReader reader;
	private DataOutputStream writer;
	
	private GebruikerDao dao;
	
	public ServerGebruiker(Socket socket, BufferedReader reader, DataOutputStream writer) {
		this.socket = socket;
		
		this.reader = reader;
		this.writer = writer;
		
		dao = new GebruikerDao();
	}
	
	@Override
	public void run() {
	
		try {
			ActionType actionType = ActionType.valueOf(reader.readLine());
			ParameterType parameterType = ParameterType.valueOf(reader.readLine());
			
			String message = reader.readLine();
			String[] parameters = message.split("\\|");
			
			// System.out.println("SERVER gebruikerrequest ontvangen: " + actionType +
			//		parameterType + message);
	
			switch (actionType) {
			case GET:
				switch(parameterType) {
				case NONE:
					ObservableList<Gebruiker> gebruikerLijst = dao.getGebruiker();
					for(Gebruiker gebruiker : gebruikerLijst) {
						String reply = this.generateGebruikerString(gebruiker) + '|';
						writer.writeBytes(reply);
					}
					break;
				case GEBRUIKER:
					int gebruikersnummer = Integer.parseInt(parameters[0]);
					Gebruiker gebruiker1 = dao.getGebruiker(gebruikersnummer);
					String reply1 = this.generateGebruikerString(gebruiker1);
					writer.writeBytes(reply1);
					break;
				case VERENIGING:
					writer.writeBytes("ParErr|Vereniging");
					break;
				case AANDEEL:
					writer.writeBytes("ParErr|Aandeel");
					break;
				case AANBIEDING:
					writer.writeBytes("ParErr|Aanbieding");
					break;
				case VERGEN:
					writer.writeBytes("ParErr|Vergen");
					break;
				default: 
					System.err.println("SERVER Onbekend parametertype: " + parameterType);
				}
				break;
			
			case ADD:
				Gebruiker gebruiker1 = new Gebruiker(Integer.parseInt(parameters[0]),
						parameters[1], parameters[2], parameters[3], 
						Double.parseDouble(parameters[4]));
				dao.addGebruiker(gebruiker1);
				
				// Reply
				String reply1 = "Okay|" + ModelType.GEBRUIKER + '|' + ActionType.ADD;
				writer.writeBytes(reply1);
			break;
			
			case REMOVE:
				dao.removeGebruiker(Integer.parseInt(parameters[0]));
				
				// Reply
				String reply2 = "Okay|" + ModelType.GEBRUIKER + '|' + ActionType.REMOVE;
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
}
