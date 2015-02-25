package nl.robinc.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javafx.collections.ObservableList;
import nl.robinc.database.dao.VerenigingDao;
import nl.robinc.model.Vereniging;
import nl.robinc.request.ActionType;
import nl.robinc.request.ModelType;
import nl.robinc.request.ParameterType;

public class ServerVereniging implements Runnable {
	
	private Socket socket;
	
	private BufferedReader reader;
	private DataOutputStream writer;
	
	private VerenigingDao dao;
	
	public ServerVereniging(Socket socket, BufferedReader reader, DataOutputStream writer) {
		this.socket = socket;
		
		this.reader = reader;
		this.writer = writer;
		
		dao = new VerenigingDao();	
	}
	
	@Override
	public void run() {
	
		try {
			ActionType actionType = ActionType.valueOf(reader.readLine());
			ParameterType parameterType = ParameterType.valueOf(reader.readLine());
			
			String message = reader.readLine();
			String[] parameters = message.split("\\|");
			
			// System.out.println("SERVER verenigingrequest ontvangen: " + actionType +
			//		parameterType + message);
	
			switch (actionType) {
			case GET:
				switch(parameterType) {
				case NONE:
					ObservableList<Vereniging> verenigingLijst = dao.getVereniging();
					for(Vereniging vereniging : verenigingLijst) {
						String reply = this.generateVerenigingString(vereniging) + '|';
						writer.writeBytes(reply);
					}
					break;
				case GEBRUIKER:
					writer.writeBytes("ParErr|Gebruiker");
					break;
				case VERENIGING:
					int verenigingsnaam = Integer.parseInt(parameters[0]);
					Vereniging vereniging = dao.getVereniging(verenigingsnaam);
					String reply = this.generateVerenigingString(vereniging);
					writer.writeBytes(reply);
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
				Vereniging vereniging1 = new Vereniging(Integer.parseInt(parameters[0]), parameters[1]);
				dao.addVereniging(vereniging1);
				
				// Reply
				String reply1 = "Okay|" + ModelType.VERENIGING + '|' + ActionType.ADD;
				writer.writeBytes(reply1);
			break;
			
			case REMOVE:
				dao.removeVereniging(Integer.parseInt(parameters[0]));
				
				// Reply
				String reply2 = "Okay|" + ModelType.VERENIGING + '|' + ActionType.REMOVE;
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
	
	private String generateVerenigingString(Vereniging vereniging) {
		String verenigingString = "" +
				vereniging.getPRIMARYKEY() + '|' +
				vereniging.getNaam();
		
		return verenigingString;
	}
}
