/*  Dijkstra's shortest-path algorithm
 *  File : Contains an adjacency list representation of an undirected weighted graph
 *  Each row consists of nodes that're adjacent to that particular vertex ( the first entry ) and length of that edge  
 *  e.g. the 6th row, has 6 in the first entry and 141,8200 as the next entry, 
 *  this denotes there is an edge between vertex 6 and vertex 141 that has length 8200.
 *  Task : compute shortest-path between 1( source vertex ) and the following ten vertices 
 *  7,37,59,82,99,115,133,165,188,197 
 */

import java.io.*;
import java.util.*;

//stores the head and its respective tails, weights
class Edges 
{
	int head;
	Map< Integer, Integer > edges = new HashMap< Integer, Integer >(); 

	Edges( int head ) 
	{
		this.head = head;
	}

	public void addEdge( int tail, int weight )
	{
		edges.put( tail, weight );
	}
	
	public String toString()
	{
		return String.format( "%d", head );
	}
}

class DijkstraCalculation
{
	Map< Integer, Integer > shortestpath;
	Edges[] adjacencylist;
	int arraysize;
	int source;	
	
	DijkstraCalculation() throws IOException
	{
		// read in raw data
		String filepath = "C:\\Users\\ASUS\\workspace\\Algorithm\\src\\Dijkstra.txt";
		Scanner scanner = new Scanner( new File( filepath ) );
		List<String> data = new ArrayList<String>();
		
		while( scanner.hasNextLine() )
			data.add( scanner.nextLine() );
		scanner.close();
		
		// initiate adjacency list array, add 1 to the size because there're no node 0
		arraysize = data.size()+1;
		adjacencylist = new Edges[arraysize]; 

		// set up the adjacency list
		for( String string : data )
		{
			String[] linesplit = string.split("\\s+");
			int head = Integer.valueOf( linesplit[0] );
			Edges edge = new Edges(head);
			
			for( int l = 1; l < linesplit.length; l++ )
			{
				StringTokenizer str = new StringTokenizer( linesplit[l], "," );
				int tail   = Integer.valueOf( str.nextToken() );
				int weight = Integer.valueOf( str.nextToken() );
				edge.addEdge( tail, weight );
			}
			adjacencylist[head] = edge;
		}
	}
	
	public int[] calculationProcess( int sourcenode )
	{
		// initiate distance of the array, the source vertex is initiated as 0
		source = sourcenode;
		int[] shortestdistance = new int[arraysize];
		Arrays.fill( shortestdistance, Integer.MAX_VALUE );
		shortestdistance[source] = 0;
		
		// initiate the queue with the source node and the shortest path
		shortestpath  = new HashMap< Integer, Integer >();
		Queue<Integer> node = new PriorityQueue<Integer>();
		node.add(source);
		
		// visit each existing edges of each node, if the cumulative distance to the tail of that node is smaller than 
		// the current shortest path, update shortest path distance to the tail of that node 
		// also update the shortest path to the head of that node
		while( !node.isEmpty() )
		{
			int head = node.poll();
			for( Integer tail : adjacencylist[head].edges.keySet() )
			{
				int edgelength = adjacencylist[head].edges.get(tail);
				if( shortestdistance[tail] > shortestdistance[head] + edgelength )
				{
					shortestdistance[tail] = shortestdistance[head] + edgelength;
					node.add(tail);
					shortestpath.put( tail, head );
				}
			}
		}
		return shortestdistance;
	}
	
    public List<Integer> getShortestPath( int destination )
    {
    	List<Integer> path = new ArrayList<Integer>();
    	// back trace the shortest path from the destination to the source
    	while( shortestpath.get(destination) != null )
    	{
    		path.add(destination);
    		int node = shortestpath.get(destination);
    		destination = node;
     	}
    	path.add(source);
    	
        Collections.reverse(path);
        return path;
    } 
}

public class Dijkstra
{
	public static void main(String[] args) throws IOException
	{
		DijkstraCalculation calculation = new DijkstraCalculation();
		// pass the source node
		int source = 1;
		int[] shortestdistance = calculation.calculationProcess(source); 
		
		// print the shortest path distances to the specified destination
		int[] destinations = { 7, 37, 59, 82, 99, 115, 133, 165, 188, 197 };
		for( int node : destinations )
			System.out.print( shortestdistance[node] + "," );
		System.out.println();
		
		// print the traversed nodes of the shortest path from source to destination node
		int destination = 7;
		System.out.println(calculation.getShortestPath(destination) );
		
	}
}


