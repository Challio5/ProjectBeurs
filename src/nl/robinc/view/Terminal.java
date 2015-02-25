package nl.robinc.view;

import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;

public class Terminal extends TitledPane {
	private static TextArea debug;
	
	public Terminal() {
		this.setText("Terminal");
		this.setExpanded(false);
		
		debug = new TextArea();
		
		this.setContent(debug);
	}
	
	public static void println(String text) {
		debug.appendText(text + '\n');
	}
	
	public static void clear() {
		debug.clear();
	}
}
