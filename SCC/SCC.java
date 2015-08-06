/*  Kosaraju's algorithm
 *  obtain the leading node and the size of the strong connected component(scc) of a given directed graph, 
 *  where scc is defined as you can traverse from one of the node to any other node within that component 
 *  The corresponding file contains the edges of a directed graph, every pair of number indicates an edge,
 *  the vertex label in first column is the tail and the vertex label in second column is the head.
 */

import java.util.Set;
import java.util.*;
import java.io.*;

class SCCCalculation
{
	Map< Integer, List<Integer> > adjacencylist, reversed_adjacencylist;
	Set<String> uniquenodes;
	boolean[] explored;
	int    [] finishingtime;
	int countfinishingtime;
	int countstrongcomponentsize;
	public List<Integer> strongcomponentsize;
	public List<Integer> leadernode;
	
	SCCCalculation( String filepath ) throws IOException
	{
		// read in files and initialize the number of edges
		Scanner scanner   = new Scanner( new File( filepath ) );
		List<String> data = new ArrayList<String>();
		
		while( scanner.hasNextLine() )
			data.add( scanner.nextLine() );
		scanner.close();
		
		// obtain the unique nodes 
		uniquenodes = new HashSet<String>();
		for( int i = 0; i < data.size(); i++ )
		{
			String line = data.get(i);
			StringTokenizer str = new StringTokenizer(line);
			while( str.hasMoreTokens() )
				uniquenodes.add( str.nextToken() );
		}
		
		// initiate both hashmaps with the unique nodes, and fill in the adjacency list
		adjacencylist          = new HashMap< Integer, List<Integer> >();
		reversed_adjacencylist = new HashMap< Integer, List<Integer> >();
		for( String nodes : uniquenodes )
		{
			int node = Integer.valueOf(nodes);
			adjacencylist.put( node, new ArrayList<Integer>() );
			reversed_adjacencylist.put( node, new ArrayList<Integer>() );
		}		

		for( int i = 0; i < data.size(); i++ )
		{
			String line = data.get(i);
			String[] linesplit = line.split(" ");
			int tail = Integer.valueOf(linesplit[0]);
			int head = Integer.valueOf(linesplit[1]);
			adjacencylist.get(tail).add(head);
			reversed_adjacencylist.get(head).add(tail);
		}
	}
	
	public void Kosaraju()
	{
		// there're no node 0, therefore add 1 to the initialized size
		finishingtime = new int[uniquenodes.size()+1];
		
		// pass 1 : loop from the largest node index on reversed adjacency list, count the finishing time for each node
		// pass 2 : loop from the largest finishing time on adjacency list, count the size of all the strongly connected component
		for( int pass = 1; pass <= 2; pass++ )
		{
			// refresh whether the node has be explored or not, if not invoke dfs
			explored = new boolean[uniquenodes.size()+1];

			if( pass == 1 )
			{
				countfinishingtime = 1;
				for( int j = uniquenodes.size(); j > 0; j-- )
				{
					if( !explored[j] )
						reversedDepthFirstSearch(j);
				}
			}else
			{
				strongcomponentsize = new ArrayList<Integer>();
				leadernode          = new ArrayList<Integer>();
				// rearrange the order of the node according to its finishing time
				int[] node = new int[uniquenodes.size()+1]; 
				for( int j = 0; j < uniquenodes.size()+1; j++ )
					node[ finishingtime[j] ] = j;

				for( int j = uniquenodes.size(); j > 0; j-- )
				{
					countstrongcomponentsize = 0;
					if( !explored[ node[j] ] )
					{
						leadernode.add(node[j]);
						DepthFirstSearch(node[j]);
						strongcomponentsize.add(countstrongcomponentsize);
					}						
				}
			}
		}
	}
	
	private void reversedDepthFirstSearch( int tail )
	{
		explored[tail] = true;
		for( Integer head : reversed_adjacencylist.get(tail) )
		{
			if( !explored[head] )
				reversedDepthFirstSearch(head);
		}
		finishingtime[tail] = countfinishingtime++;
	}
	
	private void DepthFirstSearch( int tail )
	{
		explored[tail] = true;
		for( Integer head : adjacencylist.get(tail) )
		{
			if( !explored[head] )
				DepthFirstSearch(head);
		}
		countstrongcomponentsize++;
	}
}

public class SCC
{
	public static void main(String[] args) throws IOException
	{
		String filepath = "C:\\Users\\ASUS\\Kudos-Algorithms\\SCC\\SCC.txt";
		
		SCCCalculation scc = new SCCCalculation(filepath);
		scc.Kosaraju();
		// pair the leader's node with its corresponding strong component size
		Map< Integer, Integer > strongcomponents = new HashMap< Integer, Integer >();
		for( int s = 0; s < scc.leadernode.size(); s++ )
			strongcomponents.put( scc.leadernode.get(s), scc.strongcomponentsize.get(s) );
		System.out.println(strongcomponents);
	}
}

