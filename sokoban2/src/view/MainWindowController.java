package view;

import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Optional;
import java.util.ResourceBundle;

import common.Level;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.DB.GameResults;

public class MainWindowController extends Observable implements View, Initializable {

	@FXML
	private SokobanDisplayer sd;
	@FXML
	private Label numOfSteps;
	private AnimationTimer timer;
	@FXML
	private Label timeLabel;
	private int seconds;

	public MainWindowController() {
		numOfSteps = new Label("0");
		timeLabel = new Label("0 s");
		seconds = 0;
		timer = new AnimationTimer() {
			private long lastTime = 0;

			@Override
			public void handle(long now) {
				if (lastTime != 0) {
					if (now > lastTime + 1_000_000_000) {
						seconds++;
						timeLabel.setText(Integer.toString(seconds) + " s");
						lastTime = now;
					}
				} else {
					lastTime = now;

				}

			}
		};

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		sd.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> sd.requestFocus());
		sd.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> timer.start());

		sd.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				LinkedList<String> params = new LinkedList<String>();
				params.clear();
				if (event.getCode() == KeyCode.UP) {
					params.add("move");
					params.add("up");
					setChanged();
					notifyObservers(params);
				} else if (event.getCode() == KeyCode.DOWN) {
					params.add("move");
					params.add("down");
					setChanged();
					notifyObservers(params);
				}

				else if (event.getCode() == KeyCode.RIGHT) {
					params.add("move");
					params.add("right");
					setChanged();
					notifyObservers(params);
				} else if (event.getCode() == KeyCode.LEFT) {
					params.add("move");
					params.add("left");
					setChanged();
					notifyObservers(params);
				}
				numOfSteps.setText(Integer.toString(sd.getLevel().getNumOfSteps()));
			}
		});

	}

	public void openFile() {
		numOfSteps.setText("0");
		FileChooser fc = new FileChooser();
		fc.setTitle("open file");
		fc.setInitialDirectory(new File("./resources/levels"));
		fc.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*txt"),
				new ExtensionFilter("Object Files", "*obj"), new ExtensionFilter("XML Files", "*xml"));
		File chosenFile = fc.showOpenDialog(null);
		if (chosenFile != null) {
			LinkedList<String> params = new LinkedList<String>();
			params.add("load");
			params.add(chosenFile.getAbsolutePath());
			setChanged();
			notifyObservers(params);
		}

	}

	public void saveFile() {
		FileChooser fc = new FileChooser();
		fc.setTitle("save file");
		fc.setInitialDirectory(new File("./resources/levels"));
		fc.getExtensionFilters().addAll(new ExtensionFilter("Object Files", "*obj"),
				new ExtensionFilter("XML Files", "*xml"));
		File chosenFile = fc.showSaveDialog(null);
		if (chosenFile != null) {
			LinkedList<String> params = new LinkedList<String>();
			params.add("save");
			params.add(chosenFile.getAbsolutePath());
			setChanged();
			notifyObservers(params);
		}
	}

	public void exit() {
		Platform.exit();
		LinkedList<String> params = new LinkedList<String>();
		params.add("exit");
		timer.stop();
		setChanged();
		notifyObservers(params);

	}

	@Override
	public void display(Level level) {
		if (level.getNumOfBoxOnTarget() == level.getNumOfTargets()){
			timer.stop();
			finishLevel(level);
		}
		
		sd.setLevel(level);
	}

	public void finishLevel(Level level) {
		 {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					TextInputDialog dialog = new TextInputDialog("name");
					dialog.setTitle("Congratulation");
					dialog.setHeaderText("Congratulation you finished this level");
					dialog.setContentText("Please enter your name:");
					Optional<String> result = dialog.showAndWait();
					if (result.isPresent()){
					    level.setFinishTime(seconds);
					    level.setPlayerName(result.get());
					}

				}
			});

		}
	
	}
	
	public void showResults(){
		TableView<GameResults> levelResults;
		TableColumn<GameResults,String> playerCol= new TableColumn<GameResults,String>("Player Name");
		playerCol.setCellValueFactory(new PropertyValueFactory<GameResults,String>("playerName"));
		
	}
}
