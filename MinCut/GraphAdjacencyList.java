// Reimplementation of the link:
// http://www.sanfoundry.com/java-program-describe-representation-graph-using-adjacency-list/

import java.util.*;

class AdjacencyList1
{
	int vertices = 4;
	int edges = 5;
	Map< Integer, List<Integer> > Adjacency_List;
	AdjacencyList1()
	{
		// initiate the list size ; match it with the number of vertices
		Adjacency_List = new HashMap< Integer, List<Integer> >();
		for( int v = 1; v <= vertices; v++ )
			Adjacency_List.put( v, new LinkedList<Integer>() );
	}
	
	public void process()
	{
		int source, destination;
		// read the edges in the graph
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the edges in graph Format : <source index> <destination index>");
		int count = 0;
		while( count < edges )
		{
			source = scan.nextInt();
			destination = scan.nextInt();
			setEdge( source, destination );
			count++;
		}
		scan.close();
	    display();
	}
	
	private void setEdge( int source, int destination )
	{
		List<Integer> slist = Adjacency_List.get(source);
	    slist.add(destination);
	    List<Integer> dlist = Adjacency_List.get(destination);
	    dlist.add(source);
	}
	
	private void display()
	{
		System.out.println( "the given Adjacency List for the graph" );
		for( int v = 1; v <= vertices; v++ )
		{
			System.out.print( v + "->" );
			List<Integer> edgeList = Adjacency_List.get(v);
			int e = 0;
			while( e < edgeList.size() )
			{
				if( e == edgeList.size() - 1 )
					System.out.print( edgeList.get(e) );
				else
					System.out.print( edgeList.get(e) + "->" );
				e++;
			}
			System.out.println();
		}
	}
}

public class GraphAdjacencyList
{
	public static void main(String[] args)
	{
		/* example : enter the edges
		 * 1 2 
		 * 2 3
		 * 3 4
		 * 4 1
		 * 1 3
		 * output : adjacency list
		 * 1->2->4->3 
		 * 2->1->3
		 * 3->2->4->1
		 * 4->3->1
		 */
		AdjacencyList1 adjacencylist = new AdjacencyList1();
		adjacencylist.process();
	}
}
