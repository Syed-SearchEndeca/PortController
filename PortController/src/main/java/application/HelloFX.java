package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class HelloFX extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/Sample.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
			Image img=new Image(getClass().getResource("/JavaFXv3.png").toURI().toString());
			primaryStage.getIcons().add(img);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Port Kill Application");
			primaryStage.setHeight(963);
			primaryStage.setWidth(720);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
