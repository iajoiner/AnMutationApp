import java.io.*;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.Rule;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
/** This class tests the ordered clusters.
*/
public class TestOrderedCluster {
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
	public void testOrderedClusterNumber() throws OrientationLengthException{
		ArrayList<Boolean> givenOrientation = new ArrayList<Boolean>(3);
		givenOrientation.add(true);
		givenOrientation.add(true);
		givenOrientation.add(false);//1->2->3<-4
		OrderedCluster oc = new OrderedCluster(4, givenOrientation);
		assertEquals(4, (long)oc.getNumber());
	}
	@Test
	public void testOrderedClusterFirstIndex() throws OrientationLengthException{
		ArrayList<Boolean> givenOrientation = new ArrayList<Boolean>(3);
		givenOrientation.add(false);
		givenOrientation.add(true);
		givenOrientation.add(false);//1<-2->3<-4
		OrderedCluster oc = new OrderedCluster(4, givenOrientation);
		for (int counter = 0; counter < 4; counter++) {
			assertEquals((long)(oc.getFirstIndex().get(counter)),(long)counter);
		}
	}
	@Test
	public void testOrderedClusterSecondIndex() throws OrientationLengthException{
		ArrayList<Boolean> givenOrientation = new ArrayList<Boolean>(3);
		givenOrientation.add(true);
		givenOrientation.add(true);
		givenOrientation.add(false);//1->2->3<-4
		OrderedCluster oc = new OrderedCluster(4, givenOrientation);
		for (int counter = 0; counter < 4; counter++) {
			assertEquals((long)(oc.getSecondIndex().get(counter)),(long)counter + 1);
		}
	}
	@Test
	public void testOrderedClusterSign() throws OrientationLengthException{
		ArrayList<Boolean> givenOrientation = new ArrayList<Boolean>(3);
		givenOrientation.add(false);
		givenOrientation.add(true);
		givenOrientation.add(true);//1<-2->3->4
		OrderedCluster oc = new OrderedCluster(4, givenOrientation);
		for (int counter = 0; counter < 4; counter++) {
			assertEquals(oc.getSign().get(counter), true);
		}
	}
	@Test
	public void testOrderedClusterOrientation() throws OrientationLengthException{
		ArrayList<Boolean> givenOrientation = new ArrayList<Boolean>(3);
		givenOrientation.add(false);
		givenOrientation.add(true);
		givenOrientation.add(true);//1<-2->3->4
		OrderedCluster oc = new OrderedCluster(4, givenOrientation);
		assertEquals(oc.getOrientation().get(0), false);
		assertEquals(oc.getOrientation().get(1), true);
		assertEquals(oc.getOrientation().get(2), true);
	}
 	@Test(expected = OrientationLengthException.class)
	public void testOrderedClusterOrientationLengthError() throws OrientationLengthException{
		ArrayList<Boolean> givenOrientation = new ArrayList<Boolean>(3);
		givenOrientation.add(false);
		givenOrientation.add(true);
		givenOrientation.add(true);//1<-2->3->4
		OrderedCluster oc = new OrderedCluster(5, givenOrientation);
		assertEquals("Size error! The size of orientations should be 4 but now it is 3.\n", errContent.toString());
	} 
	@Test
	public void testOrderedClusterToString() throws OrientationLengthException{
		ArrayList<Boolean> givenOrientation = new ArrayList<Boolean>(3);
		givenOrientation.add(false);
		givenOrientation.add(true);
		givenOrientation.add(true);//1<-2->3->4
		OrderedCluster oc = new OrderedCluster(4, givenOrientation);
		assertEquals(oc.toString(), "{ 01 12 23 34 }");
	}
	@Test
	public void testOrderedClusterMutationAtPositionUnsuccessfulLeftOutOfBoundary() throws OrientationLengthException{
		ArrayList<Boolean> givenOrientation = new ArrayList<Boolean>(3);
		givenOrientation.add(false);
		givenOrientation.add(true);
		givenOrientation.add(true);//1<-2->3->4
		OrderedCluster oc = new OrderedCluster(4, givenOrientation);
		assertEquals(oc.toString(), "{ 01 12 23 34 }");
		assertEquals(false, oc.mutationAtPosition(0));
		assertEquals(oc.toString(), "{ 01 12 23 34 }");
	}
	@Test
	public void testOrderedClusterMutationAtPositionUnsuccessfulRightOutOfBoundary() throws OrientationLengthException{
		ArrayList<Boolean> givenOrientation = new ArrayList<Boolean>(3);
		givenOrientation.add(false);
		givenOrientation.add(true);
		givenOrientation.add(true);//1<-2->3->4
		OrderedCluster oc = new OrderedCluster(4, givenOrientation);
		assertEquals(oc.toString(), "{ 01 12 23 34 }");
		assertEquals(false, oc.mutationAtPosition(5));
		assertEquals(oc.toString(), "{ 01 12 23 34 }");
	}
	//Change this if we ever allow red mutations!
	@Test
	public void testOrderedClusterMutationAtPositionUnsucessfulRed() throws OrientationLengthException{
		ArrayList<Boolean> givenOrientation = new ArrayList<Boolean>(3);
		givenOrientation.add(false);
		givenOrientation.add(true);
		givenOrientation.add(true);//1<-2->3->4
		OrderedCluster oc = new OrderedCluster(4, givenOrientation);
		assertEquals(oc.toString(), "{ 01 12 23 34 }");
		assertEquals(true, oc.mutationAtPosition(2));
		assertEquals("{ 01 -12 23 34 }", oc.toString());
		assertEquals(false, oc.mutationAtPosition(2));
		assertEquals("{ 01 -12 23 34 }", oc.toString());
	}
	@Test
	public void testOrderedClusterMutationAtPositionSucessfulSource() throws OrientationLengthException{
		ArrayList<Boolean> givenOrientation = new ArrayList<Boolean>(3);
		givenOrientation.add(false);
		givenOrientation.add(true);
		givenOrientation.add(true);//1<-2->3->4
		OrderedCluster oc = new OrderedCluster(4, givenOrientation);
		assertEquals(oc.toString(), "{ 01 12 23 34 }");
		assertEquals(true, oc.mutationAtPosition(2));//Not left type 1, not right type 1.
		assertEquals("{ 01 -12 23 34 }", oc.toString());
	}
	@Test
	public void testOrderedClusterMutationAtPositionSucessfulMiddle() throws OrientationLengthException{
		ArrayList<Boolean> givenOrientation = new ArrayList<Boolean>(3);
		givenOrientation.add(false);
		givenOrientation.add(true);
		givenOrientation.add(true);//1<-2->3->4
		OrderedCluster oc = new OrderedCluster(4, givenOrientation);
		assertEquals(oc.toString(), "{ 01 12 23 34 }");
		oc.mutationAtPosition(3);//Left type 1, Not right type 1.
		assertEquals("{ 01 13 -23 34 }", oc.toString());
	}
	@Test
	public void testOrderedClusterMutationAtPositionSucessfulSinkMGS() throws OrientationLengthException{
		ArrayList<Boolean> givenOrientation = new ArrayList<Boolean>(3);
		givenOrientation.add(false);
		givenOrientation.add(true);
		givenOrientation.add(true);//1<-2->3->4
		OrderedCluster oc = new OrderedCluster(4, givenOrientation);
		assertEquals(oc.toString(), "{ 01 12 23 34 }");
		oc.mutationAtPosition(4);//Left type 1.
		assertEquals("{ 01 12 24 -34 }", oc.toString());
		oc.mutationAtPosition(2);//Not left type 1, not right type 1.
		assertEquals("{ 01 -12 24 -34 }", oc.toString());
		oc.mutationAtPosition(3);//Not left type 1, right type 3.
		assertEquals("{ 01 -12 -24 23 }", oc.toString());
		oc.mutationAtPosition(1);//Not right type 1.
		assertEquals("{ -01 -12 -24 23 }", oc.toString());
		oc.mutationAtPosition(4);//Not left type 1, right type 2.
		assertEquals("{ -01 -12 -34 -23 }", oc.toString());		
	}
	@Test
	public void testOrderedClusterMutationAtPositionSucessfulLongMGS() throws OrientationLengthException{
		ArrayList<Boolean> givenOrientation = new ArrayList<Boolean>(3);
		givenOrientation.add(false);
		givenOrientation.add(true);
		givenOrientation.add(false);//1<-2->3<-4
		OrderedCluster oc = new OrderedCluster(4, givenOrientation);
		assertEquals(oc.toString(), "{ 01 12 23 34 }");
		oc.mutationAtPosition(3);//Left type 1, right type 1.
		assertEquals("{ 01 13 -23 24 }", oc.toString());
		oc.mutationAtPosition(1);//Right type 1.
		assertEquals("{ -01 03 -23 24 }", oc.toString());
		oc.mutationAtPosition(4);//Left type 3.
		assertEquals("{ -01 03 34 -24 }", oc.toString());
		oc.mutationAtPosition(2);//Left type 3,right type 1.
		assertEquals("{ 13 -03 04 -24 }", oc.toString());
		oc.mutationAtPosition(3);//Left type 3,right type 3.
		assertEquals("{ 13 34 -04 02 }", oc.toString());
		oc.mutationAtPosition(1);//Left type 1.
		assertEquals("{ -13 14 -04 02 }", oc.toString());	
		oc.mutationAtPosition(2);//Left type 2,left type 3.
		assertEquals("{ 34 -14 -01 02 }", oc.toString());
		oc.mutationAtPosition(4);//Left type 3.
		assertEquals("{ 34 -14 12 -02 }", oc.toString());
		oc.mutationAtPosition(1);//Right type 2.
		assertEquals("{ -34 -13 12 -02 }", oc.toString());
		oc.mutationAtPosition(3);//Left type 2, right type 2.
		assertEquals("{ -34 -23 -12 -01 }", oc.toString());
	}
	@Test
	public void testOrderedClusterMutationAtRootSucessfulLongMGS() throws OrientationLengthException{
		ArrayList<Boolean> givenOrientation = new ArrayList<Boolean>(3);
		givenOrientation.add(false);
		givenOrientation.add(true);
		givenOrientation.add(false);//1<-2->3<-4
		OrderedCluster oc = new OrderedCluster(4, givenOrientation);
		assertEquals(oc.toString(), "{ 01 12 23 34 }");
		oc.mutationAtRoot(2,3);//Left type 1, right type 1.
		assertEquals("{ 01 13 -23 24 }", oc.toString());
		oc.mutationAtRoot(0,1);//Right type 1.
		assertEquals("{ -01 03 -23 24 }", oc.toString());
		oc.mutationAtRoot(2,4);//Left type 3.
		assertEquals("{ -01 03 34 -24 }", oc.toString());
		oc.mutationAtRoot(0,3);//Left type 3,right type 1.
		assertEquals("{ 13 -03 04 -24 }", oc.toString());
		oc.mutationAtRoot(0,4);//Left type 3,right type 3.
		assertEquals("{ 13 34 -04 02 }", oc.toString());
		oc.mutationAtRoot(1,3);//Left type 1.
		assertEquals("{ -13 14 -04 02 }", oc.toString());	
		oc.mutationAtRoot(1,4);//Left type 2,left type 3.
		assertEquals("{ 34 -14 -01 02 }", oc.toString());
		oc.mutationAtRoot(0,2);//Left type 3.
		assertEquals("{ 34 -14 12 -02 }", oc.toString());
		oc.mutationAtRoot(3,4);//Right type 2.
		assertEquals("{ -34 -13 12 -02 }", oc.toString());
		oc.mutationAtRoot(1,2);//Left type 2, right type 2.
		assertEquals("{ -34 -23 -12 -01 }", oc.toString());
	}
	//Change this method if red mutations are ever allowed!
	@Test
	public void testOrderedClusterMutationAtRootUnsucessful() throws OrientationLengthException{
		ArrayList<Boolean> givenOrientation = new ArrayList<Boolean>(3);
		givenOrientation.add(false);
		givenOrientation.add(true);
		givenOrientation.add(true);//1<-2->3->4
		OrderedCluster oc = new OrderedCluster(4, givenOrientation);
		assertEquals(oc.toString(), "{ 01 12 23 34 }");
		assertEquals(false,oc.mutationAtRoot(-1,2));//Bad root.
		assertEquals("{ 01 12 23 34 }", oc.toString());
		assertEquals(false,oc.mutationAtRoot(1,5));//Bad root.
		assertEquals("{ 01 12 23 34 }", oc.toString());
		assertEquals(false,oc.mutationAtRoot(1,1));//Bad root.
		assertEquals("{ 01 12 23 34 }", oc.toString());
		assertEquals(false,oc.mutationAtRoot(3,2));//Bad root.
		assertEquals("{ 01 12 23 34 }", oc.toString());
		assertEquals(false,oc.mutationAtRoot(0,2));//Root not found.
		assertEquals("{ 01 12 23 34 }", oc.toString());
		assertEquals(true,oc.mutationAtRoot(1,2));//Existing green mutation.
		assertEquals("{ 01 -12 23 34 }", oc.toString());
		assertEquals(false,oc.mutationAtRoot(1,2));//Red mutation not allowed.
		assertEquals("{ 01 -12 23 34 }", oc.toString());
	}
	@Test
	public void testOrderedClusterIsRed() throws OrientationLengthException{
		ArrayList<Boolean> givenOrientation = new ArrayList<Boolean>(3);
		givenOrientation.add(false);
		givenOrientation.add(true);
		givenOrientation.add(true);//1<-2->3->4
		OrderedCluster oc = new OrderedCluster(4, givenOrientation);
		assertEquals(false, oc.isRed());
		assertEquals(oc.toString(), "{ 01 12 23 34 }");
		oc.mutationAtPosition(4);//Left type 1.
		assertEquals("{ 01 12 24 -34 }", oc.toString());
		assertEquals(false, oc.isRed());
		oc.mutationAtPosition(2);//Not left type 1, not right type 1.
		assertEquals("{ 01 -12 24 -34 }", oc.toString());
		assertEquals(false, oc.isRed());
		oc.mutationAtPosition(3);//Not left type 1, right type 3.
		assertEquals("{ 01 -12 -24 23 }", oc.toString());
		assertEquals(false, oc.isRed());
		oc.mutationAtPosition(1);//Not right type 1.
		assertEquals("{ -01 -12 -24 23 }", oc.toString());
		assertEquals(false, oc.isRed());
		oc.mutationAtPosition(4);//Not left type 1, right type 2.
		assertEquals("{ -01 -12 -34 -23 }", oc.toString());
		assertEquals(true, oc.isRed());
	}
	@Test
	public void testOrderedClusterGetPermutationOfRedClusterStrangeOrientation() throws OrientationLengthException{
		ArrayList<Boolean> givenOrientation = new ArrayList<Boolean>(3);
		givenOrientation.add(false);
		givenOrientation.add(true);
		givenOrientation.add(true);//1<-2->3->4
		OrderedCluster oc = new OrderedCluster(4, givenOrientation);
		assertEquals(false, oc.isRed());
		assertEquals(oc.toString(), "{ 01 12 23 34 }");
		oc.mutationAtPosition(4);//Left type 1.
		assertEquals("{ 01 12 24 -34 }", oc.toString());
		assertEquals(false, oc.isRed());
		oc.mutationAtPosition(2);//Not left type 1, not right type 1.
		assertEquals("{ 01 -12 24 -34 }", oc.toString());
		assertEquals(false, oc.isRed());
		oc.mutationAtPosition(3);//Not left type 1, right type 3.
		assertEquals(null, oc.getPermutationOfRedCluster());
		assertEquals("{ 01 -12 -24 23 }", oc.toString());
		assertEquals(false, oc.isRed());
		oc.mutationAtPosition(1);//Not right type 1.
		assertEquals("{ -01 -12 -24 23 }", oc.toString());
		assertEquals(false, oc.isRed());
		assertEquals(null, oc.getPermutationOfRedCluster());
		oc.mutationAtPosition(4);//Not left type 1, right type 2.
		assertEquals("{ -01 -12 -34 -23 }", oc.toString());
		assertEquals(true, oc.isRed());
		ArrayList<Integer> targetPermutation = new ArrayList<Integer>();
		targetPermutation.add(1);
		targetPermutation.add(2);
		targetPermutation.add(4);
		targetPermutation.add(3);		
		assertEquals(targetPermutation, oc.getPermutationOfRedCluster());
	}
	@Test
	public void testOrderedClusterGetPermutationOfRedCluster2() throws OrientationLengthException{
		ArrayList<Boolean> givenOrientation = new ArrayList<Boolean>(3);
		givenOrientation.add(false);
		givenOrientation.add(true);
		givenOrientation.add(false);//1<-2->3<-4
		OrderedCluster oc = new OrderedCluster(4, givenOrientation);
		assertEquals(oc.toString(), "{ 01 12 23 34 }");
		oc.mutationAtRoot(2,3);//Left type 1, right type 1.
		assertEquals("{ 01 13 -23 24 }", oc.toString());
		oc.mutationAtRoot(0,1);//Right type 1.
		assertEquals("{ -01 03 -23 24 }", oc.toString());
		oc.mutationAtRoot(2,4);//Left type 3.
		assertEquals("{ -01 03 34 -24 }", oc.toString());
		oc.mutationAtRoot(0,3);//Left type 3,right type 1.
		assertEquals("{ 13 -03 04 -24 }", oc.toString());
		oc.mutationAtRoot(0,4);//Left type 3,right type 3.
		assertEquals("{ 13 34 -04 02 }", oc.toString());
		oc.mutationAtRoot(1,3);//Left type 1.
		assertEquals("{ -13 14 -04 02 }", oc.toString());	
		oc.mutationAtRoot(1,4);//Left type 2,left type 3.
		assertEquals("{ 34 -14 -01 02 }", oc.toString());
		oc.mutationAtRoot(0,2);//Left type 3.
		assertEquals("{ 34 -14 12 -02 }", oc.toString());
		oc.mutationAtRoot(3,4);//Right type 2.
		assertEquals("{ -34 -13 12 -02 }", oc.toString());
		oc.mutationAtRoot(1,2);//Left type 2, right type 2.
		assertEquals("{ -34 -23 -12 -01 }", oc.toString());
		ArrayList<Integer> targetPermutation = new ArrayList<Integer>();
		targetPermutation.add(4);
		targetPermutation.add(3);
		targetPermutation.add(2);
		targetPermutation.add(1);		
		assertEquals(targetPermutation, oc.getPermutationOfRedCluster());
	}
	@Test
	public void testOrderedClusterCopyingMGS() throws OrientationLengthException{
		ArrayList<Boolean> givenOrientation = new ArrayList<Boolean>(3);
		givenOrientation.add(false);
		givenOrientation.add(true);
		givenOrientation.add(true);//1<-2->3->4
		OrderedCluster oc = new OrderedCluster(4, givenOrientation);
		assertEquals(oc.toString(), "{ 01 12 23 34 }");
		oc.mutationAtPosition(4);//Left type 1.
		assertEquals("{ 01 12 24 -34 }", oc.toString());
		oc.mutationAtPosition(2);//Not left type 1, not right type 1.
		assertEquals("{ 01 -12 24 -34 }", oc.toString());
		oc.mutationAtPosition(3);//Not left type 1, right type 3.
		assertEquals("{ 01 -12 -24 23 }", oc.toString());
		oc.mutationAtPosition(1);//Not right type 1.
		assertEquals("{ -01 -12 -24 23 }", oc.toString());
		oc.mutationAtPosition(4);//Not left type 1, right type 2.
		assertEquals("{ -01 -12 -34 -23 }", oc.toString());	
		OrderedCluster noc = new OrderedCluster(oc);
		assertEquals("{ -01 -12 -34 -23 }", noc.toString());
		assertEquals(3, noc.orientation.size());
		for (int counter = 0; counter < 3; counter++) {
			assertEquals(oc.orientation.get(counter), noc.orientation.get(counter));
		}
	}
	@Test
	public void testOrderedClusterNextMutationMGS() throws OrientationLengthException{
		ArrayList<Boolean> givenOrientation = new ArrayList<Boolean>(3);
		givenOrientation.add(false);
		givenOrientation.add(true);
		givenOrientation.add(true);//1<-2->3->4
		OrderedCluster oc = new OrderedCluster(4, givenOrientation);
		assertEquals(oc.toString(), "{ 01 12 23 34 }");
		assertEquals(1, oc.nextMutation(0));
		assertEquals(2, oc.nextMutation(1));
		assertEquals(3, oc.nextMutation(2));
		assertEquals(4, oc.nextMutation(3));
		assertEquals(-1, oc.nextMutation(4));
		assertEquals(-1, oc.nextMutation(5));
		assertEquals(-1, oc.nextMutation(-1));
		oc.mutationAtPosition(4);//Left type 1.
		assertEquals("{ 01 12 24 -34 }", oc.toString());
		oc.mutationAtPosition(2);//Not left type 1, not right type 1.
		assertEquals("{ 01 -12 24 -34 }", oc.toString());
		assertEquals(1, oc.nextMutation(0));
		assertEquals(3, oc.nextMutation(1));
		assertEquals(3, oc.nextMutation(2));
		assertEquals(-1, oc.nextMutation(3));
		assertEquals(-1, oc.nextMutation(4));
		oc.mutationAtPosition(3);//Not left type 1, right type 3.
		assertEquals("{ 01 -12 -24 23 }", oc.toString());
		assertEquals(1, oc.nextMutation(0));
		assertEquals(4, oc.nextMutation(1));
		assertEquals(4, oc.nextMutation(2));
		assertEquals(4, oc.nextMutation(3));
		assertEquals(-1, oc.nextMutation(4));
		oc.mutationAtPosition(1);//Not right type 1.
		assertEquals("{ -01 -12 -24 23 }", oc.toString());
		oc.mutationAtPosition(4);//Not left type 1, right type 2.
		assertEquals("{ -01 -12 -34 -23 }", oc.toString());	
		OrderedCluster noc = new OrderedCluster(oc);
		assertEquals("{ -01 -12 -34 -23 }", noc.toString());
		assertEquals(-1, oc.nextMutation(0));
		assertEquals(-1, oc.nextMutation(1));
		assertEquals(-1, oc.nextMutation(2));
		assertEquals(-1, oc.nextMutation(3));
		assertEquals(-1, oc.nextMutation(4));
		assertEquals(3, noc.orientation.size());
		for (int counter = 0; counter < 3; counter++) {
			assertEquals(oc.orientation.get(counter), noc.orientation.get(counter));
		}
	}
	@Test
	public void testOrderedClusterFindRootMGS() throws OrientationLengthException{
		ArrayList<Boolean> givenOrientation = new ArrayList<Boolean>(3);
		givenOrientation.add(false);
		givenOrientation.add(true);
		givenOrientation.add(true);//1<-2->3->4
		OrderedCluster oc = new OrderedCluster(4, givenOrientation);
		assertEquals(oc.toString(), "{ 01 12 23 34 }");
		oc.mutationAtPosition(4);//Left type 1.
		assertEquals("{ 01 12 24 -34 }", oc.toString());
		assertEquals(1, oc.findRoot(0,1));
		assertEquals(3, oc.findRoot(2,4));
		assertEquals(0, oc.findRoot(2,3));
		assertEquals(0, oc.findRoot(3,4));
		oc.mutationAtPosition(2);//Not left type 1, not right type 1.
		assertEquals("{ 01 -12 24 -34 }", oc.toString());
		oc.mutationAtPosition(3);//Not left type 1, right type 3.
		assertEquals("{ 01 -12 -24 23 }", oc.toString());
		oc.mutationAtPosition(1);//Not right type 1.
		assertEquals("{ -01 -12 -24 23 }", oc.toString());
		assertEquals(0, oc.findRoot(0,1));
		assertEquals(0, oc.findRoot(2,4));
		assertEquals(4, oc.findRoot(2,3));
		assertEquals(0, oc.findRoot(3,4));
		oc.mutationAtPosition(4);//Not left type 1, right type 2.
		assertEquals("{ -01 -12 -34 -23 }", oc.toString());		
	}
}
