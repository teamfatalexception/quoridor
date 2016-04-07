package quoridorFE;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Ignore;

public class TestAi {

	@Test
	public void testGetShitMove() {
		QuoridorBoard testBoard = new QuoridorBoard(new Player(1, "test1", 6666, 10, 4, 0), new Player(2, "test2", 6667, 10, 4, 8));
		String str = "(4, 1)";
		assertEquals("Expected " + str, str, FEai.getShitMove(1, testBoard));
	}

}
