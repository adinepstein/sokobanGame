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
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
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
	private boolean displayFlag = false;
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
		displayFlag = true;
		if (level.getNumOfBoxOnTarget() == level.getNumOfTargets()) {
			timer.stop();
			finishLevel(level);
		}
		
				sd.setLevel(level);
						
	}

	public void finishLevel(Level level) {

		sd.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.UP) {
				} else if (event.getCode() == KeyCode.DOWN) {
				} else if (event.getCode() == KeyCode.RIGHT) {
				} else if (event.getCode() == KeyCode.LEFT) {
				}

			}

		});

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				TextInputDialog dialog = new TextInputDialog("name");
				dialog.setTitle("Congratulation");
				dialog.setHeaderText("Congratulation you finished this level");
				dialog.setContentText("Please enter your name:");
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()) {
					level.setFinishTime(seconds);
					level.setPlayerName(result.get());
					addData();
				}

			}

		});

	}
	
	public void addData(){
		LinkedList<String> params = new LinkedList<String>();
		params.add("addData");
		setChanged();
		notifyObservers(params);
	}

	public void showResults() {
		if (!displayFlag) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Scores");
					alert.setHeaderText(null);
					alert.setContentText("Open a level before checking top scores");

					alert.showAndWait();
				}

			});

		} else {
			LinkedList<String> params = new LinkedList<String>();
			params.add("topScore");
			params.add("levelDisplay");
			params.add("EmptyForNow");
			setChanged();
			notifyObservers(params);
		}
	}

	public void getPlayerQuery(String name) {
		LinkedList<String> params = new LinkedList<String>();
		params.add("topScore");
		params.add("playerDisplay");
		params.add(name);
		setChanged();
		notifyObservers(params);
	}

	@Override
	public void TopScoreDisplay(ObservableList<GameResults> ol, String type) {
		if (type.equals("levelDisplay"))
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					showResults(ol);
				}

			});

		else if (type.equals("playerDisplay"))
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					showPlayerResults(ol);
				}

			});

	}

	@SuppressWarnings("unchecked")
	public void showResults(ObservableList<GameResults> ol) {
		TableView<GameResults> levelResults = new TableView<GameResults>();
		levelResults.setEditable(true);
		TableColumn<GameResults, Integer> idCol = new TableColumn<GameResults, Integer>("Result id");
		idCol.setCellValueFactory(new PropertyValueFactory<GameResults, Integer>("id"));

		TableColumn<GameResults, String> playerCol = new TableColumn<GameResults, String>("Player Name");
		playerCol.setCellValueFactory(new PropertyValueFactory<GameResults, String>("playerName"));
		playerCol.setMinWidth(100);

		TableColumn<GameResults, String> levelCol = new TableColumn<GameResults, String>("Level Name");
		levelCol.setCellValueFactory(new PropertyValueFactory<GameResults, String>("levelName"));

		TableColumn<GameResults, Integer> stepsCol = new TableColumn<GameResults, Integer>("Steps");
		stepsCol.setCellValueFactory(new PropertyValueFactory<GameResults, Integer>("steps"));
		stepsCol.setMinWidth(100);

		TableColumn<GameResults, Integer> timeCol = new TableColumn<GameResults, Integer>("Time");
		timeCol.setCellValueFactory(new PropertyValueFactory<GameResults, Integer>("finishTime"));
		timeCol.setMinWidth(100);
		levelResults.getItems().clear();
		levelResults.setItems(ol);

		levelResults.getColumns().addAll(playerCol, stepsCol, timeCol);
		levelResults.getSortOrder().add(stepsCol);
		levelResults.getSortOrder().add(timeCol);
		levelResults.setRowFactory(tv -> {
			TableRow<GameResults> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
					GameResults clickedRow = row.getItem();
					getPlayerQuery(clickedRow.getPlayerName());
				}

			});
			return row;
		});

		Stage results = new Stage();
		results.setTitle("top scores of level");

		ToolBar tb = new ToolBar();
		Label searchLabel = new Label("Search");
		TextField searchField = new TextField();
		searchField.setPromptText("level or player-then Enter");
		tb.getItems().addAll(searchLabel, searchField);

		searchField.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER))
					search(searchField.getText());
			}
		});

		levelResults.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		VBox vbox = new VBox();
		vbox.getChildren().add(levelResults);
		vbox.autosize();

		BorderPane pane = new BorderPane();
		pane.setTop(tb);
		pane.setCenter(vbox);
		Scene scene = new Scene(pane, 300, 400);
		results.setScene(scene);
		results.show();

	}

	@SuppressWarnings("unchecked")
	public void showPlayerResults(ObservableList<GameResults> ol) {
		TableView<GameResults> playerResults = new TableView<GameResults>();

		TableColumn<GameResults, Integer> idCol = new TableColumn<GameResults, Integer>("Result id");
		idCol.setCellValueFactory(new PropertyValueFactory<GameResults, Integer>("id"));

		TableColumn<GameResults, String> playerCol = new TableColumn<GameResults, String>("Player Name");
		playerCol.setCellValueFactory(new PropertyValueFactory<GameResults, String>("playerName"));

		TableColumn<GameResults, String> levelCol = new TableColumn<GameResults, String>("Level Name");
		levelCol.setCellValueFactory(new PropertyValueFactory<GameResults, String>("levelName"));

		TableColumn<GameResults, Integer> stepsCol = new TableColumn<GameResults, Integer>("Steps");
		stepsCol.setCellValueFactory(new PropertyValueFactory<GameResults, Integer>("steps"));

		TableColumn<GameResults, Integer> timeCol = new TableColumn<GameResults, Integer>("Time");
		timeCol.setCellValueFactory(new PropertyValueFactory<GameResults, Integer>("finishTime"));

		playerResults.getItems().clear();
		playerResults.setItems(ol);
		playerResults.getColumns().addAll(levelCol, stepsCol, timeCol);
		playerResults.getSortOrder().add(stepsCol);
		playerResults.getSortOrder().add(timeCol);
		playerResults.getSortOrder().add(levelCol);

		Stage results = new Stage();
		results.setTitle("top scores of Player ");
		playerResults.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		VBox vBox = new VBox();

		vBox.getChildren().add(playerResults);

		Scene scene = new Scene(vBox, 300, 400);
		results.setScene(scene);
		results.show();

	}

	@SuppressWarnings("unchecked")
	public void showLevelResults(ObservableList<GameResults> ol) {
		TableView<GameResults> playerResults = new TableView<GameResults>();

		TableColumn<GameResults, Integer> idCol = new TableColumn<GameResults, Integer>("Result id");
		idCol.setCellValueFactory(new PropertyValueFactory<GameResults, Integer>("id"));

		TableColumn<GameResults, String> playerCol = new TableColumn<GameResults, String>("Player Name");
		playerCol.setCellValueFactory(new PropertyValueFactory<GameResults, String>("playerName"));

		TableColumn<GameResults, String> levelCol = new TableColumn<GameResults, String>("Level Name");
		levelCol.setCellValueFactory(new PropertyValueFactory<GameResults, String>("levelName"));

		TableColumn<GameResults, Integer> stepsCol = new TableColumn<GameResults, Integer>("Steps");
		stepsCol.setCellValueFactory(new PropertyValueFactory<GameResults, Integer>("steps"));

		TableColumn<GameResults, Integer> timeCol = new TableColumn<GameResults, Integer>("Time");
		timeCol.setCellValueFactory(new PropertyValueFactory<GameResults, Integer>("finishTime"));

		playerResults.getItems().clear();
		playerResults.setItems(ol);
		playerResults.getColumns().addAll(playerCol, stepsCol, timeCol);
		playerResults.getSortOrder().add(stepsCol);
		playerResults.getSortOrder().add(timeCol);
		playerResults.getSortOrder().add(levelCol);

		Stage results = new Stage();
		results.setTitle("top scores of level ");
		playerResults.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		VBox vBox = new VBox();

		vBox.getChildren().add(playerResults);

		Scene scene = new Scene(vBox, 300, 400);
		results.setScene(scene);
		results.show();

	}

	public void search(String dataSearch) {
		LinkedList<String> params = new LinkedList<String>();
		params.add("search");
		params.add(dataSearch);
		setChanged();
		notifyObservers(params);

	}

	@Override
	public void searchResults(ObservableList<GameResults> ol, String searchType) {

		if (searchType.equals("level"))
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					showLevelResults(ol);
				}

			});
			
		else if (searchType.equals("player"))
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					showPlayerResults(ol);
				}

			});
		else{
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Search results");
					alert.setHeaderText(null);
					alert.setContentText("No level or Player are named like your request- TRY AGAIN");

					alert.showAndWait();
				}

			});
			
			
		}
			

	}

}
