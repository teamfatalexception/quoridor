package quoridorFE;


import java.util.concurrent.CountDownLatch;

// Main import
import javafx.application.Application;
import javafx.event.ActionEvent;
//Imports for mouse events
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
// Import Panes
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.util.ArrayList;

/**
* BEFORE YOU START
* The GUI should replicate the following google drawing found here:
* https://docs.google.com/drawings/d/1RK_2RZ2UmnLUQSSWyXzRwYLT4PVYZUkyu5Kdvs8xISQ/edit?usp=sharing
*/


public class Viewer extends Application {

	public static final CountDownLatch latch = new CountDownLatch(1);
	public static Viewer viewer = null;
	private QuoridorBoard board;
	
	private BorderPane theBorderPane;

	private static ArrayList<Tile> tilesArray = new ArrayList<Tile>();
	
	public Viewer() {
		viewerStartUp(this);
	}
	
	public static void viewerStartUp(Viewer v) {
		viewer = v;
		latch.countDown();
	}
	
	public static Viewer waitForViewerStartUp() {
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return viewer;
	}
	
	public void testTheReference() {
		VBox vbox = new VBox(10);
		vbox.getChildren().addAll(new Button("Cut"), new Button("Copy"), new Button("Paste"));
		this.theBorderPane.setLeft(vbox);
		
	}
	
	// This will be commented out eventually because it should be launched 
	// from Client.java once, then updated as needed.
    public static void main(String[] args) {
        Application.launch(args);
    } 

    
	public void setBoard(QuoridorBoard board) {
		this.board = board;
	}  
	
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
	 * Alernative way to draw the center
	 */
	public Pane drawCenterWithPane () {

		// TODO: Display the coordinates of the tile on click
		// TODO: Display the coordinates of the wall on click
		// TODO: Update the sizes of the tiles to be 100 x 100 -- update coords of 25x25 to 100x100 and update translations
		// TODO: Make the columns and rows smaller
		// TODO: Remove code that is not being used (pTile, commented out code)

		Pane thePane = new Pane();

		// May need to alter to be 1 to 10
		for (int row = 0; row < 9; row++) {
			for (int column = 0; column < 9; column++) {

				// Create a new tile
				Tile tile = new Tile(row, column);

				// setRow and column here instead? then modify the constructor? not sure the best way to handle this.
				// tile.setRow(row);
				// tile.setColumn(column);

				// Add the tile to an array
				tilesArray.add(tile);

				// Translate the X and Y, drawing another tile
				tile.setTranslateX(column * 50);
				tile.setTranslateY(row * 50);

				// Add the tile to the board
				thePane.getChildren().add(tile);

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

	    Scene scene = new Scene(theBorderPane);

	    // Add a stylesheet to the scene
	    scene.getStylesheets().add("application/ViewerStyle.css");
	    
	    // Set the scene for the stage
	    theStage.setScene(scene);

		// Make the stage viewable. This is called last in the function
		theStage.show();
	}

	/**
	* Draw the board before start() is called
	*/
	@Override
	public void init() {

		theBorderPane = new BorderPane();

	    theBorderPane.setTop(drawTop());
	    theBorderPane.setBottom(drawBottom());
	    theBorderPane.setRight(drawRight());
	    theBorderPane.setLeft(drawLeft());      // Comment this out and it will draw andrews left from within client
	    theBorderPane.setCenter(drawCenterWithPane());

	    // Master control for the window size
	    theBorderPane.setPrefSize(1000, 1000);	// Width X Height

	    // TODO: Get the elements of the top, getTop returns a node
	    // theBorderPane.getTop().getChildren();

	} 


	/**
	 * Tile class for creating a square on the grid
	 */
	private class Tile extends StackPane {

		int theRow;
		int theColumn;
		
		private Circle circle = new Circle();

		public Tile(int row, int column) {

			this.theRow = row;
			this.theColumn = column;

			System.out.println("Initial tile drawn at (" + this.theRow + "," + this.theColumn + ")");

			// TODO: Add tile to data structures, then on click should return the proper coordinates

			// TODO: Checking if a circle is on the tile means there
			// TODO: Place players on the board on startup (2 or 4 players)

			// Create a new rectangle for the grid
			Rectangle border = new Rectangle (25,25);
			
			// Make the tile transparent (white)
			border.setFill(Color.GREEN);

			// Set the line color of the tiles to black
			border.setStroke(Color.TRANSPARENT);

			getChildren().addAll(border, circle);

			// On mouse click, draw an X on the tile
			setOnMouseClicked(event -> {
				if(event.getButton() == MouseButton.PRIMARY) {

					// Draw a small circle on the tile (this will eventually be player)
					drawCircle();

					// Print the tile coordinates through the array (currently doesn't work)
					/*for (Tile theTile : tilesArray) {
						System.out.println("Tile placed at (" + this.getRow() + "," + this.getColumn() + ")");
					} */

					System.out.println("Tile placed at (" + this.getRow() + "," + this.getColumn() + ")");
				}
			});
		}

		// These were the getters and setters I was working with
		public void setRow(int theRow) {
			this.theRow = theRow;
		}

		public void setColumn(int theColumn) {
			this.theColumn = theColumn;
		}

		public int getRow() {
			return theRow;
		}

		public int getColumn() {
			return theColumn;
		}

		private void drawCircle() {
			circle.setCenterX(50.0f);
			circle.setCenterY(50.0f);
			circle.setRadius(5.0f);
		}
	}

	private class Wall extends StackPane {

		// TODO: Set an identifier to access by wall (x,y,h) (x,y,v)

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
					
					System.out.println("Clicked a wall at coordinate (x, y, orientation)");

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