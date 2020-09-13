package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;


public class Main extends Application {
	
	@Override
	public void start(Stage stage) {
		
		try {
			stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/testeGUI.fxml"))));
			stage.setTitle("MolicWorks");
			stage.setMaximized(true);
			stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}
