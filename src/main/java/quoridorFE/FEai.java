package quoridorFE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Random;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.DijkstraShortestPath;

public class FEai {

	// Some global variables.
	public static String move[] = {
	    "[(4, 4), v]",
	    "[(4, 6), v]",
	    "[(4, 8), h]",
	    "[(6, 8), v]",
            "[(2, 4), v]",
            "[(2, 2), v]",
            "[(7, 4), h]",
            "[(7, 8), v]"
	};
	public static int counter = -1;


	public FEai() {
		// TODO Auto-generated constructor stub
	}

	public static String getRecordedMove(){
	    if(counter >= move.length-1){
		System.out.println("	Reseting count!");
		counter = 0;
	    }else{
	    	counter++;
	    }
	    return move[counter];
	}

	
	public static String getMoveShortestPath(int player, QuoridorBoard qboard) {
		
		UndirectedGraph<BoardNode, edgeFE> board = qboard.board;
		BoardNode source = qboard.getNodeByPlayerNumber(player);
		ArrayList<BoardNode> winningNodeList = generateWinningNodeList(player, qboard);
		ArrayList<DijkstraShortestPath<BoardNode, edgeFE>> pathList = new ArrayList<DijkstraShortestPath<BoardNode, edgeFE>>(); 
		
		// Getting
		for (BoardNode n : winningNodeList) {
			pathList.add(new DijkstraShortestPath<BoardNode, edgeFE>(board, source, n));
		}
		DijkstraShortestPath<BoardNode, edgeFE> shortestPath = null;
		for (DijkstraShortestPath<BoardNode, edgeFE> p : pathList) {
			if (shortestPath == null) {
				shortestPath = p;
			}
			if (shortestPath.getPathLength() > p.getPathLength()) {
				shortestPath = p;
			}
		}
		// shortestPath now points to the shortest path.
		
		List<edgeFE> edgeList = shortestPath.getPathEdgeList();
		Set<BoardNode> nodesOnThePath = new HashSet<BoardNode>();
		
		for (edgeFE e : edgeList) {
			nodesOnThePath.add(e.getSource());
			nodesOnThePath.add(e.getTarget());
		}
		
		// Now I've gotta get the first node on the path that isn't my current pawn position
		// and that isn't occupied by another pawn
		BoardNode attempt = edgeList.get(0).getTarget();
		
		for (edgeFE e : edgeList) {
			attempt = e.getTarget();
			if (qboard.isValidMove(player, attempt.getxPos(), attempt.getyPos())) {
				// we found a good move
				break;
			}
		}
		
		String retStr = "("+ attempt.getxPos() + ", "+ attempt.getyPos() +")";
		
		// Currently, prints out all the path lengths from player to all other nodes on the board
		/*ArrayList<int[]> pathChart = getPaths
		    (qboard.getNodeByPlayerNumber(player), qboard);*/
		return retStr;
	}
	
	

