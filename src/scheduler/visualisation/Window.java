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
	
	@Override
	public void start(Stage primaryStage) {
		
		_primaryStage = primaryStage;
		
		_primaryStage.setTitle("Parallel Scheduler");
		
		_grid = new GridPane();
		_grid.setAlignment(Pos.CENTER);
		_grid.setHgap(10);
		_grid.setVgap(10);
		_grid.setPadding(new Insets(25,25,25,25));
		
		
		Text sceneTitle = new Text("Running Parallel Scheduler Algorithm..");
		sceneTitle.setId("scene-title");
		
		Rectangle visualisation = new Rectangle(200, 300, Color.RED);
		
		Label procText = new Label("Num of Processors:");
		
		Label outputText = new Label("Output Filename:");
		
		Label treeNodeText = new Label("Schedules considered:");
		
		Rectangle progressBar = new Rectangle(400, 50, Color.BLUE);
		
		
		_grid.add(sceneTitle, 0, 0, 3, 1);
		_grid.add(visualisation, 0, 1, 1, 3);
		_grid.add(procText, 1, 1);
		_grid.add(outputText, 1, 2);
		_grid.add(treeNodeText, 1, 3);
		_grid.add(progressBar, 0, 4, 3, 1);
		
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
