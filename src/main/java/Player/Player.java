// PLayer object for storing and managing player information.
public class Player {

	// Private fields
	private int ID;  // Which number player he is.
	private String name; // What team made him.
	private int port;
	private int wallsLeft; // How many walls he can still place.
	private int X, Y;

	// Constructor
	public Player(int ID, String name, int port, int wallsLeft, int startingX, int startingY){

	     	this.ID = ID;
	        this.name = name;
	        this.port = port;
	        this.wallsLeft = wallsLeft;
	       	this.X = startingX;
		this.Y = startingY;
	}

	public String getName(){
		return name;
	}

}
