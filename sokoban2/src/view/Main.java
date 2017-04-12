package view;



import java.util.List;

import controller.SokobanController;
import controller.server.MyClientHandler;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.MyModel;


public class Main extends Application {
	
	
	

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader fl=new FXMLLoader(getClass().getResource("MainWindow.fxml"));
			BorderPane root = fl.load();

			MainWindowController mwc=fl.getController();
			MyModel myModel=new MyModel();
			SokobanController sc;

			List<String> args=getParameters().getRaw();
			if(args.size()>0&&args.get(0).equals("-server")){
				MyClientHandler cli=new MyClientHandler();
				sc=new SokobanController(myModel, mwc,cli,Integer.parseInt(args.get(1)));
				cli.addObserver(sc);
			}
			else
			 sc=new SokobanController(myModel, mwc);

			myModel.addObserver(sc);
			mwc.addObserver(sc);

			Scene scene = new Scene(root,600,600);
			 scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				
				@Override
				public void handle(WindowEvent we) {
					mwc.exit();
					
				}
			});
			
			primaryStage.show();
					} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
