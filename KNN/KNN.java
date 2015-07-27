/* K nearest neighborhood used for predicting the class label
 * training data consists of the attributes and the class label (last column),
 * given the attribute of one testing data, predict its class label
 * Output : The count of the K nearest class label, and the predicted class
 * Reimplementation link:
 * https://www.youtube.com/watch?v=koPhesRroLY
 */

import java.io.*;
import java.util.*;

class KNNCalculations
{
	String trainingdataFile = "C:\\Users\\ASUS\\Desktop\\datamining_practice\\KNN\\training data.txt";
	String testingdataFile  = "C:\\Users\\ASUS\\Desktop\\datamining_practice\\KNN\\testing data.txt" ;	
	int K = 4;  // parameter's for KNN; decide the K nearest neighborhood
	List<Instance> instanceList = new ArrayList<Instance>(); // store the original training data (attribute and class label of each instances)
	double query [][]; // store testing data set
	int querySize;     // size of the testing data 
	KNNCalculations() throws IOException
	{
		// read in training data
		Scanner scanner1 = new Scanner ( new File(trainingdataFile) );
		List<String> list1 = new ArrayList<String>(); 

		while( scanner1.hasNextLine() )
			list1.add( scanner1.nextLine() );
		scanner1.close();
		
		StringTokenizer st1 = new StringTokenizer( list1.get(0) );
		int counttokensize = st1.countTokens();
		// initialize the data size ; exclude the class label
		double[][] data = new double[list1.size()][counttokensize-1];
		String[][] dataclass = new String[list1.size()][1];
		
		for( int i = 0; i < list1.size(); i++ ) 
		{
			st1 = new StringTokenizer( list1.get(i) );
			for(int j = 0; j < counttokensize; j++ )
			{
				if( j == ( counttokensize-1 ) ) // the last token is put to data class instead of the data
					dataclass[i][0] = ( st1.nextToken() );
				else
					data[i][j] = Double.parseDouble( st1.nextToken() ) ;
			}
		}
		// add the data and data class into instanceList
		for( int i = 0; i < list1.size(); i++ ) 
		{
			String str = new String(); 
			str = dataclass[i][0];
			instanceList.add( new Instance( data[i], str ) );
		}	
		
		// read in the query data
		Scanner scanner2 = new Scanner ( new File(testingdataFile ) );
		List<String> list2 = new ArrayList<String>();
		
		while( scanner2.hasNextLine() )
			list2.add( scanner2.nextLine() );
		scanner2.close();
		// initialize the query data set
		querySize = list2.size();
		
		StringTokenizer st2 = new StringTokenizer( list2.get(0) );
		query = new double[querySize][st2.countTokens()];

		for( int i = 0; i < querySize; i++ )
		{
			String lines = list2.get(i);
			String[] linesplit = lines.split(" ");
			for( int j = 0; j < st2.countTokens(); j++ )
				query[i][j] = Double.parseDouble(linesplit[j]);
		}
	}
	
	public void KNNprocess()
	{
		// @distanceList : stores the distance to the testing data and its corresponding class label
		List<Distance> distanceList;
		for( int q = 0; q < querySize ; q++ )
	    {	
			distanceList = new ArrayList<Distance>();
			//calculate the distance of the query with every training data
		    for( Instance instance : instanceList ) 
		    {
		    	double dist = 0;
		    	for( int j = 0; j < instance.attribute.length; j++ )
		    		dist += Math.pow( ( instance.attribute[j] - query[q][j] ), 2 );

		    	double total_distance = Math.sqrt(dist);
		    	distanceList.add( new Distance( total_distance, instance.classLabel ) );
		    }
		    // sort the distance in increasing order
		    Collections.sort(distanceList, new Comparator<Distance>()
		    {
		    	public int compare( Distance d1, Distance d2 )
		    	{
		    		return (int)(d1.distance - d2.distance);
		    	}
		    });
		    
		    // get the K nearest class labels 
		    List<String> classLabels = new ArrayList<String>();
		    for( int k = 0; k < K; k++ )
		    	classLabels.add( distanceList.get(k).classLabel ); 

		    System.out.println( " " + findMajorityClass(classLabels) );
	   }	  
	}
	
	private String findMajorityClass(List<String> classlabels)
	{
		// add the class labels to a HashSet to get unique class labels
		Set <String> distinctClass  = new HashSet  <String>(classlabels); 
		List<String> distinctLabels = new ArrayList<String>(distinctClass); 
		// count of each unique class labels
		int count[] = new int [distinctLabels.size()]; 
		for( int i = 0; i < distinctLabels.size(); i++ ) 
		{
			for( int j = 0; j < classlabels.size(); j++ )
			{
				if( distinctLabels.get(i).equals( classlabels.get(j) ) ) 
					count[i]++;
			}
		}
		// print the corresponding count of each distinctLabels
		for( int i = 0; i< distinctLabels.size(); i++ ) 
			System.out.print( distinctLabels.get(i) + " " + count[i] + " " );
		// get the maximum count
		int max = count[0];
		for( int i = 1; i < count.length; i++ )
		{
			if( max < count[i] )
			max = count[i]; 
		}		
		// count how many times the maximum count occur, that is if there are ties for the count
		// of the class labels, choose one at random
		int freq = 0;  
		for( int i = 0; i < count.length; i++ )
		{
			if( max == count[i] )
			freq++;
		}
		// @majorityclass : store the majority class to return
		String majorityclass = new String();  
		if( freq == 1 )
		{
			for( int i = 0; i < count.length; i++ )
			{
				if( max == count[i] )
				{	
					majorityclass = distinctLabels.get(i);
					break;
				}	
			}
			
		}else
		{
			// @mode : store the class labels that are max
			String mode[] = new String[freq]; 
			int freqcount = 0;
			for( int i = 0; i < count.length; i++ )
			{
				if( max == count[i] )
				{	
					mode[freqcount] = distinctLabels.get(i);
					freqcount++;
				}	
			}
			majorityclass = mode[(int)(Math.random()*freq)];
		}
		return majorityclass;
	}
}
//store each training data's instance's value and it's class label
class Instance  
{
	double attribute[];
	String classLabel;
	public Instance( double attribute[], String classLabel )
	{
		 this.attribute  = attribute ;
		 this.classLabel = classLabel;
	}
}

//store the distances(after calculation of distances from instance) and the class label
class Distance 
{
	double distance;
	String classLabel;
	public Distance( double distance, String classLabel)
	{
		this.distance   = distance  ;
		this.classLabel = classLabel;
	}
	public String toString()
	{
		return String.format("%6.3f %s", distance, classLabel);
	}
}

public class KNN //K- Nearest Neighborhood  ; lazy learner 
{
	public static void main(String[] args) throws IOException
	{
		KNNCalculations kNNcalculations = new KNNCalculations();
		kNNcalculations.KNNprocess();
	}
}

