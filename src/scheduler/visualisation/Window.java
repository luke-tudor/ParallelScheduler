package scheduler.visualisation;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;

/**
 * The class representing the root of the application, building the
 * primary window.
 * 
 * @author nathan gavin
 * 
 */

public class Window extends Application {
	
	private Stage _primaryStage;
	private Scene _scene;
	private GridPane _grid;
	
	private Rectangle _visualisation;
	private GridPane _visualTreeNode;
	private Text _sceneTitle;
	private Label _procText;
	private Label _outputText;
	private Label _treeNodeText;
	private Rectangle _progressBar;
	private Text _numOfProc;
	private Text _outputFile;
	private Text _currentNumOfTreeNodes;
	private ProgressBar _pb;
	private Label _progressBarText;
	
	private int _numProc = 0;
	private String _outputName = "null";
	private int _treeNodeNum = 0;
	
	@Override
	public void init() {
		/*
		 * Initialise values for:
		 * 	- 	_numProc
		 * 	-	_outputName
		 * 
		 * 	by calling new public methods in related classes
		 */
	}
	
	@Override
	public void start(Stage primaryStage) {

		_primaryStage = primaryStage;
		_primaryStage.setTitle("Parallel Scheduler");
		
		_grid = new GridPane();
		_grid.setAlignment(Pos.CENTER);
		_grid.setHgap(10);
		_grid.setVgap(10);
		_grid.setPadding(new Insets(25,25,25,25));
		
		_sceneTitle = new Text("Running Parallel Scheduler Algorithm..");
		_visualisation = new Rectangle(200, 300, Color.RED);
		_procText = new Label("Num of Processors:");
		_outputText = new Label("Output Filename:");
		_treeNodeText = new Label("Schedules considered:");
		_progressBarText = new Label("Progress Bar:");
		_pb = new ProgressBar(0.6);
		_progressBar = new Rectangle(400, 50, Color.BLUE);
		_numOfProc = new Text(_numProc + "");
		_outputFile = new Text(_outputName);
		_currentNumOfTreeNodes = new Text(_treeNodeNum + "");
		
		_grid.add(_sceneTitle, 0, 0, 3, 1);
		_grid.add(_visualisation, 0, 1, 1, 4);
		_grid.add(_procText, 1, 1);
		_grid.add(_numOfProc, 2, 1);
		_grid.add(_outputFile, 2, 2);
		_grid.add(_outputText, 1, 2);
		_grid.add(_treeNodeText, 1, 3);
		_grid.add(_currentNumOfTreeNodes, 2, 3);
		_grid.add(_pb, 0, 6, 3, 1);
		_grid.add(_progressBarText, 0, 5, 3, 1);
		
		_scene = new Scene(_grid);
		_primaryStage.setScene(_scene);
		_scene.getStylesheets().add(Window.class.getResource("windowStyle.css").toExternalForm());
		
		_primaryStage.show();
		
		/*
		primaryStage.setTitle("JavaFX Welcome");
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		Text sceneTitle = new Text("Welcome");
		sceneTitle.setId("welcome-text");
		grid.add(sceneTitle, 0, 0, 2 ,1);
		
		Label userName = new Label("User Name:");
		grid.add(userName, 0,1);
		
		TextField userTextField = new TextField();
		grid.add(userTextField, 1,1);
		
		Label pw = new Label("Password:");
		grid.add(pw, 0, 2);
		
		PasswordField pwBox = new PasswordField();
		grid.add(pwBox, 1, 2);
			
		Button btn = new Button("Sign in");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 1, 4);
		
		final Text actionTarget = new Text();
		actionTarget.setId("actionTarget");
		grid.add(actionTarget, 1,6);
		
		btn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent e) {
				actionTarget.setText("Sign in button pressed");
			}
		});
		
		
		//grid.setGridLinesVisible(true);
		
		Scene scene = new Scene(grid, 300, 275);
		primaryStage.setScene(scene);
		
		scene.getStylesheets().add(Window.class.getResource("style.css").toExternalForm());
		
		primaryStage.show();
		
		*/
	}

}
