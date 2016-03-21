package quoridorFE;


// Main import
import javafx.application.Application;
import javafx.stage.Stage;

import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.FontPosture;
import javafx.scene.image.Image;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import javafx.scene.text.*;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;

//Imports for mouse events
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.*;

/**
* BEFORE YOU START
* The GUI should replicate the following google drawing found here:
* https://docs.google.com/drawings/d/1RK_2RZ2UmnLUQSSWyXzRwYLT4PVYZUkyu5Kdvs8xISQ/edit?usp=sharing
*/


public class Viewer extends Application {
	
	// This will be commented out eventually because it should be launched from Client.java once, 
	// then updated as needed.
    public static void main(String[] args) {
        Application.launch(args);
        // TODO: See how John handles the old GUI and launch this that way
    } 

    /**
	 * Top area - HBox
	 * HBox will lay out children in a single horizontal row
	 */
    public HBox drawTop() {
		
		HBox top = new HBox(1);

	    // Set properties for the HBox
	    top.setStyle("-fx-background-color: #00FFFF;");

	    // Create text to be displayed at the top panel
	    Text textTop = new Text("Quoridor - Software Engineering");
	    textTop.setFill(Color.BLUE);
	    textTop.setFont(Font.font("Times New Roman", 18));
	    
	    //TODO: Center the title text

	    // Add all elements to the HBox
	    top.getChildren().addAll(textTop);

	    return top;
	}

	/**
	 * Bottom Area - Vbox
	 * Vbox will lay out children in a single vertical row 
	 */ 
	public VBox drawBottom() {

	    VBox bottom = new VBox(10);	// new VBox(num) will determine spacing between buttons

	    // Set properties for the VBox
	   	bottom.setStyle("-fx-background-color: #7FFFD4;");

	   	// Create buttons for movement 
	    Button moveUp = new Button("UP");
	    Button moveDown = new Button("DOWN");
	    Button moveLeft = new Button("LEFT");
	    Button moveRight = new Button("RIGHT");
	    
	    // Add all elements to the Vbox
	    bottom.getChildren().addAll(moveUp, moveDown, moveLeft, moveRight);

	    //TODO: Create multiple VBoxes within the BottomPane
	    //TODO: Create a Vbox and a column for Exit Game, Kick Player
	    //TODO: Make each button the same size
	    //TODO: Center the buttons on the screen

	    return bottom;
	}

	/**
	 * Right Area - Vbox
	 * Vbox will lay out children in a single vertical row 
	 */ 
	public VBox drawRight () {

		VBox right = new VBox();

		// Set properties for the VBox
	    right.setStyle("-fx-background-color: #6495ED;");
	    right.setSpacing(10);

	    // Create text for the player count of walls
	    Text p1Text = new Text("Player 1's Walls:");
	    p1Text.setFill(Color.RED); 
	    p1Text.setFont(Font.font("Times New Roman", 18));	

	    Text p2Text = new Text("Player 1's Walls:");
	    p2Text.setFill(Color.RED); 
	    p2Text.setFont(Font.font("Times New Roman", 18));

	    Text p3Text = new Text("Player 1's Walls:");
	    p3Text.setFill(Color.RED); 
	    p3Text.setFont(Font.font("Times New Roman", 18));

	    Text p4Text = new Text("Player 1's Walls:");
	    p4Text.setFill(Color.RED); 
	    p4Text.setFont(Font.font("Times New Roman", 18));

	    //TODO: Create a box to hold the number of walls next to the text
	    //TODO: Listener to get the number of players for each player
	    //TODO: Update player walls on each turn for all players

	    // Add all elements to the VBox
	    right.getChildren().addAll(p1Text,p2Text,p3Text,p4Text);
		
		return right;
	}

	/**
	 * Left Area - Vbox
	 * Vbox will lay out children in a single vertical row 
	 */ 
	public VBox drawLeft() {

	    //TODO: Delete this area eventually if not needed
	    //OR TODO: List of all of our names or other relevant information

		VBox left = new VBox();

	    // Set properties for the VBox
	    left.setStyle("-fx-background-color: #DC143C;");
	    left.setSpacing(10);

	    // Create shapes
	    Rectangle rect4 = new Rectangle(5,5,100,100);
	    rect4.setFill(Color.SKYBLUE);
	    Rectangle rect5 = new Rectangle(5,5,100,100);
	    rect5.setFill(Color.BURLYWOOD);
	    Rectangle rect6 = new Rectangle(5,5,100,100);
	    rect6.setFill(Color.GREEN);

	    // Add all elements to the VBox
	    left.getChildren().addAll(rect4,rect5,rect6);

	    return left;
	}

