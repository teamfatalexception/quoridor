import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.assertEquals;
public class objectTests{

	@Test
	public void playerTest(){

		Player kyle = new Player(1, "Kyle", 8080, 0, 4, 4);
		assertEquals(kyle.getName(), "Kyle");
	}

}
