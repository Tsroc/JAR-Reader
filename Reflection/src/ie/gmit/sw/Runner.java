package ie.gmit.sw;

import javafx.application.*;

/**
 * Runner class.
 * 
 * @author Eoin Wilkie 
 * 
 */
public class Runner {
	public static void main(String[] args) {
		System.out.println("[INFO] Launching GUI...");
		Application.launch(AppWindow.class, args);
	}
}
