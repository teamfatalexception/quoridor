package quoridorFE;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;
/*
 * The Client that can be run both as a console or a GUI
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client  {

    // Declared list of current players, 2 or 4 long until people start gettting kicked.
    private static ArrayList<Player> players = new ArrayList<Player>();
    private static Player currentPlayer;

    // for I/O
    private Scanner IOscannerIn;
    private PrintWriter IOscannerOut;
    private Socket socket;

    // if I use a GUI or not
    private ClientGUI cg;

    // the server and the port
    private String server;
    private int port;
    public static Maze maze;

    // Thread safe semaphore.
    static Semaphore semaphore = new Semaphore(0);

    // Bools for our commandline parameter flags.
    public static boolean automate = true;
    public static boolean text_only = false;
    public static boolean gui_only = false;
    public static int DELAY = 500;
    // This is our EVERYTHING, the board that will hold the players, walls and their board states.
    public static QuoridorBoard board;
    public static ArrayList<Client> clients;
    public static Viewer viewer = null;

    /*
     *
     *  server: the server address
     *  port: the port number
     *  username: the username
     */
    Client(String server, int port) {
            // which calls the common constructor with the GUI set to null
            this(server, port, null);
    }

    /*
     * Constructor call when used from a GUI
     * in console mode the ClientGUI parameter is null
     */
    Client(String server, int port,ClientGUI cg) {
            this.server = server;
            this.port = port;
            this.maze = maze;
            // save if we are in GUI mode or not
            this.cg = cg;
    }

    /*
     * To start the dialog
     */
    public boolean start() {
            // try to connect to the server
            try {
                    socket = new Socket(server, port);
            }

            catch(Exception ec) {
                    display("Error connectiong to server:" + ec);
                    return false;
            }

            String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
            display(msg);
            /* Creating both Data Stream */
            try
            {
                    IOscannerIn  = new Scanner(socket.getInputStream());
                    IOscannerOut = new PrintWriter(socket.getOutputStream(), true);
            }
            catch (IOException eIO) {
                    display("Exception creating new Input/output Streams: " + eIO);
                    return false;
            }

            // creates the Thread to listen from the server 
            new ListenFromServer().start();
          
            return true;
    }

    /*
     * To send a message to the console or the GUI
     */
    private void display(String msg) {
            if(cg == null)
                    System.out.println(msg);      // println in console mode
            else
                    cg.append(msg + "\n");                // append to the ClientGUI JTextArea (or whatever)
    }

    /*
     * To send a message to the server
     */
    void sendMessage(String msg) {
           
                    IOscannerOut.println(msg);
           
    }

    /*
    Semaphore controls
    */



   
    /*
     * When something goes wrong
     * Close the Input/Output streams and disconnect not much to do in the catch clause
     */
    private void disconnect() {
            try { 
                    if(IOscannerIn != null) IOscannerIn.close();
            }
            catch(Exception e) {}
            try {
                    if(IOscannerOut != null) IOscannerOut.close();
            }
            catch(Exception e) {}
            try{
                    if(socket != null) socket.close();
            }
            catch(Exception e) {} 

            // inform the GUI
            if(cg != null)
                    cg.connectionFailed();

    }
    /*
     * To start the Client in console mode use one of the following command
     * > java Client
     * > java Client username
     * > java Client username portNumber
     * > java Client username portNumber serverAddress
     * at the console prompt
     * If the portNumber is not specified 1500 is used
     * If the serverAddress is not specified "localHost" is used
     * If the username is not specified "Anonymous" is used

     * In console mode, if an error occurs the program simply stops
     * when a GUI id used, the GUI is informed of the disconnection
     */

    // Synchronized list to hold team names from threads.
    private static final CopyOnWriteArrayList<String> nameList = new CopyOnWriteArrayList<>();
    
    public static void main(String[] args) throws ClassNotFoundException, IOException {

        //Board to use
        Maze maze;
        //Line to be used for regex
        String line = "";
        //Loop through commands and add them to line
        for (int n = 0; n < args.length; n++){
            line += args[n] + " ";
        }
        for (int i = 1; i < args.length; i++) {
            if(args[i].equals("--delay")) {
                DELAY = Integer.parseInt(args[i+1]);
                break;
            }
        }
        System.out.println("Delay is set to: " + DELAY);
        // Gotta check and see if they sent us any little flags. ;)
        if(line.contains("--auto_off")){
            automate = false;
            System.out.println("        Automate is OFF");
        }
        if(line.contains("--text")){
            text_only = true;
            System.out.println("    Text Only is ON");
        }
        if(line.contains("--gui")){
            gui_only = true;
            System.out.println("    GUI Only is ON");
        }
        //TODO DELAY PARAMETER 

        //Replace all colons in line with whitespace
        String my_line = line.replaceAll(":", " ");

        //Split line up into individual string components
        String[] wordsOfCommandLineParameters = my_line.split("\\s+");

        // default values
        int portNumber = 1500;  //Default port
        String serverAddress = "localhost";  // Default hostname


        //Instantiate several clients to use
        Client client = new Client(serverAddress, portNumber);
        Client client2 = new Client(serverAddress, portNumber);
        Client client3 = new Client(serverAddress, portNumber);
        Client client4 = new Client(serverAddress, portNumber);

        //List for holding all clients
        //ArrayList<Client> 
        clients =  new ArrayList<Client>();

        //TODO change the names of the patterns and matchers to be more descriptive!

        //Patter to use for checking if two players
        //to check against command line parameters
        String pattern = "(.*)(\\s*)(:)(\\s*)(\\d+)(\\s*)(.*)(\\s*)(:)(\\s*)(\\d+)";

        //Pattern to use for checking if four players
        //to check against command line parameters
        String pattern2 = "(.*)(\\s*)(:)(\\s*)(\\d+)(\\s*)(.*)(\\s*)(:)(\\s*)(\\d+)(.*)(\\s*)(:)(\\s*)(\\d+)(\\s*)(.*)(\\s*)(:)(\\s*)(\\d+)";

        //Compile both patterns
        Pattern r = Pattern.compile(pattern);
        Pattern mr = Pattern.compile(pattern2);

        //Create matcher object to check for matching string
        Matcher m = r.matcher(line);
        Matcher m2 = mr.matcher(line);

        // depending of the number of arguments provided we fall through
        //Only runs if there are four players
        if(m2.find()) {
            try {
                serverAddress = wordsOfCommandLineParameters[0];
                portNumber = Integer.parseInt(wordsOfCommandLineParameters[1]);
                players.add(new Player(1, serverAddress, portNumber, 5, 0, 0));

                client = new Client(serverAddress, portNumber);
                serverAddress = wordsOfCommandLineParameters[2];
                portNumber = Integer.parseInt(wordsOfCommandLineParameters[3]);
                players.add(new Player(2, serverAddress, portNumber, 5, 0, 0));

                client2 = new Client(serverAddress, portNumber);
                serverAddress = wordsOfCommandLineParameters[4];
                portNumber = Integer.parseInt(wordsOfCommandLineParameters[5]);
                players.add(new Player(3, serverAddress, portNumber, 5, 0, 0));

                client3 = new Client(serverAddress, portNumber);
                serverAddress = wordsOfCommandLineParameters[6];
                portNumber = Integer.parseInt(wordsOfCommandLineParameters[7]);
                players.add(new Player(4, serverAddress, portNumber, 5, 0, 0));

                client4 = new Client(serverAddress, portNumber);
                clients.add(client);
                clients.add(client2);
                clients.add(client3);
                clients.add(client4);
                //Board to use
                maze = new Maze(9,9, 4);
                // New quoridor board init  --          public QuoridorBoard(Player player1, Player player2, Player player3, Player player4) 
                board = new QuoridorBoard(new Player(1, serverAddress, portNumber, 5, 0, 0), new Player(2, serverAddress, portNumber, 5, 0, 0), new Player(3, serverAddress, portNumber, 5, 0, 0), new Player(4, serverAddress, portNumber, 5, 0, 0));


                // Setup player objects time.
                // Some of these values will change as they become useful, like starting
				//positions for each player, etcc.
                // public Player(int ID, String name, int port, int wallsLeft, int startingX,
				//int startingY){
                //players.add(new Player(1, serverAddress, portNumber, 5, 0, 0));
                currentPlayer = players.get(0);

                // test if we can start the connection to the Server
                // if it failed nothing we can do
                if(!client.start())
                    return;
                if(!client2.start())
                    return;
                if(!client3.start())
                    return;
                if(!client4.start())
                    return;
            } catch(Exception e) {
                System.out.println("Invalid port number.");
                System.out.println("Usage is: > java Client [username] [portNumber] [serverAddress]");
                return;
            }
        } else if(m.find()) {
        	// only runs if there are 2 players
            try {
                serverAddress = wordsOfCommandLineParameters[0];
                portNumber = Integer.parseInt(wordsOfCommandLineParameters[1]);
                //
                players.add(0, new Player(1, serverAddress, portNumber, 5, 0, 0));
                client = new Client(serverAddress, portNumber);
                serverAddress = wordsOfCommandLineParameters[2];
                portNumber = Integer.parseInt(wordsOfCommandLineParameters[3]);
                //
                players.add(1, new Player(2, serverAddress, portNumber, 5, 0, 0));
                client2 = new Client(serverAddress, portNumber);
                clients.add(client);
                clients.add(client2);
                //Board to use
                maze = new Maze(9,9, 2);
                // New board init  --          public QuoridorBoard(Player player1, Player player2) {
                board = new QuoridorBoard(players.get(0), players.get(1));


                // test if we can start the connection to the Server
                // if it failed nothing we can do
                if(!client.start())
                    return;
                if(!client2.start())
                    return;

            }

            catch(Exception e) {
                System.out.println("Invalid port number.");
                System.out.println("Usage is: > java Client [username] [portNumber] [serverAddress]");
                return;
            }
        }

        else {
            System.out.println("Usage is: > java Client [username] [portNumber] {serverAddress]");
            return;
        }
        
        // wait for messages from user
        Scanner scan = new Scanner(System.in);
        // loop forever for message from the user
        int turn = 0;
        /*
                Main Interaction Loop
        */
        //while(true) {

                /*( This section is for deciding how to represent the the board (text or gui)
                if(text_only) {
                    System.out.println(maze);
                }
	    */

                // Test if gui only flag is called.                        
        if(gui_only) {
		
			System.out.println("GUI is launching!!");
			
			//We only want to launch the viewer once
			//gui_only = false;
			
			// Launch to open Andrew's Viewer class
			// Viewer.launch(Viewer.class);  
			
			//TODO Launch GUI.
			
			/**
			 * How to launch the application thread in order to be
			 * able to update the player move
			 */
			Thread t = new Thread() {
			    @Override
			    public void run() {
			        javafx.application.Application.launch(Viewer.class);
			    }
			};
			t.setDaemon(true);
			t.start();
			/**
			 * Called after launching the UI
			 */
			viewer = Viewer.waitForViewerStartUp();
			    
			// Call function from set board
			viewer.setBoard(board);
			
			//viewer.testTheReference();
			//System.out.println("I ran the thing.");
			viewer.refresh();
			
			// Refresh the board state 
			// Viewer.refresh();
		  
        }
               

        //hello section
        int temp = 1;
        for (Client c : clients) {
            c.sendMessage("HELLO");
            System.out.println("Writing HELLO to player" + temp);
            temp++;
        }
        // Make thread sleep for a momment before requesting teh next move.
        try {
            Thread.sleep(DELAY);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        //game
        // FIXME why is this a for loop and not for each and why is does it depend on yet another collection that we have to keep synced with all the others
        for(int i = 0 ; i < nameList.size(); i++){
            System.out.print(" " + (i+1) + " " + nameList.get(i) + " ");

            //If two players print send server its player number along with opponents name.
            if(clients.size() == 2){
                 clients.get(i).sendMessage("GAME " + (i+1) + " " + nameList.get(0) + " " + nameList.get(1));
            }
            //If four players send server its player number along with all opponents names
            if(clients.size() == 4){
                 clients.get(i).sendMessage("GAME " + (i+1) + " " + nameList.get(0) + " " + nameList.get(1) + " " + nameList.get(2) + " " + nameList.get(3));
            }
        }


        try {
        	Thread.sleep(DELAY);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }



        while(true){

                // Test if automated is run
            if(!automate) {
                    System.out.print("> ");
                    // read message from user
                    String msg = scan.nextLine();
                    //System.out.println("Recieved this from a server: " + msg);
                    // String parsing users input, looking for next turn so next move can be requested.
                    if(msg.equalsIgnoreCase("exit")){
                        //for (Client c : clients) {
                                //c.disconnect();
                        //}
						//disconnect();
						cleanUp(clients);
						//cleanUp(client2);
                        System.exit(0);
                    }else if(msg.equalsIgnoreCase("next")){
						// first thing is send hello to all the servers...
						// Ask for a move from the next player.
						//System.out.println("        >> Functionality not yet complete!\n" + "        It is player " + turn + "'s turn.");
						
						// This functionality could easily go inside a wrapper method, but just gonna
						//place move request inside here.
						if(turn == 0){
							currentPlayer = players.get(0);
						    nextTurn(client,clients.size());
						}else if(turn == 1){
							currentPlayer = players.get(1);
						    nextTurn(client2,clients.size());
						    //client2.sendMessage("MYOUSHU");
						}else if(turn == 2){
							currentPlayer = players.get(2);
						    nextTurn(client3,clients.size());
						    //client3.sendMessage("MYOUSHU");
						}else if(turn == 3){
							currentPlayer = players.get(3);
						    nextTurn(client4,clients.size());
						    //client4.sendMessage("MYOUSHU");
						}else{
						    System.out.println("ERROR >> Turn unrecognized.");
						}
						
						// Count value to iterate through players turns each time next is called. Makes
						// sure to iterate based on number of players.
		                if(turn >= players.size()-1){
		                        turn = 0;
		                }else{
		                        turn++;
		                }
						if(gui_only){
				            // Refreshing the GUI
				            viewer.refresh();
						}
			            // Gotta check if there is a winner yet!
						if(isWinner()){
						    System.out.println("There is a winner!");
						    cleanUp(clients);
							System.exit(0);
						}

                    } else if(msg.equalsIgnoreCase("help")){
                        //System.out.println("        This is the Viewer for a multi-AI played Quoridor game.
                    	//You can request next turn uring NEXT or quit using EXIT.");
                        usage();
                    }else{
                        continue;
                    }
            } else {
				//System.out.println("	Nope");
				
				//If automation is on we do . . .
				System.out.println("Automating");
				
				if(turn == 0){
				
					currentPlayer = players.get(0);
			        nextTurn(client,clients.size());
				}else if(turn == 1){
				
			        currentPlayer = players.get(1);
			        nextTurn(client2,clients.size());
			        //client2.sendMessage("MYOUSHU");
				}else if(turn == 2){
				
					currentPlayer = players.get(2);
			        nextTurn(client3,clients.size());
			        //client3.sendMessage("MYOUSHU");
				}else if(turn == 3){
				
					currentPlayer = players.get(3);
			        nextTurn(client4,clients.size());
			        //client4.sendMessage("MYOUSHU");
				}else{
			        System.out.println("ERROR >> Turn unrecognized.");
				}
				// Count value to iterate through players turns each time next is called. Makes
				//sure to iterate based on number of players.
	            if(turn >= players.size()-1){
                    turn = 0;
	            }else{
                    turn++;
	            }
				
				if(gui_only){
				    // Refreshing the GUI
			        viewer.refresh();
				}
				
				// Gotta check if there is a winner yet!
				if(isWinner()){
				    System.out.println("There is a winner! Player #" + currentPlayer.getID() + " has won!");
				    //broadcast(clients, "");
				    // TODO tell the servers who won
	                cleanUp(clients);
	                System.exit(0);
				}
				
	            // Make thread sleep for a moment before requesting the next move.
	            try {
                    Thread.sleep(DELAY);
	            } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
	            }
															
				try{
			        semaphore.acquire();
				}catch(InterruptedException e){
					System.out.println(e);
				}
													
            }
        }
    }

    // Basically a simple method that shuts down all the servers.
    public static void cleanUp(ArrayList<Client> clients){
        System.out.println("  Cleaning up!");
        // FIXME could be for each loop
        for(int i=0; i<clients.size(); i++){
            try{
                System.out.println("    Asking player " + clients.get(i).port + " to shutdown.");
                clients.get(i).sendMessage("KIKASHI " + currentPlayer.getID());
                clients.get(i).disconnect();
            }catch(Exception e){
                System.out.print(e);
                System.out.println("    Sending to: " + clients.get(i).port);
            }
        }
    }

	public static void broadcast(ArrayList<Client> clients, String text){
		// FIXME could be for each loop
		for(int i=0; i<clients.size(); i++){
            try{
                clients.get(i).sendMessage(text);
            }catch(Exception e){
                System.out.print(e);
                System.out.println("    Failure to send " + text + " to " + clients.get(i).port);
            }
        }
	}




    ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////
    public static void nextTurn(Client currentClient, int size) throws ClassNotFoundException, IOException {
        // First we request a move from the server.
        currentClient.sendMessage("MYOUSHU");
        // Then we listen on the socket for the reply. TESUJI <move string>
        //String message = (String) sInput.readObject();

        // Next we check it's legality. Will impliment after.
        //String broadcast = "ATARI";
        // This check is going to ban players who make bad moves!
        //if(!isValidMove()){
            // GOTE is protocol for BAN THAT FUCKER
            //broadcast = "GOTE" + currentClient;
        //}
        // Then if it fails we boot the player, if it passes we broadcast and update our board.

        // Returns to viewer's control to wait for new command, usually next turn.
    }


    //Checks the state of all players and states whether someone has won the match.
    public static boolean isWinner(){

	//If only one player remains!
	if(players.size() <= 1){
		System.out.println("Player x has won!");
		return true;
	}

	
	for (Player p : board.getPlayerSet()) {
		if(p.getID() == 1 && board.getNodeByPlayerNumber(1).getyPos() == 8){
			return true;
		}
		if(p.getID() == 2 && board.getNodeByPlayerNumber(2).getyPos() == 0){
            return true;
		}
		if(p.getID() == 3 && board.getNodeByPlayerNumber(3).getxPos() == 8){
            return true;
		}
		if(p.getID() == 4 && board.getNodeByPlayerNumber(4).getxPos() == 0){
            return true;
		}
	}
	

	// If we found nothing then no one has won yet return false.
	return false;
}



    /*
     * a class that waits for the message from the server and append them to the JTextArea
     * if we have a GUI or simply System.out.println() if in console mode
     */
    class ListenFromServer extends Thread {
            public void run() {

            	//While the thread is still running
                while(true) {

					// reads in characters from server
					String msg = IOscannerIn.nextLine();
														
					semaphore.release();
													
					System.out.println("Recieved from Player: " + " msg: " + msg);
					
			        // tired of parsing clutter, so removed all.
			        msg = msg.replace(',', ' ');
			        msg = msg.replace('(', ' ');
			        msg = msg.replace(')', ' ');
			        msg = msg.replace('[', ' ');
			        msg = msg.replace(']', ' ');
			        //System.out.println("    Fixed String. " + msg);
			
					// splits string into seperat components
					String[] my_cord = msg.split("\\s+");
					
					// It is ID speach.
					if(msg.contains("IAM")) {
						System.out.println(my_cord[0] + " " + my_cord[1]);
					    nameList.add(my_cord[1]);
					// It is a wall.
					} else if(msg.contains("TESUJI") && (msg.contains("v") || msg.contains("h") )){
						
						//maze.placeWall(Integer.parseInt(my_cord[1]), Integer.parseInt(my_cord[3]), my_cord[6]);
						//System.out.println("ERROR 1? " + Arrays.toString(my_cord));
						//board.placeWall(currentPlayer.getID(), Integer.parseInt(my_cord[1]), Integer.parseInt(my_cord[2]), my_cord[3].charAt(0));
						if(board.isValidMove(currentPlayer.getID(), Integer.parseInt(my_cord[2]), Integer.parseInt(my_cord[1]), my_cord[3].charAt(0)) ){
							System.out.println("IS GOOD!");
						    board.placeWall(currentPlayer.getID(), Integer.parseInt(my_cord[2]), Integer.parseInt(my_cord[1]), my_cord[3].charAt(0));
							System.out.println(currentPlayer.getID() + " " +  Integer.parseInt(my_cord[1]) + " " +  Integer.parseInt(my_cord[2]) + " " +  my_cord[3].charAt(0));
							// Gotta broadcast all changes after that.
							//System.out.println("ERROR 2?");
						    broadcast(clients, "ATARI " + currentPlayer.getID() + " [(" + my_cord[1] + ", " + my_cord[2] + "), " + my_cord[3] + "]");
						} else {
						    System.out.println("BAM, KICKED!");
							board.removePlayer(currentPlayer.getID());
							broadcast(clients, "GOTE " + currentPlayer.getID());
							if(isWinner()){
							    System.out.println("There is a winner!");
							    // TODO tell the servers who won
							    cleanUp(clients);
								System.exit(0);
							}
						    
						}
					// It is a pawn movement.
					}else if(msg.contains("TESUJI")) {
					    if(board.isValidMove(currentPlayer.getID(), Integer.parseInt(my_cord[1]), Integer.parseInt(my_cord[2])) ){
							board.movePawn(currentPlayer.getID(), Integer.parseInt(my_cord[1]), Integer.parseInt(my_cord[2]));
							broadcast(clients, "ATARI " + currentPlayer.getID() + " (" + my_cord[1] + ", " + my_cord[2] + ") ");
					    } else {
					    	System.out.println("BAM, KICKED!");
					        board.removePlayer(currentPlayer.getID());
					        broadcast(clients, "GOTE " + currentPlayer.getID());
					        if(isWinner()){
							    System.out.println("There is a winner!");
							    // TODO tell the servers who won
							    cleanUp(clients);
								System.exit(0);
							}
					        
					
					    }
					} else {
						System.out.println("I didn't quite catch that..");
					}

            }
        }
    }
    // this is a method to print out a "prompt" for the game, it tells you how to send
    //moves and such.
    public static void usage() {
            System.out.println("Welcome to the game of Quorridor!");
            System.out.println("Pay attention or you could get BANNED!");
            System.out.println("The Client will request a move by sending you \"MYOUSHU\"");
            System.out.println("This should trigger you to think about what you want and make a move.");
            System.out.println("Once you get this... send back \"TESUJI\" followed by your move.");
            System.out.println("Moves can either be a pawn move, or a wall placement.");
            System.out.println("  Walls are specified by a starting square ABOVE or");
            System.out.println("  to the LEFT of the wall and an h or w for a ");
            System.out.println("  horizontal or vertical wall.");                
            System.out.println("To send a move for your pawn use:   ");
            System.out.println("The whole wall designation is enclosed in");
            System.out.println("square brackets:");
            System.out.println("For Example:");
            System.out.println("A pawn move is a cordinate pair");
            System.out.println("<TESUJI> <(x,y)>");
            //yes im sure that the parenthesis are nessisary
            System.out.println("A valid wall declaration would be");
            System.out.println("<[(1, 0), v]> or <[(1, 0), h]>");
            System.out.println("Make valid moves or you will be BANNED");
            System.out.println("Good Luck!");

    }

    
    
    /** Protocol

         ** Initial Contacted
HELLO

    Client - Initial contact. Sent to elicit the IAM message.

IAM <name>

    Server - Response to HELLO; includes server's preferred display
    name. Display name cannot contain any whitespace.


GAME <p> <name1> <name2> [<name3> <name4>]

    Client - Game is ready to start. <p> is the player number for the
    server receiving this message. Players are numbered from 1. There
    are either 2 or 4 names. As in IAM, names cannot contain any
    whitespace.

    Players are numbered (clockwise from the top of the board)
    1, 4, 2, 3. This means that the player moving from lower to higher
    numbers (be it rows for player 1 and columns for player 3) comes
    first in the pair. The order of play is clockwise around the board
    from player 1.

         ** During Play

MYOUSHU

    Client - Request for a move. Server should be expecting this
    message as it knows which player last moved as well as its own
    player number.

TESUJI <move-string>

    Server - Response to MYOUSHU, includes the move made by the
    player. The move is the target location for the player's pawn or
    the location to place a wall.

ATARI <p> <move-string>

    Client - Communicates player #p's move to all players.

GOTE <p>

    Message sent by game-client to all move-servers informing them
    that <p> made an illegal move and is no longer in the game. The
    pawn for <p> should be removed from the game board and any
    remaining walls are lost. Note: sent as the very last message to
    the offending move-server.

KIKASHI <p>
    Message sent by game-client to all move-servers informing them
    that the game is over and the given player won. The game-client
    cannot send any additional messages to any move-server after
    sending this message.

         * Coordinates

    The board is viewed canonically with player 1 moving from the top to
    the bottom and player 3 moving from left to right. Columns and rows
    are numbered from 0-8 (from left-to-right for columns; from
    top-to-bottom for rows).

    A board position (a square where a pawn moves) is specified by an
    ordered pair (column, row). Each index ranges from 0-8. Since both
    rows and columns are labeled alike, make sure to keep track of which
    is which.

    Player 1 starts in position (4, 0) and is moving to row 8; player 2
    starts in position (4, 8) and is moving to row 0.

    Walls are specified by a starting square ABOVE or to the LEFT of the
    wall and an h or w for a horizontal or vertical wall. The whole wall
    designation is enclosed in square brackets: [(1, 0), v] or [(1, 0), h].

    Note that there are wall coordinates that are not actually valid: Any
    horizontal wall with 8 as its row, or any vertical wall with 8 as its
    column is not permitted.
         */
}

