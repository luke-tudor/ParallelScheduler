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
import javafx.scene.layout.Border;
import scheduler.structures.Edge;
import scheduler.structures.Graph;
import scheduler.structures.Node;
import scheduler.structures.TreeNode;

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

		Node nodeA = new Node("A", 2);
		Node nodeB = new Node("B", 3);
		Node nodeC = new Node("C", 3);
		Node nodeD = new Node("D", 2);
		Edge edge1 = new Edge(nodeA, nodeB, 1);
		Edge edge2 = new Edge(nodeA, nodeC, 1);
		Edge edge3 = new Edge(nodeB, nodeD, 1);
		Edge edge4 = new Edge(nodeC, nodeD, 1);	
		Graph graph = new Graph("simple");
		graph.addNode(nodeA);
		graph.addNode(nodeB);
		graph.addNode(nodeC);
		graph.addNode(nodeD);
		graph.addEdge(edge1);
		graph.addEdge(edge2);
		graph.addEdge(edge3);
		graph.addEdge(edge4);
		TreeNode t1 = new TreeNode();
		TreeNode t2 = new TreeNode(t1, nodeA, 1);
		TreeNode t3 = new TreeNode(t2, nodeB, 1);
		TreeNode t7 = new TreeNode(t3, nodeC, 0);
		
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
		_visualTreeNode = drawSchedule(t7);
		
		_grid.add(_sceneTitle, 0, 0, 3, 1);
		//_grid.add(_visualisation, 0, 1, 1, 4);
		_grid.add(_procText, 1, 1);
		_grid.add(_numOfProc, 2, 1);
		_grid.add(_outputFile, 2, 2);
		_grid.add(_outputText, 1, 2);
		_grid.add(_treeNodeText, 1, 3);
		_grid.add(_currentNumOfTreeNodes, 2, 3);
		_grid.add(_pb, 0, 6, 3, 1);
		_grid.add(_progressBarText, 0, 5, 3, 1);
		_grid.add(_progressBar, 0, 5, 3, 1);
		_grid.add(_visualTreeNode, 0,1,1,4);
		
		//_grid.setGridLinesVisible(true);
		
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
	
	private GridPane drawSchedule(TreeNode schedule) {
		TreeNode partialSched = schedule;
		int numProc = 0;
		//int height = partialSched.getHeight() + partialSched.getNode().getWeight();
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 20 ,20 ,20));
		
		while (partialSched.getParent() != null) {
			if (partialSched.getProcessor() > numProc) {
				numProc = partialSched.getProcessor();
			}
			
			Node node = partialSched.getNode();
			
			Label task = new Label(node.getName());
			task.setMinHeight(50*node.getWeight());
			task.setMinWidth(100);
			
			task.setId("task");
			
			grid.add(task, partialSched.getProcessor(), partialSched.getStartTime() + 1 , 1, node.getWeight());
			
			partialSched = partialSched.getParent();
		}
		
		for (int i = 0; i <= numProc; i++) {
			
			Label x = new Label(i+"");
			x.setId("title");
			grid.add(x, i, 0);
		}
		
		grid.setGridLinesVisible(true);
		return grid;
		
	}
	
	private Boolean checkGridPaneCellIsEmpty(GridPane grid, int col, int row) {
		for (javafx.scene.Node n : grid.getChildren()) {
			if (GridPane.getColumnIndex(n) == col && GridPane.getRowIndex(n) == row) {
				return false;
			}
		}
		return true;
	}

}
