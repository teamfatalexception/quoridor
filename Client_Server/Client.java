package Client_Server;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*
 * The Client that can be run both as a console or a GUI
 */
public class Client2  {

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
	Client2(String server, int port) {
		// which calls the common constructor with the GUI set to null
		this(server, port, null);
	}

	/*
	 * Constructor call when used from a GUI
	 * in console mode the ClientGUI parameter is null
	 */
	Client2(String server, int port, ClientGUI cg) {
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
	void sendMessage(ChatMessage msg) {
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
		Client2 client = new Client2(serverAddress, portNumber);
		Client2 client2 = new Client2(serverAddress, portNumber);
		Client2 client3 = new Client2(serverAddress, portNumber);
		Client2 client4 = new Client2(serverAddress, portNumber);
		
		//List for holding all clients
		ArrayList<Client2> clients =  new ArrayList<Client2>();
		
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
				client = new Client2(serverAddress, portNumber);
				serverAddress = words[2];
				portNumber = Integer.parseInt(words[3]);
				client2 = new Client2(serverAddress, portNumber);
				serverAddress = words[4];
				portNumber = Integer.parseInt(words[5]);
				client3 = new Client2(serverAddress, portNumber);
				serverAddress = words[6];
				portNumber = Integer.parseInt(words[7]);
				client4 = new Client2(serverAddress, portNumber);
				clients.add(client);
				clients.add(client2);
				clients.add(client3);
				clients.add(client4);
				
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
				client = new Client2(serverAddress, portNumber);
				serverAddress = words[2];
				portNumber = Integer.parseInt(words[3]);
				client2 = new Client2(serverAddress, portNumber);
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
		while(true) {
			System.out.print("> ");
			// read message from user
			String msg = scan.nextLine();
			// logout if message is LOGOUT
			if(msg.equalsIgnoreCase("LOGOUT")) {
				client.sendMessage(new ChatMessage(ChatMessage.LOGOUT, ""));
				// break to do the disconnect
				break;
			}
			// message WhoIsIn
			else if(msg.equalsIgnoreCase("WHOISIN")) {
				client.sendMessage(new ChatMessage(ChatMessage.WHOISIN, ""));				
			}
			else {
				// default to ordinary message
				//Checks if four players present.  Echo between all four.
				if(clients.size() == 4){
				client.sendMessage(new ChatMessage(ChatMessage.MESSAGE, msg));
				client2.sendMessage(new ChatMessage(ChatMessage.MESSAGE, msg));
				client3.sendMessage(new ChatMessage(ChatMessage.MESSAGE, msg));
				client4.sendMessage(new ChatMessage(ChatMessage.MESSAGE, msg));
				}
				
				//If not four players at this point can only be two.  Echo between the two.
				else{
					client.sendMessage(new ChatMessage(ChatMessage.MESSAGE, msg));
					client2.sendMessage(new ChatMessage(ChatMessage.MESSAGE, msg));
				}
			}
		}
	       
		client.disconnect();	
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


