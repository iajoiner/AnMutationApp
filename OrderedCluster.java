import java.io.*;
import java.util.ArrayList;
/** This class contains an ordered cluster
@author Ying Zhou
@version 0.1
*/
public class OrderedCluster {
	int number;
	ArrayList<Integer> firstIndex;//i in x_{ij}
	ArrayList<Integer> secondIndex;//j in x_{ij}
	ArrayList<Boolean> sign;//true for x_{ij}, false for x_{ij}^{-1}
	ArrayList<Boolean> orientation;//orientation of the quiver
	/** Generate the initial green ordered cluster of An
	@param givenNumber The nuumber n in An
	@param givenOrientation The given orientation to copy to the orientation part
	@throws OrientationLengthException when the number of arrows provided does not equal to n-1.
	*/
	OrderedCluster(int givenNumber, ArrayList<Boolean> givenOrientation) throws OrientationLengthException{//{x_{01},x_{12}...x_{n-1,n}}
		number = givenNumber;
		firstIndex = new ArrayList<Integer>(number);
		secondIndex = new ArrayList<Integer>(number);
		sign = new ArrayList<Boolean>(number);
		orientation = new ArrayList<Boolean>(number - 1);
		for (int counter = 0; counter < number; counter++) {
			firstIndex.add(counter); 
			secondIndex.add(counter + 1);
			sign.add(true);
		}
		if (givenOrientation.size() != number - 1) {//givenOrientation is of bad length
			throw new OrientationLengthException(givenOrientation.size(),number - 1);
		} else {//Copy givenOrientation to orientation
				for (int counter = 0; counter < number - 1; counter++) {
					orientation.add(givenOrientation.get(counter));
				}
		}
	}
	/** Generate an ordered cluster of An from a preexisting copy
	@param givenOrderedCluster The preexisting copy of ordered cluster
	*/
	OrderedCluster(OrderedCluster givenOrderedCluster) {
		number = givenOrderedCluster.number;
		firstIndex = new ArrayList<Integer>(number);
		secondIndex = new ArrayList<Integer>(number);
		sign = new ArrayList<Boolean>(number);
		orientation = new ArrayList<Boolean>(number - 1);
		for (int counter = 0; counter < number; counter++) {
			firstIndex.add(givenOrderedCluster.firstIndex.get(counter)); 
			secondIndex.add(givenOrderedCluster.secondIndex.get(counter));
			sign.add(givenOrderedCluster.sign.get(counter));
		}
		for (int counter = 0; counter < number - 1; counter++) {
			orientation.add(givenOrderedCluster.orientation.get(counter));
		}
	}
	/** Get number
	@return number
	*/
	public int getNumber() {
		return number;
	}
	/** Get firstIndex
	@return firstIndex
	*/
	public ArrayList<Integer> getFirstIndex() {
		return firstIndex;
	}
	/** Get secondIndex
	@return secondIndex
	*/
	public ArrayList<Integer> getSecondIndex() {
		return secondIndex;
	}
	/** Get sign
	@return sign
	*/
	public ArrayList<Boolean> getSign() {
		return sign;
	}
	/** Get orientation
	@return orientation
	*/
	public ArrayList<Boolean> getOrientation() {
		return orientation;
	}
	/** Print the ordered cluster
	@return rhe ordered cluster
	*/
	public String toString() {
		String str = "{ ";
		for (int counter = 0; counter < number; counter++) {
			if (sign.get(counter) == false) {
				str = str + "-";
			}
			str = str + firstIndex.get(counter) + secondIndex.get(counter) + " ";
		}
		str = str + "}";
		return str;
	}
	/** Find the position of a positive c-vector \beta_{ij}
	@param givenFristIndex i of \beta_{ij}
	@param givenSecondIndex j of \beta_{ij}
	@return the position if it can be found (starting from 1), 0 if +\beta_{ij} is not in the cluster.	
	*/
	public int findRoot(int givenFirstIndex, int givenSecondIndex) {
		if (givenFirstIndex < 0 || givenFirstIndex >= givenSecondIndex || givenSecondIndex > number) {//Invalid c-vectors
			return 0;
		}
		for (int counter = 0; counter < number; counter++) {
			if (firstIndex.get(counter) != givenFirstIndex || secondIndex.get(counter) != givenSecondIndex || sign.get(counter) == false) {//Not +ij
				continue;
			}
			return counter + 1;//+ij found
		}
		return 0;//+ij not found
	}
	/** Do mutation at position pos when the c-vector is green
	@param pos the position at which to do the mutation, starting from 1.
	@return true if mutation is possible false if not
	*/
	public boolean mutationAtPosition(int pos) {
		if (pos <= 0 || pos > number) {//pos is outside the range
			return false;
		}
		else {
			if (sign.get(pos - 1) == false) {
				return false;
			}
			int i = firstIndex.get(pos - 1);//1 to 0 conversion
			int j = secondIndex.get(pos - 1);
			sign.set(pos - 1, false);//x_{ij} becomes -x_{ij}
			for (int counter = 0; counter < number; counter++) {
				if (counter == pos - 1) {
					continue;
				}
				if (firstIndex.get(counter) != i && firstIndex.get(counter) != j && secondIndex.get(counter) != i && secondIndex.get(counter) != j) {//Easy pass
					continue;
				}
				if (secondIndex.get(counter) == i && orientation.get(i - 1) == true && sign.get(counter) == true) {//Left Type 1, +ki
					secondIndex.set(counter, j);//+ki->+kj
				}
				if (firstIndex.get(counter) == j && orientation.get(j - 1) == false && sign.get(counter) == true) {//Right Type 1, +jk
					firstIndex.set(counter, i);//+jk->+ik
				}
				if (secondIndex.get(counter) == j && firstIndex.get(counter) < i &&  sign.get(counter) == false && orientation.get(i - 1) == false) {//Left Type 2, -kj
					secondIndex.set(counter, i);//-kj->-ki
				}
				if (firstIndex.get(counter) == i && secondIndex.get(counter) > j &&  sign.get(counter) == false && orientation.get(j - 1) == true) {//Right Type 2, -ik
					firstIndex.set(counter, j);//-ik->-jk
				}	
				if (firstIndex.get(counter) == i && secondIndex.get(counter) < j &&  sign.get(counter) == false && orientation.get(secondIndex.get(counter) - 1) == false) {//Left Type 3, -ik
					sign.set(counter, true);//Change sign
					firstIndex.set(counter, secondIndex.get(counter));
					secondIndex.set(counter, j);//-ik->+kj
				}
				if (secondIndex.get(counter) == j && firstIndex.get(counter) > i &&  sign.get(counter) == false && orientation.get(firstIndex.get(counter) - 1) == true) {//Right Type 3, -kj
					sign.set(counter, true);//Change sign
					secondIndex.set(counter, firstIndex.get(counter));
					firstIndex.set(counter, i);//-kj->+ik
				}
			}
			return true;
		}
	}
	/** Do mutation at a positive c-vector \beta_{ij}
	@param givenFristIndex i of \beta_{ij}
	@param givenSecondIndex j of \beta_{ij}
	@return true if the mutation is done, false if it is impossible.	
	*/
	public boolean mutationAtRoot(int givenFirstIndex, int givenSecondIndex) {
		if (givenFirstIndex < 0 || givenFirstIndex >= givenSecondIndex || givenSecondIndex > number) {//Invalid c-vectors
			return false;
		}
		for (int counter = 0; counter < number; counter++) {
			if (firstIndex.get(counter) != givenFirstIndex || secondIndex.get(counter) != givenSecondIndex || sign.get(counter) == false) {//Not +ij
				continue;
			}
			return mutationAtPosition(counter + 1);//+ij found
		}
		return false;//+ij not found
	}
	/** Return whether the cluster is red.
	@return true if the cluster is red, false if it is not the case.
	*/
	public boolean isRed() {
		for (int counter = 0; counter < number; counter++) {
			if (sign.get(counter) == true) {//Not red
				return false;
			}
		}
		return true;//Nothing green is found, hence red.
	}
	/** Get the associated permutation of a red ordered cluster.
	@return null if the cluster is not red, otherwise produce the permutation.
	*/
	public ArrayList<Integer> getPermutationOfRedCluster() {
		if (isRed() == false) {//Not red.
			return null;
		}
		ArrayList<Integer> permutation = new ArrayList<Integer>(number);
		for (int counter = 0; counter < number; counter++) {
			permutation.add(0);
		}
		for (int counter = 0; counter < number; counter++) {
			permutation.set(firstIndex.get(counter), counter + 1);//This is pretty hard to read. When red, we have all roots simple, hence i = j - 1. 
		}
		return permutation;
	}
	/** Get the next mutation.
	@param pos position of last mutation, starting from 1. If no prior mutations exist pos = -1.
	@return position of next mutation. If no next mutation is possible return -1.	
	*/
	public int nextMutation(int pos) {
		if (pos > number || pos < 0) {//Invalid position
			return -1;
		}
		for (int counter = pos; counter < number; counter++) {//Real position starts from pos + 1. Since pos starts from 1 while counter starts from 0, counter goes from (pos + 1) - 1 = pos.
			if (sign.get(counter) == true) {//New position
				return counter + 1;
			}
		}
		return -1;
	}
}
