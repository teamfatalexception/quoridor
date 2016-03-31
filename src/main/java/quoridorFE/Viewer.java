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
import javafx.scene.control.ScrollPane;

// Import Panes
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;


import javafx.stage.Stage;
import javafx.scene.shape.*;
import javafx.scene.text.*;

//Imports for mouse events
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.*;
import javafx.event.ActionEvent;


/**
* BEFORE YOU START
* The GUI should replicate the following google drawing found here:
* https://docs.google.com/drawings/d/1RK_2RZ2UmnLUQSSWyXzRwYLT4PVYZUkyu5Kdvs8xISQ/edit?usp=sharing
*/


public class Viewer extends Application {

	// This will be commented out eventually because it should be launched 
	// from Client.java once, then updated as needed.
    public static void main(String[] args) {
      Application.launch(args);
    } 

    // COMMENT BACK!!
	/*private static QuoridorBoard board;

	public static QuoridorBoard getBoard() {
		return board;
	}

	public static void setBoard(QuoridorBoard board) {
		Viewer.board = board;
	}  */
	
    /**
     *  Called to update the state of the board based by the Server.
    **/
    public void refresh(){
		// Need to change states here, placeholder stub mainly.
		System.out.println("REFRESHING");
    }

    /**
	 * Top area - HBox
	 * HBox will lay out children in a single horizontal row
	 */
    private HBox drawTop() {
		
		HBox top = new HBox(1);
		top.setId("top");

		// If you want to nest a VBox within and HBox
		// VBox bottom = new VBox(1);
		// Then nest with 
	    // bottom.getChildren().add(top);

	    // Set properties for the HBox

	    // Create text to be displayed at the top panel
	    Text textTop = new Text("Quoridor - Death Match");
	    top.setStyle("-fx-background-color: #7FFFD4;");

	    // Set text to be blue
	    textTop.setFill(Color.BLUE);

	    // Set the font type and size of the top text
	    textTop.setFont(Font.font("Times New Roman", 22));

	    // Center the text 
	    textTop.setTextAlignment(TextAlignment.CENTER);

	    // Add all elements to the HBox
	    top.getChildren().addAll(textTop);
        
        // Center the top box alignment
        top.setAlignment(Pos.CENTER);

	    return top;
	}

