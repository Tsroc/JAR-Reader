package ie.gmit.sw;

import java.io.*;

import ie.gmit.sw.database.Database;
import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

/**
 * JavaFX GUI used to measure metrics of a JAR file.
 * 
 * @author Eoin Wilkie 
 *
 */
public class AppWindow extends Application {
	private TextField txtFile; 
	private boolean jarLoaded = false;
	private Database db;
	private JarReader jr;
	// https://stackoverflow.com/questions/45891247/javafx-add-panels-dynamically
	
	/**
	 * Main entry point for the JavaFX application.
	 */
	@Override
	public void start(Stage stage) throws Exception {
		
		stage.setTitle("GMIT - B.Sc. in Computing (Software Development)");
		stage.setWidth(800);
		stage.setHeight(600);
		
		stage.setOnCloseRequest((e) -> System.exit(0));
		
		VBox box = new VBox();
		box.setPadding(new Insets(10));
		box.setSpacing(8);

		Scene scene = new Scene(box); 
		stage.setScene(scene);

		box.getChildren().add(getFileChooserPane(stage)); 
		box.getChildren().add(getMetrics(stage)); 

		// Display the window
		stage.show();
		stage.centerOnScreen();
	}

	/**
	 * Adds a pane to the main application, 
	 * Allows the user to entry a JAR file which will then be processed. 
	 * 
	 * @param stage The main application.
	 * @return Title pane to be added as a child.
	 */
	private TitledPane getFileChooserPane(Stage stage) {
		VBox panel = new VBox(); 
		txtFile = new TextField(); 

		FileChooser fc = new FileChooser(); 
		fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JAR Files", "*.jar"));

		Button btnOpen = new Button("Select File"); 
		btnOpen.setOnAction(e -> { 
			File f = fc.showOpenDialog(stage);
			txtFile.setText(f.getAbsolutePath());
		});

		Button btnProcess = new Button("Process"); 
		btnProcess.setOnAction(e -> { 
			File f = new File(txtFile.getText());
			System.out.println("[INFO] Processing file " + f.getName());
			
			// When the file is processed, the results should be stored in an instance of MicroStream DB
			try {
				jarLoaded = true;
				jr = new JarReader(f);
				jr.process();
			} catch (IOException | ClassNotFoundException e1) {
				e1.printStackTrace();
			}

		});

		ToolBar tb = new ToolBar(); 
		tb.getItems().add(btnOpen); 
		tb.getItems().add(btnProcess); 

		panel.getChildren().add(txtFile); 
		panel.getChildren().add(tb);

		TitledPane tp = new TitledPane("Select File to Process", panel); 
		tp.setCollapsible(false);
		return tp;
	}

	/**
	 * Adds a pane to the main application, 
	 * Allows the user to perform the following metric tests on the processed JAR file,
	 * Number of classes, Weighted methods per class, Number of children in tree. 
	 * 
	 * @param stage The main application.
	 * @return Title pane to be added as a child.
	 */
	private TitledPane getMetrics(Stage stage) {
		db = new Database();
		VBox panel = new VBox(); 		
		ToolBar tb = new ToolBar(); 
		Label label = new Label();
		
		Button btnCLS = new Button("CLS"); 
		btnCLS.setOnAction(e -> {
			if(jarLoaded)
				label.setText(String.format("Number of classes: %s", db.queryCLS()));
			else
				label.setText("Please process a JAR file to perform mood metric tests.");
		});

		Button btnWMC= new Button("WMC"); 
		btnWMC.setOnAction(e -> {
			if(jarLoaded)
				label.setText(String.format("Weighted Methods per Class...\n%s", db.queryWMC()));
			else
				label.setText("Please process a JAR file to perform mood metric tests.");
		});

		Button btnNOC = new Button("NOC"); 
		btnNOC.setOnAction(e -> {
			if(jarLoaded)
				label.setText(String.format("Number of Children in Tree...\n%s", db.queryNOC()));
			else
				label.setText("Please process a JAR file to perform mood metric tests.");
		});

		tb.getItems().add(btnCLS); 
		tb.getItems().add(btnWMC); 
		tb.getItems().add(btnNOC); 
		panel.getChildren().add(tb);
		panel.getChildren().add(label);

		TitledPane tp = new TitledPane("Metric", panel); 
		tp.setCollapsible(false);
		return tp;
		
	}

}