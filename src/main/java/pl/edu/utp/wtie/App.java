package pl.edu.utp.wtie;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class App extends Application {

	private FXMLLoader loader;
	private ScrollPane scrollPane;
	private Scene scene;
	
	public static Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		App.primaryStage = primaryStage;
		
		loader = new FXMLLoader(getClass().getResource("/fxml/AppScene.fxml"));
		scrollPane = loader.load();
		scene = new Scene(scrollPane);
		
		primaryStage.setScene(scene);
		primaryStage.setX(250);
        primaryStage.setY(50);
		primaryStage.setTitle("Reflections");
		primaryStage.show();
	}
	
	public static void setWindow() {
		App.primaryStage.sizeToScene();
		App.primaryStage.setMinWidth(App.primaryStage.getWidth());
		App.primaryStage.setMaxHeight(App.primaryStage.getHeight());
	}

	public static void main(String[] args) {
		launch(args);
	}
}
