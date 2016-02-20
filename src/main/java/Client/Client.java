import java.net.*;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * The Client that can be run both as a console or a GUI
 */

public class Client  {

	// Declared list of current players, 2 or 4 long until people start gettting kicked.
	private static ArrayList<Player> players = new ArrayList<Player>();
	private static Player currentPlayer;

	// for I/O
	private ObjectInputStream sInput;		// to read from the socket
	private ObjectOutputStream sOutput;		// to write on the socket
	private Socket socket;
	// if I use a GUI or not
	private ClientGUI cg;
	// the server, the port and the username
	private String server, username;
	private int port;

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
	Client(String server, int port, ClientGUI cg) {
		this.server = server;
		this.port = port;
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
			sInput  = new ObjectInputStream(socket.getInputStream());
			sOutput = new ObjectOutputStream(socket.getOutputStream());
		}
		catch (IOException eIO) {
			display("Exception creating new Input/output Streams: " + eIO);
			return false;
		}

		// creates the Thread to listen from the server 
		new ListenFromServer().start();
		// Send our username to the server this is the only message that we
		// will send as a String. All other messages will be ChatMessage objects
		try
		{
			sOutput.writeObject(username);
		}
		catch (IOException eIO) {
			display("Exception doing login : " + eIO);
			disconnect();
			return false;
		}
		// success we inform the caller that it worked
		return true;
	}

	/*
	 * To send a message to the console or the GUI
	 */
	private void display(String msg) {
		if(cg == null)
			System.out.println(msg);      // println in console mode
		else
			cg.append(msg + "\n");		// append to the ClientGUI JTextArea (or whatever)
	}
	
	/*
	 * To send a message to the server
	 */
	void sendMessage(String msg) {
		try {
			sOutput.writeObject(msg);
		}
		catch(IOException e) {
			display("Exception writing to server: " + e);
		}
	}

	/*
	 * When something goes wrong
	 * Close the Input/Output streams and disconnect not much to do in the catch clause
	 */
	private void disconnect() {
		try { 
			if(sInput != null) sInput.close();
		}
		catch(Exception e) {}
		try {
			if(sOutput != null) sOutput.close();
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
	public static void main(String[] args) {
		//Line to be used for regex
		String line = "";
		//Loop through commands and add them to line
		for (int n = 0; n < args.length; n++){
			line += args[n] + " ";
		}
		
		//Replace all colons in line with whitespace
		String my_line = line.replaceAll(":", " ");
		
		//Split line up into individual string components
		String[] words = my_line.split("\\s+");
		
		// default values
		int portNumber = 1500;  //Default port
		String serverAddress = "localhost";  // Default hostname
		
		
		//Instantiate several clients to use
		Client client = new Client(serverAddress, portNumber);
		Client client2 = new Client(serverAddress, portNumber);
		Client client3 = new Client(serverAddress, portNumber);
		Client client4 = new Client(serverAddress, portNumber);
		
		//List for holding all clients
		ArrayList<Client> clients =  new ArrayList<Client>();
		
		//Patter to use for checking if two players
		String pattern = "(.*)(\\s*)(:)(\\s*)(\\d+)(\\s*)(.*)(\\s*)(:)(\\s*)(\\d+)";
		
		//Pattern to use for checking if four players
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
				serverAddress = words[0];
				portNumber = Integer.parseInt(words[1]);
				//
                                players.add(new Player(1, serverAddress, portNumber, 5, 0, 0));

				client = new Client(serverAddress, portNumber);
				serverAddress = words[2];
				portNumber = Integer.parseInt(words[3]);
				//
                                players.add(new Player(2, serverAddress, portNumber, 5, 0, 0));

				client2 = new Client(serverAddress, portNumber);
				serverAddress = words[4];
				portNumber = Integer.parseInt(words[5]);
				//
                                players.add(new Player(3, serverAddress, portNumber, 5, 0, 0));

				client3 = new Client(serverAddress, portNumber);
				serverAddress = words[6];
				portNumber = Integer.parseInt(words[7]);
				//
                                players.add(new Player(4, serverAddress, portNumber, 5, 0, 0));

				client4 = new Client(serverAddress, portNumber);
				clients.add(client);
				clients.add(client2);
				clients.add(client3);
				clients.add(client4);

				// Setup player objects time.
				// Some of these values will change as they become useful, like starting positions for each player, etcc.
				// public Player(int ID, String name, int port, int wallsLeft, int startingX, int startingY){
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
				}
				catch(Exception e) {
					System.out.println("Invalid port number.");
					System.out.println("Usage is: > java Client [username] [portNumber] [serverAddress]");
					return;
				}
			}
		
		//Only runs if there are two players
		else if(m.find()) {
			try {
				serverAddress = words[0];
				portNumber = Integer.parseInt(words[1]);
				//
                                players.add(new Player(1, serverAddress, portNumber, 5, 0, 0));
				client = new Client(serverAddress, portNumber);
				serverAddress = words[2];
				portNumber = Integer.parseInt(words[3]);
				//
                                players.add(new Player(2, serverAddress, portNumber, 5, 0, 0));
				client2 = new Client(serverAddress, portNumber);
				clients.add(client);
				clients.add(client2);
				
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
		while(true) {
			System.out.print("> ");
			// read message from user
			String msg = scan.nextLine();

			// String parsing users input, looking for next turn so next move can be requested.
			if(msg.equalsIgnoreCase("exit")){

				System.exit(0);
			}else if(msg.equalsIgnoreCase("next")){

				// Ask for a move from the next player.
				System.out.println("	>> Functionality not yet complete!\n" + "	It is player " + turn + "'s turn.");

				// This functionality could easily go inside a wrapper method, but just gonna place move request inside here.
				if(turn == 0){
					client.sendMessage("MYOUSHU");
				}else if(turn == 1){
					client2.sendMessage("MYOUSHU");
				}else if(turn == 1){
                                        client3.sendMessage("MYOUSHU");
                                }else if(turn == 1){
                                        client4.sendMessage("MYOUSHU");
                                }else{
					System.out.println("ERROR >> Turn unrecognized.");
				}

				// Count value to iterate through players turns each time next is called. Makes sure to iterate based on number of players.
				if(turn >= players.size()-1){
					turn = 0;
				}else{
					turn++;
				}

			}else if(msg.equalsIgnoreCase("help")){
				System.out.println("	This is the Viewer for a multi-AI played Quoridor game. You can request next turn uring NEXT or quit using EXIT.");
			}else{
				continue;
			}

			/*
				if(clients.size() == 4){
				client.sendMessage(msg);
				client2.sendMessage(msg);
				client3.sendMessage(msg);
				client4.sendMessage(msg);
				}
				//If not four players at this point can only be two.  Echo between the two.
				else{
					client.sendMessage(msg);
					client2.sendMessage(msg);
				}
			}   */
		}
	    
	}

	/*
	 * a class that waits for the message from the server and append them to the JTextArea
	 * if we have a GUI or simply System.out.println() if in console mode
	 */
	class ListenFromServer extends Thread {

		public void run() {
			while(true) {
				try {
					String msg = (String) sInput.readObject();
					// if console mode print the message and add back the prompt
					if(cg == null) {
						System.out.println(msg);
						System.out.print("> ");
					}
					else {
						cg.append(msg);
					}
				}
				catch(IOException e) {
					display("Server has close the connection: " + e);
					if(cg != null) 
						cg.connectionFailed();
					break;
				}
			
				catch(ClassNotFoundException e2) {
				}
			}
		}
	}
}


