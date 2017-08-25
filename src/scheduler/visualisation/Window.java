package scheduler.visualisation;


import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import java.util.Timer;

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
	private GridPane _visualTreeNode;
	private GridPane _infoPane;
	private Text _sceneTitle;
	private Label _procText;
	private Label _outputText;
	private Label _treeNodeText;
	private Text _numOfProc;
	private Text _outputFile;
	private Text _currentNumOfTreeNodes;
	private ProgressBar _pb;
	private Label _progressBarText;
	private ToggleButton _settings;
	private SplitPane _splitPane;
	private VBox _paneToMove;
	
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
		Timer t = new Timer();
		WindowTimer w = new WindowTimer(this);
		//t.schedule(w, 0, 500);
	}
	
	@Override
	public void start(Stage primaryStage) {

		TreeNode t7 = createTestTreeNode();
		
		_primaryStage = primaryStage;
		_primaryStage.setTitle("Parallel Scheduler");
		
		_grid = new GridPane();
		_grid.setAlignment(Pos.CENTER);
		_grid.setHgap(10);
		_grid.setVgap(10);
		_grid.setPadding(new Insets(25,25,25,25));
		
		initialiseElements();
		_visualTreeNode = drawSchedule(t7);
		
		setElementIds();
		
		addElementsToGrid();
	
		_splitPane = new SplitPane();
		_splitPane.getItems().addAll(_grid, _paneToMove);
		
		setUpCollapsablePanel();
		
		_scene = new Scene(_splitPane);
		
		_primaryStage.setScene(_scene);
		_scene.getStylesheets().add(Window.class.getResource("windowStyle.css").toExternalForm());
		_primaryStage.show();
	}
	
	private void setUpCollapsablePanel() {
		

		_paneToMove.setMinWidth(0);

        DoubleProperty splitPaneDividerPosition = _splitPane.getDividers().get(0).positionProperty();

        //update toggle button status if user moves divider:
        splitPaneDividerPosition.addListener((obs, oldPos, newPos) -> 
            _settings.setSelected(newPos.doubleValue() < 0.95));
      
        splitPaneDividerPosition.set((_splitPane.getWidth() - 210)/_splitPane.getWidth());

        _settings.setOnAction(event -> {
        	KeyValue end;
            if (_settings.isSelected()) {
                end = new KeyValue(splitPaneDividerPosition, (_splitPane.getWidth() - 210)/_splitPane.getWidth());
            } else {
                end = new KeyValue(splitPaneDividerPosition, 1.0);
            }
            new Timeline(new KeyFrame(Duration.seconds(0.5), end)).play();
        });
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
		_procText = new Label("Num of Processors:");
		_outputText = new Label("Output Filename:");
		_treeNodeText = new Label("Schedules considered:");
		_progressBarText = new Label("Progress Bar:");
		_pb = new ProgressBar(0.6);
		_numOfProc = new Text(_numProc + "");
		_outputFile = new Text(_outputName);
		_currentNumOfTreeNodes = new Text(_treeNodeNum + "");
		_settings = new ToggleButton("Settings");		
		_infoPane = new GridPane();		
		_paneToMove = new VBox(_infoPane);
	}

	private void setElementIds() {
		_visualTreeNode.setId("Tree-node");
		_sceneTitle.setId("title");
		_procText.setId("proc-text");
		_outputText.setId("output-text");
		_treeNodeText.setId("tree-node-text");
		_progressBarText.setId("progress-bar-text");
		_pb.setId("pb");
		_numOfProc.setId("num-of-proc");
		_outputFile.setId("output-file");
		_currentNumOfTreeNodes.setId("current-num-of-tree-nodes");
	}
	
	private void addElementsToGrid() {
		
		_grid.add(_sceneTitle, 0, 0, 3, 1);
		_grid.add(_pb, 0, 6, 3, 1);
		_grid.add(_progressBarText, 0, 5, 3, 1);
		_grid.add(_visualTreeNode, 0,1,1,4);
		_grid.add(_settings, 0, 7);

		_infoPane.add(_procText, 0, 1);
		_infoPane.add(_numOfProc, 1, 1);
		_infoPane.add(_outputText, 0, 2);
		_infoPane.add(_outputFile, 1, 2);
		_infoPane.add(_treeNodeText, 0, 3);
		_infoPane.add(_currentNumOfTreeNodes, 1, 3);
	}
	
	private GridPane drawSchedule(TreeNode schedule) {
		TreeNode partialSched = schedule;
		int numProc = 0;
		
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
			x.setId("tree-node-title");
			grid.add(x, i, 0);
		}
		
		//grid.setGridLinesVisible(true);
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

	public void setTreeNode(TreeNode node) {
		_visualTreeNode = drawSchedule(node);
	}
	
	public void setNumOfTreeNodes(int num) {
		_treeNodeNum = num;
	}
}
