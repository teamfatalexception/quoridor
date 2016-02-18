package servers;

import java.io.IOException;
import java.io.PrintStream;

import java.lang.Math;
import java.lang.Integer;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Pattern;
import java.util.Date;
import java.util.Calendar;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import java.util.Scanner;



public class MoveServer {

    /** Default port number; used if none is provided. */
    public final static int DEFAULT_PORT_NUMBER = 3939;

    /** Default machine name is the local machine; used if none provided. */
    public final static String DEFAULT_MACHINE_NAME = "localhost";

    /** Command-line switches */
    public final static String ARG_PORT = "--port";
    public final static String ARG_MACHINE = "--machine";

    /** Server display-name stuff */
    public final static String TEAM_PREFIX = "TFE";
    public final static String TEAM_NAME = "FatalException";

    /** Message op-codes */
    public final static String MSG_HELLO = "HELLO";
    public final static String MSG_IAM = "IAM";
    public final static String MSG_GAME = "GAME";
    public final static String MSG_MYOUSHU = "MYOUSHU";
    public final static String MSG_TESUJI = "TESUJI";
    public final static String MSG_ATARI = "ATARI";
    public final static String MSG_GOTE = "GOTE";
    public final static String MSG_KIKASHI = "KIKASHI";


    /** Player Info */
    private int playerNumber = 0;

    private String player1 = "";
    private String player2 = "";
    private String player3 = "";
    private String player4 = "";

    /** Game state stuff */

    boolean isGameOn = false;



    /** Port number of distant machine */
    private int portNumber;

    /**  * Creates a new <code>FirstServer</code> instance. FirstServer is
     * a listening echo server (it responds with a slightly modified
     * version of the same message it was sent).
     *
     * @param portNumber required port number where the server will
     * listen.
     */
    public MoveServer(int portNumber) {
        this.portNumber = portNumber;
    }

    public void run() {

        try {
        ServerSocket server = new ServerSocket(portNumber);
        System.out.format("Server now accepting connections on port %d!!!!\n",
        portNumber);

        Socket client;
        while ((client = server.accept()) != null) {
            System.out.format("Connection from %s\n", client);
            Scanner cin = new Scanner(client.getInputStream());
            PrintStream cout = new PrintStream(client.getOutputStream());
            String clientMessage = "";
            while (cin.hasNextLine() && !isGameOn) {
                clientMessage = cin.nextLine();
                System.out.println("Server received: \"" + clientMessage + "\"");

                String[] msgArgs = clientMessage.split(" ");

                if (msgArgs[0].equals("HELLO")) {
                    System.out.println("Server received HELLO");
                    // I should replace with something like iam(cout)
                    cout.println("IAM tfe:testName");
                }
                if (msgArgs[0].equals(MSG_GAME)) {
                    playerNumber = (int) Integer.valueOf(msgArgs[1]);
                    if (msgArgs.length == 4) {
                        player1 = msgArgs[2];
                        player2 = msgArgs[3];
                    } else {
                        player1 = msgArgs[2];
                        player2 = msgArgs[3];
                        player3 = msgArgs[4];
                        player4 = msgArgs[5];
                    }
                    isGameOn = true;
                }
            }

            while (isGameOn) {
                if (cin.hasNextLine()) {
                    String[] msgArgs = cin.nextLine().split(" ");
                    if (msgArgs[0].equals(MSG_MYOUSHU)) {
                        // Send move to client

                    } else if (msgArgs[0].equals(MSG_ATARI)) {
                        // Update game state with new move

                    } else if (msgArgs[0].equals(MSG_GOTE)) {
                        int kickedPlayer = (int) Integer.valueOf(msgArgs[1]);
                        if (kickedPlayer == playerNumber) {
                            // oh shit we just got kicked out of the game
                            isGameOn = false:
                            System.exit(0);
                        }

                    } else if (msgArgs[0].equals(MSG_KIKASHI)) {
                        int winningPlayer = (int) Integer.valueOf(msgArgs[1]);
                        if (winningPlayer == playerNumber) {
                            // WE WON
                        } else {
                            // WE LOST
                        }
                        isGameOn = false;
                    }
                }
            }

            cout.close();
            cin.close();
        }
        } catch (IOException ioe) {
            // there was a standard input/output error (lower-level from uhe)
            ioe.printStackTrace();
            System.exit(1);
        } 
    }

    private static void usage() {
      System.err.print("usage: java <.jar path> Server [options]\n" +
        "       where options:\n" + "       --port port\n");
    }
    public static void main(String[] args) {
        int port = DEFAULT_PORT_NUMBER;

        /* Parsing parameters. argNdx will move forward across the
         * indices; remember for arguments that have their own parameters, you
         * must advance past the value for the argument too.
         */
        int argNdx = 0;

        while (argNdx < args.length) {
            String curr = args[argNdx];
            if (curr.equals(ARG_PORT)) {
                ++argNdx;
                String numberStr = args[argNdx];
                port = Integer.parseInt(numberStr);
            } else {
                // if there is an unknown parameter, give usage and quit
                System.err.println("Unknown parameter \"" + curr + "\"");
                usage();
                System.exit(1);
            }
            ++argNdx;
        }
        MoveServer ms = new MoveServer(port);
        ms.run();
    }
}
