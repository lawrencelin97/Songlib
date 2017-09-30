package songlib.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import songlib.view.SonglibController;

public class SonglibApp extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/songlib/view/Songlib.fxml"));
		
		BorderPane root = (BorderPane)loader.load();
		
		SonglibController SonglibController = 
		         loader.getController();
		      SonglibController.start(primaryStage);
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Songlib");
		primaryStage.setResizable(false);  
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
