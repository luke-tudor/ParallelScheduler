package scheduler.visualisation;



import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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
	private Text _sceneTitle;
	private Label _procText;
	private Label _outputText;
	private Text _numOfProc;
	private Text _outputFile;
	private ProgressBar _pb;
	private Label _progressBarText;
	
	private int _numThreads;
	private String _outputName;
	private int _totalNodes;
	private TreeNode _currentTreeNode;
	private int _numOfProcessors;
	
	private Scheduler _s;
	
	private Timer t;
	/**
	 *  This method is run first when application.launch() is
	 *  called, and will initialise the fields of the application.
	 *  
	 *  also initialises the timer object, which controls when 
	 *  the window is updated.
	 * 
	 */
	@Override
	public void init() {
		
		
		_s = Scheduler.getInstance();

		// Commented out these due to error
		// initialise required fields to avoid null pointers
		//_numThreads = _s.getNumThreads();
		//_outputName = _s.getOuputName();
		//_totalNodes = _s.getNumOfNodes();
		//_numOfProcessors = _s.getNumProc();
		_pb = new ProgressBar(0);
		
		// timer runs the run() method every 20ms
		t = new Timer();
		t.scheduleAtFixedRate(new TimerTask( ) {
			public void run() {
				
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						// set the state of the visual tree node and progress bar
						setTreeNode(_s.getNextTN());
						setProgressBar();
					}
				});
			}
		}, 0, 20);

	}

	/**
	 *  This method is called automatically after the init() method,
	 *  its purpose is to build the stage.
	 */
	@Override
	public void start(Stage primaryStage) {

		_primaryStage = primaryStage;
		_primaryStage.setTitle("Parallel Scheduler");
		_primaryStage.setMinWidth(700);
		_primaryStage.setMinHeight(500);
		
		// initialise root of scene
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
		
		// on exit of the stage, the scheduler algorithm is ended
		_primaryStage.setOnCloseRequest(e -> {
			t.cancel();
			t.purge();
			Platform.exit();
		});
		_primaryStage.show();
	}

	// to remove
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
	
	/**
	 * This method initialises the objects being placed on the 
	 * root GridPane
	 * 
	 * Also binds the width of the progress bar to the width of the
	 * window
	 */
	private void initialiseElements() {

		_sceneTitle = new Text("Parallel Scheduler");
		_procText = new Label("Number of Threads Running: ");
		_outputText = new Label("Output Filename: ");
		_progressBarText = new Label("Progress");
		_pb = new ProgressBar(0);
		_numOfProc = new Text(_numThreads + "");
		_outputFile = new Text(_outputName);
		
		_pb.prefWidthProperty().bind(_grid.widthProperty().subtract(50));
	}

	/**
	 * This method sets IDs for the objects in the root pane, for the
	 * purposes of css styling of the window.
	 */
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
	
	/**
	 * This method clears the root pane and adds the various objects
	 * to the pane. this is called every time the scene refreshes, 
	 * and places updated versions of the objects on the pane.
	 */
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

	/**
	 * This method returns the number of tasks found in a schedule.
	 * It is used for determining the level of the progres bar.
	 * @param schedule
	 * @return number of tasks in schedule
	 */
	private int numOfNodesDone(TreeNode schedule) {
		TreeNode partialSched = schedule;
		int count = 0;
		while (partialSched != null && partialSched.getParent() != null) {
			count++;
			partialSched = partialSched.getParent();
		}
		return count;
	}
	
	/**
	 * This method returns a gridpane which represents an empty schedule,
	 * and is used for initialising the _visualTreeNode field.
	 * 
	 * @return schedule
	 */
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
	
	/**
	 * This method takes a schedule and creates a visual representation
	 * of that schedule. 
	 * 
	 * @param schedule
	 * @return visualisation of the schedule
	 */
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
	

	public void setTreeNode(TreeNode node) {
		_currentTreeNode = node;
		drawSchedule(_currentTreeNode);
		
		addElementsToGrid();
		
	}

	public void setProgressBar() {
		_pb.setProgress((double) numOfNodesDone(_currentTreeNode) / _totalNodes);
	}
}
