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

	
	public static String getShitMove(int player, QuoridorBoard qboard) {
		
		UndirectedGraph<BoardNode, edgeFE> board = qboard.board;
		BoardNode source = qboard.getNodeByPlayerNumber(player);
		ArrayList<BoardNode> winningNodeList = generateWinningNodeList(player, qboard);
		ArrayList<DijkstraShortestPath<BoardNode, edgeFE>> pathList = new ArrayList<DijkstraShortestPath<BoardNode, edgeFE>>(); 
		
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
	    }else*/ if(player == 2){
                if(qboard.getNodeByPlayerNumber(1).getyPos() > 4){
                    // Gotta check it legal too.. But increment everytime otherwise will try same move forever!
		    counter++;
                    if(counter < move.length-1 && isValid(player, qboard, move[counter]) ){
                        return move[counter];
		    }
                }else{
                    // If it's not time just do shortest path.
                    return getShitMove(player, qboard);
                }
	    /*}else{
		System.out.println("*Sweats* I-I'm not ready for more than two.");
	    */
	    }

	    return getShitMove(player, qboard);
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
	
}