	/**
	Just lets you quickly know if your move if an illegal one with a boolean return value.
	**/
	public static boolean isValid(int player, QuoridorBoard qboard, String attempt){

		// Gotta check if it's a wall or a move.
		int x, y;
		char or;
		if(attempt.contains("v") || attempt.contains("h")){

		    //Then it is a wall.
		    x = Integer.parseInt("" + attempt.charAt(2));
		    y = Integer.parseInt("" + attempt.charAt(5));
		    or = attempt.charAt(9);
                    if (qboard.isValidMove(player, x, y, or)) {
                        // we found a good move
	                return true;
                    }
		}else{

		    // It is a pawn move.
		    x = Integer.parseInt(""+attempt.charAt(1));
	     	    y = Integer.parseInt(""+attempt.charAt(4));
                    if (qboard.isValidMove(player, x, y) ) {
                        // we found a good move
                        return true;
                    }
		}
		return false;
	}
	
	
	/* Copying team morty's AI concept
	public static String getMove2(int player, QuoridorBoard qboard){
	    //iterate through other players.
	    String move = getMoveShortestPath(player, qboard);
	    for(int i=0; i<qboard.getPlayerSet().size()+1; i++){
		//if their length is shorter than ours, block them.
  	        if(shortestPathToWin(i, qboard) < shortestPathToWin(player, qboard) && i != player){
		    move = blockPlayer(i, qboard);
	        }
	    }
	    //if(isValid(player, qboard, move)){
	 	return move;
	    //}else{
		//return getRecordedMove();
	    //}
	    //return move
	}*/

	
	/**
	The actual AI move method.
	**/
	public static String getMove(int player, QuoridorBoard qboard){
	    Random ran = new Random();
	    int r = ran.nextInt(10);
	    boolean keepgoing = true;
	    String output = getMoveShortestPath(player, qboard);
	    while(keepgoing){
	        // Lets select a move based on that number. Later we will have a weighted system generated based on board state.
			System.out.println(""+r);
			if(r < 3){
			    output = blockClosestOpponent(player, qboard);
			}else if(r == 4){
			    output = getRecordedMove();
			}//else{
			    //output = getMoveShortestPath(player, qboard);
			    //<Up>output = blockPlayer(output, qboard);
			//}
			// Check if it's valid, if it isn't we will contiue to search for the next legal move we can make.
			String msg = "";
                        msg = output.replace(',', ' ');
                        msg = msg.replace('(', ' ');
                        msg = msg.replace(')', ' ');
                        msg = msg.replace('[', ' ');
                        msg = msg.replace(']', ' ');
			String[] my_cord = msg.trim().split("\\s+");
			System.out.println("	MSG:" + msg);

			if(output.contains("v") || output.contains("h") && qboard.isValidMove(player, Integer.parseInt(my_cord[0]), Integer.parseInt(my_cord[1]), my_cord[2].charAt(0))){
			    keepgoing = false;
			    System.out.println("        LEGAL MOVE:" + output);
			}else if(qboard.isValidMove(player, Integer.parseInt(my_cord[0]), Integer.parseInt(my_cord[1]))){
			    keepgoing = false;
                            System.out.println("        LEGAL MOVE:" + output);
			}else{
			    r = ran.nextInt(10);
			    System.out.println("	ILLEGAL MOVE:" + output + "	NUM:" + r);			  
			}
	    }
	    return output;
	}
	
	
	
	private static ArrayList<BoardNode> generateWinningNodeList(int player, QuoridorBoard qboard) {
		ArrayList<BoardNode> list = new ArrayList<BoardNode>();
		if (player == 1) {
			for (int i = 0; i < 9; i++) {
				list.add(qboard.getNodeByCoords(i, 8));
			}
		} else if (player == 2) {
			for (int i = 0; i < 9; i++) {
				list.add(qboard.getNodeByCoords(i, 0));
			}
		} else if (player == 3) {
			for (int i = 0; i < 9; i++) {
				list.add(qboard.getNodeByCoords(8, i));
			}
		} else if (player == 4) {
			for (int i = 0; i < 9; i++) {
				list.add(qboard.getNodeByCoords(0, i));
			}
		}
		return list;
	}
	
	
	
	// Method getPaths()
	// Param: Boardnode player: The position of a player
	// 	  QuoridorBoard qboard: Current iteration of the board
	// Returns: ArrayList<int[]> rows:
	//	List of lists of each row, filled with the distance from player to each node in that row.
	public static ArrayList<int[]> getPaths(BoardNode player, QuoridorBoard qboard) {
	    System.out.println("Shortest Path from player to all other nodes on the board:"
		+	"Player is at position 0");
	    ArrayList<int[]> rows = new ArrayList<int[]>(9);		//initializing rows
	    for(int i = 0; i < 9; i++){
		int[] row = new int[9];
		for(int j = 0; j < 9; j++){
		    // getting the path from player to node at (i, j)
		    UndirectedGraph<BoardNode, edgeFE> board = qboard.board;
		    row[j] = (int)(new DijkstraShortestPath<BoardNode, edgeFE>
		    (board, player, qboard.getNodeByCoords(i, j)).getPathLength());
		    System.out.print(row[j] + " ");
		}
		rows.add(i, row);	// Adding each row to rows<>
		System.out.println("");
	    }
	    return rows;
	}
	
	
	
