/*  Random Hill Climbing Algorithm to Travel Salesman problem
 *  specify the number of iteration to train it, cities are randomly swapped to see if it improves optimization
 *  The file contains the 
 *  The first column represents the city's index, the second and third column are the x and y coordinates of the city
 *  Reimplementation link
 *  http://www.programmershare.com/632078/ 
 */

import java.io.*;
import java.util.*;

class TravelSalesman
{
	String filepath = "C:\\Users\\ASUS\\workspace\\Algorithm\\src\\HillClimbing.txt";
	int iteration = 500; // number of iteration for the hill climbing (training the answer)
	int citynum; // number of cities = size of data
	int[] x, y; // array that stores the x and y coordinates
	int[][] distances; // pairwise distance matrix
	int[] citytour; // order of the city, i.e. tour
	
	TravelSalesman() throws IOException
	{
		// read in the data, get the number of cities, and initialize the array
		Scanner scanner = new Scanner( new File(filepath) );
		List<String> data = new ArrayList<String>();  

		while( scanner.hasNextLine() )
			data.add( scanner.nextLine() );

		citynum = data.size();
		x = new int[citynum]; y = new int[citynum];
		
		// split the strings into arrays and extract the x and y coordinates
		for( int i = 0; i < citynum; i++ )
		{
			String line = data.get(i);
			String[] stringsplit = line.split(" ");
			x[i] = Integer.valueOf(stringsplit[1]); // x  
	        y[i] = Integer.valueOf(stringsplit[2]); // y  
		}
		scanner.close();
	}
	
	public void process()
	{
		distances = new int[citynum][citynum];
		// calculate the distance matrix
		for( int i = 0; i < citynum; i++ )
		{
			for( int j = i+1; j < citynum; j++ )
			{
				double dist = Math.sqrt( Math.pow( x[i] - x[j], 2 ) + Math.pow( y[i] - y[j], 2 ) );
				distances[j][i] = distances[i][j] = (int)Math.round(dist);
			}
		}
		// initialize the city tour ( the order is selected randomly )
		init();
		// train the city tour with hill climbing, where two cities randomly switch places 
		// and see if the algorithm improves 
		evaluate();
	}
	
	private void init()
	{
		citytour = new int[citynum];
		// generate a random city tour order
		List<Integer> random = new ArrayList<Integer>();
		for( int i = 0; i < citynum; i++ )
			random.add(i);
		// shuffle the collection
		Collections.shuffle(random);
		// pass it back to the array
		for( int i = 0; i < citynum; i++ )
			citytour[i] = random.get(i); 
	}
	// process of the hill climbing algorithm
	private void evaluate()
	{
		// initialize the cost of the tour with the first random generated tour
		int bestcosts = calculatecost(citytour);
		display(citytour);
		System.out.println( "Inital Costs: " + bestcosts );
		// initialize the temp tour with the first random generated tour
		int[] temptour = new int[citynum];

		// cities that swap places
		int rand1; int rand2;
		Random random = new Random();
		// store the best answer's iteration
		int bestiteration = 0;
		
		for( int t = 0; t < iteration; t++ )
		{
			for( int i = 0; i < citynum; i++ )
				temptour[i] = citytour[i];
			// generate the indexes in the city tour that should swap places
			rand1 = random.nextInt(citynum);
			rand2 = random.nextInt(citynum);
			while( rand1 == rand2 )
				rand2 = random.nextInt(citynum);
			// swap places
			int temp = temptour[rand1];  
			temptour[rand1] = temptour[rand2];  
			temptour[rand2] = temp; 
			// compare the cost, if it's better, store the distance and pass the city tour order 
			int costs = calculatecost(temptour);
			if( costs < bestcosts )
			{
				bestcosts = costs;
				bestiteration = t;
				for( int i = 0; i < citynum; i++ )
					citytour[i] = temptour[i];
			}
			// display first iteration for demo use
			if( 0 < t && t < 3 )
			{
				Scanner pause = new Scanner(System.in);
				pause.nextLine();
				display(citytour);
				System.out.println( "------------------------------------------------" );
				System.out.println( "Switched:");
				System.out.println( "Index: "+ rand1 + " " + "City: " + temptour[rand2] + ", Index: " + rand2 + " City: " + temptour[rand1] );   
				System.out.println( "Costs: " + bestcosts );
			}
		}
		// prints the best tour along with its costs and the number of iteration that got the answer
		Scanner pause = new Scanner(System.in);
		pause.nextLine();
		display(citytour);
		System.out.println( "Best Costs: " + bestcosts );
		System.out.println( "Best Iterations: " + bestiteration );
		pause.close();
	}
	// calculate the total cost for each tour order
	private int calculatecost( int[] tour )
	{
		int cost = 0;
		for( int i = 1; i < citynum; i++ )
			cost += distances[tour[i-1]][tour[i]];
		// include the last city to the first city's distance
		cost += distances[tour[citynum-1]][tour[0]];
		return cost;
	}
	
	private void display( int[] array)
	{
		System.out.println("Tour: ");
		for( int i = 0; i < citynum; i++ )
		{
			System.out.print( array[i] + " " );
			if( i % 24 == 0 && i != 0 )
				System.out.println();
		}
		System.out.println();
	}
	
}
 
public class HillClimbing
{
	public static void main(String[] args) throws IOException
	{
		TravelSalesman travelsalesman = new TravelSalesman();
		travelsalesman.process();
	}
}
