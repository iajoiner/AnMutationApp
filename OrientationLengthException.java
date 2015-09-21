import java.io.*;
/** This class contains an orientation length exception. Thrown when the input length of orientation does not equal to n-1 in An quiver.
@author Ying Zhou
@version 0.1
*/
public class OrientationLengthException extends Exception{
	/** Throw an OrientationLengthException.
	@param actualLength the actual number of arrows in the input.
	@param intendedLength the intended number of arrows in an An quiver, which is n-1.
	*/
	OrientationLengthException(int actualLength, int intendedLength) {
		System.err.println("Size error! The size of orientations should be "+ intendedLength + " but now it is " + actualLength + ".");
	}
}
