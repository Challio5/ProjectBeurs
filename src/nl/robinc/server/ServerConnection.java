package nl.robinc.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import nl.robinc.request.ModelType;

public class ServerConnection implements Runnable {

	private Socket socket;
	
	public ServerConnection(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		// System.out.println("SERVERCONNECTION Verbinding geaccepteerd");

		try {
			BufferedReader reader = new BufferedReader(
						new InputStreamReader(
							socket.getInputStream()));
			
			DataOutputStream writer = new DataOutputStream(socket.getOutputStream());

			ModelType dataType = ModelType.valueOf(reader.readLine());
				
			// Start nieuwe thread per datatype
			switch(dataType) {
			case GEBRUIKER:
				new Thread(new ServerGebruiker(socket, reader, writer)).start();
				break;
			case VERENIGING:
				new Thread(new ServerVereniging(socket, reader, writer)).start();
				break;
			case AANDEEL:
				new Thread(new ServerAandeel(socket, reader, writer)).start();
				break;
			case AANBIEDING:
				new Thread(new ServerAanbieding(socket, reader, writer)).start();
				break;
			default:
				System.err.println("SERVERCONNECTION Onbekend datatype: " + dataType);
			}
				
			// Sluit de stream
			//reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
