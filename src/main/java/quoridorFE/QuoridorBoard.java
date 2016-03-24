/**
* The QuoridorBoard class implements the Quoridor board and methods for interacting with it.
* 
* Player objects passed to the constructor are assumed to be unique and 
* class methods may not behave as expected if there are player objects with duplicate IDs on the board.
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
import org.jgrapht.Graphs;
import org.jgrapht.graph.ClassBasedVertexFactory;
import org.jgrapht.graph.SimpleGraph;

public class QuoridorBoard implements Cloneable{
	
	private class Wall {
		int x;
		int y;
		char orientation;
		
		public Wall(int x, int y, char orientation) {
			super();
			this.x = x;
			this.y = y;
			this.orientation = orientation;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + orientation;
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Wall other = (Wall) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (orientation != other.orientation)
				return false;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}

		private QuoridorBoard getOuterType() {
			return QuoridorBoard.this;
		}
		
	}
	
	UndirectedGraph<BoardNode, edgeFE> board;
	
	HashSet<Player> playerSet; // TODO decide if this part is even needed (prolly isn't)
	
	private int numPlayers;
	
	private HashSet<Wall> wallSet;
	private HashSet<Wall> invalidWallSet;
	
	
	public QuoridorBoard(Player player1, Player player2) {
		this(player1, player2, null, null);
	}
	
	
	public QuoridorBoard(Player player1, Player player2, Player player3, Player player4) {
		playerSet = new HashSet<Player>();
		wallSet = new HashSet<Wall>();
		invalidWallSet = new HashSet<Wall>();
		
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
		playerSet.add(player1);
		playerSet.add(player2);
		if (player3 != null && player4 != null) {
			numPlayers = 4;
			this.getNodeByCoords(0, 4).setPlayer(player3);
			this.getNodeByCoords(8, 4).setPlayer(player4);
			playerSet.add(player3);
			playerSet.add(player4);
		} else {
			numPlayers = 2;
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
	
	private BoardNode getNodeByCoords(int x, int y, SimpleGraph<BoardNode, edgeFE> board) {
		for (BoardNode n : board.vertexSet()) {
			if (n.getxPos() == x && n.getyPos() == y) {
				return n;
			}
		}
		return null;
	}

	public BoardNode getNodeByPlayerNumber(int player) {
		for (BoardNode n : this.board.vertexSet()) {
			if (n.getPlayer() != null) {
				if (player == n.getPlayer().getID()) {
					return n;
				}
			}
		}
		return null;
	}
	
	private BoardNode getNodeByPlayerNumber(int player, SimpleGraph<BoardNode, edgeFE> board) {
		for (BoardNode n : board.vertexSet()) {
			if (n.getPlayer() != null) {
				if (player == n.getPlayer().getID()) {
					return n;
				}
			}
		}
		return null;
	}
	
	public HashSet<Wall> getWallSet() {
		return wallSet;
	}


	public boolean isValidMove(int player, int x, int y) {
		
		if (x > 8 || y > 8) return false; // Move is out of bounds.
		
		BoardNode source = this.getNodeByPlayerNumber(player);
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
		// check for out of bounds
		if (x > 7 || y > 7){ 
			//throw new IllegalMoveException("out of bounds");
			return false;
		}
		// check number of walls left
		if (this.getNodeByPlayerNumber(player).getPlayer().wallsLeft() == 0) {
			//throw new IllegalMoveException("number of walls");
			return false;
		}
		
		// check wall against list of invalid walls
		if (invalidWallSet.contains(new Wall(x, y, orientation))) {
			//throw new IllegalMoveException("number of walls");
			return false;
		}
		
		// check for path to win condition
		// CAN'T CLONE GRAPH
		SimpleGraph<BoardNode, edgeFE> boardCopy = new SimpleGraph<BoardNode, edgeFE>(edgeFE.class);
		Graphs.addGraph(boardCopy, this.board);
		
		if (orientation == 'v') {
			BoardNode firstSource = this.getNodeByCoords(x, y, boardCopy);
			BoardNode firstTarget = this.getNodeByCoords(x+1, y, boardCopy);
			BoardNode secondSource = this.getNodeByCoords(x, y+1, boardCopy);
			BoardNode secondTarget = this.getNodeByCoords(x+1, y+1, boardCopy);
			boardCopy.removeEdge(firstSource, firstTarget);
			boardCopy.removeEdge(secondSource, secondTarget);
		} else {
			BoardNode firstSource = this.getNodeByCoords(x, y, boardCopy);
			BoardNode firstTarget = this.getNodeByCoords(x, y+1, boardCopy);
			BoardNode secondSource = this.getNodeByCoords(x+1, y, boardCopy);
			BoardNode secondTarget = this.getNodeByCoords(x+1, y+1, boardCopy);
			boardCopy.removeEdge(firstSource, firstTarget);
			boardCopy.removeEdge(secondSource, secondTarget);
		}
		
		
		//boolean pathExists = false;
		for(Player p : this.playerSet) {
			boolean pathExists = false;
			for (int i = 0; i < 9; i++) {
				if (p.getID() == 1) {	
					if (DijkstraShortestPath.findPathBetween(boardCopy, this.getNodeByPlayerNumber(p.getID(), boardCopy), this.getNodeByCoords(i, 8, boardCopy)) != null) {
						// if there is no path, check next node
						// if there are no paths, return false
						pathExists = true;
						break;
					}
				} else if (p.getID() == 2) {
					if (DijkstraShortestPath.findPathBetween(boardCopy, this.getNodeByPlayerNumber(p.getID(), boardCopy), this.getNodeByCoords(i, 0, boardCopy)) != null) {
						// if there is no path, check next node
						// if there are no paths, return false
						pathExists = true;
						break;
					}
				} else if (p.getID() == 3) {
					if (DijkstraShortestPath.findPathBetween(boardCopy, this.getNodeByPlayerNumber(p.getID(), boardCopy), this.getNodeByCoords(8, i, boardCopy)) != null) {
						// if there is no path, check next node
						// if there are no paths, return false
						pathExists = true;
						break;
					}
				} else if (p.getID() == 4) {
					if (DijkstraShortestPath.findPathBetween(boardCopy, this.getNodeByPlayerNumber(p.getID(), boardCopy), this.getNodeByCoords(0, i, boardCopy)) != null) {
						// if there is no path, check next node
						// if there are no paths, return false
						pathExists = true;
						break;
					}
				}
			}
			if (!pathExists) {
				//throw new IllegalMoveException("path to victory");
				return false;
			}
		}
		
		
		
		// if you made it here, then it must be a valid move
		return true;
	}
	
	public void placeWall(int player, int x, int y, char orientation) {
		if (this.isValidMove(player, x, y, orientation) == false) throw new IllegalMoveException("You fucked up, scrub.");
		
		Player p = this.getNodeByPlayerNumber(player).getPlayer();
		
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
		
		Wall placedWall = new Wall(x, y, orientation);
		wallSet.add(placedWall);
		generateInvalidWalls(placedWall);
	}
	
	private void placeWallUnchecked(int player, int x, int y, char orientation) {
		Player p = this.getNodeByPlayerNumber(player).getPlayer();
		
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
		
		Wall placedWall = new Wall(x, y, orientation);
		wallSet.add(placedWall);
		generateInvalidWalls(placedWall);
	}
	
	private void generateInvalidWalls(Wall placedWall) {
		if (placedWall.orientation == 'h') {
			invalidWallSet.add(new Wall(placedWall.x, placedWall.y, 'v')); 
			if (placedWall.x > 0) invalidWallSet.add(new Wall(placedWall.x - 1, placedWall.y, 'h'));
			if (placedWall.x < 7) invalidWallSet.add(new Wall(placedWall.x + 1, placedWall.y, 'h'));
		} else {
			invalidWallSet.add(new Wall(placedWall.x, placedWall.y, 'h'));
			if (placedWall.y > 0) invalidWallSet.add(new Wall(placedWall.x, placedWall.y - 1, 'v'));
			if (placedWall.y < 7) invalidWallSet.add(new Wall(placedWall.x, placedWall.y + 1, 'v'));
		}
	}
	
	public void movePawn(int player, int x, int y) {
		if (this.isValidMove(player, x, y) == false) throw new IllegalMoveException("You fucked up, scrub.");
		
		BoardNode currentLocation = this.getNodeByPlayerNumber(player);
		BoardNode targetLocation = this.getNodeByCoords(x, y);
		Player p = currentLocation.getPlayer();
		
		targetLocation.setPlayer(p);
		currentLocation.setPlayer(null);
	}


}
