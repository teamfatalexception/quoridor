/**
* The QuoridorBoard class implements the Quoridor board and methods for interacting with it.
* 
* Player objects passed to the constructor are assumed to be unique and 
* class methods may not behave as expected if there are duplicate players on the board.
*
* @author  Andrew Valancius
* 
* @since   2016-02-26 
*/


package quoridorFE;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.VertexFactory;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.generate.GridGraphGenerator;
import org.jgrapht.graph.ClassBasedVertexFactory;
import org.jgrapht.graph.SimpleGraph;

public class QuoridorBoard {
	
	UndirectedGraph<BoardNode, edgeFE> board;
	
	HashSet<Player> playerSet; // TODO decide if this part is even needed (prolly isn't)
	HashSet<String> wallSet; // TODO talk to team about wall objects maybe?
	
	
	public QuoridorBoard(Player player1, Player player2) {
		this(player1, player2, null, null);
	}
	
	
	public QuoridorBoard(Player player1, Player player2, Player player3, Player player4) {
		// Gotta populate the board
		board = new SimpleGraph<BoardNode, edgeFE>(edgeFE.class);
		
		GridGraphGenerator<BoardNode, edgeFE> gridGen =
				new GridGraphGenerator<BoardNode, edgeFE>(9, 9);
		
		VertexFactory<BoardNode> vFactory =
	            new ClassBasedVertexFactory<BoardNode>(BoardNode.class);
		
		gridGen.generateGraph(board, vFactory, null);
		// The empty graph is now constructed
		
		int xcount = 0;
		int ycount = 0;
		for (BoardNode n : board.vertexSet()){
			//System.out.println("Node x: " + n.getxPos() + " y: " + n.getyPos());
			n.setxPos(xcount);
			n.setyPos(ycount);
			xcount++;
			if (xcount == 9) {
				xcount = 0;
				ycount++;
			}
			//System.out.println("Node x: " + n.getxPos() + " y: " + n.getyPos());
		}
		// Every node of the graph now has a position
		
		
		
		this.getNodeByCoords(4, 0).setPlayer(player1);
		this.getNodeByCoords(4, 8).setPlayer(player2);
		if (player3 != null && player4 != null) {
			this.getNodeByCoords(0, 4).setPlayer(player3);
			this.getNodeByCoords(8, 4).setPlayer(player4);
		}
		// Now the players have been placed on the board.
		
		
		// debug info
		/*
		for (BoardNode n : board.vertexSet()){
			System.out.println("Node x: " + n.getxPos() + " y: " + n.getyPos());
			for (edgeFE e : board.edgesOf(n)){
				System.out.println(e.toString());
			}
		}
		*/
	}

	
	
	public static void main(String[] args){
		// This main() was just for my testing purposes.
		// it should be deleted eventually
		QuoridorBoard testBoard = new QuoridorBoard(new Player(1, "test1", 6666, 10, 4, 0), new Player(2, "test2", 6667, 10, 4, 8));
		
	}

	public BoardNode getNodeByCoords(int x, int y) {
		for (BoardNode n : this.board.vertexSet()) {
			if (n.getxPos() == x && n.getyPos() == y) {
				return n;
			}
		}
		return null;
	}

	public BoardNode getPlayerPosition(int player) {
		for (BoardNode n : this.board.vertexSet()) {
			if (n.getPlayer() != null) {
				if (player == n.getPlayer().getID()) {
					return n;
				}
			}
		}
		return null;
	}
	
	
	public boolean isValidMove(int player, int x, int y) {
		
		BoardNode source = this.getPlayerPosition(player);
		BoardNode target = this.getNodeByCoords(x, y);
		
		if (source.equals(target)) return false; 		// source and destination are the same
		if (source.getPlayer() == null) return false; 	// there is no player at this location.
		if (target.getPlayer() != null) return false; 	// you can't move your pawn to the same place as an opponents
		
		if (source.getPlayer().getID() != player) {
			// player attempting to move a pawn that does not belong to him
			return false;
		}
		DijkstraShortestPath<BoardNode, edgeFE> path =
				new DijkstraShortestPath<BoardNode, edgeFE>(this.board, source, target);
		List<edgeFE> edgeList = path.getPathEdgeList();
		Set<BoardNode> nodesOnThePath = new HashSet<BoardNode>();
		
		if (edgeList.size() > 1) {
			for (edgeFE e : edgeList) {
				nodesOnThePath.add(e.getSource());
				nodesOnThePath.add(e.getTarget());
			}
			for (BoardNode n : nodesOnThePath) {
				// if there is an empty node on this path it better be the target position
				if (n.getPlayer() == null && n != target) return false;
			}	
		}
		// ELSE return true
		return true;
	}
	
	public boolean isValidMove(int player, int x, int y, char orientation) {
		// TODO implement this shit
		return false;
	}
	
	public void placeWall(int player, int x, int y, char orientation) {
		
		Player p = this.getPlayerPosition(player).getPlayer();
		
		if (orientation == 'v') {
			BoardNode firstSource = this.getNodeByCoords(x, y);
			BoardNode firstTarget = this.getNodeByCoords(x+1, y);
			BoardNode secondSource = this.getNodeByCoords(x, y+1);
			BoardNode secondTarget = this.getNodeByCoords(x+1, y+1);
			this.board.removeEdge(firstSource, firstTarget);
			this.board.removeEdge(secondSource, secondTarget);
		} else {          
			BoardNode firstSource = this.getNodeByCoords(x, y);
			BoardNode firstTarget = this.getNodeByCoords(x, y+1);
			BoardNode secondSource = this.getNodeByCoords(x+1, y);
			BoardNode secondTarget = this.getNodeByCoords(x+1, y+1);
			this.board.removeEdge(firstSource, firstTarget);
			this.board.removeEdge(secondSource, secondTarget);
		}
		p.decrementWalls();
		//TODO deal with the collection of walls placed
		//wallSet.add(x + " " + y + " " + orientation);
	}
	
	public void movePawn(int player, int x, int y) {
		BoardNode currentLocation = this.getPlayerPosition(player);
		BoardNode targetLocation = this.getNodeByCoords(x, y);
		Player p = currentLocation.getPlayer();
		
		targetLocation.setPlayer(p);
		currentLocation.setPlayer(null);
	}

}
