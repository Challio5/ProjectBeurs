package nl.robinc.server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server implements Runnable {
	
	// Serversocket voor binnenkomende connecties
	private ServerSocket server;
	
	// Constructor
	public Server() {		
		try {
			server = new ServerSocket(50000);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Start nieuwe threads voor het asynchroon afhandelen van requests
	@Override
	public void run() {
		while(true) {
			try {
				new Thread(new ServerConnection(server.accept())).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// Stopt de server
	public void close() {
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}