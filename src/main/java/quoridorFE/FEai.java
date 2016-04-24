package quoridorFE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.DijkstraShortestPath;

public class FEai {

	// Some global variables.
	public static String move[] = {
	    "[(4, 4), v]",
	    "[(4, 6), v]",
	    "[(4, 8), h]",
	    "[(6, 8), h]"

	};
	public static int counter = -1;


	public FEai() {
		// TODO Auto-generated constructor stub
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
                    if (qboard.isValidMove(player, x, y, or) ) {
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

	/**
	The actual AI move method.
	**/
	public static String getMove(int player, QuoridorBoard qboard){


	    // Shiller's opening, once the two have crossed paths, start placing walls to screw the other one over.
	    //	The concept is to force the other player to do the most backtracking.
	    /*if(player == 1){
		if(qboard.getNodeByPlayerNumber(2).getyPos() < 4){
		    // Gotta check it legal too..
		    counter++;
		    if(counter < move.length-1 && isValid(player, qboard, move[counter]) ){
		        return move[counter];
		    }
		}else{
		    // If it's not time just do shortest path.
		    return getShitMove(player, qboard);
	    	}
	    }else 
		if(player == 2){
                    //if(qboard.getNodeByPlayerNumber(1).getyPos() > 4){
                    // Gotta check it legal too.. But increment everytime otherwise will try same move forever!
		    counter++;
                    if(counter < move.length-1 && isValid(player, qboard, move[counter]) ){
                        return move[counter];
		    }
                }*/
                // If it's not time just do shortest path.
                //else return getShitMove(player, qboard);
	    //}


	    if(!defendCloseOpponents(player, qboard).equals("") ){
		return defendCloseOpponents(player, qboard);
            }
            // If it's not time just do shortest path.
	    return getMoveShortestPath(player, qboard);
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
	
	// Method defendCloseOpponents
	private static String defendCloseOpponents(int player, QuoridorBoard qboard) {
	    UndirectedGraph<BoardNode, edgeFE> board = qboard.board;
	    // shortest guys player number paired with his shortest path to win.
	    int[] playerPair = new int[]{0, 1000};
	    //String m = "[(";
	    // Iterate through all players
	    for(int i=1; i<5; i++){
		// If it is us or the player has been kicked.
		if(i == player || qboard.getNodeByPlayerNumber(i) == null){
		    continue;
		}else{
		    // Check for shortest path.
		    int temp = shortestPathToWin(i, qboard);
   	            if(temp < playerPair[1] && temp < 4){
			playerPair[0] = i;
			playerPair[1] = temp;
		    }
	        }
	    }
	    // Now we have the player who is quickest to winning and we need to find is next step on shortest path and block it with a wall.
	    String hisMove = "" + getMoveShortestPath(playerPair[0], qboard);

	    // parse out everything that is not a number
	    hisMove = hisMove.replace(',', ' ');
            hisMove = hisMove.replace('(', ' ');
            hisMove = hisMove.replace(')', ' ');
            hisMove = hisMove.replace('[', ' ');
            hisMove = hisMove.replace(']', ' ');

	    String[] holder = hisMove.trim().split("\\s++");

            // We gotta remove this later.
	    if(!qboard.isValidMove(playerPair[0], Integer.parseInt(holder[0]), Integer.parseInt(holder[1]))){
                return "";
            }

	    // figure out which direction that is.
	    //int dirY = Math.abs(qboard.getNodeByPlayerNumber(playerPair[0]).getyPos() - Integer.parseInt(holder[1]));
	    int dirX = qboard.getNodeByPlayerNumber(playerPair[0]).getxPos() - Integer.parseInt(holder[0]);

	    // Now use offset to move walls
	    /*if(Integer.parseInt(holder[0]) == -1){
		holder[0] = ""+(Integer.parseInt(holder[0]) - 1);
	    }else if(Integer.parseInt(holder[1]) == -1){
                holder[1] = ""+(Integer.parseInt(holder[1]) - 1);
            }*/


	    // give the wall coordinate that blocks that.
	    switch(Math.abs(dirX)){
		case 1:
			//vertical
		    return "[(" + holder[0] + ", " + holder[1] + "), v]";
	        case 0:
			//horizontial
		    return "[(" + holder[0] + ", " + holder[1] + "), h]";
	    }
	    return "";
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