	// Method blockClosestOpponent, takes a player integer, which is the current player and the current game board.
	public static String blockClosestOpponent(int player, QuoridorBoard qboard) {

	    //UndirectedGraph<BoardNode, edgeFE> board = qboard.board;
	    // shortest guys player number paired with his shortest path to win.
	    int[] playerPair = new int[]{0, 1000};

	    // Iterate through all players
	    // FIXME this could be a for each loop
	    for(int i=0; i<qboard.getPlayerSet().size()+1; i++){

			// If it is us or the player has been kicked.
			if(i == player || qboard.getNodeByPlayerNumber(i) == null) {
			    System.out.println(qboard.getNodeByPlayerNumber(i));
			} else {
			    // Check for shortest path.
			    int temp = shortestPathToWin(i, qboard);
   	            	    if(temp < playerPair[1]){
				System.out.println("	New shortest is:" + i + "!");
				playerPair[0] = i;
				playerPair[1] = temp;
   	            	    }
		        }
	    }
	    // Now that we have the closest player lets return a blocking wall on him.
	    return blockPlayer(playerPair[0], qboard);
	}
	
	
	
	//Param: current player's number, current board state
	//Returns: string of wall to block that player's shortest path.
	// Will be modified to smartly increase their shortest path the most.
        public static String blockPlayer(int player, QuoridorBoard qboard){
	    System.out.println("	BLOCLKING:" + player);
	    // Now we have the player who is quickest to winning and we need to find is next step on shortest path and block it with a wall.
	    String thisMove = "" + getMoveShortestPath(player, qboard);

	    // parse out everything that is not a number into a nice array
	    String hisMove = thisMove;
	    hisMove = hisMove.replace(',', ' ');
            hisMove = hisMove.replace('(', ' ');
            hisMove = hisMove.replace(')', ' ');
            hisMove = hisMove.replace('[', ' ');
            hisMove = hisMove.replace(']', ' ');
	    String[] holder = hisMove.trim().split("\\s++");

	    // Get his current space
            int curX = qboard.getNodeByPlayerNumber(player).getxPos();
            int curY = qboard.getNodeByPlayerNumber(player).getyPos();

	    // figure out what direction he is moving.
            int dirX = curX - Integer.parseInt(holder[0]);
	    int dirY = curY - Integer.parseInt(holder[1]);

	    /* Gotta build an offset based on direction.
	    if(Integer.parseInt(holder[0]) == -1){
		holder[0] = ""+(Integer.parseInt(holder[0]) - 1);
	    }else if(Integer.parseInt(holder[1]) == -1){
                holder[1] = ""+(Integer.parseInt(holder[1]) - 1);
            }

	     Do orientation based on their direction they will move.
	    if(Math.abs(dirX) == 1 && isValid(player, qboard, ("["+hisMove+", v]") )){
		return "[" + hisMove + ", v]";
	    }else{
  	        return "[" + thisMove + ", h]";
	    }*/

	    // Now that we know his direction of movement lets try blocking it.
	    if(dirX == -1){
		return "[(" + (Integer.parseInt(holder[0]) - 1) + ", " + (Integer.parseInt(holder[1])) + "), v]";
	    }else if(dirX == 1){
		return "[(" + (Integer.parseInt(holder[0])) + ", " + (Integer.parseInt(holder[1])) +"), v]";
	    }else if(dirY == -1){
                return "[(" + (Integer.parseInt(holder[0])) + ", " + (Integer.parseInt(holder[1]) + 1) + "), h]";
	    }else if(dirY == 1){
                return "[(" + (Integer.parseInt(holder[0])) + ", " + (Integer.parseInt(holder[1])) +"), h]";
	    }else{
	        return "[" + thisMove + ", h]";
	    }
	}


	// Param: player p, and an instance of the board
	// Returns: shortest path to a winning node
	private static int shortestPathToWin(int player, QuoridorBoard qboard){

	    ArrayList<BoardNode> winningNodes = generateWinningNodeList(player, qboard);
	    UndirectedGraph<BoardNode, edgeFE> board = qboard.board;
	    int shortest = 10000000;
	    for(int i = 0; i< winningNodes.size(); i++) {
		int pathLength = (int)(new DijkstraShortestPath<BoardNode, edgeFE>
		    (board, qboard.getNodeByPlayerNumber(player), winningNodes.get(i)).getPathLength() );
		if(pathLength < shortest)
		    shortest = pathLength;
	    }
	    return shortest;
	}
}
