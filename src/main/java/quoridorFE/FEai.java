package quoridorFE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.DijkstraShortestPath;

public class FEai {

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
		
		// Now I've gotta get the first node on the path that isn't my current pawn position.
		BoardNode firstTry = edgeList.get(0).getTarget();
		
		String retStr = firstTry.toString();
		
		return retStr;
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