	 /**
	  * Center Area
	  */ 
	public Pane drawCenter () {

	    // TODO: OnClick, change the tile color itself
	    // TODO: Create a 9x9 grid (or 17x17?)
	    // TODO: Create the walls between the grid squares
	    // TODO: Place players on the board
	    // TODO: Give each player a unique color and number overlayed on top of them
	    // TODO: Place walls on the board
	    // TODO: Place valid walls on the board
	    // TODO: Move players
	    // TODO: onClick of player, hover the valid moves that can be made on the board (see mock)


		// May be easier to do a gridPane
	    Pane thePane = new Pane();
	    thePane.setPrefSize(100,100);

	    // TODO: Create outer for 17x17 for the walls
	    // TODO: Create inner for 9x9 for tiles

	    for (int r = 0; r < 17; r++) {
	    	for (int s = 0; s < 17; s++) {
	    		for (int i = 0; i < 9; i++) {
	    			for (int j = 0; j < 9; j++) {
	    		
	    				Tile tile = new Tile();
	    				Wall wall = new Wall();
	    		
	    				// The smaller the number of these, the closer the rectangles are together
	    				tile.setTranslateX(j*50);
	    				tile.setTranslateY(i*50);

	    				wall.setTranslateX(s*20);
	    				wall.setTranslateY(r*20);

	    				// TODO: Translate the walls properly

	    				thePane.getChildren().addAll(tile, wall);

	    				//PSUEDO FOR ADDING
	    				// for (Node node: elements)
	    				// objectname.getChildren().add(node);
	    			}
	    		}
	    	}
	    }



	    return thePane;
	}
    
	/**
	 * Launch the stage of the application.
	 * The stage controls window properties such as title,
	 * icon, visibility, resizability, fullscreen mode, and decorations.
	 */
	@Override
	public void start(Stage theStage) {
		
		// Set the title of the stage
		theStage.setTitle("Quoridor");
		
	    // FlowPane allows for static sizing when the window expands/shrinks
	    FlowPane flowRoot = new FlowPane();
	    
	    // BorderPane allows for you to create multiple areas on the window (top, bottom, left, right, center)
	    BorderPane theBorderPane = new BorderPane();

	    // Call functions to draw each area of the GUI
	    theBorderPane.setTop(drawTop());
	    //TODO: Add multiple parameters to setBottom(drawButtomLeft, drawBottomCenter, drawBottomRight)
	    theBorderPane.setBottom(drawBottom());
	    theBorderPane.setRight(drawRight());
	    theBorderPane.setLeft(drawLeft());
	    theBorderPane.setCenter(drawCenter());

	    // We probably don't even need the FlowPane if we are setting the window to not be resizable, let's discuss that.

	    // Set fixed size window (resizing maintains element positions)
	    theBorderPane.setPrefSize(700, 600);
	    flowRoot.getChildren().addAll(theBorderPane);

	    Scene scene = new Scene(flowRoot, 700, 600);	// change back to theBorderPane instead of root for dyanamic (but overlapping) sizing
	    theStage.setScene(scene);
	    
	    //TODO: One function to handle the stage. 
		
		// Do not allow the player to resize the main window
		theStage.setResizable(false);

		// Make the stage viewable. This is called last in the function
		theStage.show();
	}

	/**
	 * Tile class for creating a square on the grid
	 */
	private class Tile extends StackPane {
		
		private Text text = new Text();
		private Circle circle = new Circle();


		public Tile() {

			// Create a new rectangle for the grid
			Rectangle border = new Rectangle (20,20);
			
			// Make the tile transparent (white)
			border.setFill(null);

			// Set the line color of the tiles to black
			border.setStroke(Color.BLACK);

			// Align elements within the tile to be centered
			setAlignment(Pos.CENTER);

			getChildren().addAll(border, text, circle);

			// On mouse click, draw an X on the tile
			setOnMouseClicked(event -> {
				if(event.getButton() == MouseButton.PRIMARY) {
					drawCircle();
				}
				else if(event.getButton() == MouseButton.SECONDARY) {
					drawX();
				}
			});
		}

		private void drawX() {
			text.setText("X");
		}

		private void drawCircle() {
			circle.setCenterX(100.0f);
			circle.setCenterY(100.0f);
			circle.setRadius(5.0f);
		}
	}

	private class Wall extends StackPane {
		
		public Wall() {

			Rectangle theWall = new Rectangle (5,5);
			
			theWall.setFill(Color.RED);

			theWall.setStroke(Color.RED);

			getChildren().addAll(theWall);

			// TODO: Listener to draw the walls on click
		}
	}

}


