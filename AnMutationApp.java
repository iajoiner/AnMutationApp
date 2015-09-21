import java.io.*;
import java.util.ArrayList;
/**
Utility class.
@author Ying Zhou
@version 0.1
*/
public final class AnMutationApp {
	/** Print maximal green sequences of a given quiver.
	@param givenNumber The nuumber n in An
	@param givenOrientation The given orientation to copy to the orientation part
	@throws OrientationLengthException when the number of arrows provided does not equal to n-1.
	*/
	public static void getMaximalGreenSequences(int givenNumber, ArrayList<Boolean> givenOrientation) throws OrientationLengthException {
		ArrayList<Integer> mutationList = new ArrayList<Integer>(givenNumber * (givenNumber + 1) / 2);
		//mutationList contains all mutations.
		ArrayList<OrderedCluster> clusterList = new ArrayList<OrderedCluster>(givenNumber * (givenNumber + 1) / 2);
		//clusterList contains all clusters.
		mutationList.add(0);//New nodes have mutation position 0.
		clusterList.add(new OrderedCluster(givenNumber, givenOrientation));//Initialize clusterList.
		int length = 1;//The size of the two lists.
		String str = "";//To be printed.
		int mgsCount = 0;//Count maximal green sequences.
		while (true) {
			if (length == 0) {//When the mutation list is finally empty
				return;
			}
			int nextPosition = clusterList.get(length - 1).nextMutation(mutationList.get(length - 1));
			//nextPosition to mutate at from the current bottom of the tree
			mutationList.set(length - 1, nextPosition);//nextPosition
			if (nextPosition == -1) {//No more mutations!
				if (clusterList.get(length - 1).isRed() == true) {//A maximal green sequence found
					mgsCount++;//New maximal green sequence
					str = str + mgsCount + " "; 
					for (int counter = 0; counter < length - 1; counter++) {//Print the maximal green sequence.
						str = str + clusterList.get(counter).getFirstIndex().get(mutationList.get(counter) - 1);
						str = str + clusterList.get(counter).getSecondIndex().get(mutationList.get(counter) - 1) + " "; 
					}
					str = str + "    " + clusterList.get(length - 1).getPermutationOfRedCluster().toString();
					//Print the permutation of the maximal green sequence.
					System.out.println(str); //Print MGS and permutation.
					str = "";
				}
				mutationList.remove(length - 1);
				clusterList.remove(length - 1);
				length = length - 1;//Delete the node.
			} else {//Mutation available! New node.
				clusterList.add(new OrderedCluster(clusterList.get(length - 1)));
				//Copy the last (i.e. target) node
				mutationList.add(0);//New nodes have mutation position 0.
				length = length + 1;//New node!
				clusterList.get(length - 1).mutationAtPosition(nextPosition);
				//Transform the node to its intended mutated.
			}
		}
	}
	/** Main method
	@param args standard input, currently useless.
	@throws OrientationLengthException when the number of arrows provided does not equal to n-1.
	*/
	public static void main(String args[]) throws OrientationLengthException {
		int givenNumber = 4;
		ArrayList<Boolean> givenOrientation = new ArrayList<Boolean>(1);
		givenOrientation.add(true);
		givenOrientation.add(false);
		givenOrientation.add(true);
		getMaximalGreenSequences(givenNumber, givenOrientation);
	}
}
