// Greedy Algorithm implementation on unweighted set cover
// Reimplementation link : 
// http://www.lix.polytechnique.fr/~nielsen/JavaProgramming/ProgrammingAlgorithmsJava-9.pdf
import java.util.*;

class Set
{
	// number of elements and input subsets
	int nelements;
	int nsubsets ;
	// create a boolean matrix to determine what number to display
	boolean[][] incidenceMatrix;
	// stores the subsets that are chosen in order
	List<String> result = new ArrayList<String>();

	Set( int[] solutions, int[][] subsets )
	{
		this.nsubsets  = subsets.length;
		this.nelements = solutions.length;
		// initialize all the boolean matrix as false 
		incidenceMatrix = new boolean[nsubsets][nelements];
		for( int i = 0; i < nsubsets; i++ ) // data row
		{
			for( int j = 0; j < nelements; j++ ) // data column
			{
				incidenceMatrix[i][j] = false;
			}
		}
		// initialize subsets' element, i.e. true if the subset contains that element
		for( int i = 0; i < nsubsets; i++ )
		{
			for( int j = 0; j < subsets[i].length; j++ )
			{
				incidenceMatrix[i][ subsets[i][j] ] = true;
			}
		}
	}
	// Process of the algorithm
	public void SetGreedy()
	{
		int[] results = new int[nsubsets];
		// display the original matrix
		System.out.println( "Set Cover Problem:" );
		display();
		// used as system pause for step by step demo
		Scanner scanner = new Scanner( System.in );
		scanner.nextLine(); 
		// greedy algorithm for the set cover problem
		greedy();
		System.out.println("Solutions " + result );
		scanner.close();
	}
	
	private void display()
	{
		for( int i = 0; i < nsubsets; i++ )
		{
			for( int j = 0; j < nelements; j++ )
			{
				if (incidenceMatrix[i][j])
					 System.out.print("1");
				else System.out.print("0");
			}
			System.out.println("");
		}
	}
	
	private void greedy()
	{ 
		// count the number of elements that are covered
		int cover = 0;
		// index of the chosen subset
		int select;
		// looping stops if all the elements have been included
		while( cover != nelements )
		{
			select = largestSubset();
			// convert to true for the subset index that are chosen
			String string = Integer.toString(select+1);
			result.add(string);
			// add up the covered elements
			cover += cover(select);
			// update the incidenceMatrix
			update(select);
			System.out.println( "Select S" + string + ", Covered elements = " + cover );
			display();
			Scanner scanner1 = new Scanner( System.in );
			scanner1.nextLine(); // used as system pause
		}
	}
	// returns the index of the largest elements-covered subset, if there are ties
	// then it chooses the subset that first appeared
	private int largestSubset()
	{
		int max = -1, index = 0, number;
		// returns the number of elements covered for each subset and compare
		for( int i = 0; i < nsubsets; i++ )
		{
			number = cover(i);
			if( number > max )
			{
				max = number;
				index = i;
			}
		}
		return index;
	}
	
	// returns the number of elements that the input subset covers
	private int cover(int i)
	{
		int number = 0;
		for( int j = 0; j < nelements; j++ )
		{
			if( incidenceMatrix[i][j] )
				number++;
		}
		return number;
	}
	
	private void update(int select)
	{
		// remove the elements that have been included for the chosen subset for all other subset
		// remove means converting it to false, so that it will print out as 0
		for( int i = 0; i < nsubsets; i++ )
		{
			if( i != select )
			{
				for( int j = 0; j < nelements; j++ )
				{
					if( incidenceMatrix[select][j] )
						incidenceMatrix[i][j] = false;
				}
			}
		}
		// remove the chosen subset as well  
		for( int j = 0; j < nelements; j++ )
			incidenceMatrix[select][j] = false;
	}
}

public class SetCover
{
	public static void main(String[] args)
	{
		// test sample 1
		// six distinct elements ( 0 ~ 5 ) and six subsets
        // int[] solutions = { 0, 1, 2, 3, 4, 5, };
		// int[][] subsets = { {0,1,3}, {2,3,4}, {0,2,5}, {1,2,4}, {3,4,5}, {0,2} };
		
		// test sample 2
		int[] solutions = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int[][] subsets = { {0,1,2,7,8,9}, {0,1,2,3,4}, {3,4,6}, {5,6,7,8,9}, {4,5,6} }; 
		// solutions array is passed in to get the element size
		Set set = new Set( solutions, subsets );
		set.SetGreedy();
	}
}
