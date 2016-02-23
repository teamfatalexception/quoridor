package quoridorFE;

import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.generate.GridGraphGenerator;

public class BoardGraph {
	BoardNode[][] boardArray;
	
	UndirectedGraph<BoardNode, DefaultEdge> board;
	
	public BoardGraph() {
		// Gotta populate the board
		boardArray = new BoardNode[9][9];
		
		board = new SimpleGraph<BoardNode, DefaultEdge>(DefaultEdge.class);
		
		GridGraphGenerator<BoardNode, DefaultEdge> gridGen =
				new GridGraphGenerator<BoardNode, DefaultEdge>(9, 9);
		
		VertexFactory<BoardNode> vFactory =
	            new ClassBasedVertexFactory<BoardNode>(BoardNode.class);
		
		gridGen.generateGraph(board, vFactory, null);
		
		// Not sure where to go from here.
		// If "board" is all set up in a grid, we now have to
		// make sure the vertices correspond to board positions and
		// I'm not quite sure how to do that right now.
		
		
		/* leaving this here for now because I'm not sure if we'll want it later
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				boardArray[i][j] = new BoardNode(i, j);		
				board.addVertex(boardArray[i][j]);
			}
		}
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (i == 0 || i == 9 || j == 0)
				board.addEdge(boardArray[i][j], boardArray[i][j]);
			}
		}
		*/
		
	}


}
