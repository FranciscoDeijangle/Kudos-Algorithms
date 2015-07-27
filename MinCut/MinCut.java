/* Randomized contraction algorithm for the min cut problem
 * run it many times (can use different random seeds), and remember the smallest cut that you ever find
 * The file contains the adjacency list representation of a simple undirected graph
 * The first column in the file represents the vertex label, others are
 * all the vertices that the vertex is adjacent to. 
 */

import java.io.*;
import java.util.*;

class RandomContraction
{
	int vertices;
	Map< Integer, List<Integer> > Adjacency_List;
	
	RandomContraction() throws IOException
	{
		String filepath = "C:\\Users\\ASUS\\workspace\\Algorithm\\src\\MinCut.txt";
		Scanner scanner = new Scanner( new File( filepath ) );
		List<String> file = new ArrayList<String>();
		// read in file and initiate size of vertices 
		while( scanner.hasNextLine() )
			file.add( scanner.nextLine() );
		scanner.close();
		vertices = file.size();
		// initiate adjacency list
		Adjacency_List = new HashMap< Integer, List<Integer> >();
		for( int v = 0; v < vertices; v++ )
		{
			StringTokenizer str = new StringTokenizer( file.get(v) );
			int edges = str.countTokens();
			int node  = 0;
			for( int e = 0; e < edges; e++ )
			{
				if( e == 0 )
				{
					node = Integer.valueOf( str.nextToken() );
					Adjacency_List.put( node , new ArrayList<Integer>() );
				}else
				{
					List<Integer> edgelist = Adjacency_List.get(node);
					edgelist.add( Integer.valueOf( str.nextToken() ) );
				}
			}
		}
	}
	
	public int minCutProcess()
	{
		// randomly generate the edge to cut from the graph (Adjacency_List)
		while( Adjacency_List.size() > 2 )
		{
			List<Integer> verticesList = new ArrayList<Integer>( Adjacency_List.keySet() );
			Random random = new Random();
			
			int rand1 = random.nextInt( Adjacency_List.size() );
			int rand_node1 = verticesList.get(rand1);
			
			List<Integer> edgelist = Adjacency_List.get(rand_node1);
			int rand2 = random.nextInt( edgelist.size() );
			int rand_node2 = edgelist.get(rand2);

			removeEdgeProcess( rand_node1, rand_node2 );
		}
		// return the size of the min cut, size of the remaining two keysets are the same
		int result = 0;
		for( Integer i : Adjacency_List.keySet() )
			result = Adjacency_List.get(i).size();
		return result;
	}
	
	private void removeEdgeProcess( int node1, int node2 )
	{
		List<Integer> edgelist1 = Adjacency_List.get(node1);
		List<Integer> edgelist2 = Adjacency_List.get(node2);
		// merge node, i.e. remove one of them 
		Adjacency_List.remove(node1);
		// remove the edges for the chosen two node from the adjacency list 
		edgelist1 = removeEdge( edgelist1, node2 );
		edgelist2 = removeEdge( edgelist2, node1 );
		// for all the other adjacency list remove the record of the node that has just been removed,
		// and replace it with node that stayed, meaning the edges now point to node that stayed.
		for( Integer list : edgelist1 )
		{
			List<Integer> removelist = Adjacency_List.get(list);
			for( int r = 0; r < removelist.size(); r++ )
			{
				if( removelist.get(r) == node1 )
					removelist.set( r, node2 );
			}
		}
		// add all the pointing nodes of the removed node to the one that stayed
		edgelist2.addAll(edgelist1);
		Adjacency_List.put( node2, edgelist2 );
	}
	
	private List<Integer> removeEdge( List<Integer> edgelist, int node )
	{
		// reform the list, don't use remove with the original linkedlist, will lead to null pointer
		List<Integer> resultlist = new ArrayList<Integer>();
        for (int e = 0; e < edgelist.size(); e++)
        {
            if( !(edgelist.get(e) == node ) )
            	resultlist.add( edgelist.get(e) );
        }
		return resultlist;
	}
}

public class MinCut
{
	public static void main(String[] args) throws IOException
	{
		RandomContraction randomcontraction = new RandomContraction();
		int iteration = 80;
		int[] mincut = new int[iteration];
		for( int i = 0; i < iteration; i++ )
			mincut[i] = randomcontraction.minCutProcess();
		// print the size of the minimum cut
		int min = mincut[0];
		for( int i = 1; i < iteration; i++ )
		{
			if( min > mincut[i] )
				min = mincut[i];
		}
		System.out.println(min);
	}
}