	/**
	 * Bottom Area - Vbox
	 * Vbox will lay out children in a single vertical row 
	 */ 
	private VBox drawBottom() {

	    VBox bottom = new VBox();	// new VBox(num) will determine spacing between buttons
	    bottom.setId("bottom");
	    
	    // Set properties for the VBox
        bottom.setStyle("-fx-background-color: #7FFFD4;");

	    Text textBottom = new Text("Lorem ipsum dolor sit amet, \nconsectetur adipiscing elit, \nsed do eiusmod tempor incididunt ut labore et \ndolore magna aliqua. Ut enim ad \nminim veniam, quis nostrud exercitation ullamco laboris \nnisi ut aliquip ex ea commodo consequat. \nDuis aute irure dolor in reprehenderit in voluptate velit \nesse cillum dolore eu fugiat nulla pariatur. \nExcepteur sint occaecat cupidatat non proident, \nsunt in culpa qui officia deserunt mollit anim id est laborum.");

        // Attempt to display the board coords in the bottom of the text
	    // Text textBottom = new Text(board.getNodeByCoords(1, 2).toString());

	    // Init the left scrollpane.
	    ScrollPane sp = new ScrollPane();
	    sp.setPrefSize(480, 110);
	    sp.setContent(textBottom);

	    // Init the right HBox
	    HBox console = new HBox(30);
	    console.getChildren().addAll(sp);

	    // Default text for players turn
        Text turn_text = new Text("It is no ones turn.");
        turn_text.setFont(Font.font("Times New Roman", 50));

        // Add elements to the right text box
        console.getChildren().addAll(turn_text);

	    // Add all elements to the Vbox
	    bottom.getChildren().addAll(console);

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

		VBox right = new VBox(50);
		right.setId("right");

		// Set properties for the VBox
	    right.setStyle("-fx-background-color: #000088;");
	    
	    right.setSpacing(15);

	    // HBox to display p1's walls
	    HBox p1 = new HBox(15);
	    Text p1Text = new Text("PLAYER 1'S WALLS: 0");
	    p1Text.setId("WallCount");
	    p1.getChildren().addAll(p1Text);

	    // Displaying p2's walls
	    HBox p2 = new HBox(15);
	    Text p2Text = new Text("PLAYER 2'S WALLS: 0");
	    p2Text.setId("WallCount");
	    p2.getChildren().addAll(p2Text);

	    // Displaying p3's walls
	    HBox p3 = new HBox(15);
	    Text p3Text = new Text("PLAYER 3'S WALLS: 0");
	    p3Text.setId("WallCount");
	    p3.getChildren().addAll(p3Text);
	    
	    // Displaying p4's walls
	    HBox p4 = new HBox(15);
	    Text p4Text = new Text("PLAYER 4'S WALLS: 0");
	    p4Text.setId("WallCount");
	    p4.getChildren().addAll(p4Text);
    
	    // Vbox buttons holds all of the buttons
	    VBox buttons = new VBox(50);
	    buttons.setSpacing(10);
	    // Create buttons for movement 
	    Button moveUp = new Button("UP");
	    Button moveDown = new Button("DOWN");
	    Button moveLeft = new Button("LEFT");
	    Button moveRight = new Button("RIGHT");
	    Button kickPlayer = new Button("KICK PLAYER");
	    Button endGame = new Button("END GAME");
	    
	    moveUp.setMaxWidth(Double.MAX_VALUE);
	    moveDown.setMaxWidth(Double.MAX_VALUE);
	    moveLeft.setMaxWidth(Double.MAX_VALUE);
	    moveRight.setMaxWidth(Double.MAX_VALUE);
	    kickPlayer.setMaxWidth(Double.MAX_VALUE);
	    endGame.setMaxWidth(Double.MAX_VALUE);
	    
	    // setting up event that happens when up is pressed
	    moveUp.setOnAction(new EventHandler<ActionEvent>() {
		@Override public void handle(ActionEvent e) {
		// currently, it prints up to the console. eventually, the line below will be
		// used to talk to other parts of our project, informing them that up has been pressed
		System.out.println("WHAT LIES CAN I TELL YOU TODAY?");
		}
	     });
	     
	      // setting up event that happens when down is pressed
	    moveDown.setOnAction(new EventHandler<ActionEvent>() {
		@Override public void handle(ActionEvent e) {
		System.out.println("DON'T VOTE TRUMP, I'LL THINK YOU'RE STUPID");
		}
	     });

	      // setting up event that happens when left is pressed
	    moveLeft.setOnAction(new EventHandler<ActionEvent>() {
		@Override public void handle(ActionEvent e) {
		System.out.println("SOAP IN A SOCK LEAVES NO BRUISES");
		}
	     });
	     
	      // setting up event that happens when right is pressed
	    moveRight.setOnAction(new EventHandler<ActionEvent>() {
		@Override public void handle(ActionEvent e) {
		System.out.println("THE NSA KNOWS WHAT PORN I WATCH");
		}
	     });	
	     // setting up event that happens when kickPlayer is pressed
	    kickPlayer.setOnAction(new EventHandler<ActionEvent>() {
		@Override public void handle(ActionEvent e) {
		System.out.println("THE PUMPING LEMMA");
		}
	     });
	     	      // setting up event that happens when endGame is pressed
	    endGame.setOnAction(new EventHandler<ActionEvent>() {
		@Override public void handle(ActionEvent e) {
		System.out.println("GO, AND HAVE A WONDERFUL DAY");
		}
	     });
	     
	     buttons.getChildren().addAll(moveUp, moveDown, moveLeft, moveRight, kickPlayer, endGame);
	     
	    //TODO: Listener to get the number of walls for each player
	    //TODO: Update player walls on each turn for all players
	    

	    // Add all elements to the VBox
	    right.getChildren().addAll(p1, p2, p3, p4, buttons);
	   
        
        // Center the right pane
		//right.setAlignment(Pos.CENTER);
	    
		return right;
	}

	

