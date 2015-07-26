/*  QuickSort Algorithm
 *  the pivot element is chosen by the median of three method, while the median will be swapped
 *  to the leftmost index of the array
 *  Output : the original unsorted array and the sorted array 
 */ 

class ArrayCalculate
{
	// example unsorted array
	long[] elements = { 77, 99, 44, 55, 22, 88, 11, 0, 66, 33 };
	
	ArrayCalculate()
	{
		System.out.println("Unsorted Array:");
		display();
	}
	
	public void sortProcess()
	{
		// implements quicksort algorithm
		quickSort( 0, elements.length-1 );
		System.out.println("Sorted Array:");
		display();
	}
	
	private void quickSort( int left, int right )
	{
		// base case of the recursive call
		if( left == right )
			return;
		// @pivot : chooses the median value of the leftmost, center and rightmost element   
		long pivot = median( left, right );
		// @index : index of the pivot element(in its final sorted position)
		int index = partitionProcess( left, right, pivot );
		// recursive calls
		quickSort( left, index );
		quickSort( index+1, right );
	}
	
	private long median( int left, int right  )
	{
		int center = (left + right) / 2;
		//  fix the position of the median of the three index, so that the
		//  median of the three index will always appear in the leftmost position 
		if( elements[center] > elements[left] )
			swap( center, left );
		if( elements[center] > elements[right] )
			swap( center, right );
		if( elements[left] > elements[right] )
			swap( left, right );
		// returns the value of the median
		return elements[left];
	}
	
	private int partitionProcess( int left, int right, long pivot )
	{
		// the leftmost element in the array is the pivot element, so exclude that in the beginning
		int i = left + 1;
		for( int j = i; j < right; j++ )
		{
			if( elements[j] < pivot )
			{
				swap( i, j );
				i++;
			}
		}
		// swap the pivot element to its final position
		swap( i-1, left );
		return i-1;
	}
	
	private void swap( int index1, int index2 )
	{
		// swap the position of the two elements
		long temp = elements[index1];
		elements[index1] = elements[index2];
		elements[index2] = temp;
	}
	
	private void display()
	{
		for( long element : elements )
			System.out.print( element + " " );
		System.out.println();
	}
}

public class QuickSort1
{
	public static void main(String[] args)
	{
		ArrayCalculate calculation = new ArrayCalculate();
		calculation.sortProcess();
	}
}

