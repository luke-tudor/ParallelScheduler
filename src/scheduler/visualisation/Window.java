package scheduler.visualisation;


import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import scheduler.Scheduler;
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
	private GridPane _visualTreeNode;
	private GridPane _infoPane;
	private Text _sceneTitle;
	private Label _procText;
	private Label _outputText;
	private Text _numOfProc;
	private Text _outputFile;
	private Text _currentNumOfTreeNodes;
	private ProgressBar _pb;
	private Label _progressBarText;
	private ToggleButton _settings;
	private SplitPane _splitPane;
	private VBox _paneToMove;
	private ProgressBar _progressBar;
	
	private int _numThreads = 0;
	private String _outputName = "null";
	private int _treeNodeNum = 0;
	private int _totalNodes = 0;
	private TreeNode _currentTreeNode;
	private int _numOfProcessors;
	
	private Scheduler _s;
	
	private Timer t;
	/**
	 *  This method is run first when application.launch() is
	 *  called, and will initialise the fields of the application.
	 * 
	 */
	@Override
	public void init() {
		/*
		 * Initialise values for:
		 * 	- 	_numThreads
		 * 	-	_outputName
		 *  -	total num of nodes
		 * 
		 * 	by calling new public methods in related classes
		 */
		
		_s = Scheduler.getInstance();
		
		_numThreads = _s.getNumThreads();
		_outputName = _s.getOuputName();
		_totalNodes = _s.getNumOfNodes();
		_numOfProcessors = _s.getNumProc();
		_pb = new ProgressBar(0);
		
		t = new Timer();
		t.scheduleAtFixedRate(new TimerTask( ) {
			public void run() {
				
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
						LocalDateTime now = LocalDateTime.now();
						//System.out.println(dtf.format(now)); 
						
						setTreeNode(_s.getNextTN());
						setProgressBar();
					}
				});
			}
		}, 0, 10);
		
		//WindowTimer w = new WindowTimer(this);
		//t.schedule(w, 0, 30);
	}
	
	@Override
	public void start(Stage primaryStage) {

		TreeNode t7 = createTestTreeNode();
		
		_primaryStage = primaryStage;
		_primaryStage.setTitle("Parallel Scheduler");
		_primaryStage.setMinWidth(700);
		
		_grid = new GridPane();
		_grid.setAlignment(Pos.TOP_LEFT);
		_grid.setHgap(10);
		_grid.setVgap(10);
		_grid.setPadding(new Insets(25,25,25,25));
		
		initialiseElements();
		_visualTreeNode = new GridPane();
		_visualTreeNode.setAlignment(Pos.CENTER);
		_visualTreeNode.setHgap(10);
		_visualTreeNode.setVgap(10);
		_visualTreeNode.setPadding(new Insets(20, 20 ,20 ,20));
		_visualTreeNode = drawDefaultSchedule();
		
		setElementIds();
		
		addElementsToGrid();
		
		_scene = new Scene(_grid);
		
		_primaryStage.setScene(_scene);
		_scene.getStylesheets().add(Window.class.getResource("windowStyle.css").toExternalForm());
		_primaryStage.setOnCloseRequest(e -> {
			t.cancel();
			t.purge();
			Platform.exit();
		});
		_primaryStage.show();
	}

	private TreeNode createTestTreeNode() {
		
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
		
		return t7;
	}
	
	private void initialiseElements() {

		_sceneTitle = new Text("Running Parallel Scheduler Algorithm..");
		_procText = new Label("Num of Threads running:");
		_outputText = new Label("Output Filename:");
		_progressBarText = new Label("Progress Bar:");
		_pb = new ProgressBar(0);
		_numOfProc = new Text(_numThreads + "");
		_outputFile = new Text(_outputName);
		_settings = new ToggleButton("Settings");		
		_infoPane = new GridPane();		
		_paneToMove = new VBox(_infoPane);
		
		_pb.prefWidthProperty().bind(_grid.widthProperty().subtract(50));
	}

	private void setElementIds() {
		_visualTreeNode.setId("Tree-node");
		_sceneTitle.setId("title");
		_procText.setId("proc-text");
		_outputText.setId("output-text");
		_progressBarText.setId("progress-bar-text");
		_pb.setId("pb");
		_numOfProc.setId("num-of-proc");
		_outputFile.setId("output-file");
	}
	
	private void addElementsToGrid() {
		
		_grid.getChildren().clear();
		
		_grid.add(_sceneTitle, 0, 0, 3, 1);
		_grid.add(_pb, 0, 6, 3, 1);
		_grid.add(_progressBarText, 0, 5, 3, 1);
		_grid.add(_visualTreeNode, 0,1,1,4);

		_grid.add(_procText, 1, 1);
		_grid.add(_numOfProc, 1, 2);
		_grid.add(_outputText, 1, 3);
		_grid.add(_outputFile, 1, 4);
	}

	private int numOfNodesDone(TreeNode schedule) {
		TreeNode partialSched = schedule;
		int count = 0;
		while (partialSched != null && partialSched.getParent() != null) {
			count++;
			partialSched = partialSched.getParent();
		}
		return count;
	}
	
	private GridPane drawDefaultSchedule() {
		GridPane grid = new GridPane();
		
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 20 ,20 ,20));
		
		for (int i = 0; i <= _numOfProcessors; i++) {
			
			Label x = new Label("p"+i);
			x.setMaxWidth(100);
			x.setId("tree-node-title");
			grid.add(x, i, 0);
		}
		
		grid.setMinHeight(300);
		
		return grid;
		
	}
	
	private GridPane drawSchedule(TreeNode schedule) {
		TreeNode partialSched = schedule;
		_visualTreeNode.getChildren().clear();
		
		while (partialSched != null && partialSched.getNode() != null) {
			
			Node node = partialSched.getNode();
			
			Label task = new Label(node.getName());
			task.setMinHeight(5*node.getWeight());
			task.setMinWidth(50);
			
			task.setId("task");
			
			_visualTreeNode.add(task, partialSched.getProcessor(), partialSched.getStartTime() + 1 , 1, node.getWeight());
			
			partialSched = partialSched.getParent();
		}
		
		for (int i = 0; i < _numOfProcessors; i++) {
			
			Label x = new Label("p"+i);
			x.setId("tree-node-title");
			_visualTreeNode.add(x, i, 0);
		}
		
		_visualTreeNode.setGridLinesVisible(true);
		return _visualTreeNode;
		
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

	public void setTreeNode(TreeNode node) {
		_currentTreeNode = node;
		drawSchedule(_currentTreeNode);
		
		addElementsToGrid();
		
	}
	
	public void setNumOfTreeNodes(int num) {
		_treeNodeNum = num;
	}
	
	public void setProgressBar() {
		_pb.setProgress((double) numOfNodesDone(_currentTreeNode) / _totalNodes);
	}
}
