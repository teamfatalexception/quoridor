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
		String cmpstr = FEai.getShitMove(1, testBoard);
		assertEquals("Expected " + str, str, cmpstr);
		
		testBoard.movePawn(1, 4, 1);
		testBoard.movePawn(1, 4, 2);
		testBoard.movePawn(1, 4, 3);
		testBoard.movePawn(1, 4, 4);
		testBoard.movePawn(2, 4, 7);
		testBoard.movePawn(2, 4, 6);
		testBoard.movePawn(2, 4, 5);
		// pawn 1 is at (4, 4) and 2 is at (4, 5)
		str = "(4, 6)";
		cmpstr = FEai.getShitMove(1, testBoard);
		assertEquals("Single jump, expected " + str + " Recieved: " + cmpstr, str, cmpstr);
		// tested single jump
		
		testBoard.movePawn(3, 0, 5);
		testBoard.movePawn(3, 0, 6);
		testBoard.movePawn(3, 1, 6);
		testBoard.movePawn(3, 2, 6);
		testBoard.movePawn(3, 3, 6);
		testBoard.movePawn(3, 4, 6);
		// pawn 1 is at (4, 4), 2 is at (4, 5), and 3 is at (4, 6)
		str = "(4, 7)";
		cmpstr = FEai.getShitMove(1, testBoard);
		assertEquals("Double jump, expected " + str + " Recieved: " + cmpstr, str, cmpstr);
		// tested double jump
		
		testBoard.movePawn(4, 8, 5);
		testBoard.movePawn(4, 8, 6);
		testBoard.movePawn(4, 8, 7);
		testBoard.movePawn(4, 7, 7);
		testBoard.movePawn(4, 6, 7);
		testBoard.movePawn(4, 5, 7);
		testBoard.movePawn(4, 4, 7);
		// pawn 1 is at (4, 4), 2 is at (4, 5), 3 is at (4, 6), and 4 is at (4, 7)
		str = "(4, 8)";
		cmpstr = FEai.getShitMove(1, testBoard);
		assertEquals("Triple jump, expected " + str + " Recieved: " + cmpstr, str, cmpstr);
		// tested triple jump
		
	}
	
	@Ignore
	@Test
	public void testGetShitMoveWithBlockingEnemy() {
		QuoridorBoard testBoard = new QuoridorBoard(new Player(1, "TST", "test1", 5), 
													new Player(2, "TST", "test2", 5),
													new Player(3, "TST", "test3", 5),
													new Player(4, "TST", "test4", 5));

			String str = "(4, 1)";
			String cmpstr = FEai.getShitMove(1, testBoard);
			assertEquals("Expected " + str, str, cmpstr);
			
			testBoard.movePawn(1, 4, 1);
			testBoard.movePawn(1, 4, 2);
			testBoard.movePawn(1, 4, 3);
			testBoard.movePawn(1, 4, 4);
			testBoard.movePawn(1, 4, 5);
			// pawn 1 is at (4, 5)
			testBoard.movePawn(2, 4, 7);
			testBoard.movePawn(2, 4, 6);
			// pawn 2 is at (4, 6)
			testBoard.movePawn(3, 0, 5);
			testBoard.movePawn(3, 0, 6);
			testBoard.movePawn(3, 0, 7);
			testBoard.movePawn(3, 1, 7);
			testBoard.movePawn(3, 2, 7);
			testBoard.movePawn(3, 3, 7);
			testBoard.movePawn(3, 4, 7);
			// pawn 3 is at (4, 7)
			testBoard.movePawn(4, 8, 5);
			testBoard.movePawn(4, 8, 6);
			testBoard.movePawn(4, 8, 7);
			testBoard.movePawn(4, 8, 8);
			testBoard.movePawn(4, 7, 8);
			testBoard.movePawn(4, 6, 8);
			testBoard.movePawn(4, 5, 8);
			testBoard.movePawn(4, 4, 8);
			// pawn 4 is at (4, 8)
			
			// 4/13/2014 AV - This test is here to highlight how the shit AI might return a bad move if it's shortest path to victory ends on an occupied space. 
			
			cmpstr = FEai.getShitMove(1, testBoard);
			assertTrue("Expected a valid move to be returned by FEai.getShitMove()", testBoard.isValidMove(1, Integer.parseInt(Character.toString(cmpstr.charAt(1))), Integer.parseInt(Character.toString(cmpstr.charAt(4)))));
	}
	
	@Test
	public void testDefendCloseOpponents() {
		// Build testboard
		QuoridorBoard testBoard = new QuoridorBoard(new Player(1, "TST", "test1", 5), 
					    new Player(2, "TST", "test2", 5),
					    new Player(3, "TST", "test3", 5),
					    new Player(4, "TST", "test4", 5));
		// Move player 2 to 4, 3
		testBoard.movePawn(2, 4, 3);
		FEai testAI = new FEai();
		String blockingWall = testAI.getMove(1, testBoard);// Asking for a move
		assertEquals(blockingWall, "[(4, 3), h]" );	
	}

}
