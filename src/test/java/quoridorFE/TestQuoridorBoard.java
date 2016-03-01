package quoridorFE;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestQuoridorBoard {

	@Test
	public void testQuoridorBoard2Player() {
		QuoridorBoard testBoard = new QuoridorBoard(new Player(1, "test1", 6666, 10, 4, 0), new Player(2, "test2", 6667, 10, 4, 8));
		int player1Xpos = 4;
		int player1Ypos = 0;
		int player2Xpos = 4;
		int player2Ypos = 8;
		
		BoardNode n1 = testBoard.getNodeByCoords(player1Xpos, player1Ypos);
		BoardNode n2 = testBoard.getNodeByCoords(player2Xpos, player2Ypos);
		
		assertEquals("Player 1 is not where it should be.", n1.getPlayer().getID(), 1);
		assertEquals("Player 2 is not where it should be.", n2.getPlayer().getID(), 2);
	}

	@Test
	public void testQuoridorBoard4Player() {
		QuoridorBoard testBoard = new QuoridorBoard(new Player(1, "test1", 6666, 5, 4, 0), new Player(2, "test2", 6667, 5, 4, 8),
													new Player(3, "test3", 6668, 5, 0, 4), new Player(4, "test4", 6669, 5, 8, 4));
		
		BoardNode n1 = testBoard.getNodeByCoords(4, 0);
		BoardNode n2 = testBoard.getNodeByCoords(4, 8);
		BoardNode n3 = testBoard.getNodeByCoords(0, 4);
		BoardNode n4 = testBoard.getNodeByCoords(8, 4);
		
		assertEquals("Player 1 is not where it should be.", n1.getPlayer().getID(), 1);
		assertEquals("Player 2 is not where it should be.", n2.getPlayer().getID(), 2);
		assertEquals("Player 3 is not where it should be.", n3.getPlayer().getID(), 3);
		assertEquals("Player 4 is not where it should be.", n4.getPlayer().getID(), 4);
	}

	@Test
	public void testGetNodeByCoords() {
		QuoridorBoard testBoard = new QuoridorBoard(new Player(1, "test1", 6666, 10, 4, 0), new Player(2, "test2", 6667, 10, 4, 8));
		BoardNode node = testBoard.getNodeByCoords(2, 0);
		
		assertEquals("X value was not as expected", 2, node.getxPos());
		assertEquals("Y value was not as expected", 0, node.getyPos());
	}

	@Test
	public void testGetPlayerPositionInt() {
		QuoridorBoard testBoard = new QuoridorBoard(new Player(1, "test1", 6666, 10, 4, 0), new Player(2, "test2", 6667, 10, 4, 8));
		BoardNode node2 = testBoard.getPlayerPosition(2);
		
		assertEquals("X value was not as expected.", 4, node2.getxPos());
		assertEquals("Y value was not as expected.", 8, node2.getyPos());
	}

	

	@Test
	public void testIsValidMoveIntIntIntIntInt() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testIsValidMoveIntIntIntChar() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testPlaceWall() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testMovePawn() {
		QuoridorBoard testBoard = new QuoridorBoard(new Player(1, "test1", 6666, 10, 4, 0), new Player(2, "test2", 6667, 10, 4, 8));
		int newX = 4;
		int newY = 1;
		testBoard.movePawn(1, newX, newY);
		
		assertEquals("X value was not as expected.", newX, testBoard.getPlayerPosition(1).getxPos());
		assertEquals("Y value was not as expected.", newY, testBoard.getPlayerPosition(1).getyPos());
	}

}
