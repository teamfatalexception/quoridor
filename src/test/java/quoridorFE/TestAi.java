package quoridorFE;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Ignore;

public class TestAi {

	@Test
	public void testGetShitMove() {
		QuoridorBoard testBoard = new QuoridorBoard(new Player(1, "TST", "test1", 5), 
													new Player(2, "TST", "test2", 5),
													new Player(3, "TST", "test3", 5),
													new Player(4, "TST", "test4", 5));
		
		String str = "(4, 1)";
		assertEquals("Expected " + str, str, FEai.getShitMove(1, testBoard));
		
		testBoard.movePawn(1, 4, 1);
		testBoard.movePawn(1, 4, 2);
		testBoard.movePawn(1, 4, 3);
		testBoard.movePawn(1, 4, 4);
		testBoard.movePawn(2, 4, 7);
		testBoard.movePawn(2, 4, 6);
		testBoard.movePawn(2, 4, 5);
		// pawn 1 is at (4, 4) and 2 is at (4, 5)
		str = "(4, 6)";
		assertEquals("Expected " + str, str, FEai.getShitMove(1, testBoard));
		
	}

}
