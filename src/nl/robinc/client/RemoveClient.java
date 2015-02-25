package nl.robinc.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import nl.robinc.request.ActionType;
import nl.robinc.request.ModelType;
import nl.robinc.request.ParameterType;

public class RemoveClient implements Runnable{

	private int key;
	
	private ModelType dataType;
	private ActionType actionType;
	
	public RemoveClient(ModelType dataType, int key) {
		this.key = key;
		
		this.dataType = dataType;
		this.actionType = ActionType.REMOVE;
	}
	
	@Override
	public void run() {
		try {
			Socket client = new Socket("localhost", 50000);
			
			BufferedReader input = new BufferedReader(
					new InputStreamReader(
						client.getInputStream()));

			DataOutputStream output = new DataOutputStream(
						client.getOutputStream());
			
			String reply1 = "" + dataType + '|' + actionType + '|' + ParameterType.NONE + '|' + key;
			output.writeBytes(reply1);
			System.out.println(input.readLine());
			
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