	/**
	 * Left Area - Vbox
	 * Vbox will lay out children in a single vertical row 
	 */ 
	public VBox drawLeft() {

	    //TODO: Delete this area eventually if not needed
	    //OR TODO: List of all of our names or other relevant information

		VBox left = new VBox(50);
		left.setId("left");

	    // Set properties for the VBox
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
	public GridPane drawCenterwithGridPane () {

	    // TODO: OnClick, change the tile color itself
	    // TODO: Create a 9x9 grid (or 17x17?)
	    // TODO: Create the walls between the grid squares
	    // TODO: Place players on the board
	    // TODO: Give each player a unique color and number overlayed on top of them
	    // TODO: Place walls on the board
	    // TODO: Place valid walls on the board
	    // TODO: Move players
	    // TODO: onClick of player, hover the valid moves that can be made on the board (see mock)
        // TODO: import jpeg/png files

		// Image imagetest = new Image(getClass().getResourceAsStream("image.png"));
		// ImageView disp = new ImageView(imagetest);
		// Set as backround!

		// Create a GridPane
	    GridPane thePane = new GridPane();

	    // TODO: Create multiple GridPanes to nest on top of each other?
	    // One GridPane for the Tiles
	    // One GridPane for the vertical walls
	    // One GridPane for the horizontal walls

	    // Draw the initial grid
		for (int i = 0; i < 17; i++) {
			for (int j = 0; j < 17; j++) {
			
				// Create a tile for the 17 x 17 board
				Tile tile = new Tile();

				// Add the tiles to the board
				thePane.add(tile, i, j);

				// Removed this line for adding children, goes it in one line with thePane.add()
				// thePane.getChildren().add(tile);

			}
		}

		// Draw the player tiles on the board
		for (int k = 0; k < 17; k+=2) {		  	// column
			for (int l = 0; l < 17; l+=2) {     // row
				
				// Create the player tiles (9x9)
				// PlayerTile pTile = new PlayerTile();

				// Add the player tiles to the board (red tiles)
				// thePane.add(pTile, k, l);	
			}
		}

		// Draw the walls on the board
		// May just handle this in the Tile class 
		// This does not work because creating wall overlaps things incorrectly
		for (int a = 0; a < 17; a++) {		   // column
			for (int b = 0; b < 17; b++) {     // row

				String theOrientation;
				
				// TODO: regexp to match the ((x,y),orientation)
				// TODO: Capture x, y, orientation in capture groups

				theOrientation = "h";

				if (theOrientation.equals("v")) {

					//Wall wall = new Wall(25,75);
					//thePane.add(wall, 0, 0);
				}
					
				else if (theOrientation.equals("h")) {
					//Wall wall = new Wall(75,25);
					//thePane.add(wall, 0, 0);
				
				}
			}
		}
	    	
	    return thePane;
	}

	/** 
	 * Alernative way to draw the center
	 */
	public Pane drawCenterWithPane () {

		// TODO: Display the coordinates of the tile on click
		// TODO: Display the coordinates of the wall on click
		// TODO: Update the sizes of the tiles to be 100 x 100 -- update coords of 25x25 to 100x100 and update translations
		// TODO: Make the columns and rows smaller
		// TODO: Remove code that is not being used (pTile, commented out code)

		Pane thePane = new Pane();

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {

				Tile tile = new Tile();

				// Old way of drawing the tiles
				// Translate the X and Y, drawing another tile
				tile.setTranslateX(j * 50);
				tile.setTranslateY(i * 50);

				thePane.getChildren().add(tile);

				// wall.setTranslateX(s*25);
				// wall.setTranslateY(r*25);

				// Pesudo: Adding multiple children in a list into pane
				// for (Node node: elements)
				// objectname.getChildren().add(node);
			}
		}
		
		// Draw Vertical Walls
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 16; j+=2) {		// Making this +=2 sets the proper row for the vertical
				
				Wall wall;
		
				wall = new Wall(25, 75);	

				wall.setTranslateX(25 + (j * 25));
				wall.setTranslateY(i * 25);

				thePane.getChildren().add(wall);											

			} // END FOR
		} // END FOR 
		
		
		// Draw horiztonal walls
		for (int i = 0; i < 16; i+=2) {
			for (int j = 0; j < 15; j++) {		// Making this +=2 sets the proper row for the vertical

				Wall wall;

				// Length, Height
				wall = new Wall(75, 25);

				wall.setTranslateX(j * 25);
				wall.setTranslateY(25 + (i * 25));

				thePane.getChildren().add(wall);

			} // END FOR
		} // END FOR 
		
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
	    // FlowPane flowRoot = new FlowPane();
	   
	    // BorderPane allows for you to create multiple areas on the window (top, bottom, left, right, center)
	    BorderPane theBorderPane = new BorderPane();

	    // Call functions to draw each area of the GUI
	    // These can only take one function (that functions will return the node)
	    theBorderPane.setTop(drawTop());
	    theBorderPane.setBottom(drawBottom());
	    theBorderPane.setRight(drawRight());
	    theBorderPane.setLeft(drawLeft());
	    theBorderPane.setCenter(drawCenterWithPane());

	    // This is the master control for the window size 
	    theBorderPane.setPrefSize(1000, 1000);	// Width X Height

	    // The code on this line sets a FlowPane so that when you resize the window, the elements all stay in place
	    // Without this, resizing the window causes overlapping of the elements (but does not matter because resize is set to false)
	    // flowRoot.getChildren().addAll(theBorderPane);

	    Scene scene = new Scene(theBorderPane);	// change back to (flowRoot, size x size) so that it does not overlap
	    scene.getStylesheets().add("application/ViewerStyle.css");
	    
	    // Set the scene for the stage
	    theStage.setScene(scene);
		
		// Do not allow the player to resize the main window
		// theStage.setResizable(false);

		// Make the stage viewable. This is called last in the function
		theStage.show();
	}

	/**
	 * Tile class for creating a square on the grid
	 */
	private class Tile extends StackPane {
		
		private Circle circle = new Circle();

		// I was trying to handle the wall within the tile class so that I would not
		// need the Wall class, and instead it could handle it here.
		// I'm still not sure the best way to do it because it can be done through both
		private Rectangle wall = new Rectangle();

		public Tile() {

			// Create a new rectangle for the grid
			Rectangle border = new Rectangle (25,25);
			
			// Make the tile transparent (white)
			border.setFill(Color.GREEN);

			// Set the line color of the tiles to black
			border.setStroke(Color.TRANSPARENT);

			// Align elements within the tile to be centered
			// setAlignment(Pos.CENTER);

			getChildren().addAll(border, circle, wall);

			// On mouse click, draw an X on the tile
			setOnMouseClicked(event -> {
				if(event.getButton() == MouseButton.PRIMARY) {
					drawCircle();
					System.out.println("Clicked a tile at coordinate X, Y!");
					// TODO: Display the tile coords in terminal
				}
			});
		}

		private void drawCircle() {
			circle.setCenterX(50.0f);
			circle.setCenterY(50.0f);
			circle.setRadius(5.0f);
		}
	}

	private class Wall extends StackPane {

		public Wall(int wallLength, int wallHeight) {

			Rectangle theWall = new Rectangle(wallLength, wallHeight);

			// Set properties for the walls
			theWall.setFill(null);

			// Change this back to black if you need to see 
			theWall.setStroke(Color.TRANSPARENT);	

			// Add all the nodes to the object
			getChildren().addAll(theWall);

	        // On mouse click, update the color
			setOnMouseClicked(event -> {
				if(event.getButton() == MouseButton.PRIMARY) {
					
					System.out.println("Clicked a wall!");

					// IF (the wall color is red) theWall.getFill()
						// wall placed here!

					theWall.setFill(Color.RED);
    				theWall.setStroke(Color.RED);
					
        			//theWall.isEmpty == false
				}
		    });
        }
	}
}