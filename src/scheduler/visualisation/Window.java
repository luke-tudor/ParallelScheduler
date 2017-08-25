package scheduler.visualisation;


import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
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
	//	_grid.add(_procText, 1, 1);
	//	_grid.add(_numOfProc, 2, 1);
	//	_grid.add(_outputFile, 2, 2);
	//	_grid.add(_outputText, 1, 2);
	//	_grid.add(_treeNodeText, 1, 3);
	//	_grid.add(_currentNumOfTreeNodes, 2, 3);
		_grid.add(_pb, 0, 6, 3, 1);
		_grid.add(_progressBarText, 0, 5, 3, 1);
	//	_grid.add(_progressBar, 0, 5, 3, 1);
		_grid.add(_visualTreeNode, 0,1,1,4);
		
		GridPane infoPane = new GridPane();
		infoPane.add(_procText, 0, 1);
		infoPane.add(_numOfProc, 1, 1);
		infoPane.add(_outputText, 0, 2);
		infoPane.add(_outputFile, 1, 2);
		infoPane.add(_treeNodeText, 0, 3);
		infoPane.add(_currentNumOfTreeNodes, 1, 3);
		
		VBox paneToMove = new VBox(infoPane);
		paneToMove.setMinWidth(0);
	
		
		//_grid.setGridLinesVisible(true);
		ToggleButton settings = new ToggleButton("Settings");
		_grid.add(settings, 0, 7);
		SplitPane splitPane = new SplitPane();
		splitPane.getItems().addAll(_grid, paneToMove);
		
        DoubleProperty splitPaneDividerPosition = splitPane.getDividers().get(0).positionProperty();

        //update toggle button status if user moves divider:
        splitPaneDividerPosition.addListener((obs, oldPos, newPos) -> 
            settings.setSelected(newPos.doubleValue() < 0.95));
      
        splitPaneDividerPosition.set((splitPane.getWidth() - 210)/splitPane.getWidth());

        settings.setOnAction(event -> {
        	KeyValue end;
            if (settings.isSelected()) {
                end = new KeyValue(splitPaneDividerPosition, (splitPane.getWidth() - 210)/splitPane.getWidth());
            } else {
                end = new KeyValue(splitPaneDividerPosition, 1.0);
            }
            new Timeline(new KeyFrame(Duration.seconds(0.5), end)).play();
        });
        
		_scene = new Scene(splitPane);
		_primaryStage.setScene(_scene);
		_scene.getStylesheets().add(Window.class.getResource("windowStyle.css").toExternalForm());
		
		_primaryStage.show();
		
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
	
	/**
	 * For the gridPane we don't want to override already existing contents of a cell. 
	 * 
	 * @param grid The gridPane to check
	 * @param col the column of the cell we want to check
	 * @param row the row of the cell we want to check
	 * @return true if the cell is empty, false otherwise
	 */
	private Boolean checkGridPaneCellIsEmpty(GridPane grid, int col, int row) {
		for (javafx.scene.Node n : grid.getChildren()) {
			if (GridPane.getColumnIndex(n) == col && GridPane.getRowIndex(n) == row) {
				return false;
			}
		}
		return true;
	}

}
