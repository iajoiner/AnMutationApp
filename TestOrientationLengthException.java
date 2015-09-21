import java.io.*;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
/** This class tests the orientation length exception.
*/
public class TestOrientationLengthException {
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	@Before
	public void setUpStream() {
		System.setErr(new PrintStream(errContent));
	}
	@After
	public void cleanUpStream() {
		System.setErr(null);
	}
	@Test
	public void testOrientationLengthException() {
		OrientationLengthException ole = new OrientationLengthException(3,4);
		assertEquals("Size error! The size of orientations should be 4 but now it is 3.\n", errContent.toString());
	}
}
