// Merge Sort for counting number of inversions in an given unsorted array.

import java.io.*;
import java.util.*;

class Inverse
{
	public int[] array;
	
	Inverse( int[] unsorted )
	{
		array = new int[unsorted.length];
		// make a copy of the unsorted array
		for( int i = 0; i < unsorted.length; i++ )
			array[i] = unsorted[i];
		System.out.println( "Number of Inverses: " + mergeSort( unsorted, 0, unsorted.length - 1 ) );
	}
	
	private long mergeSort( int[] unsorted, int lowerBound, int higherBound  )
	{
		// base case : return the count of inversions as 0
		if( lowerBound == higherBound )
			return 0;
		// index of the highest point for the left half array
		int mid = ( lowerBound + higherBound ) / 2;
		// return the recursive cumulative count 
		return mergeSort( unsorted, lowerBound, mid ) + mergeSort( unsorted, mid + 1, higherBound ) + merge( unsorted, lowerBound, mid + 1, higherBound );          
	}
	
	private long merge( int[] unsorted, int lowpt, int midpt, int highpt )
	{
		// initiate the number of count for the inversions
		long count = 0;
		int j = 0;
		// store the constant of the initial location of the passed in array
		int lowpoint = lowpt; 
		// make a constant upper bound for the left array
		int midpoint = midpt - 1;
		// length of the sub-array at that recursive level
		int length = highpt - lowpoint + 1;
		// process of rearranging the arrays in correct orders 
		while( lowpt <= midpoint && midpt <= highpt )
		{
			if( array[lowpt] < array[midpt] )
				unsorted[j++] = array[lowpt++];
			else
			{
				unsorted[j++] = array[midpt++];
				// the number of inversion is the count of numbers that have are left in the left
				// array when that number of right array is drawn, that is, difference in indices remember to add 1
				count += ( midpoint - lowpt + 1 );
			}				
		}
		// fill in the numbers if the other half of the array was already filled
		while( lowpt <= midpoint )
			unsorted[j++] = array[lowpt++];
		while( midpt <= highpt )
			unsorted[j++] = array[midpt++];
		// fill in the numbers after arranging. remember to start filling from the low point
		System.arraycopy( unsorted, 0, array, lowpoint, length );
		
		return count;
	}
}

// Extension of the mergeSort to counting the number of inversions of an 
// arbitrarily ordered integer array, with no integer repeated.
public class ArrayInversion 
{
	public static void display( int[] array )
	{
		for( int i = 0; i < array.length; i++)
			System.out.print( array[i] + " " );
		System.out.println("");
	}
	
	public static void main(String[] args) throws IOException
	{
		String filepath = "C:\\Users\\ASUS\\workspace\\Algorithm\\src\\ArrayInversion.txt";
		Scanner scanner = new Scanner( new File(filepath) );
		List<Integer> pass = new ArrayList<Integer>();
		while( scanner.hasNextLine() )
			pass.add( scanner.nextInt() );

		scanner.close();
		// pass the array list to an array
		int[] temp = new int[pass.size()];
		for( int i = 0; i < pass.size(); i++ )
			temp[i] = pass.get(i);
		
		// return and prints the number of inversions
		Inverse inverse = new Inverse(temp); // 2407905288
		
		// -------------------------------------------------------------
		// testing sample
		/*int[] a1 = { 2, 4, 1, 3, 5 }; // 3
		int[] a2 = { 4, 3, 2, 1 };    // 6
		int[] a3 = { 1, 2, 3, 4 };    // 0
		display(a1);
		Inverse inverse = new Inverse(a1);
		display(inverse.array);*/
	}
}
